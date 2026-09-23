package com.sparetime.demospringai.tool;

import com.sparetime.demospringai.entity.po.Course;
import com.sparetime.demospringai.entity.query.CourseQuery;
import com.sparetime.demospringai.service.ICourseReservationService;
import com.sparetime.demospringai.service.ICourseService;
import com.sparetime.demospringai.service.ISchoolService;
import jakarta.annotation.Resource;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Qiu Yifan
 * @date 2026/9/23 下午4:54
 * @desc
 */
@Component
public class CourseTools {
    @Resource
    private ICourseService courseService;
    @Resource
    private ISchoolService schoolService;
    @Resource
    private ICourseReservationService courseReservationService;

    @Tool(description = "根据条件查询课程，没有传条件时，返回所有课程")
    public List<Course> queryCourse(@ToolParam(description = "查询条件", required = false) CourseQuery query) {
        if (query == null) {
            return courseService.list();
        }
        courseService.query()
                .eq(query.getType() != null, Course::getType, query.getType());
    }

}