package com.sld.diy.controller;

import com.sld.diy.dto.PackageDetailDto;
import com.sld.diy.dto.PackageSummaryDto;
import com.sld.diy.service.PackageService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/packages")
public class PackageController {

  private final PackageService packageService;

  public PackageController(PackageService packageService) {
    this.packageService = packageService;
  }

  @GetMapping
  public List<PackageSummaryDto> list() {
    return packageService.listPackages();
  }

  @GetMapping("/{id}")
  public PackageDetailDto detail(@PathVariable("id") Long id) {
    return packageService.getPackageDetail(id);
  }
}
