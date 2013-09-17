package com.aijuts.cxll.service;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import com.aijuts.cxll.util.Tool;

@Service("favoriteService")  
@Transactional
public class FavoriteService {
	
//	private static final Logger logger = LoggerFactory.getLogger(MemberService.class);
	private JdbcTemplate jdbcTemplate;
	private Tool tool;
	
	@Resource(name="dataSource")  
    public void setDataSource(DataSource dataSource) {  
        this.jdbcTemplate = new JdbcTemplate(dataSource);  
    }
	
	public int getFavCount(int sellerid, int userid, int type){
		String sql = "select COUNT(*) from t_favorite where fid = ? and userid = ? and type = ?";
		int count = 0;
		try {
			Object[] info = {sellerid, userid, type};
			count = jdbcTemplate.queryForInt(sql, info);
		} catch (Exception e) {
			// TODO: handle exception
//			logger.error("方法getFavCount异常：" + e);
		}
		return count;
	}
	
	public int insertFavorite(int sellerid, int userid, int type, String title, String date) {
		String sql = "insert into t_favorite values(?, ?, ?, ?, ?)";
		int count = 0;
		try {
			Object[] info = {title, type, sellerid, date, userid};
			count = jdbcTemplate.update(sql, info);
		} catch (Exception e) {
			// TODO: handle exception
//			logger.error("方法insertFavorite异常：" + e);
		}
		return count;
	}

}
