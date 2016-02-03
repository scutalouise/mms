package com.agama.das.protocol.snmp.mib;

/**
 * @Description:接口类，用于定义oid操作方法
 * @Author:佘朝军
 * @Since :2015年11月19日 下午2:34:39
 */
public interface MibOID {

	public String getOID();

	public int getMode();

	public String getParent();

	public int size();

}
