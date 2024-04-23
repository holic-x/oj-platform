package com.noob.framework.job.cycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 自定义定时任务
 */
// todo 取消注释开启任务
//@Component
@Slf4j
public class IncSyncPostToEs {

    /**
     * 每分钟执行一次
     */
    @Scheduled(fixedRate = 60 * 1000)
    public void run() {
        // 自定义定时任务
    }
}
