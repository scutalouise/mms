package com.agama.itam.dao.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.agama.authority.service.IOrganizationService;
import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.dao.utils.Page;
import com.agama.common.enumbean.ProblemStatusEnum;
import com.agama.itam.bean.DeviceMaintenanceChartBean;
import com.agama.itam.dao.IMaintenanceChartDao;

@Repository
public class MaintenanceChartDaoImpl extends HibernateDaoImpl<DeviceMaintenanceChartBean, Integer> implements IMaintenanceChartDao{

	@Autowired
	private IOrganizationService organizationService;
	
	@Override
	public Page<DeviceMaintenanceChartBean> findPageBySQL(Page<DeviceMaintenanceChartBean> page,
			HttpServletRequest request) {
		
		String ids="";
		String	name = request.getParameter("name");
		String	problemTypeId = request.getParameter("problemTypeId");
		String	problemStatus = request.getParameter("problemStatus");
		String	orgId = request.getParameter("orgId");
		String	GT_recordTime = request.getParameter("GTD_recordTime");
		String	LT_recordTime = request.getParameter("LTD_recordTime");
		if (orgId != null && orgId !="") {
			ids = organizationService.getOrganizationIdStrById(Integer.parseInt(orgId));
		}
		
		StringBuffer sql = new StringBuffer(
				  "select a.* from "
				+ "(select hd.name as name,hd.organization_id as orgId,ho.org_name as orgName,"
				+ "hp.identifier as identifier,hp.record_time as recordTime,hp.resolve_time as resolveTime,"
				+ "hp.problem_type_id as problemTypeId,hp.id as problemId,"
				+ "(select t.name from problem_type t where hp.problem_type_id=t.id) as problemType,"
				+ "(select u.name from user u where hp.record_user_id=u.id) as recordUserName,"
				+ "(select u.name from user u where hp.resolve_user_id=u.id) as resolveUserName,"
				+ "hp.description as description,case hp.report_way when '0' then '电话报修' "
				+ "when '1' then '口头报备' when '2' then '手持机' else '其他' end as reportWay,"
				+ "case hp.enable when 'NEW' then '新问题' when 'CALLBACK' then '打回' when 'ASSIGNED' then '已分配' "
				+ "when 'HANDLING' then '处理中' when 'RESOLVED' then '已解决' when 'CLOSED' then '已关闭' else '其他' end as enable "
				+ "from host_device hd,organization ho,problem hp "
				+ "where hp.status=0 and hp.identifier=hd.identifier and ho.id=hd.organization_id ");
	    
		sql.append("union "
				+ "select nd.name as name,nd.organization_id as orgId,no.org_name as orgName,"
				+ "np.identifier as identifier,np.record_time as recordTime,np.resolve_time as resolveTime,"
				+ "np.problem_type_id as problemTypeId,np.id as problemId,"
				+ "(select t.name from problem_type t where np.problem_type_id=t.id) as problemType,"
				+ "(select u.name from user u where np.record_user_id=u.id) as recordUserName,"
				+ "(select u.name from user u where np.resolve_user_id=u.id) as resolveUserName,"
				+ "np.description as description,case np.report_way when '0' then '电话报修' "
				+ "when '1' then '口头报备' when '2' then '手持机' else '其他' end as reportWay,"
				+ "case np.enable when 'NEW' then '新问题' when 'CALLBACK' then '打回' when 'ASSIGNED' then '已分配' "
				+ "when 'HANDLING' then '处理中' when 'RESOLVED' then '已解决' when 'CLOSED' then '已关闭' else '其他' end as enable "
				+ "from network_device nd,organization no,problem np "
				+ "where np.status=0 and np.identifier=nd.identifier and no.id=nd.organization_id ");

		sql.append("union "
				+ "select ud.name as name,ud.organization_id as orgId,uo.org_name as orgName,"
				+ "up.identifier as identifier,up.record_time as recordTime,up.resolve_time as resolveTime,"
				+ "up.problem_type_id as problemTypeId,up.id as problemId,"
				+ "(select t.name from problem_type t where up.problem_type_id=t.id) as problemType,"
				+ "(select u.name from user u where up.record_user_id=u.id) as recordUserName,"
				+ "(select u.name from user u where up.resolve_user_id=u.id) as resolveUserName,"
				+ "up.description as description,case up.report_way when '0' then '电话报修' "
				+ "when '1' then '口头报备' when '2' then '手持机' else '其他' end as reportWay,"
				+ "case up.enable when 'NEW' then '新问题' when 'CALLBACK' then '打回' when 'ASSIGNED' then '已分配' "
				+ "when 'HANDLING' then '处理中' when 'RESOLVED' then '已解决' when 'CLOSED' then '已关闭' else '其他' end as enable "
				+ "from unintelligent_device ud,organization uo,problem up "
				+ "where up.status=0 and up.identifier=ud.identifier and uo.id=ud.organization_id ");
			
		sql.append("union "
				+ "select cd.name as name,cd.organization_id as orgId,co.org_name as orgName,"
				+ "cp.identifier as identifier,cp.record_time as recordTime,cp.resolve_time as resolveTime,"
				+ "cp.problem_type_id as problemTypeId,cp.id as problemId,"
				+ "(select t.name from problem_type t where cp.problem_type_id=t.id) as problemType,"
				+ "(select u.name from user u where cp.record_user_id=u.id) as recordUserName,"
				+ "(select u.name from user u where cp.resolve_user_id=u.id) as resolveUserName,"
				+ "cp.description as description,case cp.report_way when '0' then '电话报修' "
				+ "when '1' then '口头报备' when '2' then '手持机' else '其他' end as reportWay,"
				+ "case cp.enable when 'NEW' then '新问题' when 'CALLBACK' then '打回' when 'ASSIGNED' then '已分配' "
				+ "when 'HANDLING' then '处理中' when 'RESOLVED' then '已解决' when 'CLOSED' then '已关闭' else '其他' end as enable "
				+ "from collection_device cd,organization co,problem cp "
				+ "where cp.status=0 and cp.identifier=cd.identifier and co.id=cd.organization_id ");
			
		sql.append("union "
				+ "select pd.name as name,pd.organization_id as orgId,po.org_name as orgName,"
				+ "pp.identifier as identifier,pp.record_time as recordTime,pp.resolve_time as resolveTime,"
				+ "pp.problem_type_id as problemTypeId,pp.id as problemId,"
				+ "(select t.name from problem_type t where pp.problem_type_id=t.id) as problemType,"
				+ "(select u.name from user u where pp.record_user_id=u.id) as recordUserName,"
				+ "(select u.name from user u where pp.resolve_user_id=u.id) as resolveUserName,"
				+ "pp.description as description,case pp.report_way when '0' then '电话报修' "
				+ "when '1' then '口头报备' when '2' then '手持机' else '其他' end as reportWay,"
				+ "case pp.enable when 'NEW' then '新问题' when 'CALLBACK' then '打回' when 'ASSIGNED' then '已分配' "
				+ "when 'HANDLING' then '处理中' when 'RESOLVED' then '已解决' when 'CLOSED' then '已关闭' else '其他' end as enable "
				+ "from pe_device pd,organization po,problem pp "
				+ "where pp.status=0 and pp.identifier=pd.identifier and po.id=pd.organization_id) a "
				+ "where name is not null ");
			
			if (name != null && name != "") {
				sql.append("and name like '%" + name.trim() + "%' ");
			}
			if (problemTypeId != null && problemTypeId != "") {
				sql.append("and problemTypeId=" + Integer.parseInt(problemTypeId) + " ");
			}
			if (problemStatus != null && problemStatus != "") {
				sql.append("and enable='" + ProblemStatusEnum.valueOf(problemStatus).getText() + "' ");
			}
			if (ids != null && ids != "") {
				sql.append("and orgId in(" + ids + ") ");
			}
			if (GT_recordTime != null && GT_recordTime != "") {
				sql.append("and recordTime>=str_to_date('" + GT_recordTime + "','%Y-%m-%d') ");
			}
			if (LT_recordTime != null && LT_recordTime != "") {
				sql.append("and recordTime<=str_to_date('" + LT_recordTime + "','%Y-%m-%d') ");
			}
			
			sql.append("order by orgId,recordTime");
			//return  getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.aliasToBean(DeviceMaintenanceChartBean.class)).list();
	       return findPageBySQL(page, sql.toString());
	}

}
