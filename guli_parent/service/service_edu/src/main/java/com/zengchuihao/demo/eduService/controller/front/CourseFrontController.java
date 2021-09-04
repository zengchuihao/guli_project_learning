package com.zengchuihao.demo.eduService.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zengchuihao.commonutils.JwtUtils;
import com.zengchuihao.commonutils.R;
import com.zengchuihao.commonutils.order.CourseWebOrder;
import com.zengchuihao.demo.eduService.client.OrderClient;
import com.zengchuihao.demo.eduService.entity.EduCourse;
import com.zengchuihao.demo.eduService.entity.EduTeacher;
import com.zengchuihao.demo.eduService.entity.frontVo.CourseFrontVo;
import com.zengchuihao.demo.eduService.entity.frontVo.CourseWebVo;
import com.zengchuihao.demo.eduService.entity.vo.ChapterVo;
import com.zengchuihao.demo.eduService.service.EduChapterService;
import com.zengchuihao.demo.eduService.service.EduCourseService;
import com.zengchuihao.demo.eduService.service.EduTeacherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/coursefront")
public class CourseFrontController {


    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private OrderClient orderClient;

    @PostMapping("getCourseFrontList/{page}/{limit}")
    public R getCourseFrontList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo) {

        Page<EduCourse> pageParams = new Page<>(page, limit);
        Map<String, Object> map =  eduCourseService.getCourseFrontList(pageParams, courseFrontVo);

        return R.ok().data(map);
    }

    //根据课程id获得前台展示的单个课程全部信息
    @GetMapping("getCourseFrontInfo/{courseId}")
    public R getCourseFrontInfo(@PathVariable String courseId, HttpServletRequest request) {

        CourseWebVo courseWebVo = eduCourseService.getCourseFrontById(courseId);

        List<ChapterVo> chapterList = eduChapterService.getChapterList(courseId);

        //使用微服务调用远程的orderController中的方法，返回是否已经购买课程的数据
        boolean isBuyCourse = orderClient.isBuyCourse(courseId, JwtUtils.getMemberIdByJwtToken(request));

        return R.ok().data("courseWebVo", courseWebVo).data("chapterList", chapterList).data("isBuyCourse",isBuyCourse);
    }

    //根据课程id得到课程的前端信息，用于生成订单
    @PostMapping("getCourseOrderInfo/{courseId}")
    public CourseWebOrder getCourseOrderInfo(@PathVariable String courseId) {

        CourseWebVo courseWebVo = eduCourseService.getCourseFrontById(courseId);
        CourseWebOrder courseWebOrder = new CourseWebOrder();
        BeanUtils.copyProperties(courseWebVo, courseWebOrder);
        return courseWebOrder;
    }


}
