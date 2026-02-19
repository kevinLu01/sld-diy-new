package com.sld.diy.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdminSkuRequest(
  @NotBlank String code,
  @NotBlank String name,
  @NotBlank String category,
  String model,
  @NotNull Integer basePriceCents,
  String stockStatus
) {}
