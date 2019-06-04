package com.protoss.linebot.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-mysql-report
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 25/02/18
 * Time: 19.10
 * To change this template use File | Settings | File Templates.
 */

@Entity
public class LineData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String userId;

    public LineData() {
    }

    public LineData(String userId) {
        super();
        this.userId = userId;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

//    @Override
//    public String toString()
//    {
//        return "LineData{" +
//                "userId='" + userId + '\'' +
//                '}';
//    }
}
