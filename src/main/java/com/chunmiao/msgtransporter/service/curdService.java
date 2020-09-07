package com.chunmiao.msgtransporter.service;

import com.chunmiao.msgtransporter.dao.CurdRepository;
import com.chunmiao.msgtransporter.entity.msgEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class curdService {
    @Autowired
    private CurdRepository curdRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    private boolean updateTopFlag = true, updateAllFlag = true, updatePageFlag = true;

    
    public msgEntity topNews() {
//        拿到绑定的键值对
        BoundValueOperations topNew = redisTemplate.boundValueOps("topNew");

//         不需要更新时直接返回
        if (!updateTopFlag) {
            return (msgEntity) topNew.get();
//            需要更新时从数据库拿值并存到redis
        } else {
            msgEntity top = curdRepository.findTopByOrderByIdDesc();
            topNew.set(top);
            updateTopFlag = false;
            return top;
        }
    }

    public Page<msgEntity> pageNews(int page, int size) {
        BoundValueOperations pageNews = redisTemplate.boundValueOps("pageNews");

        if (!updatePageFlag) {
            return (Page<msgEntity>) pageNews.get();
        } else {
            Page<msgEntity> msgs = curdRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
            pageNews.set(msgs);
            updatePageFlag = false;
            return msgs;
        }
    }

    public List<msgEntity> allNews() {
        BoundValueOperations allNews = redisTemplate.boundValueOps("updateAll");

        if (!updateAllFlag) {
            return (List<msgEntity>) allNews.get();
        } else {
            List<msgEntity> msgs = curdRepository.findAll();
            allNews.set(msgs);
            updateAllFlag = false;
            return msgs;
        }
    }

    public msgEntity addNew(msgEntity msgEntity) {
        updateTopFlag = false;
        updateAllFlag = false;
        updatePageFlag = false;
        return curdRepository.save(msgEntity);
    }

    public void delNewById(Integer id) {
        updateTopFlag = false;
        updateAllFlag = false;
        updatePageFlag = false;
        curdRepository.deleteById(id);
    }

    public void delAllNews() {
        updateTopFlag = false;
        updateAllFlag = false;
        updatePageFlag = false;
        curdRepository.deleteAll();
    }
}
