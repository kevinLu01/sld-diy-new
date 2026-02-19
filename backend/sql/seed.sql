SET NAMES utf8mb4;

INSERT INTO solution_package (id, name, scene, status, version, default_price_cents, summary)
VALUES
  (1, '标准冷库方案', '冷库', 'published', 1, 2648000, '适用 50-80 平方'),
  (2, '生鲜门店方案', '门店', 'published', 1, 1850000, '适用 20-40 平方');

INSERT INTO sku (id, code, name, category, model, base_price_cents, stock_status, is_active)
VALUES
  (101, 'COMP-01', '压缩机', '压缩机', 'SLD-C01', 1200000, 'in_stock', 1),
  (102, 'FAN-02', '冷风机', '冷风机', 'SLD-F02', 520000, 'in_stock', 1),
  (103, 'VALVE-01', '膨胀阀', '阀件', 'SLD-V01', 80000, 'in_stock', 1);

INSERT INTO solution_package_item (id, package_id, sku_id, quantity, is_replaceable, group_key, sort_order)
VALUES
  (1001, 1, 101, 1, 0, 'core', 1),
  (1002, 1, 102, 2, 1, 'core', 2),
  (1003, 1, 103, 1, 1, 'aux', 3),
  (2001, 2, 101, 1, 0, 'core', 1),
  (2002, 2, 102, 1, 1, 'core', 2),
  (2003, 2, 103, 1, 1, 'aux', 3);
