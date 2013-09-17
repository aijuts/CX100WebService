package com.aijuts.cxll.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aijuts.cxll.entity.PopularSellerId;

@Service("popularSellerIdService")  
@Transactional
public class PopularSellerIdService {
	
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="dataSource")  
    public void setDataSource(DataSource dataSource) {  
        this.jdbcTemplate = new JdbcTemplate(dataSource);  
    }
	
	public List<PopularSellerId> getAll() {
		String sql = "select top 5 sellerid "
				+ "from t_order "
				+ "where 1=1 "
				+ "and siteId=1 "
				+ "and sellerid "
				+ "in(select userid from t_seller where freezestate=2 and isdelete=0 and ischannelshow=1 and auditstate=3) "
				+ "group by sellerid "
				+ "order by count(sellerid) desc";
		RowMapper<PopularSellerId> mapper = new RowMapper<PopularSellerId>() {

			@Override
			public PopularSellerId mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				// TODO Auto-generated method stub
				PopularSellerId popularSellerId = new PopularSellerId();
				popularSellerId.setSellerid(rs.getString("sellerid"));
				return popularSellerId;
			}
		};
		return jdbcTemplate.query(sql, mapper);
	}
	
	public Map<String, Object> getSellerName(int sellerid) {
		String sql = "select name from t_seller where userid = " + sellerid;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = jdbcTemplate.queryForMap(sql);
		} catch (Exception e) {
			// TODO: handle exception
			map.put("name", "");
		}
		return map;
	}

}
