package com.aijuts.cxll.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aijuts.cxll.HomeController;
import com.aijuts.cxll.entity.Scourcelev;

@Service("scourcelevService")  
@Transactional
public class ScourcelevService {
	
//	private static final Logger logger = LoggerFactory.getLogger(ScourcelevService.class);
	private JdbcTemplate jdbcTemplate;
	private Scourcelev scourcelev;
	
	@Resource(name="dataSource")  
    public void setDataSource(DataSource dataSource) {  
        this.jdbcTemplate = new JdbcTemplate(dataSource);  
    }
	
	public Scourcelev getScourcelev(int levid) {
		String sql = "select sc.levid, sc.name, sc.upperlimit, sc.lowerlimit, sc.picid, sc.type, sc.parentid, sc.note "
				+ "from dbo.t_scourcelev sc "
				+ "where levid = " + levid;
		Map<String, Object> map = new HashMap<String, Object>();
		scourcelev = new Scourcelev();
		try {
			map = jdbcTemplate.queryForMap(sql);
		} catch (Exception e) {
			// TODO: handle exception
//			logger.error("方法getScourcelev异常：" + e);
			map.put("levid", "");
			map.put("name", "");
			map.put("upperlimit", "");
			map.put("lowerlimit", "");
			map.put("picid", "");
			map.put("type", "");
			map.put("parentid", "");
			map.put("note", "");
//			scourcelev.setLevid("");
//			scourcelev.setName("");
//			scourcelev.setUpperlimit("");
//			scourcelev.setLowerlimit("");
//			scourcelev.setPicid("");
//			scourcelev.setType("");
//			scourcelev.setParentid("");
//			scourcelev.setNote("");
		}
		scourcelev.setLevid(map.get("levid")+"");
		scourcelev.setName(map.get("name")+"");
		scourcelev.setUpperlimit(map.get("upperlimit")+"");
		scourcelev.setLowerlimit(map.get("lowerlimit")+"");
		scourcelev.setPicid(map.get("picid")+"");
		scourcelev.setType(map.get("type")+"");
		scourcelev.setParentid(map.get("parentid")+"");
		scourcelev.setNote(map.get("note")+"");
		return scourcelev;
	}
	
	public int getScourceLevid() {
		String sql = "select top 1 levid "
				+ "from t_scourcelev "
				+ "where type = 1 and parentid <> 0 "
				+ "order by lowerlimit ASC";
		int n = 0;
		try {
			n = jdbcTemplate.queryForInt(sql);
		} catch (Exception e) {
			// TODO: handle exception
//			logger.error("方法getScourceLevid异常：" + e);
		}
		return n;
	}

}
