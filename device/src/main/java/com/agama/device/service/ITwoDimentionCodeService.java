package com.agama.device.service;

import javax.servlet.http.HttpServletRequest;

import com.agama.common.service.IBaseService;
import com.agama.device.domain.TwoDimentionCode;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ITwoDimentionCodeService extends IBaseService<TwoDimentionCode, Integer> {

	/**
	 * @Description 通过唯一识别码获取二维码
	 * @param identifier
	 * @return TwoDimentionCode
	 * @Since 2016年1月21日 下午2:54:58
	 */
	public TwoDimentionCode getByidentifier(String identifier);

	/**
	 * @Description 生成二维码
	 * @param identifier
	 * @param id
	 * @param pathEdit
	 * @param request
	 * @return
	 * @throws JsonProcessingException
	 * @Since 2016年1月25日 下午4:52:37
	 */
	public String createQR(String identifier, Integer id, HttpServletRequest request, String pathEdit)
			throws JsonProcessingException;
}
