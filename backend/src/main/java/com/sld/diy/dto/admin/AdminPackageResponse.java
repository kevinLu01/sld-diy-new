package com.sld.diy.dto.admin;

public record AdminPackageResponse(
  Long id,
  String name,
  String scene,
  String status,
  Integer version,
  Integer defaultPriceCents
) {}
