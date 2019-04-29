package org.bricks.framework;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;

@NacosPropertySource(dataId = "example", autoRefreshed = true)
@SpringBootApplication
@ServletComponentScan
@MapperScan({"org.bricks.framework.*.dao"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
