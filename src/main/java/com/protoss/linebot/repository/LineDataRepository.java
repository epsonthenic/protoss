package com.protoss.linebot.repository;

import com.protoss.linebot.entity.LineData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-mysql-report
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 25/02/18
 * Time: 19.11
 * To change this template use File | Settings | File Templates.
 */
public interface LineDataRepository extends CrudRepository<LineData, Long> {
    //    List<LineData> findByBrand(@Param("userId") String brand);
    List<LineData> findByUserId(@Param("userId") String userId);
//    List<LineData> findById(@Param("id") Long id);

}
