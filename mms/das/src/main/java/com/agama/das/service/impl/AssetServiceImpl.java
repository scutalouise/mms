package com.agama.das.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.log4j.Log4j;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.agama.aws.dao.MongoDao;
import com.agama.aws.helper.MongoQueryHelper;
import com.agama.das.model.entity.Asset;
import com.agama.das.service.AssetService;

/**
 * @Description:实现类，实现设备参数信息操作的方法
 * @Author:佘朝军
 * @Since :2015年11月23日 上午9:31:29
 */
@Log4j
@Service
public class AssetServiceImpl implements AssetService {

	@Autowired
	private MongoDao mongoDao;

	@Override
	public void insert(Asset asset) {
		mongoDao.save(asset);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveByAssetsList(List<Map<String, Object>> assets) {
		for (Map<String, Object> map : assets) {
			try {
				Asset asset = new Asset();
				asset.setAddress(map.get("address").toString());
				asset.setIdentifier(map.get("identifier").toString());
				asset.setDeviceType(map.get("deviceType").toString());
				asset.setMetaData((Map<String, Object>) map.get("metaData"));// TODO
				asset.setSecondType(map.get("secondType").toString());
				asset.setUserName(map.get("userName").toString());
				asset.setPassword(map.get("password").toString());
				asset.setPort((Integer) map.get("port"));
				asset.setProtocol(map.get("protocol").toString());
				asset.setProtocolVersion(map.get("protocolVersion").toString());
				insert(asset);
			} catch (Exception e) {
				log.error("设备编码为：[" + map.get("deviceId") + map.get("deviceType") + "]的数据插入失败！", e);
				e.printStackTrace();
			}
		}

	}

	@Override
	public List<Asset> getAll() {
		return mongoDao.queryListByCriteriaAndSort(new Criteria(), null, Asset.class);
	}

	@Override
	public void removeOneByObjectId(ObjectId objectId) {
		mongoDao.removeByObjectId(objectId, Asset.class);
	}

	@Override
	public void update(Asset asset) {
		mongoDao.updateBeanByObjectId(asset.getId(), asset, Asset.class);
	}

	@Override
	public void removeAll() {
		List<Asset> assets = getAll();
		for (Asset asset : assets) {
			removeOneByObjectId(asset.getId());
		}
	}

	@Override
	public Asset getOneByObjectId(ObjectId objectId) {
		return mongoDao.queryOneByObjectId(objectId, Asset.class);
	}

	@Override
	public List<Asset> getListByDeviceType(String deviceType) {
		Map<String, Object> eqMap = new HashMap<String, Object>();
		eqMap.put("deviceType", deviceType);
		Criteria criteria = MongoQueryHelper.createCriteria(null, null, eqMap, null, null, null, null, null);
		return mongoDao.queryListByCriteriaAndSort(criteria, null, Asset.class);
	}

}
