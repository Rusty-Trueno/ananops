package com.ananops.provider.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ananops.core.utils.RequestUtil;
import com.ananops.provider.service.UacUserService;
import com.ananops.security.core.SecurityUser;
import com.ananops.wrapper.WrapMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * APP环境下认证成功处理器.
 *
 * @author paascloud.net@gmail.com
 */
@Component("pcAuthenticationSuccessHandler")
@Slf4j
public class PcAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Resource
	private ObjectMapper objectMapper;
	@Resource
	private ClientDetailsService clientDetailsService;
	@Resource
	private UacUserService uacUserService;
	@Resource
	private AuthorizationServerTokenServices authorizationServerTokenServices;

	private static final String BEARER_TOKEN_TYPE = "Basic ";

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	                                    Authentication authentication) throws IOException, ServletException {

		logger.info("登录成功");
		//先获得clientId和clientSecret
		String header = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (header == null || !header.startsWith(BEARER_TOKEN_TYPE)) {
			throw new UnapprovedClientAuthenticationException("请求头中无client信息");
		}

		String[] tokens = RequestUtil.extractAndDecodeHeader(header);
		assert tokens.length == 2;

		String clientId = tokens[0];
		String clientSecret = tokens[1];
		//校验clientDetails用clientDetailsService获取
		ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

		if (clientDetails == null) {
			throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在:" + clientId);
		} else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
			throw new UnapprovedClientAuthenticationException("clientSecret不匹配:" + clientId);
		}
		//clientDetails+tokenRequest才能构建oAuth2Request
		TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "custom");

		OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

		OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
		//authorizationServerTokenServices用于生成access token
		OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
		//SecurityUser是UserDetails的实现 authentication.getPrincipal()获取当前用户的信息
		SecurityUser principal = (SecurityUser) authentication.getPrincipal();
		//uacUserService保存token和principal键值对到redis内
		uacUserService.handlerLoginData(token, principal, request);

		log.info("用户【 {} 】记录登录日志", principal.getUsername());
		//返回token
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write((objectMapper.writeValueAsString(WrapMapper.ok(token))));

	}

}
//最后需要在资源服务器里配置PcAuthenticationSuccessHandler
