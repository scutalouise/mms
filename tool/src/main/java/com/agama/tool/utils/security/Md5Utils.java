package com.agama.tool.utils.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Md5Utils {

    private static final Logger LOGGER = LoggerFactory.getLogger(Md5Utils.class);


    private static byte[] md5(String s) {
        MessageDigest algorithm;
        try {
            algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(s.getBytes("UTF-8"));
            byte[] messageDigest = algorithm.digest();
            return messageDigest;
        } catch (Exception e) {
            LOGGER.error("MD5 Error...", e);
        }
        return null;
    }

    private static final String toHex(byte hash[]) {
        if (hash == null) {
            return null;
        }
        StringBuffer buf = new StringBuffer(hash.length * 2);
        int i;

        for (i = 0; i < hash.length; i++) {
            if ((hash[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString(hash[i] & 0xff, 16));
        }
        return buf.toString();
    }

    public static String hash(String s) {
        try {
            return new String(toHex(md5(s)).getBytes("UTF-8"), "UTF-8");
        } catch (Exception e) {
            LOGGER.error("not supported charset...{}", e);
            return s;
        }
    }
    
    /**
	 * @Description:根据指定字符串，获取MD5码经自定义加密后信息
	 * @param s 指定字符串
	 * @return 自定义加密字符串
	 * @Author:佘朝军
	 * @Since :2015年11月18日 上午9:43:10
	 */
	public final static String getCustomMD5(String s) {
		try {
			char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
					'9', 'a', 'b', 'c', 'd', 'e', 'f' };
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				// 交换每一位字节编码的位置
				str[k++] = hexDigits[byte0 & 0xf];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			}
			return getCustomEncryption(str);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @Description:对MD5加密后的信息进行两次自定义排序作为第二层自定义加密
	 * @param charArr
	 * @return string
	 * @Author:佘朝军
	 * @Since :2015年11月18日 上午10:01:07
	 */
	private static String getCustomEncryption(char[] charArr) {
		// 将字符数组进行全倒序
		char reChar[] = new char[charArr.length];
		for (int i = 0; i < charArr.length; i++) {
			reChar[i] = charArr[charArr.length - i - 1];
		}
		// 将得到的字符数组按照等差数列关系进行第二次倒序排列
		int an = 1;
		int bn = 2;
		int index = 1;
		int checkNum = 0;
		char resultChar[] = new char[reChar.length];
		for (int i = 0; i < reChar.length; i++) {
			resultChar[i] = reChar[checkNum + bn - index];
			index++;
			if (index > bn) {
				an = an + 2;
				bn = 2 * an;
				index = 1;
				checkNum = i + 1;
			}
		}
		return new String(resultChar);
	}
	
	/**
	 * 得到指定文件MD5信息
	 */
	public static String getMD5(File file) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		FileInputStream fis = null;
		MessageDigest md = null;
		byte[] buf = new byte[2048];

		try {
			md = MessageDigest.getInstance("MD5");
			fis = new FileInputStream(file);
			int num = fis.read(buf);
			while (num != (-1)) {
				md.update(buf, 0, num);
				num = fis.read(buf);
			}
		} catch (FileNotFoundException ex) {
			LOGGER.error("输入的文件未能找到..", ex);
			ex.printStackTrace();
		} catch (IOException e) {
			LOGGER.error("输入的文件存在IO错误..", e);
			e.printStackTrace();
		} catch (NoSuchAlgorithmException ex) {
			LOGGER.error("获取文件的MD5值时出现错误..", ex);
			ex.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				LOGGER.error("输入的文件关闭失败..", e);
			}
		}
		byte[] tmd = md.digest();
		int j = tmd.length;
		char str[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
			byte byte0 = tmd[i];
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str[k++] = hexDigits[byte0 & 0xf];
		}
		return new String(str);
	}

	/**
	 * 得到指定数据输入流MD5信息
	 */
	public static String getMD5(InputStream stream) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		MessageDigest md = null;
		byte[] buf = new byte[2048];

		try {
			md = MessageDigest.getInstance("MD5");
			int num = stream.read(buf);
			while (num != (-1)) {
				md.update(buf, 0, num);
				num = stream.read(buf);
			}
		} catch (FileNotFoundException ex) {
			LOGGER.error("输入的文件流未能找到..", ex);
			ex.printStackTrace();
		} catch (IOException e) {
			LOGGER.error("输入的文件流存在IO错误..", e);
			e.printStackTrace();
		} catch (NoSuchAlgorithmException ex) {
			LOGGER.error("获取文件流的MD5值时出现错误..", ex);
			ex.printStackTrace();
		}
		byte[] tmd = md.digest();
		int j = tmd.length;
		char str[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
			byte byte0 = tmd[i];
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str[k++] = hexDigits[byte0 & 0xf];
		}
		return new String(str);
	}


}
