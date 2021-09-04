import request from '@/utils/request'

export default {
  //根据课程id创建订单号
  createOrder(courseId) {
    return request({
      url: `/eduorder/order/createOrder/${courseId}`,
      method: 'post'
    })
  },

  //根据订单号得到该订单的对象信息
  getOrderInfo(orderId) {

    return request({
      url: `/eduorder/order/getOrderInfo/${orderId}`,
      method: 'get'
    })
  },


    //根据订单号生成二维码
    createNative(orderNo) {

      return request({
        url: `/eduorder/pay-log/createPayLog/${orderNo}`,
        method: 'get'
      })
    },

    //查询支付状态
    queryPayState(orderNo) {

      return request({
        url: `/eduorder/pay-log/queryPayState/${orderNo}`,
        method: 'get'
      })
    }




    




}