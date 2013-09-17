package com.aijuts.cxll;

import java.security.Key;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.aijuts.cxll.util.DESPlus;
import com.aijuts.cxll.util.Tool;
import com.sun.org.apache.xerces.internal.impl.dv.xs.BaseDVFactory;

public class Test {
	
	private static BASE64Decoder decoder = new BASE64Decoder();
	private static BASE64Encoder encoder = new BASE64Encoder();
	
	public static void main(String[] args) throws Exception {
		//byte[] key = decoder.decodeBuffer("YWJjZGVmZ2hpamtsbW5vcHFyc3R1dnd4");
		byte[] key = {8, 4, 7, 6, 3, 5, 2, 1, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120};
		//byte[] key = {1, 3, 2, 5, 6, 7, 8, 4};
		String ss = new String(key, "UTF-8");
		System.out.println(ss);
		byte[] keyiv = { 8, 4, 7, 6, 3, 5, 2, 1 };
 
		byte[] data="123456".getBytes("UTF-8");
        
//		System.out.println("ECB加密解密");
//		byte[] str3 = des3EncodeECB(key,data );
//		byte[] str4 = ees3DecodeECB(key, str3);
//        System.out.println(new BASE64Encoder().encode(str3));
//        System.out.println(new String(str4, "UTF-8"));
 
        System.out.println();
 
        System.out.println("CBC加密解密");
        
        byte[] str5 = des3EncodeCBC(key, keyiv, data);
        String string5 = encoder.encode(str5);
        System.out.println(string5);
        
        byte[] b = decoder.decodeBuffer(string5);
        byte[] str6 = des3DecodeCBC(key, keyiv, b);
        String string6 = new String(str6, "UTF-8");
        System.out.println(string6);
        
//        byte[] aa = {1, 3, 2, 5, 6, 7, 8, 4, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120};
//        String bb = encoder.encode(aa);
//        System.out.println(bb);
//        
//        byte[] cc = decoder.decodeBuffer(bb);
//        String dd = encoder.encode(cc);
//        System.out.println(dd);
        
        String ff = "29RhZIOzrlg=";
        byte[] d2 = decoder.decodeBuffer(ff);
        byte[] str7 = des3DecodeCBC(key, keyiv, d2);
        String gg = new String(str7, "UTF-8");
        System.out.println("gg:" + gg);
        
        
        String orderid = "000" + 1234;
		String tmp = orderid.substring(orderid.length() - 3, orderid.length());
		System.out.println(tmp);
		
		System.out.println(System.currentTimeMillis());
		Date d = new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("SS");
		String str=sdf.format(d);
		System.out.println(str);
		
		Tool tool = new Tool();
		String code = tool.CreateOrderCode(407, 2);
		System.out.println(code);
//		char[] cc = code.toCharArray();
//		for (int i = 0; i < cc.length; i++) {
////			int num = Integer.valueOf(cc[i]+"") - 48;
//			int num = Integer.valueOf(cc[i]);
//			System.out.print(num + ",");
//		}
//		System.out.println("");
//		for (int i = 0; i < cc.length; i++) {
////			int num = Integer.valueOf(cc[i]+"") - 48;
//			int num = Integer.valueOf(cc[i]) - 48;
//			System.out.print(num + ",");
//		}
 
    }
 
    /**
     * ECB加密,不要IV
     * @param key 密钥
     * @param data 明文
     * @return Base64编码的密文
     * @throws Exception
     */
    public static byte[] des3EncodeECB(byte[] key, byte[] data)
            throws Exception {
 
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
 
        Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
 
        cipher.init(Cipher.ENCRYPT_MODE, deskey);
        byte[] bOut = cipher.doFinal(data);
 
        return bOut;
    }
 
    /**
     * ECB解密,不要IV
     * @param key 密钥
     * @param data Base64编码的密文
     * @return 明文
     * @throws Exception
     */
    public static byte[] ees3DecodeECB(byte[] key, byte[] data)
            throws Exception {
 
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
 
        Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
 
        cipher.init(Cipher.DECRYPT_MODE, deskey);
 
        byte[] bOut = cipher.doFinal(data);
 
        return bOut;
 
    }
 
    /**
     * CBC加密
     * @param key 密钥
     * @param keyiv IV
     * @param data 明文
     * @return Base64编码的密文
     * @throws Exception
     */
    public static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data)
            throws Exception {
 
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
 
        Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(keyiv);
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] bOut = cipher.doFinal(data);
 
        return bOut;
    }
 
    /**
     * CBC解密
     * @param key 密钥
     * @param keyiv IV
     * @param data Base64编码的密文
     * @return 明文
     * @throws Exception
     */
    public static byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data)
            throws Exception {
 
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
 
        Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(keyiv);
 
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
 
        byte[] bOut = cipher.doFinal(data);
 
        return bOut;
 
    }
    
    
    
    
    
    
    
    
    
    
	
//	public static void main(String[] args) {
//		try {
//			String test = "123456";
//			String k = encoder.encode(key);
//			System.err.println(k);
//			DESPlus des = new DESPlus("AQMCBQYHCAQ=");   //自定义密钥
//			System.out.println("加密前的字符："+test);
//			System.out.println("加密后的字符："+des.encrypt(test));
//			System.out.println("解密后的字符："+des.decrypt(des.encrypt(test)));
////			String xx = "[B@1ef45e0";
////			System.out.println(decrypt(xx, "13256784"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	private static byte[] key = { 1, 3, 2, 5, 6, 7, 8, 4 };
//	private byte[] iV = { 8, 4, 7, 6, 3, 5, 2, 1 };
	
//	/** 
//     * 解密 
//     *  
//     * @param src 数据源 
//     * @param key 密钥，长度必须是8的倍数 
//     * @return 返回解密后的原始数据 
//     * @throws Exception 
//     */  
//    public String decrypt(String message) throws Exception {  
//        BASE64Decoder decoder = new BASE64Decoder();  
//        byte[] bytesrc = decoder.decodeBuffer(message);  
//        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");   
//        DESKeySpec desKeySpec = new DESKeySpec(PASSWORD_CRYPT_KEY.getBytes("UTF-8"));  
//        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
//        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);  
//        IvParameterSpec iv = new IvParameterSpec(IV.getBytes("UTF-8"));  
//           
//        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);     
//     
//        byte[] retByte = cipher.doFinal(bytesrc);    
//        return new String(retByte);  
//          
//    }
//	
//	public static String decrypt(String message,String key) throws Exception {
//		byte[] bytesrc =convertHexString(message);
//		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
//		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
//		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
//		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
//		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
//		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
//		byte[] retByte = cipher.doFinal(bytesrc);
//		return new String(retByte);
//	}
//
//	public static byte[] encrypt(String message, String key)
//			throws Exception {
//		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
//		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
//		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
//		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
//		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
//		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
//		return cipher.doFinal(message.getBytes("UTF-8"));
//	}
//
//	public static byte[] convertHexString(String ss){
//		byte digest[] = new byte[ss.length() / 2];
//		for(int i = 0; i < digest.length; i++){
//			String byteString = ss.substring(2 * i, 2 * i + 2);
//			int byteValue = Integer.parseInt(byteString, 16);
//			digest[i] = (byte)byteValue;
//		}
//		return digest;
//	}
	
//	"/// <summary>
//	  /// 添加会员
//	  /// </summary>
//	  /// <param name=""userInfo"">会员信息</param>
//	  /// <returns>成功返回会员userid，失败返回 0 </returns>
//	  internal int AddVipMember(ConsumerInfo userInfo)
//	  {
//	   #region 会员用户名重复判断
//	   string sql = """", sqlA = """", sqlC = """";
//	   int result = 0;
//	   int userid = 0;//会员id
//	   int levid = 0;
//	   dao.ServiceData serviceData = new dao.ServiceData();
//	   if (serviceData.IsExsitAccount(userInfo.UserAccount)) //判断是否存在相同的用户名
//	   {
//	    throw new ServiceError(ErrorCode.UserExsit);
//	   }
//	   #endregion
//
//	   #region 会员姓名没有重复
//	   else // 如果没有重复的用户名，先执行对帐户表中的插入
//	   {
//	    sql = ""insert into t_useraccount values({0},{1},{2},{3},{4},{5},{6},{7},{8},{9},{10},{11});  select @@identity"";
//	    sql = string.Format(sql, userInfo.SiteId,
//	    SqlUtils.QueryToStr(userInfo.UserAccount),
//	    SqlUtils.QueryToStr(null),
//	    SqlUtils.QueryToStr(null),
//	    SqlUtils.QueryToStr(userInfo.NameAccount),
//	    SqlUtils.QueryToStr(userInfo.Password),
//	    SqlUtils.QueryToStr(userInfo.V_Password),
//	    (int)userInfo.Type,
//	    SqlUtils.ToDateTime(userInfo.RegistTime),
//	    SqlUtils.QueryToStr(userInfo.Name),
//	    (int)userInfo.State,
//	    GetIntValue(userInfo.IsDelete));
//	    SqlCommand cmd = GetCommand();
//	    cmd.CommandText = sql;
//	    IDataReader dr = GetDataReader(cmd);
//	    if (dr.Read())
//	    {
//	     userid = Convert.ToInt32(dr.GetValue(0));
//	    }
//	    dr.Close();
//	    cmd.Dispose();
//	    sql = null;
//	    if (userid > 0) // 帐户表中的插入成功后返回 会员userid，将userid 插入到会员表中
//	    {
//	     //设置刚注册会员的初始等级
//	     sqlC = ""select top 1 levid from t_scourcelev where type=1 and parentid<>0  ""
//	     + ""  order by lowerlimit ASC"";
//	     SqlCommand cmdC = GetCommand();
//	     cmdC.CommandText = sqlC;
//	     IDataReader drC = GetDataReader(cmdC);
//	     if (drC.Read())
//	     {
//	      if (!drC.IsDBNull(0))
//	      {
//	       levid = Convert.ToInt32(drC.GetValue(0));
//	      }
//	     }
//	     cmdC.Dispose();
//	     drC.Close();
//	     sqlC = null;
//	     if (levid > 0) // 如果有会员等级的就执行插入
//	     {
//	      sqlA = ""insert into  t_member(userid,epic,fid,isdelete,mobile,mail,name,lev,vantages,mailstate,usablevantages) ""
//	      + "" values ({0},{1},{2},{3},{4},{5},{6},{7},{8},{9},{10}) "";
//	      sqlA = string.Format(sqlA, userid, 0, 0, GetIntValue(false),
//	      SqlUtils.QueryToStr(userInfo.MobileAccount),
//	      SqlUtils.QueryToStr(userInfo.MailAccount),
//	      SqlUtils.QueryToStr(userInfo.Name),
//	      levid, 0, (int)MailState.Unsubscribe, 0);
//	      try
//	      {
//	       result = NonQueryReader(sqlA) > 0 ? userid : 0;
//	      }
//	      catch (SqlException ex)
//	      {
//	       Context.Error(""sql语句:"" + sqlA);
//	       Context.Error(ex);
//	      }
//	     }
//	    }
//	    return result;
//	   }
//	   #endregion
//	  }
//	"

    
    
    
    
    
//  /// <summary>
//    /// 添加订单
//    /// </summary>
//    /// <param name="order">订单实体</param>
//    /// <returns>标识列id值</returns>
//    internal int AddOrder(OrderInfo order)
//    {
//        int result = 0;
//        string sql = "insert into t_order values({0},{1},{2},{3},{4},{5},{6},{7},{8},{9},{10},{11},{12},{13},{14},{15},{16},{17},{18},{19});select @@identity";
//        string cmdText = string.Format(sql,
//            SqlUtils.QueryToStr(order.OrderCode),
//            (int)order.State,
//            order.SiteId,
//            SqlUtils.ToDateTime(order.AddTime),
//            SqlUtils.ToDateTime(order.ReceiptTime),
//            SqlUtils.ToDateTime(order.Scheduled),
//            order.SellerId,
//            order.ConsumerId,
//            order.ConsuerCount,
//            order.Loge,
//            order.Packing,
//            order.Freight,
//            order.Address,
//            SqlUtils.QueryToStr(order.Title),
//            (int)order.Type,
//            order.Payment,
//            order.TotalPayment,
//            SqlUtils.QueryToStr(order.Content),
//            SqlUtils.QueryToStr(order.ConsumerCode),
//            order.ProductTotal);
//        try
//        {
//            SqlCommand cmd = GetCommand();
//            cmd.CommandText = cmdText;
//            IDataReader dr = GetDataReader(cmd);
//            if (dr.Read())
//            {
//                result = Convert.ToInt32(dr.GetValue(0));
//            }
//            if (result > 0)//代表插入订单成功
//            {
//                //把订单编号更新到订单表中
//                string orderCode = SDW.CXLM.IBiz.CommonTools.CreateOrderCode(result, order.Type);
//                string sqlCode = "update t_order set code={0} where orderid={1}";
//                sqlCode = string.Format(sqlCode, orderCode, result);
//                if (!(NonQueryReader(sqlCode) > 0))
//                {
//                    Context.Error("生成订单编号失败");
//                    Context.Error("sql语句:" + sqlCode);
//                }
//                //批量插入订单详细
//                if (order.Detail != null && order.Detail.Count > 0)
//                {
//                    string sqlStr = "insert into t_orderdetail values({0},{1},{2},{3},{4},{5})";
//                    List<string> sqls = new List<string>();
//                    for (int i = 0; i < order.Detail.Count; i++)
//                    {
//                        string cmdStr = string.Format(sqlStr,
//                            result,
//                            SqlUtils.QueryToStr(order.Detail[i].Name),
//                            order.Detail[i].Price,
//                            order.Detail[i].Count,
//                            order.Detail[i].AltPrice,
//                            order.Detail[i].Total);
//                        sqls.Add(cmdStr);
//                    }
//                    if (!Transaction(sqls))
//                    {
//                        Context.Error("插入订单详细时出错");
//                        foreach (string item in sqls)
//                        {
//                            Context.Error("sql语句:" + item);
//                        }
//                    }
//                    if (order.Type==OrderType.Coupon)
//                    {
//                        string sqlDetail = "select detailid,[count] from t_orderdetail where orderid=" + result;
//                        SqlCommand cmdCouDetail = GetCommand();
//                        cmdCouDetail.CommandText = sqlDetail;
//                        IDataReader drCouDetail = GetDataReader(cmdCouDetail);
//                        string sqlCouDetail = "insert into t_couorderdetail values({0},{1},'{2}',{3},{4},{5},{6},{7})";
//                        List<string> sqlCouDetails = new List<string>();
//                        int j=0;
//                        int detailCoun = 0;
//                        while (drCouDetail.Read())
//			            {
//                            detailCoun = Convert.ToInt32(drCouDetail.GetDecimal(1));
//                            for (int i = 0; i < detailCoun; i++)
//                            {
//                                string sqlCouDetailStr = string.Format(sqlCouDetail,
//                                drCouDetail.GetInt32(0),
//                                order.Detail[j].CouDetail[0].SellerVid,
//                                "",
//                                 (int)order.Detail[j].CouDetail[0].States,
//                                 order.Detail[j].CouDetail[0].Couponid,
//                                 0,
//                                 result,
//                                 SqlUtils.ToDateTime(order.ReceiptTime));
//                                sqlCouDetails.Add(sqlCouDetailStr);
//                            }
//                            j++;
//			            }
//                        if (!Transaction(sqlCouDetails))
//                        {
//                            Context.Error("插入礼券订单详细扩展表时出错");
//                            foreach (string item in sqlCouDetails)
//                            {
//                                Context.Error("sql语句:" + item);
//                            }
//                        }
//                        drCouDetail.Close();
//                        cmdCouDetail.Dispose();
//                    }
//                }
//            }
//            dr.Close();
//            cmd.Dispose();
//        }
//        catch (SqlException ex)
//        {
//            Context.Error("sql语句：" + cmdText);
//            Context.Error(ex);
//        }
//        return result;
//    }


}
