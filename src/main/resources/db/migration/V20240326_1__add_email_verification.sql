-- 用户表添加邮箱相关字段（如果不存在）
SET @dbname = DATABASE();
SET @tablename = "user";
SET @columnname = "email";
SET @preparedStatement = (SELECT IF(
  (
    SELECT COUNT(*)
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE
      (TABLE_SCHEMA = @dbname)
      AND (TABLE_NAME = @tablename)
      AND (COLUMN_NAME = @columnname)
  ) > 0,
  "SELECT 1",
  "ALTER TABLE user ADD COLUMN email VARCHAR(100) COMMENT '用户邮箱'"
));
PREPARE alterIfNotExists FROM @preparedStatement;
EXECUTE alterIfNotExists;
DEALLOCATE PREPARE alterIfNotExists;

-- 添加email_verified列（如果不存在）
SET @columnname = "email_verified";
SET @preparedStatement = (SELECT IF(
  (
    SELECT COUNT(*)
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE
      (TABLE_SCHEMA = @dbname)
      AND (TABLE_NAME = @tablename)
      AND (COLUMN_NAME = @columnname)
  ) > 0,
  "SELECT 1",
  "ALTER TABLE user ADD COLUMN email_verified BOOLEAN DEFAULT FALSE COMMENT '邮箱是否验证'"
));
PREPARE alterIfNotExists FROM @preparedStatement;
EXECUTE alterIfNotExists;
DEALLOCATE PREPARE alterIfNotExists;

-- 添加邮箱唯一索引（如果不存在）
SET @indexname = "idx_email";
SET @preparedStatement = (SELECT IF(
  (
    SELECT COUNT(*)
    FROM INFORMATION_SCHEMA.STATISTICS
    WHERE
      (TABLE_SCHEMA = @dbname)
      AND (TABLE_NAME = @tablename)
      AND (INDEX_NAME = @indexname)
  ) > 0,
  "SELECT 1",
  "ALTER TABLE user ADD UNIQUE INDEX idx_email (email)"
));
PREPARE alterIfNotExists FROM @preparedStatement;
EXECUTE alterIfNotExists;
DEALLOCATE PREPARE alterIfNotExists;

-- 创建邮箱验证码表（如果不存在）
CREATE TABLE IF NOT EXISTS email_verify_code (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    email VARCHAR(100) NOT NULL COMMENT '邮箱地址',
    code VARCHAR(6) NOT NULL COMMENT '验证码',
    type VARCHAR(20) NOT NULL COMMENT '验证码类型：REGISTER-注册，RESET_PASSWORD-重置密码',
    expired_at TIMESTAMP NOT NULL COMMENT '过期时间',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    used BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否已使用',
    PRIMARY KEY (id),
    INDEX idx_email (email),
    INDEX idx_code (code),
    INDEX idx_expired_at (expired_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='邮箱验证码表'; 