import request from '@/utils/request'



export default {

    addCourse(courseInfo) {

        return request({
            url:`/eduservice/edu-course/addCourse`,
            method:'post',
            data:courseInfo
        })
    },

    teacherList() {
        
       return request({ 
        url:`/eduservice/edu-teacher/findAll`,
        method:'get'

       })
    },

    getCourseInfo(id) {
        
        return request({ 
         url:`/eduservice/edu-course/getCourseInfoById/`+id,
         method:'get'
        })
     },

     updateCourseInfo(courseInfoVo) {
        
        return request({ 
         url:`/eduservice/edu-course/updateCourseInfo`,
         method:'post',
         data:courseInfoVo
        })
     },
     
    getCoursePublishInfo(courseId) {
        return request({
            url:`/eduservice/edu-course/getCoursePublishInfoVo/`+courseId,
            method:'get'
        })
    },

    publishStatus(courseId) {
        return request({
            url:`/eduservice/edu-course/publishStatus/`+courseId,
            method:'post'
        })
    },

    coursePageCondition(page, limit, courseQuery) {
        return request({
            url:`/eduservice/edu-course/coursePageCondition/${page}/${limit}`,
            method:'post',
            data:courseQuery
        })
    },

    deleteCourse(courseId) {
        return request({
            url:`/eduservice/edu-course/deleteCourse/`+courseId,
            method:'delete'
        })
    }

}

