import request from '@/utils/request'



export default {

    getTeacherListPage(current, limit, teacherQuery) {

        return request({
            url: `/eduservice/edu-teacher/pageTeacherCondition/${current}/${limit}`,
            method: 'post',
            data: teacherQuery
          })


    },

    deleteTeacherById(id) {
       return request({
         url:`/eduservice/edu-teacher/${id}`,
         method:'delete'    
       }) 

    },

    saveTeacher(teacher) {
        return request({
            url:`/eduservice/edu-teacher/addTeacher`,
            method:'post',
            data:teacher
        })
    },

    getTeacherById(id) {
        return request({
            url:`/eduservice/edu-teacher/findTeacherById/${id}`,
            method:'get'
        })
    },

    updateTeacher(teacher) {

        return request({
            url:`/eduservice/edu-teacher/updateTeacher`,
            method:'post',
            data:teacher
        })

    }
    

}

