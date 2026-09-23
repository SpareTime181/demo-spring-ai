package com.sparetime.demospringai.service.impl;

import com.sparetime.demospringai.entity.po.Course;
import com.sparetime.demospringai.mapper.CourseMapper;
import com.sparetime.demospringai.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学科表 服务实现类
 * </p>
 *
 * @author SpareTime
 * @since 2026-09-23
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

}
