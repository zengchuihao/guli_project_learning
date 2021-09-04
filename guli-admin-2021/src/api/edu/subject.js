import request from '@/utils/request'



export default {

    getSubjectList() {

        return request({
            url:`/eduservice/edusubject/getsubjectlist`,
            method:'get'
        })

    }
    

}

