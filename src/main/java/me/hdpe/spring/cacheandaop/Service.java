package me.hdpe.spring.cacheandaop;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class Service {

	@Cacheable(cacheNames = "int-strings")
	public String getString(int i) {
		return String.valueOf(i);
	}
}
