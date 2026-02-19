package com.sld.diy.dto;

public record PricingBreakdownItem(
  Long skuId,
  Integer quantity,
  Integer unitPriceCents,
  Integer lineTotalCents
) {}
