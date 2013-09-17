package com.aijuts.cxll.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JDBC��װ��
 * @author Administrator
 *
 */
public class DB {
	
	private ResultSet rs;  
    private Statement stm;  
    private Connection con;  
    private String url = "jdbc:sqlserver://192.168.1.132;DatabaseName=cxlm";  
    private String classname = "com.microsoft.sqlserver.jdbc.SQLServerDriver";  
    private String username =  "sa";  
    private String password =  "aijuts888!";  
      
    /*----------------------------------------------------*/  
    /** 
     * ���캯�� 
     */  
    public DB(){  
    	try{  
    		Class.forName(classname);//�������ݿ�����  
    	}catch(ClassNotFoundException e){  
    		e.printStackTrace();  
    	}  
    }  
    /** 
     * �������ݿ����� 
     */  
    public Connection getCon(){   
    	try{  
    		con=DriverManager.getConnection(url,username,password);  
    	}catch(Exception e){e.printStackTrace(System.err);}  
    	return con;  
    }  
    
    /*----------------------------------------------------*/  
    /** 
     * ��ȡStatement��¼ 
     */  
    public Statement getStm(){  
    	try{  
    		con=getCon();  
    		stm=con.createStatement();  
    	}catch(Exception e){e.printStackTrace(System.err);}  
    	return stm;  
    }  
    /** 
     * ��������ķ�������ѯ���ݿ⣬���ص������ 
     * ��������ù������£� 
     * DB db=new DB(); 
     *   ResultSet r=db.getrs(sql); 
     *   while(r.next()){ 
     *    String s1 = r.getInt(1); 
     * } 
     */  
    public ResultSet getrs(String sql){  
    	if(sql==null)sql="";  
    	try{  
    		stm=getStm();  
    		rs=stm.executeQuery(sql);  
    	}catch(SQLException e){e.printStackTrace();}  
    	return rs;  
    }  
    
    /*----------------------------------------------------*/  
    /** 
     * ��ȡStatement��¼�� 
     */  
    public Statement getStmed(){  
    	try{  
    		con=getCon();  
    		stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);  
    	}catch(Exception e){e.printStackTrace(System.err);}  
    	return stm;  
    }  
    /** 
     * ��������ķ�������ѯ���ݿ⣬����һ������� 
     * ��������ù������£� 
     * DB db=new DB(); 
     *   ResultSet rs=db.getRs(sql); 
     *   if(rs.next()){ 
     *    String s1 = r.getInt(1); 
     *      String s2 = r.getInt(2); 
     *      String s3 = r.getInt(3); 
     * } 
     */  
    public ResultSet getRs(String sql){  
    	if(sql==null)sql="";  
    	try{  
    		stm=getStmed();  
    		rs=stm.executeQuery(sql);  
    	}catch(SQLException e){e.printStackTrace();}  
    	return rs;  
    }  
    
    /*----------------------------------------------------*/  
    /** 
     * �����ݿ���и��²������ʺ�SQL��insert����update��� 
     * ����һ��intֵ����ʾ���µļ�¼�� 
     * ������Ϊ0,��ʾ����ʧ�� 
     * ��������ù������£� 
     * DB db=new DB(); 
     *   int i=db.update(sql); 
     *   f(i==0){ 
     *    return mapping.findForward("false"); 
     * } 
     *  return mapping.findForward("success"); 
     */  
    public int update(String sql){  
    	int num=0;  
    	if(sql==null)sql="";  
    	try{  
    		stm=getStmed();  
    		num=stm.executeUpdate(sql);  
    	}catch(SQLException e){e.printStackTrace();num=0;}  
    	return num;  
    }  
    
    /*----------------------------------------------------*/  
    /** 
     * ɾ�����ݿ�������� 
     * ��������ù������£� 
     * DB db=new DB(); 
     *   db.delete(sql); 
     */  
    public boolean delete(String sql){  
    	boolean ok;  
    	if(sql==null)sql="";  
    	try{  
    		stm=getStmed();  
    		ok=stm.execute(sql);  
    	}catch(SQLException e){e.printStackTrace();}  
    	return true;  
    }  
    
    /** 
     * �Ͽ����ݿ����� 
     * ��������ù������£� 
     * DB db=new DB(); 
     *   db.closed(); 
     */  
    public void closed(){  
    	try{  
    		if(rs!=null)rs.close();  
    	}catch(Exception e){e.printStackTrace();}  
    	try{  
    		if(stm!=null)stm.close();  
    	}catch(Exception e){e.printStackTrace();}  
    	try{  
    		if(con!=null)con.close();  
    	}catch(Exception e){e.printStackTrace();}       
    }
    
}
