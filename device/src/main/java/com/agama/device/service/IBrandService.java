package com.agama.device.service;

import java.util.List;

import com.agama.common.enumbean.SecondDeviceType;
import com.agama.common.service.IBaseService;
import com.agama.device.domain.Brand;

public interface IBrandService extends IBaseService<Brand, Integer> {

	/**
	 * 根据二级设备类型获取品牌列表
	 * 
	 * @param secondDeviceType
	 * @return
	 */
	public List<Brand> getBrandBySecondDeviceType(SecondDeviceType secondDeviceType);

	/**
	 * 根据二级设备验证品牌
	 * 
	 * @param secondDeviceType
	 * @return
	 */
	public List<Brand> validBrand(SecondDeviceType secondDeviceType, Integer id);
}
