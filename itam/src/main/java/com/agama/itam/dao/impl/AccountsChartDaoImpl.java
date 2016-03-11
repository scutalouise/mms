package com.agama.itam.dao.impl;

import org.springframework.stereotype.Repository;

import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.dao.utils.Page;
import com.agama.itam.bean.DeviceAccountsChartBean;
import com.agama.itam.dao.IAccountsChartDao;

@Repository
public class AccountsChartDaoImpl extends HibernateDaoImpl<DeviceAccountsChartBean, Integer> implements IAccountsChartDao{

	@Override
	public Page<DeviceAccountsChartBean> findPageBySQL(Page<DeviceAccountsChartBean> page) {
	 
		StringBuffer sql = new StringBuffer(
	       	"select p.purchase_date as purchaseDate,b.name as brandName,"
	       	+ "case d.second_device_type when 'TERMINAL' then '终端' when 'SERVER' then '服务器' "
			+ "when 'INTEGRATEDMACHINE' then '一体机' when 'GIT' then 'GIT网关' when 'HANDDEVICE' then '手持机' "
		    + "when 'DATACOLLECT' then '数据采集设备' when 'ROUTER' then '路由器' when 'FIREWALL' then '防火墙' "
		    + "when 'SWITCHBOARD' then '交换机' when 'UPS' then 'UPS设备' when 'TH' then '温湿度' "
		    + "when 'WATER' then '水浸' when 'SMOKE' then '烟感' when 'PRINTER' then '打印机' "
		    + "when 'SCANNER' then '扫描仪' when 'FAX' then '传真机' when 'LAMINATOR' then '塑封机' "
		    + "when 'COUNTER' then '点钞机' when 'SORTER' then '清分机' when 'POS' then 'POS机' "
		    + "when 'FINGERPRINT' then '指纹仪' when 'CARDREADER' then '读卡器' "
		    + "when 'PASSWORDKEYBOARD' then '密码键盘' when 'HIGHSHOTMETER' then '高拍仪' "
		    + "when 'IDCARDREADER' then '身份证阅读器' when 'BINDING' then '扎把机' "
		    + "when 'CARDSENDER' then '发卡机' else '其他' end as deviceType,"
	   		+ "d.free_quantity as previousQuantity,"
	   		+ "p.quantity as inflowQuantity,(d.quantity-d.free_quantity) as outflowQuantity,"
	   		+ "d.scrap_quantity as scrapQuantity,d.other_note as otherNote "
	   		+ "from device_inventory d,device_purchase p,brand b "
	   		+ "where d.id=p.device_inventory_id and d.brand_id=b.id "
	   		+ "group by p.purchase_date,d.second_device_type "
	   		+ "order by p.purchase_date desc");
	 
	    return findPageBySQL(page, sql.toString());
	}
}
