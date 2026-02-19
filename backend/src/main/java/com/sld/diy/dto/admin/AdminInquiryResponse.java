package com.sld.diy.dto.admin;

public record AdminInquiryResponse(
  Long id,
  String customerName,
  String phone,
  Integer calcPriceCents,
  String status
) {}
