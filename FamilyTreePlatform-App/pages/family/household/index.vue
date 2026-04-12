<template>
  <view class="container">
    <uni-nav-bar
      title="家庭管理"
      left-text="返回"
      left-icon="back"
      @clickLeft="onBack"
    />
    
    <view class="search-bar">
      <uni-search-bar
        v-model="searchKeyword"
        placeholder="请输入家庭名称搜索"
        @confirm="onSearch"
      />
    </view>
    
    <uni-list v-if="loading">
      <uni-list-item>
        <uni-load-more status="loading" />
      </uni-list-item>
    </uni-list>
    
    <uni-list v-else-if="householdList.length === 0">
      <uni-list-item title="暂无家庭数据" />
    </uni-list>
    
    <uni-list v-else>
      <uni-list-item
        v-for="household in householdList"
        :key="household.id"
        :title="household.name"
        :subtitle="getHouseholdInfo(household)"
        @click="onHouseholdClick(household)"
      >
        <template v-slot:right>
          <uni-tag type="warning">
            {{ household.memberCount || 0 }}人
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
      @trigger="onAddHousehold"
    />
  </view>
</template>

<script>
import { listHousehold } from '@/api/family/household'

export default {
  data() {
    return {
      searchKeyword: '',
      loading: true,
      loadStatus: 'more',
      hasMore: true,
      pageNum: 1,
      pageSize: 10,
      householdList: []
    }
  },
  onLoad() {
    this.getHouseholdList()
  },
  methods: {
    onBack() {
      uni.navigateBack()
    },
    getHouseholdList() {
      this.loading = true
      const query = {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        name: this.searchKeyword
      }
      listHousehold(query).then(res => {
        this.loading = false
        if (res.code === 200) {
          if (this.pageNum === 1) {
            this.householdList = res.rows
          } else {
            this.householdList = [...this.householdList, ...res.rows]
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
      this.getHouseholdList()
    },
    loadMore() {
      if (this.hasMore && !this.loading) {
        this.pageNum++
        this.loadStatus = 'loading'
        this.getHouseholdList()
      }
    },
    getHouseholdInfo(household) {
      let info = ''
      if (household.address) {
        info += `地址: ${household.address}`
      }
      if (household.householderName) {
        info += ` | 户主: ${household.householderName}`
      }
      return info
    },
    onHouseholdClick(household) {
      uni.navigateTo({
        url: `/pages/family/household/detail?id=${household.id}`
      })
    },
    onAddHousehold() {
      uni.navigateTo({
        url: '/pages/family/household/add'
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