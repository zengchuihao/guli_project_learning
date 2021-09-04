import request from '@/utils/request'

export default {
  //查询前端的条件查询带分页
  getCourseFront(page, limit, searchObj) {
    return request({
      url: `/eduservice/coursefront/getCourseFrontList/${page}/${limit}`,
      method: 'post',
      data:searchObj
    })
  },

  //查询所有的一二级分类
  getAllSubject() {

    return request({
      url: `/eduService/edusubject/getsubjectlist`,
      method: 'get'
    })
  },

    //根据课程id查询该课程的全部相关信息
    getCourseFrontInfo(courseId) {

      return request({
        url: `/eduservice/coursefront/getCourseFrontInfo/${courseId}`,
        method: 'get'
      })
    },


}