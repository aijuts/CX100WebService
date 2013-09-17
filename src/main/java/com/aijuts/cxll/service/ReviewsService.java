package com.aijuts.cxll.service;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aijuts.cxll.entity.Reviews;

@Service("reviewsService")  
@Transactional
public class ReviewsService {
	
//	private static final Logger logger = LoggerFactory.getLogger(SellerService.class);
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="dataSource")  
    public void setDataSource(DataSource dataSource) {  
        this.jdbcTemplate = new JdbcTemplate(dataSource);  
    }
	
	public List<Reviews> getReviewsAll(int userid) {
		String sql = "select r.revid, m.userid, m.name, m.fid, r.taste, r.environment, r.service, r.message, r.capita, r.special, r.likefood, r.spendingtime, r.addtime "
				+ "from dbo.t_reviews r "
				+ "left join dbo.t_member m on r.userid = m.userid "
				+ "where r.sellerid = " + userid;
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Reviews>(Reviews.class));
	}

}
