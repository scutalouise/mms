package com.agama.itam.util;

import com.agama.authority.entity.Organization;

/**
 * @Description:
 * @Author:佘朝军
 * @Since :2015年12月22日 下午2:12:53
 */
public class HandsetUtils {

	public static final double ERROR_RANGE = 0.010000;

	public static final String PROPERTIES_FILE_PATH = "/application.properties";

	public static boolean coordinateCheck(double latitude, double longitude, Organization org) {
		double resultLatitude = Math.abs(latitude - org.getLatitude());
		double resultLongitude = Math.abs(longitude - org.getLongitude());
		return (resultLatitude < ERROR_RANGE && resultLongitude < ERROR_RANGE);
	}

}
