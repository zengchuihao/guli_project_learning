<template>
  <div class="app-container">
        <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="teacherQuery.name" placeholder="讲师名"/>
      </el-form-item>

      <el-form-item>
        <el-select v-model="teacherQuery.level" clearable placeholder="讲师头衔">
          <el-option :value="1" label="高级讲师"/>
          <el-option :value="2" label="首席讲师"/>
        </el-select>
      </el-form-item>

      <el-form-item label="添加时间">
        <el-date-picker
          v-model="teacherQuery.begin"
          type="datetime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="teacherQuery.end"
          type="datetime"
          placeholder="选择截止时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>  
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="数据加载中"
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

      <el-table-column prop="name" label="名称" width="80" />

      <el-table-column label="头衔" width="80">
        <template slot-scope="scope">
          {{ scope.row.level===1?'高级讲师':'首席讲师' }}
        </template>
      </el-table-column>

      <el-table-column prop="intro" label="资历" />

      <el-table-column prop="gmtCreate" label="添加时间" width="160"/>

      <el-table-column prop="sort" label="排序" width="60" />

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/teacher/save/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
          </router-link>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
        :page-size = "limit"
        :current-page = "page"
        :total = "total"
        small
        layout="prev, pager, next"
        @current-change = "getList"
    >
    </el-pagination>


  </div>
</template>


<script>

    import teacher from '@/api/edu/teacher.js'


    export default{
        data(){
            return{
                list:null,
                page:1,
                limit:4,
                total:0,
                teacherQuery:{}
            }
        },

        created() {
           this.getList() 
        },

        methods:{
            getList(page=1) {
                this.page = page
                teacher.getTeacherListPage(this.page, this.limit, this.teacherQuery)
                .then(respon =>{ 
                    console.log(respon) 
                    this.list = respon.data.rows
                    this.total = respon.data.total
                })
            },

            removeDataById(id) {
                this.$confirm('是否删除该讲师', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'

                }).then(() => {
                    teacher.deleteTeacherById(id)
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                })
            },

            resetData() {
              this.teacherQuery = {}
              this.getList()

            }

        }
            


    }

</script>