package com.protoss.linebot.service;

import com.protoss.linebot.entity.LineData;
import com.protoss.linebot.entity.MasterDataDetail;

import java.util.List;

public interface AppMailDataService {

    List<MasterDataDetail> masterDatakey(Long id, String code);
    List<LineData> getUserId();

}
