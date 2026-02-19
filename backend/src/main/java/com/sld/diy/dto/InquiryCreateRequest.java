package com.sld.diy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InquiryCreateRequest(
  @NotBlank String customerName,
  @NotBlank String phone,
  String projectAddress,
  Double area,
  String scene,
  Long packageId,
  @NotBlank String selectedConfigJson,
  @NotNull Integer calcPriceCents
) {}
