package com.sld.diy.service;

import com.sld.diy.dto.PackageDetailDto;
import com.sld.diy.dto.PackageItemDto;
import com.sld.diy.dto.PackageSummaryDto;
import com.sld.diy.dto.admin.AdminInquiryResponse;
import com.sld.diy.dto.admin.AdminPackageResponse;
import com.sld.diy.dto.admin.AdminSkuResponse;
import java.util.List;

public final class MockData {
  private MockData() {}

  public static List<PackageSummaryDto> packages() {
    return List.of(
      new PackageSummaryDto(1L, "标准冷库方案", "冷库", 2648000, "适用 50-80 平方", "published"),
      new PackageSummaryDto(2L, "生鲜门店方案", "门店", 1850000, "适用 20-40 平方", "published")
    );
  }

  public static PackageDetailDto packageDetail(Long id) {
    return new PackageDetailDto(
      id,
      id == 2 ? "生鲜门店方案" : "标准冷库方案",
      id == 2 ? "门店" : "冷库",
      id == 2 ? 1850000 : 2648000,
      "默认包含压缩机/冷风机/阀件/安装",
      "published",
      List.of(
        new PackageItemDto(101L, "压缩机", 1, false, 1200000),
        new PackageItemDto(102L, "冷风机", 2, true, 520000),
        new PackageItemDto(103L, "膨胀阀", 1, true, 80000)
      )
    );
  }

  public static List<AdminPackageResponse> adminPackages() {
    return List.of(
      new AdminPackageResponse(1L, "标准冷库方案", "冷库", "published", 1, 2648000),
      new AdminPackageResponse(2L, "生鲜门店方案", "门店", "draft", 1, 1850000)
    );
  }

  public static List<AdminSkuResponse> adminSkus() {
    return List.of(
      new AdminSkuResponse(101L, "COMP-01", "压缩机", "压缩机", 1200000, "in_stock"),
      new AdminSkuResponse(102L, "FAN-02", "冷风机", "冷风机", 520000, "in_stock")
    );
  }

  public static List<AdminInquiryResponse> adminInquiries() {
    return List.of(
      new AdminInquiryResponse(9001L, "张三", "13800000000", 2648000, "new"),
      new AdminInquiryResponse(9002L, "李四", "13900000000", 1850000, "new")
    );
  }
}
