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
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Notation;

import com.aijuts.cxll.HomeController;
import com.aijuts.cxll.entity.Cateclassify;
import com.aijuts.cxll.entity.Consumption;
import com.aijuts.cxll.entity.Files;
import com.aijuts.cxll.entity.Image;
import com.aijuts.cxll.entity.Notice;
import com.aijuts.cxll.entity.Order;
import com.aijuts.cxll.entity.Replymsg;
import com.aijuts.cxll.entity.Reviews;
import com.aijuts.cxll.entity.Seller;
import com.aijuts.cxll.entity.SellerPer;
import com.aijuts.cxll.entity.SellerPurpose;
import com.aijuts.cxll.entity.SellerTaEnSe;
import com.aijuts.cxll.util.Tool;

@Service("sellerService")  
@Transactional
public class SellerService {
	
//	private static final Logger logger = LoggerFactory.getLogger(SellerService.class);
	private JdbcTemplate jdbcTemplate;
	private Seller seller;
	private Tool tool;
	
	@Resource(name="dataSource")  
    public void setDataSource(DataSource dataSource) {  
        this.jdbcTemplate = new JdbcTemplate(dataSource);  
    }
	
	public List<Seller> getAll(String latitude, String longitude, String distance, String pageStart, String pageSize, int tagKey, int tagValue, String likeName) {
		try {
			String sql_function = "CREATE FUNCTION [dbo].[fnGetDistance](@LatBegin REAL, @LngBegin REAL, @LatEnd REAL, @LngEnd REAL) RETURNS FLOAT"
					+ "\n" + "  AS "
					+ "\n" + "BEGIN"
					+ "\n" + "  DECLARE @Distance REAL"
					+ "\n" + "  DECLARE @EARTH_RADIUS REAL"
					+ "\n" + "  SET @EARTH_RADIUS = 6378.137  "
					+ "\n" + "  DECLARE @RadLatBegin REAL,@RadLatEnd REAL,@RadLatDiff REAL,@RadLngDiff REAL"
					+ "\n" + "  SET @RadLatBegin = @LatBegin *PI()/180.0  "
					+ "\n" + "  SET @RadLatEnd = @LatEnd *PI()/180.0  "
					+ "\n" + "  SET @RadLatDiff = @RadLatBegin - @RadLatEnd  "
					+ "\n" + "  SET @RadLngDiff = @LngBegin *PI()/180.0 - @LngEnd *PI()/180.0   "
					+ "\n" + "  SET @Distance = 2 *ASIN(SQRT(POWER(SIN(@RadLatDiff/2), 2)+COS(@RadLatBegin)*COS(@RadLatEnd)*POWER(SIN(@RadLngDiff/2), 2)))"
					+ "\n" + "  SET @Distance = @Distance * @EARTH_RADIUS  "
					+ "\n" + "  --SET @Distance = Round(@Distance * 10000) / 10000  "
					+ "\n" + "  RETURN @Distance "
					+ "\n" + "END";
			jdbcTemplate.update(sql_function);
		} catch (Exception e) {
			// TODO: handle exception
//			logger.error("异常：" + e);
		}
//		logger.info(tagKey + " " + tagValue);
		String sql = "";
		sql += "select top " + pageSize + " se.userid, se.logoid, fi.extname, sesta.levid, se.name sename, co.name coname, se.addr, "
				+ "longitude = LEFT(se.point, CHARINDEX(',', se.point) - 1), "
				+ "latitude = SUBSTRING(se.point, CHARINDEX(',', se.point) + 1, LEN(se.point)), "
				+ "dbo.fnGetDistance(" + latitude + "," + longitude + ",SUBSTRING(se.point, CHARINDEX(',', se.point) + 1, LEN(se.point)),LEFT(se.point, CHARINDEX(',', se.point) - 1)) distance "
				+ "from dbo.t_seller se "
				+ "left join dbo.t_consumption co on se.consumid = co.consumid "
				+ "left join dbo.t_files fi on se.logoid = fi.fid "
				+ "left join dbo.t_sellersta sesta on se.userid = sesta.sellerid ";
		switch (tagKey) {
		case 0:
			sql += "";
			break;
		case 1:
			sql += ", dbo.t_sellervegetable sell ";
			break;
		case 2:
			sql += ", dbo.t_purrelation p ";
			break;
		case 3:
			sql += "";
			break;
		case 4:
			sql += "";
			break;
		case 5:
			sql += "";
			break;
		case 6:
			sql += ", dbo.t_sellerrail sr ";
			break;
		}
		sql += "where CHARINDEX(',', se.point) > 0 ";
		if (!distance.equals("0")) {
			sql += "and dbo.fnGetDistance(" + latitude + "," + longitude + ",SUBSTRING(se.point, CHARINDEX(',', se.point) + 1, LEN(se.point)),LEFT(se.point, CHARINDEX(',', se.point) - 1)) < " + distance + " ";
		}
		if (!pageStart.equals("0")) {
			sql += "and dbo.fnGetDistance(" + latitude + "," + longitude + ",SUBSTRING(se.point, CHARINDEX(',', se.point) + 1, LEN(se.point)),LEFT(se.point, CHARINDEX(',', se.point) - 1)) > " + pageStart + " ";
		}
		switch (tagKey) {
		case 0:
			sql += "";
			break;
		case 1:
			sql += "and sell.sid = se.userid and sell.vid = " + tagValue + " ";
			break;
		case 2:
			sql += "and p.sid = se.userid and p.purid = " + tagValue + " ";
			break;
		case 3:
			sql += "and se.consumid = " + tagValue + " ";
			break;
		case 4:
			sql += "and se.zoneid = " + tagValue + " ";
			break;
		case 5:
			sql += "and se.zid = " + tagValue + " ";
			break;
		case 6:
			sql += "and sr.sid = se.userid and sr.railid = " + tagValue + " ";
			break;
		}
		sql += "and se.name like '%" + likeName + "%' ";
		sql += "order by distance asc";
		RowMapper<Seller> mapper = new RowMapper<Seller>() {

			@Override
			public Seller mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				Seller seller = new Seller();
				seller.setUserid(rs.getString("userid"));
				seller.setLogoid(rs.getInt("logoid"));
				seller.setExtname(rs.getString("extname"));
				seller.setLevid(rs.getInt("levid"));
				seller.setSename(rs.getString("sename"));
				seller.setConame(rs.getString("coname"));
				seller.setAddr(rs.getString("addr"));
				seller.setLatitude(rs.getString("latitude"));
				seller.setLongitude(rs.getString("longitude"));
				seller.setDistance(rs.getString("distance"));
				return seller;
			}
		};
		return jdbcTemplate.query(sql, mapper);
	}
	
	public Seller getSeller(int userid){
		String sql = "select se.userid, se.name, se.businesshours, se.tel, se.addr, se.info, se.point "
				+ "from dbo.t_seller se "
				+ "where se.userid = " + userid;
		Map<String, Object> map = new HashMap<String, Object>();
		seller = new Seller();
		try {
			map = jdbcTemplate.queryForMap(sql);
		} catch (Exception e) {
			// TODO: handle exception
//			logger.error("方法getSeller异常：" + e);
			map.put("userid", "");
			map.put("name", "");
			map.put("businesshours", "");
			map.put("tel", "");
			map.put("addr", "");
			map.put("info", "");
			map.put("point", "");
		}
		tool = new Tool();
		String userid1 = tool.verStr(map.get("userid") + "");
		seller.setUserid(userid1);
		String name = tool.verStr(map.get("name") + "");
//		logger.info("name" + name);
		seller.setSename(name);
		String businesshours = map.get("businesshours") + "";
		if (businesshours == null || businesshours.equals("null")) {
			businesshours = "";
		}
		seller.setBusinesshours(businesshours);
		String tel = map.get("tel") + "";
		if (tel == null || tel.equals("null")) {
			tel = "";
		}
		seller.setTel(tel);
		seller.setBusinesshours(businesshours);
		String addr = map.get("addr") + "";
		if (addr == null || addr.equals("null")) {
			addr = "";
		}
		seller.setAddr(addr);
		String info = tool.verStr(map.get("info") + "");
		seller.setInfo(info);
		String point = tool.verStr(map.get("point") + "");
		seller.setPoint(point);
		return seller;
	}
	
	public List<Image> getImageAll(int id, int type) {
		String sql = "select ty.fileid, fi.fid, fi.extname "
				+ "from t_typereffile ty, dbo.t_files fi "
				+ "where ty.fileid = fi.fid "
				+ "and type = " + type + " "
				+ "and disid = " + id;
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Image>(Image.class));
	}
	
	public SellerTaEnSe getSellerTaEnSe(int userid) {
		String sql = "select SUM(taste)/(select COUNT(1) from t_reviews where sellerid = " + userid + ") taste, "
				+ "SUM(environment)/(select COUNT(1) from t_reviews where sellerid = " + userid + ") environment, "
				+ "SUM([service])/(select COUNT(1) from t_reviews where sellerid = " + userid + ") [service] "
				+ "from dbo.t_sellersta "
				+ "where sellerid = " + userid;
		Map<String, Object> map = new HashMap<String, Object>();
		SellerTaEnSe sellerTaEnSe = new SellerTaEnSe();
		try {
			map = jdbcTemplate.queryForMap(sql);
		} catch (Exception e) {
			// TODO: handle exception
//			logger.error("方法getSellerTaste异常：" + e);
			map.put("taste", "0");
			map.put("environment", "0");
			map.put("service", "0");
		}
		tool = new Tool();
		String taste = tool.verStr(map.get("taste") + "");
		sellerTaEnSe.setTaste(taste + "0");
		String environment = tool.verStr(map.get("environment") + "");
		sellerTaEnSe.setEnvironment(environment + "0");
		String service = tool.verStr(map.get("service") + "");
		sellerTaEnSe.setService(service + "0");
		return sellerTaEnSe;
	}
	
	public SellerPer getSellerPer(int userid) {
		String sql = "select se.userid, se.consumid, co.name "
				+ "from dbo.t_seller se "
				+ "left join dbo.t_consumption co on se.consumid = co.consumid "
				+ "where se.userid = " + userid;
		Map<String, Object> map = new HashMap<String, Object>();
		SellerPer sellerPer = new SellerPer();
		try {
			map = jdbcTemplate.queryForMap(sql);
		} catch (Exception e) {
			// TODO: handle exception
//			logger.error("方法getSellerPer异常：" + e);
			map.put("userid", "");
			map.put("consumid", "");
			map.put("name", "");
		}
		tool = new Tool();
		String userid1 = tool.verStr(map.get("userid") + "");
		sellerPer.setUserid(userid1);
		String consumid = tool.verStr(map.get("consumid") + "");
		sellerPer.setConsumid(consumid);
		String name = tool.verStr(map.get("name") + "");
		sellerPer.setName(name);
		return sellerPer;
	}
	
	public List<SellerPurpose> getSellerPurposeAll(int userid) {
		String sql = "select p1.sid, p1.purid, p2.name "
				+ "from dbo.t_purrelation p1 "
				+ "left join dbo.t_purpose p2 on p1.purid = p2.purid "
				+ "where p1.sid = " + userid;
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<SellerPurpose>(SellerPurpose.class));
	}
	
	public List<Cateclassify> getCateclassifyAll(int userid) {
		String sql = "select * from t_cateclassify ca where ca.sid = " + userid;
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Cateclassify>(Cateclassify.class));
	}
	
	public List<Notice> getNoticeAll(int userid) {
		String sql = "select top 5 n.noticeid, n.title "
				+ "from t_notice as n "
				+ "left join dbo.t_seller as s on (n.userid = s.userid) "
				+ "where 1 = 1 "
				+ "and n.userid = " + userid + " "
				+ "and n.siteid = 1 "
				+ "and n.state = 3 "
				+ "and n.type = 1 "
				+ "order by n.[state] asc, "
				+ "n.userid desc, "
				+ "n.stime desc";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Notice>(Notice.class));
	}
	
	public List<Order> getOrderAllTop5(int userid) {
		String sql = "SELECT top 5 o.orderid, o.code, o.[state], o.addtime, o.schedule, o.sellerid, o.consumerid, m.name, m.fid, o.consuercount, o.total "
				+ "FROM cxlm.dbo.t_order o WITH(NOLOCK) left join dbo.t_member m on o.consumerid = m.userid "
				+ "WHERE 1=1 AND o.[type]=2 AND o.[state] NOT IN (1,2,5,7,9,10) AND o.sellerid = " + userid + " "
				+ "ORDER BY o.orderid DESC";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Order>(Order.class));
	}
	
	public Files getFiles(int fid) {
		String sql = "select f.fid, f.extname from dbo.t_files f where fid = " + fid;
		Map<String, Object> map = new HashMap<String, Object>();
		Files files = new Files();
		try {
			map = jdbcTemplate.queryForMap(sql);
		} catch (Exception e) {
			// TODO: handle exception
//			logger.error("方法getFiles异常：" + e);
			map.put("fid", "0");
			map.put("extname", "");
		}
		tool = new Tool();
		String fid1 = tool.verStr(map.get("fid") + "");
		files.setFid(fid1 + "0");
		String extname = tool.verStr(map.get("extname") + "");
		files.setExtname(extname);
		return files;
	}
	
	public List<Reviews> getReviewsAll(int userid) {
		String sql = "select top 5 r.revid, m.userid, m.name, m.fid, r.taste, r.environment, r.service, r.message, r.capita, r.special, r.likefood, r.spendingtime, r.addtime "
				+ "from dbo.t_reviews r "
				+ "left join dbo.t_member m on r.userid = m.userid "
				+ "where r.sellerid = " + userid;
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Reviews>(Reviews.class));
	}
	
	public List<Replymsg> getReplymsg(int revid) {
		String sql = "select r.replyid, r.rtime, r.content "
				+ "from dbo.t_replymsg r "
				+ "where r.msgid = " + revid + " and r.state = 2";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Replymsg>(Replymsg.class));
	}

}
