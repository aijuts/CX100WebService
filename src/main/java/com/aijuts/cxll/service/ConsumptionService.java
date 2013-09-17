package com.aijuts.cxll.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aijuts.cxll.entity.Consumption;

@Service("consumptionService")  
@Transactional
public class ConsumptionService {
	
//	private static final Logger logger = LoggerFactory.getLogger(ConsumptionService.class);
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="dataSource")  
    public void setDataSource(DataSource dataSource) {  
        this.jdbcTemplate = new JdbcTemplate(dataSource);  
    }
	
	public List<Consumption> getAll() {
		String sql = "select * from dbo.t_consumption";
		RowMapper<Consumption> mapper = new RowMapper<Consumption>() {

			@Override
			public Consumption mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				// TODO Auto-generated method stub
				Consumption consumption = new Consumption();
				consumption.setConsumid(rs.getString("consumid"));
				consumption.setName(rs.getString("name"));
				consumption.setNote(rs.getString("note"));
				return consumption;
			}
		};
		return jdbcTemplate.query(sql, mapper);
	}
	
	public Map<String, Object> getConsumDetail(int consumid) {
		String sql = "select * from dbo.t_consumption where consumid = " + consumid;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = jdbcTemplate.queryForMap(sql);
		} catch (Exception e) {
			// TODO: handle exception
//			logger.error("异常：" + e);
			map.put("consumid", 0);
			map.put("name", "");
			map.put("note", "");
		}
		return map;
	}

}
