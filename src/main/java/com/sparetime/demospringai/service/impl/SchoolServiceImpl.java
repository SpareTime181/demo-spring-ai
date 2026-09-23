package com.sparetime.demospringai.service.impl;

import com.sparetime.demospringai.entity.po.School;
import com.sparetime.demospringai.mapper.SchoolMapper;
import com.sparetime.demospringai.service.ISchoolService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 校区表 服务实现类
 * </p>
 *
 * @author SpareTime
 * @since 2026-09-23
 */
@Service
public class SchoolServiceImpl extends ServiceImpl<SchoolMapper, School> implements ISchoolService {

}
