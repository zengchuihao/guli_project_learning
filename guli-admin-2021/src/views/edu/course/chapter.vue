<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="2" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="最终发布"/>
    </el-steps>



    <el-button type="text" @click="openChapterDialog()">添加章节</el-button>
    <!-- 章节 -->
    <ul class="chanpterList">
        <li
            v-for="chapter in SubjectList"
            :key="chapter.id">
            <p>
                {{ chapter.title }}

                <span class="acts">
                    <el-button type="" @click="oppenVideo(chapter.id)">添加课时</el-button>
                    <el-button style="" type="text" @click="editChapter(chapter.id)">编辑</el-button>
                    <el-button type="text" @click="removeChapter(chapter.id)">删除</el-button>
                </span>
            </p>

            <!-- 视频 -->
            <ul class="chanpterList videoList">
                <li
                    v-for="video in chapter.children"
                    :key="video.id">
                    <p>{{ video.title }}
                        <span class="acts">
                            <el-button type="text" @click="modifyVideo(video.id)">编辑</el-button>
                            <el-button type="text" @click="removeVideo(video.id)">删除</el-button>
                        </span>
                    </p>
                </li>
            </ul>
        </li>
    </ul>
    <div>
        <el-button @click="previous">上一步</el-button>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="next">下一步</el-button>
    </div>


    <!-- 添加和修改章节表单 -->
    <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
        <el-form :model="chapter" label-width="120px">
            <el-form-item label="章节标题">
                <el-input v-model="chapter.title"/>
            </el-form-item>
            <el-form-item label="章节排序">
                <el-input-number v-model="chapter.sort" :min="0" controls-position="right"/>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
        </div>
    </el-dialog>


    <!-- 添加和修改课时表单 -->
    <el-dialog :visible.sync="dialogVideoFormVisible" title="添加课时">
      <el-form :model="video" label-width="120px">
        <el-form-item label="课时标题">
          <el-input v-model="video.title"/>
        </el-form-item>
        <el-form-item label="课时排序">
          <el-input-number v-model="video.sort" :min="0" controls-position="right"/>
        </el-form-item>
        <el-form-item label="是否免费">
          <el-radio-group v-model="video.free">
            <el-radio :label="true">免费</el-radio>
            <el-radio :label="false">默认</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="上传视频">
            <el-upload
                  :on-success="handleVodUploadSuccess"
                  :on-remove="handleVodRemove"
                  :before-remove="beforeVodRemove"
                  :on-exceed="handleUploadExceed"
                  :file-list="fileList"
                  :action="BASE_API+'/eduvod/video/uploadVideo'"
                  :limit="1"
                  class="upload-demo">
              <el-button size="small" type="primary">上传视频</el-button>
              <el-tooltip placement="right-end">
                  <div slot="content">最大支持1G，<br>
                      支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
                      GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
                      MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
                      SWF、TS、VOB、WMV、WEBM 等视频格式上传</div>
                  <i class="el-icon-question"/>
              </el-tooltip>
            </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button :disabled="saveVideoBtnDisabled" type="primary" @click="saveOrUpdateVideo">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

import chapter from '@/api/edu/chapter'


export default {
  data() {
    return {
      saveBtnDisabled: false, // 保存按钮是否禁用
      SubjectList:[],
      courseId:'',
      chapter:{
        title:'',
        sort:0
      },
      video:{
        titel:'',
        sort:0,
        free:0,
        videoSourceId:'',
        videoOriginalName:''
      },
      dialogChapterFormVisible:false,  //添加章节弹框按钮
      saveVideoBtnDisabled:false,
      dialogVideoFormVisible:false ,   //添加课时对话框按钮
      fileList: [],//上传文件列表
      BASE_API: process.env.BASE_API // 接口API地址
    }
  },

  created() {
    //console.log('chapter created')
    if(this.$route.params && this.$route.params.id) {
      this.courseId = this.$route.params.id
      this.getSubjectList()
    }

  },




 
 methods: {

//==============================================课时操作方法=======================================================


        beforeVodRemove(file, fileList) {
          return this.$confirm(`确定删除${file.name}？`)
        },

        handleVodRemove() {
            chapter.removeAlyVideo(this.video.videoSourceId)
                .then(response =>{
                  this.$message({
                    type:'success',
                    message:'删除视频成功'
                  })

                  this.fileList=[]
                  this.video.videoSourceId=''
                  this.video.videoOriginalName=''
                })
        },

        handleVodUploadSuccess(response, file, fileList) {
          this.video.videoSourceId = response.data.videoId
          this.video.videoOriginalName=file.name
        },
        //视图上传多于一个视频
        handleUploadExceed(files, fileList) {
          this.$message.warning('想要重新上传视频，请先删除已上传的视频')
        },

      modifyVideo(id) {
          this.dialogVideoFormVisible = true
          chapter.getVideo(id)
          .then(response => {
            this.video = response.data.video
          })
      },


      updateVideo() {
          chapter.updateVideo(this.video)
            .then(response => {
              this.dialogVideoFormVisible = false
              this.$message({
              type:'success',
              message:'修改小节成功'
            })
              this.getSubjectList()
          })
      },



      removeVideo(id){

          this.$confirm('此操作将删除该小节，是否继续？','提示',{ confirmButtonText: '确定', cancelButtonText:'取消', type:'warning'})
          .then(() => {

            chapter.deleteVideo(id)
            .then(response => {
                this.dialogVideoFormVisible = false
                this.$message({
                   type:'success',
                   message:'删除小节成功'
                 })
              this.getSubjectList()
            })
            
          })  
      },


      oppenVideo(chapterId) {
          
          this.dialogVideoFormVisible = true
          //this.saveVideoBtnDisabled=true
          this.video.chapterId = chapterId
          this.video.titel=''
          this.video.sort=0
          this.video.free=0
          this.video.videoSourceId=''
          
      },

      saveOrUpdateVideo() {
          if(!this.video.id){
             this.addVideo()
          } else {
            this.updateVideo()
          }
      },


      addVideo() {

          this.video.courseId = this.courseId
          chapter.addVideo(this.video)
          .then(response => {
            this.dialogVideoFormVisible = false
            this.$message({
              type:'success',
              message:'添加小节成功'
            })
             this.getSubjectList()
          })
        

      },








//==============================================章节操作方法=======================================================

    //删除功能章节功能

    removeChapter(chapterId) {

        this.$confirm('此操作将删除该章节，是否继续？','提示',{ confirmButtonText: '确定', cancelButtonText:'取消', type:'warning'})
        .then(() => {

            chapter.deleteChapter(chapterId)
            .then(response => {

                this.$message({
                  type:'success',
                  message:'删除成功'
                })

                this.getSubjectList()

            })

        })

    },


    updateChapter() {

        chapter.updateChapter (this.chapter)
        .then( response => {
           this.dialogChapterFormVisible = false
            this.$message({
            type:'success',
            message:'修改章节成功'
          })

          this.getSubjectList()
        }) 
    },

    addChapter() {
        this.chapter.courseId = this.courseId
        chapter.addChapter(this.chapter)
        .then(response => {
            this.dialogChapterFormVisible = false
            this.$message({
              type:'success',
              message:'添加章节成功'
            })

            this.getSubjectList()

        })
    },

    editChapter(chapterId) {

        this.dialogChapterFormVisible = true
        chapter.getChapter(chapterId)
          .then(response => {
              this.chapter = response.data.chapter
          })

    },

    saveOrUpdate() {

        if(!this.chapter.id) {
          this.addChapter()
        } else {
          this.updateChapter()
        }

    },

    getSubjectList() {
      chapter.getSubjectList(this.courseId)
      .then(response => {
          this.SubjectList = response.data.list
      })
    },

    previous() {
      //console.log('previous')
      this.$router.push({ path: '/course/info/'+this.courseId})
    },

    next() {
      //console.log('next')
      this.$router.push({ path: '/course/publish/'+this.courseId})
    },

    openChapterDialog() {
      this.dialogChapterFormVisible = true
      this.chapter.title = ''
      this.chapter.sort = 0
    }
  }
}
</script>

<style scoped>
.chanpterList{
    position: relative;
    list-style: none;
    margin: 0;
    padding: 0;
}
.chanpterList li{
  position: relative;
}
.chanpterList p{
  float: left;
  font-size: 20px;
  margin: 10px 0;
  padding: 10px;
  height: 70px;
  line-height: 50px;
  width: 100%;
  border: 1px solid #DDD;
}
.chanpterList .acts {
    float: right;
    font-size: 14px;
}

.videoList{
  padding-left: 50px;
}
.videoList p{
  float: left;
  font-size: 14px;
  margin: 10px 0;
  padding: 10px;
  height: 50px;
  line-height: 30px;
  width: 100%;
  border: 1px dotted #DDD;
}

</style>