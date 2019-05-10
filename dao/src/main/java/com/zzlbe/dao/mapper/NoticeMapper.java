package com.zzlbe.dao.mapper;

import com.zzlbe.dao.entity.NoticeEntity;
import com.zzlbe.dao.search.NoticeSearch;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeMapper {

    List<NoticeSearch> selectByPage(NoticeSearch noticeSearch);

    List<NoticeSearch> select2ByPage(NoticeSearch noticeSearch);

    NoticeEntity selectById(long id);

    Integer selectByPageTotal();

    int delete(int id);

    void insert(NoticeEntity noticeEntity);

    void update(NoticeEntity goodssortEntity);
}
