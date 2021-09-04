import request from '@/utils/request'

export default {
  //获取后台的视频播放凭证
  getVideoPlayAuth(vid) {
    return request({
      url: `/eduvod/video/getVideoAuth/${vid}`,
      method: 'get'
    })
  },


}