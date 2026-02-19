package com.sld.diy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sld.diy.entity.Inquiry;
import com.sld.diy.mapper.InquiryMapper;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AdminInquiryService {
  private final InquiryMapper inquiryMapper;

  public AdminInquiryService(InquiryMapper inquiryMapper) {
    this.inquiryMapper = inquiryMapper;
  }

  public List<Inquiry> list() {
    return inquiryMapper.selectList(new QueryWrapper<Inquiry>().orderByDesc("id"));
  }

  public Inquiry detail(Long id) {
    return inquiryMapper.selectById(id);
  }
}
