package com.sld.diy.dto;

import java.util.List;

public record RulesValidateRequest(
  List<PricingItemInput> items,
  String scene,
  Double area
) {}
