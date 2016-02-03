package com.agama.tool.utils.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;


/**
 * 应用的工具类-用于定义一些应用级的常量和方法
 * 
 */
public class EncryptionUtils {

	/**
	 * MD5数据加密-字符型
	 * 
	 * @param plainText
	 *            待加密字符
	 * @param Md5Type
	 *            加密类型 16 或 32位加密 ，默认32位加密
	 * @return 返回MD5加密后的字符
	 */
	public static final String GetMD5(String plainText, String Md5Type) {
		String str = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			if (Md5Type.equals("16")) {
				str = buf.toString().substring(8, 24);// 16位的加密
			} else {
				str = buf.toString();// 32位的加密
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 生成GUID 全球唯一的ID
	 * 
	 * @return
	 */
	public static final String GetGUID() {
		UUID uuid = UUID.randomUUID();
		String ruid = "";
		if (uuid.toString().length() <= 32) {
			ruid = uuid.toString();
		} else {
			ruid = uuid.toString().replace("-", "");
		}
		return ruid;
	}

	

	
	
}
