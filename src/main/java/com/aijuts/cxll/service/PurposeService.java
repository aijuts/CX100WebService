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

import com.aijuts.cxll.entity.Purpose;
import com.aijuts.cxll.entity.Vegetable;

@Service("purposeService")  
@Transactional
public class PurposeService {
	
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="dataSource")  
    public void setDataSource(DataSource dataSource) {  
        this.jdbcTemplate = new JdbcTemplate(dataSource);  
    }
	
	public List<Purpose> getAll() {
		String sql = "select * from dbo.t_purpose where type = 1";
		RowMapper<Purpose> mapper = new RowMapper<Purpose>() {

			@Override
			public Purpose mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				// TODO Auto-generated method stub
				Purpose purpose = new Purpose();
				purpose.setPurid(rs.getString("purid"));
				purpose.setName(rs.getString("name"));
				purpose.setNote(rs.getString("note"));
				purpose.setType(rs.getString("type"));
				return purpose;
			}
		};
		return jdbcTemplate.query(sql, mapper);
	}

}
