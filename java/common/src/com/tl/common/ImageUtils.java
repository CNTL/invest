package com.tl.common;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;


/**
 * ͼƬ�������ࣺ<br>
 * ���ܣ�����ͼ���и�ͼ��ͼ������ת������ɫת�ڰס�����ˮӡ��ͼƬˮӡ��
 * 
 * @author Administrator
 */
public class ImageUtils {
	/**
	 * ���ֳ�����ͼƬ��ʽ
	 */
	public static String IMAGE_TYPE_GIF = "gif";// ͼ�ν�����ʽ
	public static String IMAGE_TYPE_JPG = "jpg";// ������Ƭר����
	public static String IMAGE_TYPE_JPEG = "jpeg";// ������Ƭר����
	public static String IMAGE_TYPE_BMP = "bmp";// Ӣ��Bitmap��λͼ���ļ�д������Windows����ϵͳ�еı�׼ͼ���ļ���ʽ
	public static String IMAGE_TYPE_PNG = "png";// ����ֲ����ͼ��
	public static String IMAGE_TYPE_PSD = "psd";// Photoshop��ר�ø�ʽPhotoshop

	/**
	 * ������ڣ����ڲ���
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// 1-����ͼ��
		// ����һ������������
		// ImageUtils.scale("c:/abc.jpg", "c:/abc_scale.jpg", 2, false);// ����OK
		// �����������߶ȺͿ������
		//ImageUtils.scale("c:/abc.jpg", "c:/abc_scale2.jpg", 503, 752, true);// ����OK
		// 2-�и�ͼ�� ImageUtils.cut("c:/abc.jpg", "c:/abc_cut.jpg", 0, 0, 400,400);// ����OK
		// 3-ͼ������ת���� ImageUtils.convert("c:/abc.jpg", "GIF","c:/abc_convert.gif");// ����OK
		// 4-��ɫת�ڰף� ImageUtils.gray("c:/abc.jpg", "c:/abc_gray.jpg");// ����OK
		// 5-��ͼƬ�������ˮӡ�� ImageUtils.pressText("����ˮӡ����", "c:/abc.jpg","c:/abc_pressText.jpg", "����", Font.BOLD, Color.white, 80, 0, 0,0.5f);// ����OK
		// 6-��ͼƬ���ͼƬˮӡ�� ImageUtils.pressImage("c:/press.jpg", "c:/abc.jpg","c:/abc_pressImage.jpg", 0, 0, 0.5f);// ����OK
		// 7-��תͼƬ��ImageUtils.rotateImage("c:/abc.jpg", "c:/abc_rotateImage.jpg", 60 , 250 ,350);// ����OK
	}

	/**
	 * ����ͼ�񣨰��������ţ�
	 * 
	 * @param srcImageFile
	 *            Դͼ���ļ���ַ
	 * @param result
	 *            ���ź��ͼ���ַ
	 * @param scale
	 *            ���ű���
	 * @param flag
	 *            ����ѡ��:true �Ŵ�; false ��С;
	 */
	public final static void scale(String srcImageFile, String result,
			int scale, boolean flag) {
		try {
			BufferedImage src = ImageIO.read(new File(srcImageFile)); // �����ļ�
			int width = src.getWidth(); // �õ�Դͼ��
			int height = src.getHeight(); // �õ�Դͼ��
			if (flag) {// �Ŵ�
				width = width * scale;
				height = height * scale;
			} else {// ��С
				width = width / scale;
				height = height / scale;
			}
			Image image = src.getScaledInstance(width, height,
					Image.SCALE_DEFAULT);
			BufferedImage tag = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // ������С���ͼ
			g.dispose();
			ImageIO.write(tag, "JPEG", new File(result));// ������ļ���
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����ͼ�񣨰��߶ȺͿ�����ţ�
	 * 
	 * @param srcImageFile
	 *            Դͼ���ļ���ַ
	 * @param result
	 *            ���ź��ͼ���ַ
	 * @param height
	 *            ���ź�ĸ߶�
	 * @param width
	 *            ���ź�Ŀ��
	 * @param flag
	 *            �Ƿ񱣳ֿ�߱�,true���ֿ�߱ȣ�false������;
	 */
	public final static void scale(String srcImageFile, String result,
			int width, int height, boolean flag) {
		try {
			double ratio = 0.0; // ԭͼ���ű���
			File f = new File(srcImageFile);
			BufferedImage bi = ImageIO.read(f);
			int w = bi.getWidth(); // �õ�Դͼ��
			int h = bi.getHeight(); // �õ�Դͼ��
			ratio = (new Integer(w)).doubleValue()
					/ (new Integer(h)).doubleValue();
			int wDest = 0;
			int hDest = 0;
			if (flag) {
				if (ratio * height <= width) {
					// �Ը߶�Ϊ��׼���ȱ�������ͼƬ
					hDest = height;
					wDest = (int) (ratio * height);

					Image itemp = bi.getScaledInstance(wDest, hDest,
							Image.SCALE_SMOOTH);
					BufferedImage image = new BufferedImage(width, height,
							BufferedImage.TYPE_INT_RGB);
					Graphics2D g = image.createGraphics();
					g.setColor(new Color(Integer.parseInt("CCCCCC", 16)));
					g.fillRect(0, 0, width, height);
					g.drawImage(itemp, (width - wDest) / 2, 0, wDest, hDest,
							Color.white, null);
					g.dispose();

					ImageIO.write(image, "JPEG", new File(result));
				} else {
					// �Կ��Ϊ��׼���ȱ�������ͼƬ
					wDest = width;
					hDest = (int) (wDest / ratio);

					Image itemp = bi.getScaledInstance(wDest, hDest,
							Image.SCALE_SMOOTH);
					BufferedImage image = new BufferedImage(width, height,
							BufferedImage.TYPE_INT_RGB);
					Graphics2D g = image.createGraphics();
					g.setColor(new Color(Integer.parseInt("CCCCCC", 16)));
					g.fillRect(0, 0, width, height);
					g.drawImage(itemp, 0, (height - hDest) / 2, wDest, hDest,
							Color.white, null);
					g.dispose();

					ImageIO.write(image, "JPEG", new File(result));
				}
			} else {
				Image itemp = bi.getScaledInstance(width, height,
						Image.SCALE_SMOOTH);
				BufferedImage image = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);
				Graphics2D g = image.createGraphics();
				g.setColor(new Color(Integer.parseInt("CCCCCC", 16)));
				g.fillRect(0, 0, width, height);
				g.drawImage(itemp, 0, 0, itemp.getWidth(null), itemp
						.getHeight(null), Color.white, null);
				itemp = image;
				ImageIO.write((BufferedImage) itemp, "JPEG", new File(result));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����ͼ�񣨰��߶ȺͿ�����ţ�
	 * 
	 * @param srcImageFile
	 *            Դͼ���ļ���ַ
	 * @param result
	 *            ���ź��ͼ���ַ
	 * @param height
	 *            ���ź�ĸ߶�
	 * @param width
	 *            ���ź�Ŀ��
	 * @param flag
	 *            �Ƿ񱣳ֿ�߱�,true���ֿ�߱ȣ�false������;
	 */
	public final static BufferedImage scale(BufferedImage bi, int width,
			int height, boolean flag) {
		try {
			double ratio = 0.0; // ԭͼ���ű���
			int w = bi.getWidth(); // �õ�Դͼ��
			int h = bi.getHeight(); // �õ�Դͼ��
			ratio = (new Integer(w)).doubleValue()
					/ (new Integer(h)).doubleValue();
			int wDest = 0;
			int hDest = 0;
			if (flag) {
				if (ratio * height <= width) {
					// �Ը߶�Ϊ��׼���ȱ�������ͼƬ
					hDest = height;
					wDest = (int) (ratio * height);

					Image itemp = bi.getScaledInstance(wDest, hDest,
							Image.SCALE_SMOOTH);
					BufferedImage image = new BufferedImage(width, height,
							BufferedImage.TYPE_INT_RGB);
					Graphics2D g = image.createGraphics();
					g.setColor(new Color(Integer.parseInt("CCCCCC", 16)));
					g.fillRect(0, 0, width, height);
					g.drawImage(itemp, (width - wDest) / 2, 0, wDest, hDest,
							Color.white, null);
					g.dispose();

					return image;
				} else {
					// �Կ��Ϊ��׼���ȱ�������ͼƬ
					wDest = width;
					hDest = (int) (wDest / ratio);

					Image itemp = bi.getScaledInstance(wDest, hDest,
							Image.SCALE_SMOOTH);
					BufferedImage image = new BufferedImage(width, height,
							BufferedImage.TYPE_INT_RGB);
					Graphics2D g = image.createGraphics();
					g.setColor(new Color(Integer.parseInt("CCCCCC", 16)));
					g.fillRect(0, 0, width, height);
					g.drawImage(itemp, 0, (height - hDest) / 2, wDest, hDest,
							Color.white, null);
					g.dispose();

					return image;
				}
			} else {
				Image itemp = bi.getScaledInstance(width, height,
						Image.SCALE_SMOOTH);
				BufferedImage image = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);
				Graphics2D g = image.createGraphics();
				g.setColor(new Color(Integer.parseInt("CCCCCC", 16)));
				g.fillRect(0, 0, width, height);
				g.drawImage(itemp, 0, 0, itemp.getWidth(null), itemp
						.getHeight(null), Color.white, null);
				itemp = image;
				return image;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ͼ���и�(��ָ���������Ϳ���и�)
	 * 
	 * @param srcImageFile
	 *            Դͼ���ַ
	 * @param result
	 *            ��Ƭ���ͼ���ַ
	 * @param x
	 *            Ŀ����Ƭ�������X
	 * @param y
	 *            Ŀ����Ƭ�������Y
	 * @param width
	 *            Ŀ����Ƭ���
	 * @param height
	 *            Ŀ����Ƭ�߶�
	 */
	public final static void cut(String srcImageFile, String result, int x,
			int y, int width, int height) {
		try {
			// ��ȡԴͼ��
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getHeight(); // Դͼ���
			int srcHeight = bi.getWidth(); // Դͼ�߶�
			if (srcWidth > 0 && srcHeight > 0) {
				// �ĸ������ֱ�Ϊͼ���������Ϳ��
				// ��: CropImageFilter(int x,int y,int width,int height)
				ImageFilter cropFilter = new CropImageFilter(x, y, width,
						height);
				Image img = Toolkit.getDefaultToolkit().createImage(
						new FilteredImageSource(bi.getSource(), cropFilter));
				BufferedImage tag = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);
				Graphics g = tag.getGraphics();
				g.drawImage(img, 0, 0, width, height, null); // �����и���ͼ
				g.dispose();
				// ���Ϊ�ļ�
				ImageIO.write(tag, "JPEG", new File(result));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ͼ���и�(��ָ���������Ϳ���и�)
	 * 
	 * @param bi
	 *            Դͼ��
	 * @param result
	 *            ��Ƭ���ͼ���ַ
	 * @param x
	 *            Ŀ����Ƭ�������X
	 * @param y
	 *            Ŀ����Ƭ�������Y
	 * @param width
	 *            Ŀ����Ƭ���
	 * @param height
	 *            Ŀ����Ƭ�߶�
	 */
	public final static BufferedImage cut(BufferedImage bi, int x, int y,
			int width, int height) {
		try {
			int srcWidth = bi.getHeight(); // Դͼ���
			int srcHeight = bi.getWidth(); // Դͼ�߶�
			if (srcWidth > 0 && srcHeight > 0) {
				// �ĸ������ֱ�Ϊͼ���������Ϳ��
				// ��: CropImageFilter(int x,int y,int width,int height)
				ImageFilter cropFilter = new CropImageFilter(x, y, width,
						height);
				Image img = Toolkit.getDefaultToolkit().createImage(
						new FilteredImageSource(bi.getSource(), cropFilter));
				BufferedImage tag = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);
				Graphics g = tag.getGraphics();
				g.drawImage(img, 0, 0, width, height, null); // �����и���ͼ
				g.dispose();
				// ���Ϊ�ļ�
				return tag;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ͼ������ת����GIF->JPG��GIF->PNG��PNG->JPG��PNG->GIF(X)��BMP->PNG
	 * 
	 * @param srcImageFile
	 *            Դͼ���ַ
	 * @param formatName
	 *            ������ʽ����ʽ���Ƶ� String����JPG��JPEG��GIF��
	 * @param destImageFile
	 *            Ŀ��ͼ���ַ
	 */
	public final static void convert(String srcImageFile, String formatName,
			String destImageFile) {
		try {
			File f = new File(srcImageFile);
			f.canRead();
			f.canWrite();
			BufferedImage src = ImageIO.read(f);
			ImageIO.write(src, formatName, new File(destImageFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ɫתΪ�ڰ�
	 * 
	 * @param srcImageFile
	 *            Դͼ���ַ
	 * @param destImageFile
	 *            Ŀ��ͼ���ַ
	 */
	public final static void gray(String srcImageFile, String destImageFile) {
		try {
			BufferedImage src = ImageIO.read(new File(srcImageFile));
			ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
			ColorConvertOp op = new ColorConvertOp(cs, null);
			src = op.filter(src, null);
			ImageIO.write(src, "JPEG", new File(destImageFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ͼƬ�������ˮӡ
	 * 
	 * @param pressText
	 *            ˮӡ����
	 * @param srcImageFile
	 *            Դͼ���ַ
	 * @param destImageFile
	 *            Ŀ��ͼ���ַ
	 * @param fontName
	 *            ˮӡ����������
	 * @param fontStyle
	 *            ˮӡ��������ʽ
	 * @param color
	 *            ˮӡ��������ɫ
	 * @param fontSize
	 *            ˮӡ�������С
	 * @param x
	 *            ����ֵ
	 * @param y
	 *            ����ֵ
	 * @param alpha
	 *            ͸���ȣ�alpha �����Ƿ�Χ [0.0, 1.0] ֮�ڣ������߽�ֵ����һ����������
	 */
	public final static void pressText(String pressText, String srcImageFile,
			String destImageFile, String fontName, int fontStyle, Color color,
			int fontSize, int x, int y, float alpha) {
		try {
			File img = new File(srcImageFile);
			Image src = ImageIO.read(img);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			g.setColor(color);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			// ��ָ���������ˮӡ����
			g.drawString(pressText, (width - (getLength(pressText) * fontSize))
					/ 2 + x, (height - fontSize) / 2 + y);
			g.dispose();
			ImageIO.write((BufferedImage) image, "JPEG",
					new File(destImageFile));// ������ļ���
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ͼƬ���ͼƬˮӡ
	 * 
	 * @param pressImg
	 *            ˮӡͼƬ
	 * @param srcImageFile
	 *            Դͼ���ַ
	 * @param destImageFile
	 *            Ŀ��ͼ���ַ
	 * @param x
	 *            ����ֵ�� Ĭ�����м�
	 * @param y
	 *            ����ֵ�� Ĭ�����м�
	 * @param alpha
	 *            ͸���ȣ�alpha �����Ƿ�Χ [0.0, 1.0] ֮�ڣ������߽�ֵ����һ����������
	 */
	public final static void pressImage(String pressImg, String srcImageFile,
			String destImageFile, int x, int y, float alpha) {
		try {
			File img = new File(srcImageFile);
			Image src = ImageIO.read(img);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			// ˮӡ�ļ�
			Image src_biao = ImageIO.read(new File(pressImg));
			int width_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			g.drawImage(src_biao, (width - width_biao) / 2,
					(height - height_biao) / 2, width_biao, height_biao, null);
			// ˮӡ�ļ�����
			g.dispose();
			ImageIO.write((BufferedImage) image, "JPEG",
					new File(destImageFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��תͼƬΪָ���Ƕ�
	 * 
	 * @param srcImageFile
	 *            Դͼ���ַ
	 * @param destImageFile
	 *            Ŀ��ͼ���ַ
	 * @param degree
	 *            ��ת�Ƕ�
	 * @return
	 */
	public static void rotateImage(String srcImageFile, String destImageFile,
			final int degree, final int x, final int y) {
		try {
			File imgage = new File(srcImageFile);
			Image src = ImageIO.read(imgage);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage bufferedimage = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufferedimage.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			g.dispose();

			int w = bufferedimage.getWidth();
			int h = bufferedimage.getHeight();
			int type = bufferedimage.getColorModel().getTransparency();
			BufferedImage img = new BufferedImage(w, h, type);
			Graphics2D graphics2d = img.createGraphics();
			graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			graphics2d.rotate(Math.toRadians(degree), x, y);
			graphics2d.drawImage(bufferedimage, 0, 0, null);
			graphics2d.dispose();

			ImageIO.write((BufferedImage) img, "JPEG", new File(destImageFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��תͼƬΪָ���Ƕ�
	 * 
	 * @param srcImageFile
	 *            Դͼ���ַ
	 * @param destImageFile
	 *            Ŀ��ͼ���ַ
	 * @param degree
	 *            ��ת�Ƕ�
	 * @return
	 */
	public static BufferedImage rotateImage(BufferedImage bi, final int degree,
			final int x, final int y) {
		try {
			int w = bi.getWidth();
			int h = bi.getHeight();
			int type = bi.getColorModel().getTransparency();
			BufferedImage img = new BufferedImage(w, h, type);
			Graphics2D graphics2d = img.createGraphics();
			graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			graphics2d.rotate(Math.toRadians(degree), x, y);
			graphics2d.drawImage(bi, 0, 0, null);
			graphics2d.dispose();
			return img;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ����text�ĳ��ȣ�һ�������������ַ���
	 * 
	 * @param text
	 * @return
	 */
	public final static int getLength(String text) {
		int length = 0;
		for (int i = 0; i < text.length(); i++) {
			if (new String(text.charAt(i) + "").getBytes().length > 1) {
				length += 2;
			} else {
				length += 1;
			}
		}
		return length / 2;
	}

	/**
	 * ת���ַ�Ϊint
	 * 
	 * @param text
	 * @return
	 */
	public final static int getInt(String text) {
		return (int) Float.parseFloat(text);
	}

	/**
	 * �õ�ͼƬԭ��С
	 * 
	 * @param text
	 * @return
	 */
	public final static Map<String, Integer> getImageSize(String srcImageFile) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		try {
			File imgage = new File(srcImageFile);
			Image src = ImageIO.read(imgage);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			map.put("w", width);
			map.put("h", height);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("w", 0);
			map.put("h", 0);
		}
		return map;
	}

	/**
	 * �ü�ͼƬ
	 * 
	 * @param imageSource
	 *            Դͼ��
	 * @param imageDest
	 *            Ŀ��ͼ��
	 * @return
	 */
	public static void cropzoom(String imageSource, String imageDest,
			String imageRotate, String viewPortW, String viewPortH,
			String imageX, String imageY, String imageW, String imageH,
			String selectorX, String selectorY, String selectorW,
			String selectorH) {
		try {
			// �����󴰿ڵĶ����䴰�ڴ�С���ڿ��Ӵ��ڴ�С����2����ͼƬ��С
			BufferedImage totalImage = new BufferedImage(getInt(viewPortW)
					+ getInt(imageW) * 2, getInt(viewPortH) + getInt(imageH)
					* 2, BufferedImage.TYPE_INT_RGB);

			// ��ȡԴͼ��
			BufferedImage bi = ImageIO.read(new File(imageSource));

			// �Ȱ�Դͼ�񱣳ֿ�߱ȣ�ƽ���ķŴ�
			BufferedImage tmp1 = ImageUtils.scale(bi, getInt(imageW),
					getInt(imageH), false);

			// �ѷŴ���ͼƬ�ŵ������󴰿���
			Graphics2D g = totalImage.createGraphics();
			g.setColor(new Color(Integer.parseInt("CCCCCC", 16)));
			g.fillRect(0, 0, getInt(viewPortW) + getInt(imageW) * 2,
					getInt(viewPortH) + getInt(imageH) * 2);
			g.drawImage(tmp1, getInt(imageW) + getInt(imageX), getInt(imageH)
					+ getInt(imageY), getInt(imageW), getInt(imageH),
					Color.white, null);
			g.dispose();

			// ��ͼƬ����Ϊ���ĵ㣬��תͼƬ
			BufferedImage tmp2 = ImageUtils.rotateImage(totalImage,
					getInt(imageRotate), getInt(imageW) + getInt(imageX)
							+ getInt(imageW) / 2, getInt(imageH)
							+ getInt(imageY) + getInt(imageH) / 2);

			// �������󴰿ڵĻ����ϣ��س����Ӵ���
			BufferedImage tmp3 = ImageUtils.cut(tmp2, getInt(imageW),
					getInt(imageH), getInt(viewPortW), getInt(viewPortH));

			// ������ڿ��Ӵ����Ͻس�ѡ������ͼƬ
			BufferedImage tmp4 = ImageUtils.cut(tmp3, getInt(selectorX),
					getInt(selectorY), getInt(selectorW), getInt(selectorH));
			ImageIO.write(tmp4, "JPEG", new File(imageDest));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}