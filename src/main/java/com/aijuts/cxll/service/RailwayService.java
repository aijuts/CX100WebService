package com.aijuts.cxll.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aijuts.cxll.entity.Railway;

@Service("railwayService")  
@Transactional
public class RailwayService {
	
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="dataSource")
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	public List<Railway> getAll() {
		String sql = "select * from dbo.t_railway";
		RowMapper<Railway> mapper = new RowMapper<Railway>() {

			@Override
			public Railway mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				Railway railway = new Railway();
				railway.setRailid(rs.getString("railid"));
				railway.setProid(rs.getString("proid"));
				railway.setName(rs.getString("name"));
				railway.setParentid(rs.getString("parentid"));
				railway.setCityid(rs.getString("cityid"));
				railway.setNote(rs.getString("note"));
				return railway;
			}
		};
		return jdbcTemplate.query(sql, mapper);
	}

}
