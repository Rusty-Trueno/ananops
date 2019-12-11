package com.ananops.security.core.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码生成器
 *
 * @author ananops.net @gmail.com
 */
public interface ValidateCodeGenerator {

	/**
	 * 生成校验码
	 *
	 * @param request the request
	 *
	 * @return validate code
	 */
	ValidateCode generate(ServletWebRequest request);

}
