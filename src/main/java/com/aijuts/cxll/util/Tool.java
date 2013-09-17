package com.aijuts.cxll.util;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.crypto.Data;

import org.codehaus.plexus.util.StringUtils;

public class Tool {
	
	/**
	 * fid为图片id，ext为图片格式后缀 这两个参数得出图片路径
	 * 图片路径为：www.cx100.cn+"/Content/upload/"+GetFilePath（fid，ext）
	 * @param fileId
	 * @param Ext
	 * @return
	 */
	public String GetFilePath(int fileId, String Ext)
	{
		String dir = Integer.toHexString(fileId);
		int total = 8, p = 2;
		dir = padLeft(dir, total);
		String dir4 = dir.substring(p*3, p*4);
		return GetFilePath(fileId) + dir4 + Ext;
	}
	
	public String GetFilePath(int fileId)
	{
		String dir = Integer.toHexString(fileId);
		int total = 8, p = 2;
		dir = padLeft(dir, total);
		String dir1 = dir.substring(p*0, p*1);
		String dir2 = dir.substring(p*1, p*2);
		String dir3 = dir.substring(p*2, p*3);
		dir = dir1 + "/" + dir2 + "/" + dir3 + "/";
		return dir;
	}

	public String padLeft(String s, int length)
	{
	    byte[] bs = new byte[length];
	    byte[] ss = s.getBytes();
	    Arrays.fill(bs, (byte) (48 & 0xff));
	    System.arraycopy(ss, 0, bs,length - ss.length, ss.length);
	    return new String(bs);
	}
	
	private char[] hexDigit = {
    		'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
    };
   
    private char toHex(int nibble) {
    	return hexDigit[(nibble & 0xF)];
    }
   
    /**
     * 将字符串编码成 Unicode 。
     * @param theString 待转换成Unicode编码的字符串。
     * @param escapeSpace 是否忽略空格。
     * @return 返回转换后Unicode编码的字符串。
     */
    public String toUnicode(String theString, boolean escapeSpace) {
    	int len = theString.length();
    	int bufLen = len * 2;
    	if (bufLen < 0) {
            bufLen = Integer.MAX_VALUE;
        }
        StringBuffer outBuffer = new StringBuffer(bufLen);

        for(int x=0; x<len; x++) {
            char aChar = theString.charAt(x);
            // Handle common case first, selecting largest block that
            // avoids the specials below
            if ((aChar > 61) && (aChar < 127)) {
                if (aChar == '\\') {
                    outBuffer.append('\\'); outBuffer.append('\\');
                    continue;
                }
                outBuffer.append(aChar);
                continue;
            }
            switch(aChar) {
                case ' ':
                    if (x == 0 || escapeSpace) 
                        outBuffer.append('\\');
                    outBuffer.append(' ');
                    break;
                case '\t':outBuffer.append('\\'); outBuffer.append('t');
                          break;
                case '\n':outBuffer.append('\\'); outBuffer.append('n');
                          break;
                case '\r':outBuffer.append('\\'); outBuffer.append('r');
                          break;
                case '\f':outBuffer.append('\\'); outBuffer.append('f');
                          break;
                case '=': // Fall through
                case ':': // Fall through
                case '#': // Fall through
                case '!':
                    outBuffer.append('\\'); outBuffer.append(aChar);
                    break;
                default:
                    if ((aChar < 0x0020) || (aChar > 0x007e)) {
                        outBuffer.append('\\');
                        outBuffer.append('u');
                        outBuffer.append(toHex((aChar >> 12) & 0xF));
                        outBuffer.append(toHex((aChar >>   8) & 0xF));
                        outBuffer.append(toHex((aChar >>   4) & 0xF));
                        outBuffer.append(toHex( aChar         & 0xF));
                    } else {
                        outBuffer.append(aChar);
                    }
            }
        }
        return outBuffer.toString();
    }
   
    /**
     * 从 Unicode 码转换成编码前的特殊字符串。
     * @param in Unicode编码的字符数组。
     * @param off 转换的起始偏移量。
     * @param len 转换的字符长度。
     * @param convtBuf 转换的缓存字符数组。
     * @return 完成转换，返回编码前的特殊字符串。
     */
    public String fromUnicode(char[] in, int off, int len, char[] convtBuf) {
        if (convtBuf.length < len) {
            int newLen = len * 2;
            if (newLen < 0) {
                newLen = Integer.MAX_VALUE;
            }
            convtBuf = new char[newLen];
        }
        char aChar;
        char[] out = convtBuf;
        int outLen = 0;
        int end = off + len;

        while (off < end) {
            aChar = in[off++];
            if (aChar == '\\') {
                aChar = in[off++];
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = in[off++];
                        switch (aChar) {
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                            value = (value << 4) + aChar - '0';
                            break;
                        case 'a':
                        case 'b':
                        case 'c':
                        case 'd':
                        case 'e':
                        case 'f':
                            value = (value << 4) + 10 + aChar - 'a';
                            break;
                        case 'A':
                        case 'B':
                        case 'C':
                        case 'D':
                        case 'E':
                        case 'F':
                            value = (value << 4) + 10 + aChar - 'A';
                            break;
                        default:
                            throw new IllegalArgumentException(
                                    "Malformed \\uxxxx encoding.");
                        }
                    }
                    out[outLen++] = (char) value;
                } else {
                    if (aChar == 't') {
                        aChar = '\t';
                    } else if (aChar == 'r') {
                        aChar = '\r';
                    } else if (aChar == 'n') {
                        aChar = '\n';
                    } else if (aChar == 'f') {
                        aChar = '\f';
                    }
                    out[outLen++] = aChar;
                }
            } else {
                out[outLen++] = (char) aChar;
            }
        }
        return new String(out, 0, outLen);
    }
    
    /* 
     * 16进制数字字符集 
     */ 
    private String hexString="0123456789abcdef"; 
    
    /* 
     * 将字符串编码成16进制数字,适用于所有字符（包括中文） 
     */ 
    public String encode(String str) 
    { 
    	//根据默认编码获取字节数组 
    	byte[] bytes=str.getBytes(); 
    	StringBuilder sb=new StringBuilder(bytes.length*2); 
    	//将字节数组中每个字节拆解成2位16进制整数 
    	for(int i=0;i<bytes.length;i++) 
    	{ 
    		sb.append(hexString.charAt((bytes[i]&0xf0)>>4)); 
    		sb.append(hexString.charAt((bytes[i]&0x0f)>>0)); 
    	} 
    	return sb.toString(); 
    } 
    
    /* 
     * 将16进制数字解码成字符串,适用于所有字符（包括中文） 
     */ 
    public String decode(String bytes) 
    { 
    	ByteArrayOutputStream baos=new ByteArrayOutputStream(bytes.length()/2); 
    	//将每2位16进制整数组装成一个字节 
    	for(int i=0;i<bytes.length();i+=2) 
    		baos.write((hexString.indexOf(bytes.charAt(i))<<4 |hexString.indexOf(bytes.charAt(i+1)))); 
    	return new String(baos.toByteArray()); 
    } 
    
    /*
     *  把十六进制Unicode编码字符串转换为中文字符串 
     */
    public String unicodeToString(String str) {
    	Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");   
    	Matcher matcher = pattern.matcher(str);   	
    	char ch; 	
    	while (matcher.find()) {
    		ch = (char) Integer.parseInt(matcher.group(2), 16);
    		str = str.replace(matcher.group(1), ch + "");   
    	}
    	return str;
    }
    
    // 转化十六进制编码为字符串 
    public String toStringHex(String s) throws Exception
    {
    	byte[] baKeyword = new byte[s.length()/2];
    	for(int i = 0; i < baKeyword.length; i++)
    	{
    		baKeyword[i] = (byte)(0xff & Integer.parseInt(s.substring(i*2, i*2+2),16));
    	} 
    	s = new String(baKeyword, "utf-8");//UTF-16le:Not
    	return s;
    }
    
    public String verStr(String str) {
    	String string = str;
    	if (string == null || string.equals("null")) {
    		string = "";
		}
		return string;
	}
    
    /**
     * md5加密
     * 
     * @param plainText
     * @return
     */
    public static String md5s(String plainText) {
        if (isEmpty(plainText)) {
            return "";
        }
        String resultStr = "";
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
            resultStr = buf.toString();
            return resultStr;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return resultStr;
        }
    }
    
    /**
     * String 为空判断
     * 
     * @param res
     * @return
     */
    public static boolean isEmpty(String res) {
        if (null == res || "".equals(res)) {
            return true;
        }
        return false;
    }
    
    public final static String md5(String s) {  
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',  
                'a', 'b', 'c', 'd', 'e', 'f' };  
        try {  
            byte[] strTemp = s.getBytes();  
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");  
            mdTemp.update(strTemp);  
            byte[] md = mdTemp.digest();  
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
            e.printStackTrace();  
            return null;  
        }  
    }
    
    public boolean checkEmail(String email){
    	boolean tag = true;
        final String pattern1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        final Pattern pattern = Pattern.compile(pattern1);
        final Matcher mat = pattern.matcher(email);
        if (!mat.find()) {
            tag = false;
        }
        return tag;
    }
    
    public boolean checkPhone(String phone) {
    	String regExp = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";
    	Pattern p = Pattern.compile(regExp);
    	Matcher m = p.matcher(phone);
    	return m.find();
    }
    
    public String getLocationTime() {
    	Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(date);
		return time;
	}
    
    public String getRandomTime() {
    	Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSS");
		String random = format.format(date);
		return random;
	}
    
    public String CreateOrderCode(int orderId, int orderType) throws Exception
	{
		StringBuilder code = new StringBuilder();
		//加上时间标识
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSS");
		String timestr = format.format(date);
		code.append(timestr.substring(14, timestr.length()));//fff
		code.append(timestr.substring(8, 10));//HH
		code.append(timestr.substring(2, 4));//yy
		code.append(timestr.substring(6, 8));//dd
		code.append(timestr.substring(4, 6));//MM
		code.append(timestr.substring(10, 12));//mm
		code.append(timestr.substring(12, 14));//ss
		timestr = null;
		
		//取id后3位
		String orderid = "000" + orderId;
		String tmp = orderid.substring(orderid.length() - 3, orderid.length());
		code.append(tmp);
		String ss = System.currentTimeMillis() + "";
		String sss = ss.substring(ss.length() - 3, ss.length());
		Random random = new Random(Integer.valueOf(sss));
		code.insert(6, random.nextInt(10));//年份中间
		code.insert(11, random.nextInt(10));//月份中间
		random = null;
		tmp = null;

		//结果
		StringBuilder result = new StringBuilder();
		//前20位的和值
		int total = 0;
		int num = 0;
		char[] codeChar = code.toString().toCharArray();
		for (int i = 0; i < codeChar.length; i++) {
			num = Integer.valueOf(codeChar[i]) - 48;
			total += num;
			result.append(num);
		}

		//倒数第二位是订单类型
		result.append((int)orderType);

		result.append(total % 10);
		code = null;
		return result.toString();
	}

}
