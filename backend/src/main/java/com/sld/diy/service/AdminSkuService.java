package com.sld.diy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sld.diy.dto.admin.AdminSkuRequest;
import com.sld.diy.entity.Sku;
import com.sld.diy.mapper.SkuMapper;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AdminSkuService {
  private final SkuMapper skuMapper;

  public AdminSkuService(SkuMapper skuMapper) {
    this.skuMapper = skuMapper;
  }

  public List<Sku> list() {
    return skuMapper.selectList(new QueryWrapper<Sku>().orderByDesc("id"));
  }

  public Sku create(AdminSkuRequest request) {
    Sku sku = new Sku();
    sku.setCode(request.code());
    sku.setName(request.name());
    sku.setCategory(request.category());
    sku.setModel(request.model());
    sku.setBasePriceCents(request.basePriceCents());
    sku.setStockStatus(request.stockStatus() == null ? "in_stock" : request.stockStatus());
    sku.setIsActive(1);
    skuMapper.insert(sku);
    return sku;
  }

  public Sku update(Long id, AdminSkuRequest request) {
    Sku sku = new Sku();
    sku.setId(id);
    sku.setCode(request.code());
    sku.setName(request.name());
    sku.setCategory(request.category());
    sku.setModel(request.model());
    sku.setBasePriceCents(request.basePriceCents());
    sku.setStockStatus(request.stockStatus() == null ? "in_stock" : request.stockStatus());
    skuMapper.updateById(sku);
    return skuMapper.selectById(id);
  }
}
