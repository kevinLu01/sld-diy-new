package com.sld.diy.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdminPackageRequest(
  @NotBlank String name,
  @NotBlank String scene,
  @NotNull Integer defaultPriceCents,
  String summary,
  @NotBlank String status
) {}
