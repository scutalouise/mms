package com.agama.das.service;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import com.agama.das.model.entity.Asset;

/**@Description:接口类，提供设备参数的各种操作方法
 * @Author:佘朝军
 * @Since :2015年11月23日 上午9:23:06
 */
public interface AssetService {
	
	public void insert(Asset asset);
	
	public void saveByAssetsList(List<Map<String,Object>> assets);
	
	public void removeOneByObjectId(ObjectId objectId);
	
	public void removeAll();
	
	public void update(Asset asset);
	
	public Asset getOneByObjectId(ObjectId objectId);
	
	public List<Asset> getAll();
	
	public List<Asset> getListByDeviceType(String deviceType);

}
