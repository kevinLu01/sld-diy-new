# Week 7 容器化与 CI/CD 计划（v1）

## 目标
- Docker Compose 本地可运行。
- GitHub Actions 自动化构建、测试与集成验证。

## Docker Compose 规划

服务建议：
- `db`（MySQL 8）
- `redis`
- `api`（Spring Boot 或 NestJS）
- `nginx`（反向代理）
- 可选 `minio`（附件存储）

要求：
- `db` 具备健康检查与持久化卷。
- `api` 依赖 `db` 与 `redis`。
- `.env` 统一管理数据库与密钥配置。
- 暴露：`api` -> `localhost:8080`，`nginx` -> `localhost:80`。

## 脚本规范（前置约定）

前端（Vue/Vite 方向）：
- `frontend:install`
- `frontend:lint`
- `frontend:test`
- `frontend:build`

后端（Spring Boot / NestJS）：
- `backend:install`
- `backend:lint`
- `backend:test`
- `backend:build`

## GitHub Actions 规划

`ci.yml` 结构：
- `frontend` job：install → lint → test → build
- `backend` job：install → lint → test → build
- `integration` job：启动 `db` + `redis` 容器 → 运行 BE 集成测试

缓存策略：
- FE：`node_modules` / `pnpm-store`
- BE：Maven/Gradle 缓存或 `node_modules`

环境变量：
- `DB_HOST`, `DB_PORT`, `DB_USER`, `DB_PASSWORD`
- 测试环境使用独立账号和数据库

## CI 对齐示例

Vue/Vite：
- `pnpm install`
- `pnpm lint`
- `pnpm test`
- `pnpm build`

Spring Boot：
- `./gradlew test`
- `./gradlew bootJar`

NestJS：
- `npm ci`
- `npm run lint`
- `npm test`
- `npm run build`
