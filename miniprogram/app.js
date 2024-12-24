App({
  globalData: {
    userInfo: null,
    token: null,
    baseUrl: 'http://localhost:8080'
  },

  onLaunch() {
    // 获取本地存储的token
    const token = wx.getStorageSync('token')
    if (token) {
      this.globalData.token = token
    }
  },

  // 封装请求方法
  request(options) {
    return new Promise((resolve, reject) => {
      wx.request({
        ...options,
        url: this.globalData.baseUrl + options.url,
        header: {
          'content-type': 'application/json',
          'satoken': this.globalData.token
        },
        success: (res) => {
          if (res.data.code === 200) {
            resolve(res.data.data)
          } else if (res.data.code === 401) {
            // token失效，重新登录
            wx.removeStorageSync('token')
            this.globalData.token = null
            wx.navigateTo({
              url: '/pages/index/index'
            })
            reject(res.data.msg)
          } else {
            wx.showToast({
              title: res.data.msg || '请求失败',
              icon: 'none'
            })
            reject(res.data.msg)
          }
        },
        fail: (err) => {
          wx.showToast({
            title: '网络错误',
            icon: 'none'
          })
          reject(err)
        }
      })
    })
  }
})
