# Week 1–2 FE Detailed Design Doc v1

目标：在现有 `index.html`、`admin.html` 原型基础上，完成可演示的前台 H5 与后台管理台 UI 细化与组件化规划，确保与 `doc/plan.md` 的 MVP 流程一致，便于后续 Vue3 实现。

## 1. 范围与交付

- 覆盖页面：前台 `index.html`（H5）、后台 `admin.html`（管理台）。
- 输出内容：UI 架构、组件地图、页面区块结构、交互与状态、样式规范建议。
- 不包含：后端 API、真实数据接入、登录鉴权、权限控制实现。

## 2. UI 架构（信息层级）

### 2.1 前台 H5（DIY 商城）

- 顶部品牌与价值主张
- 推荐方案包
- 场景筛选与 SKU 卡片列表
- DIY 清单与兼容提示
- 询价提交入口
- 服务保障与底部导航

### 2.2 后台管理台

- 侧边导航（模块入口）
- 顶部操作条（筛选、操作、状态）
- 数据概览卡片（核心 KPI）
- 方案包配置表（核心管理）
- 运营建议区块（功能指引）

## 3. 组件地图（Component Map）

### 3.1 前台 H5 组件

基础组件
- `app-shell`：页面容器、背景、整体间距。
- `section-head`：标题 + 辅助说明。
- `badge`：运营标识、状态标签。
- `tag-pill`：小标签（推荐、热销）。
- `price`：价格展示样式。
- `primary-btn` / `secondary-btn`：主要/次要按钮。

业务组件
- `hero-card`：价值主张 + 快捷入口。
- `plan-card`：方案包卡片（标题、适用面积、组件摘要、价格、CTA）。
- `plan-list`：方案包列表容器。
- `scene-tabs`：场景切换 Tab。
- `sku-card`：SKU 组件卡片（规格、标签、价格、加入按钮）。
- `builder-list`：DIY 配置清单列表。
- `builder-row`：单行配置项 + 兼容状态。
- `compat-status`：兼容状态胶囊（兼容/建议/冲突）。
- `total-bar`：总价 + 询价 CTA。
- `service-card`：服务保障说明区块。
- `bottom-nav`：底部导航。

交互组件
- `plan-selector`：一键套用 + 选中态。
- `tab-switcher`：场景切换。
- `price-updater`：价格展示更新。

### 3.2 后台管理台组件

基础组件
- `layout`：侧边栏 + 内容区布局。
- `sidebar-menu`：模块导航。
- `topbar`：标题 + 操作按钮。
- `metric-card`：KPI 统计卡。
- `data-table`：方案包列表表格。
- `status-tag`：已发布/待审批状态。
- `info-list`：功能建议/运营提示。

业务组件
- `package-table`：方案包配置表。
- `capability-list`：后台能力建议模块。

## 4. 页面区块结构

### 4.1 前台 `index.html`

1) Header/Brand
- 目的：品牌识别 + 价值主张入口。
- 内容：品牌标题、Badge。
- 交互：无。

2) Hero Card
- 目的：解释“DIY 商城”价值与入口。
- 内容：标题、说明、快捷入口卡（整机/辅材/送装）。
- 交互：后续可接 `scene-tabs`。

3) 方案包推荐（Plan List）
- 对应 `plan.md`：MVP 第 1 步（推荐方案包）。
- 内容：3 个推荐方案包卡片。
- 交互：一键套用，激活态 + 价格更新。

4) 场景筛选 + SKU 列表
- 对应 `plan.md`：MVP 第 3 步（DIY 微调）。
- 内容：场景 Tab + SKU 卡片列表。
- 交互：Tab 切换激活态，SKU 加入按钮。

5) DIY 配置清单
- 对应 `plan.md`：兼容校验与 DIY 清单。
- 内容：组件行 + 兼容状态 + 默认推荐。
- 交互：后续接规则校验结果。

6) 总价 + 询价 CTA
- 对应 `plan.md`：MVP 第 4 步（提交询价）。
- 内容：总价显示 + 提交询价按钮。

7) 服务保障
- 目的：增强信任。
- 内容：质保、安装、技术支持。

8) Bottom Nav
- 目的：移动端模块入口占位。
- 内容：首页/方案包/订单/我的。

### 4.2 后台 `admin.html`

1) Sidebar
- 对应 `plan.md`：后台模块入口。
- 内容：方案包管理、SKU 组件库、规则引擎、价格策略、询价订单池、内容配置、权限与审计。

2) Topbar
- 目的：管理入口 + 主 CTA。
- 内容：标题、说明、`新建方案包` 按钮。

3) Metric Cards
- 对应 `plan.md`：核心指标展示。
- 内容：上线方案包、询价转化、毛利预警、兼容异常。

4) 方案包配置表
- 对应 `plan.md`：方案包管理。
- 内容：方案名称、适用场景、版本、状态。

5) 后台能力建议
- 对应 `plan.md`：后台模块说明。
- 内容：7 个功能模块说明。

## 5. 交互与状态设计

### 5.1 前台状态

- `activePlanId`：当前套用方案。
- `selectedScene`：当前场景。
- `builderItems`：DIY 配置清单。
- `compatStatus`：兼容校验状态（兼容/建议/冲突）。
- `totalPrice`：总价展示。

### 5.2 后台状态

- `activeMenu`：当前模块。
- `metricData`：KPI 汇总。
- `packageList`：方案包列表。
- `status`：发布/待审批状态。

## 6. 视觉与样式规范建议

- 使用 `:root` 统一色彩变量，保持和现有原型一致。
- 统一圆角（12–16px）与阴影（轻量卡片层级）。
- 字体：中文使用 "PingFang SC" + "Microsoft YaHei"。
- 按钮风格：主按钮实底，次按钮描边。
- 标签统一使用胶囊形态。
- 页面间距：模块间 12–16px，卡片内边距 12–14px。

## 7. Week 1–2 实施建议（前端视角）

Week 1
- 统一整理 `index.html`/`admin.html` 的 UI 组件拆分与命名。
- 对 `index.html` 的方案包、DIY 清单、询价 CTA 做视觉细化。
- 对 `admin.html` 的 KPI 卡片、表格样式做一致性增强。

Week 2
- 为 H5 增加基础交互（选中态、Tab 切换、价格更新）。
- 补充移动端适配细节（安全区、底部导航固定）。
- 输出 `H5` 与 `Admin` 的组件命名规范，准备 Vue 化迁移。

## 8. 对齐 `plan.md` 的对应关系

- 前台流程：推荐方案包 -> DIY 微调 -> 兼容校验 -> 提交询价。
- 后台能力：方案包管理、SKU 组件库、规则引擎、价格策略、询价/订单池、内容配置、权限审计。

