const app = getApp()

Page({
  data: {
    children: [],
    showModal: false,
    editingChildren: null,
    formData: {
      name: '',
      schoolId: '',
      gradeId: '',
      classId: ''
    },
    schools: [],
    grades: [],
    classes: [],
    schoolIndex: -1,
    gradeIndex: -1,
    classIndex: -1
  },

  onLoad() {
    this.getChildren()
    this.getSchools()
  },

  getChildren() {
    app.request({
      url: '/api/children/list',
      method: 'GET'
    }).then(data => {
      this.setData({
        children: data
      })
    })
  },

  getSchools() {
    app.request({
      url: '/api/school/list',
      method: 'GET'
    }).then(data => {
      this.setData({
        schools: data
      })
    })
  },

  getGrades(schoolId) {
    app.request({
      url: '/api/grade/list',
      method: 'GET',
      data: { schoolId }
    }).then(data => {
      this.setData({
        grades: data,
        gradeIndex: -1,
        classes: [],
        classIndex: -1
      })
    })
  },

  getClasses(gradeId) {
    app.request({
      url: '/api/class/list',
      method: 'GET',
      data: { gradeId }
    }).then(data => {
      this.setData({
        classes: data,
        classIndex: -1
      })
    })
  },

  addChildren() {
    this.setData({
      showModal: true,
      editingChildren: null,
      formData: {
        name: '',
        schoolId: '',
        gradeId: '',
        classId: ''
      },
      schoolIndex: -1,
      gradeIndex: -1,
      classIndex: -1
    })
  },

  editChildren(e) {
    const id = e.currentTarget.dataset.id
    const children = this.data.children.find(item => item.id === id)
    this.setData({
      showModal: true,
      editingChildren: children,
      formData: {
        name: children.name,
        schoolId: children.schoolId,
        gradeId: children.gradeId,
        classId: children.classId
      }
    })
    // 设置选中的学校、年级、班级
    const schoolIndex = this.data.schools.findIndex(item => item.id === children.schoolId)
    this.setData({ schoolIndex })
    if (schoolIndex !== -1) {
      this.getGrades(children.schoolId).then(() => {
        const gradeIndex = this.data.grades.findIndex(item => item.id === children.gradeId)
        this.setData({ gradeIndex })
        if (gradeIndex !== -1) {
          this.getClasses(children.gradeId).then(() => {
            const classIndex = this.data.classes.findIndex(item => item.id === children.classId)
            this.setData({ classIndex })
          })
        }
      })
    }
  },

  deleteChildren(e) {
    const id = e.currentTarget.dataset.id
    wx.showModal({
      title: '提示',
      content: '确定要删除该子女信息吗？',
      success: (res) => {
        if (res.confirm) {
          app.request({
            url: `/api/children/${id}`,
            method: 'DELETE'
          }).then(() => {
            wx.showToast({
              title: '删除成功',
              icon: 'success'
            })
            this.getChildren()
          })
        }
      }
    })
  },

  closeModal() {
    this.setData({
      showModal: false
    })
  },

  inputName(e) {
    this.setData({
      'formData.name': e.detail.value
    })
  },

  schoolChange(e) {
    const index = e.detail.value
    const school = this.data.schools[index]
    this.setData({
      schoolIndex: index,
      'formData.schoolId': school.id
    })
    this.getGrades(school.id)
  },

  gradeChange(e) {
    const index = e.detail.value
    const grade = this.data.grades[index]
    this.setData({
      gradeIndex: index,
      'formData.gradeId': grade.id
    })
    this.getClasses(grade.id)
  },

  classChange(e) {
    const index = e.detail.value
    const classItem = this.data.classes[index]
    this.setData({
      classIndex: index,
      'formData.classId': classItem.id
    })
  },

  validateForm() {
    const { name, schoolId, gradeId, classId } = this.data.formData
    if (!name) {
      wx.showToast({
        title: '请输入姓名',
        icon: 'none'
      })
      return false
    }
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
    if (!classId) {
      wx.showToast({
        title: '请选择班级',
        icon: 'none'
      })
      return false
    }
    return true
  },

  saveChildren() {
    if (!this.validateForm()) return

    const url = this.data.editingChildren
      ? `/api/children/${this.data.editingChildren.id}`
      : '/api/children'
    const method = this.data.editingChildren ? 'PUT' : 'POST'

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
      this.getChildren()
    })
  }
})
