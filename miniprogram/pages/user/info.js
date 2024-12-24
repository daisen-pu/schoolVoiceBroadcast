const app = getApp()

Page({
  data: {
    userInfo: {
      nickName: '',
      phone: '',
      email: '',
      address: ''
    }
  },

  onLoad() {
    this.getUserInfo()
  },

  getUserInfo() {
    app.request({
      url: '/api/user/info',
      method: 'GET'
    }).then(data => {
      this.setData({
        userInfo: data
      })
    })
  },

  inputNickName(e) {
    this.setData({
      'userInfo.nickName': e.detail.value
    })
  },

  inputPhone(e) {
    this.setData({
      'userInfo.phone': e.detail.value
    })
  },

  inputEmail(e) {
    this.setData({
      'userInfo.email': e.detail.value
    })
  },

  inputAddress(e) {
    this.setData({
      'userInfo.address': e.detail.value
    })
  },

  validateForm() {
    const { nickName, phone } = this.data.userInfo
    if (!nickName) {
      wx.showToast({
        title: '请输入昵称',
        icon: 'none'
      })
      return false
    }
    if (!phone) {
      wx.showToast({
        title: '请输入手机号',
        icon: 'none'
      })
      return false
    }
    if (!/^1\d{10}$/.test(phone)) {
      wx.showToast({
        title: '手机号格式不正确',
        icon: 'none'
      })
      return false
    }
    return true
  },

  saveUserInfo() {
    if (!this.validateForm()) return

    app.request({
      url: '/api/user/info',
      method: 'PUT',
      data: this.data.userInfo
    }).then(() => {
      wx.showToast({
        title: '保存成功',
        icon: 'success'
      })
      app.globalData.userInfo = this.data.userInfo
      setTimeout(() => {
        wx.navigateBack()
      }, 1500)
    })
  }
})
