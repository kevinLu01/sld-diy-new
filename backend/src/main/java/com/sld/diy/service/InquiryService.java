package com.sld.diy.service;

import com.sld.diy.dto.InquiryCreateRequest;
import com.sld.diy.entity.Inquiry;
import com.sld.diy.mapper.InquiryMapper;
import org.springframework.stereotype.Service;

@Service
public class InquiryService {
  private final InquiryMapper inquiryMapper;

  public InquiryService(InquiryMapper inquiryMapper) {
    this.inquiryMapper = inquiryMapper;
  }

  public Inquiry create(InquiryCreateRequest request) {
    Inquiry inquiry = new Inquiry();
    inquiry.setCustomerName(request.customerName());
    inquiry.setPhone(request.phone());
    inquiry.setProjectAddress(request.projectAddress());
    inquiry.setArea(request.area());
    inquiry.setScene(request.scene());
    inquiry.setPackageId(request.packageId());
    inquiry.setSelectedConfigJson(request.selectedConfigJson());
    inquiry.setCalcPriceCents(request.calcPriceCents());
    inquiry.setStatus("new");
    inquiryMapper.insert(inquiry);
    return inquiry;
  }
}
