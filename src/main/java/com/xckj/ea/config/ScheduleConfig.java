package com.xckj.ea.config;

import com.xckj.ea.service.MessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.time.LocalDateTime;

@Configuration
//@EnableScheduling
public class ScheduleConfig implements SchedulingConfigurer {
    @Autowired
    MessageService messageService;
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
              //  () -> System.out.println("执行动态定时任务: " + LocalDateTime.now().toLocalTime()),
                () -> messageService.generateMessage(),
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    //2.1 执行周期
                    String cron = "0/60 * * * * ?";
                    //2.2 合法性校验.
                    if (StringUtils.isEmpty(cron)) {
                        // Omitted Code ..
                    }
                    //2.3 返回执行周期(Date)
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                }
        );
    }
}
