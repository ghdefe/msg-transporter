package com.chunmiao.msgtransporter.controller;

import com.chunmiao.msgtransporter.entity.msgEntity;
import com.chunmiao.msgtransporter.service.curdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class curdImpl {

    @Autowired
    private curdService service;

    @RequestMapping("/topNews")
    public msgEntity topNews() {
        msgEntity msgEntity = service.topNews();
        return msgEntity;
    }

    @RequestMapping("/pageNews/{page}/{size}")
    public Page<msgEntity> pageNews(@PathVariable Integer page, @PathVariable Integer size) {
        return service.pageNews(page, size);
    }

    @RequestMapping("/allNews")
    public List<msgEntity> allNews() {
        return service.allNews();
    }

    @RequestMapping("/addNew")
    public void addNew(msgEntity msg) {
        service.addNew(msg);
    }

    @RequestMapping("/delNewById/{id}")
    public void delNewById(@PathVariable Integer id) {
        service.delNewById(id);
    }

    @RequestMapping("/delAllNews")
    public void delAllNews() {
        service.delAllNews();
    }

}
