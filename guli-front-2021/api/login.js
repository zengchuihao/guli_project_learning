import request from '@/utils/request'

export default {
  //根据用户信息登录
  login(userInfo) {
    return request({
      url: `/educenter/ucenter-member/login`,
      method: 'post',
      data:userInfo
    })
  },
  //根据登录后返回的token字符串获得用户信息
  getMember() {
    return request({
      url: `/educenter/ucenter-member/getMember`,
      method: 'get'
    })
  }
}