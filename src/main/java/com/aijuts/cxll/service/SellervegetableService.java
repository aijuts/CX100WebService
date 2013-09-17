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

import com.aijuts.cxll.entity.SellerVegetable;

@Service("sellervegetableService")  
@Transactional
public class SellervegetableService {
	
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="dataSource")  
    public void setDataSource(DataSource dataSource) {  
        this.jdbcTemplate = new JdbcTemplate(dataSource);  
    }
	
	public List<SellerVegetable> getAllBySid(int sid) {
		String sql = "select se.sid, se.vid, ve.name "
				+ "from dbo.t_sellervegetable se "
				+ "left join t_vegetable ve on se.vid = ve.vid "
				+ "where se.sid = " + sid;
		RowMapper<SellerVegetable> mapper = new RowMapper<SellerVegetable>() {

			@Override
			public SellerVegetable mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				// TODO Auto-generated method stub
				SellerVegetable sellerVegetable = new SellerVegetable();
				sellerVegetable.setSid(rs.getString("sid"));
				sellerVegetable.setVid(rs.getString("vid"));
				sellerVegetable.setName(rs.getString("name"));
				return sellerVegetable;
			}
		};
		return jdbcTemplate.query(sql, mapper);
	}

}
