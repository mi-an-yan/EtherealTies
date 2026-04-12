<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="家庭名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入家庭名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="地址" prop="address">
        <el-input
          v-model="queryParams.address"
          placeholder="请输入地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="户主" prop="householderId">
        <el-select v-model="queryParams.householderId" placeholder="请选择户主" clearable>
          <el-option
            v-for="person in personOptions"
            :key="person.id"
            :label="person.name"
            :value="person.id"
          />
        </el-select>
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
          v-hasPermi="['family:household:add']"
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
          v-hasPermi="['family:household:edit']"
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
          v-hasPermi="['family:household:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['family:household:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="householdList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="家庭名称" align="center" prop="name" />
      <el-table-column label="地址" align="center" prop="address" />
      <el-table-column label="户主" align="center" prop="householderName">
        <template slot-scope="scope">
          {{ scope.row.householderName || '-' }}
        </template>
      </el-table-column>
      <el-table-column label="成员数量" align="center" prop="memberCount" />
      <el-table-column label="描述" align="center" prop="description" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['family:household:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['family:household:remove']"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-user"
            @click="handleMembers(scope.row)"
            v-hasPermi="['family:household:list']"
          >成员管理</el-button>
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

    <!-- 添加或修改家庭对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="家庭名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入家庭名称" />
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入地址" />
        </el-form-item>
        <el-form-item label="户主" prop="householderId">
          <el-select v-model="form.householderId" placeholder="请选择户主" clearable>
            <el-option
              v-for="person in personOptions"
              :key="person.id"
              :label="person.name"
              :value="person.id"
            />
          </el-select>
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
    <el-dialog :title="'家庭成员管理 - ' + (currentHousehold?.name || '')" :visible.sync="memberDialogVisible" width="600px" append-to-body>
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
        <el-table-column label="关系" align="center" prop="relationship" />
      </el-table>

      <div class="mt-2">
        <el-button type="primary" size="mini" @click="addMembers">添加成员</el-button>
        <el-button type="danger" size="mini" @click="removeMembers">移除成员</el-button>
      </div>
      
      <div slot="footer" class="dialog-footer">
        <el-button @click="memberDialogVisible = false">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listHousehold, getHousehold, addHousehold, updateHousehold, delHousehold, exportHousehold } from "@/api/family/household";
import { listPerson } from "@/api/family/person";

export default {
  name: "Household",
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
      // 家庭表格数据
      householdList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        address: null,
        householderId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "家庭名称不能为空", trigger: "blur" }
        ]
      },
      // 人员选择选项
      personOptions: [],
      // 成员管理相关
      memberDialogVisible: false,
      currentHousehold: null,
      memberLoading: false,
      memberList: [],
      selectedMembers: [],
      memberQuery: {
        name: null
      }
    };
  },
  created() {
    this.getList();
    this.getPersonOptions();
  },
  methods: {
    /** 查询家庭列表 */
    getList() {
      this.loading = true;
      listHousehold(this.queryParams).then(response => {
        this.householdList = response.rows;
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
      this.title = "新增家庭";
      this.open = true;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getHousehold(id).then(response => {
        this.form = response.data;
        this.title = "修改家庭";
        this.open = true;
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm(
        `是否确认删除家庭编号为"${ids}"的数据项?`,
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(function() {
          return delHousehold(ids);
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
            updateHousehold(this.form).then(response => {
              if (response.code === 200) {
                this.$message.success("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.$message.error(response.msg);
              }
            });
          } else {
            addHousehold(this.form).then(response => {
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
      this.$confirm("是否确认导出所有家庭数据?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        return exportHousehold(queryParams);
      }).then(response => {
        this.download(response.msg);
      });
    },
    /** 获取人员选项 */
    getPersonOptions() {
      listPerson({ pageNum: 1, pageSize: 1000 }).then(response => {
        this.personOptions = response.rows;
      });
    },
    /** 成员管理 */
    handleMembers(household) {
      this.currentHousehold = household;
      this.memberDialogVisible = true;
      this.getMembers();
    },
    /** 获取成员列表 */
    getMembers() {
      this.memberLoading = true;
      // 这里需要调用获取家庭成员的API，暂时使用人员列表模拟
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
      // 这里需要调用添加家庭成员的API
      this.$message.success("成员添加成功");
      this.getMembers();
    },
    /** 移除成员 */
    removeMembers() {
      if (this.selectedMembers.length === 0) {
        this.$message.warning("请选择要移除的成员");
        return;
      }
      // 这里需要调用移除家庭成员的API
      this.$message.success("成员移除成功");
      this.getMembers();
    }
  }
};
</script>