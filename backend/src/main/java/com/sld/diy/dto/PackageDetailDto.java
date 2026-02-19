package com.sld.diy.dto;

import java.util.List;

public record PackageDetailDto(
  Long id,
  String name,
  String scene,
  Integer defaultPriceCents,
  String summary,
  String status,
  List<PackageItemDto> items
) {}
