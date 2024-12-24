const app = getApp()

Page({
  data: {
    notifications: [],
    current: 1,
    size: 10,
    hasMore: true
  },

  onLoad() {
    this.getNotifications()
  },

  onPullDownRefresh() {
    this.setData({
      notifications: [],
      current: 1,
      hasMore: true
    })
    this.getNotifications().then(() => {
      wx.stopPullDownRefresh()
    })
  },

  getNotifications() {
    if (!this.data.hasMore) return Promise.resolve()

    return app.request({
      url: '/api/notification/list',
      method: 'GET',
      data: {
        current: this.data.current,
        size: this.data.size
      }
    }).then(data => {
      const notifications = [...this.data.notifications, ...data.records]
      this.setData({
        notifications,
        hasMore: notifications.length < data.total
      })
      if (this.data.hasMore) {
        this.setData({
          current: this.data.current + 1
        })
      }
    })
  },

  loadMore() {
    this.getNotifications()
  },

  goToDetail(e) {
    const id = e.currentTarget.dataset.id
    wx.navigateTo({
      url: `/pages/notification/detail?id=${id}`
    })
  }
})
