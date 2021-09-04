package com.zengchuihao.demo.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zengchuihao.demo.eduService.entity.EduTeacher;
import com.zengchuihao.demo.eduService.mapper.EduTeacherMapper;
import com.zengchuihao.demo.eduService.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-07-26
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public Map<String, Object> teacherPageList(Page<EduTeacher> pageParam) {

        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        baseMapper.selectPage(pageParam, wrapper);

        List<EduTeacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        long pages = pageParam.getPages();
        long current = pageParam.getCurrent();
        long size = pageParam.getSize();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        HashMap<String, Object> map = new HashMap<>();
        map.put("records", records);
        map.put("total", total);
        map.put("pages", pages);
        map.put("current", current);
        map.put("size", size);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }
}
