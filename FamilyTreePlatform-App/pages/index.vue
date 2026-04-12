<template>
  <view class="content">
    <image class="logo" src="@/static/logo.png"></image>
    <view class="text-area">
      <text class="title">族谱管理平台</text>
    </view>
    
    <view class="menu-container">
      <view class="menu-item" @click="navigateTo('pages/family/person/index')">
        <uni-icon type="person" size="48" color="#409EFF" />
        <text class="menu-text">人员管理</text>
      </view>
      <view class="menu-item" @click="navigateTo('pages/family/household/index')">
        <uni-icon type="home" size="48" color="#67C23A" />
        <text class="menu-text">家庭管理</text>
      </view>
      <view class="menu-item" @click="navigateTo('pages/family/tree/index')">
        <uni-icon type="tree" size="48" color="#E6A23C" />
        <text class="menu-text">族谱管理</text>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  methods: {
    navigateTo(url) {
      uni.navigateTo({
        url: url
      })
    }
  }
}
</script>

<style scoped>
  .content {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  }

  .logo {
    height: 200rpx;
    width: 200rpx;
    margin-top: 100rpx;
    margin-left: auto;
    margin-right: auto;
    margin-bottom: 50rpx;
  }

  .text-area {
    display: flex;
    justify-content: center;
    margin-bottom: 80rpx;
  }

  .title {
    font-size: 36rpx;
    color: #303133;
    font-weight: bold;
  }

  .menu-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
    width: 90%;
  }

  .menu-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin: 20rpx;
    width: 120rpx;
  }

  .menu-text {
    margin-top: 10rpx;
    font-size: 24rpx;
    color: #606266;
  }
</style>
