package com.sld.diy.controller;

import com.sld.diy.dto.admin.AdminSkuRequest;
import com.sld.diy.dto.admin.AdminSkuResponse;
import com.sld.diy.entity.Sku;
import com.sld.diy.service.AdminSkuService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/skus")
public class AdminSkuController {

  private final AdminSkuService skuService;

  public AdminSkuController(AdminSkuService skuService) {
    this.skuService = skuService;
  }

  @GetMapping
  public List<AdminSkuResponse> list() {
    return skuService.list().stream()
      .map(sku -> new AdminSkuResponse(
        sku.getId(),
        sku.getCode(),
        sku.getName(),
        sku.getCategory(),
        sku.getBasePriceCents(),
        sku.getStockStatus()
      ))
      .toList();
  }

  @PostMapping
  public AdminSkuResponse create(@Valid @RequestBody AdminSkuRequest request) {
    Sku sku = skuService.create(request);
    return new AdminSkuResponse(sku.getId(), sku.getCode(), sku.getName(), sku.getCategory(), sku.getBasePriceCents(), sku.getStockStatus());
  }

  @PutMapping("/{id}")
  public AdminSkuResponse update(@PathVariable("id") Long id, @Valid @RequestBody AdminSkuRequest request) {
    Sku sku = skuService.update(id, request);
    return new AdminSkuResponse(sku.getId(), sku.getCode(), sku.getName(), sku.getCategory(), sku.getBasePriceCents(), sku.getStockStatus());
  }
}
