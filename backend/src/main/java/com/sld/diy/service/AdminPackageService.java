package com.sld.diy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sld.diy.dto.admin.AdminPackageRequest;
import com.sld.diy.entity.SolutionPackage;
import com.sld.diy.mapper.SolutionPackageMapper;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AdminPackageService {
  private final SolutionPackageMapper packageMapper;

  public AdminPackageService(SolutionPackageMapper packageMapper) {
    this.packageMapper = packageMapper;
  }

  public List<SolutionPackage> list() {
    return packageMapper.selectList(new QueryWrapper<SolutionPackage>().orderByDesc("id"));
  }

  public SolutionPackage create(AdminPackageRequest request) {
    SolutionPackage pkg = new SolutionPackage();
    pkg.setName(request.name());
    pkg.setScene(request.scene());
    pkg.setStatus(request.status());
    pkg.setVersion(1);
    pkg.setDefaultPriceCents(request.defaultPriceCents());
    pkg.setSummary(request.summary());
    packageMapper.insert(pkg);
    return pkg;
  }

  public SolutionPackage update(Long id, AdminPackageRequest request) {
    SolutionPackage pkg = new SolutionPackage();
    pkg.setId(id);
    pkg.setName(request.name());
    pkg.setScene(request.scene());
    pkg.setStatus(request.status());
    pkg.setDefaultPriceCents(request.defaultPriceCents());
    pkg.setSummary(request.summary());
    packageMapper.updateById(pkg);
    return packageMapper.selectById(id);
  }

  public SolutionPackage publish(Long id) {
    SolutionPackage pkg = new SolutionPackage();
    pkg.setId(id);
    pkg.setStatus("published");
    packageMapper.updateById(pkg);
    return packageMapper.selectById(id);
  }

  public SolutionPackage unpublish(Long id) {
    SolutionPackage pkg = new SolutionPackage();
    pkg.setId(id);
    pkg.setStatus("draft");
    packageMapper.updateById(pkg);
    return packageMapper.selectById(id);
  }
}
