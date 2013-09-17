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

import com.aijuts.cxll.entity.Notice;
import com.aijuts.cxll.entity.NoticeDetail;

@Service("noticeService")  
@Transactional
public class NoticeService {
	
//	private static final Logger logger = LoggerFactory.getLogger(SellerService.class);
	private JdbcTemplate jdbcTemplate;
	private NoticeDetail noticeDetail;
	
	@Resource(name="dataSource")  
    public void setDataSource(DataSource dataSource) {  
        this.jdbcTemplate = new JdbcTemplate(dataSource);  
    }
	
	public List<Notice> getNoticeAll(int userid) {
		String sql = "select n.noticeid, n.title "
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
	
	public NoticeDetail getNoticeDetail(int noticeid) {
		String sql = "select noticeid, title, content, stime, isshow "
				+ "from dbo.t_notice "
				+ "where noticeid = " + noticeid;
		Map<String, Object> map = new HashMap<String, Object>();
		noticeDetail = new NoticeDetail();
		try {
			map = jdbcTemplate.queryForMap(sql);
		} catch (Exception e) {
			// TODO: handle exception
			map.put("noticeid", "");
			map.put("title", "");
			map.put("content", "");
			map.put("stime", "");
		}
		String noticeid1 = map.get("noticeid") + "";
		if (noticeid1 == null || noticeid1.equals("null")) {
			noticeid1 = "";
		}
		noticeDetail.setNoticeid(noticeid1);
		String title = map.get("title") + "";
		if (title == null || title.equals("null")) {
			title = "";
		}
		noticeDetail.setTitle(title);
		String content = map.get("content") + "";
		if (content == null || content.equals("null")) {
			content = "";
		}
		noticeDetail.setContent(content);
		String stime = map.get("stime") + "";
		if (stime == null || stime.equals("null")) {
			stime = "";
		}
		noticeDetail.setStime(stime);
		return noticeDetail;
	}

}
