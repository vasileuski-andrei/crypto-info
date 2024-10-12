package eu.senla.cryptoservice.configuration;

import eu.senla.cryptoservice.job.CoinmarketcapJob;
import org.quartz.CronTrigger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

import java.util.Objects;

@Configuration
public class QuartzConfiguration {

    @Value("${quartz.cron-schedule}")
    private String quartzCronSchedule;

    @Bean
    JobDetailFactoryBean jobDetailFactoryBean() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(CoinmarketcapJob.class);
        jobDetailFactoryBean.setName(CoinmarketcapJob.class.getName());
        jobDetailFactoryBean.setDurability(true);
        jobDetailFactoryBean.setRequestsRecovery(true);

        return jobDetailFactoryBean;
    }

    @Bean
    CronTriggerFactoryBean jobTrigger(JobDetailFactoryBean jobDetailFactoryBean) {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(Objects.requireNonNull(jobDetailFactoryBean.getObject()));
        cronTriggerFactoryBean.setCronExpression(quartzCronSchedule);
        cronTriggerFactoryBean.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_FIRE_ONCE_NOW);

        return cronTriggerFactoryBean;
    }
}
