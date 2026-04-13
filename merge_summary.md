此次合并主要实现了家族树平台的核心功能，包括族谱管理、成员管理、家庭户管理等模块，并提供了Web端和移动端的完整实现。同时更新了服务器端配置和数据模型，支持族谱可视化功能。

| 文件 | 变更 |
|------|---------|
| FamilyTreePlatform-Server/ruoyi-admin/src/main/resources/application.yml | - 新增家族树平台相关配置<br>- 添加Springdoc配置支持API文档<br>- 配置XSS和CSRF防护 |
| FamilyTreePlatform-Server/ruoyi-admin/src/main/java/com/familytree/platform/web/controller/system/FamilyTreeController.java | - 实现族谱管理的完整CRUD操作<br>- 支持族谱成员管理和关联操作<br>- 提供族谱可视化数据接口 |
| FamilyTreePlatform-Web/ruoyi-ui/src/views/family/tree/index.vue | - 实现族谱列表展示和搜索功能<br>- 支持族谱的新增、编辑、删除操作<br>- 集成族谱成员管理功能<br>- 提供族谱可视化界面 |
| FamilyTreePlatform-Web/ruoyi-ui/src/api/family/tree.js | - 新增族谱相关API调用函数<br>- 支持族谱数据的增删改查操作 |
| FamilyTreePlatform-App/api/family/tree.js | - 新增移动端族谱API调用函数<br>- 支持移动应用的族谱操作 |
| FamilyTreePlatform-Server/ruoyi-system/src/main/java/com/familytree/platform/system/domain/FamilyTree.java | - 定义族谱数据模型<br>- 包含族谱基本信息和关联关系 |
| FamilyTreePlatform-Server/ruoyi-system/src/main/java/com/familytree/platform/system/service/IFamilyTreeService.java | - 定义族谱服务接口<br>- 包含族谱管理和成员管理方法 |
| FamilyTreePlatform-Server/ruoyi-system/src/main/java/com/familytree/platform/system/service/impl/FamilyTreeServiceImpl.java | - 实现族谱服务的具体业务逻辑<br>- 支持族谱数据的增删改查和成员管理 |
| FamilyTreePlatform-Web/ruoyi-ui/src/views/family/person/index.vue | - 实现家庭成员列表展示和管理<br>- 支持成员信息的新增、编辑、删除操作 |
| FamilyTreePlatform-Web/ruoyi-ui/src/views/family/household/index.vue | - 实现家庭户管理功能<br>- 支持家庭户信息的增删改查操作 |
| FamilyTreePlatform-App/pages/family/tree/index.vue | - 实现移动端族谱管理界面<br>- 支持移动设备上的族谱操作