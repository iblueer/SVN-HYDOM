package com.hydom.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 工具�?- 海顿软件常用
 */

public class HydomUtil{

//	public static final String CLASS_PATH;
//
//	static {
//		String classPath = HydomUtil.class.getProtectionDomain()
//				.getCodeSource().getLocation().getFile();
//		if (null != classPath && !"".equals(classPath.trim())) {
//			if (classPath.indexOf("/") == -1)
//				classPath = classPath.replace("\\", "/");
//			classPath = classPath.substring(1, classPath
//					.lastIndexOf("WEB-INF/classes"));
//			if (System.getProperty("os.name").indexOf("WINDOWS") != -1)
//				classPath = "/" + classPath;
//		}
//		CLASS_PATH = classPath;
//	}

	/**
	 * 随机获取UUID字符�?无中划线)
	 * 
	 * @return UUID字符�?
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.substring(0, 8) + uuid.substring(9, 13)
				+ uuid.substring(14, 18) + uuid.substring(19, 23)
				+ uuid.substring(24);
	}

	/**
	 * 根据指定长度 分隔字符�?
	 * 
	 * @param str
	 *            �?��处理的字符串
	 * @param length
	 *            分隔长度
	 * 
	 * @return 字符串集�?
	 */
	public static List<String> splitString(String str, int length) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < str.length(); i += length) {
			int endIndex = i + length;
			if (endIndex <= str.length()) {
				list.add(str.substring(i, i + length));
			} else {
				list.add(str.substring(i, str.length() - 1));
			}
		}
		return list;
	}

	/**
	 * 获取当前年月字符�?日期格式：yyyyMM)
	 * 
	 * @return 当前年月字符�?
	 */
	public static String getCurrentDateString() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
		String dateString = simpleDateFormat.format(new Date());
		return dateString;
	}
	
	/**
	 * 获取当前年月字符�?日期格式：yyyyMMdd HH:mm:ss)
	 * 
	 * @return 当前年月字符�?
	 */
	public static String getCurrentDate() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = simpleDateFormat.format(new Date());
		return dateString;
	}

	/**
	 * 获取文件的扩展名.
	 * 
	 * @param fileName
	 *            �?��处理的文件的名字.
	 * @return 文件扩展�?
	 */
	public static String getExtension(String fileName) {
		if (fileName != null) {
			int i = fileName.lastIndexOf('.');
			if (i > 0 && i < fileName.length() - 1) {
				return fileName.substring(i + 1).toLowerCase();
			}
		}
		return null;
	}

	/**
	 * 将字符串数组转化为字符串，以分隔符间�?
	 * 
	 * @param array
	 *            �?��处理的数�?
	 * 
	 * @param separator
	 *            分隔�?
	 * 
	 * @return 转化后的字符�?
	 */
	public static String toString(String[] array, String separator) {
		StringBuffer stringBuffer = new StringBuffer();
		for (String str : array) {
			stringBuffer.append(separator + str);
		}
		stringBuffer.deleteCharAt(0);
		return stringBuffer.toString();
	}

	/**
	 * 将字符串List转化为字符串，以分隔符间�?
	 * 
	 * @param list
	 *            �?��处理的List.
	 * 
	 * @param separator
	 *            分隔�?
	 * 
	 * @return 转化后的字符�?
	 */
	public static String toString(List<String> list, String separator) {
		StringBuffer stringBuffer = new StringBuffer();
		for (String str : list) {
			stringBuffer.append(separator + str);
		}
		stringBuffer.deleteCharAt(0);
		return stringBuffer.toString();
	}

	public static String toJsString(final String p, final boolean b) {

		if (p == null) {
			return "";
		}

		final StringBuilder sf = new StringBuilder();
		int ampFlag = 0;

		for (int i = 0; i < p.length(); i++) {

			final char temp = p.charAt(i);
			if (ampFlag == 1) {
				ampFlag = 0;
				if (temp == '#') {
					sf.append("&#");
					continue;
				}
				if (b) {
					sf.append("&amp;");
				} else {
					sf.append("&");
				}
			}

			switch (temp) {
			case '"':
				if (b) {
					sf.append("&quot;");
				} else {
					sf.append("\\\"");
				}
				break;
			case '\'':
				sf.append("\\\'");
				break;
			case '\\':
				sf.append("\\\\");
				break;
			case '\n':
				sf.append("\\n");
				break;
			case '\r':
				sf.append("\\r");
				break;
			case '&':
				ampFlag = 1;
				break;
			case '<':
				sf.append("&lt;");
				break;
			case '>':
				sf.append("&gt;");
				break;
			default:
				sf.append(temp);
			}
		}

		if (ampFlag == 1) {
			ampFlag = 0;
			if (b) {
				sf.append("&amp;");
			} else {
				sf.append("&");
			}
		}

		return sf.toString();
	}

	/**
	 * 字符串转化成JavaScript调用的字�?
	 * 
	 * @param p
	 * @return JavaScript Stirng
	 */
	public static String toJsString(final String p) {

		return toJsString(p, false);
	}

	/**
	 * 过滤SQL,防止SQL注入.
	 * 
	 * @param str
	 *            待转字符�?
	 * @return SQL过滤后字符串
	 */
	public static String sqlFilter(String str) {
		if (str != null && str.trim().length() > 0) {
			String inj_str = "'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|-|+|,";
			String inj_stra[] = inj_str.split("\\|");
			for (int i = 0; i < inj_stra.length; i++) {
				str = str.replace(inj_stra[i], "");
				str = str.replace(inj_stra[i].toUpperCase(), "");
			}
		}
		return str;
	}

	/**
	 * 随机生成�?��大小字符�?
	 * 
	 * @param sLen
	 *            长度
	 * @return 随机ID
	 */
	public static String getRandomStr(int sLen) {
		return getRandomStr(sLen, false);
	}

	/**
	 * 随机生成�?��字符�?
	 * 
	 * @param sLen
	 *            长度
	 * @return 随机ID
	 */
	public static String getRandomStr(int sLen, boolean bFlg) {
		String base;
		if (bFlg) {
			base = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		} else {
			base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		}
		StringBuffer temp = new StringBuffer();
		for (int i = 0; i < sLen; i++) {
			int p = (int) (Math.random() * 26);
			if (p > 25) {
				p = 25;
			}
			temp.append(base.substring(p, p + 1));
		}
		return temp.toString();
	}
	/***
	 * @param 需要加密的字符串
	 * @return 加密好的字符串
	 * **/
	public static String md5Hex(String value){
		 return DigestUtils.md5Hex(value);
	}
	/**
	 * 空判断
	 * 
	 * @return
	 */
	public static boolean isNull(Object obj) {
		if (obj == null) {
			return true;
		}
		if ("".equals(obj.toString().trim())) {
			return true;
		}
		if (obj.toString().length() < 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * 转码Unicode
	 * @param theString
	 * @param escapeSpace 是否过滤空格
	 * @return
	 */
	public static String toEncodedUnicode(String theString, boolean escapeSpace) {
        int len = theString.length();
        int bufLen = len * 2;
        if (bufLen < 0) {
            bufLen = Integer.MAX_VALUE;
        }
        
        StringBuffer outBuffer = new StringBuffer(bufLen);

        for (int x = 0; x < len; x++) {
            char aChar = theString.charAt(x);
            // Handle common case first, selecting largest block that
            // avoids the specials below
            if ((aChar > 61) && (aChar < 127)) {
                if (aChar == '\\') {
                    outBuffer.append('\\');
                    outBuffer.append('\\');
                    continue;
                }
                outBuffer.append(aChar);
                continue;
            }

            switch (aChar) {
            case ' ':
                if (x == 0 || escapeSpace) outBuffer.append('\\');
                outBuffer.append(' ');
                break;
            case '\t':
                outBuffer.append('\\');
                outBuffer.append('t');
                break;
            case '\n':
                outBuffer.append('\\');
                outBuffer.append('n');
                break;
            case '\r':
                outBuffer.append('\\');
                outBuffer.append('r');
                break;
            case '\f':
                outBuffer.append('\\');
                outBuffer.append('f');
                break;
            case '=': // Fall through
            case ':': // Fall through
            case '#': // Fall through
            case '!':
                outBuffer.append('\\');
                outBuffer.append(aChar);
                break;
            default:
                if ((aChar < 0x0020) || (aChar > 0x007e)) {
                    // 每个unicode有16位，每四位对应的16进制从高位保存到低位
                    outBuffer.append('\\');
                    outBuffer.append('u');
                    outBuffer.append(toHex((aChar >> 12) & 0xF));
                    outBuffer.append(toHex((aChar >> 8) & 0xF));
                    outBuffer.append(toHex((aChar >> 4) & 0xF));
                    outBuffer.append(toHex(aChar & 0xF));
                } else {
                    outBuffer.append(aChar);
                }
            }
        }
        return outBuffer.toString();
    }
	
	private static final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
        'B', 'C', 'D', 'E', 'F' };
	
	private static char toHex(int nibble) {
        return hexDigit[(nibble & 0xF)];
    }
	
	
//	80% 的情况出现 200
//	10% 的情况出现 1000
//	5% 的情况出现 5000
//	3% 的情况出现 10000
//	2% 的情况出现 50000

//	public static double rate0 = 0.80;
//	public static double rate1 = 0.10;
//	public static double rate2 = 0.05;
//	public static double rate3 = 0.03;
//	public static double rate4 = 0.02;
//
//	private int PercentageRandom() {
//		double randomNumber;
//		randomNumber = Math.random();
//		if (randomNumber >= 0 && randomNumber <= rate0) {
//			return 200;
//		} else if (randomNumber >= rate0 && randomNumber <= rate0 + rate1) {
//			return 1000;
//		} else if (randomNumber >= rate0 + rate1
//				&& randomNumber <= rate0 + rate1 + rate2) {
//			return 5000;
//		} else if (randomNumber >= rate0 + rate1 + rate2
//				&& randomNumber <= rate0 + rate1 + rate2 + rate3) {
//			return 10000;
//		} else if (randomNumber >= rate0 + rate1 + rate2 + rate3
//				&& randomNumber <= rate0 + rate1 + rate2 + rate3 + rate4) {
//			return 50000;
//		}
//		return 0;
//	}
	
	/**
	 * 清除HTML标记
	 * 
	 * @param htmlText
	 *            带HTML标记的字符串
	 * @return 纯文本字符串
	 */
	public static String cleanHtmlTag(String htmlText) {
		String reg = "</?[a-z][a-z0-9]*[^<>]*>?";
		return htmlText.replaceAll(reg, "");
	}

	/**
	 * 清除HTML标记,带格式.
	 * @param htmlText
	 *            带HTML标记的字符串
	 * @return 纯文本字符串
	 */
    public static String delHTMLTag(String htmlText){ 
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式 
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式 
         
        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
        Matcher m_script=p_script.matcher(htmlText); 
        htmlText=m_script.replaceAll(""); //过滤script标签 
         
        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
        Matcher m_style=p_style.matcher(htmlText); 
        htmlText=m_style.replaceAll(""); //过滤style标签 
         
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
        Matcher m_html=p_html.matcher(htmlText); 
        htmlText=m_html.replaceAll(""); //过滤html标签 

        return htmlText.trim(); //返回文本字符串 
    }
    
    /**
     * 删除HTML文本中的img标签.
     */
    public static String delIMGTag(String htmlText) {
    	String regEx_img = "<img[^>]*/>";
    	
        Pattern p_script=Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE); 
        Matcher m_script=p_script.matcher(htmlText); 
        htmlText = m_script.replaceAll(""); //过滤IMG标签
        
        return htmlText.trim();
    }

	/**
	 * 获取年月日文件夹.
	 * <pre>如：2014/12/30</pre>
	 * @return 文件夹路径
	 */
	public static String getDateFloder() {
		Calendar ca = Calendar.getInstance();
		
		return File.separator + ca.get(Calendar.YEAR) + File.separator + ca.get(Calendar.MONTH) + File.separator + ca.get(Calendar.DAY_OF_MONTH);
	}
}