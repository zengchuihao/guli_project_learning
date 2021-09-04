<template>
  <div class="app-container">
        <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="courseQuery.title" placeholder="课程名称"/>
      </el-form-item>

      <el-form-item>
        <el-select v-model="courseQuery.status" clearable placeholder="课程状态">
          <el-option :value='Normal' label="已发布"/>
          <el-option :value='Draft' label="未发布"/>
        </el-select>
      </el-form-item>

      <el-form-item >
        <el-date-picker
          v-model="courseQuery.begin"
          type="datetime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="courseQuery.end"
          type="datetime"
          placeholder="选择截止时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>

        <el-button type="primary" icon="el-icon-search" @click="getCourseList()">查询</el-button>
        <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>  
    <el-table
      :data="list"
      border
      fit
      highlight-current-row>

      <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="title" label="课程名称" width="80" />

      <el-table-column label="发布状态" width="80">
        <template slot-scope="scope">
          {{ scope.row.status==='Draft'?'未发布':'已发布' }}
        </template>
      </el-table-column>

      <el-table-column prop="lessonNum" label="课时数" />

      <el-table-column prop="gmtCreate" label="添加时间" width="160"/>

      <el-table-column prop="viewCount" label="浏览数量" width="60" />

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/teacher/save/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
          </router-link>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeCourseById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
        :page-size = "limit"
        :current-page = "page"
        :total = "total"
        small
        layout="prev, pager, next"
        @current-change = "getCourseList"
    >
    </el-pagination>


  </div>
</template>


<script>

    import course from '@/api/edu/course'


    export default{
        data(){
            return{
                list:null,
                page:1,
                limit:8,
                total:0,
                courseQuery:{},
                Draft:'Draft',
                Normal:'Normal'
                
            }
        },

        created() {
           this.getCourseList() 
        },

        methods:{


            getCourseList(page=1){
                this.page = page
                course.coursePageCondition(this.page, this.limit, this.courseQuery)
                .then(response => {
                  this.list = response.data.records
                  this.total = response.data.total
                })

            },

            resetData() {
              this.courseQuery = {}
              this.getCourseList()

            },

            removeCourseById(courseId){
                this.$confirm('此操作将删除该课程，是否继续？','提示',{ confirmButtonText: '确定', cancelButtonText:'取消', type:'warning'})
                .then(() =>{
                    course.deleteCourse(courseId)
                    .then(response =>{
                        this.$message({
                          type:'success',
                          message:'删除成功'
                        })
                        
                      this.getCourseList()
                    })
                })
                
               
            }

        }
            


    }

</script>