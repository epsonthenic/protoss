package com.protoss.linebot.controller;

import com.protoss.linebot.entity.LineData;
import com.protoss.linebot.entity.MasterDataDetail;
import com.protoss.linebot.repository.LineDataRepository;
import com.protoss.linebot.service.AppMailDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/Dashboard")
public class AppMailDataController {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AppMailDataService appMailDataService;

    @Autowired
    private LineDataRepository lineDataRepository;

    @GetMapping("/getkeyword")
    public List<MasterDataDetail> masterDatakey(@RequestParam("id") Long id, @RequestParam("code") String code) {
        return appMailDataService.masterDatakey(id, code);
    }

    @PostMapping("/savebyTable")
    public void SaveByTableId(@RequestBody String tableId){
        LOGGER.info("asds {}",tableId);
        LineData lineData = new LineData();
        lineData.setTableId(tableId);
        lineDataRepository.save(lineData);
    }

    @GetMapping("/getuserid")
    public List<LineData> getUserId(){
        return appMailDataService.getUserId();
    }

}

