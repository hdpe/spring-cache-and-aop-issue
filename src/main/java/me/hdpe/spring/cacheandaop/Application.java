package me.hdpe.spring.cacheandaop;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;
import org.springframework.aop.support.RootClassFilter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class Application {

	@Bean
	public DefaultAdvisorAutoProxyCreator proxyCreator() {
		DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
//		proxyCreator.setProxyTargetClass(true);
		return proxyCreator;
	}

	@Bean
	public NameMatchMethodPointcutAdvisor pointcutAdvisor() {
		NameMatchMethodPointcutAdvisor advisor = new NameMatchMethodPointcutAdvisor();
		advisor.setClassFilter(new RootClassFilter(Service.class));
		advisor.addMethodName("*");
		advisor.setAdvice(new EnsureNonNegativeAdvice());
		return advisor;
	}

	public static class EnsureNonNegativeAdvice implements MethodBeforeAdvice {

		@Override
		public void before(Method method, Object[] args, Object target)
				throws Throwable {
			if ((int) args[0] < 0) {
				throw new IllegalArgumentException();
			}
		}
	}
}
