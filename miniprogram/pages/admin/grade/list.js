const app = getApp()

Page({
  data: {
    schools: [],
    grades: [],
    schoolIndex: -1,
    modalSchoolIndex: -1,
    searchKey: '',
    showModal: false,
    editingGrade: null,
    formData: {
      schoolId: '',
      name: '',
      director: '',
      phone: ''
    },
    current: 1,
    size: 10,
    total: 0,
    loading: false
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
        this.getGrades(data[0].id)
      }
    })
  },

  getGrades(schoolId) {
    if (this.data.loading) return
    this.setData({ loading: true })
    app.request({
      url: '/admin/grade/list',
      method: 'GET',
      data: {
        schoolId,
        name: this.data.searchKey,
        current: this.data.current,
        size: this.data.size
      }
    }).then(data => {
      this.setData({
        grades: this.data.current === 1 ? data.records : [...this.data.grades, ...data.records],
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
      grades: []
    })
    this.getGrades(this.data.schools[index].id)
  },

  modalSchoolChange(e) {
    const index = e.detail.value
    this.setData({
      modalSchoolIndex: index,
      'formData.schoolId': this.data.schools[index].id
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
        grades: []
      })
      this.getGrades(this.data.schools[this.data.schoolIndex].id)
    }
  },

  showModal() {
    this.setData({
      showModal: true,
      editingGrade: null,
      modalSchoolIndex: this.data.schoolIndex,
      formData: {
        schoolId: this.data.schools[this.data.schoolIndex].id,
        name: '',
        director: '',
        phone: ''
      }
    })
  },

  closeModal() {
    this.setData({
      showModal: false
    })
  },

  editGrade(e) {
    const id = e.currentTarget.dataset.id
    const grade = this.data.grades.find(item => item.id === id)
    const schoolIndex = this.data.schools.findIndex(item => item.id === grade.schoolId)
    this.setData({
      showModal: true,
      editingGrade: grade,
      modalSchoolIndex: schoolIndex,
      formData: {
        schoolId: grade.schoolId,
        name: grade.name,
        director: grade.director,
        phone: grade.phone
      }
    })
  },

  deleteGrade(e) {
    const id = e.currentTarget.dataset.id
    wx.showModal({
      title: '提示',
      content: '确定要删除该年级吗？删除后相关的班级信息也将被删除。',
      success: (res) => {
        if (res.confirm) {
          app.request({
            url: `/admin/grade/${id}`,
            method: 'DELETE'
          }).then(() => {
            wx.showToast({
              title: '删除成功',
              icon: 'success'
            })
            this.getGrades(this.data.schools[this.data.schoolIndex].id)
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

  inputDirector(e) {
    this.setData({
      'formData.director': e.detail.value
    })
  },

  inputPhone(e) {
    this.setData({
      'formData.phone': e.detail.value
    })
  },

  validateForm() {
    const { schoolId, name, director, phone } = this.data.formData
    if (!schoolId) {
      wx.showToast({
        title: '请选择学校',
        icon: 'none'
      })
      return false
    }
    if (!name) {
      wx.showToast({
        title: '请输入年级名称',
        icon: 'none'
      })
      return false
    }
    if (!director) {
      wx.showToast({
        title: '请输入年级主任',
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

  saveGrade() {
    if (!this.validateForm()) return

    const url = this.data.editingGrade
      ? `/admin/grade/${this.data.editingGrade.id}`
      : '/admin/grade'
    const method = this.data.editingGrade ? 'PUT' : 'POST'

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
      this.getGrades(this.data.schools[this.data.schoolIndex].id)
    })
  },

  onReachBottom() {
    if (this.data.grades.length >= this.data.total) return
    this.setData({
      current: this.data.current + 1
    })
    this.getGrades(this.data.schools[this.data.schoolIndex].id)
  }
})
