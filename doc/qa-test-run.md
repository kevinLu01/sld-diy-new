# 接口联调执行记录

日期：2026-02-19

## 前提
- 后端容器已启动：`docker compose up -d --build backend`
- API Base（容器内）：`http://localhost:8081`

> 说明：当前环境中主机侧 `curl http://localhost:8081` 连接失败，因此本次使用 `docker exec` 在容器内执行接口验证。

## 执行结果（容器内）

1. `GET /api/packages`
- 结果：通过
- 响应（解码后）：
```json
[
  {
    "id": 2,
    "name": "生鲜门店方案",
    "scene": "门店",
    "defaultPriceCents": 1850000,
    "summary": "适用 20-40 平方",
    "status": "published"
  },
  {
    "id": 1,
    "name": "标准冷库方案",
    "scene": "冷库",
    "defaultPriceCents": 2648000,
    "summary": "适用 50-80 平方",
    "status": "published"
  }
]
```

2. `GET /api/packages/1`
- 结果：通过
- 响应（解码后）：
```json
{
  "id": 1,
  "name": "标准冷库方案",
  "scene": "冷库",
  "defaultPriceCents": 2648000,
  "summary": "适用 50-80 平方",
  "status": "published",
  "items": [
    {"skuId":101,"skuName":"压缩机","quantity":1,"isReplaceable":false,"basePriceCents":1200000},
    {"skuId":102,"skuName":"冷风机","quantity":2,"isReplaceable":true,"basePriceCents":520000},
    {"skuId":103,"skuName":"膨胀阀","quantity":1,"isReplaceable":true,"basePriceCents":80000}
  ]
}
```

3. `POST /api/pricing/calc`
- 结果：通过
- 响应：
```json
{
  "totalPriceCents": 2352000,
  "breakdown": [
    {"skuId":101,"quantity":1,"unitPriceCents":1200000,"lineTotalCents":1260000},
    {"skuId":102,"quantity":2,"unitPriceCents":520000,"lineTotalCents":1092000}
  ]
}
```

4. `POST /api/rules/validate`
- 结果：通过
- 响应：
```json
{
  "isCompatible": false,
  "violations": ["面积偏小，建议确认是否需要小型方案包","部分组件数量超过推荐上限"],
  "replacements": ["推荐小型冷库方案包","建议联系技术工程师确认选型"]
}
```

5. `POST /api/inquiries`
- 结果：通过
- 响应：
```json
{"inquiryId":3,"status":"new"}
```

## 执行命令（容器内）
```
docker exec -i sld-diy-backend sh -c "curl -s http://localhost:8081/api/packages"

docker exec -i sld-diy-backend sh -c "curl -s http://localhost:8081/api/packages/1"

docker exec -i sld-diy-backend sh -c "curl -s -X POST http://localhost:8081/api/pricing/calc -H 'Content-Type: application/json' -d '{\"packageId\":1,\"items\":[{\"skuId\":101,\"quantity\":1},{\"skuId\":102,\"quantity\":2}],\"regionCode\":\"east\"}'"

docker exec -i sld-diy-backend sh -c "curl -s -X POST http://localhost:8081/api/rules/validate -H 'Content-Type: application/json' -d '{\"items\":[{\"skuId\":101,\"quantity\":1},{\"skuId\":102,\"quantity\":4}],\"scene\":\"冷库\",\"area\":18}'"

docker exec -i sld-diy-backend sh -c "curl -s -X POST http://localhost:8081/api/inquiries -H 'Content-Type: application/json' -d '{\"customerName\":\"张三\",\"phone\":\"13800000000\",\"projectAddress\":\"上海\",\"area\":60,\"scene\":\"冷库\",\"packageId\":1,\"selectedConfigJson\":\"[{\\\"skuId\\\":101,\\\"quantity\\\":1}]\",\"calcPriceCents\":2648000}'"
```
