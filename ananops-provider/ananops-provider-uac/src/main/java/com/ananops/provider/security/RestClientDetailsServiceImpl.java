package com.ananops.provider.security;

import com.ananops.security.core.properties.OAuth2ClientProperties;
import com.ananops.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * The class Rest client details service.
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
@Component("restClientDetailsService")
public class RestClientDetailsServiceImpl implements ClientDetailsService {

	private ClientDetailsService clientDetailsService;

	@Autowired
	private SecurityProperties securityProperties;	//总的配置信息

	/**
	 * Init.
	 */
	@PostConstruct
	public void init() {
		//把所有的OAuth2ClientProperties加载到内存中
		InMemoryClientDetailsServiceBuilder builder = new InMemoryClientDetailsServiceBuilder();
		if (ArrayUtils.isNotEmpty(securityProperties.getOauth2().getClients())) {
			for (OAuth2ClientProperties client : securityProperties.getOauth2().getClients()) { //配置项在OAuth2ClientProperties 位置在配置文件里
				builder.withClient(client.getClientId())
						.secret(client.getClientSecret())
						.authorizedGrantTypes("refresh_token", "password", "client_credentials") //授权模式
						.accessTokenValiditySeconds(client.getAccessTokenValidateSeconds())
						.refreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds())
						.scopes(client.getScope()); //用来限制客户端权限访问的范围，可以用来设置角色或者权限
			}
		}
		try {
			clientDetailsService = builder.build();
		} catch (Exception e) {
			log.error("init={}", e.getMessage(), e);
		}
	}

	/**
	 * Load client by client id client details.
	 *
	 * @param clientId the client id
	 *
	 * @return the client details
	 *
	 * @throws ClientRegistrationException the client registration exception
	 */
	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		return clientDetailsService.loadClientByClientId(clientId);
	}
}
