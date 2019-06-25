package com.protoss.linebot.controller;

import com.protoss.linebot.entity.LineData;
import com.protoss.linebot.entity.MasterDataDetail;
import com.protoss.linebot.service.AppMailDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/RCD")
public class AppMailDataController {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AppMailDataService appMailDataService;

    @GetMapping("/getkeyword")
    public List<MasterDataDetail> masterDatakey(@RequestParam("id") Long id, @RequestParam("code") String code) {
        return appMailDataService.masterDatakey(id, code);
    }

    @GetMapping("/getuserid")
    public List<LineData> getUserId(){
        return appMailDataService.getUserId();
    }

}

