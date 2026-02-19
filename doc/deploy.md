# 部署说明（本地/容器）

## 方式一：本地开发运行
1. 启动数据库：`docker compose up -d db redis`
2. 启动后端：`mvn -f backend/pom.xml spring-boot:run`
3. 启动前端：`npm --workspace frontend run dev`

## 方式二：Docker Compose 一键启动
1. 构建并启动：`docker compose up -d --build`
2. 前端：`http://localhost:5173`
3. 后端：`http://localhost:8080`

## 环境变量
- 后端：
  - `SPRING_DATASOURCE_URL`
  - `SPRING_DATASOURCE_USERNAME`
  - `SPRING_DATASOURCE_PASSWORD`
- 前端：
  - `VITE_API_BASE`

## 健康检查
- MySQL：`docker compose ps` 显示 `healthy`
