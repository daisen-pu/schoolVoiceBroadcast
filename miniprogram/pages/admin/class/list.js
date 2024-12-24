const app = getApp()

Page({
  data: {
    schools: [],
    grades: [],
    modalGrades: [],
    classes: [],
    schoolIndex: -1,
    gradeIndex: -1,
    modalSchoolIndex: -1,
    modalGradeIndex: -1,
    searchKey: '',
    showModal: false,
    editingClass: null,
    formData: {
      schoolId: '',
      gradeId: '',
      name: '',
      teacher: '',
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
    app.request({
      url: '/admin/grade/list',
      method: 'GET',
      data: { schoolId }
    }).then(data => {
      this.setData({
        grades: data
      })
      if (data.length > 0 && this.data.gradeIndex === -1) {
        this.setData({
          gradeIndex: 0
        })
        this.getClasses(data[0].id)
      } else if (data.length === 0) {
        this.setData({
          gradeIndex: -1,
          classes: []
        })
      }
    })
  },

  getClasses(gradeId) {
    app.request({
      url: '/admin/class/list',
      method: 'GET',
      data: {
        gradeId,
        name: this.data.searchKey
      }
    }).then(data => {
      this.setData({
        classes: data
      })
    })
  },

  schoolChange(e) {
    const index = e.detail.value
    this.setData({
      schoolIndex: index,
      gradeIndex: -1,
      classes: []
    })
    this.getGrades(this.data.schools[index].id)
  },

  gradeChange(e) {
    const index = e.detail.value
    this.setData({
      gradeIndex: index
    })
    this.getClasses(this.data.grades[index].id)
  },

  modalSchoolChange(e) {
    const index = e.detail.value
    this.setData({
      modalSchoolIndex: index,
      modalGradeIndex: -1,
      modalGrades: [],
      'formData.schoolId': this.data.schools[index].id,
      'formData.gradeId': ''
    })
    // 获取该学校下的年级列表
    app.request({
      url: '/admin/grade/list',
      method: 'GET',
      data: { schoolId: this.data.schools[index].id }
    }).then(data => {
      this.setData({
        modalGrades: data
      })
    })
  },

  modalGradeChange(e) {
    const index = e.detail.value
    this.setData({
      modalGradeIndex: index,
      'formData.gradeId': this.data.modalGrades[index].id
    })
  },

  inputSearch(e) {
    this.setData({
      searchKey: e.detail.value
    })
  },

  search() {
    if (this.data.gradeIndex !== -1) {
      this.getClasses(this.data.grades[this.data.gradeIndex].id)
    }
  },

  showModal() {
    this.setData({
      showModal: true,
      editingClass: null,
      modalSchoolIndex: this.data.schoolIndex,
      modalGradeIndex: this.data.gradeIndex,
      modalGrades: this.data.grades,
      formData: {
        schoolId: this.data.schools[this.data.schoolIndex].id,
        gradeId: this.data.grades[this.data.gradeIndex].id,
        name: '',
        teacher: '',
        phone: ''
      }
    })
  },

  closeModal() {
    this.setData({
      showModal: false
    })
  },

  editClass(e) {
    const id = e.currentTarget.dataset.id
    const classItem = this.data.classes.find(item => item.id === id)
    const schoolIndex = this.data.schools.findIndex(item => item.id === classItem.schoolId)
    
    // 先获取该学校下的年级列表
    app.request({
      url: '/admin/grade/list',
      method: 'GET',
      data: { schoolId: classItem.schoolId }
    }).then(data => {
      const gradeIndex = data.findIndex(item => item.id === classItem.gradeId)
      this.setData({
        showModal: true,
        editingClass: classItem,
        modalSchoolIndex: schoolIndex,
        modalGrades: data,
        modalGradeIndex: gradeIndex,
        formData: {
          schoolId: classItem.schoolId,
          gradeId: classItem.gradeId,
          name: classItem.name,
          teacher: classItem.teacher,
          phone: classItem.phone
        }
      })
    })
  },

  deleteClass(e) {
    const id = e.currentTarget.dataset.id
    wx.showModal({
      title: '提示',
      content: '确定要删除该班级吗？',
      success: (res) => {
        if (res.confirm) {
          app.request({
            url: `/admin/class/${id}`,
            method: 'DELETE'
          }).then(() => {
            wx.showToast({
              title: '删除成功',
              icon: 'success'
            })
            this.getClasses(this.data.grades[this.data.gradeIndex].id)
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

  inputTeacher(e) {
    this.setData({
      'formData.teacher': e.detail.value
    })
  },

  inputPhone(e) {
    this.setData({
      'formData.phone': e.detail.value
    })
  },

  validateForm() {
    const { schoolId, gradeId, name, teacher, phone } = this.data.formData
    if (!schoolId) {
      wx.showToast({
        title: '请选择学校',
        icon: 'none'
      })
      return false
    }
    if (!gradeId) {
      wx.showToast({
        title: '请选择年级',
        icon: 'none'
      })
      return false
    }
    if (!name) {
      wx.showToast({
        title: '请输入班级名称',
        icon: 'none'
      })
      return false
    }
    if (!teacher) {
      wx.showToast({
        title: '请输入班主任',
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

  saveClass() {
    if (!this.validateForm()) return

    const url = this.data.editingClass
      ? `/admin/class/${this.data.editingClass.id}`
      : '/admin/class'
    const method = this.data.editingClass ? 'PUT' : 'POST'

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
      this.getClasses(this.data.grades[this.data.gradeIndex].id)
    })
  }
})
