import request from '@/utils/request'

export default {

    getListBanner() {
        return request({
            url:`/educms/front_banner/getBanner`,
            method:'get'
        })
    }

}