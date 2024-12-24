const app = getApp()

Page({
  data: {
    userInfo: null
  },

  onLoad() {
    if (app.globalData.token) {
      this.getUserInfo()
    }
  },

  onShow() {
    if (app.globalData.token) {
      this.getUserInfo()
    }
  },

  getUserInfo() {
    app.request({
      url: '/api/user/info',
      method: 'GET'
    }).then(data => {
      this.setData({
        userInfo: data
      })
      app.globalData.userInfo = data
    })
  },

  goToUserInfo() {
    wx.navigateTo({
      url: '/pages/user/info'
    })
  },

  goToChildren() {
    wx.navigateTo({
      url: '/pages/user/children'
    })
  },

  goToNotifications() {
    wx.switchTab({
      url: '/pages/notification/list'
    })
  },

  logout() {
    wx.showModal({
      title: '提示',
      content: '确定要退出登录吗？',
      success: (res) => {
        if (res.confirm) {
          app.globalData.token = null
          app.globalData.userInfo = null
          wx.removeStorageSync('token')
          this.setData({
            userInfo: null
          })
          wx.switchTab({
            url: '/pages/index/index'
          })
        }
      }
    })
  }
})
