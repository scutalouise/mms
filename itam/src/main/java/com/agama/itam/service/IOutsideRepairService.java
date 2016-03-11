package com.agama.itam.service;

import java.io.Serializable;
import java.util.List;

import com.agama.common.dao.utils.Page;
import com.agama.common.service.IBaseService;
import com.agama.itam.domain.OutsideRepair;

/**@Description:外修记录业务层接口
 * @Author:佘朝军
 * @Since :2016年3月2日 上午11:50:07
 */
public interface IOutsideRepairService extends IBaseService<OutsideRepair, Serializable>{
	
	public Page<OutsideRepair> getListForPage(Page<OutsideRepair> page, String startTime, String endTime) throws Exception;
	
	public void deleteOutsideRepair(OutsideRepair or);
	
	public void saveByIdentifiers(OutsideRepair or, List<String> identifierList) throws Exception;
	
	public void updateRepairRecord(OutsideRepair or) throws Exception;

}
