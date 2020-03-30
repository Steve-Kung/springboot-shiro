package cn.stevekung;

import cn.stevekung.pojo.User;
import cn.stevekung.service.UserServiceImpl;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringShiroApplicationTests {

	@Test
	public void contextLoads() {
		String hex = new Sha256Hash("admin").toHex();
		System.out.println(hex);
	}

	@Autowired
	DataSource dataSource;
	@Test
	public void testDataSource(){
		System.out.println(dataSource.getClass());
		DruidDataSource dataSource = (DruidDataSource) this.dataSource;
		int maxActive = dataSource.getMaxActive();
		System.out.println(maxActive);
	}

	@Autowired
	UserServiceImpl userService;
	@Test
	public void userServiceTest(){
		User user = userService.getUserByUsername("steve");
		System.out.println(user);
	}

	@Autowired
	StringRedisTemplate stringRedisTemplate;
	@Test
	public void testRedis(){
		User user = User.builder().id(1).username("steve").password("kung").salt("salt").build();
		String string = JSON.toJSONString(user);
		stringRedisTemplate.opsForValue().set("user1", string);

	}
}
