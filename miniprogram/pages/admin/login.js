const app = getApp()

Page({
  data: {
    username: '',
    password: ''
  },

  inputUsername(e) {
    this.setData({
      username: e.detail.value
    })
  },

  inputPassword(e) {
    this.setData({
      password: e.detail.value
    })
  },

  login() {
    const { username, password } = this.data
    if (!username || !password) {
      wx.showToast({
        title: '请输入用户名和密码',
        icon: 'none'
      })
      return
    }

    app.request({
      url: '/auth/admin-login',
      method: 'POST',
      data: {
        username,
        password
      }
    }).then(data => {
      app.globalData.token = data.token
      app.globalData.isAdmin = true
      wx.setStorageSync('token', data.token)
      wx.setStorageSync('isAdmin', true)
      wx.showToast({
        title: '登录成功',
        icon: 'success'
      })
      setTimeout(() => {
        wx.redirectTo({
          url: '/pages/admin/index'
        })
      }, 1500)
    })
  }
})
