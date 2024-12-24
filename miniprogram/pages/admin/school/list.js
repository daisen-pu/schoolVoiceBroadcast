const app = getApp()

Page({
  data: {
    schools: [],
    searchKey: '',
    showModal: false,
    editingSchool: null,
    formData: {
      name: '',
      address: '',
      phone: ''
    }
  },

  onLoad() {
    this.getSchools()
  },

  onShow() {
    if (!app.globalData.isAdmin) {
      wx.redirectTo({
        url: '/pages/admin/login'
      })
    }
  },

  getSchools() {
    app.request({
      url: '/admin/school/list',
      method: 'GET',
      data: {
        name: this.data.searchKey
      }
    }).then(data => {
      this.setData({
        schools: data
      })
    })
  },

  inputSearch(e) {
    this.setData({
      searchKey: e.detail.value
    })
  },

  search() {
    this.getSchools()
  },

  showModal() {
    this.setData({
      showModal: true,
      editingSchool: null,
      formData: {
        name: '',
        address: '',
        phone: ''
      }
    })
  },

  closeModal() {
    this.setData({
      showModal: false
    })
  },

  editSchool(e) {
    const id = e.currentTarget.dataset.id
    const school = this.data.schools.find(item => item.id === id)
    this.setData({
      showModal: true,
      editingSchool: school,
      formData: {
        name: school.name,
        address: school.address,
        phone: school.phone
      }
    })
  },

  deleteSchool(e) {
    const id = e.currentTarget.dataset.id
    wx.showModal({
      title: '提示',
      content: '确定要删除该学校吗？删除后相关的年级、班级信息也将被删除。',
      success: (res) => {
        if (res.confirm) {
          app.request({
            url: `/admin/school/${id}`,
            method: 'DELETE'
          }).then(() => {
            wx.showToast({
              title: '删除成功',
              icon: 'success'
            })
            this.getSchools()
          })
        }
      }
    })
  },

  inputName(e) {
    this.setData({
      'formData.name': e.detail.value
    })
  },

  inputAddress(e) {
    this.setData({
      'formData.address': e.detail.value
    })
  },

  inputPhone(e) {
    this.setData({
      'formData.phone': e.detail.value
    })
  },

  validateForm() {
    const { name, address, phone } = this.data.formData
    if (!name) {
      wx.showToast({
        title: '请输入学校名称',
        icon: 'none'
      })
      return false
    }
    if (!address) {
      wx.showToast({
        title: '请输入学校地址',
        icon: 'none'
      })
      return false
    }
    if (!phone) {
      wx.showToast({
        title: '请输入联系电话',
        icon: 'none'
      })
      return false
    }
    return true
  },

  saveSchool() {
    if (!this.validateForm()) return

    const url = this.data.editingSchool
      ? `/admin/school/${this.data.editingSchool.id}`
      : '/admin/school'
    const method = this.data.editingSchool ? 'PUT' : 'POST'

    app.request({
      url,
      method,
      data: this.data.formData
    }).then(() => {
      wx.showToast({
        title: '保存成功',
        icon: 'success'
      })
      this.setData({
        showModal: false
      })
      this.getSchools()
    })
  }
})
