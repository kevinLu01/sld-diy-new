CREATE TABLE solution_package (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(128) NOT NULL,
  scene VARCHAR(64) NOT NULL,
  status VARCHAR(16) NOT NULL,
  version INT NOT NULL DEFAULT 1,
  default_price_cents INT NOT NULL DEFAULT 0,
  tags JSON NULL,
  summary VARCHAR(255) NULL,
  created_by BIGINT NULL,
  updated_by BIGINT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  deleted_at DATETIME NULL,
  INDEX idx_solution_package_scene_status (scene, status)
) DEFAULT CHARSET=utf8mb4;

CREATE TABLE sku (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  code VARCHAR(64) NOT NULL UNIQUE,
  name VARCHAR(128) NOT NULL,
  category VARCHAR(64) NOT NULL,
  model VARCHAR(64) NULL,
  specs_json JSON NULL,
  power_kw DECIMAL(10,2) NULL,
  temp_zone VARCHAR(32) NULL,
  voltage VARCHAR(32) NULL,
  refrigerant VARCHAR(32) NULL,
  base_price_cents INT NOT NULL DEFAULT 0,
  stock_status VARCHAR(16) NOT NULL DEFAULT 'in_stock',
  is_active TINYINT NOT NULL DEFAULT 1,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  deleted_at DATETIME NULL,
  INDEX idx_sku_category_active (category, is_active),
  INDEX idx_sku_stock_status (stock_status)
) DEFAULT CHARSET=utf8mb4;

CREATE TABLE solution_package_item (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  package_id BIGINT NOT NULL,
  sku_id BIGINT NOT NULL,
  quantity INT NOT NULL DEFAULT 1,
  is_replaceable TINYINT NOT NULL DEFAULT 0,
  group_key VARCHAR(64) NULL,
  sort_order INT NOT NULL DEFAULT 0,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  deleted_at DATETIME NULL,
  FOREIGN KEY (package_id) REFERENCES solution_package(id),
  FOREIGN KEY (sku_id) REFERENCES sku(id),
  UNIQUE KEY uniq_package_sku (package_id, sku_id),
  INDEX idx_package_sort (package_id, sort_order),
  INDEX idx_package_item_sku (sku_id)
) DEFAULT CHARSET=utf8mb4;

CREATE TABLE inquiry (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  customer_name VARCHAR(64) NOT NULL,
  phone VARCHAR(32) NOT NULL,
  project_address VARCHAR(255) NULL,
  area DECIMAL(10,2) NULL,
  scene VARCHAR(64) NULL,
  package_id BIGINT NULL,
  selected_config_json JSON NOT NULL,
  calc_price_cents INT NOT NULL DEFAULT 0,
  status VARCHAR(16) NOT NULL DEFAULT 'new',
  assigned_to BIGINT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (package_id) REFERENCES solution_package(id),
  INDEX idx_inquiry_status_created (status, created_at),
  INDEX idx_inquiry_package (package_id)
) DEFAULT CHARSET=utf8mb4;
