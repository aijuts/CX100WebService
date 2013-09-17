package com.aijuts.cxll.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aijuts.cxll.entity.Cateclassify;
import com.aijuts.cxll.entity.Logeinfo;

@Service("logeinfoService")  
@Transactional
public class LogeinfoService {
	
//	private static final Logger logger = LoggerFactory.getLogger(LogeinfoService.class);
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="dataSource")  
    public void setDataSource(DataSource dataSource) {  
        this.jdbcTemplate = new JdbcTemplate(dataSource);  
    }
	
	public List<Logeinfo> getLogeinfoAll(int sellerid) {
		String sql = "select * from t_logeinfo where 1 = 1  and state = 1  and  userid = " + sellerid;
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Logeinfo>(Logeinfo.class));
	}

}
