package com.sld.diy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sld.diy.dto.PackageDetailDto;
import com.sld.diy.dto.PackageItemDto;
import com.sld.diy.dto.PackageSummaryDto;
import com.sld.diy.entity.Sku;
import com.sld.diy.entity.SolutionPackage;
import com.sld.diy.entity.SolutionPackageItem;
import com.sld.diy.mapper.SkuMapper;
import com.sld.diy.mapper.SolutionPackageItemMapper;
import com.sld.diy.mapper.SolutionPackageMapper;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PackageService {
  private final SolutionPackageMapper packageMapper;
  private final SolutionPackageItemMapper itemMapper;
  private final SkuMapper skuMapper;

  public PackageService(SolutionPackageMapper packageMapper, SolutionPackageItemMapper itemMapper, SkuMapper skuMapper) {
    this.packageMapper = packageMapper;
    this.itemMapper = itemMapper;
    this.skuMapper = skuMapper;
  }

  public List<PackageSummaryDto> listPackages() {
    List<SolutionPackage> packages = packageMapper.selectList(
      new QueryWrapper<SolutionPackage>().orderByDesc("id")
    );

    return packages.stream()
      .map(pkg -> new PackageSummaryDto(
        pkg.getId(),
        pkg.getName(),
        pkg.getScene(),
        pkg.getDefaultPriceCents(),
        pkg.getSummary(),
        pkg.getStatus()
      ))
      .toList();
  }

  public PackageDetailDto getPackageDetail(Long id) {
    SolutionPackage pkg = packageMapper.selectById(id);
    if (pkg == null) {
      return null;
    }

    List<SolutionPackageItem> items = itemMapper.selectList(
      new QueryWrapper<SolutionPackageItem>().eq("package_id", id).orderByAsc("sort_order")
    );

    List<Long> skuIds = items.stream().map(SolutionPackageItem::getSkuId).distinct().toList();
    Map<Long, Sku> skuMap = skuIds.isEmpty()
      ? Map.of()
      : skuMapper.selectBatchIds(skuIds).stream().collect(Collectors.toMap(Sku::getId, sku -> sku));

    List<PackageItemDto> dtoItems = items.stream()
      .map(item -> {
        Sku sku = skuMap.get(item.getSkuId());
        return new PackageItemDto(
          item.getSkuId(),
          sku == null ? "" : sku.getName(),
          item.getQuantity(),
          item.getIsReplaceable() != null && item.getIsReplaceable() == 1,
          sku == null ? 0 : sku.getBasePriceCents()
        );
      })
      .toList();

    return new PackageDetailDto(
      pkg.getId(),
      pkg.getName(),
      pkg.getScene(),
      pkg.getDefaultPriceCents(),
      pkg.getSummary(),
      pkg.getStatus(),
      dtoItems
    );
  }
}
