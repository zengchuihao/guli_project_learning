import request from '@/utils/request'

export default {

    indexData() {
        return request({
            url:`/eduservice/indexfront/indexCourseAndTeacher`,
            method:'get'
        })
    }

}