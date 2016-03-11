package com.agama.itam.dao.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.agama.authority.service.IOrganizationService;
import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.dao.utils.Page;
import com.agama.common.enumbean.FirstDeviceType;
import com.agama.itam.bean.DeviceBelongChartBean;
import com.agama.itam.dao.IBelongChartDao;

@Repository
public class BelongChartDaoImpl extends HibernateDaoImpl<DeviceBelongChartBean, Integer> implements IBelongChartDao{

	@Autowired
	private IOrganizationService organizationService;
	
	@Override
	public Page<DeviceBelongChartBean> findPageBySQL(Page<DeviceBelongChartBean> page,HttpServletRequest request) {
		
		String ids="";
		String	name = request.getParameter("name");
		String	deviceType = request.getParameter("firstDeviceType");
		String	orgId = request.getParameter("orgId");
		String	GT_purchaseDate = request.getParameter("GTD_purchaseDate");
		String	LT_purchaseDate = request.getParameter("LTD_purchaseDate");
		if (orgId != null && orgId !="") {
			ids = organizationService.getOrganizationIdStrById(Integer.parseInt(orgId));
		}
		
		StringBuffer sql = new StringBuffer(
		     "select a.* from "
			+"(select hd.name as name,hd.model as model,hd.organization_id as orgId,ho.org_name as orgName,hd.manager_id as managerId,"
			+"hu.name as managerName,hp.purchase_date as purchaseDate,hd.identifier as identifier,"
			+"case hi.first_device_type when 'HOSTDEVICE' then '主机设备' else '其他' end as deviceType,"
			+"(select min(1) from host_device h) as quantity "
			+"from (host_device hd,device_inventory hi,device_purchase hp,organization ho) left join user hu on hd.manager_id=hu.id "
			+"where hd.status=0 and hd.purchase_id=hp.id and hd.organization_id=ho.id and hp.device_inventory_id=hi.id ");
		
        sql.append("union "
		    +"select nd.name as name,nd.model as model,nd.organization_id as orgId,no.org_name as orgName,nd.manager_id as managerId,"
			+"nu.name as managerName,np.purchase_date as purchaseDate,nd.identifier as identifier,"
			+"case ni.first_device_type when 'NETWORKDEVICE' then '网络设备' else '其他' end as deviceType,"
			+"(select min(1) from network_device n) as quantity "
			+"from (network_device nd,device_inventory ni,device_purchase np,organization no) left join user nu on nd.manager_id=nu.id "
			+"where nd.status=0 and nd.purchase_id=np.id and nd.organization_id=no.id and np.device_inventory_id=ni.id ");
		
		sql.append("union "
		    +"select ud.name as name,ud.model as model,ud.organization_id as orgId,uo.org_name as orgName,ud.manager_id as managerId,"
			+"uu.name as managerName,up.purchase_date as purchaseDate,ud.identifier as identifier,"
			+"case ui.first_device_type when 'UNINTELLIGENTDEVICE' then '非智能设备' else '其他' end as deviceType,"
			+"(select min(1) from unintelligent_device u) as quantity "
			+"from (unintelligent_device ud,device_inventory ui,device_purchase up,organization uo) left join user uu on ud.manager_id=uu.id "
			+"where ud.status=0 and ud.purchase_id=up.id and ud.organization_id=uo.id and up.device_inventory_id=ui.id ");
		
		sql.append("union "
		    +"select cd.name as name,cd.model as model,cd.organization_id as orgId,co.org_name as orgName,cd.manager_id as managerId,"
			+"cu.name as managerName,cp.purchase_date as purchaseDate,cd.identifier as identifier,"
			+"case ci.first_device_type when 'COLLECTDEVICE' then '采集设备' else '其他' end as deviceType,"
			+"(select min(1) from collection_device c) as quantity "
			+"from (collection_device cd,device_inventory ci,device_purchase cp,organization co) left join user cu on cd.manager_id=cu.id "
			+"where cd.status=0 and cd.purchase_id=cp.id and cd.organization_id=co.id and cp.device_inventory_id=ci.id ");
		
		sql.append("union "
		    +"select pd.name as name,pd.model as model,pd.organization_id as orgId,po.org_name as orgName,pd.manager_id as managerId,"
			+"pu.name as managerName,pp.purchase_date as purchaseDate,pd.identifier as identifier,"
			+"case pi.first_device_type when 'PEDEVICE' then '动环设备' else '其他' end as deviceType,"
			+"(select min(1) from pe_device p) as quantity "
			+"from (pe_device pd,device_inventory pi,device_purchase pp,organization po) left join user pu on pd.manager_id=pu.id "
			+"where pd.status=0 and pd.purchase_id=pp.id and pd.organization_id=po.id and pp.device_inventory_id=pi.id) a "
			+"where name is not null ");

        if (name != null && name != "") {
			sql.append("and name like '%" + name.trim() + "%' ");
		}
		if (deviceType != null && deviceType != "") {
			sql.append("and deviceType='" + FirstDeviceType.valueOf(deviceType).getName() + "' ");
		}
		if (ids != null && ids != "") {
			sql.append("and orgId in(" + ids + ") ");
		}
		if (GT_purchaseDate != null && GT_purchaseDate != "") {
			sql.append("and purchaseDate>=str_to_date('" + GT_purchaseDate + "','%Y-%m-%d') ");
		}
		if (LT_purchaseDate != null && LT_purchaseDate != "") {
			sql.append("and purchaseDate<=str_to_date('" + LT_purchaseDate + "','%Y-%m-%d') ");
		}
		
		sql.append("order by orgId,deviceType,purchaseDate desc ");
		//return  getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(DeviceBelongChartBean.class)).list();
	    return findPageBySQL(page, sql.toString());
	}

}
