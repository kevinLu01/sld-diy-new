package com.sld.diy.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName("inquiry")
public class Inquiry {
  @TableId
  private Long id;
  private String customerName;
  private String phone;
  private String projectAddress;
  private Double area;
  private String scene;
  private Long packageId;
  private String selectedConfigJson;
  private Integer calcPriceCents;
  private String status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
