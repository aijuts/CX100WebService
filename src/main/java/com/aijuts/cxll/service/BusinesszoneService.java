package com.aijuts.cxll.service;

import java.security.interfaces.RSAKey;
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

import com.aijuts.cxll.entity.Businesszone;

@Service("businesszoneService")  
@Transactional
public class BusinesszoneService {
	
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="dataSource")  
    public void setDataSource(DataSource dataSource) {  
        this.jdbcTemplate = new JdbcTemplate(dataSource);  
    }
	
	public List<Businesszone> getAll() {
		String sql = "select * from dbo.t_businesszone";
		RowMapper<Businesszone> mapper = new RowMapper<Businesszone>() {

			@Override
			public Businesszone mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				// TODO Auto-generated method stub
				Businesszone businesszone = new Businesszone();
				businesszone.setZid(rs.getString("zid"));
				businesszone.setName(rs.getString("name"));
				businesszone.setNote(rs.getString("note"));
				businesszone.setIshot(rs.getString("ishot"));
				businesszone.setProviceid(rs.getString("proviceid"));
				businesszone.setCityid(rs.getString("cityid"));
				businesszone.setZoneid(rs.getString("zoneid"));
				return businesszone;
			}
		};
		return jdbcTemplate.query(sql, mapper);
	}

}
