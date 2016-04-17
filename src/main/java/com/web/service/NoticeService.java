package com.web.service;

import java.util.Map;

import com.common.SearchTemplate;
import com.web.dao.NoticeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>TODO</p>
 *
 * @author <a href="mailto:lctang@foxmail.com">Sukey</a>
 * @version 1.0
 */

@Service("noticeSerivice")
public class NoticeService {

    @Autowired
    NoticeDao noticeDao;

    public SearchTemplate searchNotice(Map<String,Integer> map) {
        return noticeDao.searchNotice(map);
    }
}
