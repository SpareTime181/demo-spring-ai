package com.sparetime.demospringai.tool;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.sparetime.demospringai.entity.po.Course;
import com.sparetime.demospringai.entity.po.CourseReservation;
import com.sparetime.demospringai.entity.po.School;
import com.sparetime.demospringai.entity.query.CourseQuery;
import com.sparetime.demospringai.service.ICourseReservationService;
import com.sparetime.demospringai.service.ICourseService;
import com.sparetime.demospringai.service.ISchoolService;
import jakarta.annotation.Resource;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

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
        QueryChainWrapper<Course> wrapper = courseService.query()
                .eq(query.getType() != null, "type", query.getType())
                .le(query.getEdu() != null, "edu", query.getEdu());
        if (query.getSorts() != null) {
            for (CourseQuery.Sort sort : query.getSorts()) {
                wrapper.orderBy(sort.getField() != null, sort.getAsc(), sort.getField());
            }
        }
        return wrapper.list();
    }

    @Tool(description = "查询所有校区")
    public List<School> querySchool() {
            return schoolService.list();
    }

    @Tool(description = "预定课程，返回预定记录的id")
    public Integer reserveCourse(@ToolParam(description = "预约课程") String course,
                                 @ToolParam(description = "预约校区") String school,
                                 @ToolParam(description = "学生姓名") String studentName,
                                 @ToolParam(description = "联系电话") String contactInfo,
                                 @ToolParam(description = "备注", required = false) String remark) {
        CourseReservation reservation = new CourseReservation();
        reservation.setCourse(course);
        reservation.setSchool(school);
        reservation.setStudentName(studentName);
        reservation.setContactInfo(contactInfo);
        reservation.setRemark(remark);
        courseReservationService.save(reservation);
        return reservation.getId();
    }

}