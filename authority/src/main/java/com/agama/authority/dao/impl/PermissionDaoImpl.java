package com.agama.authority.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.agama.authority.dao.IPermissionDao;
import com.agama.authority.entity.Permission;
import com.agama.common.dao.impl.HibernateDaoImpl;
import com.agama.common.enumbean.EnabledStateEnum;
import com.agama.common.enumbean.StatusEnum;

/**
 * @Description:权限DAO实现
 * @Author:scuta
 * @Since :2015年8月27日 上午9:38:25
 */
@Repository("permissionDao")
public class PermissionDaoImpl extends HibernateDaoImpl<Permission, Integer> implements IPermissionDao {

	/**
	 * 查询用户拥有的权限
	 * 
	 * @param userId
	 *            用户id
	 * @return 结果集合
	 */
	@SuppressWarnings("unchecked")
	public List<Permission> findPermissions(Integer userId,String code) {
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct p.* from permission p ");
		sb.append("INNER JOIN role_permission rp on p.ID=rp.PERMISSION_ID ");
		sb.append("INNER JOIN role r ON  r.id=rp.ROLE_ID ");
		sb.append("INNER JOIN user_role  ur ON ur.ROLE_ID =rp.ROLE_ID ");
		sb.append("INNER JOIN user u ON u.id = ur.USER_ID ");
		sb.append("where u.id=?0 ");
		if(code!=null){
			sb.append("and p.code='").append(code).append("'");
		}
		appendJudgeStatus(sb,"p");
		appendJudgeStatus(sb,"r");
		appendJudgeStatus(sb,"u");
		sb.append("order by p.sort");
		SQLQuery sqlQuery = createSQLQuery(sb.toString(), userId);
		sqlQuery.addEntity(Permission.class);
		// sqlQuery.setCacheable(true);
		return sqlQuery.list();
	}

	/**
	 * 查询所有的菜单
	 * 
	 * @param userId
	 * @return 菜单集合
	 */
	@SuppressWarnings("unchecked")
	public List<Permission> findMenus() {
		StringBuffer sb = new StringBuffer();
		sb.append("select p.ID id,p.PID pid,p.NAME name,p.URL url,p.ICON icon,p.SORT sort,p.COdE code,p.DESCRIPTION description from permission p ");
		sb.append("where p.TYPE='F' ");
		appendJudgeStatus(sb,"p");
		sb.append(" order by p.sort ");
		
		SQLQuery sqlQuery = createSQLQuery(sb.toString());
		sqlQuery.addScalar("id", StandardBasicTypes.INTEGER);
		sqlQuery.addScalar("pid", StandardBasicTypes.INTEGER);
		sqlQuery.addScalar("name", StandardBasicTypes.STRING);
		sqlQuery.addScalar("url", StandardBasicTypes.STRING);
		sqlQuery.addScalar("icon", StandardBasicTypes.STRING);
		
		sqlQuery.addScalar("sort", StandardBasicTypes.INTEGER);
		sqlQuery.addScalar("code",StandardBasicTypes.STRING);
		sqlQuery.addScalar("description", StandardBasicTypes.STRING);
		sqlQuery.setResultTransformer(Transformers.aliasToBean(Permission.class));
		// sqlQuery.setCacheable(true);
		return sqlQuery.list();
	}

	/**
	 * 查询用户拥有的菜单权限
	 * 
	 * @param userId
	 * @return 结果集合
	 */
	@SuppressWarnings("unchecked")
	public List<Permission> findMenus(Integer userId) {
		StringBuffer sb = new StringBuffer();
		sb.append("select p.ID id,p.PID pid,p.NAME name,p.URL url,p.ICON icon,p.SORT sort,p.DESCRIPTION description from permission p ");
		sb.append("INNER JOIN role_permission rp on p.ID=rp.PERMISSION_ID ");
		sb.append("INNER JOIN role r ON r.id=rp.ROLE_ID ");
		sb.append("INNER JOIN user_role ur ON ur.ROLE_ID =rp.ROLE_ID ");
		sb.append("INNER JOIN user u ON u.id = ur.USER_ID ");
		sb.append("where p.TYPE='F' and u.id=?0 ");
		appendJudgeStatus(sb, "p");//对状态进行一个过滤
		appendJudgeStatus(sb, "r");
		appendJudgeStatus(sb, "u");
		sb.append("order by p.sort");
		SQLQuery sqlQuery = createSQLQuery(sb.toString(), userId);
		sqlQuery.addScalar("id", StandardBasicTypes.INTEGER);
		sqlQuery.addScalar("pid", StandardBasicTypes.INTEGER);
		sqlQuery.addScalar("name", StandardBasicTypes.STRING);
		sqlQuery.addScalar("url", StandardBasicTypes.STRING);
		sqlQuery.addScalar("icon", StandardBasicTypes.STRING);
		sqlQuery.addScalar("sort", StandardBasicTypes.INTEGER);
		sqlQuery.addScalar("description", StandardBasicTypes.STRING);
		sqlQuery.setResultTransformer(Transformers.aliasToBean(Permission.class));
		// sqlQuery.setCacheable(true);
		return sqlQuery.list();
	}

	/**
	 * 查询菜单下的操作权限
	 * 
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Permission> findMenuOperation(Integer pid) {
		StringBuffer sb = new StringBuffer();
		sb.append("select p.ID id,p.NAME name,p.URL url,p.PERM_CODE permCode,p.DESCRIPTION description from permission p ");
		sb.append("where p.TYPE='O' and p.PID=?0 ");
		appendJudgeStatus(sb,"p");
		sb.append("order by p.SORT");
		SQLQuery sqlQuery = createSQLQuery(sb.toString(), pid);
		sqlQuery.addScalar("id", StandardBasicTypes.INTEGER);
		sqlQuery.addScalar("name", StandardBasicTypes.STRING);
		sqlQuery.addScalar("url", StandardBasicTypes.STRING);
		sqlQuery.addScalar("permCode", StandardBasicTypes.STRING);
		sqlQuery.addScalar("description", StandardBasicTypes.STRING);
		sqlQuery.setResultTransformer(Transformers.aliasToBean(Permission.class));
		// sqlQuery.setCacheable(true);
		return sqlQuery.list();
	}
	
	/**
	 * @Description:添加了enabled,status状态后，新增一个工具，增加sql语句的判断；
	 * @param sb
	 * @Since :2016年2月25日 下午2:15:54
	 */
	private void appendJudgeStatus(StringBuffer sb, String abbr) {
		sb.append(" and "+ abbr +".enable='"+ EnabledStateEnum.ENABLED +"' ");
		sb.append(" and "+ abbr +".status='"+ StatusEnum.NORMAL +"' ");
	}

	/**
	 * 查询当前pid下的所有Permission,包括操作与菜单；
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Permission> findAllPermissionsByPid(Integer pid) {
		StringBuffer sb = new StringBuffer();
		sb.append("select p.* from permission p ");
		sb.append("where p.PID=?0 ");
		appendJudgeStatus(sb,"p");
		sb.append("order by p.SORT");
		SQLQuery sqlQuery = createSQLQuery(sb.toString(), pid);
		sqlQuery.addEntity(Permission.class);
		return sqlQuery.list();
	}
}
