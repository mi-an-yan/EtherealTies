-- ----------------------------
-- 族谱管理平台核心表结构
-- ----------------------------

-- ----------------------------
-- 1、人员表
-- ----------------------------
drop table if exists family_person;
create table family_person (
  id               bigint(20)      not null auto_increment    comment '主键ID',
  name             varchar(50)     not null                   comment '姓名',
  gender           char(1)         not null                   comment '性别（1-男，2-女）',
  birth_date       date                                       comment '出生日期',
  death_date       date                                       comment '逝世日期',
  birth_place      varchar(200)    default ''                 comment '出生地',
  death_place      varchar(200)    default ''                 comment '逝世地',
  description      text                                       comment '个人简介',
  father_id        bigint(20)      default null               comment '父亲ID',
  mother_id        bigint(20)      default null               comment '母亲ID',
  spouse_id        bigint(20)      default null               comment '配偶ID',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  primary key (id),
  key idx_family_person_father (father_id),
  key idx_family_person_mother (mother_id),
  key idx_family_person_spouse (spouse_id)
) engine=innodb auto_increment=1000 comment = '人员表';


-- ----------------------------
-- 2、家庭表
-- ----------------------------
drop table if exists family_household;
create table family_household (
  id               bigint(20)      not null auto_increment    comment '主键ID',
  household_name   varchar(100)    not null                   comment '家庭名称',
  address          varchar(200)    default ''                 comment '家庭地址',
  founding_date    date                                       comment '成立日期',
  description      text                                       comment '家庭简介',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  primary key (id)
) engine=innodb auto_increment=2000 comment = '家庭表';


-- ----------------------------
-- 3、人员-家庭关联表
-- ----------------------------
drop table if exists family_person_household;
create table family_person_household (
  id               bigint(20)      not null auto_increment    comment '主键ID',
  person_id        bigint(20)      not null                   comment '人员ID',
  household_id     bigint(20)      not null                   comment '家庭ID',
  relationship_type varchar(50)    default ''                 comment '关系类型（如：成员、户主等）',
  join_date        date                                       comment '加入日期',
  leave_date       date                                       comment '离开日期',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  primary key (id),
  unique key uk_person_household (person_id, household_id),
  key idx_family_person_household_person (person_id),
  key idx_family_person_household_household (household_id)
) engine=innodb auto_increment=3000 comment = '人员-家庭关联表';


-- ----------------------------
-- 4、族谱表
-- ----------------------------
drop table if exists family_tree;
create table family_tree (
  id               bigint(20)      not null auto_increment    comment '主键ID',
  tree_name        varchar(100)    not null                   comment '族谱名称',
  founder          varchar(50)     default ''                 comment '创始人',
  founding_date    date                                       comment '创建日期',
  description      text                                       comment '族谱简介',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  primary key (id)
) engine=innodb auto_increment=4000 comment = '族谱表';


-- ----------------------------
-- 5、族谱-人员关联表
-- ----------------------------
drop table if exists family_tree_person;
create table family_tree_person (
  id               bigint(20)      not null auto_increment    comment '主键ID',
  tree_id          bigint(20)      not null                   comment '族谱ID',
  person_id        bigint(20)      not null                   comment '人员ID',
  generation       int(4)          default 0                  comment '辈分',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  primary key (id),
  unique key uk_tree_person (tree_id, person_id),
  key idx_family_tree_person_tree (tree_id),
  key idx_family_tree_person_person (person_id),
  key idx_family_tree_person_generation (generation)
) engine=innodb auto_increment=5000 comment = '族谱-人员关联表';


-- ----------------------------
-- 初始化数据
-- ----------------------------

-- 初始化人员数据
insert into family_person values(1000, '张三', '1', '1950-01-01', null, '北京市', null, '家族创始人', null, null, 1001, 'admin', sysdate(), '', null);
insert into family_person values(1001, '李四', '2', '1952-02-01', null, '上海市', null, '张三的妻子', null, null, 1000, 'admin', sysdate(), '', null);
insert into family_person values(1002, '张小明', '1', '1975-03-01', null, '北京市', null, '张三和李四的儿子', 1000, 1001, 1003, 'admin', sysdate(), '', null);
insert into family_person values(1003, '王芳', '2', '1978-04-01', null, '广州市', null, '张小明的妻子', null, null, 1002, 'admin', sysdate(), '', null);
insert into family_person values(1004, '张小小', '1', '2000-05-01', null, '北京市', null, '张小明和王芳的儿子', 1002, 1003, null, 'admin', sysdate(), '', null);

-- 初始化家庭数据
insert into family_household values(2000, '张家', '北京市朝阳区', '1970-01-01', '张氏家族主家庭', 'admin', sysdate(), '', null);

-- 初始化人员-家庭关联数据
insert into family_person_household values(3000, 1000, 2000, '户主', '1970-01-01', null, 'admin', sysdate(), '', null);
insert into family_person_household values(3001, 1001, 2000, '成员', '1970-01-01', null, 'admin', sysdate(), '', null);
insert into family_person_household values(3002, 1002, 2000, '成员', '1975-03-01', null, 'admin', sysdate(), '', null);
insert into family_person_household values(3003, 1003, 2000, '成员', '2000-01-01', null, 'admin', sysdate(), '', null);
insert into family_person_household values(3004, 1004, 2000, '成员', '2000-05-01', null, 'admin', sysdate(), '', null);

-- 初始化族谱数据
insert into family_tree values(4000, '张氏家谱', '张三', '1970-01-01', '张氏家族族谱', 'admin', sysdate(), '', null);

-- 初始化族谱-人员关联数据
insert into family_tree_person values(5000, 4000, 1000, 1, 'admin', sysdate(), '', null);
insert into family_tree_person values(5001, 4000, 1001, 1, 'admin', sysdate(), '', null);
insert into family_tree_person values(5002, 4000, 1002, 2, 'admin', sysdate(), '', null);
insert into family_tree_person values(5003, 4000, 1003, 2, 'admin', sysdate(), '', null);
insert into family_tree_person values(5004, 4000, 1004, 3, 'admin', sysdate(), '', null);