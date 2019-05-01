package com.zzlbe.dao.mapper;

import com.zzlbe.dao.entity.AttendanceEntity;
import com.zzlbe.dao.search.AttendanSearch;
import com.zzlbe.dao.search.AttendanceSearch;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PROJECT: Sales
 * DESCRIPTION: note
 *
 * @author duGraceful
 * @date 2019/3/26
 */
@Repository
public interface AttendanceMapper {

    void insert(AttendanceEntity attendanceEntity);

    void update(AttendanceEntity attendanceEntity);

    AttendanceEntity selectById(Long id);

    AttendanceEntity selectByExample(AttendanceSearch search);

    List<AttendanceEntity> selectByPage(AttendanceSearch search);

    Integer selectByPageTotal(AttendanceSearch search);

    List<AttendanSearch> select2ByPage(AttendanceSearch search);//关联查询，新增一列name


}
