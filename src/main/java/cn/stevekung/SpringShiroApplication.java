package cn.stevekung;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@MapperScan("cn.stevekung.mapper")
@SpringBootApplication
public class SpringShiroApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringShiroApplication.class, args);
	}

}
