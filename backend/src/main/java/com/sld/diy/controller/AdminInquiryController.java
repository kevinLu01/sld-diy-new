package com.sld.diy.controller;

import com.sld.diy.dto.admin.AdminInquiryResponse;
import com.sld.diy.entity.Inquiry;
import com.sld.diy.service.AdminInquiryService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/inquiries")
public class AdminInquiryController {

  private final AdminInquiryService inquiryService;

  public AdminInquiryController(AdminInquiryService inquiryService) {
    this.inquiryService = inquiryService;
  }

  @GetMapping
  public List<AdminInquiryResponse> list() {
    return inquiryService.list().stream()
      .map(inquiry -> new AdminInquiryResponse(
        inquiry.getId(),
        inquiry.getCustomerName(),
        inquiry.getPhone(),
        inquiry.getCalcPriceCents(),
        inquiry.getStatus()
      ))
      .toList();
  }

  @GetMapping("/{id}")
  public AdminInquiryResponse detail(@PathVariable("id") Long id) {
    Inquiry inquiry = inquiryService.detail(id);
    if (inquiry == null) {
      return null;
    }
    return new AdminInquiryResponse(
      inquiry.getId(),
      inquiry.getCustomerName(),
      inquiry.getPhone(),
      inquiry.getCalcPriceCents(),
      inquiry.getStatus()
    );
  }
}
