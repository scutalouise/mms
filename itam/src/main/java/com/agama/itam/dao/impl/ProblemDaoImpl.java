package com.agama.itam.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.agama.authority.service.IOrganizationService;
import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.dao.utils.Page;
import com.agama.common.enumbean.FirstDeviceType;
import com.agama.common.enumbean.ProblemStatusEnum;
import com.agama.common.enumbean.SecondDeviceType;
import com.agama.common.enumbean.StatusEnum;
import com.agama.itam.bean.DeviceFaultTopNBean;
import com.agama.itam.bean.MaintenanceTopNBean;
import com.agama.itam.bean.SupplyTopNBean;
import com.agama.itam.dao.IProblemDao;
import com.agama.itam.domain.Problem;

/**
 * @Description:问题记录DAO实现类
 * @Author:佘朝军
 * @Since :2016年1月19日 下午2:56:50
 */
@SuppressWarnings("unchecked")
@Repository
public class ProblemDaoImpl extends HibernateDaoImpl<Problem, Serializable> implements IProblemDao {
	
	@Autowired
	private IOrganizationService organizationService;

	@Override
	public List<Problem> getAllList() throws Exception {
		String hql = "from Problem where status = " + StatusEnum.NORMAL.ordinal();
		return (List<Problem>) getSession().createQuery(hql).list();
	}

	@Override
	public Page<Problem> searchListForHandling(String problemCode, Integer problemTypeId, Integer resolveUserId, String enable, String recordTime, String recordEndTime,
			Page<Problem> page, String model) throws Exception {
		String hql = "from Problem where status = " + StatusEnum.NORMAL.ordinal();
		if (!StringUtils.isBlank(problemCode)) {
			hql += " and problemCode like '%" + problemCode.trim() + "%' ";
		}
		if (problemTypeId != null) {
			hql += " and problemTypeId = " + problemTypeId;
		}
		if (resolveUserId != null) {
			hql += " and resolveUserId = " + resolveUserId;
		}
		if (StringUtils.isNotBlank(enable)) {
			hql += " and enable = '" + enable + "' ";
		} else {
			if ("handle".equals(model)) {
				hql += " and enable in ('" + ProblemStatusEnum.HANDLING + "','"
						+ ProblemStatusEnum.ASSIGNED + "') ";
			}
		}
		if (!StringUtils.isBlank(recordTime)) {
			hql += " and recordTime >= '" + recordTime.trim() + " 00:00:00' ";
		}
		if (!StringUtils.isBlank(recordEndTime)) {
			hql += " and recordTime <= '" + recordEndTime.trim() + " 23:59:59' ";
		}
		if ("problem".equals(model)) {
			hql += " order by recordTime desc ";
		}
		return this.findPage(page, hql);
	}

	@Override
	public Long countTaskTotalByUserId(Integer userId) throws Exception {
		String hql = "from Problem where resolveUserId = " + userId;
		return this.countHqlResult(hql);
	}

	@Override
	public List<SupplyTopNBean> findDataBySQL(HttpServletRequest request) {
		
		String brandId=request.getParameter("brandId");
		String sort=request.getParameter("sort");
		String top=request.getParameter("top");
		String beginDate=request.getParameter("beginDate");
		String endDate=request.getParameter("endDate");
		
		StringBuffer sql = new StringBuffer(
				"select a.brandId,a.brandName,a.supplyName,a.recordTime,"
				+ "sum(a.quantity) as quantity from "
			    + "(select hu.brand_id as brandId,hb.name as brandName,"
			    + "hs.org_name as supplyName,hp.record_time as recordTime,"
			    + "count(1) as quantity "
				+ "from host_device hd,problem hp,device_purchase hu,"
				+ "brand hb,supply_maintain_org hs "
				+ "where hp.identifier=hd.identifier and hd.purchase_id=hu.id "
				+ "and hu.brand_id=hb.id and hu.supply_id=hs.id "
				+ "group by hu.supply_id,hu.brand_id ");
		
		sql.append("union all "
				+ "select nu.brand_id as brandId,nb.name as brandName,"
				+ "ns.org_name as supplyName,np.record_time as recordTime,"
				+ "count(1) as quantity "
				+ "from network_device nd,problem np,device_purchase nu,"
				+ "brand nb,supply_maintain_org ns "
				+ "where np.identifier=nd.identifier and nd.purchase_id=nu.id "
				+ "and nu.brand_id=nb.id and nu.supply_id=ns.id "
				+ "group by nu.supply_id,nu.brand_id ");
		
		sql.append("union all "
				+ "select uu.brand_id as brandId,ub.name as brandName,"
				+ "us.org_name as supplyName,up.record_time as recordTime,"
				+ "count(1) as quantity "
				+ "from unintelligent_device ud,problem up,device_purchase uu,"
				+ "brand ub,supply_maintain_org us "
				+ "where up.identifier=ud.identifier and ud.purchase_id=uu.id "
				+ "and uu.brand_id=ub.id and uu.supply_id=us.id "
				+ "group by uu.supply_id,uu.brand_id ");
		
		sql.append("union all "
				+ "select cu.brand_id as brandId,cb.name as brandName,"
				+ "cs.org_name as supplyName,cp.record_time as recordTime,"
				+ "count(1) as quantity "
				+ "from collection_device cd,problem cp,device_purchase cu,"
				+ "brand cb,supply_maintain_org cs "
				+ "where cp.identifier=cd.identifier and cd.purchase_id=cu.id "
				+ "and cu.brand_id=cb.id and cu.supply_id=cs.id "
				+ "group by cu.supply_id,cu.brand_id ");
		
		sql.append("union all "
				+ "select pu.brand_id as brandId,pb.name as brandName,"
				+ "ps.org_name as supplyName,pp.record_time as recordTime,"
				+ "count(1) as quantity "
				+ "from pe_device pd,problem pp,device_purchase pu,"
				+ "brand pb,supply_maintain_org ps "
				+ "where pp.identifier=pd.identifier and pd.purchase_id=pu.id "
				+ "and pu.brand_id=pb.id and pu.supply_id=ps.id "
				+ "group by pu.supply_id,pu.brand_id) a "
				+ "where brandName is not null and supplyName is not null ");
		
		if (brandId != null && brandId != "") {
			sql.append("and brandId='" + Integer.parseInt(brandId) + "' ");
		}
		if (beginDate != null && beginDate != "") {
			sql.append("and recordTime>=str_to_date('" + beginDate + "','%Y-%m-%d') ");
		}
		if (endDate != null && endDate != "") {
			sql.append("and recordTime<=str_to_date('" + endDate + "','%Y-%m-%d') ");
		}
		
		sql.append("group by supplyName,brandName "
				 + "order by quantity " + sort +" "
				 + "limit 0,"+ top);
		
		return  getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(SupplyTopNBean.class)).list();
	}

	@Override
	public List<MaintenanceTopNBean> findMaintenanceDataBySQL(HttpServletRequest request) {
		
		String deviceType=request.getParameter("deviceType");
		String brandId=request.getParameter("brandId");
		String sort=request.getParameter("sort");
		String top=request.getParameter("top");
		String beginDate=request.getParameter("beginDate");
		String endDate=request.getParameter("endDate");
		
		StringBuffer sql = new StringBuffer(
				"select a.* from "
			    + "(select hu.brand_id as brandId,hb.name as brandName,"
			    + "hs.org_name as maintenanceName,hp.id as problemId,"
			    + "case hi.first_device_type when 'HOSTDEVICE' then '主机设备' else '其他' end as deviceType,"
			    + "format(timestampdiff(second,hp.record_time,hp.resolve_time)/60/60,2) as time,"
			    + "hp.record_time as recordTime,count(1) as quantity "
				+ "from host_device hd,problem hp,device_purchase hu,organization ho,"
				+ "brand hb,supply_maintain_org hs,device_inventory hi "
				+ "where hp.identifier=hd.identifier and hd.purchase_id=hu.id "
				+ "and hu.brand_id=hb.id and hd.maintain_way='OUTER' "
				+ "and hd.maintain_org_id=hs.id and hu.device_inventory_id=hi.id "
				+ "group by hs.id,hu.brand_id,hi.first_device_type ");
		
		sql.append("union "
				+ "select nu.brand_id as brandId,nb.name as brandName,"
				+ "ns.org_name as maintenanceName,np.id as problemId,"
				+ "case ni.first_device_type when 'NETWORKDEVICE' then '网络设备' else '其他' end as deviceType,"
				+ "format(timestampdiff(second,np.record_time,np.resolve_time)/60/60,2) as time,"
				+ "np.record_time as recordTime,count(1) as quantity "
				+ "from network_device nd,problem np,device_purchase nu,"
				+ "brand nb,supply_maintain_org ns,device_inventory ni "
				+ "where np.identifier=nd.identifier and nd.purchase_id=nu.id "
				+ "and nu.brand_id=nb.id and nd.maintain_way='OUTER' "
				+ "and nd.maintain_org_id=ns.id and nu.device_inventory_id=ni.id "
				+ "group by ns.id,nu.brand_id,ni.first_device_type ");
		
		sql.append("union "
				+ "select uu.brand_id as brandId,ub.name as brandName,"
				+ "us.org_name as maintenanceName,up.id as problemId,"
				+ "case ui.first_device_type when 'UNINTELLIGENTDEVICE' then '非智能设备' else '其他' end as deviceType,"
				+ "format(timestampdiff(second,up.record_time,up.resolve_time)/60/60,2) as time,"
				+ "up.record_time as recordTime,count(1) as quantity "
				+ "from unintelligent_device ud,problem up,device_purchase uu,"
				+ "brand ub,supply_maintain_org us,device_inventory ui "
				+ "where up.identifier=ud.identifier and ud.purchase_id=uu.id "
				+ "and uu.brand_id=ub.id and ud.maintain_way='OUTER' "
				+ "and ud.maintain_org_id=us.id and uu.device_inventory_id=ui.id "
				+ "group by us.id,uu.brand_id,ui.first_device_type ");
		
		sql.append("union "
				+ "select cu.brand_id as brandId,cb.name as brandName,"
				+ "cs.org_name as maintenanceName,cp.id as problemId,"
				+ "case ci.first_device_type when 'COLLECTDEVICE' then '采集设备' else '其他' end as deviceType,"
				+ "format(timestampdiff(second,cp.record_time,cp.resolve_time)/60/60,2) as time,"
				+ "cp.record_time as recordTime,count(1) as quantity "
				+ "from collection_device cd,problem cp,device_purchase cu,"
				+ "brand cb,supply_maintain_org cs,device_inventory ci "
				+ "where cp.identifier=cd.identifier and cd.purchase_id=cu.id "
				+ "and cu.brand_id=cb.id and cd.maintain_way='OUTER' "
				+ "and cd.maintain_org_id=cs.id and cu.device_inventory_id=ci.id "
				+ "group by cs.id,cu.brand_id,ci.first_device_type ");
		
		sql.append("union "
				+ "select pu.brand_id as brandId,pb.name as brandName,"
				+ "ps.org_name as maintenanceName,pp.id as problemId,"
				+ "case pi.first_device_type when 'PEDEVICE' then '动环设备' else '其他' end as deviceType,"
				+ "format(timestampdiff(second,pp.record_time,pp.resolve_time)/60/60,2) as time,"
				+ "pp.record_time as recordTime,count(1) as quantity "
				+ "from pe_device pd,problem pp,device_purchase pu,"
				+ "brand pb,supply_maintain_org ps,device_inventory pi "
				+ "where pp.identifier=pd.identifier and pd.purchase_id=pu.id "
				+ "and pu.brand_id=pb.id and pd.maintain_way='OUTER' "
				+ "and pd.maintain_org_id=ps.id and pu.device_inventory_id=pi.id "
				+ "group by ps.id,pu.brand_id,pi.first_device_type) a "
				+ "where maintenanceName is not null ");
		
		if (deviceType != null && deviceType != "") {
			sql.append("and deviceType='" + FirstDeviceType.valueOf(deviceType).getName() + "' ");
		}
		if (brandId != null && brandId != "") {
			sql.append("and brandId=" + Integer.parseInt(brandId) + " ");
		}
		if (beginDate != null && beginDate != "") {
			sql.append("and recordTime>=str_to_date('" + beginDate + "','%Y-%m-%d') ");
		}
		if (endDate != null && endDate != "") {
			sql.append("and recordTime<=str_to_date('" + endDate + "','%Y-%m-%d') ");
		}
		
		sql.append("group by maintenanceName,brandId,deviceType "
				 + "order by quantity " + sort +" "
				 + "limit 0,"+ top);
		
		return  getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(MaintenanceTopNBean.class)).list();
	}

	@Override
	public List<DeviceFaultTopNBean> findDeviceFaultDataBySQL(HttpServletRequest request) {
		
		String ids="";
		String deviceType=request.getParameter("deviceType");
		String orgId=request.getParameter("orgId");
		if (orgId != null && orgId !="") {
			ids = organizationService.getOrganizationIdStrById(Integer.parseInt(orgId));
		}
		String sort=request.getParameter("sort");
		String top=request.getParameter("top");
		String beginDate=request.getParameter("beginDate");
		String endDate=request.getParameter("endDate");
		
		StringBuffer sql = new StringBuffer(
				 "select a.orgId,a.orgName,a.deviceType,a.date,"
				+ "sum(a.quantity) as quantity from "
			    + "(select ho.id as orgId,ho.org_name as orgName,"
			    + "case hi.second_device_type when 'TERMINAL' then '终端' when 'SERVER' then '服务器' "
			    + "when 'INTEGRATEDMACHINE' then '一体机' else '其他' end as deviceType,"
			    + "hp.record_time as date,count(1) as quantity "
				+ "from host_device hd,problem hp,device_purchase hu,"
				+ "device_inventory hi,organization ho "
				+ "where hp.identifier=hd.identifier and hd.purchase_id=hu.id "
				+ "and hd.organization_id=ho.id and hu.device_inventory_id=hi.id "
				+ "group by ho.id ");
		
		if (ids.length()>0) {
			sql.append(",hi.second_device_type ");
		}
		
		sql.append("union "
				+ "select no.id as orgId,no.org_name as orgName,"
				+ "case ni.second_device_type when 'GIT' then 'GIT网关' when 'HANDDEVICE' then '手持机' "
			    + "when 'DATACOLLECT' then '数据采集设备' else '其他' end as deviceType,"
			    + "np.record_time as date,count(1) as quantity "
				+ "from network_device nd,problem np,device_purchase nu,"
				+ "device_inventory ni,organization no "
				+ "where np.identifier=nd.identifier and nd.purchase_id=nu.id "
				+ "and nd.organization_id=no.id and nu.device_inventory_id=ni.id "
				+ "group by no.id ");
		
		if (ids.length()>0) {
			sql.append(",ni.second_device_type ");
		}
		
		sql.append("union "
				+ "select uo.id as orgId,uo.org_name as orgName,"
				+ "case ui.second_device_type when 'PRINTER' then '打印机' when 'SCANNER' then '扫描仪' "
			    + "when 'FAX' then '传真机' when 'LAMINATOR' then '塑封机' when 'COUNTER' then '点钞机' "
			    + "when 'SORTER' then '清分机' when 'POS' then 'POS机' when 'FINGERPRINT' then '指纹仪' "
			    + "when 'CARDREADER' then '读卡器' when 'PASSWORDKEYBOARD' then '密码键盘' "
			    + "when 'HIGHSHOTMETER' then '高拍仪' when 'IDCARDREADER' then '身份证阅读器' "
			    + "when 'BINDING' then '扎把机' when 'CARDSENDER' then '发卡机' "
			    + "else '其他' end as deviceType,"
			    + "up.record_time as date,count(1) as quantity "
				+ "from unintelligent_device ud,problem up,device_purchase uu,"
				+ "device_inventory ui,organization uo "
				+ "where up.identifier=ud.identifier and ud.purchase_id=uu.id "
				+ "and ud.organization_id=uo.id and uu.device_inventory_id=ui.id "
				+ "group by uo.id ");
		
		if (ids.length()>0) {
			sql.append(",ui.second_device_type ");
		}
		
		sql.append("union "
				+ "select co.id as orgId,co.org_name as orgName,"
				+ "case ci.second_device_type when 'ROUTER' then '路由器' when 'FIREWALL' then '防火墙' "
			    + "when 'SWITCHBOARD' then '交换机' else '其他' end as deviceType,"
			    + "cp.record_time as date,count(1) as quantity "
				+ "from collection_device cd,problem cp,device_purchase cu,"
				+ "device_inventory ci,organization co "
				+ "where cp.identifier=cd.identifier and cd.purchase_id=cu.id "
				+ "and cd.organization_id=co.id and cu.device_inventory_id=ci.id "
				+ "group by co.id ");
		
		if (ids.length()>0) {
			sql.append(",ci.second_device_type ");
		}
		
		sql.append("union "
				+ "select po.id as orgId,po.org_name as orgName,"
				+ "case pi.second_device_type when 'UPS' then 'UPS设备' when 'TH' then '温湿度' "
			    + "when 'WATER' then '水浸' when 'SMOKE' then '烟感' else '其他' end as deviceType,"
			    + "pp.record_time as date,count(1) as quantity "
				+ "from pe_device pd,problem pp,device_purchase pu,"
				+ "device_inventory pi,organization po "
				+ "where pp.identifier=pd.identifier and pd.purchase_id=pu.id "
				+ "and pd.organization_id=po.id and pu.device_inventory_id=pi.id "
				+ "group by po.id ");
		
		if (ids.length()>0) {
			sql.append(",pi.second_device_type ");
		}
		
		sql.append(") a where deviceType is not null ");
		
		if (deviceType != null && deviceType != "") {
			sql.append("and deviceType='" + SecondDeviceType.valueOf(deviceType).getName() + "' ");
		}
		if (ids != null && ids != "") {
			sql.append("and orgId in(" + ids + ") ");
		}
		if (beginDate != null && beginDate != "") {
			sql.append("and date>=str_to_date('" + beginDate + "','%Y-%m-%d') ");
		}
		if (endDate != null && endDate != "") {
			sql.append("and date<=str_to_date('" + endDate + "','%Y-%m-%d') ");
		}
		
		sql.append("group by orgId ");
		
		if (ids.length()>0) {
			sql.append(",deviceType ");
		}
		
		sql.append("order by quantity " + sort +" "
				 + "limit 0,"+ top);
		
		return  getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(DeviceFaultTopNBean.class)).list();

	}

}
