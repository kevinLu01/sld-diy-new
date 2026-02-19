# 项目进度与续航指令

更新日期：2026-02-20

## 当前阶段
- 周期：W3-W4（核心功能 MVP）
- 目标：前台一键套用/DIY微调/询价提交 + 后台方案包管理 + 核心 API/DDL + 联调

## 已完成
- 单仓多包骨架：`frontend/`（Vue3+Vite+TS）、`backend/`（Spring Boot 3 + MyBatis Plus）
- Docker：MySQL/Redis 容器已重建，DDL/seed 已重新初始化
- 后端：已接入真实数据库（Mapper/Service/Controller），端口改为 8081
- 前端：已迁移首页结构并接入 `/api` 全链路（packages/pricing/rules/inquiries）
- 规则校验：`/api/rules/validate` 已补充场景/面积逻辑占位
- 接口联调：容器内测试通过（见 `doc/qa-test-run.md`）
- UI 回归：页面可操作（见 `doc/ui-test-report.md`）

## 进行中
- UI 联调深度回归（价格联动、规则提示、询价提交）

## 待办（优先级顺序）
1. 验证价格联动与规则提示 UI 交互
2. QA 输出联调测试结论与问题单
3. 规则引擎可配置入口（M2 预留）

## 端口与部署
- 前端：`http://localhost:5173`（Nginx 静态托管）
- 后端：`http://localhost:8081`（API）

## 多 Agent 任务快照
- Leader：维护进度/风险台账，更新 `doc/progress.md`
- FE：首页 Vue 组件化 + API 联调 + 规则提示 UI
- BE：DB 接入 + 规则/价格占位 + 端口调整
- QA：接口联调记录已完成；UI 回归已更新
- Architect：Nginx 配置建议已给出（SPA + /api proxy）

## 关键运行命令
- 前端/后端/DB：`docker compose up -d --build --pull=never`

## 一句续航指令（下次打开用）
> 请继续按 `doc/progress.md` 执行，优先完成“待办”第 1-2 项，并产出阶段性汇报。

## 一键续航命令（重新建团队并继续）
> 继续使用多-agent模板，按 `doc/progress.md` 立刻推进待办，并先执行 UI 交互回归与 QA 结论输出。
