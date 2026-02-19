package com.sld.diy.controller;

import com.sld.diy.dto.PricingCalcRequest;
import com.sld.diy.dto.PricingCalcResponse;
import com.sld.diy.service.PricingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pricing")
public class PricingController {

  private final PricingService pricingService;

  public PricingController(PricingService pricingService) {
    this.pricingService = pricingService;
  }

  @PostMapping("/calc")
  public PricingCalcResponse calc(@RequestBody PricingCalcRequest request) {
    return pricingService.calc(request);
  }
}
