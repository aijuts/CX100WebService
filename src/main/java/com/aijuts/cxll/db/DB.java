package com.aijuts.cxll.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JDBC封装类
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
     * 构造函数 
     */  
    public DB(){  
    	try{  
    		Class.forName(classname);//加载数据库驱动  
    	}catch(ClassNotFoundException e){  
    		e.printStackTrace();  
    	}  
    }  
    /** 
     * 创建数据库连接 
     */  
    public Connection getCon(){   
    	try{  
    		con=DriverManager.getConnection(url,username,password);  
    	}catch(Exception e){e.printStackTrace(System.err);}  
    	return con;  
    }  
    
    /*----------------------------------------------------*/  
    /** 
     * 获取Statement记录 
     */  
    public Statement getStm(){  
    	try{  
    		con=getCon();  
    		stm=con.createStatement();  
    	}catch(Exception e){e.printStackTrace(System.err);}  
    	return stm;  
    }  
    /** 
     * 调法上面的方法，查询数据库，返回单个结果 
     * 其他类调用过程如下： 
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
     * 获取Statement记录集 
     */  
    public Statement getStmed(){  
    	try{  
    		con=getCon();  
    		stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);  
    	}catch(Exception e){e.printStackTrace(System.err);}  
    	return stm;  
    }  
    /** 
     * 调法上面的方法，查询数据库，返回一个结果集 
     * 其他类调用过程如下： 
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
     * 对数据库进行更新操作，适合SQL的insert语句和update语句 
     * 返回一个int值，表示更新的记录数 
     * 若返回为0,表示更新失败 
     * 其他类调用过程如下： 
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
     * 删除数据库的中数据 
     * 其他类调用过程如下： 
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
     * 断开数据库连接 
     * 其他类调用过程如下： 
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
