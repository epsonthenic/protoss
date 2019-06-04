package com.protoss.linebot.repository;

import com.protoss.linebot.entity.MasterData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MasterDataRepository extends JpaSpecificationExecutor<MasterData>,
        JpaRepository<MasterData, Long>,
        PagingAndSortingRepository<MasterData, Long> {

}
