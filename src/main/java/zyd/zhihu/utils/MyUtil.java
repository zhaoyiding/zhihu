package zyd.zhihu.utils;

import com.alibaba.fastjson.JSONObject;

import java.security.MessageDigest;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;

public class MyUtil {
	
	private static final Logger logger = Logger.getLogger("MyUtil");
	
	public static int ANONYMOUS_USERID = 3;
	public static int SYSTEM_USERID = 4;
	
	public static String getJsonMsgStr(int code, String msg) {
		JSONObject object = new JSONObject(2);
		object.put("code", code);
		object.put("msg", msg);
		return object.toJSONString();
	}
	
	public static String getJsonMsgStr(int code, Map<String, Object> map) {
		JSONObject object = new JSONObject(map);
		object.put("code", code);
		return object.toJSONString();
	}
	
	public static String MD5(String key) {
		char hexDigits[] = {
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
		};
		try {
			byte[] btInput = key.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			logger.severe("生成MD5失败：" + e.getMessage());
			return null;
		}
	}
	
	public static String getSalt() {
		return UUID.randomUUID().toString().substring(0, 5);
	}
	
	public static String getHeadUrl() {
		return String.format("http://images.nowcoder.com/head/%dt.png",
				new Random().nextInt(1000));
	}
	
	public static String getTicket() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static String getConversationId(int fromId, int toId) {
		return String.format("%d_%d", Math.min(fromId, toId), Math.max(fromId, toId));
	}
}
