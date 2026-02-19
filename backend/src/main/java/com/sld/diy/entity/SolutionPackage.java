package com.sld.diy.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName("solution_package")
public class SolutionPackage {
  @TableId
  private Long id;
  private String name;
  private String scene;
  private String status;
  private Integer version;
  private Integer defaultPriceCents;
  private String summary;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
