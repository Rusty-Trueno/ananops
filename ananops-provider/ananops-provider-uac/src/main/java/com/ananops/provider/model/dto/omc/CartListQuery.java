package com.ananops.provider.model.dto.omc;

import com.ananops.provider.model.vo.CartProductVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * The class Cart list query.
 *
 * @author paascloud.net@gmail.com
 */
@Data
public class CartListQuery implements Serializable {
	private static final long serialVersionUID = -5968284112162772265L;
	private List<CartProductVo> cartProductVoList;
}
