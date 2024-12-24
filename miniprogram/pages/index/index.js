const app = getApp()

Page({
  data: {
    userInfo: null,
    hasUserInfo: false,
    notifications: []
  },

  onLoad() {
    if (app.globalData.token) {
      this.getUserInfo()
      this.getNotifications()
    }
  },

  onShow() {
    if (app.globalData.token) {
      this.getNotifications()
    }
  },

  login() {
    wx.login({
      success: (res) => {
        if (res.code) {
          app.request({
            url: '/auth/wx-login',
            method: 'POST',
            data: {
              code: res.code
            }
          }).then(data => {
            app.globalData.token = data.token
            wx.setStorageSync('token', data.token)
            this.getUserInfo()
            this.getNotifications()
          })
        }
      }
    })
  },

  getUserInfo() {
    app.request({
      url: '/api/user/info',
      method: 'GET'
    }).then(data => {
      this.setData({
        userInfo: data,
        hasUserInfo: true
      })
      app.globalData.userInfo = data
    })
  },

  getNotifications() {
    app.request({
      url: '/api/notification/list',
      method: 'GET',
      data: {
        current: 1,
        size: 5
      }
    }).then(data => {
      this.setData({
        notifications: data.records
      })
    })
  },

  goToDetail(e) {
    const id = e.currentTarget.dataset.id
    wx.navigateTo({
      url: `/pages/notification/detail?id=${id}`
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

  goToUserInfo() {
    wx.navigateTo({
      url: '/pages/user/info'
    })
  }
})
