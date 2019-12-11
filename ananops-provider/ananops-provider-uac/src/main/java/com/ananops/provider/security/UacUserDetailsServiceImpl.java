package com.ananops.provider.security;

import com.ananops.provider.model.domain.UacUser;
import com.ananops.provider.service.UacUserService;
import com.ananops.security.core.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * The class Uac user details service.
 * 获取数据库的User信息 包括授予的权限即url形式
 * @author paascloud.net @gmail.com
 */
@Component
@Slf4j
public class UacUserDetailsServiceImpl implements UserDetailsService {

	@Resource
	private UacUserService uacUserService;

	/**
	 * Load user by username user details.
	 *
	 * @param username the usernameff
	 *
	 * @return the user details
	 */
	@Override
	public UserDetails loadUserByUsername(String username) {
		Collection<GrantedAuthority> grantedAuthorities;
		UacUser user = uacUserService.findByLoginName(username);
		log.info("UacUser: "+user);
		if (user == null) {
			throw new BadCredentialsException("用户名不存在或者密码错误");
		}
		user = uacUserService.findUserInfoByUserId(user.getId());
		log.info("user: "+ user);
		grantedAuthorities = uacUserService.loadUserAuthorities(user.getId()); //
		log.info("grantedAuthorities: "+grantedAuthorities);
		return new SecurityUser(user.getId(), user.getLoginName(), user.getLoginPwd(),
				user.getUserName(), user.getGroupId(), user.getGroupName(), user.getStatus(), grantedAuthorities);
	}
}
