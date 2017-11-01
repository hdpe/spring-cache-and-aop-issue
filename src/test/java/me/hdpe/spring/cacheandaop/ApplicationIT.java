package me.hdpe.spring.cacheandaop;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
public class ApplicationIT {

	@Autowired
	private Service service;
	
	@Autowired
	private CacheManager cacheManager;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private Cache cache;
	
	@Before
	public void setUp() {
		cache = cacheManager.getCache("int-strings");
	}
	
	@After
	public void tearDown() {
		cache.clear();
	}
	
	@Test
	public void getStringReturnsValue() {
		String value = service.getString(1);
		
		assertThat(value, is("1"));
	}
	
	@Test
	public void getStringCachesValue() {
		service.getString(1);
		
		assertThat(cache.get(1, String.class), is("1"));
	}
	
	@Test
	public void getStringWithNegativeThrowsException() {
		thrown.expect(IllegalArgumentException.class);
		
		service.getString(-1);
	}
}
