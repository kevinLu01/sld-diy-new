# 架构与数据库首轮审查清单（v1）

## 1. 范围与非目标
- 确认 M1 范围与 `doc/plan.md` 一致。
- 明确本期非目标：ERP/CRM 集成、完整 RBAC、复杂审批流。

## 2. 业务流与系统边界
- 业务主流程：方案包推荐 → 一键套用 → DIY 微调 → 询价提交 → 销售跟进。
- 角色：用户端、运营、技术、销售、财务。

## 3. 领域模型覆盖
- 前台/后台每个 UI 实体需有对应后端实体。
- 确认数据生命周期：草稿 → 发布 → 询价快照。

## 4. 版本与发布策略
- 方案包/规则/价格策略的版本化策略明确。
- 发布/下架与“当前版本”选择逻辑明确。

## 5. 规则与定价
- 规则类型（兼容/冲突/推荐）及优先级明确。
- 定价策略作用域（地区/场景/客户类型）明确。
- 冲突处理策略明确。

## 6. 询价与报价
- 询价快照语义明确（配置、价格、版本号）。
- 报价版本与有效期有定义。

## 7. 审计与权限
- 核心变更需审计：发布、价格、规则。
- 关键操作权限边界明确。

## 8. 数据质量与约束
- 唯一约束：SKU code/型号、方案包版本号。
- 软删除策略一致。
- JSON 字段仅用于规则/策略配置。

## 9. 指标与监控
- 关键指标：询价转化、低毛利预警等。
- 指标来源字段明确。

## 10. 风险与控制
- 规则复杂度膨胀 → 先限制规则类型。
- 数据一致性不足 → 发布前校验。

---

## DB 首轮审查（Condensed）
1. 必要表齐全：`solution_package`, `solution_package_item`, `sku`, `inquiry`, `inquiry_item`, `inquiry_quote`, `audit_log`。
2. 统一 `created_at`, `updated_at`, `status` 字段。
3. 版本表需有 `effective_at` 与 `is_published`。
4. 询价快照不可变。
5. 索引覆盖高频查询：场景筛选、询价状态、SKU 型号。
6. 无循环外键依赖。
7. JSON 字段最小化。
8. 软删除逻辑一致。

---

## 审查反馈模板

**Summary**
- Ready / Needs Changes / Not Ready
- Top risks (1-3)

**Strengths**
- ...

**Blocking Issues**
1. Issue:
- Impact:
- Recommendation:

**Non-Blocking Issues**
1. Issue:
- Impact:
- Recommendation:

**DB Notes**
- Missing tables:
- Index gaps:
- Versioning gaps:
- Audit gaps:

**API Notes**
- Missing endpoints:
- Payload mismatch risks:

**Next Actions**
1. ...
2. ...
