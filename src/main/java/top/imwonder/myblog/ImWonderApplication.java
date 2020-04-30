package top.imwonder.myblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication(exclude = QuartzAutoConfiguration.class)
public class ImWonderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImWonderApplication.class, args);
	}

}
