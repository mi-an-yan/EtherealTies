<template>
  <view class="container">
    <uni-nav-bar
      title="族谱管理"
      left-text="返回"
      left-icon="back"
      @clickLeft="onBack"
    />
    
    <view class="search-bar">
      <uni-search-bar
        v-model="searchKeyword"
        placeholder="请输入族谱名称搜索"
        @confirm="onSearch"
      />
    </view>
    
    <uni-list v-if="loading">
      <uni-list-item>
        <uni-load-more status="loading" />
      </uni-list-item>
    </uni-list>
    
    <uni-list v-else-if="treeList.length === 0">
      <uni-list-item title="暂无族谱数据" />
    </uni-list>
    
    <uni-list v-else>
      <uni-list-item
        v-for="tree in treeList"
        :key="tree.id"
        :title="tree.name"
        :subtitle="getTreeInfo(tree)"
        @click="onTreeClick(tree)"
      >
        <template v-slot:right>
          <uni-tag type="success">
            {{ tree.memberCount || 0 }}人
          </uni-tag>
        </template>
      </uni-list-item>
    </uni-list>
    
    <uni-load-more
      v-if="!loading && hasMore"
      :status="loadStatus"
      @clickLoadMore="loadMore"
    />
    
    <uni-fab
      :pattern="['add']"
      :horizontal="'right'"
      :vertical="'bottom'"
      @trigger="onAddTree"
    />
  </view>
</template>

<script>
import { listTree } from '@/api/family/tree'

export default {
  data() {
    return {
      searchKeyword: '',
      loading: true,
      loadStatus: 'more',
      hasMore: true,
      pageNum: 1,
      pageSize: 10,
      treeList: []
    }
  },
  onLoad() {
    this.getTreeList()
  },
  methods: {
    onBack() {
      uni.navigateBack()
    },
    getTreeList() {
      this.loading = true
      const query = {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        name: this.searchKeyword
      }
      listTree(query).then(res => {
        this.loading = false
        if (res.code === 200) {
          if (this.pageNum === 1) {
            this.treeList = res.rows
          } else {
            this.treeList = [...this.treeList, ...res.rows]
          }
          this.hasMore = res.rows.length === this.pageSize
          this.loadStatus = this.hasMore ? 'more' : 'noMore'
        } else {
          uni.showToast({ title: res.msg, icon: 'none' })
        }
      }).catch(err => {
        this.loading = false
        uni.showToast({ title: '网络错误', icon: 'none' })
      })
    },
    onSearch() {
      this.pageNum = 1
      this.getTreeList()
    },
    loadMore() {
      if (this.hasMore && !this.loading) {
        this.pageNum++
        this.loadStatus = 'loading'
        this.getTreeList()
      }
    },
    getTreeInfo(tree) {
      let info = ''
      if (tree.creator) {
        info += `创建人: ${tree.creator}`
      }
      if (tree.createTime) {
        info += ` | 创建时间: ${tree.createTime}`
      }
      return info
    },
    onTreeClick(tree) {
      uni.navigateTo({
        url: `/pages/family/tree/detail?id=${tree.id}`
      })
    },
    onAddTree() {
      uni.navigateTo({
        url: '/pages/family/tree/add'
      })
    }
  }
}
</script>

<style scoped>
.container {
  padding-bottom: 80rpx;
}

.search-bar {
  padding: 10rpx;
  background-color: #f5f5f5;
}
</style>