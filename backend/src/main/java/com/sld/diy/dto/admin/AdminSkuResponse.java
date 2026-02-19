package com.sld.diy.dto.admin;

public record AdminSkuResponse(
  Long id,
  String code,
  String name,
  String category,
  Integer basePriceCents,
  String stockStatus
) {}
