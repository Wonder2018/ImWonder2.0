/*
 * @Author: Wonder2019 
 * @Date: 2020-05-02 17:59:43 
 * @Last Modified by:   Wonder2019 
 * @Last Modified time: 2020-05-02 17:59:43 
 */
package top.imwonder.myblog;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ImWonderApplication.class);
	}

}
