package com.sld.diy.dto;

import java.util.List;

public record PricingCalcRequest(
  Long packageId,
  List<PricingItemInput> items,
  String regionCode
) {}
