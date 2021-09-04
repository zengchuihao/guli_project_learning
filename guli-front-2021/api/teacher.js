import request from '@/utils/request'

export default {
  //根据手机号码发送短信
  teacherPageList(page, limit) {
    return request({
      url: `/eduservice/teacherfront/frontteacherlist/${page}/${limit}`,
      method: 'post'
    })
  },

  getFrontTeacherInfo(id) {

    return request({
      url: `/eduservice/teacherfront/getFrontTeacherInfo/${id}`,
      method: 'get'
    })
  }


}