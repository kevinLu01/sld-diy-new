package com.sld.diy.controller;

import com.sld.diy.dto.RulesValidateRequest;
import com.sld.diy.dto.RulesValidateResponse;
import com.sld.diy.service.RulesService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rules")
public class RulesController {

  private final RulesService rulesService;

  public RulesController(RulesService rulesService) {
    this.rulesService = rulesService;
  }

  @PostMapping("/validate")
  public RulesValidateResponse validate(@RequestBody RulesValidateRequest request) {
    return rulesService.validate(request);
  }
}
