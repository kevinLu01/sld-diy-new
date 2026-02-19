package com.sld.diy.dto;

public record PackageSummaryDto(
  Long id,
  String name,
  String scene,
  Integer defaultPriceCents,
  String summary,
  String status
) {}
