/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacMenuMapper.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.mapper;

import com.paascloud.core.mybatis.MyMapper;
import com.paascloud.provider.model.domain.UacMenu;
import com.paascloud.provider.model.vo.MenuVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * The interface Uac menu mapper.
 *
 * @author paascloud.net @gmail.com
 */
@Mapper
@Component
public interface UacMenuMapper extends MyMapper<UacMenu> {

	/**
	 * Find menu vo list by user id list.
	 *
	 * @param userId the user id
	 *
	 * @return the list
	 */
	List<MenuVo> findMenuVoListByUserId(Long userId);

	/**
	 * Find menu code list by user id list.
	 *
	 * @param userId the user id
	 *
	 * @return the list
	 */
	List<String> findMenuCodeListByUserId(Long userId);

	/**
	 * Select menu list list.
	 *
	 * @param uacMenu the uac menu
	 *
	 * @return the list
	 */
	List<UacMenu> selectMenuList(UacMenu uacMenu);

	/**
	 * Select menu child count by pid int.
	 *
	 * @param pid the pid
	 *
	 * @return the int
	 */
	int selectMenuChildCountByPid(Long pid);

	/**
	 * Select by url uac menu.
	 *
	 * @param url the url
	 *
	 * @return the uac menu
	 */
	UacMenu selectByUrl(String url);

	/**
	 * Update menu int.
	 *
	 * @param uacMenu the uac menu
	 *
	 * @return the int
	 */
	int updateMenu(UacMenu uacMenu);

	/**
	 * 根据角色ID查询菜单列表.
	 *
	 * @param roleId the role id
	 *
	 * @return the list
	 */
	List<UacMenu> listMenuListByRoleId(@Param("roleId") Long roleId);

	/**
	 * List menu list.
	 *
	 * @param menuIdList the menu id list
	 *
	 * @return the list
	 */
	List<UacMenu> listMenu(@Param("menuIdList") Set<Long> menuIdList);
}