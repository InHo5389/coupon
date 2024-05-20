package couponconsumer;

import couponcore.CouponCoreConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(CouponCoreConfiguration.class)
@SpringBootApplication
public class CouponConsumerApplication {

	public static void main(String[] args) {
		// coupon-core yml과 coupon-consumer yml 시스템 속성을 반영하기위해 시스템 설정
		System.setProperty("spring.config.name","application-core,application-consumer");
		SpringApplication.run(CouponConsumerApplication.class, args);
	}
}
