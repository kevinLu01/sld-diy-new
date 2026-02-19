package com.sld.diy.service;

import com.sld.diy.dto.RulesValidateRequest;
import com.sld.diy.dto.RulesValidateResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RulesService {

  public RulesValidateResponse validate(RulesValidateRequest request) {
    List<String> violations = new ArrayList<>();
    List<String> replacements = new ArrayList<>();

    if (request.items() == null || request.items().isEmpty()) {
      violations.add("配置为空，无法校验兼容性");
    }

    if (request.area() != null && request.area() < 20) {
      violations.add("面积偏小，建议确认是否需要小型方案包");
      replacements.add("推荐小型冷库方案包");
    }

    if (request.scene() == null || request.scene().isBlank()) {
      replacements.add("请先选择场景以获得更准确的推荐");
    }

    boolean hasHighQty = request.items() != null
      && request.items().stream().anyMatch(item -> item.quantity() != null && item.quantity() > 3);
    if (hasHighQty) {
      violations.add("部分组件数量超过推荐上限");
      replacements.add("建议联系技术工程师确认选型");
    }

    boolean isCompatible = violations.isEmpty();
    return new RulesValidateResponse(isCompatible, violations, replacements);
  }
}
