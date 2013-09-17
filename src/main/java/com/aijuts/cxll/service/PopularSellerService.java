package com.aijuts.cxll.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aijuts.cxll.entity.Files;
import com.aijuts.cxll.entity.PopularSeller;

@Service("popularSellerService")  
@Transactional
public class PopularSellerService {
	
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="dataSource")  
    public void setDataSource(DataSource dataSource) {  
        this.jdbcTemplate = new JdbcTemplate(dataSource);  
    }
	
	public Map<String, Object> getPopularSeller(int sellerid) {
		String sql = "select top 1 fid, userid, siteid, name, extname, size, [state], addtime, iscover "
				+ "from t_files a join t_typereffile b on a.fid = b.fileid "
				+ "where b.type = 1 and b.disid = " + sellerid + " and a.state = 0 order by a.addtime asc";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = jdbcTemplate.queryForMap(sql);
		} catch (Exception e) {
			// TODO: handle exception
			map.put("fid", 0);
			map.put("extname", "");
			map.put("userid", 0);
		}
		return map;
	}

}
