package com.agama.itam.util;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

/**
 * @Description:根据实体获取表名，或根据表名获取实体的名字与id名字；
 * @Author:杨远高
 * @Since :2016年1月29日 上午11:24:22
 */
public class EntityUtils {

	@Autowired
	private LocalSessionFactoryBean sessionFactory;
	
	private Map<String, String> tableEntityMappings;
	private Map<String, String> entityTableMappings;
	private Map<String, String> entityIdMappings;
	
	/**
	 * @Description:初始化，获得Spring配置的sessionFactory；再得到hibernate的sessionFactory实现；并封装数据到map中；
	 * 
	 * @Since :2016年1月29日 上午11:24:51
	 */
	private void initMappings() {
	    if (tableEntityMappings == null) {
	        tableEntityMappings = new HashMap<String, String>();
	        entityIdMappings = new HashMap<String, String>();
	        Map<String, ClassMetadata> metaMap = sessionFactory.getObject().getAllClassMetadata();//获得hibernate的SessionFactory再来处理；
	        for (String key : metaMap.keySet()) {
	            AbstractEntityPersister classMetadata = (AbstractEntityPersister) metaMap.get(key);
	            String tableName = classMetadata.getTableName().toLowerCase();
	            int index = tableName.indexOf(".");
	            if (index >= 0) {
	                tableName = tableName.substring(index + 1);
	            }
	            String className = classMetadata.getEntityMetamodel().getName();
	            String idName = classMetadata.getIdentifierColumnNames()[0];
	            tableEntityMappings.put(tableName, className);
	            entityTableMappings.put(className, tableName);
	            entityIdMappings.put(className, idName);
	        }
	    }
	}
	 
	/**
	 * @Description:根据实体名获取表名
	 * @param tableName
	 * @return
	 * @Since :2016年1月29日 上午11:26:02
	 */
	public String getEntityNameByTableName(String tableName) {
	    initMappings();
	    return tableEntityMappings.get(tableName);
	}
	 
	/**
	 * @Description:根据实体名获取id名字；
	 * @param entityName
	 * @return
	 * @Since :2016年1月29日 上午11:26:19
	 */
	public String getIdNameByEntityName(String entityName) {
	    initMappings();
	    return entityIdMappings.get(entityName);
	}
	
	/**
	 * @Description:根据实体名字获取表名
	 * @param entityName
	 * @return
	 * @Since :2016年1月29日 上午11:27:15
	 */
	public String getTableNameByEntity(String entityName){
		initMappings();
		return entityTableMappings.get(entityName);
	}
}
