const app = getApp()

Page({
  data: {
    stats: {
      schoolCount: 0,
      userCount: 0,
      notificationCount: 0
    }
  },

  onLoad() {
    this.getStatistics()
  },

  onShow() {
    if (!app.globalData.isAdmin) {
      wx.redirectTo({
        url: '/pages/admin/login'
      })
    }
  },

  getStatistics() {
    app.request({
      url: '/admin/statistics',
      method: 'GET'
    }).then(data => {
      this.setData({
        stats: data
      })
    })
  },

  logout() {
    wx.showModal({
      title: '提示',
      content: '确定要退出登录吗？',
      success: (res) => {
        if (res.confirm) {
          app.globalData.token = null
          app.globalData.isAdmin = false
          wx.removeStorageSync('token')
          wx.removeStorageSync('isAdmin')
          wx.redirectTo({
            url: '/pages/admin/login'
          })
        }
      }
    })
  },

  goToSchool() {
    wx.navigateTo({
      url: '/pages/admin/school/list'
    })
  },

  goToGrade() {
    wx.navigateTo({
      url: '/pages/admin/grade/list'
    })
  },

  goToClass() {
    wx.navigateTo({
      url: '/pages/admin/class/list'
    })
  },

  goToUser() {
    wx.navigateTo({
      url: '/pages/admin/user/list'
    })
  },

  goToNotification() {
    wx.navigateTo({
      url: '/pages/admin/notification/list'
    })
  }
})
