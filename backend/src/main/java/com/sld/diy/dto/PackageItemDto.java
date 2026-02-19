package com.sld.diy.dto;

public record PackageItemDto(
  Long skuId,
  String skuName,
  Integer quantity,
  Boolean isReplaceable,
  Integer basePriceCents
) {}
