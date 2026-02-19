package com.sld.diy.dto;

import java.util.List;

public record PricingCalcResponse(
  Integer totalPriceCents,
  List<PricingBreakdownItem> breakdown
) {}
