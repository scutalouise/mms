package com.agama.device.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
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
		// 服务器绝对路径
		String url = request.getSession().getServletContext().getRealPath("/");
		File file = new File(url + "QRimages");
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}
		String path = file + "\\" + identifier + ".png";
		String path_edit = file + "\\" + identifier + "_e.png";
		String pathSuf = identifier + ".png";
		String pathSuf_edit = identifier + "_e.png";
		if (phoneNumber != null) {
			if (phoneNumber == "") {
				phoneNumberText = "";
			}
			TwoDimentionCodeUtil.IOImage(path_edit, content, phoneNumberText, identifier);
			return pathSuf_edit;
		} else if (paths != null) {
			return pathSuf_edit;
		} else {
			TwoDimentionCodeUtil.IOImage(path, content, phoneNumberText, identifier);
			return pathSuf;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void IOImage(String path, String content, String phoneNumberText, String identifier) {
		int width = 200;
		int height = 200;
		// 二维码的图片格式
		String format = "png";
		Hashtable hints = new Hashtable();
		// 内容所使用编码
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		try {
			File file = new File(path);
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
			OutputStream os = new FileOutputStream(file);
			// 生成二维码
			TwoDimentionCodeUtil.writeToStream(bitMatrix, format, os);
			// 顺时针旋转90度
			BufferedImage buffImg = ImageIO.read(file);
			buffImg = TwoDimentionCodeUtil.rotateImage(buffImg, 90);
			ImageIO.write(buffImg, "png", file);
			os.flush();
			os.close();
			BufferedImage buffImage = ImageIO.read(file);
			buffImage = TwoDimentionCodeUtil.rotateImage(buffImage, -90);
			Graphics g = buffImage.getGraphics();
			Font head = new Font("黑体", Font.BOLD, 20);
			Color color = new Color(BLACK);
			g.setFont(head);
			g.setColor(color);
			g.drawString(identifier, 18, 16);
			Font foot = new Font("黑体", Font.BOLD, 14);
			g.setFont(foot);
			g.drawString(phoneNumberText, 20, 196);
			OutputStream outImg = new FileOutputStream(file);
			buffImage = TwoDimentionCodeUtil.rotateImage(buffImage, 90);
			ImageIO.write(buffImage, "png", outImg);
			outImg.flush();
			outImg.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
	}

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
}
