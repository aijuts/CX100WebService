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

@Service("memberService")  
@Transactional
public class MemberService {
	
//	private static final Logger logger = LoggerFactory.getLogger(MemberService.class);
	private JdbcTemplate jdbcTemplate;
	private Tool tool;
	
	@Resource(name="dataSource")  
    public void setDataSource(DataSource dataSource) {  
        this.jdbcTemplate = new JdbcTemplate(dataSource);  
    }
	
	public Map<String, Object> getMember(int userid) {
		String sql = "select * from dbo.t_member where userid = " + userid;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = jdbcTemplate.queryForMap(sql);
		} catch (Exception e) {
			// TODO: handle exception
//			logger.error("方法getMember异常：" + e);
			map.put("memberid", "0");
			map.put("userid", "0");
			map.put("name", "");
			map.put("mobile", "");
			map.put("realname", "");
			map.put("sex", "0");
			map.put("lev", "0");
			map.put("zoneid", "0");
			map.put("addr", "");
			map.put("pro", "");
			map.put("birthday", "");
			map.put("qq", "");
			map.put("msn", "");
			map.put("vid", "");
			map.put("interest", "");
			map.put("mail", "");
			map.put("mailstate", "0");
			map.put("regdate", "");
			map.put("mstate", "0");
			map.put("btype", "0");
			map.put("epic", "0");
			map.put("fid", "0");
			map.put("consumid", "0");
			map.put("vantages", "0");
			map.put("isdelete", "0");
			map.put("usablevantages", "0");
		}
		return map;
	}
	
	public int getOrderCount(int userid, int type) {
		String sql = "select count(1) "
				+ "from t_order O "
				+ "left join t_seller S on(O.sellerid = S.userid) "
				+ "left join t_useraccount UU on (O.consumerid = UU.userid) "
				+ "where 1 = 1 and O.consumerid = " + userid + " and O.type = " + type;
		int count = 0;
		try {
			count = jdbcTemplate.queryForInt(sql);
		} catch (Exception e) {
			// TODO: handle exception
//			logger.error("方法getGeneralOrderCount异常：" + e);
		}
		return count;
	}
	
	public int getFavoriteCount(int userid, int type) {
		String sql = "select COUNT(1) from t_favorite "
				+ "where userid = " + userid + " and type = " + type;
		int count = 0;
		try {
			count = jdbcTemplate.queryForInt(sql);
		} catch (Exception e) {
			// TODO: handle exception
//			logger.error("方法getFavoriteCount异常：" + e);
		}
		return count;
	}
	
	public int getReviewCount(int userid) {
		String sql = "select COUNT(1) "
				+ "from t_reviews R "
				+ "left join t_order as O on (R.orderid = O.orderid) "
				+ "left join t_seller S on (R.sellerid = S.userid) "
				+ "left join t_useraccount U on (R.userid = U.userid) "
				+ "where 1 = 1 and R.userid = " + userid;
		int count = 0;
		try {
			count = jdbcTemplate.queryForInt(sql);
		} catch (Exception e) {
			// TODO: handle exception
//			logger.error("方法getFavoriteCount异常：" + e);
		}
		return count;
	}
	
	public int updateMember(int userid, Map<String, Object> map) {
		String sql = "update dbo.t_member set " + map.get("key") + " = ? where userid = ?";
		String key2 = "";
		int count = 0;
		try {
			Object[] info = {map.get("value"), userid};
			count = jdbcTemplate.update(sql, info);
			
			if (map.get("key").equals("name")) {
				key2 = "nameaccount";
				sql = "update dbo.t_useraccount set " + key2 + " = ? where userid = ?";
				count = jdbcTemplate.update(sql, info);
			}
			if (map.get("key").equals("mobile")) {
				key2 = "mobileaccount";
				sql = "update dbo.t_useraccount set " + key2 + " = ? where userid = ?";
				count = jdbcTemplate.update(sql, info);
			}
			if (map.get("key").equals("mail")) {
				key2 = "mailaccount";
				sql = "update dbo.t_useraccount set " + key2 + " = ? where userid = ?";
				count = jdbcTemplate.update(sql, info);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
//			logger.error("方法updateMember异常：" + e);
		}
		return count;
	}
	
	public int getMemerCount(String key, String value) {
		String sql = "select COUNT(*) from dbo.t_member where " + key + " = '" + value + "'";
		int count = 0;
		try {
			count = jdbcTemplate.queryForInt(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return count;
	}

}
