package hanghae.review.global.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean("customTaskExecutor")
    public ExecutorService taskExecutor(){
        return Executors.newFixedThreadPool(100);
    }
}
