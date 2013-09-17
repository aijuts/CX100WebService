package com.aijuts.cxll.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aijuts.cxll.entity.Dish;
import com.aijuts.cxll.entity.Reviews;
import com.aijuts.cxll.entity.SellerPer;
import com.aijuts.cxll.util.Tool;

@Service("dishService")  
@Transactional
public class DishService {
	
//	private static final Logger logger = LoggerFactory.getLogger(SellerService.class);
	private JdbcTemplate jdbcTemplate;
	private Tool tool;
	
	@Resource(name="dataSource")  
    public void setDataSource(DataSource dataSource) {  
        this.jdbcTemplate = new JdbcTemplate(dataSource);  
    }
	
	public List<Dish> getDishAll(int userid, int type) {
		String sql = "select d.disid, d.code, d.name, se.name sename, ca.name caname, d.price, d.unit "
				+ "from dbo.t_dishes d "
				+ "left join dbo.t_seller se on d.userid = se.userid "
				+ "left join dbo.t_cateclassify ca on d.type = ca.classid "
				+ "where d.userid = " + userid + " "
				+ "and d.type = " + type;
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Dish>(Dish.class));
	}
	
	public Dish getDish(int disid) {
		String sql = "select d.disid, d.code, d.name, se.name sename, ca.name caname, d.price, d.unit, d.info, d.content "
				+ "from dbo.t_dishes d "
				+ "left join dbo.t_seller se on d.userid = se.userid "
				+ "left join dbo.t_cateclassify ca on d.type = ca.classid "
				+ "where d.disid = " + disid;
		Map<String, Object> map = new HashMap<String, Object>();
		Dish dish = new Dish();
		try {
			map = jdbcTemplate.queryForMap(sql);
		} catch (Exception e) {
			// TODO: handle exception
//			logger.error("方法getDish异常：" + e);
			map.put("disid", "");
			map.put("code", "");
			map.put("name", "");
			map.put("sename", "");
			map.put("caname", "");
			map.put("price", "");
			map.put("unit", "");
			map.put("info", "");
			map.put("content", "");
		}
		tool = new Tool();
		String disid1 = tool.verStr(map.get("disid") + "");
		dish.setDisid(disid1);
		String code = tool.verStr(map.get("code") + "");
		dish.setCode(code);;
		String name = tool.verStr(map.get("name") + "");
		dish.setName(name);
		String sename = tool.verStr(map.get("sename") + "");
		dish.setSename(sename);
		String caname = tool.verStr(map.get("caname") + "");
		dish.setCaname(caname);
		String price = tool.verStr(map.get("price") + "");
		dish.setPrice(price);
		String unit = tool.verStr(map.get("unit") + "");
		dish.setUnit(unit);
		String info = tool.verStr(map.get("info") + "");
		dish.setInfo(info);
		String content = tool.verStr(map.get("content") + "");
		dish.setContent(content);
		return dish;
	}

}
