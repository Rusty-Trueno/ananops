/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacRoleMenuMapper.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.mapper;

import com.paascloud.core.mybatis.MyMapper;
import com.paascloud.provider.model.domain.UacRoleMenu;
import com.paascloud.provider.model.vo.role.MenuCountVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The interface Uac role menu mapper.
 *
 * @author paascloud.net @gmail.com
 */
@Mapper
@Component
public interface UacRoleMenuMapper extends MyMapper<UacRoleMenu> {
	/**
	 * Count child menu num list.
	 *
	 * @param roleId the role id
	 *
	 * @return the list
	 */
	List<MenuCountVo> countChildMenuNum(Long roleId);

	/**
	 * Add role menu list int.
	 *
	 * @param addUacRoleMenuList the add uac role menu list
	 *
	 * @return the int
	 */
	int addRoleMenuList(List<UacRoleMenu> addUacRoleMenuList);


	/**
	 * Delete by role id list int.
	 *
	 * @param roleIdList the role id list
	 *
	 * @return the int
	 */
	int deleteByRoleIdList(@Param("roleIdList") List<Long> roleIdList);
}