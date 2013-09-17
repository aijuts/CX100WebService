package com.aijuts.cxll.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aijuts.cxll.entity.City;

@Service("cityService")  
@Transactional
public class CityService {
	
//	private static final Logger logger = (Logger) LoggerFactory.getLogger(CityService.class);
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="dataSource")  
    public void setDataSource(DataSource dataSource) {  
        this.jdbcTemplate = new JdbcTemplate(dataSource);  
    }
	
	public List<City> getAll() {
//		logger.debug("Retrieving all cities");
		String sql = "select * from dbo.t_city";
		RowMapper<City> mapper = new RowMapper<City>() {
			@Override
			public City mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				City city = new City();
				city.setCityid(rs.getInt("cityid"));
				city.setProid(rs.getInt("proid"));
				city.setName(rs.getString("name"));
				city.setCount(rs.getInt("count"));
				return city;
			}
		};
		return jdbcTemplate.query(sql, mapper);
	}

}
