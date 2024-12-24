const app = getApp()

Page({
  data: {
    schools: [],
    users: [],
    schoolIndex: -1,
    modalSchoolIndex: -1,
    searchKey: '',
    showModal: false,
    editingUser: null,
    current: 1,
    size: 10,
    total: 0,
    loading: false,
    roles: [
      { id: 'ADMIN', name: '管理员' },
      { id: 'TEACHER', name: '教师' },
      { id: 'PARENT', name: '家长' }
    ],
    roleIndex: -1,
    formData: {
      schoolId: '',
      name: '',
      phone: '',
      role: ''
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
      method: 'GET'
    }).then(data => {
      this.setData({
        schools: data
      })
      if (data.length > 0 && this.data.schoolIndex === -1) {
        this.setData({
          schoolIndex: 0
        })
        this.getUsers(data[0].id)
      }
    })
  },

  getUsers(schoolId) {
    if (this.data.loading) return
    this.setData({ loading: true })
    app.request({
      url: '/admin/user/list',
      method: 'GET',
      data: {
        schoolId,
        name: this.data.searchKey,
        current: this.data.current,
        size: this.data.size
      }
    }).then(data => {
      this.setData({
        users: this.data.current === 1 ? data.records : [...this.data.users, ...data.records],
        total: data.total,
        loading: false
      })
    }).catch(() => {
      this.setData({ loading: false })
    })
  },

  schoolChange(e) {
    const index = e.detail.value
    this.setData({
      schoolIndex: index,
      current: 1,
      users: []
    })
    this.getUsers(this.data.schools[index].id)
  },

  modalSchoolChange(e) {
    const index = e.detail.value
    this.setData({
      modalSchoolIndex: index,
      'formData.schoolId': this.data.schools[index].id
    })
  },

  roleChange(e) {
    const index = e.detail.value
    this.setData({
      roleIndex: index,
      'formData.role': this.data.roles[index].id
    })
  },

  inputSearch(e) {
    this.setData({
      searchKey: e.detail.value
    })
  },

  search() {
    if (this.data.schoolIndex !== -1) {
      this.setData({
        current: 1,
        users: []
      })
      this.getUsers(this.data.schools[this.data.schoolIndex].id)
    }
  },

  showModal() {
    this.setData({
      showModal: true,
      modalSchoolIndex: this.data.schoolIndex,
      roleIndex: -1,
      editingUser: null,
      formData: {
        schoolId: this.data.schools[this.data.schoolIndex].id,
        name: '',
        phone: '',
        role: ''
      }
    })
  },

  closeModal() {
    this.setData({
      showModal: false,
      editingUser: null
    })
  },

  editUser(e) {
    const id = e.currentTarget.dataset.id
    const user = this.data.users.find(item => item.id === id)
    if (user) {
      const roleIndex = this.data.roles.findIndex(item => item.id === user.role)
      const schoolIndex = this.data.schools.findIndex(item => item.id === user.schoolId)
      this.setData({
        showModal: true,
        editingUser: user,
        modalSchoolIndex: schoolIndex,
        roleIndex,
        formData: {
          schoolId: user.schoolId,
          name: user.name,
          phone: user.phone,
          role: user.role
        }
      })
    }
  },

  deleteUser(e) {
    const id = e.currentTarget.dataset.id
    wx.showModal({
      title: '提示',
      content: '确定要删除该用户吗？',
      success: (res) => {
        if (res.confirm) {
          app.request({
            url: `/admin/user/${id}`,
            method: 'DELETE'
          }).then(() => {
            wx.showToast({
              title: '删除成功',
              icon: 'success'
            })
            this.setData({
              current: 1,
              users: []
            })
            this.getUsers(this.data.schools[this.data.schoolIndex].id)
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

  inputPhone(e) {
    this.setData({
      'formData.phone': e.detail.value
    })
  },

  validateForm() {
    const { schoolId, name, phone, role } = this.data.formData
    if (!schoolId) {
      wx.showToast({
        title: '请选择学校',
        icon: 'none'
      })
      return false
    }
    if (!name) {
      wx.showToast({
        title: '请输入用户名称',
        icon: 'none'
      })
      return false
    }
    if (!phone) {
      wx.showToast({
        title: '请输入手机号码',
        icon: 'none'
      })
      return false
    }
    if (!/^1[3-9]\d{9}$/.test(phone)) {
      wx.showToast({
        title: '手机号码格式不正确',
        icon: 'none'
      })
      return false
    }
    if (!role) {
      wx.showToast({
        title: '请选择用户角色',
        icon: 'none'
      })
      return false
    }
    return true
  },

  saveUser() {
    if (!this.validateForm()) return
    const url = this.data.editingUser 
      ? `/admin/user/${this.data.editingUser.id}`
      : '/admin/user'
    const method = this.data.editingUser ? 'PUT' : 'POST'
    
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
        showModal: false,
        current: 1,
        users: []
      })
      this.getUsers(this.data.schools[this.data.schoolIndex].id)
    })
  },

  onReachBottom() {
    if (this.data.users.length >= this.data.total) return
    this.setData({
      current: this.data.current + 1
    })
    this.getUsers(this.data.schools[this.data.schoolIndex].id)
  }
})
