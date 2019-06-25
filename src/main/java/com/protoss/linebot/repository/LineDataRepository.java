package com.protoss.linebot.repository;

import com.protoss.linebot.entity.LineData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LineDataRepository extends JpaSpecificationExecutor<LineData>,
        JpaRepository<LineData, Long>,
        PagingAndSortingRepository<LineData, Long> {

        }
