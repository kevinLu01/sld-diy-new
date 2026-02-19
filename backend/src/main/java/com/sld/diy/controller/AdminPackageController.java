package com.sld.diy.controller;

import com.sld.diy.dto.admin.AdminPackageRequest;
import com.sld.diy.dto.admin.AdminPackageResponse;
import com.sld.diy.entity.SolutionPackage;
import com.sld.diy.service.AdminPackageService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/packages")
public class AdminPackageController {

  private final AdminPackageService packageService;

  public AdminPackageController(AdminPackageService packageService) {
    this.packageService = packageService;
  }

  @GetMapping
  public List<AdminPackageResponse> list() {
    return packageService.list().stream()
      .map(pkg -> new AdminPackageResponse(
        pkg.getId(),
        pkg.getName(),
        pkg.getScene(),
        pkg.getStatus(),
        pkg.getVersion(),
        pkg.getDefaultPriceCents()
      ))
      .toList();
  }

  @PostMapping
  public AdminPackageResponse create(@Valid @RequestBody AdminPackageRequest request) {
    SolutionPackage pkg = packageService.create(request);
    return new AdminPackageResponse(pkg.getId(), pkg.getName(), pkg.getScene(), pkg.getStatus(), pkg.getVersion(), pkg.getDefaultPriceCents());
  }

  @PutMapping("/{id}")
  public AdminPackageResponse update(@PathVariable("id") Long id, @Valid @RequestBody AdminPackageRequest request) {
    SolutionPackage pkg = packageService.update(id, request);
    return new AdminPackageResponse(pkg.getId(), pkg.getName(), pkg.getScene(), pkg.getStatus(), pkg.getVersion(), pkg.getDefaultPriceCents());
  }

  @PostMapping("/{id}/publish")
  public AdminPackageResponse publish(@PathVariable("id") Long id) {
    SolutionPackage pkg = packageService.publish(id);
    return new AdminPackageResponse(pkg.getId(), pkg.getName(), pkg.getScene(), pkg.getStatus(), pkg.getVersion(), pkg.getDefaultPriceCents());
  }

  @PostMapping("/{id}/unpublish")
  public AdminPackageResponse unpublish(@PathVariable("id") Long id) {
    SolutionPackage pkg = packageService.unpublish(id);
    return new AdminPackageResponse(pkg.getId(), pkg.getName(), pkg.getScene(), pkg.getStatus(), pkg.getVersion(), pkg.getDefaultPriceCents());
  }
}
