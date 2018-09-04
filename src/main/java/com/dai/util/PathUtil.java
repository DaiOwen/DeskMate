package com.dai.util;
/**
 * 文件路径和操作类
 * @author 18491
 *
 */
public class PathUtil {

	private static String seperator = System.getProperty("file.separator");
	
	public static String getImgBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		if(os.toLowerCase().startsWith("win")) {
			basePath = "F:/Java/prodectDev/win_image";
		}else {
			basePath = "/Users/baidu/work/image";
		}
		basePath = basePath.replace("/", seperator);
		return basePath;
	}
	
	public static String getShopImgPath(Long shopId) {
		String imagePath = "/upload/item/shop/"+shopId+"/";
		return imagePath.replace("/", seperator);
	}
}
