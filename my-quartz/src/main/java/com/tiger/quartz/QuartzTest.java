package com.tiger.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Slf4j
public class QuartzTest {
    static Scheduler scheduler;
    static JobDetail jobDetail;
    static Trigger trigger;


    static {
        Properties props = new Properties();
        props.put("org.quartz.scheduler.instanceName", "Test");
        props.put("org.quartz.threadPool.threadCount", "2");
        try {
            StdSchedulerFactory schedulerFactory = new StdSchedulerFactory(props);
            scheduler = schedulerFactory.getScheduler();
            scheduler.getListenerManager().addJobListener(new MyJobListener());
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws SchedulerException, InterruptedException {

        startRepeateJob();

        trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger", "group")
                .startNow()
                .build();
        jobDetail = JobBuilder.newJob(MyJob.class)
                .withIdentity("job", "group")
                .build();

        for (int i = 0; i < 0; i++) {
            if (scheduler.checkExists(jobDetail.getKey())) {
                //scheduler.deleteJob(jobDetail.getKey());
                //scheduler.resumeTrigger(trigger.getKey());
                scheduler.deleteJob(jobDetail.getKey());
                //log.info(i + "");
                log.error("remove job:" + jobDetail.getKey());
            } else {
                scheduler.scheduleJob(jobDetail, trigger);
            }
            TimeUnit.MILLISECONDS.sleep(550L);
        }
    }


    static void startRepeateJob() throws SchedulerException {
        for (int i = 0; i < 10; i++) {
            JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
                    .withIdentity("job-" + i)
                    .build();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .startNow()
                    .withIdentity("trigger-" + i)
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(1)
                            .repeatForever())
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
        }
    }

    static class MyJobListener implements JobListener{

        @Override
        public String getName() {
            return "job-listener";
        }

        @Override
        public void jobToBeExecuted(JobExecutionContext context) {
            //log.info("job to be execute:"+context.getJobDetail().getKey());
        }

        @Override
        public void jobExecutionVetoed(JobExecutionContext context) {
           // log.info("vetoed:"+context.getJobDetail().getKey());

        }

        @Override
        public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
            //log.info("job was executed:"+context.getJobDetail().getKey());
           // log.info("job:"+ context.getJobDetail().getKey()+" next time:"+context.getNextFireTime());
        }
    }
}
