package com.tiger.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

import java.util.concurrent.TimeUnit;

/**
 * job不能内部类，匿名内部类
 */
@Slf4j
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class MyJob implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        JobDetail jobDetail = context.getJobDetail();
        log.info("execute job:" + jobDetail.getKey());
        try {
            TimeUnit.MILLISECONDS.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}