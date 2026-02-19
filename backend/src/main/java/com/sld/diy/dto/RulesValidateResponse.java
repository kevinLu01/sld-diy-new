package com.sld.diy.dto;

import java.util.List;

public record RulesValidateResponse(
  boolean isCompatible,
  List<String> violations,
  List<String> replacements
) {}
