package com.sld.diy.controller;

import com.sld.diy.dto.InquiryCreateRequest;
import com.sld.diy.dto.InquiryCreateResponse;
import com.sld.diy.entity.Inquiry;
import com.sld.diy.service.InquiryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inquiries")
public class InquiryController {

  private final InquiryService inquiryService;

  public InquiryController(InquiryService inquiryService) {
    this.inquiryService = inquiryService;
  }

  @PostMapping
  public InquiryCreateResponse create(@Valid @RequestBody InquiryCreateRequest request) {
    Inquiry inquiry = inquiryService.create(request);
    return new InquiryCreateResponse(inquiry.getId(), inquiry.getStatus());
  }
}
