package com.ananops.provider.security;

import com.ananops.security.core.authorize.AuthorizeConfigProvider;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * The class Pc permission authorize config provider.
 *
 * @author paascloud.net @gmail.com
 */
@Order
@Component
public class PcPermissionAuthorizeConfigProvider implements AuthorizeConfigProvider {

	/**
	 * Config boolean.
	 *
	 * @param config the config
	 *
	 * @return the boolean
	 */
	@Override
	public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		config
				.anyRequest()  //权限控制配置 permissionService实现了url的权限校验
				.access("@permissionService.hasPermission(authentication,request)");
		return true;
	}

}
