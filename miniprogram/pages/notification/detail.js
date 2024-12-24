const app = getApp()

Page({
  data: {
    notification: null
  },

  onLoad(options) {
    const id = options.id
    this.getNotificationDetail(id)
  },

  getNotificationDetail(id) {
    app.request({
      url: `/api/notification/${id}`,
      method: 'GET'
    }).then(data => {
      this.setData({
        notification: data
      })
    })
  }
})
