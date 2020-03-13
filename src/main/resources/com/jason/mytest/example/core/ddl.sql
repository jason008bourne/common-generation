${gg.setOverride(true)}[#t /]
${gg.setOutputFile(ddlPath+"/${table.sqlName}.sql")}[#t  /]
DROP TABLE IF EXISTS ${table.sqlName};
CREATE TABLE ${table.sqlName}
(
  [#if pkJavaType=='Long']
    id BIGINT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '自增id',
  [/#if]
  [#if pkJavaType=='Integer']
    id INT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '自增id',
  [/#if]
[#list table.columns as field]
  [#if field.asType=='String']
    ${field.sqlName} VARCHAR(64) COMMENT '${(field.remarks)!}',
  [/#if]
  [#if field.asType=='Long']
    ${field.sqlName} BIGINT UNSIGNED COMMENT '${(field.remarks)!}',
  [/#if]
  [#if field.asType=='Integer']
    ${field.sqlName} INT UNSIGNED COMMENT '${(field.remarks)!}',
  [/#if]
  [#if field.asType=='Boolean']
    ${field.sqlName} TINYINT(1) UNSIGNED COMMENT '${(field.remarks)!}',
  [/#if]
  [#if field.asType=='Enum']
    ${field.sqlName} TINYINT(4) UNSIGNED COMMENT '${(field.remarks)!}',
  [/#if]
  [#if field.asType=='Date']
    ${field.sqlName} DATETIME COMMENT '${(field.remarks)!}',
  [/#if]
  [#if field.asType=='BigDecimal']
    ${field.sqlName} DECIMAL(10,2) COMMENT '${(field.remarks)!}',
   [/#if]
[/#list]
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更改时间'
    -- UNIQUE INDEX `uk_field1_field2` (`field1`, `field2`),
    -- INDEX `idx_field1` (`field1`),
)COMMENT='${(table.remarks)!}' ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci AUTO_INCREMENT=1;

