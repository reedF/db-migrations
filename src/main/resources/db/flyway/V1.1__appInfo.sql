create schema crm;

CREATE TABLE test_data (
 value VARCHAR(25) NOT NULL PRIMARY KEY
);
--CREATE TABLE crm.appinfo
--(
--  id bigserial NOT NULL, -- 主键
--  app_name character varying(20), -- app名称
--  app_type smallint NOT NULL, -- app类型:0,android;1,ios
--  latest_version character varying(20), -- 当前最新版本号
--  path character varying(100), -- 下载地址
--  description character varying(50), -- 版本描述
--  force_upgrade_versions character varying(200), -- 强制更新版本列表，逗号","分割
--  ctime timestamp with time zone NOT NULL DEFAULT now(), -- insert time
--  mtime timestamp with time zone NOT NULL DEFAULT now(), -- update time
--  CONSTRAINT appinfo_pk_id PRIMARY KEY (id)
--)

