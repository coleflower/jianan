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
INSERT INTO t_sequence(seqname,seqhead,seqvalue,setp)  VALUES ('salesarea', 'S', 0, 1);
INSERT INTO t_sequence(seqname,seqhead,seqvalue,setp)  VALUES ('employee', 'E', 0, 1);
INSERT INTO t_sequence(seqname,seqhead,seqvalue,setp)  VALUES ('projectcategory', 'PC', 0, 1);
INSERT INTO t_sequence(seqname,seqhead,seqvalue,setp)  VALUES ('project', 'PJ', 0, 1);
INSERT INTO t_sequence(seqname,seqhead,seqvalue,setp)  VALUES ('customer', 'C', 0, 1);
INSERT INTO t_sequence(seqname,seqhead,seqvalue,setp)  VALUES ('customercontacts', 'CC', 0, 1);
INSERT INTO t_sequence(seqname,seqhead,seqvalue,setp)  VALUES ('contract', 'CON', 0, 1);
INSERT INTO t_sequence(seqname,seqhead,seqvalue,setp)  VALUES ('express', 'EP', 0, 1);
INSERT INTO t_sequence(seqname,seqhead,seqvalue,setp)  VALUES ('debugorder', 'DO', 0, 1);
INSERT INTO t_sequence(seqname,seqhead,seqvalue,setp)  VALUES ('aftersale', 'AS', 0, 1);
INSERT INTO t_sequence(seqname,seqhead,seqvalue,setp)  VALUES ('evaluate', 'EV', 0, 1);
INSERT INTO t_sequence(seqname,seqhead,seqvalue,setp)  VALUES ('finance', 'F', 0, 1);
INSERT INTO t_sequence(seqname,seqhead,seqvalue,setp)  VALUES ('financebill', 'FB', 0, 1);
INSERT INTO t_sequence(seqname,seqhead,seqvalue,setp)  VALUES ('productmaterials', 'PM', 0, 1);
INSERT INTO t_sequence(seqname,seqhead,seqvalue,setp)  VALUES ('procurement', 'P', 0, 1);
INSERT INTO t_sequence(seqname,seqhead,seqvalue,setp)  VALUES ('carApplication', 'CA', 0, 1);
INSERT INTO t_sequence(seqname,seqhead,seqvalue,setp)  VALUES ('procurementgoods', 'PG', 0, 1);
INSERT INTO t_sequence(seqname,seqhead,seqvalue,setp)  VALUES ('production', 'PT', 0, 1);
INSERT INTO t_sequence(seqname,seqhead,seqvalue,setp)  VALUES ('design', 'DS', 0, 1);

--售后服务表
DROP TABLE IF EXISTS `t_after_sale`;
CREATE TABLE `t_after_sale` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `after_service_code` varchar(20) NOT NULL COMMENT '售后服务表编号',
  `contract_code` varchar(20) NOT NULL COMMENT '合同编号',
  `employee_code` varchar(20) DEFAULT NULL COMMENT '处理人编号',
  `customer_code` varchar(20) NOT NULL,
  `customer_complaint` longtext COMMENT '客户投诉',
  `solution` longtext COMMENT '解决方案',
  `services_core` int(10) NOT NULL COMMENT '评分分数',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `status` int(4) NOT NULL DEFAULT '0' COMMENT '状态 0待处理 1已处理',
  `version` int(20) NOT NULL DEFAULT '1',
  `create_date` timestamp NOT NULL,
  `update_date` timestamp NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='售后服务表';

--副审核表
DROP TABLE IF EXISTS `t_assessor`;
CREATE TABLE `t_assessor` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `procurement_id` int(20) NOT NULL COMMENT '工单id',
  `department_code` varchar(20) NOT NULL COMMENT '部门编号',
  `employee_code` varchar(20) NOT NULL COMMENT '员工编号',
  `sort` int(10) NOT NULL COMMENT '审核序号',
  `type` int(2) NOT NULL,
  `status` int(2) DEFAULT NULL COMMENT '审核状态 0此人已审核 1此人正在处理的审核 ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COMMENT='审核表';

--主审核表
DROP TABLE IF EXISTS `t_assessor_primary`;
CREATE TABLE `t_assessor_primary` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `assessor_code` varchar(20) NOT NULL COMMENT '工单号',
  `code_type` varchar(20) NOT NULL COMMENT '工单类型',
  `approver_code` varchar(20) NOT NULL COMMENT '审核人编号',
  `approver_name` varchar(20) NOT NULL COMMENT '审核人姓名',
  `sort` int(10) NOT NULL COMMENT '排序',
  `status` int(2) DEFAULT NULL COMMENT '状态 0审核成功 1待完成审核 null 待审核',
  `remark` varchar(500) NOT NULL COMMENT '备注',
  `approver_time` datetime NOT NULL COMMENT '审核时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='主审核表';

--审核人员表
DROP TABLE IF EXISTS `t_assesstor_assistant`;
CREATE TABLE `t_assesstor_assistant` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL COMMENT '工单号',
  `approver_code` varchar(20) NOT NULL COMMENT '审核人编号',
  `approver_name` varchar(20) NOT NULL COMMENT '审核人姓名',
  `sort` int(10) NOT NULL COMMENT '排序',
  `status` int(2) DEFAULT NULL COMMENT '状态 0审核成功 1待完成审核 null 待审核',
  `remark` varchar(500) NOT NULL COMMENT '备注',
  `approver_time` datetime NOT NULL COMMENT '审核时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--汽车清单表
DROP TABLE IF EXISTS `t_car`;
CREATE TABLE `t_car` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `brank` varchar(20) NOT NULL COMMENT '品牌',
  `type` varchar(20) NOT NULL COMMENT '型号',
  `total_road_haul` double(10,2) NOT NULL COMMENT '行驶里程',
  `total_refueling_number` int(10) NOT NULL COMMENT '加油次数',
  `status` int(2) DEFAULT '0' COMMENT '状态 0未使用 1正在使用 2维修中',
  `create_time` timestamp NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

--汽车申请表
DROP TABLE IF EXISTS `t_car_application`;
CREATE TABLE `t_car_application` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `car_use_code` varchar(20) NOT NULL COMMENT '车辆使用流水号',
  `car_id` varchar(20) NOT NULL COMMENT '关联car的id',
  `employee_code` varchar(20) NOT NULL COMMENT '员工编号',
  `employee_name` varchar(20) NOT NULL COMMENT '员工姓名',
  `road_haul` double(10,2) DEFAULT NULL COMMENT '行驶里程',
  `refueling_number` int(10) DEFAULT NULL COMMENT '加油次数',
  `status` int(2) DEFAULT '0' COMMENT '状态 0待审核 1审核成功 2审核拒绝',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `use_time` datetime NOT NULL COMMENT '使用时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

--产品检查表
DROP TABLE IF EXISTS `t_check_product`;
CREATE TABLE `t_check_product` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `product_code` varchar(20) NOT NULL COMMENT '关联的产品编号',
  `item_name` varchar(20) NOT NULL COMMENT '名字',
  `score` int(4) NOT NULL COMMENT '分数',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

--合同表
DROP TABLE IF EXISTS `t_contract`;
CREATE TABLE `t_contract` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `customercode` varchar(20) NOT NULL COMMENT '客户编号',
  `projectcode` varchar(10) DEFAULT NULL COMMENT '项目编号',
  `parentcontractcode` varchar(20) DEFAULT NULL COMMENT '补发合同编号',
  `contractcode` varchar(20) NOT NULL COMMENT '合同编号',
  `employeecode` varchar(10) NOT NULL COMMENT '雇员编号（工号）',
  `contractname` varchar(100) NOT NULL COMMENT '合同名称',
  `recordnumber` varchar(50) DEFAULT NULL COMMENT '备案号',
  `dealdate` datetime NOT NULL COMMENT '成交日期',
  `deliverydate` datetime NOT NULL,
  `deliveryinfo` varchar(500) NOT NULL,
  `contacts` varchar(20) NOT NULL,
  `deliveryadr` varchar(50) NOT NULL,
  `total` varchar(20) DEFAULT NULL COMMENT '合同标的',
  `paymethod` varchar(20) NOT NULL,
  `advanceratio` varchar(10) NOT NULL COMMENT '预付比例',
  `contractstate` varchar(20) NOT NULL COMMENT '合同状态',
  `fundstate` varchar(20) NOT NULL COMMENT '款项状态',
  `settledate` datetime DEFAULT NULL COMMENT '结清日期',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  `producttype` varchar(20) NOT NULL COMMENT '产品型号',
  `productname` varchar(20) NOT NULL COMMENT '产品名称',
  `productcount` int(20) DEFAULT NULL COMMENT '产品数量',
  `createdate` datetime DEFAULT NULL COMMENT '创建时间',
  `updatedate` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `contractcode` (`contractcode`),
  KEY `customercode` (`customercode`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='合同表';

--合同款项表
DROP TABLE IF EXISTS `t_contract_fund`;
CREATE TABLE `t_contract_fund` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `contractcode` varchar(20) NOT NULL COMMENT '合同编号',
  `fundtype` varchar(20) NOT NULL COMMENT '款项类型',
  `itemname` varchar(10) NOT NULL COMMENT '款项名称',
  `payway` varchar(20) NOT NULL COMMENT '付款方式',
  `fund` varchar(20) NOT NULL COMMENT '付款金额',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  `infostate` varchar(1) DEFAULT '0' COMMENT '信息状态',
  `isdel` int(11) DEFAULT '0' COMMENT '删除标记',
  `createdate` datetime DEFAULT NULL COMMENT '创建时间',
  `updatedate` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `contractcode` (`contractcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合同款项表';

--联系记录表
DROP TABLE IF EXISTS `t_customer_contactrecord`;
CREATE TABLE `t_customer_contactrecord` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `customercode` varchar(20) NOT NULL COMMENT '客户编号',
  `contactscode` varchar(20) NOT NULL COMMENT '联系人编号',
  `connectionway` varchar(20) NOT NULL COMMENT '联系途径:电话,QQ,EMAIL,上门拜访',
  `communicatetype` varchar(20) NOT NULL COMMENT '沟通类型:主动联系,对方联系',
  `contents` varchar(500) NOT NULL COMMENT '沟通内容',
  `contactdate` datetime NOT NULL COMMENT '联系日期',
  `infostate` varchar(1) DEFAULT '0' COMMENT '信息状态',
  `createdate` datetime DEFAULT NULL COMMENT '创建时间',
  `updatedate` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `contactscode` (`contactscode`),
  KEY `customercode` (`customercode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='联系记录表';

--客户联系人表
DROP TABLE IF EXISTS `t_customer_contacts`;
CREATE TABLE `t_customer_contacts` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `customercode` varchar(20) NOT NULL COMMENT '客户编号',
  `contactscode` varchar(20) NOT NULL COMMENT '联系人编号',
  `contactsname` varchar(20) NOT NULL COMMENT '联系人姓名',
  `department` varchar(50) NOT NULL COMMENT '所在部门',
  `position` varchar(50) DEFAULT NULL COMMENT '职位',
  `telephone` varchar(50) DEFAULT NULL COMMENT '座机',
  `mobile` varchar(50) DEFAULT NULL COMMENT '手机',
  `qq` varchar(50) DEFAULT NULL COMMENT 'qq',
  `weixin` varchar(50) DEFAULT NULL COMMENT '微信号',
  `email` varchar(50) DEFAULT NULL COMMENT '电子邮件',
  `infostate` varchar(1) DEFAULT '0' COMMENT '信息状态',
  `createdate` datetime DEFAULT NULL COMMENT '创建时间',
  `updatedate` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `contactscode` (`contactscode`),
  KEY `customercode` (`customercode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户联系人表';

--客户需求表
DROP TABLE IF EXISTS `t_customer_demand`;
CREATE TABLE `t_customer_demand` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `customercode` varchar(20) NOT NULL COMMENT '客户编号',
  `demandtype` varchar(20) NOT NULL COMMENT '需求类型：疑似 新增 有效 实现 作废 ',
  `demanddate` datetime NOT NULL COMMENT '需求日期',
  `demandinfo` varchar(500) NOT NULL COMMENT '需求内容',
  `infostate` varchar(1) DEFAULT '0' COMMENT '信息状态',
  `createdate` datetime DEFAULT NULL COMMENT '创建时间',
  `updatedate` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `customercode` (`customercode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户需求表';

--客户基本信息表
DROP TABLE IF EXISTS `t_customer_info`;
CREATE TABLE `t_customer_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `employeecode` varchar(10) NOT NULL COMMENT '雇员编号（工号）',
  `areacode` varchar(10) DEFAULT NULL COMMENT '区域编号',
  `customercode` varchar(20) NOT NULL COMMENT '客户编号',
  `customername` varchar(100) NOT NULL COMMENT '客户名称',
  `legalperson` varchar(20) DEFAULT NULL COMMENT '法人',
  `switchboard` varchar(20) DEFAULT NULL COMMENT '总机',
  `fax` varchar(20) DEFAULT NULL COMMENT '传真',
  `country` varchar(10) DEFAULT NULL COMMENT '国家',
  `province` varchar(10) DEFAULT NULL COMMENT '省',
  `city` varchar(20) DEFAULT NULL COMMENT '市',
  `county` varchar(20) DEFAULT NULL COMMENT '区县',
  `address` varchar(100) NOT NULL COMMENT '地址',
  `postcode` varchar(6) DEFAULT NULL COMMENT '邮编',
  `industry` varchar(500) DEFAULT NULL COMMENT '所属行业',
  `businessscope` varchar(500) DEFAULT NULL COMMENT '经营范围',
  `grade` varchar(10) NOT NULL COMMENT '客户等级',
  `customertype` varchar(20) DEFAULT NULL COMMENT '客户类型',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  `infostate` varchar(1) DEFAULT '0' COMMENT '信息状态',
  `createdate` datetime DEFAULT NULL COMMENT '创建时间',
  `updatedate` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `customercode` (`customercode`),
  UNIQUE KEY `customername` (`customername`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='客户基本信息表';

--客户产品表
DROP TABLE IF EXISTS `t_customer_product`;
CREATE TABLE `t_customer_product` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `customercode` varchar(20) NOT NULL COMMENT '客户编号',
  `productname` varchar(50) NOT NULL COMMENT '产品名称',
  `description` varchar(500) DEFAULT NULL COMMENT '备注',
  `infostate` varchar(1) DEFAULT '0' COMMENT '信息状态',
  `createdate` datetime DEFAULT NULL COMMENT '创建时间',
  `updatedate` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `customercode` (`customercode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户产品表';

--客户沟通总结（心得）表
DROP TABLE IF EXISTS `t_customer_summary`;
CREATE TABLE `t_customer_summary` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `customercode` varchar(20) NOT NULL COMMENT '客户编号',
  `starttime` datetime NOT NULL COMMENT '开始时间',
  `endtime` datetime NOT NULL COMMENT '结束时间',
  `description` text NOT NULL COMMENT '心得描述',
  `isreply` varchar(1) DEFAULT '0' COMMENT '是否回复',
  `employeecode` varchar(10) DEFAULT NULL COMMENT '评价人编号',
  `replyinfo` text COMMENT '评价内容',
  `infostate` varchar(1) DEFAULT '0' COMMENT '信息状态',
  `createdate` datetime DEFAULT NULL COMMENT '创建时间',
  `updatedate` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `customercode` (`customercode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户沟通总结（心得）表';

--工作日报表
DROP TABLE IF EXISTS `t_daily`;
CREATE TABLE `t_daily` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `summary` varchar(500) NOT NULL COMMENT '今日总结',
  `plan` varchar(500) NOT NULL COMMENT '明日计划',
  `felling` varchar(500) NOT NULL COMMENT '工作心得',
  `department` varchar(10) NOT NULL,
  `employee_code` varchar(10) NOT NULL COMMENT '员工',
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

--部门表
DROP TABLE IF EXISTS `t_department`;
CREATE TABLE `t_department` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `departmentcode` varchar(10) NOT NULL COMMENT '部门编码',
  `departmentname` varchar(20) NOT NULL COMMENT '部门名称',
  `iscrm` int(11) NOT NULL COMMENT '是否需要客户管理',
  `parentid` int(11) NOT NULL COMMENT '父分类id',
  `parentpath` varchar(20) DEFAULT NULL COMMENT '当前分类所有上层（父）层级路径',
  `depth` int(11) NOT NULL DEFAULT '0' COMMENT '当前所在层级数',
  `sameroottag` int(11) NOT NULL DEFAULT '0' COMMENT '同类根属性标识，表示父子分类属于唯一标识',
  `child` int(11) NOT NULL DEFAULT '0' COMMENT '当前分类子分类数量',
  `previd` int(11) NOT NULL DEFAULT '0' COMMENT '同级上个分类id',
  `nextid` int(11) NOT NULL DEFAULT '0' COMMENT '同级下个分类id',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '父子分类内部排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `departmentcode` (`departmentcode`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='部门表';

--设计工单表
DROP TABLE IF EXISTS `t_design`;
CREATE TABLE `t_design` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `design_code` varchar(32) NOT NULL COMMENT '设计工单编号',
  `file_name` varchar(128) DEFAULT NULL COMMENT '文件名称',
  `drawing_number` varchar(128) DEFAULT NULL COMMENT '图号',
  `material` varchar(512) DEFAULT NULL COMMENT '材料',
  `technical_requirement` varchar(512) DEFAULT NULL COMMENT '技术要求',
  `pic_id` int(11) DEFAULT NULL COMMENT '设计图ID',
  `contract_code` varchar(32) NOT NULL COMMENT '合同编号',
  `status` int(11) NOT NULL COMMENT '工单状态 0待审核 1已审核  2审核中 -1审核未通过',
  `createdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatedate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_contract_code` (`contract_code`),
  KEY `idx_design_code` (`design_code`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='设计工单表';

--设计工单设计图表
DROP TABLE IF EXISTS `t_design_pic`;
CREATE TABLE `t_design_pic` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `design_code` varchar(32) NOT NULL COMMENT '工单编号',
  `index_pic` varchar(512) NOT NULL COMMENT '主设计图',
  `exhibit_pic` text COMMENT '附属设计图 ,隔开多图',
  `createdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatedate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_design_code` (`design_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设计工单设计图表';

--设计工单审核表
DROP TABLE IF EXISTS `t_design_review`;
CREATE TABLE `t_design_review` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `design_code` varchar(32) NOT NULL COMMENT '工单编号',
  `review` int(11) NOT NULL COMMENT '0为未审核 1通过 -1为审核不通过',
  `remarks` varchar(512) DEFAULT NULL COMMENT '备注',
  `employee_code` varchar(32) DEFAULT NULL COMMENT '审核人编号',
  `createdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_design_code` (`design_code`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='设计工单审核表';

--员工表
DROP TABLE IF EXISTS `t_employee`;
CREATE TABLE `t_employee` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `employeecode` varchar(10) NOT NULL COMMENT '雇员编号（工号）',
  `passwords` varchar(100) NOT NULL COMMENT '登录密码',
  `departmentcode` varchar(10) DEFAULT NULL COMMENT '所属部门',
  `positioncode` varchar(10) DEFAULT NULL COMMENT '职位',
  `name` varchar(10) DEFAULT NULL COMMENT '姓名',
  `sex` varchar(2) DEFAULT NULL COMMENT '性别',
  `education` varchar(5) DEFAULT NULL COMMENT '最高学历',
  `college` varchar(20) DEFAULT NULL COMMENT '毕业院校',
  `extension` varchar(10) DEFAULT NULL COMMENT '分机号',
  `telephone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `entrydate` datetime DEFAULT NULL COMMENT '入职时间',
  `leavedate` datetime DEFAULT NULL COMMENT '离职时间',
  `accountstatus` varchar(5) NOT NULL COMMENT '帐号状态:未激活（创建）,激活,禁用,停用',
  `handoverperson` varchar(10) DEFAULT NULL COMMENT '交接人',
  `rolecode` varchar(100) DEFAULT NULL COMMENT '角色',
  `permission` varchar(100) DEFAULT NULL COMMENT '菜单权限',
  `salesareas` varchar(500) DEFAULT NULL COMMENT '销售区域编码',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  `createdate` datetime DEFAULT NULL COMMENT '创建时间',
  `updatedate` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `employeecode` (`employeecode`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='雇员（用户）表';

--成品评价表
DROP TABLE IF EXISTS `t_evaluate`;
CREATE TABLE `t_evaluate` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `product_evluate_code` varchar(20) NOT NULL COMMENT '产品评价单编号',
  `contract_code` varchar(20) NOT NULL COMMENT '合同编号',
  `average` decimal(4,2) DEFAULT NULL COMMENT '平均分',
  `create_date` timestamp NOT NULL,
  `update_date` timestamp NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='成品评价表';

--成品详情表
DROP TABLE IF EXISTS `t_evaluate_detail`;
CREATE TABLE `t_evaluate_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `product_evlauate_code` varchar(20) NOT NULL COMMENT '产品评价编号',
  `department_code` varchar(20) NOT NULL COMMENT '部门编号',
  `employee_code` varchar(20) NOT NULL COMMENT '员工编号',
  `score` decimal(4,2) NOT NULL COMMENT '分数',
  `evaluate` varchar(500) NOT NULL COMMENT '评价',
  `create_date` timestamp NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

--物流工单表
DROP TABLE IF EXISTS `t_express`;
CREATE TABLE `t_express` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `express_code` varchar(20) NOT NULL COMMENT '运输协议编号',
  `contract_code` varchar(20) NOT NULL COMMENT '合同编号',
  `employee_code` varchar(20) DEFAULT NULL COMMENT '员工编号',
  `employee_name` varchar(20) DEFAULT NULL COMMENT '处理人名字',
  `destination` varchar(50) DEFAULT NULL COMMENT '目的地',
  `send_time` timestamp NULL DEFAULT NULL COMMENT '发送时间',
  `arrive_time` timestamp NULL DEFAULT NULL COMMENT '达到时间',
  `express_price` decimal(18,4) DEFAULT NULL COMMENT '运费',
  `express_company` varchar(32) DEFAULT NULL COMMENT '物流公司',
  `status` int(6) NOT NULL DEFAULT '0' COMMENT '状态 0未发货 1已发货',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `version` int(20) NOT NULL DEFAULT '1',
  `create_date` timestamp NOT NULL COMMENT '创建时间',
  `update_date` timestamp NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='物流工单表';

--发票表
DROP TABLE IF EXISTS `t_finance_bill`;
CREATE TABLE `t_finance_bill` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `finance_bill_code` varchar(20) DEFAULT NULL COMMENT '开票工单',
  `bill_type` varchar(20) NOT NULL COMMENT '发票类型',
  `bill_title` varchar(20) NOT NULL COMMENT '发票抬头',
  `contract_code` varchar(20) NOT NULL COMMENT '合同编号',
  `customer_code` varchar(20) NOT NULL COMMENT '客户编号',
  `proposer` varchar(20) NOT NULL COMMENT '申请人',
  `proposer_name` varchar(20) DEFAULT NULL COMMENT '申请人名字',
  `employee_code` varchar(20) DEFAULT NULL COMMENT '处理员工编号',
  `employee_name` varchar(20) DEFAULT NULL COMMENT '处理人姓名',
  `money` decimal(12,4) NOT NULL COMMENT '金额',
  `status` varchar(20) NOT NULL DEFAULT '' COMMENT '状态',
  `icon` varchar(100) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_date` timestamp NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--设备调试工单表
DROP TABLE IF EXISTS `t_fix`;
CREATE TABLE `t_fix` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `express_code` varchar(20) DEFAULT NULL COMMENT '物流编号',
  `debug_order_code` varchar(20) DEFAULT NULL COMMENT '调试工单编号',
  `contract_code` varchar(20) NOT NULL COMMENT '合同编号',
  `employee_code` varchar(20) DEFAULT NULL COMMENT '处理人编号',
  `customer_code` varchar(20) NOT NULL,
  `fix_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '调试时间',
  `handler_code` varchar(20) DEFAULT NULL COMMENT '销售部选择调试工单时间人员编号',
  `handler_name` varchar(20) DEFAULT NULL COMMENT '销售部选择调试工单时间人员姓名',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `status` int(4) NOT NULL DEFAULT '0' COMMENT '状态 0待处理 1已处理 2处理完成',
  `version` int(20) NOT NULL DEFAULT '1' COMMENT '版本号',
  `create_date` timestamp NOT NULL COMMENT '创建时间',
  `update_date` timestamp NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='设备调试工单表';

--职位表
DROP TABLE IF EXISTS `t_position`;
CREATE TABLE `t_position` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `positioncode` varchar(10) NOT NULL COMMENT '职位编码',
  `positionname` varchar(20) NOT NULL COMMENT '职位名称',
  `parentid` int(11) NOT NULL COMMENT '父分类id',
  `parentpath` varchar(20) DEFAULT NULL COMMENT '当前分类所有上层（父）层级路径',
  `depth` int(11) NOT NULL DEFAULT '0' COMMENT '当前所在层级数',
  `sameroottag` int(11) NOT NULL DEFAULT '0' COMMENT '同类根属性标识，表示父子分类属于唯一标识',
  `child` int(11) NOT NULL DEFAULT '0' COMMENT '当前分类子分类数量',
  `previd` int(11) NOT NULL DEFAULT '0' COMMENT '同级上个分类id',
  `nextid` int(11) NOT NULL DEFAULT '0' COMMENT '同级下个分类id',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '父子分类内部排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `positioncode` (`positioncode`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='职位表';

--采购单
DROP TABLE IF EXISTS `t_procurement`;
CREATE TABLE `t_procurement` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `procurement_code` varchar(20) DEFAULT NULL COMMENT '采购工单编号',
  `product_materials_id` int(20) NOT NULL COMMENT '原料id',
  `product_code` varchar(20) NOT NULL COMMENT '产品编号',
  `name` varchar(20) NOT NULL COMMENT '规格名称',
  `material` varchar(20) DEFAULT NULL COMMENT '材料',
  `count` int(10) NOT NULL COMMENT '数量',
  `code_name` varchar(20) DEFAULT NULL COMMENT '标准代号',
  `supplier` varchar(20) DEFAULT NULL COMMENT '供应商',
  `operator` varchar(20) DEFAULT NULL COMMENT '操作人',
  `quoted_price` decimal(20,2) DEFAULT NULL COMMENT '报价',
  `status` int(4) NOT NULL DEFAULT '0' COMMENT '0待处理 1待审批 2审批成功 3审批拒绝 4资金申请待处理 5已拨款 6已生成采购单 ',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COMMENT='采购单';

--采购单
DROP TABLE IF EXISTS `t_procurement_goods`;
CREATE TABLE `t_procurement_goods` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `procurement_goods_code` varchar(20) NOT NULL COMMENT '采购单编号',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `material` varchar(20) DEFAULT NULL COMMENT '材料',
  `standard_code` varchar(20) DEFAULT NULL COMMENT '标准代号',
  `count` int(4) NOT NULL COMMENT '数量',
  `status` int(3) DEFAULT '0' COMMENT '状态 0待处理 1待财务处理 2已拨款',
  `employee_code` varchar(20) DEFAULT NULL COMMENT '员工编号',
  `employee_name` varchar(20) DEFAULT NULL COMMENT '员工姓名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COMMENT='采购单';

--采购原料单
DROP TABLE IF EXISTS `t_product_materials`;
CREATE TABLE `t_product_materials` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `product_materials_code` varchar(20) NOT NULL,
  `product_code` varchar(20) NOT NULL COMMENT '产品编号',
  `product_materials_name` varchar(20) NOT NULL COMMENT '产品原料名称',
  `product_materials` varchar(20) DEFAULT NULL COMMENT '原料材料',
  `product_materials_count` int(10) NOT NULL COMMENT '产品原料数量',
  `product_materials_standard_code` varchar(20) DEFAULT NULL COMMENT '产品标准代号',
  `procurement_status` int(4) NOT NULL DEFAULT '0' COMMENT '采购状态  0默认 1缺货 2缺货,采购中 3有货 4采购拒绝 5申请出库中 6申请部分出库中 7已出库 8部分已出库 9出库拒绝',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `stock_log` varchar(500) DEFAULT NULL COMMENT '减库存日志',
  `supplier` varchar(20) DEFAULT NULL COMMENT '供应商',
  `product_price` decimal(10,4) DEFAULT NULL COMMENT '产品价格',
  `procurement_remark` varchar(500) DEFAULT NULL COMMENT '采购备注',
  `handler_code` varchar(20) NOT NULL COMMENT '处理人编号',
  `create_date` timestamp NOT NULL COMMENT '创建时间',
  `application_reason` varchar(500) DEFAULT NULL COMMENT '申请原因',
  `icon` varchar(100) DEFAULT NULL COMMENT '图片',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8mb4 COMMENT='产品原料表';

--生产工单
DROP TABLE IF EXISTS `t_production`;
CREATE TABLE `t_production` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `product_code` varchar(20) NOT NULL COMMENT '产品编号',
  `product_type` varchar(20) NOT NULL COMMENT '型号',
  `product_name` varchar(20) NOT NULL COMMENT '产品名称',
  `product_count` int(10) NOT NULL COMMENT '产品数量',
  `contract_code` varchar(20) NOT NULL,
  `handler_code` varchar(20) DEFAULT NULL COMMENT '处理人编号',
  `materials_handler` varchar(20) DEFAULT NULL,
  `status` int(4) NOT NULL DEFAULT '0' COMMENT '0待处理 1生产中 2生产完成 3质检通过 4质检拒绝 5原料待出库 6可下发生产状态 7采购拒绝',
  `worker_count` int(6) DEFAULT NULL COMMENT '员工数量',
  `finish_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '结束时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `materials_remark` varchar(500) DEFAULT NULL COMMENT '原料审核备注',
  `quality_remark` varchar(500) DEFAULT NULL COMMENT '质检备注',
  `create_time` timestamp NOT NULL,
  `update_time` timestamp NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

--项目表
DROP TABLE IF EXISTS `t_project`;
CREATE TABLE `t_project` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `projectcategorycode` varchar(10) NOT NULL COMMENT '项目分类编码',
  `projectcode` varchar(10) NOT NULL COMMENT '项目编码',
  `projectname` varchar(50) NOT NULL COMMENT '项目名称',
  `owner` varchar(50) NOT NULL COMMENT '项目主导人',
  `floorprice` varchar(10) DEFAULT NULL COMMENT '最低限价',
  `projectstate` varchar(5) NOT NULL COMMENT '项目状态:未生效,已生效,终止',
  `targetindustry` varchar(200) DEFAULT NULL COMMENT '目标行业（客群）',
  `projectdescription` varchar(500) DEFAULT NULL COMMENT '项目描述',
  `infostate` varchar(1) DEFAULT '0' COMMENT '信息状态',
  `createdate` datetime DEFAULT NULL COMMENT '创建时间',
  `updatedate` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `projectcode` (`projectcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目表';

--项目分类表
DROP TABLE IF EXISTS `t_project_category`;
CREATE TABLE `t_project_category` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `projectcategorycode` varchar(10) NOT NULL COMMENT '项目分类编码',
  `projectcategoryname` varchar(20) NOT NULL COMMENT '项目分类名称',
  `parentid` int(11) NOT NULL COMMENT '父分类id',
  `parentpath` varchar(20) DEFAULT NULL COMMENT '当前分类所有上层（父）层级路径',
  `depth` int(11) NOT NULL DEFAULT '0' COMMENT '当前所在层级数',
  `sameroottag` int(11) NOT NULL DEFAULT '0' COMMENT '同类根属性标识，表示父子分类属于唯一标识',
  `child` int(11) NOT NULL DEFAULT '0' COMMENT '当前分类子分类数量',
  `previd` int(11) NOT NULL DEFAULT '0' COMMENT '同级上个分类id',
  `nextid` int(11) NOT NULL DEFAULT '0' COMMENT '同级下个分类id',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '父子分类内部排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `projectcategorycode` (`projectcategorycode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目分类表';

--项目附件表
DROP TABLE IF EXISTS `t_project_enclosure`;
CREATE TABLE `t_project_enclosure` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `projectcode` varchar(10) NOT NULL COMMENT '项目编码',
  `enclosurename` varchar(20) NOT NULL COMMENT '附件名称',
  `url` varchar(500) DEFAULT NULL COMMENT '附近路径',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `infostate` varchar(1) DEFAULT '0' COMMENT '信息状态',
  `createdate` datetime DEFAULT NULL COMMENT '创建时间',
  `updatedate` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `projectcode` (`projectcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目附件表';

--货架表
DROP TABLE IF EXISTS `t_rack`;
CREATE TABLE `t_rack` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `rack_code` varchar(32) NOT NULL COMMENT '货架编号',
  `storage_code` varchar(32) NOT NULL COMMENT '所属仓库编号',
  `type` int(11) NOT NULL COMMENT '类型: 0为堆货区 1为货架',
  `area` varchar(32) DEFAULT NULL COMMENT '所在区域',
  `description` varchar(128) DEFAULT NULL COMMENT '描述',
  `size` varchar(32) NOT NULL COMMENT '总库存',
  `stock` varchar(32) NOT NULL DEFAULT '0' COMMENT '已使用库存',
  `createdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatedate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_rack_code` (`rack_code`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='货架表';

--报销表
DROP TABLE IF EXISTS `t_reimbursement`;
CREATE TABLE `t_reimbursement` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `apply_no` varchar(20) NOT NULL COMMENT '申请流水号',
  `employee_code` varchar(20) NOT NULL COMMENT '申请人编号',
  `mold` varchar(20) NOT NULL COMMENT '报销类型',
  `code` varchar(20) NOT NULL COMMENT '所属业务编号',
  `cost` varchar(20) NOT NULL DEFAULT '0' COMMENT '总金额',
  `state` varchar(4) NOT NULL DEFAULT '0' COMMENT '状态',
  `approver` varchar(10) DEFAULT NULL COMMENT '审批人',
  `payway` varchar(20) DEFAULT NULL COMMENT '打款方式',
  `voucher` varchar(100) DEFAULT NULL COMMENT '打款凭证',
  `pay_time` timestamp NULL DEFAULT NULL COMMENT '完成时间',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  `version` int(20) NOT NULL DEFAULT '1',
  `create_date` timestamp NOT NULL COMMENT '创建时间',
  `update_date` timestamp NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

--报销明细表
DROP TABLE IF EXISTS `t_reimbursementItem`;
CREATE TABLE `t_reimbursementItem` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `apply_no` varchar(20) NOT NULL COMMENT '流水号编号',
  `cost` varchar(20) NOT NULL COMMENT '金额',
  `category` varchar(20) NOT NULL COMMENT '报销类别',
  `detail` varchar(500) NOT NULL COMMENT '费用明细',
  `bill` varchar(100) NOT NULL COMMENT '票据资料',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_date` timestamp NOT NULL COMMENT '创建时间',
  `update_date` timestamp NOT NULL COMMENT '跟新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

--销售区域表
DROP TABLE IF EXISTS `t_salesarea`;
CREATE TABLE `t_salesarea` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `areacode` varchar(10) NOT NULL COMMENT '销售区域编码',
  `areaname` varchar(20) NOT NULL COMMENT '销售区域名称',
  `parentid` int(11) NOT NULL COMMENT '父分类id',
  `parentpath` varchar(20) DEFAULT NULL COMMENT '当前分类所有上层（父）层级路径',
  `depth` int(11) NOT NULL DEFAULT '0' COMMENT '当前所在层级数',
  `sameroottag` int(11) NOT NULL DEFAULT '0' COMMENT '同类根属性标识，表示父子分类属于唯一标识',
  `child` int(11) NOT NULL DEFAULT '0' COMMENT '当前分类子分类数量',
  `previd` int(11) NOT NULL DEFAULT '0' COMMENT '同级上个分类id',
  `nextid` int(11) NOT NULL DEFAULT '0' COMMENT '同级下个分类id',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '父子分类内部排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `areacode` (`areacode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='销售区域表';

--入库/出库表
DROP TABLE IF EXISTS `t_stock`;
CREATE TABLE `t_stock` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `material` varchar(20) DEFAULT NULL COMMENT '材料',
  `stock_code` varchar(32) NOT NULL COMMENT '入库/出库编号',
  `purchase_code` varchar(32) NOT NULL COMMENT '采购清单编号',
  `goods_code` varchar(32) NOT NULL COMMENT '物品编号',
  `rack_code` varchar(32) NOT NULL COMMENT '货架编号',
  `price` varchar(32) DEFAULT NULL COMMENT '单价',
  `number` varchar(32) NOT NULL COMMENT '数量',
  `type` int(11) NOT NULL COMMENT '0为入库  1为出库 2库存已被选择 ',
  `employee_code` varchar(32) NOT NULL COMMENT '入库/出库人编号',
  `createdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_stock_code` (`stock_code`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='入库/出库表';

--仓库表
DROP TABLE IF EXISTS `t_storage`;
CREATE TABLE `t_storage` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `storage_code` varchar(32) NOT NULL COMMENT '仓库编号',
  `storage_name` varchar(32) NOT NULL COMMENT '仓库名称',
  `manager` varchar(32) NOT NULL COMMENT '仓库管理员',
  `createdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatedate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_storage_code` (`storage_code`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='仓库表';

--行程明细表
DROP TABLE IF EXISTS `t_trip_item`;
CREATE TABLE `t_trip_item` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `plancode` varchar(20) NOT NULL COMMENT '计划编号',
  `destination` varchar(20) NOT NULL COMMENT '出差地点',
  `starttime` datetime DEFAULT NULL COMMENT '开始时间',
  `endtime` datetime DEFAULT NULL COMMENT '结束时间',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  `createdate` datetime DEFAULT NULL COMMENT '创建时间',
  `updatedate` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `plancode` (`plancode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='行程明细表';

--出差计划表
DROP TABLE IF EXISTS `t_trip_plan`;
CREATE TABLE `t_trip_plan` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `plancode` varchar(20) NOT NULL COMMENT '计划编号',
  `employeecode` varchar(10) NOT NULL COMMENT '雇员编号',
  `days` varchar(20) NOT NULL COMMENT '出差天数',
  `cause` varchar(50) NOT NULL COMMENT '出差事由',
  `cost` varchar(10) DEFAULT NULL COMMENT '出差预估费',
  `state` varchar(10) NOT NULL COMMENT '状态',
  `approver` varchar(10) DEFAULT NULL COMMENT '审批人',
  `createdate` datetime DEFAULT NULL COMMENT '创建时间',
  `updatedate` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `plancode` (`plancode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='出差计划表';