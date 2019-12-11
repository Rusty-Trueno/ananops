package com.ananops.provider.service.impl;


import com.ananops.core.support.BaseService;
import com.ananops.provider.model.domain.UacUserMenu;
import com.ananops.provider.service.UacUserMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The class Uac user menu service.
 *
 * @author paascloud.net@gmail.com
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UacUserMenuServiceImpl extends BaseService<UacUserMenu> implements UacUserMenuService {
}
