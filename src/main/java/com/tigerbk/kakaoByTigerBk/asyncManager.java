//package com.tigerbk.kakaoByTigerBk;
//
//import java.util.concurrent.Executor;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
// 
//
//@Configuration
//@EnableAsync
//public class asyncManager {
//
//	@Bean(name = "asyncManager")
//    public Executor asyncExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(3);
//        executor.setMaxPoolSize(3);
//        executor.setQueueCapacity(100);
//        executor.setThreadNamePrefix("asyncManager Thread");
//        executor.initialize();
//        return executor;
//    }
//}
