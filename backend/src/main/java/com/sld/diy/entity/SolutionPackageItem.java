package com.sld.diy.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName("solution_package_item")
public class SolutionPackageItem {
  @TableId
  private Long id;
  private Long packageId;
  private Long skuId;
  private Integer quantity;
  private Integer isReplaceable;
  private String groupKey;
  private Integer sortOrder;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
