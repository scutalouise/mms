package com.agama.device.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class TwoDimentionCodeUtil {
	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	private TwoDimentionCodeUtil() {
	}

	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format " + format + " to " + file);
		}
	}

	public static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, stream)) {
			throw new IOException("Could not write an image of format " + format);
		}
	}

	public static String matrixToImage(Map<String, Object> map, String phone, HttpServletRequest request, String paths)
			throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(map);
		String identifier = request.getParameter("identifier");
		String phoneNumber = request.getParameter("phoneNumber");
		String phoneNumberText = TwoDimentionCodeUtil.phoneNumberText(phone, phoneNumber);
		// 服务器绝对路径
		String url = request.getSession().getServletContext().getRealPath("/");
		String separator = System.getProperties().getProperty("file.separator");
		// 验证文件夹是否存在
		File file = TwoDimentionCodeUtil.isExistDir(url, separator, "QRimages");
		String bgFile = TwoDimentionCodeUtil.isExistBg(file, separator, "background.png");
		String path = file + separator;
		String pathSuf = identifier + ".png";
		String pathSuf_edit = identifier + "_e.png";
		if (phoneNumber != null) {
			if (phoneNumber == "") {
				phoneNumberText = "";
			}
			TwoDimentionCodeUtil.IOImage(bgFile, path + pathSuf_edit, content, phoneNumberText, identifier);
			return pathSuf_edit;
		} else if (paths != null) {
			if (TwoDimentionCodeUtil.isExistFile(path + pathSuf_edit)) {
				TwoDimentionCodeUtil.IOImage(bgFile, path + pathSuf_edit, content, phoneNumberText, identifier);
			}
			return pathSuf_edit;
		} else {
			TwoDimentionCodeUtil.IOImage(bgFile, path + pathSuf, content, phoneNumberText, identifier);
			return pathSuf;
		}
	}

	/**
	 * @Description 生成二维码
	 * @param bgPath
	 * @param path
	 * @param content
	 * @param phoneNumberText
	 * @param identifier
	 * @Since 2016年2月3日 下午2:23:12
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void IOImage(String bgPath, String path, String content, String phoneNumberText, String identifier) {
		int width = 200;
		int height = 200;
		// 二维码的图片格式
		String format = "png";
		Hashtable hints = new Hashtable();
		// 纠错等级
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		// 内容所使用编码
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		try {
			File file = new File(path);
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
			OutputStream os = new FileOutputStream(file);
			// 生成二维码
			TwoDimentionCodeUtil.writeToStream(bitMatrix, format, os);
			// 合并图片
			TwoDimentionCodeUtil.merge(bgPath, path, file);
			os.flush();
			os.close(); 
			BufferedImage buffImage = ImageIO.read(file);
			Graphics2D g2 = buffImage.createGraphics();
			g2.setColor(Color.BLACK); 
			Font head = new Font("黑体", Font.BOLD,20); 
			g2.setFont(head);
			g2.drawString(identifier, 22, 17); 
			Font foot = new Font("黑体", Font.BOLD, 14);
			g2.setFont(foot);
			g2.drawString(phoneNumberText, 23, 204);
			g2.dispose();
			OutputStream ost = new FileOutputStream(file); // 顺时针旋转90度
			buffImage = TwoDimentionCodeUtil.rotateImage(buffImage, 90);
			ImageIO.write(buffImage, "png", ost);
			ost.flush();
			ost.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Description 图片合并
	 * @param bgPath
	 * @param qrbgPath
	 * @param file
	 * @Since 2016年2月3日 下午2:22:42
	 */
	public static void merge(String bgPath, String qrbgPath, File file) {
		try {
			BufferedImage bgImage = ImageIO.read(new File(bgPath));
			Graphics2D g2 = bgImage.createGraphics();
			BufferedImage qrImage = ImageIO.read(new File(qrbgPath));
			int width = qrImage.getWidth();
			int height = qrImage.getHeight();
			int x = (bgImage.getWidth() - width) / 2;
			int y = (bgImage.getHeight() - height) / 2;
			g2.drawImage(qrImage, x, y, width, height, null);
			g2.drawRect(x, y, width, height);
			g2.dispose();
			OutputStream os = new FileOutputStream(file);
			ImageIO.write(bgImage, "png", os);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Description 图片旋转
	 * @param bufferedimage
	 * @param degree
	 *            角度
	 * @return
	 * @Since 2016年2月3日 下午2:21:59
	 */
	public static BufferedImage rotateImage(BufferedImage bufferedimage, int degree) {
		int w = bufferedimage.getWidth();
		int h = bufferedimage.getHeight();
		int type = bufferedimage.getColorModel().getTransparency();
		BufferedImage img;
		Graphics2D graphics2d;
		(graphics2d = (img = new BufferedImage(w, h, type)).createGraphics())
				.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);
		graphics2d.drawImage(bufferedimage, 0, 0, null);
		graphics2d.dispose();
		return img;
	}
	
	/**
	 * @Description 报修电话文本
	 * @param phone
	 * @param phoneNumber
	 * @return
	 * @Since 2016年2月3日 下午2:21:31
	 */
	public static String phoneNumberText(String phone, String phoneNumber) {
		String phoneNumberText;
		if (phoneNumber == null) {
			if (phone == null || phone == "") {
				phoneNumberText = "";
			} else {
				phoneNumberText = "报修电话：" + phone;// 默认管理员电话
			}
		} else {
			phoneNumberText = "报修电话：" + phoneNumber;
		}
		return phoneNumberText;
	}

	/**
	 * @Description 判断图片文件夹是否存在
	 * @param url
	 * @param dir
	 * @return
	 * @Since 2016年2月3日 下午2:19:53
	 */
	public static File isExistDir(String url, String separator, String dir) {
		File file = new File(url + separator + dir);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}
		return file;
	}

	/**
	 * @Description 判断二维码图片是否存在
	 * @param path
	 * @return
	 * @Since 2016年2月3日 下午2:20:43
	 */
	public static boolean isExistFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @Description 判断背景图片是否存在
	 * @param file
	 * @return
	 * @Since 2016年2月3日 下午2:20:43
	 */
	public static String isExistBg(File file, String separator, String fileSuf) {
		String bgPath = file + separator + fileSuf;
		File bgFile = new File(bgPath);
		if (!bgFile.exists()) {
			// 生成背景图片
			int width = 210;
			int height = 210;
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					image.setRGB(x, y, WHITE);
				}
			}
			try {
				ImageIO.write(image, "png", bgFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bgPath;
	}
}
