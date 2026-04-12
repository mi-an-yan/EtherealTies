<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="族谱名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入族谱名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建人" prop="creator">
        <el-input
          v-model="queryParams.creator"
          placeholder="请输入创建人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['family:tree:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['family:tree:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['family:tree:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['family:tree:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-view"
          size="mini"
          :disabled="single"
          @click="handleVisualize"
          v-hasPermi="['family:tree:view']"
        >可视化</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="treeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="族谱名称" align="center" prop="name" />
      <el-table-column label="创建人" align="center" prop="creator" />
      <el-table-column label="创建时间" align="center" prop="createTime" />
      <el-table-column label="成员数量" align="center" prop="memberCount" />
      <el-table-column label="描述" align="center" prop="description" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['family:tree:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['family:tree:remove']"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-user"
            @click="handleMembers(scope.row)"
            v-hasPermi="['family:tree:list']"
          >成员管理</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleVisualize(scope.row)"
            v-hasPermi="['family:tree:view']"
          >可视化</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改族谱对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="族谱名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入族谱名称" />
        </el-form-item>
        <el-form-item label="创建人" prop="creator">
          <el-input v-model="form.creator" placeholder="请输入创建人" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入描述" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 成员管理对话框 -->
    <el-dialog :title="'族谱成员管理 - ' + (currentTree?.name || '')" :visible.sync="memberDialogVisible" width="600px" append-to-body>
      <el-form :model="memberQuery" ref="memberQueryForm" size="small" :inline="true" label-width="68px">
        <el-form-item label="姓名" prop="name">
          <el-input
            v-model="memberQuery.name"
            placeholder="请输入姓名"
            clearable
            @keyup.enter.native="getMembers"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="getMembers">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetMemberQuery">重置</el-button>
        </el-form-item>
      </el-form>
      
      <el-table v-loading="memberLoading" :data="memberList" @selection-change="handleMemberSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="编号" align="center" prop="id" />
        <el-table-column label="姓名" align="center" prop="name" />
        <el-table-column label="性别" align="center" prop="gender">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.gender == 1" type="primary">男</el-tag>
            <el-tag v-else type="success">女</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="出生日期" align="center" prop="birthDate" />
        <el-table-column label="辈分" align="center" prop="generation" />
      </el-table>

      <div class="mt-2">
        <el-button type="primary" size="mini" @click="addMembers">添加成员</el-button>
        <el-button type="danger" size="mini" @click="removeMembers">移除成员</el-button>
      </div>
      
      <div slot="footer" class="dialog-footer">
        <el-button @click="memberDialogVisible = false">关闭</el-button>
      </div>
    </el-dialog>

    <!-- 族谱可视化对话框 -->
    <el-dialog :title="'族谱可视化 - ' + (currentTree?.name || '')" :visible.sync="visualizeDialogVisible" width="90%" height="80%" append-to-body>
      <div v-loading="visualizeLoading" class="visualize-container">
        <div ref="visualizeContainer" class="visualize-content"></div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="visualizeDialogVisible = false">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listTree, getTree, addTree, updateTree, delTree, exportTree, getTreeVisualization } from "@/api/family/tree";
import { listPerson } from "@/api/family/person";

export default {
  name: "Tree",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 族谱表格数据
      treeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        creator: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "族谱名称不能为空", trigger: "blur" }
        ],
        creator: [
          { required: true, message: "创建人不能为空", trigger: "blur" }
        ]
      },
      // 成员管理相关
      memberDialogVisible: false,
      currentTree: null,
      memberLoading: false,
      memberList: [],
      selectedMembers: [],
      memberQuery: {
        name: null
      },
      // 可视化相关
      visualizeDialogVisible: false,
      visualizeLoading: false,
      visualizeData: null
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询族谱列表 */
    getList() {
      this.loading = true;
      listTree(this.queryParams).then(response => {
        this.treeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {};
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.title = "新增族谱";
      this.open = true;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getTree(id).then(response => {
        this.form = response.data;
        this.title = "修改族谱";
        this.open = true;
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm(
        `是否确认删除族谱编号为"${ids}"的数据项?`,
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(function() {
          return delTree(ids);
        })
        .then(() => {
          this.getList();
          this.$message.success("删除成功");
        })
        .catch(() => {});
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateTree(this.form).then(response => {
              if (response.code === 200) {
                this.$message.success("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.$message.error(response.msg);
              }
            });
          } else {
            addTree(this.form).then(response => {
              if (response.code === 200) {
                this.$message.success("新增成功");
                this.open = false;
                this.getList();
              } else {
                this.$message.error(response.msg);
              }
            });
          }
        }
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有族谱数据?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        return exportTree(queryParams);
      }).then(response => {
        this.download(response.msg);
      });
    },
    /** 成员管理 */
    handleMembers(tree) {
      this.currentTree = tree;
      this.memberDialogVisible = true;
      this.getMembers();
    },
    /** 获取成员列表 */
    getMembers() {
      this.memberLoading = true;
      // 这里需要调用获取族谱成员的API，暂时使用人员列表模拟
      listPerson(this.memberQuery).then(response => {
        this.memberList = response.rows;
        this.memberLoading = false;
      });
    },
    /** 重置成员查询 */
    resetMemberQuery() {
      this.resetForm("memberQueryForm");
      this.getMembers();
    },
    /** 成员选择 */
    handleMemberSelectionChange(selection) {
      this.selectedMembers = selection;
    },
    /** 添加成员 */
    addMembers() {
      if (this.selectedMembers.length === 0) {
        this.$message.warning("请选择要添加的成员");
        return;
      }
      // 这里需要调用添加族谱成员的API
      this.$message.success("成员添加成功");
      this.getMembers();
    },
    /** 移除成员 */
    removeMembers() {
      if (this.selectedMembers.length === 0) {
        this.$message.warning("请选择要移除的成员");
        return;
      }
      // 这里需要调用移除族谱成员的API
      this.$message.success("成员移除成功");
      this.getMembers();
    },
    /** 可视化 */
    handleVisualize(row) {
      this.currentTree = row;
      this.visualizeDialogVisible = true;
      this.getVisualizationData();
    },
    /** 获取可视化数据 */
    getVisualizationData() {
      this.visualizeLoading = true;
      getTreeVisualization(this.currentTree.id).then(response => {
        this.visualizeData = response.data;
        this.visualizeLoading = false;
        this.renderVisualization();
      });
    },
    /** 渲染可视化 */
    renderVisualization() {
      if (!this.visualizeData) return;
      
      // 这里需要集成可视化库，如d3.js或echarts
      // 暂时使用简单的DOM操作模拟
      const container = this.$refs.visualizeContainer;
      container.innerHTML = `
        <div style="padding: 20px;">
          <h3>族谱可视化</h3>
          <p>成员数量: ${this.visualizeData.nodes?.length || 0}</p>
          <p>关系数量: ${this.visualizeData.links?.length || 0}</p>
          <div style="margin-top: 20px;">
            <h4>成员列表</h4>
            <ul>
              ${this.visualizeData.nodes?.map(node => `<li>${node.name} (${node.gender === 1 ? '男' : '女'})</li>`).join('') || '无成员'}
            </ul>
          </div>
        </div>
      `;
    }
  }
};
</script>

<style scoped>
.visualize-container {
  height: 600px;
  overflow: auto;
}

.visualize-content {
  width: 100%;
  height: 100%;
}
</style>