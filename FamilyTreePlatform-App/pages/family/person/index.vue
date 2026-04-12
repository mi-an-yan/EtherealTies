<template>
  <view class="container">
    <uni-nav-bar
      title="人员管理"
      left-text="返回"
      left-icon="back"
      @clickLeft="onBack"
    />
    
    <view class="search-bar">
      <uni-search-bar
        v-model="searchKeyword"
        placeholder="请输入姓名搜索"
        @confirm="onSearch"
      />
    </view>
    
    <view class="filter-bar">
      <uni-segmented-control
        :values="['全部', '男性', '女性']"
        :current="genderFilter"
        @clickItem="onGenderFilterChange"
      />
    </view>
    
    <uni-list v-if="loading">
      <uni-list-item>
        <uni-load-more status="loading" />
      </uni-list-item>
    </uni-list>
    
    <uni-list v-else-if="personList.length === 0">
      <uni-list-item title="暂无人员数据" />
    </uni-list>
    
    <uni-list v-else>
      <uni-list-item
        v-for="person in personList"
        :key="person.id"
        :title="person.name"
        :subtitle="getPersonInfo(person)"
        @click="onPersonClick(person)"
      >
        <template v-slot:right>
          <uni-tag :type="person.gender === 1 ? 'primary' : 'success'">
            {{ person.gender === 1 ? '男' : '女' }}
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
      @trigger="onAddPerson"
    />
  </view>
</template>

<script>
import { listPerson } from '@/api/family/person'

export default {
  data() {
    return {
      searchKeyword: '',
      genderFilter: 0,
      loading: true,
      loadStatus: 'more',
      hasMore: true,
      pageNum: 1,
      pageSize: 10,
      personList: []
    }
  },
  onLoad() {
    this.getPersonList()
  },
  methods: {
    onBack() {
      uni.navigateBack()
    },
    getPersonList() {
      this.loading = true
      const query = {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        name: this.searchKeyword,
        gender: this.genderFilter === 0 ? null : this.genderFilter === 1 ? 1 : 0
      }
      listPerson(query).then(res => {
        this.loading = false
        if (res.code === 200) {
          if (this.pageNum === 1) {
            this.personList = res.rows
          } else {
            this.personList = [...this.personList, ...res.rows]
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
      this.getPersonList()
    },
    onGenderFilterChange(e) {
      this.genderFilter = e.current
      this.pageNum = 1
      this.getPersonList()
    },
    loadMore() {
      if (this.hasMore && !this.loading) {
        this.pageNum++
        this.loadStatus = 'loading'
        this.getPersonList()
      }
    },
    getPersonInfo(person) {
      let info = ''
      if (person.birthDate) {
        info += `出生: ${person.birthDate}`
      }
      if (person.deathDate) {
        info += ` | 去世: ${person.deathDate}`
      }
      if (person.phone) {
        info += ` | ${person.phone}`
      }
      return info
    },
    onPersonClick(person) {
      uni.navigateTo({
        url: `/pages/family/person/detail?id=${person.id}`
      })
    },
    onAddPerson() {
      uni.navigateTo({
        url: '/pages/family/person/add'
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

.filter-bar {
  padding: 10rpx;
  border-bottom: 1rpx solid #e5e5e5;
}
</style>