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
import java.util.concurrent.Callable;

@Service
public class curdService {
    @Autowired
    private CurdRepository curdRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    private boolean[] flag;
    {
        flag = new boolean[2];  //0 updateTopFlag,1 updateAllFlag
    }


    public msgEntity topNews() {
        return saveToRedis("topNew", flag,0,
                () -> curdRepository.findTopByOrderByIdDesc());
    }

    public Page<msgEntity> pageNews(int page, int size) {
        return curdRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
    }

    public List<msgEntity> allNews() {
        return saveToRedis("topNew",flag,1, () -> curdRepository.findAll());
    }

    public msgEntity addNew(msgEntity msgEntity) {
        for (int i = 0; i < flag.length; i++) {
            flag[i] = false;
        }
        return curdRepository.save(msgEntity);
    }

    public void delNewById(Integer id) {
        for (int i = 0; i < flag.length; i++) {
            flag[i] = false;
        }
        curdRepository.deleteById(id);
    }

    public void delAllNews() {
        for (int i = 0; i < flag.length; i++) {
            flag[i] = false;
        }
        curdRepository.deleteAll();
    }


    //从redis取数据
    <T> T saveToRedis(String s, boolean[] flag, int i,Callable<T> saveMethod) {
        BoundValueOperations news = redisTemplate.boundValueOps(s); //拿到绑定的键值对
        // 不需要更新时直接返回
        if (flag[i]) {
            System.out.println("从redis拿");
            return  (T)news.get();
            // 需要更新时从数据库拿值并存到redis
        } else {
            System.out.println("从数据库拿");
            T msg = null;
            try {
                msg = saveMethod.call(); //call要从数据库中取对象，并返回T对象
            } catch (Exception e) {
                e.printStackTrace();
            }
            news.set(msg);
            flag[i] = true;
            return msg;
        }
    }
}
