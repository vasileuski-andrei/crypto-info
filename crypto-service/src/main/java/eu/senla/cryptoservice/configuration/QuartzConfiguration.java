package eu.senla.cryptoservice.configuration;

import eu.senla.cryptoservice.job.CoinmarketcapJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfiguration {

    @Value("${spring.quartz.cron-schedule}")
    private String quartzCronSchedule;

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(CoinmarketcapJob.class)
                .withIdentity("myJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger jobTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail())
                .withIdentity("jobTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule(quartzCronSchedule))
                .build();
    }
}
