package com.sld.diy.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName("sku")
public class Sku {
  @TableId
  private Long id;
  private String code;
  private String name;
  private String category;
  private String model;
  private String tempZone;
  private String voltage;
  private String refrigerant;
  private Integer basePriceCents;
  private String stockStatus;
  private Integer isActive;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
