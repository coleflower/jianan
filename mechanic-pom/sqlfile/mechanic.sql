-- 20180710

-- 序列表
DROP TABLE IF EXISTS `t_sequence`;
CREATE TABLE `t_sequence` (
	`id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`seqname` varchar(20) NOT NULL COMMENT '序列名称',
	`seqhead` varchar(20) NOT NULL COMMENT '序列头（编号唯一识别）',
	`seqvalue` int NOT NULL DEFAULT 0 COMMENT '当前序列值',
	`setp` int NOT NULL DEFAULT 1 COMMENT '步长',
	PRIMARY KEY (`id`),
	UNIQUE KEY `seqname` (`seqname`)
)
COMMENT='序列表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 获取当前序列号
DROP FUNCTION IF EXISTS currseqval;
DELIMITER $
CREATE FUNCTION currseqval (seq_name VARCHAR(50))
RETURNS INTEGER
CONTAINS SQL
BEGIN
    DECLARE value INTEGER;
    SET value = 0;
    SELECT seqvalue INTO value
    FROM t_sequence
    WHERE seqname = seq_name;
    RETURN value;
END$
DELIMITER ;
 
-- 获取下一个序列号
DROP FUNCTION IF EXISTS nextseqval;
DELIMITER $
CREATE FUNCTION nextseqval (seq_name VARCHAR(50))
RETURNS INTEGER
CONTAINS SQL
BEGIN
    UPDATE t_sequence SET seqvalue = seqvalue + setp
    WHERE seqname = seq_name;
    RETURN currseqval(seq_name);
END$
DELIMITER ;
 
-- 重设序列号
DROP FUNCTION IF EXISTS setsqeval;
DELIMITER $
CREATE FUNCTION setseqval (seq_name VARCHAR(50), value INTEGER)
RETURNS INTEGER
CONTAINS SQL
BEGIN
    UPDATE t_sequence SET seqvalue = value
    WHERE seqname = seq_name;
    RETURN currval(seq_name);
    END$
DELIMITER ;

-- 初始化
INSERT INTO t_sequence(seqname,seqhead,seqvalue,setp)  VALUES ('department', 'D', 0, 1);
INSERT INTO t_sequence(seqname,seqhead,seqvalue,setp)  VALUES ('position', 'P', 0, 1);
INSERT INTO t_sequence(seqname,seqhead,seqvalue,setp)  VALUES ('employee', 'E', 0, 1);
INSERT INTO t_sequence(seqname,seqhead,seqvalue,setp)  VALUES ('customer', 'C', 0, 1);
INSERT INTO t_sequence(seqname,seqhead,seqvalue,setp)  VALUES ('customercontacts', 'CC', 0, 1);
INSERT INTO t_sequence(seqname,seqhead,seqvalue,setp)  VALUES ('contract', 'CON', 0, 1);
-- 20180917
INSERT INTO t_sequence(seqname,seqhead,seqvalue,setp)  VALUES ('trip', 'T', 0, 1);

-- 部门表
DROP TABLE IF EXISTS `t_department`;
CREATE TABLE `t_department` (
	`id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`departmentcode` varchar(10) NOT NULL COMMENT '部门编码',
	`departmentname` varchar(20) NOT NULL COMMENT '部门名称',
	`iscrm` int NOT NULL COMMENT '是否需要客户管理',
	`parentid` int NOT NULL COMMENT '父分类id',
	`parentpath` varchar(20) default NULL COMMENT '当前分类所有上层（父）层级路径',
	`depth` int NOT NULL DEFAULT 0 COMMENT '当前所在层级数',
	`sameroottag` int NOT NULL DEFAULT 0 COMMENT '同类根属性标识，表示父子分类属于唯一标识',
	`child` int NOT NULL  DEFAULT 0 COMMENT '当前分类子分类数量',
	`previd` int NOT NULL DEFAULT 0 COMMENT '同级上个分类id',
	`nextid` int NOT NULL DEFAULT 0 COMMENT '同级下个分类id',
	`sort` int NOT NULL DEFAULT 0 COMMENT '父子分类内部排序',
	PRIMARY KEY (`id`),
	UNIQUE KEY `departmentcode` (`departmentcode`)
)
COMMENT='部门表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 职位表
DROP TABLE IF EXISTS `t_position`;
CREATE TABLE `t_position` (
	`id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`positioncode` varchar(10) NOT NULL COMMENT '职位编码',
	`positionname` varchar(20) NOT NULL COMMENT '职位名称',
	`parentid` int NOT NULL COMMENT '父分类id',
	`parentpath` varchar(20) default NULL COMMENT '当前分类所有上层（父）层级路径',
	`depth` int NOT NULL DEFAULT 0 COMMENT '当前所在层级数',
	`sameroottag` int NOT NULL DEFAULT 0 COMMENT '同类根属性标识，表示父子分类属于唯一标识',
	`child` int NOT NULL  DEFAULT 0 COMMENT '当前分类子分类数量',
	`previd` int NOT NULL DEFAULT 0 COMMENT '同级上个分类id',
	`nextid` int NOT NULL DEFAULT 0 COMMENT '同级下个分类id',
	`sort` int NOT NULL DEFAULT 0 COMMENT '父子分类内部排序',
	PRIMARY KEY (`id`),
	UNIQUE KEY `positioncode` (`positioncode`)
)
COMMENT='职位表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 雇员表（用户）
DROP TABLE IF EXISTS `t_employee`;
CREATE TABLE `t_employee` (
	`id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`employeecode` varchar(10) NOT NULL COMMENT '雇员编号（工号）',
	`passwords` varchar(100) NOT NULL COMMENT '登录密码',
	`departmentcode` varchar(10) COMMENT '所属部门',
	`positioncode` varchar(10) COMMENT '职位',
	`name` varchar(10) COMMENT '姓名',
	`sex` varchar(2) COMMENT '性别',
	`education` varchar(5) COMMENT '最高学历',
	`college` varchar(20) COMMENT '毕业院校',
	`extension` varchar(10) COMMENT '分机号',
	`telephone` varchar(20) COMMENT '联系电话',
	`entrydate` datetime COMMENT '入职时间', 
	`leavedate` datetime COMMENT '离职时间', 
	`accountstatus` varchar(5) NOT NULL COMMENT '帐号状态:未激活（创建）,激活,禁用,停用',
	`handoverperson` varchar(10) COMMENT '交接人',
	`rolecode` varchar(100) COMMENT '角色',
	`permission` varchar(100) COMMENT '菜单权限',
	`salesareas` varchar(500) COMMENT '销售区域编码',
	`remarks` varchar(500) COMMENT '备注',
	`createdate` datetime default NULL COMMENT '创建时间', 
	`updatedate` datetime default NULL COMMENT '更新时间', 
	PRIMARY KEY (`id`),
	UNIQUE KEY `employeecode` (`employeecode`)
)
COMMENT='雇员（用户）表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 客户基本信息表
DROP TABLE IF EXISTS `t_customer_info`;
CREATE TABLE `t_customer_info` (
	`id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`employeecode` varchar(10) NOT NULL COMMENT '雇员编号（工号）',
	`areacode` varchar(10) COMMENT '区域编号',
	`customercode` varchar(20) NOT NULL COMMENT '客户编号',
	`customername` varchar(100) NOT NULL COMMENT '客户名称',
	`legalperson` varchar(20) COMMENT '法人',
	`switchboard` varchar(20) COMMENT '总机',
	`fax` varchar(20) COMMENT '传真',
	`country` varchar(10) COMMENT '国家',
	`province` varchar(10) COMMENT '省',
	`city` varchar(20) COMMENT '市',
	`county` varchar(20) COMMENT '区县',
	`address` varchar(100) NOT NULL COMMENT '地址',
	`postcode` varchar(6) COMMENT '邮编',
	`industry` varchar(500) COMMENT '所属行业',
	`businessscope` varchar(500) COMMENT '经营范围',
	`grade` varchar(10) NOT NULL COMMENT '客户等级',
	`customertype` varchar(10) NOT NULL COMMENT '客户类型 普通客户/公海客户',
	`remarks` varchar(500) COMMENT '备注',
	`infostate` varchar(1) DEFAULT '0' COMMENT '信息状态',
	`createdate` datetime default NULL COMMENT '创建时间', 
	`invaliddate` datetime default NULL COMMENT '失效时间', 
	`updatedate` datetime default NULL COMMENT '更新时间', 
	PRIMARY KEY (`id`),
	UNIQUE KEY `customercode` (`customercode`),
	UNIQUE KEY `customername` (`customername`)
)
COMMENT='客户基本信息表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 客户历史记录表
DROP TABLE IF EXISTS `t_customer_history`;
CREATE TABLE `t_customer_history` (
	`id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`customercode` varchar(20) NOT NULL COMMENT '客户编码',
	`belongemployeecode` varchar(10) NOT NULL COMMENT '所属雇员',
	`opter` varchar(10) NOT NULL COMMENT '操作人',
	`optevent` varchar(500) COMMENT '事件',
	`createdate` datetime default NULL COMMENT '创建时间', 
	`updatedate` datetime default NULL COMMENT '更新时间', 
	PRIMARY KEY (`id`),
	INDEX (`customercode`)
)
COMMENT='客户历史记录表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 客户产品表
DROP TABLE IF EXISTS `t_customer_product`;
CREATE TABLE `t_customer_product` (
	`id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`customercode` varchar(20) NOT NULL COMMENT '客户编号',
	`productname` varchar(50) NOT NULL COMMENT '产品名称',
	`description` varchar(500) COMMENT '备注',
	`infostate` varchar(1) DEFAULT '0' COMMENT '信息状态',
	`createdate` datetime default NULL COMMENT '创建时间', 
	`updatedate` datetime default NULL COMMENT '更新时间', 
	PRIMARY KEY (`id`),
	INDEX (`customercode`)
)
COMMENT='客户产品表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 客户联系人表
DROP TABLE IF EXISTS `t_customer_contacts`;
CREATE TABLE `t_customer_contacts` (
	`id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`customercode` varchar(20) NOT NULL COMMENT '客户编号',
	`contactscode` varchar(20) NOT NULL COMMENT '联系人编号',
	`contactsname` varchar(20) NOT NULL COMMENT '联系人姓名',
	`department` varchar(50) NOT NULL COMMENT '所在部门',
	`position` varchar(50) COMMENT '职位',
	`telephone` varchar(50) COMMENT '座机',
	`mobile` varchar(50) COMMENT '手机',
	`qq` varchar(50) COMMENT 'qq',
	`weixin` varchar(50) COMMENT '微信号',
	`email` varchar(50) COMMENT '电子邮件',
	`infostate` varchar(1) DEFAULT '0' COMMENT '信息状态',
	`createdate` datetime default NULL COMMENT '创建时间', 
	`updatedate` datetime default NULL COMMENT '更新时间', 
	PRIMARY KEY (`id`),
	UNIQUE KEY `contactscode` (`contactscode`),
	INDEX (`customercode`)
)
COMMENT='客户联系人表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 联系记录表
DROP TABLE IF EXISTS `t_customer_contactrecord`;
CREATE TABLE `t_customer_contactrecord` (
	`id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`customercode` varchar(20) NOT NULL COMMENT '客户编号',
	`contactscode` varchar(20) NOT NULL COMMENT '联系人编号',
	`connectionway` varchar(20) NOT NULL COMMENT '联系途径:电话,QQ,EMAIL,上门拜访',
	`communicatetype` varchar(20) NOT NULL COMMENT '沟通类型:主动联系,对方联系',
	`contents` varchar(500) NOT NULL COMMENT '沟通内容',
	`contactdate` datetime NOT NULL COMMENT '联系日期',
	`infostate` varchar(1) DEFAULT '0' COMMENT '信息状态',
	`createdate` datetime default NULL COMMENT '创建时间', 
	`updatedate` datetime default NULL COMMENT '更新时间', 
	PRIMARY KEY (`id`),
	INDEX (`contactscode`),
	INDEX (`customercode`)
)
COMMENT='联系记录表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 客户需求表
DROP TABLE IF EXISTS `t_customer_demand`;
CREATE TABLE `t_customer_demand` (
	`id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`customercode` varchar(20) NOT NULL COMMENT '客户编号',
	`demandtype` varchar(20) NOT NULL COMMENT '需求类型：疑似 新增 有效 实现 作废 ',	
	`demanddate` datetime NOT NULL COMMENT '需求日期',
	`demandinfo` varchar(500) NOT NULL COMMENT '需求内容',
	`infostate` varchar(1) DEFAULT '0' COMMENT '信息状态',
	`createdate` datetime default NULL COMMENT '创建时间', 
	`updatedate` datetime default NULL COMMENT '更新时间', 
	PRIMARY KEY (`id`),
	INDEX (`customercode`)
)
COMMENT='客户需求表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 客户沟通总结（心得）表
DROP TABLE IF EXISTS `t_customer_summary`;
CREATE TABLE `t_customer_summary` (
	`id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`customercode` varchar(20) NOT NULL COMMENT '客户编号',
	`starttime` datetime NOT NULL COMMENT '开始时间',
	`endtime` datetime NOT NULL COMMENT '结束时间',
	`description` text NOT NULL COMMENT '心得描述',
	`isreply` varchar(1) DEFAULT '0' COMMENT '是否回复',
	`employeecode` varchar(10) COMMENT '评价人编号',
	`replyinfo` text COMMENT '评价内容',
	`infostate` varchar(1) DEFAULT '0' COMMENT '信息状态',
	`createdate` datetime default NULL COMMENT '创建时间', 
	`updatedate` datetime default NULL COMMENT '更新时间', 
	PRIMARY KEY (`id`),
	INDEX (`customercode`)
)
COMMENT='客户沟通总结（心得）表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 合同表
DROP TABLE IF EXISTS `t_contract`;
CREATE TABLE `t_contract` (
	`id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`customercode` varchar(20) NOT NULL COMMENT '客户编号',
	`contractcode` varchar(20) NOT NULL COMMENT '合同编号',
	`employeecode` varchar(10) NOT NULL COMMENT '雇员编号（工号）',
	`contractname` varchar(100) NOT NULL COMMENT '合同名称',
	`recordnumber` varchar(50) COMMENT '备案号',
	`dealdate` datetime NOT NULL COMMENT '成交日期',
	`deliverydate` datetime NOT NULL COMMENT '交货日期',
	`deliveryinfo` varchar(500) NOT NULL COMMENT '交货内容',
	`contacts` varchar(50) NOT NULL COMMENT '联系人',
	`deliveryadr` varchar(200) NOT NULL COMMENT '收货地址',
	`paymethod` varchar(20) NOT NULL COMMENT '付款方式',
	`total` varchar(20) NOT NULL COMMENT '合同标的',
	`advanceratio` varchar(10) NOT NULL COMMENT '预付比例',
	`contractstate` varchar(20) NOT NULL COMMENT '合同状态',
	`fundstate` varchar(20) NOT NULL COMMENT '款项状态',
	`settledate` datetime  COMMENT '结清日期',
	`remarks` varchar(500) COMMENT '备注',
	`createdate` datetime default NULL COMMENT '创建时间', 
	`updatedate` datetime default NULL COMMENT '更新时间', 
	PRIMARY KEY (`id`),
	UNIQUE KEY `contractcode` (`contractcode`),
	INDEX (`customercode`)
)
COMMENT='合同表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 合同款项表
DROP TABLE IF EXISTS `t_contract_fund`;
CREATE TABLE `t_contract_fund` (
	`id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`contractcode` varchar(20) NOT NULL COMMENT '合同编号',
	`fundtype` varchar(20) NOT NULL COMMENT '款项类型',
	`itemname` varchar(10) NOT NULL COMMENT '款项名称',
	`payway` varchar(20) NOT NULL COMMENT '付款方式',
	`fund` varchar(20) NOT NULL COMMENT '付款金额',
	`remarks` varchar(500) COMMENT '备注',
	`infostate` varchar(1) DEFAULT '0' COMMENT '信息状态',
	`isdel` int DEFAULT 0 COMMENT '删除标记',
	`createdate` datetime default NULL COMMENT '创建时间', 
	`updatedate` datetime default NULL COMMENT '更新时间', 
	PRIMARY KEY (`id`),
	INDEX (`contractcode`)
)
COMMENT='合同款项表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 初始化管理员
INSERT INTO t_employee(employeecode,passwords,name,permission,accountstatus,createdate) VALUES ('1001', 'b8c37e33defde51cf91e1e03e51657da', '超级管理员', 'menu001,menu002,menu003,menu004,menu005,menu006,menu007,menu008','102', now())

--rollback
delete from t_employee where employeecode='1001'

-- 20180907新增
-- 出差计划表
DROP TABLE IF EXISTS `t_trip_plan`;
CREATE TABLE `t_trip_plan` (
	`id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`plancode` varchar(20) NOT NULL COMMENT '计划编号',
	`employeecode` varchar(10) NOT NULL COMMENT '雇员编号',
	`days` varchar(20) NOT NULL COMMENT '出差天数',
	`cause` varchar(50) NOT NULL COMMENT '出差事由',
	`cost` varchar(10) COMMENT '出差预估费',
	`state` varchar(10) NOT NULL COMMENT '状态',
	`approver` varchar(10) COMMENT '审批人',
	`createdate` datetime default NULL COMMENT '创建时间', 
	`updatedate` datetime default NULL COMMENT '更新时间', 
	PRIMARY KEY (`id`),
	UNIQUE KEY `plancode` (`plancode`)
)
COMMENT='出差计划表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 行程明细表
DROP TABLE IF EXISTS `t_trip_item`;
CREATE TABLE `t_trip_item` (
	`id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`plancode` varchar(20) NOT NULL COMMENT '计划编号',
	`destination` varchar(20) NOT NULL COMMENT '出差地点',
	`starttime` datetime default NULL COMMENT '开始时间',
	`endtime` datetime default NULL COMMENT '结束时间',
	`remarks` varchar(500) COMMENT '备注',
	`createdate` datetime default NULL COMMENT '创建时间', 
	`updatedate` datetime default NULL COMMENT '更新时间', 
	PRIMARY KEY (`id`),
	INDEX (`plancode`)
)
COMMENT='行程明细表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 报销申请表
DROP TABLE IF EXISTS `t_reimbursement`;
CREATE TABLE `t_reimbursement` (
	`id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`applyno` varchar(20) NOT NULL COMMENT '申请流水号',
	`employeecode` varchar(10) NOT NULL COMMENT '申请人（雇员编号）',
	`mold` varchar(10) NOT NULL COMMENT '报销类型',
	`code` varchar(20) NOT NULL COMMENT '所属业务编码',
	`cost` varchar(20) NOT NULL COMMENT '费用总额',
	`state` varchar(10) NOT NULL COMMENT '审批状态',
	`approver` varchar(10) COMMENT '审批人',
	`payway` varchar(10) COMMENT '汇路',
	`voucher` varchar(200) COMMENT '打款凭证',
	`paytime` datetime COMMENT '完成时间',
	`remarks` varchar(500) COMMENT '备注',
	`createdate` datetime default NULL COMMENT '创建时间', 
	`updatedate` datetime default NULL COMMENT '更新时间', 
	PRIMARY KEY (`id`),
	UNIQUE KEY `applyno` (`applyno`)
)
COMMENT='报销申请表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 报销单据明细表
DROP TABLE IF EXISTS `t_reimbursement_item`;
CREATE TABLE `t_reimbursement_item` (
	`id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`applyno` varchar(20) NOT NULL COMMENT '申请流水号',
	`cost` varchar(20) NOT NULL COMMENT '报销金额',
	`category` varchar(10) NOT NULL COMMENT '报销类别',
	`detail` varchar(200) NOT NULL COMMENT '费用明细',
	`bill` varchar(200) COMMENT '票据资料',
	`remarks` varchar(500) COMMENT '备注',
	`createdate` datetime default NULL COMMENT '创建时间', 
	`updatedate` datetime default NULL COMMENT '更新时间', 
	PRIMARY KEY (`id`),
	INDEX (`applyno`)
)
COMMENT='报销单据明细表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 20180907新增结束

-- 20180926 新增开始
-- 设计工单表
DROP TABLE IF EXISTS `t_design`;
CREATE TABLE `t_design` (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`design_code` varchar(32) not null COMMENT '设计工单编号',
	`file_name` varchar(128) NOT NULL COMMENT '文件名称',
	`drawing_number` varchar(128) NOT NULL COMMENT '图号',
	`material` varchar(512) NOT NULL COMMENT '材料',
	`technical_requirement` varchar(512) COMMENT '技术要求',
	`pic_id` int COMMENT '设计图ID',
	`contract_code` varchar(32) COMMENT '合同编号',
	`status` int NOT NULL COMMENT '工单状态 0为审核中 1已完成',
  `createdate` timestamp not null default current_timestamp comment '创建时间',
  `updatedate` timestamp not null default current_timestamp on update current_timestamp comment '更新时间',
	PRIMARY KEY (`id`),
	KEY `idx_contract_code` (`contract_code`),
	KEY `idx_design_code` (`design_code`)
)
COMMENT='设计工单表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 设计工单审核表
DROP TABLE IF EXISTS `t_design_review`;
CREATE TABLE `t_design_review` (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	/*`review_code` varchar(32) not null COMMENT '审核编号',*/
	`design_code` varchar(32) NOT NULL COMMENT '工单编号',
	`review` int NOT NULL COMMENT '1通过 -1为审核不通过',
	`remarks` varchar(512) COMMENT '备注',
	`employee_code` varchar(32) COMMENT '审核人编号',
  `createdate` timestamp not null default current_timestamp comment '创建时间',
	PRIMARY KEY (`id`),
	KEY `idx_design_code` (`design_code`)
)
COMMENT='设计工单审核表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 设计工单设计图表
DROP TABLE IF EXISTS `t_design_pic`;
CREATE TABLE `t_design_pic` (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`design_code` varchar(32) NOT NULL COMMENT '工单编号',
	`index_pic` varchar(512) NOT NULL COMMENT '主设计图',
	`exhibit_pic` text COMMENT '附属设计图;隔开多图',
  `createdate` timestamp not null default current_timestamp comment '创建时间',
  `updatedate` timestamp not null default current_timestamp on update current_timestamp comment '更新时间',
	PRIMARY KEY (`id`),
	KEY `idx_design_code` (`design_code`)
)
COMMENT='设计工单设计图表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 仓库表
DROP TABLE IF EXISTS `t_storage`;
CREATE TABLE `t_storage` (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`storage_code` varchar(32) not null COMMENT '仓库编号',
	`storage_name` varchar(32) not null COMMENT '仓库名称',
	`manager` varchar(32) NOT NULL COMMENT '仓库管理员',
  `createdate` timestamp not null default current_timestamp comment '创建时间',
  `updatedate` timestamp not null default current_timestamp on update current_timestamp comment '更新时间',
	PRIMARY KEY (`id`),
	KEY `idx_storage_code` (`storage_code`)
)
COMMENT='仓库表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 货架表
DROP TABLE IF EXISTS `t_rack`;
CREATE TABLE `t_rack` (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`rack_code` varchar(32) not null COMMENT '货架编号',
	`storage_code` varchar(32) NOT NULL COMMENT '所属仓库编号',
	`type` int NOT NULL COMMENT '类型: 0为堆货区 1为货架',
	`area` varchar(32) COMMENT '所在区域',
	`description` varchar(128) COMMENT '描述',
	`size` varchar(32) not null COMMENT '总库存',
	`stock` varchar(32) not null default '0' COMMENT '已使用库存',
  `createdate` timestamp not null default current_timestamp comment '创建时间',
  `updatedate` timestamp not null default current_timestamp on update current_timestamp comment '更新时间',
  PRIMARY KEY (`id`),
	KEY `idx_rack_code` (`rack_code`)
)
COMMENT='货架表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 入库/出库表
DROP TABLE IF EXISTS `t_stock`;
CREATE TABLE `t_stock` (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`stock_code` varchar(32) not null COMMENT '入库/出库编号',
	`purchase_code` varchar(32) not null COMMENT '采购清单编号',
	`goods_code` varchar(32) NOT NULL COMMENT '物品编号',
	`rack_code` varchar(32) not null COMMENT '货架编号',
	`price` varchar(32) COMMENT '单价',
	`number` varchar(32) not null COMMENT '数量',
	`type` int not null COMMENT '0为入库  1为出库',
  `employee_code` varchar(32) not null COMMENT '入库/出库人编号',
  `createdate` timestamp not null default current_timestamp comment '创建时间',
	PRIMARY KEY (`id`),
	KEY `idx_stock_code` (`stock_code`)
)
COMMENT='入库/出库表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 20180926 结束