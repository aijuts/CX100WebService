package com.aijuts.cxll;

import com.aijuts.cxll.util.DesUtil;
import com.aijuts.cxll.util.Md5Util;
import com.aijuts.cxll.util.Tool;

public class Test2 {
	
	public static void main(String[] args) {
		try {  
            DesUtil des = new DesUtil("13256784");// 自定义密钥  
            String src = "123456";  
            String src1 = des.encrypt(src);  
            String src2 = des.decrypt(src1);  
            String src3 = Md5Util.getMd5(src);  
            System.out.println("DES加密前的字符：" + src + "，长度：" + src.length());  
            System.out.println("DES加密后的字符：" + src1 + "，长度：" + src1.length());  
            System.out.println("DES解密后的字符：" + src2 + "，长度：" + src2.length());  
            System.out.println("MD5加密后的字符：" + src3 + "，长度：" + src3.length());  
            
            Tool tool = new Tool();
    		System.out.println(tool.md5(src));
    		System.out.println(tool.md5s(src));
        } catch (Exception e) {  
            e.printStackTrace();  
        }
	}

}
