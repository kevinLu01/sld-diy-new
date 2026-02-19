package com.sld.diy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sld.diy.dto.PricingBreakdownItem;
import com.sld.diy.dto.PricingCalcRequest;
import com.sld.diy.dto.PricingCalcResponse;
import com.sld.diy.dto.PricingItemInput;
import com.sld.diy.entity.Sku;
import com.sld.diy.entity.SolutionPackageItem;
import com.sld.diy.mapper.SkuMapper;
import com.sld.diy.mapper.SolutionPackageItemMapper;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PricingService {
  private final SkuMapper skuMapper;
  private final SolutionPackageItemMapper itemMapper;

  public PricingService(SkuMapper skuMapper, SolutionPackageItemMapper itemMapper) {
    this.skuMapper = skuMapper;
    this.itemMapper = itemMapper;
  }

  public PricingCalcResponse calc(PricingCalcRequest request) {
    List<PricingItemInput> items = request.items();

    if ((items == null || items.isEmpty()) && request.packageId() != null) {
      List<SolutionPackageItem> packageItems = itemMapper.selectList(
        new QueryWrapper<SolutionPackageItem>().eq("package_id", request.packageId())
      );
      items = packageItems.stream()
        .map(item -> new PricingItemInput(item.getSkuId(), item.getQuantity()))
        .toList();
    }

    if (items == null || items.isEmpty()) {
      return new PricingCalcResponse(0, List.of());
    }

    List<Long> skuIds = items.stream().map(PricingItemInput::skuId).distinct().toList();
    Map<Long, Sku> skuMap = skuMapper.selectBatchIds(skuIds).stream()
      .collect(Collectors.toMap(Sku::getId, sku -> sku));

    BigDecimal factor = regionFactor(request.regionCode());
    List<PricingBreakdownItem> breakdown = new ArrayList<>();
    int total = 0;

    for (PricingItemInput item : items) {
      Sku sku = skuMap.get(item.skuId());
      int unit = sku == null ? 0 : sku.getBasePriceCents();
      int quantity = item.quantity() == null ? 0 : item.quantity();
      int line = unit * quantity;
      int adjusted = factor.multiply(BigDecimal.valueOf(line)).setScale(0, RoundingMode.HALF_UP).intValue();
      total += adjusted;
      breakdown.add(new PricingBreakdownItem(item.skuId(), quantity, unit, adjusted));
    }

    return new PricingCalcResponse(total, breakdown);
  }

  private BigDecimal regionFactor(String regionCode) {
    if (regionCode == null || regionCode.isBlank()) {
      return BigDecimal.ONE;
    }
    return switch (regionCode) {
      case "east" -> BigDecimal.valueOf(1.05);
      case "north" -> BigDecimal.valueOf(1.02);
      case "south" -> BigDecimal.valueOf(1.03);
      default -> BigDecimal.ONE;
    };
  }
}
