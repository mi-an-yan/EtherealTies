# 族谱管理平台实现计划

## 1. 项目现状分析

### 1.1 现有代码结构

当前代码库包含以下项目：
- **RuoYi**：Java后端服务
- **RuoYi-App**：基于uni-app的移动端应用
- **RuoYi-Vue**：基于Vue3的前端项目（包含管理后台）

### 1.2 技术栈确认

- **后端**：Java + Spring Boot（若依框架）
- **前端**：Vue3 + Element Plus
- **移动端**：uni-app
- **数据库**：MySQL 8.0 + Redis

## 2. 项目重命名方案

### 2.1 项目名称

统一命名为 **FamilyTreePlatform**（族谱管理平台）

### 2.2 各端项目命名

| 原项目名称 | 新项目名称 | 说明 |
|-----------|-----------|------|
| RuoYi | FamilyTreePlatform-Server | Java后端服务 |
| RuoYi-App | FamilyTreePlatform-App | uni-app移动端应用 |
| RuoYi-Vue | FamilyTreePlatform-Web | Vue3前端项目（网站端） |
| RuoYi-Vue | FamilyTreePlatform-Admin | Vue3前端项目（管理后台，前后端分离） |

### 2.3 包名和路径调整

- **后端**：将包名从 `com.ruoyi` 改为 `com.familytree.platform`
- **前端**：调整相关配置文件中的项目名称和路径
- **移动端**：调整 uni-app 配置文件中的项目信息

## 3. 数据库设计

### 3.1 核心表结构

#### 3.1.1 人员表（family_person）
| 字段名 | 数据类型 | 说明 |
|--------|----------|------|
| id | BIGINT | 主键ID |
| name | VARCHAR(50) | 姓名 |
| gender | CHAR(1) | 性别（1-男，2-女） |
| birth_date | DATE | 出生日期 |
| death_date | DATE | 逝世日期 |
| birth_place | VARCHAR(200) | 出生地 |
| death_place | VARCHAR(200) | 逝世地 |
| description | TEXT | 个人简介 |
| father_id | BIGINT | 父亲ID |
| mother_id | BIGINT | 母亲ID |
| spouse_id | BIGINT | 配偶ID |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |

#### 3.1.2 家庭表（family_household）
| 字段名 | 数据类型 | 说明 |
|--------|----------|------|
| id | BIGINT | 主键ID |
| household_name | VARCHAR(100) | 家庭名称 |
| address | VARCHAR(200) | 家庭地址 |
| founding_date | DATE | 成立日期 |
| description | TEXT | 家庭简介 |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |

#### 3.1.3 人员-家庭关联表（family_person_household）
| 字段名 | 数据类型 | 说明 |
|--------|----------|------|
| id | BIGINT | 主键ID |
| person_id | BIGINT | 人员ID |
| household_id | BIGINT | 家庭ID |
| relationship_type | VARCHAR(50) | 关系类型（如：成员、户主等） |
| join_date | DATE | 加入日期 |
| leave_date | DATE | 离开日期 |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |

#### 3.1.4 族谱表（family_tree）
| 字段名 | 数据类型 | 说明 |
|--------|----------|------|
| id | BIGINT | 主键ID |
| tree_name | VARCHAR(100) | 族谱名称 |
| founder | VARCHAR(50) | 创始人 |
| founding_date | DATE | 创建日期 |
| description | TEXT | 族谱简介 |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |

#### 3.1.5 族谱-人员关联表（family_tree_person）
| 字段名 | 数据类型 | 说明 |
|--------|----------|------|
| id | BIGINT | 主键ID |
| tree_id | BIGINT | 族谱ID |
| person_id | BIGINT | 人员ID |
| generation | INT | 辈分 |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |

### 3.2 数据初始化

- 导入基础数据
- 创建管理员账户
- 初始化系统配置

## 4. 核心功能模块

### 4.1 后端核心模块

#### 4.1.1 人员管理模块
- 人员信息CRUD
- 亲属关系管理
- 人员搜索和筛选
- 人员导入/导出

#### 4.1.2 家庭管理模块
- 家庭信息CRUD
- 家庭成员管理
- 家庭历史记录

#### 4.1.3 族谱管理模块
- 族谱信息CRUD
- 族谱结构管理
- 族谱可视化数据接口

#### 4.1.4 用户管理模块
- 用户注册/登录
- 权限管理
- 个人信息管理

#### 4.1.5 系统管理模块
- 配置管理
- 日志管理
- 数据备份/恢复

### 4.2 前端功能模块

#### 4.2.1 网站端（FamilyTreePlatform-Web）
- 首页展示
- 族谱浏览
- 人员查询
- 家庭展示
- 用户登录/注册

#### 4.2.2 管理后台（FamilyTreePlatform-Admin，前后端分离）
- 人员管理
- 家庭管理
- 族谱管理
- 用户管理
- 系统设置
- 前后端分离架构，独立部署

### 4.3 移动端功能模块

#### 4.3.1 族谱浏览
- 族谱树状图
- 人员详情
- 亲属关系查看

#### 4.3.2 家庭管理
- 家庭信息
- 成员列表
- 家庭动态

#### 4.3.3 个人中心
- 个人信息
- 我的族谱
- 系统设置

## 5. 项目改造步骤

### 5.1 后端改造
1. 项目重命名和包名调整
2. 数据库表结构设计和创建
3. 核心功能模块开发
4. API接口实现
5. 权限控制配置

### 5.2 前端改造
1. 项目重命名
2. 页面布局调整
3. 核心功能页面开发
4. API调用适配
5. 响应式设计优化

### 5.3 移动端改造
1. 项目重命名
2. 页面结构调整
3. 核心功能实现
4. API调用适配
5. 移动端适配优化

## 6. 技术实现要点

### 6.1 后端技术要点
- 使用Spring Boot和MyBatis-Plus框架
- 实现RESTful API接口
- 集成Redis缓存
- 实现JWT认证
- 配置多环境部署

### 6.2 前端技术要点
- 使用Vue3 + Element Plus
- 实现族谱可视化（使用D3.js或类似库）
- 响应式设计
- 状态管理（Vuex/Pinia）
- 路由权限控制

### 6.3 移动端技术要点
- 使用uni-app框架
- 实现跨平台适配
- 离线数据缓存
- 优化移动端性能
- 适配不同屏幕尺寸

## 7. 部署和运维

### 7.1 开发环境
- JDK 1.8+
- MySQL 8.0+
- Redis 6.0+
- Node.js 14.0+
- Vue CLI 4.0+

### 7.2 部署方案
- 后端：Docker容器化部署
- 前端：静态资源部署（Nginx）
- 移动端：打包为App发布

### 7.3 监控和维护
- 日志监控
- 性能监控
- 定期数据备份
- 安全更新

## 8. 风险评估和应对措施

### 8.1 技术风险
- **风险**：族谱数据结构复杂，关系处理困难
  **应对**：设计合理的数据模型，使用图数据库思想处理复杂关系

- **风险**：前端族谱可视化性能问题
  **应对**：实现分页加载和虚拟滚动，优化渲染性能

- **风险**：移动端数据同步问题
  **应对**：实现离线缓存和增量同步机制

### 8.2 业务风险
- **风险**：数据隐私和安全
  **应对**：实现严格的权限控制和数据加密

- **风险**：用户体验和易用性
  **应对**：进行用户测试，优化界面设计和操作流程

## 9. 项目时间线

1. **准备阶段**：项目重命名和环境搭建
2. **数据库设计**：核心表结构设计和创建
3. **后端开发**：核心功能模块实现
4. **前端开发**：网站端和管理后台实现
5. **移动端开发**：App功能实现
6. **测试阶段**：功能测试和性能优化
7. **部署阶段**：生产环境部署和配置
8. **维护阶段**：系统监控和持续优化

## 10. 结论

本计划基于若依框架，实现一个功能完整的族谱管理平台，包括后端服务、前端网站、管理后台和移动端App。通过合理的项目结构设计和技术选型，确保系统的可扩展性和可维护性。同时，通过详细的风险评估和应对措施，确保项目的顺利实施和稳定运行。