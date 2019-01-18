package com.zhujs.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by jinshan.zhu@luckincoffee.com
 * Date: 2018/9/13
 * Time: 17:21
 */
@Component
public class TestSchedule {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Scheduled(fixedRate = 10000)
    public void heatbeat() {
        logger.info("test heatBeat..");
    }
}
