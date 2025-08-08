package com.lucifer.ai.mcp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author lucifer
 * @date 2025/8/8 14:46
 */
@Slf4j
@RestController
@RequestMapping("/time")
public class RestFulController {
    /**
     * 获取指定时区的时间
     */
    @GetMapping("/city")
    public String getCityTime(
            @RequestParam("timeZoneId") String timeZoneId,
            @RequestHeader("token") String token
           ) {
        log.info("token is {}", token);
        log.info("The current time zone is {}", timeZoneId);
        return String.format("The current time zone is %s and the current time is " + "%s", timeZoneId, getTimeByZoneId(timeZoneId));
    }


    private static String getTimeByZoneId(String zoneId) {
        ZoneId zid = ZoneId.of(zoneId);
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zid);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        String formattedDateTime = zonedDateTime.format(formatter);
        return formattedDateTime;
    }
}
