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

import com.aijuts.cxll.entity.Zone;

@Service("zoneService")  
@Transactional
public class ZoneService {
	
//	private static final Logger logger = LoggerFactory.getLogger(ZoneService.class);
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="dataSource")  
    public void setDataSource(DataSource dataSource) {  
        this.jdbcTemplate = new JdbcTemplate(dataSource);  
    }
	
	public List<Zone> getAll() {
		String sql = "select * from dbo.t_zone where cityid = 66";
		RowMapper<Zone> mapper = new RowMapper<Zone>() {

			@Override
			public Zone mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				Zone zone = new Zone();
				zone.setZoneid(rs.getString("zoneid"));
				zone.setCityid(rs.getString("cityid"));
				zone.setName(rs.getString("name"));
				return zone;
			}
		};
		return jdbcTemplate.query(sql, mapper);
	}
	
	public Map<String, Object> getZoneDetail(int zoneid) {
		String sql = "select * from dbo.t_zone where zoneid = " + zoneid;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = jdbcTemplate.queryForMap(sql);
		} catch (Exception e) {
			// TODO: handle exception
//			logger.error("方法getZoneDetail异常：" + e);
			map.put("zoneid", 0);
			map.put("cityid", 0);
			map.put("name", "");
		}
		return map;
	}
	
	public Map<String, Object> getCityDetail(int cityid) {
		String sql = "select * from dbo.t_city where cityid = " + cityid;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = jdbcTemplate.queryForMap(sql);
		} catch (Exception e) {
			// TODO: handle exception
//			logger.error("方法getCityDetail异常：" + e);
			map.put("cityid", 0);
			map.put("proid", 0);
			map.put("name", "");
			map.put("count", 0);
		}
		return map;
	}
	
	public Map<String, Object> getProviceDetail(int proid) {
		String sql = "select * from dbo.t_provice where proid = " + proid;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = jdbcTemplate.queryForMap(sql);
		} catch (Exception e) {
			// TODO: handle exception
//			logger.error("方法getProviceDetail异常：" + e);
			map.put("proid", 0);
			map.put("name", "");
			map.put("count", 0);
			map.put("areaid", 0);
		}
		return map;
	}

}
