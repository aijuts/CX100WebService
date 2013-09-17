package com.aijuts.cxll.service;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.aijuts.cxll.entity.Order;
import com.aijuts.cxll.util.Tool;

@Service("orderService")  
@Transactional
public class OrderService {
	
//	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
	private JdbcTemplate jdbcTemplate;
	private Tool tool;
	
	@Resource(name="dataSource")
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        tool = new Tool();
    }
	
	public List<Order> getOrderAll(int userid) {
		String sql = "SELECT o.orderid, o.code, o.[state], o.addtime, o.schedule, o.sellerid, o.consumerid, m.name, m.fid, o.consuercount, o.total "
				+ "FROM cxlm.dbo.t_order o WITH(NOLOCK) left join dbo.t_member m on o.consumerid = m.userid "
				+ "WHERE 1=1 AND o.[type]=2 AND o.[state] NOT IN (1,2,5,7,9,10) AND o.sellerid = " + userid + " "
				+ "ORDER BY o.orderid DESC";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Order>(Order.class));
	}
	
	public int insertOrder(String addtime, String rectime, String schedule, int sellerid, int userid, int number, int loge, int type, String content) {
		String sql = "insert into t_order "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) "
				+ "select @@identity orderid";
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		String code = tool.getRandomTime();
		try {
			Object[] orderInfo = {code, 3, 1, addtime, rectime, schedule, sellerid, userid, number, loge, 0, 0, 0, "", type, 0, 0, content, "", 0};
//			Object[] orderInfo = {code, 3, 1, "2013-09-03 14:27:21", "2013-09-03 15:00:00", "2013-09-03 15:00:00", 7, 394, 8, 0, 0, 0, 0, "", 2, 0, 0, "", "", 0};
			count = jdbcTemplate.update(sql, orderInfo);
			if (count > 0) {
				map = getOrderDetail(code);
				count = (Integer) map.get("orderid");
			}
		} catch (Exception e) {
			// TODO: handle exception
//			logger.error("方法insertOrder异常：" + e);
		}
		return count;
	}
	
	public Map<String, Object> getOrderDetail(String code) {
		String sql = "select * from dbo.t_order where code = '" + code + "'";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = jdbcTemplate.queryForMap(sql);
		} catch (Exception e) {
			// TODO: handle exception
//			logger.error("方法getOrderDetail异常：" + e);
		}
		return map;
	}
	
	public int updateOrder(int orderid, String code) {
		String sql = "update t_order set code = '" + code + "' where orderid = " + orderid;
		int count = 0;
		try {
			count = jdbcTemplate.update(sql);
		} catch (Exception e) {
			// TODO: handle exception
//			logger.error("方法updateOrder异常：" + e);
		}
		return count;
	}
	
	public int deleteOrder(int orderid) {
		String sql = "delete from dbo.t_order where orderid = " + orderid;
		int count = 0;
		try {
			count = jdbcTemplate.update(sql);
		} catch (Exception e) {
			// TODO: handle exception
//			logger.error("方法deleteOrder异常：" + e);
		}
		return count;
	}

}
