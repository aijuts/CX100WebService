package com.aijuts.cxll.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aijuts.cxll.util.Tool;

@Service("userService")  
@Transactional
public class UserService {
	
//	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	private JdbcTemplate jdbcTemplate;
	private Tool tool;
	
	@Resource(name="dataSource")  
    public void setDataSource(DataSource dataSource) {  
        this.jdbcTemplate = new JdbcTemplate(dataSource);  
    }
	
	public int getUserCount(String username) {
		String sql = "select COUNT(*) "
				+ "from dbo.t_useraccount u "
				+ "where u.account = '" + username + "' "
				+ "or u.mailaccount = '" + username + "' "
				+ "or u.mobileaccount = '" + username + "' "
				+ "or u.nameaccount = '" + username + "'";
		int count = 0;
		try {
			count = jdbcTemplate.queryForInt(sql);
		} catch (Exception e) {
			// TODO: handle exception
//			logger.error("方法getUserCount异常：" + e);
		}
		return count;
	}
	
	public int insertUser(String username, String password, String date, boolean flag_email, boolean flag_phone) {
		String mailaccount = "", mobileaccount = "";
		if (flag_email == true) {
			mailaccount = username;
		}
		if (flag_phone == true) {
			mobileaccount = username;
		}
		String sql = "insert into dbo.t_useraccount "
				+ "values (0, ?, ?, ?, '', ?, '', 1, ?, '', 1, 0, '', '')";
		int n = 0;
		try {
			Object[] regInfo = {username, mailaccount, mobileaccount, password, date};
			n = jdbcTemplate.update(sql, regInfo);
		} catch (Exception e) {
			// TODO: handle exception
//			logger.error("方法insertUser异常：" + e);
		}
		return n;
	}
	
	public int getUserid(String username) {
		String sql = "select u.userid "
				+ "from dbo.t_useraccount u where u.account = '" + username + "'";
		int userid = 0;
		try {
			userid = jdbcTemplate.queryForInt(sql);
		} catch (Exception e) {
			// TODO: handle exception
//			logger.error("方法getUserid异常：" + e);
		}
		return userid;
	}
	
	public int insertMember(int userid, String username, int lev, boolean flag_email, boolean flag_phone) {
		String mailaccount = "", mobileaccount = "";
		if (flag_email == true) {
			mailaccount = username;
		}
		if (flag_phone == true) {
			mobileaccount = username;
		}
		String sql = "insert into dbo.t_member (userid,epic,fid,isdelete,mobile,mail,name,lev,vantages,mailstate,usablevantages) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		int n = 0;
		try {
			Object[] regInfo = {userid, 0, 0, 0, mobileaccount, mailaccount, username, lev, 0, 2, 0};
			n = jdbcTemplate.update(sql, regInfo);
		} catch (Exception e) {
			// TODO: handle exception
//			logger.error("方法insertMember异常：" + e);
		}
		return n;
	}
	
	public Map<String, Object> getUserById(int userid) {
		String sql = "select * from t_useraccount where isdelete = 0 and userid = ?";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Object[] info = {userid};
			map = jdbcTemplate.queryForMap(sql, info);
		} catch (Exception e) {
			// TODO: handle exception
//			logger.error("方法getUser异常：" + e);
			map.put("userid", "");
			map.put("siteid", "");
			map.put("account", "");
			map.put("mailaccount", "");
			map.put("mobileaccount", "");
			map.put("nameaccount", "");
			map.put("pwd", "");
			map.put("v_pwd", "");
			map.put("type", "");
			map.put("regtime", "");
			map.put("name", "");
			map.put("state", "");
			map.put("isdelete", "");
		}
		return map;
	}
	
	public Map<String, Object> getUserLogin(String username, String password) {
		String sql = "select * from t_useraccount where isdelete = 0 "
				+ "and (account = ? or mobileaccount = ? or nameaccount = ? or mailaccount = ?) and pwd = ?";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Object[] info = {username, username, username, username, password};
			map = jdbcTemplate.queryForMap(sql, info);
		} catch (Exception e) {
			// TODO: handle exception
//			logger.error("方法getUser异常：" + e);
			map.put("userid", "");
			map.put("siteid", "");
			map.put("account", "");
			map.put("mailaccount", "");
			map.put("mobileaccount", "");
			map.put("nameaccount", "");
			map.put("pwd", "");
			map.put("v_pwd", "");
			map.put("type", "");
			map.put("regtime", "");
			map.put("name", "");
			map.put("state", "");
			map.put("isdelete", "");
		}
		return map;
	}

}
