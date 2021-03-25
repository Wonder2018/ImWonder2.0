package top.imwonder.myblog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

// @SpringBootTest
class ApplicationTests {

	@Autowired
	private JdbcTemplate jt;

	// @Autowired
	// private Gson gson;

	// @Resource
	// private RedisTemplate<String,Article> redisTemplate;

	// @Autowired
	// private RedisService<String> redisService;

	// @Autowired
	// private ArticleDAO aDAO;

	// @Autowired
	// private OssResourceDAO orDAO;

	// @Test
	// void contextLoads() {
	// 	long l = redisService.getExpire("hfbdjfbjhdbf");
	// 	return;
	// }

	// @Test
	// public void insetSP() {
	// jt.update("insert into w_system_properties (w_id, w_name, w_value)
	// value(?,?,?)", IdUtil.uuid(), "bucketMap", gson.toJson(bucket));
	// }

}
