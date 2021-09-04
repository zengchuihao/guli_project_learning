<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

        <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
        <el-step title="填写课程基本信息"/>
        <el-step title="创建课程大纲"/>
        <el-step title="最终发布"/>
        </el-steps>

        <el-form label-width="120px">

        <el-form-item label="课程标题">
            <el-input v-model="courseInfo.title" placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"/>
        </el-form-item>

        <!-- 所属分类 TODO -->
         <el-form-item label="课程分类">
          <el-select
            v-model="courseInfo.subjectParentId"
            placeholder="一级分类" @change="getSecondSubject">
            <el-option
              v-for="subject in firstSubjectList"
              :key="subject.id"
              :label="subject.title"
              :value="subject.id"/>
          </el-select>
           <el-select
            v-model="courseInfo.subjectId"
            placeholder="二级分类">
            <el-option
              v-for="subject in secondSubjectList"
              :key="subject.id"
              :label="subject.title"
              :value="subject.id"/>
          </el-select>

        </el-form-item>

        <!-- 课程讲师 TODO -->
        <el-form-item label="课程讲师">
          <el-select
            v-model="courseInfo.teacherId"
            placeholder="请选择">
            <el-option
              v-for="teacher in teacherList"
              :key="teacher.id"
              :label="teacher.name"
              :value="teacher.id"/>
          </el-select>
        </el-form-item>

        <el-form-item label="总课时">
            <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数"/>
        </el-form-item>

        <!-- 课程简介-->
        <el-form-item label="课程简介">
            <tinymce :height="300" v-model="courseInfo.description"/>
        </el-form-item>

        <!-- 课程封面 TODO -->
        <el-form-item label="课程封面">

          <el-upload
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
            :action="BASE_API+'/eduoss/fileoss/'"
            class="avatar-uploader">
            <img :src="courseInfo.cover">
          </el-upload>

        </el-form-item>
  

        <el-form-item label="课程价格">
            <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元"/> 元
        </el-form-item>

        <el-form-item>
            <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存并下一步</el-button>
        </el-form-item>
        </el-form>
  </div>
</template>
<script>

import course from '@/api/edu/course'
import subject from '@/api/edu/subject'
import Tinymce from '@/components/Tinymce'

export default {

  components: { Tinymce },
  data() {
    return {
      courseInfo:{
            title: '',
            subjectId: '',
            teacherId: '',
            subjectParentId: '',
            lessonNum: 0,
            description: '',
            cover: '/static/1111111111.jpg',
            price: 0
      },
      saveBtnDisabled: false, // 保存按钮是否禁用
      teacherList:[],
      firstSubjectList:[],
      secondSubjectList:[],
      BASE_API:process.env.BASE_API,
      courseId:''
    }
  },

  created() {
    
    this.init()
    //console.log('info created')
   
  },

  watch: {
    $route(to, form) {
     this.init()
    }

  },

  methods: {

     init() {
      if(this.$route.params && this.$route.params.id) {
        this.courseId = this.$route.params.id
        this.getInfo()

      } else {
        this.courseInfo={},
        this.addteacherList()
        this.getFirstSubject()
        this.courseInfo.cover = '/static/1111111111.jpg'
      }

    },

      //获取课程信息
      getInfo() {
        course.getCourseInfo(this.courseId)
        .then(response => {
          this.courseInfo = response.data.info

            subject.getSubjectList()
            .then( response => {

              this.firstSubjectList = response.data.list
              for(var i = 0; i<this.firstSubjectList,length; i++) {
                  var oneSubject = this.firstSubjectList[i]
                  if(this.courseInfo.subjectParentId == oneSubject.id) {
                    this.secondSubjectList = oneSubject.children
                  }
              }

            })

          this.addteacherList()

        })
      },

      //上传封面
      handleAvatarSuccess(res, file) {
        this.courseInfo.cover = res.data.url
      },

      beforeAvatarUpload(file) {
        
        const isJPG = file.type
        const isLt2M = file.size

        if(!isJPG) {
          this.$message.error('上传图片只能是jpg格式');
        }
        if(!isLt2M) {
          this.$message.error('上传图片大学不能大于2m')
        }
        return isJPG && isLt2M



      },


      //获取二级分类
      getSecondSubject(value) {
        //value 一级分类的id
          for(var i = 0; i < this.firstSubjectList.length; i++ ) {
            var oneSubject = this.firstSubjectList[i]
            if(value === oneSubject.id) {
              this.secondSubjectList = oneSubject.children
              //this.courseInfo.subjectId = ''
            }
          }


      },

      //获取一级分类
      getFirstSubject() {
        subject.getSubjectList()
        .then(response => {
          this.firstSubjectList = response.data.list
        })
      },

      addCourse() {

          course.addCourse(this.courseInfo)
          .then(response => {
             this.$message({
                 type:'success',
                 message:'添加课程信息成功'
             })

            this.$router.push({ path: '/course/chapter/'+response.data.courseId})
          })

      },


      updateCourse() {

        course.updateCourseInfo(this.courseInfo)
        .then(response => {
          this.$message({
                 type:'success',
                 message:'修改课程信息成功'
             })

            this.$router.push({ path: '/course/chapter/'+this.courseId})
        })

      },

      saveOrUpdate() {

          if( !this.courseInfo.id ) {
              this.addCourse()
          } else {
            this.updateCourse()
          }

      },

      addteacherList() {
        course.teacherList()
        .then(response => {
          this.teacherList = response.data.items
        })
      }

  }
}
</script>

<style scoped>
.tinymce-container {
  line-height: 29px;
}
</style>