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

import com.aijuts.cxll.entity.Vegetable;

@Service("vegetableService")  
@Transactional
public class VegetableService {
	
//	private static final Logger logger = LoggerFactory.getLogger(VegetableService.class);
	private JdbcTemplate jdbcTemplate;
	private Vegetable vegetable;
	
	@Resource(name="dataSource")  
    public void setDataSource(DataSource dataSource) {  
        this.jdbcTemplate = new JdbcTemplate(dataSource);  
    }
	
	public List<Vegetable> getAll() {
		String sql = "select * from dbo.t_vegetable";
		RowMapper<Vegetable> mapper = new RowMapper<Vegetable>() {

			@Override
			public Vegetable mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				// TODO Auto-generated method stub
				Vegetable vegetable = new Vegetable();
				vegetable.setVid(rs.getString("vid"));
				vegetable.setName(rs.getString("name"));
				vegetable.setContent(rs.getString("content"));
				vegetable.setParentid(rs.getString("parentid"));
				vegetable.setSort(rs.getString("sort"));
				return vegetable;
			}
		};
		return jdbcTemplate.query(sql, mapper);
	}
	
	public Vegetable getVegetable(int vid) {
		String sql = "select * from dbo.t_vegetable where vid = " + vid;
		Map<String, Object> map = new HashMap<String, Object>();
		vegetable = new Vegetable();
		try {
			map = jdbcTemplate.queryForMap(sql);
		} catch (Exception e) {
			// TODO: handle exception
//			logger.error("方法getVegetable异常：" + e);
			map.put("vid", "");
			map.put("name", "");
			map.put("content", "");
			map.put("parentid", "");
			map.put("sort", "");
//			vegetable.setVid("");
//			vegetable.setName("");
//			vegetable.setContent("");
//			vegetable.setParentid("");
//			vegetable.setSort("");
		}
		vegetable.setVid(map.get("vid")+"");
		vegetable.setName(map.get("name")+"");
		vegetable.setContent(map.get("content")+"");
		vegetable.setParentid(map.get("parentid")+"");
		vegetable.setSort(map.get("sort")+"");
		return vegetable;
	}

}
