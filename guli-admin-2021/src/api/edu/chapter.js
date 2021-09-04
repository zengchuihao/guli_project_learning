import request from '@/utils/request'



export default {


//=================================================小节操作========================================================


        addVideo(video) {

            return request({
                url:`/eduservice/edu-video/addVideo`,
                method:'post',
                data:video
            })
        },

        deleteVideo(id) {

            return request({
                url:`/eduservice/edu-video/deleteVideo/`+id,
                method:'delete'
            })
        },

        getVideo(id) {   
            return request({
                url:`/eduservice/edu-video/getVideo/`+id,
                method:'get'
            })
        },

        updateVideo(video) {
            return request({
                url:`/eduservice/edu-video/updateVideo`,
                method:'post',
                data:video
            })
        },

        removeAlyVideo(id) {
            return request({
                url:`/eduvod/video/removeAlyVideo/` +id,
                method:'delete'
            })
        },






//=============================================章节操作==================================================

    getSubjectList(courseId) {

        return request({
            url:`/eduservice/edu-chapter/getChapterList/`+courseId,
            method:'post'
        })
    },

    addChapter(chapter) {

        return request({
            url:`/eduservice/edu-chapter/addChapter`,
            method:'post',
            data: chapter
        })
    },

    getChapter(chapterId) {

        return request({
            url:`/eduservice/edu-chapter/getChapter/`+chapterId,
            method:'get',
        })
    },

    updateChapter(chapter) {

        return request({
            url:`/eduservice/edu-chapter/updateChapter`,
            method:'post',
            data: chapter
        })
    },

    deleteChapter(chapterId) {
        return request({
            url:`/eduservice/edu-chapter/deleteChapter/`+chapterId,
            method:'delete',
        })
    },

}