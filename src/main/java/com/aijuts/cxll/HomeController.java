package com.aijuts.cxll;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.crypto.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aijuts.cxll.entity.Businesszone;
import com.aijuts.cxll.entity.Cateclassify;
import com.aijuts.cxll.entity.City;
import com.aijuts.cxll.entity.Consumption;
import com.aijuts.cxll.entity.Dish;
import com.aijuts.cxll.entity.Files;
import com.aijuts.cxll.entity.Image;
import com.aijuts.cxll.entity.Logeinfo;
import com.aijuts.cxll.entity.Notice;
import com.aijuts.cxll.entity.NoticeDetail;
import com.aijuts.cxll.entity.Order;
import com.aijuts.cxll.entity.PopularSeller;
import com.aijuts.cxll.entity.PopularSellerId;
import com.aijuts.cxll.entity.Purpose;
import com.aijuts.cxll.entity.Railway;
import com.aijuts.cxll.entity.Replymsg;
import com.aijuts.cxll.entity.Reviews;
import com.aijuts.cxll.entity.Scourcelev;
import com.aijuts.cxll.entity.Seller;
import com.aijuts.cxll.entity.SellerPer;
import com.aijuts.cxll.entity.SellerPurpose;
import com.aijuts.cxll.entity.SellerTaEnSe;
import com.aijuts.cxll.entity.SellerVegetable;
import com.aijuts.cxll.entity.User;
import com.aijuts.cxll.entity.Vegetable;
import com.aijuts.cxll.entity.Zone;
import com.aijuts.cxll.service.BusinesszoneService;
import com.aijuts.cxll.service.CityService;
import com.aijuts.cxll.service.ConsumptionService;
import com.aijuts.cxll.service.DishService;
import com.aijuts.cxll.service.FavoriteService;
import com.aijuts.cxll.service.LogeinfoService;
import com.aijuts.cxll.service.MemberService;
import com.aijuts.cxll.service.NoticeService;
import com.aijuts.cxll.service.OrderService;
import com.aijuts.cxll.service.PopularSellerIdService;
import com.aijuts.cxll.service.PopularSellerService;
import com.aijuts.cxll.service.PurposeService;
import com.aijuts.cxll.service.RailwayService;
import com.aijuts.cxll.service.ReviewsService;
import com.aijuts.cxll.service.ScourcelevService;
import com.aijuts.cxll.service.SellerService;
import com.aijuts.cxll.service.SellervegetableService;
import com.aijuts.cxll.service.UserService;
import com.aijuts.cxll.service.VegetableService;
import com.aijuts.cxll.service.ZoneService;
import com.aijuts.cxll.util.EncryptDecryptData;
import com.aijuts.cxll.util.Tool;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Resource(name = "cityService")
	private CityService cityService;
	
	@Resource(name = "vegetableService")
	private VegetableService vegetableService;
	
	@Resource(name = "purposeService")
	private PurposeService purposeService;
	
	@Resource(name = "consumptionService")
	private ConsumptionService consumptionService;
	
	@Resource(name = "zoneService")
	private ZoneService zoneService;
	
	@Resource(name = "businesszoneService")
	private BusinesszoneService businesszoneService;
	
	@Resource(name = "railwayService")
	private RailwayService railwayService;
	
	@Resource(name = "sellerService")
	private SellerService sellerService;
	
	@Resource(name = "popularSellerIdService")
	private PopularSellerIdService popularSellerIdService;
	
	@Resource(name = "popularSellerService")
	private PopularSellerService popularSellerService;
	
	@Resource(name = "scourcelevService")
	private ScourcelevService scourcelevService;
	
	@Resource(name = "sellervegetableService")
	private SellervegetableService sellervegetableService;
	
	@Resource(name = "noticeService")
	private NoticeService noticeService;
	
	@Resource(name = "orderService")
	private OrderService orderService;
	
	@Resource(name = "reviewsService")
	private ReviewsService reviewsService;
	
	@Resource(name = "dishService")
	private DishService dishService;
	
	@Resource(name = "userService")
	private UserService userService;
	
	@Resource(name = "memberService")
	private MemberService memberService;
	
	@Resource(name = "favoriteService")
	private FavoriteService favoriteService;
	
	@Resource(name = "logeinfoService")
	private LogeinfoService logeinfoService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value = "/sql", method = RequestMethod.GET)
	@ResponseBody
	public List<City> sqlTest() {
//		DB db = new DB();
//		ResultSet resultSet = db.getrs("select * from dbo.t_city where cityid = 1");
//		String s1 = "";
//		try {
//			while (resultSet.next()) {
//				s1 = resultSet.getString(1) + resultSet.getString(2) + resultSet.getString(3) + resultSet.getString(4);
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return s1;
		
		List<City> list = cityService.getAll();
		return list;
	}
	
	public Map<String, Object> getMap(List list, boolean flag) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", list.size());
		map.put("data", list);
		map.put("success", flag + "");
		return map;
	}
	
	public Map<String, Object> getMap(Map<String, Object> detail, boolean flag) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", 1);
		map.put("data", detail);
		map.put("success", flag + "");
		return map;
	}
	
	@RequestMapping(value = "/vegetable/list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getVegetableAll() {
		logger.info("获取菜系列表start");
		List<Vegetable> list = new ArrayList<Vegetable>();
		boolean flag = true;
		try {
			list = vegetableService.getAll();
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			logger.error("异常：" + e);
		}
		logger.info("获取菜系列表end");
		Map<String, Object> map = getMap(list, flag);
		return map;
	}
	
	@RequestMapping(value = "/purpose/list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getPurposeAll() {
		logger.info("获取目的列表start");
		List<Purpose> list = new ArrayList<Purpose>();
		boolean flag = true;
		try {
			list = purposeService.getAll();
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			logger.error("异常：" + e);
		}
		logger.info("获取目的列表end");
		Map<String, Object> map = getMap(list, flag);
		return map;
	}
	
	@RequestMapping(value = "/consumption/list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getConsumptionAll() {
		logger.info("获取人均列表start");
		List<Consumption> list = new ArrayList<Consumption>();
		boolean flag = true;
		try {
			list = consumptionService.getAll();
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			logger.error("异常：" + e);
		}
		logger.info("获取人均列表end");
		Map<String, Object> map = getMap(list, flag);
		return map;
	}
	
	@RequestMapping(value = "/zone/list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getZoneAll() {
		logger.info("获取行政区列表start");
		List<Zone> list = new ArrayList<Zone>();
		boolean flag = true;
		try {
			list = zoneService.getAll();
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			logger.error("异常：" + e);
		}
		logger.info("获取行政区列表end");
		Map<String, Object> map = getMap(list, flag);
		return map;
	}
	
	@RequestMapping(value = "/businesszone/list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getBusinesszoneAll() {
		logger.info("获取商圈列表start");
		List<Businesszone> list = new ArrayList<Businesszone>();
		boolean flag = true;
		try {
			list = businesszoneService.getAll();
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			logger.error("异常：" + e);
		}
		logger.info("获取商圈列表end");
		Map<String, Object> map = getMap(list, flag);
		return map;
	}
	
	@RequestMapping(value = "/railway/list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getRailway() {
		logger.info("获取地铁列表Start");
		List<Railway> list = new ArrayList<Railway>();
		boolean flag = true;
		try {
			list = railwayService.getAll();
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			logger.error("异常：" + e);
		}
		logger.info("获取地铁列表end");
		Map<String, Object> map = getMap(list, flag);
		return map;
	}
	
	@RequestMapping(value = "/seller/list/{lat}/{lng}/{dis}/{pageStart}/{pageSize}/{tagKey}/{tagValue}/{likeName}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getSellerAll(
			@PathVariable String lat, 
			@PathVariable String lng, 
			@PathVariable String dis, 
			@PathVariable String pageStart,
			@PathVariable String pageSize,
			@PathVariable String tagKey,
			@PathVariable String tagValue,
			@PathVariable String likeName) {
		logger.info("获取商家列表Start");
		List<Seller> list_seller = new ArrayList<Seller>();
		List<SellerVegetable> list_sellervegetable = new ArrayList<SellerVegetable>();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Tool tool = new Tool();
		boolean flag = true;
		try {
			String likename = tool.toStringHex(likeName);
			if (likename.equals("0")) {
				likename = "";
			}
			logger.info("likeName " + likeName);
			logger.info("likename " + likename);
			list_seller = sellerService.getAll(lat, lng, dis, pageStart, pageSize, Integer.valueOf(tagKey), Integer.valueOf(tagValue), likename);
			for (int i = 0; i < list_seller.size(); i++) {
				//商家图标路径
				int fid = list_seller.get(i).getLogoid();
				String extname = list_seller.get(i).getExtname();
				String dir;
				if (fid == 0 || extname == null) {
					dir = "";
				}else {
					dir = tool.GetFilePath(fid, extname);
				}
				logger.info("dir:" + dir);
				//商家评分
				int levid = list_seller.get(i).getLevid();
//				Scourcelev scourcelev = scourcelevService.getScourcelev(levid);
				//商家菜系
				int userid = Integer.valueOf(list_seller.get(i).getUserid());
				list_sellervegetable = sellervegetableService.getAllBySid(userid);
				List<Map<String, Object>> list_vegetable = new ArrayList<Map<String, Object>>();
				logger.info("list_sellervegetable.size():" + list_sellervegetable.size());
				for (int j = 0; j < list_sellervegetable.size(); j++) {
					String vid = list_sellervegetable.get(j).getVid();
					String name = list_sellervegetable.get(j).getName();
					logger.info("vid:" + vid + " name:" + name);
//					Vegetable vegetable = vegetableService.getVegetable(vid);
					Map<String, Object> map = new HashMap<String, Object>();
//					logger.info(vegetable.getVid() + " " + vegetable.getName());
					map.put("vid", vid);
					map.put("name", name);
					list_vegetable.add(map);
				}
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userid", list_seller.get(i).getUserid());
				map.put("image", dir);
				String name = list_seller.get(i).getSename();
				if (name == null) {
					name = "";
				}
				map.put("name", name);
//				int lowerlimit = Integer.valueOf(scourcelev.getLowerlimit());
//				int upperlimit = Integer.valueOf(scourcelev.getUpperlimit());
				String evaluate = "";
				switch (levid) {
				case 0:
					evaluate = "0";
					break;
				case 34:
					evaluate = "1";
					break;
				case 35:
					evaluate = "2";
					break;
				case 36:
					evaluate = "3";
					break;
				case 37:
					evaluate = "4";
					break;
				case 38:
					evaluate = "5";
					break;
				}
				map.put("evaluate", evaluate);
				String percapita = list_seller.get(i).getConame();
				if (percapita == null) {
					percapita = "";
				}
				map.put("percapita", percapita);
				String addr = list_seller.get(i).getAddr();
				if (addr == null) {
					addr = "";
				}
				map.put("addr", addr);
				map.put("cuisines", list_vegetable);
				String latitude = list_seller.get(i).getLatitude();
				if (latitude == null) {
					latitude = "";
				}
				map.put("latitude", latitude);
				String longitude = list_seller.get(i).getLongitude();
				if (longitude == null) {
					longitude = "";
				}
				map.put("longitude", longitude);
				String distance = list_seller.get(i).getDistance();
				if (distance == null) {
					distance = "";
				}
				map.put("distance", distance);
				list.add(map);
			}
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			logger.error("异常：" + e);
		}
		logger.info("获取商家列表end");
		Map<String, Object> map = getMap(list, flag);
		return map;
	}
	
	@RequestMapping(value = "/seller/detail/{userid}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getSellerDetail(@PathVariable int userid) {
		logger.info("获取商家详细start");
		Map<String, Object> map_seller = new HashMap<String, Object>();
		Seller seller = new Seller();
		List<Image> list_sellerImage = new ArrayList<Image>();
		List<String> list_image = new ArrayList<String>();
		List<SellerVegetable> list_sellervegetable = new ArrayList<SellerVegetable>();
		List<Map<String, Object>> list_vegetable = new ArrayList<Map<String,Object>>();
		List<SellerPurpose> list_sellerPurpose = new ArrayList<SellerPurpose>();
		List<Map<String, Object>> list_purpose = new ArrayList<Map<String,Object>>();
		List<Cateclassify> list_sellerCateclassify = new ArrayList<Cateclassify>();
		List<Map<String, Object>> list_Cateclassify = new ArrayList<Map<String,Object>>();
		List<Notice> list_sellerNotice = new ArrayList<Notice>();
		List<Map<String, Object>> list_notice = new ArrayList<Map<String,Object>>();
		List<Order> list_sellerOrder = new ArrayList<Order>();
		List<Map<String, Object>> list_order = new ArrayList<Map<String,Object>>();
		List<Reviews> list_sellerReviews = new ArrayList<Reviews>();
		List<Map<String, Object>> list_reviews = new ArrayList<Map<String,Object>>();
		Tool tool = new Tool();
		boolean flag = true;
		try {
			//商家基本信息
			seller = sellerService.getSeller(userid);
			//商家大图
			list_sellerImage = sellerService.getImageAll(userid, 1);
			for (int i = 0; i < list_sellerImage.size(); i++) {
				int fid = list_sellerImage.get(i).getFid();
				String extname = list_sellerImage.get(i).getExtname();
				String dir;
				if (fid == 0 || extname == null) {
					dir = "";
				}else {
					dir = tool.GetFilePath(fid, extname);
				}
				logger.info("seller image: " + dir);
				list_image.add(dir);
			}
			//餐厅口味、环境、服务
			SellerTaEnSe sellerTaEnSe  = sellerService.getSellerTaEnSe(userid);
			//人均
			SellerPer sellerPer = sellerService.getSellerPer(userid);
			//菜系
			list_sellervegetable = sellervegetableService.getAllBySid(userid);
			for (int i = 0; i < list_sellervegetable.size(); i++) {
				String vid = list_sellervegetable.get(i).getVid();
				String name = list_sellervegetable.get(i).getName();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("vid", vid);
				map.put("name", name);
				list_vegetable.add(map);
			}
			//设施与服务
			list_sellerPurpose = sellerService.getSellerPurposeAll(userid);
			for (int i = 0; i < list_sellerPurpose.size(); i++) {
				String purid = list_sellerPurpose.get(i).getPurid();
				String name = list_sellerPurpose.get(i).getName();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("purid", purid);
				map.put("name", name);
				list_purpose.add(map);
			}
			//菜品分类
			list_sellerCateclassify = sellerService.getCateclassifyAll(userid);
			for (int i = 0; i < list_sellerCateclassify.size(); i++) {
				String classid = list_sellerCateclassify.get(i).getClassid();
				String sid = list_sellerCateclassify.get(i).getSid();
				String name = list_sellerCateclassify.get(i).getName();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("classid", classid);
				map.put("sid", sid);
				map.put("name", name);
				list_Cateclassify.add(map);
			}
			//餐厅动态
			list_sellerNotice = sellerService.getNoticeAll(userid);
			for (int i = 0; i < list_sellerNotice.size(); i++) {
				String noticeid = list_sellerNotice.get(i).getNoticeid();
				String title = list_sellerNotice.get(i).getTitle();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("noticeid", noticeid);
				map.put("title", title);
				list_notice.add(map);
			}
			//订单记录
			list_sellerOrder = sellerService.getOrderAllTop5(userid);
			for (int i = 0; i < list_sellerOrder.size(); i++) {
				String orderid = list_sellerOrder.get(i).getOrderid();
				String code = list_sellerOrder.get(i).getCode();
				String state = list_sellerOrder.get(i).getState();
				String addtime = list_sellerOrder.get(i).getAddtime();
				String schedule = list_sellerOrder.get(i).getSchedule();
				String sellerid = list_sellerOrder.get(i).getSellerid();
				String consumerid = list_sellerOrder.get(i).getConsumerid();
				String name = list_sellerOrder.get(i).getName();
				String fidStr = list_sellerOrder.get(i).getFid();
				Files files = sellerService.getFiles(Integer.valueOf(fidStr));
				int fid = Integer.valueOf(files.getFid());
				String extname = files.getExtname();
				String dir;
				if (fid == 0 || extname == null || extname.equals("")) {
					dir = "";
				}else {
					dir = tool.GetFilePath(fid, extname);
				}
				logger.info("member image: " + dir);
				String consuercount = list_sellerOrder.get(i).getConsuercount();
				String total = list_sellerOrder.get(i).getTotal();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("orderid", orderid);
				map.put("code", code);
				map.put("state", state);
				map.put("addtime", addtime);
				map.put("schedule", schedule);
				map.put("sellerid", sellerid);
				map.put("consumerid", consumerid);
				map.put("name", name);
				map.put("dir", dir);
				map.put("consuercount", consuercount);
				map.put("total", total);
				list_order.add(map);
			}
			//餐厅点评
			list_sellerReviews = sellerService.getReviewsAll(userid);
			for (int i = 0; i < list_sellerReviews.size(); i++) {
				String revid = list_sellerReviews.get(i).getRevid();
				String userId = list_sellerReviews.get(i).getUserid();
				String name = list_sellerReviews.get(i).getName();
				String fidStr = list_sellerReviews.get(i).getFid();
				Files files = sellerService.getFiles(Integer.valueOf(fidStr));
				int fid = Integer.valueOf(files.getFid());
				String extname = files.getExtname();
				String dir;
				if (fid == 0 || extname == null || extname.equals("")) {
					dir = "";
				}else {
					dir = tool.GetFilePath(fid, extname);
				}
				logger.info("reviews member image: " + dir);
				String taste = list_sellerReviews.get(i).getTaste();
				String environment = list_sellerReviews.get(i).getEnvironment();
				String service = list_sellerReviews.get(i).getService();
				String message = list_sellerReviews.get(i).getMessage();
				String capita = list_sellerReviews.get(i).getCapita();
				String special = list_sellerReviews.get(i).getSpecial();
				String likefood = list_sellerReviews.get(i).getLikefood();
				String spendingtime = list_sellerReviews.get(i).getSpendingtime();
				String addtime = list_sellerReviews.get(i).getAddtime();
				List<Replymsg> list_sellerReplymsg = sellerService.getReplymsg(Integer.valueOf(revid));
				List<Map<String, Object>> list_replymsg = new ArrayList<Map<String,Object>>();
				for (int j = 0; j < list_sellerReplymsg.size(); j++) {
					String replyid = list_sellerReplymsg.get(j).getReplyid();
					String rtime = list_sellerReplymsg.get(j).getRtime();
					String content = list_sellerReplymsg.get(j).getContent();
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("replyid", replyid);
					map.put("rtime", rtime);
					map.put("content", content);
					list_replymsg.add(map);
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("revid", revid);
				map.put("userid", userId);
				map.put("name", name);
				map.put("dir", dir);
				map.put("taste", taste);
				map.put("environment", environment);
				map.put("service", service);
				map.put("message", message);
				map.put("capita", capita);
				map.put("special", special);
				map.put("likefood", likefood);
				map.put("spendingtime", spendingtime);
				map.put("addtime", addtime);
				map.put("replymsg", list_replymsg);
				list_reviews.add(map);
			}
			
			map_seller.put("userid", seller.getUserid());
			map_seller.put("name", seller.getSename());
			map_seller.put("image", list_image);
			String taste = sellerTaEnSe.getTaste();
			if (taste.equals("null") || taste == null) {
				taste = "0";
			}
			map_seller.put("taste", Integer.valueOf(taste));
			String environment = sellerTaEnSe.getEnvironment();
			if (environment.equals("null") || environment == null) {
				environment = "0";
			}
			map_seller.put("environment", Integer.valueOf(environment));
			String service = sellerTaEnSe.getService();
			if (service.equals("null") || service == null) {
				service = "0";
			}
			map_seller.put("service", Integer.valueOf(service));
			String perCapita = sellerPer.getName();
			if (perCapita.equals("null") || perCapita == null) {
				perCapita = "";
			}
			map_seller.put("perCapita", perCapita);
			map_seller.put("cuisines", list_vegetable);
			map_seller.put("businesshours", seller.getBusinesshours());
			map_seller.put("purpose", list_purpose);
			map_seller.put("tel", seller.getTel());
			map_seller.put("addr", seller.getAddr());
			map_seller.put("info", seller.getInfo());
			map_seller.put("point", seller.getPoint());
			map_seller.put("cateclassify", list_Cateclassify);
			map_seller.put("notice", list_notice);
			map_seller.put("order", list_order);
			map_seller.put("reviews", list_reviews);
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			logger.error("异常：" + e);
		}
		logger.info("获取商家详细end");
		Map<String, Object> map = getMap(map_seller, flag);
		return map;
	}
	
	@RequestMapping(value = "/popularSeller/list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getPopularSellerAll() {
		logger.info("获取热门商家列表start");
		List<PopularSellerId> list_sellerIds = new ArrayList<PopularSellerId>();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Tool tool = new Tool();
		boolean flag = true;
		try {
			list_sellerIds = popularSellerIdService.getAll();
			for (int i = 0; i < list_sellerIds.size(); i++) {
				int sellerid = Integer.valueOf(list_sellerIds.get(i).getSellerid());
				Map<String, Object> map_sellerName = popularSellerIdService.getSellerName(sellerid);
				String name = tool.verStr(map_sellerName.get("name") + "");
				Map<String, Object> map_popularSeller = popularSellerService.getPopularSeller(sellerid);
				int fid = (Integer) map_popularSeller.get("fid");
				String extname = (String) map_popularSeller.get("extname");
				int userid = (Integer) map_popularSeller.get("userid");
				String dir = tool.GetFilePath(fid, extname);
				Map<String, Object> map_image = new HashMap<String, Object>();
				map_image.put("userid", userid);
				map_image.put("name", name);
				map_image.put("dir", dir);
				list.add(map_image);
			}
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			logger.error("异常：" + e);
		}
		logger.info("获取热门商家列表end");
		Map<String, Object> map = getMap(list, flag);
		return map;
	}
	
	@RequestMapping(value = "/seller/notice/detail/{noticeid}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getNoticeDetail(@PathVariable int noticeid) {
		logger.info("获取商家新闻start");
		Map<String, Object> map_notice = new HashMap<String, Object>();
		NoticeDetail noticeDetail = new NoticeDetail();
		boolean flag = true;
		try {
			noticeDetail = noticeService.getNoticeDetail(noticeid);
			map_notice.put("noticeid", noticeDetail.getNoticeid());
			map_notice.put("title", noticeDetail.getTitle());
			map_notice.put("content", noticeDetail.getContent());
			map_notice.put("stime", noticeDetail.getStime());
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			logger.error("异常：" + e);
		}
		logger.info("获取商家新闻end");
		Map<String, Object> map = getMap(map_notice, flag);
		return map;
	}
	
	@RequestMapping(value = "/seller/notice/list/{userid}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getNoticeAll(@PathVariable int userid) {
		logger.info("获取商家新闻列表start");
		List<Notice> list = new ArrayList<Notice>();
		boolean flag = true;
		try {
			list = noticeService.getNoticeAll(userid);
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			logger.error("异常：" + e);
		}
		logger.info("获取商家新闻列表end");
		Map<String, Object> map = getMap(list, flag);
		return map;
	}
	
	@RequestMapping(value = "/seller/order/list/{userid}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getOrderAll(@PathVariable int userid) {
		logger.info("获取商家订单记录列表start");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Order> list_order = new ArrayList<Order>();
		Tool tool = new Tool();
		boolean flag = true;
		try {
			list_order = orderService.getOrderAll(userid);
			for (int i = 0; i < list_order.size(); i++) {
				String orderid = list_order.get(i).getOrderid();
				String code = list_order.get(i).getCode();
				String state = list_order.get(i).getState();
				String addtime = list_order.get(i).getAddtime();
				String schedule = list_order.get(i).getSchedule();
				String sellerid = list_order.get(i).getSellerid();
				String consumerid = list_order.get(i).getConsumerid();
				String name = list_order.get(i).getName();
				String fidStr = list_order.get(i).getFid();
				Files files = sellerService.getFiles(Integer.valueOf(fidStr));
				int fid = Integer.valueOf(files.getFid());
				String extname = files.getExtname();
				String dir;
				if (fid == 0 || extname == null || extname.equals("")) {
					dir = "";
				}else {
					dir = tool.GetFilePath(fid, extname);
				}
				logger.info("member image: " + dir);
				String consuercount = list_order.get(i).getConsuercount();
				String total = list_order.get(i).getTotal();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("orderid", orderid);
				map.put("code", code);
				map.put("state", state);
				map.put("addtime", addtime);
				map.put("schedule", schedule);
				map.put("sellerid", sellerid);
				map.put("consumerid", consumerid);
				map.put("name", name);
				map.put("dir", dir);
				map.put("consuercount", consuercount);
				map.put("total", total);
				list.add(map);
			}
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			logger.error("异常：" + e);
		}
		logger.info("获取商家订单记录列表end");
		Map<String, Object> map = getMap(list, flag);
		return map;
	}
	
	@RequestMapping(value = "/seller/reviews/list/{userid}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getReviewsAll(@PathVariable int userid) {
		logger.info("获取商家评论列表start");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Reviews> list_reviews = new ArrayList<Reviews>();
		Tool tool = new Tool();
		boolean flag = true;
		try {
			list_reviews = reviewsService.getReviewsAll(userid);
			for (int i = 0; i < list_reviews.size(); i++) {
				String revid = list_reviews.get(i).getRevid();
				String userId = list_reviews.get(i).getUserid();
				String name = list_reviews.get(i).getName();
				String fidStr = list_reviews.get(i).getFid();
				Files files = sellerService.getFiles(Integer.valueOf(fidStr));
				int fid = Integer.valueOf(files.getFid());
				String extname = files.getExtname();
				String dir;
				if (fid == 0 || extname == null || extname.equals("")) {
					dir = "";
				}else {
					dir = tool.GetFilePath(fid, extname);
				}
				logger.info("reviews member image: " + dir);
				String taste = list_reviews.get(i).getTaste();
				String environment = list_reviews.get(i).getEnvironment();
				String service = list_reviews.get(i).getService();
				String message = list_reviews.get(i).getMessage();
				String capita = list_reviews.get(i).getCapita();
				String special = list_reviews.get(i).getSpecial();
				String likefood = list_reviews.get(i).getLikefood();
				String spendingtime = list_reviews.get(i).getSpendingtime();
				String addtime = list_reviews.get(i).getAddtime();
				List<Replymsg> list_sellerReplymsg = sellerService.getReplymsg(Integer.valueOf(revid));
				List<Map<String, Object>> list_replymsg = new ArrayList<Map<String,Object>>();
				for (int j = 0; j < list_sellerReplymsg.size(); j++) {
					String replyid = list_sellerReplymsg.get(j).getReplyid();
					String rtime = list_sellerReplymsg.get(j).getRtime();
					String content = list_sellerReplymsg.get(j).getContent();
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("replyid", replyid);
					map.put("rtime", rtime);
					map.put("content", content);
					list_replymsg.add(map);
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("revid", revid);
				map.put("userid", userId);
				map.put("name", name);
				map.put("dir", dir);
				map.put("taste", taste);
				map.put("environment", environment);
				map.put("service", service);
				map.put("message", message);
				map.put("capita", capita);
				map.put("special", special);
				map.put("likefood", likefood);
				map.put("spendingtime", spendingtime);
				map.put("addtime", addtime);
				map.put("replymsg", list_replymsg);
				list.add(map);
			}
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			logger.error("异常：" + e);
		}
		logger.info("获取商家评论列表end");
		Map<String, Object> map = getMap(list, flag);
		return map;
	}
	
	@RequestMapping(value = "/seller/dish/list/{userid}/{type}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getDishAllBySeller(
			@PathVariable int userid, 
			@PathVariable int type) {
		logger.info("获取商家菜品列表start");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Dish> list_dish = new ArrayList<Dish>();
		Tool tool = new Tool();
		boolean flag = true;
		try {
			list_dish = dishService.getDishAll(userid, type);
			for (int i = 0; i < list_dish.size(); i++) {
				Dish dish = new Dish();
				dish.setDisid(list_dish.get(i).getDisid());
				dish.setCode(list_dish.get(i).getCode());
				dish.setName(list_dish.get(i).getName());
				dish.setSename(list_dish.get(i).getSename());
				dish.setCaname(list_dish.get(i).getCaname());
				dish.setPrice(list_dish.get(i).getPrice());
				dish.setUnit(list_dish.get(i).getUnit());
				List<String> list_image = new ArrayList<String>();
				List<Image> listImage = sellerService.getImageAll(Integer.valueOf(dish.getDisid()), 2);
				for (int j = 0; j < listImage.size(); j++) {
					int fid = listImage.get(j).getFid();
					String extname = listImage.get(j).getExtname();
					String dir;
					if (fid == 0 || extname == null) {
						dir = "";
					}else {
						dir = tool.GetFilePath(fid, extname);
					}
					logger.info("dish image: " + dir);
					list_image.add(dir);
				}
				Map<String, Object> map_dish = new HashMap<String, Object>();
				map_dish.put("disid", dish.getDisid());
				map_dish.put("code", dish.getCode());
				map_dish.put("name", dish.getName());
				map_dish.put("sename", dish.getSename());
				map_dish.put("caname", dish.getCaname());
				map_dish.put("price", dish.getPrice());
				map_dish.put("unit", dish.getUnit());
				map_dish.put("image", list_image);
				list.add(map_dish);
			}
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			logger.error("异常：" + e);
		}
		logger.info("获取商家菜品列表end");
		Map<String, Object> map = getMap(list, flag);
		return map;
	}
	
	@RequestMapping(value = "/dish/detail/{disid}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getDishDetail(@PathVariable int disid) {
		logger.info("获取商家菜品start");
		Map<String, Object> map_dish = new HashMap<String, Object>();
		Dish dish = new Dish();
		Tool tool = new Tool();
		boolean flag = true;
		try {
			dish = dishService.getDish(disid);
			map_dish.put("disid", dish.getDisid());
			map_dish.put("code", dish.getCode());
			map_dish.put("name", dish.getName());
			map_dish.put("sename", dish.getSename());
			map_dish.put("caname", dish.getCaname());
			map_dish.put("price", dish.getPrice());
			map_dish.put("unit", dish.getUnit());
			map_dish.put("info", dish.getInfo());
			map_dish.put("content", dish.getContent());
			List<String> list_image = new ArrayList<String>();
			List<Image> listImage = sellerService.getImageAll(Integer.valueOf(dish.getDisid()), 2);
			for (int j = 0; j < listImage.size(); j++) {
				int fid = listImage.get(j).getFid();
				String extname = listImage.get(j).getExtname();
				String dir;
				if (fid == 0 || extname == null) {
					dir = "";
				}else {
					dir = tool.GetFilePath(fid, extname);
				}
				logger.info("dish image: " + dir);
				list_image.add(dir);
			}
			map_dish.put("image", list_image);
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			logger.error("异常：" + e);
		}
		logger.info("获取商家菜品end");
		Map<String, Object> map = getMap(map_dish, flag);
		return map;
	}
	
	@RequestMapping(value = "/user/reg/{username}/{password}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUserReg(@PathVariable String username, @PathVariable String password) {
		logger.info("用户注册start");
		Map<String, Object> map_user_reg = new HashMap<String, Object>();
		Tool tool = new Tool();
		boolean flag = true;
		try {
			String userName = tool.toStringHex(username);
			int count = userService.getUserCount(userName);
			if (count == 0) {
				boolean flag_email = tool.checkEmail(userName);
				boolean flag_phone = tool.checkPhone(userName);
				Date date = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateStr = dateFormat.format(date);
				int n = userService.insertUser(userName, password, dateStr, flag_email, flag_phone);
				switch (n) {
				case 0:
					map_user_reg.put("isReg", 0);
					map_user_reg.put("info", "用户注册失败！");
					break;
				case 1:
					int userid = userService.getUserid(userName);
					int levid = scourcelevService.getScourceLevid();
					if (userid > 0 && levid > 0) {
						int m = userService.insertMember(userid, userName, levid, flag_email, flag_phone);
						switch (m) {
						case 0:
							map_user_reg.put("isReg", 0);
							map_user_reg.put("info", "注册成功，获取用户资料失败！");
							break;
						case 1:
							Map<String, Object> mapRegInfo = userService.getUserById(userid);
							map_user_reg.put("userid", tool.verStr(mapRegInfo.get("userid") + ""));
							map_user_reg.put("siteid", tool.verStr(mapRegInfo.get("siteid") + ""));
							map_user_reg.put("account", tool.verStr(mapRegInfo.get("account") + ""));
							map_user_reg.put("mailaccount", tool.verStr(mapRegInfo.get("mailaccount") + ""));
							map_user_reg.put("mobileaccount", tool.verStr(mapRegInfo.get("mobileaccount") + ""));
							map_user_reg.put("nameaccount", tool.verStr(mapRegInfo.get("nameaccount") + ""));
							map_user_reg.put("pwd", tool.verStr(mapRegInfo.get("pwd") + ""));
							map_user_reg.put("v_pwd", tool.verStr(mapRegInfo.get("v_pwd") + ""));
							map_user_reg.put("type", tool.verStr(mapRegInfo.get("type") + ""));
							map_user_reg.put("regtime", tool.verStr(mapRegInfo.get("regtime") + ""));
							map_user_reg.put("name", tool.verStr(mapRegInfo.get("name") + ""));
							map_user_reg.put("state", tool.verStr(mapRegInfo.get("state") + ""));
							map_user_reg.put("isdelete", tool.verStr(mapRegInfo.get("isdelete") + ""));
							map_user_reg.put("isReg", 1);
							map_user_reg.put("info", "注册成功，欢迎进入用户中心！");
							break;
						}
					}else {
						map_user_reg.put("isReg", 0);
						map_user_reg.put("info", "用户注册数据不正确！");
					}
					break;
				}
			}else {
				map_user_reg.put("isReg", 0);
				map_user_reg.put("info", "该账号已存在！");
			}
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			logger.error("异常：" + e);
		}
		logger.info("用户注册end");
		Map<String, Object> map = getMap(map_user_reg, flag);
		return map;
	}
	
	@RequestMapping(value = "/user/login/{username}/{password}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUserLogin(@PathVariable String username, @PathVariable String password) {
		logger.info("用户登录start");
		Map<String, Object> map_user_login = new HashMap<String, Object>();
		Tool tool = new Tool();
		boolean flag = true;
		try {
			String userName = tool.toStringHex(username);
			map_user_login = userService.getUserLogin(userName, password);
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			logger.error("异常：" + e);
		}
		logger.info("用户登录end");
		Map<String, Object> map = getMap(map_user_login, flag);
		return map;
	}
	
	@RequestMapping(value = "/file/dir/{fid}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getImage(@PathVariable int fid) {
		logger.info("获取图片start");
		Map<String, Object> map_file = new HashMap<String, Object>();
		Tool tool = new Tool();
		boolean flag = true;
		try {
			Files files = sellerService.getFiles(fid);
//			int fid = Integer.valueOf(files.getFid());
			String extname = files.getExtname();
			String dir;
			if (fid == 0 || extname == null || extname.equals("")) {
				dir = "";
			}else {
				dir = tool.GetFilePath(fid, extname);
			}
			map_file.put("image", dir);
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			logger.error("异常：" + e);
		}
		logger.info("获取图片end");
		Map<String, Object> map = getMap(map_file, flag);
		return map;
	}
	
	@RequestMapping(value = "/user/member/detail/{userid}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getMember(@PathVariable int userid) {
		logger.info("获取用户详细信息start");
		Map<String, Object> map_member = new HashMap<String, Object>();
		Map<String, Object> map_user_member = new HashMap<String, Object>();
		Tool tool = new Tool();
		boolean flag = true;
		try {
			map_user_member = memberService.getMember(userid);
			//用户头像
			int fid = (Integer) map_user_member.get("fid");
			Files files = sellerService.getFiles(fid);
			String extname = files.getExtname();
			String dir;
			if (fid == 0 || extname == null || extname.equals("")) {
				dir = "";
			}else {
				dir = tool.GetFilePath(fid, extname);
			}
			//普通订单数量
			int generalOrderCount = memberService.getOrderCount(userid, 2);
			//外卖订单数量
			int takeoutOrderCount = memberService.getOrderCount(userid, 1);
			//餐厅收藏数量
			int favoriteSellerCount = memberService.getFavoriteCount(userid, 1);
			//菜品收藏数量
			int favoriteDishCount = memberService.getFavoriteCount(userid, 2);
			//我的点评数量
			int reviewCount = memberService.getReviewCount(userid);
			//会员所在省市区
			String zoneidStr = tool.verStr(map_user_member.get("zoneid") + "");
			String ssq = "";
			if (zoneidStr.equals("") || zoneidStr == null) {
				ssq = "";
			}else {
				Map<String, Object> map_zone = zoneService.getZoneDetail(Integer.valueOf(zoneidStr));
				int cityid = Integer.valueOf(map_zone.get("cityid") + "");
				String zoneName = map_zone.get("name") + "";
				Map<String, Object> map_city = zoneService.getCityDetail(cityid);
				int proid = Integer.valueOf(map_city.get("proid") + "");
				String cityName = map_city.get("name") + "";
				Map<String, Object> map_provice = zoneService.getProviceDetail(proid);
				String proviceName = map_provice.get("name") + "";
				ssq = proviceName + cityName + zoneName;
			}
			//会员喜欢的菜系 4;54;25;40;16;10;12;47;48;13;51;20;11;53;64;66;69;70;71;72;73;74
			String vidStr = tool.verStr(map_user_member.get("vid") + "");
			List<Map<String, Object>> list_vid = new ArrayList<Map<String, Object>>();
			if (vidStr.equals("") || vidStr == null) {
				
			}else {
				String[] vidSplit = vidStr.split(";");
				for (int i = 0; i < vidSplit.length; i++) {
					Vegetable vegetable = vegetableService.getVegetable(Integer.valueOf(vidSplit[i]));
					Map<String, Object> map_vid = new HashMap<String, Object>();
					map_vid.put("vid", vegetable.getVid());
					map_vid.put("name", vegetable.getName());
					list_vid.add(map_vid);
				}
			}
			//会员消费水平
			String consumidStr = tool.verStr(map_user_member.get("consumid") + "");
			Map<String, Object> map_con = new HashMap<String, Object>();
			if (consumidStr.equals("") || consumidStr == null) {
				map_con.put("consumid", "0");
				map_con.put("name", "");
			}else {
				Map<String, Object> map_consum = consumptionService.getConsumDetail(Integer.valueOf(consumidStr));
				map_con.put("consumid", tool.verStr(map_consum.get("consumid") + ""));
				map_con.put("name", tool.verStr(map_consum.get("name") + ""));
			}
			map_member.put("memberid", tool.verStr(map_user_member.get("memberid") + ""));
			map_member.put("userid", tool.verStr(map_user_member.get("userid") + ""));
			map_member.put("name", tool.verStr(map_user_member.get("name") + ""));
			map_member.put("mobile", tool.verStr(map_user_member.get("mobile") + ""));
			map_member.put("realname", tool.verStr(map_user_member.get("realname") + ""));
			map_member.put("sex", tool.verStr(map_user_member.get("sex") + ""));
			map_member.put("lev", tool.verStr(map_user_member.get("lev") + ""));
			map_member.put("zoneid", tool.verStr(map_user_member.get("zoneid") + ""));
			map_member.put("addr", tool.verStr(map_user_member.get("addr") + ""));
			map_member.put("pro", tool.verStr(map_user_member.get("pro") + ""));
			map_member.put("birthday", tool.verStr(map_user_member.get("birthday") + ""));
			map_member.put("qq", tool.verStr(map_user_member.get("qq") + ""));
			map_member.put("msn", tool.verStr(map_user_member.get("msn") + ""));
			map_member.put("vid", tool.verStr(map_user_member.get("vid") + ""));
			map_member.put("interest", tool.verStr(map_user_member.get("interest") + ""));
			map_member.put("mail", tool.verStr(map_user_member.get("mail") + ""));
			map_member.put("mailstate", tool.verStr(map_user_member.get("mailstate") + ""));
			map_member.put("regdate", tool.verStr(map_user_member.get("regdate") + ""));
			map_member.put("mstate", tool.verStr(map_user_member.get("mstate") + ""));
			map_member.put("btype", tool.verStr(map_user_member.get("btype") + ""));
			map_member.put("epic", tool.verStr(map_user_member.get("epic") + ""));
			map_member.put("consumid", tool.verStr(map_user_member.get("consumid") + ""));
			map_member.put("vantages", tool.verStr(map_user_member.get("vantages") + ""));
			map_member.put("isdelete", tool.verStr(map_user_member.get("isdelete") + ""));
			map_member.put("usablevantages", tool.verStr(map_user_member.get("usablevantages") + ""));
			map_member.put("image", dir);
			map_member.put("generalOrderCount", generalOrderCount);
			map_member.put("takeoutOrderCount", takeoutOrderCount);
			map_member.put("favoriteSellerCount", favoriteSellerCount);
			map_member.put("favoriteDishCount", favoriteDishCount);
			map_member.put("reviewCount", reviewCount);
			map_member.put("ssq", ssq);
			map_member.put("cuisines", list_vid);
			map_member.put("consum", map_con);
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			logger.error("异常：" + e);
		}
		logger.info("获取用户详细信息end");
		Map<String, Object> map = getMap(map_member, flag);
		return map;
	}
	
	@RequestMapping(value = "/user/favorite/count/{sellerid}/{userid}/{type}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getFavorite(
			@PathVariable int sellerid, 
			@PathVariable int userid, 
			@PathVariable int type) {
		logger.info("查询用户是否收藏start");
		Map<String, Object> map_favorite = new HashMap<String, Object>();
		Tool tool = new Tool();
		boolean flag = true;
		try {
			int count = favoriteService.getFavCount(sellerid, userid, type);
			if (count > 0) {
				map_favorite.put("isFav", 0);  //0表示已收藏，1表示未收藏
				map_favorite.put("info", "已收藏");
			}else {
				map_favorite.put("isFav", 1);  //0表示已收藏，1表示未收藏
				map_favorite.put("info", "未收藏");
			}
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			logger.error("异常：" + e);
		}
		logger.info("查询用户是否收藏end");
		Map<String, Object> map = getMap(map_favorite, flag);
		return map;
	}
	
	@RequestMapping(value = "/user/favorite/insert/{sellerid}/{userid}/{type}/{title}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> insertFavorite(
			@PathVariable int sellerid, 
			@PathVariable int userid, 
			@PathVariable int type, 
			@PathVariable String title) {
		logger.info("用户收藏start");
		Map<String, Object> map_favorite = new HashMap<String, Object>();
		Tool tool = new Tool();
		boolean flag = true;
		try {
			int count = favoriteService.getFavCount(sellerid, userid, type);
			if (count > 0) {
				map_favorite.put("isFav", 0);  //0表示不可收藏或收藏失败，1表示收藏成功
				map_favorite.put("info", "已收藏");
			}else {
				String sellerTitle = tool.toStringHex(title);
				Date date = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateStr = dateFormat.format(date);
				int n = favoriteService.insertFavorite(sellerid, userid, type, sellerTitle, dateStr);
				switch (n) {
				case 0:
					map_favorite.put("isFav", 0);  //0表示不可收藏或收藏失败，1表示收藏成功
					map_favorite.put("info", "收藏失败");
					break;
				case 1:
					map_favorite.put("isFav", 1);  //0表示不可收藏或收藏失败，1表示收藏成功
					map_favorite.put("info", "收藏成功");
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			logger.error("异常：" + e);
		}
		logger.info("用户收藏end");
		Map<String, Object> map = getMap(map_favorite, flag);
		return map;
	}
	
	@RequestMapping(value = "/seller/logeinfo/list/{sellerid}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getLogeinfo(@PathVariable int sellerid) {
		logger.info("查询商家包厢start");
		List<Logeinfo> list_loge = new ArrayList<Logeinfo>();
		Tool tool = new Tool();
		boolean flag = true;
		try {
			list_loge = logeinfoService.getLogeinfoAll(sellerid);
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			logger.error("异常：" + e);
		}
		logger.info("查询商家包厢end");
		Map<String, Object> map = getMap(list_loge, flag);
		return map;
	}
	
	@RequestMapping(value = "/order/commit/{bookingDate}/{bookingTime}/{sellerid}/{userid}/{number}/{loge}/{type}/{remark}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> commitOrder(
			@PathVariable String bookingDate,
			@PathVariable String bookingTime,
			@PathVariable int sellerid,
			@PathVariable int userid,
			@PathVariable int number,
			@PathVariable int loge,
			@PathVariable int type,
			@PathVariable String remark) {
		logger.info("提交订单start");
		Map<String, Object> map_order = new HashMap<String, Object>();
		int orderid = 0, count = 0;
		Tool tool = new Tool();
		boolean flag = true;
		try {
			String addtime = tool.getLocationTime();
			String bookTime = bookingDate + " " + bookingTime;
			String reMark = tool.toStringHex(remark);
			String content = reMark.substring(7, reMark.length());
			orderid = orderService.insertOrder(addtime, bookTime, bookTime, sellerid, userid, number, loge, type, content);
			if (orderid > 0) {
				String code = tool.CreateOrderCode(orderid, 2);
				logger.info("code " + code);
				count = orderService.updateOrder(orderid, code);
				switch (count) {
				case 0:
					map_order.put("commitState", count);
					map_order.put("commitInfo", "订单提交失败");
					break;
				case 1:
					map_order.put("commitState", count);
					map_order.put("commitInfo", "订单提交成功");
					break;
				}
			}else {
				map_order.put("commitState", count);
				map_order.put("commitInfo", "订单提交失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			orderService.deleteOrder(orderid);
			map_order.put("commitState", count);
			map_order.put("commitInfo", "订单提交失败");
			logger.error("异常：" + e);
		}
		logger.info("提交订单end");
		Map<String, Object> map = getMap(map_order, flag);
		return map;
	}
	
	@RequestMapping(value = "/user/member/update/{userid}/{key}/{value}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> updateMember(
			@PathVariable int userid, 
			@PathVariable String key, 
			@PathVariable String value) {
		logger.info("修改用户资料start");
		Map<String, Object> map_member = new HashMap<String, Object>();
		Tool tool = new Tool();
		boolean flag = true;
		try {
			Map<String, Object> map_update = new HashMap<String, Object>();
			String existInfo = "";
			if (key.equals("name")) {
				existInfo = "该用户昵称已存在";
			}
			if (key.equals("mobile")) {
				existInfo = "该用户手机号已存在";
			}
			if (key.equals("mail")) {
				existInfo = "该用户邮箱已存在";
			}
			String memberValue = tool.toStringHex(value);
//			String memberValue = value;
			if (key.equals("name") || key.equals("mobile") || key.equals("mail")) {
				//判断昵称是否存在
				int isExist = memberService.getMemerCount(key, memberValue);
				if (isExist != 0) {
					map_member.put("updateState", 0);
					map_member.put("updateInfo", existInfo);
				}else {
					map_update.put("key", key);
					map_update.put("value", memberValue);
					int count = memberService.updateMember(userid, map_update);
					switch (count) {
					case 0:
						map_member.put("updateState", count);
						map_member.put("updateInfo", "用户资料修改失败");
						break;
					case 1:
						map_member.put("updateState", count);
						map_member.put("updateInfo", "用户资料修改成功");
						break;
					}
				}
			}else {
				map_update.put("key", key);
				map_update.put("value", memberValue);
				int count = memberService.updateMember(userid, map_update);
				switch (count) {
				case 0:
					map_member.put("updateState", count);
					map_member.put("updateInfo", "用户资料修改失败");
					break;
				case 1:
					map_member.put("updateState", count);
					map_member.put("updateInfo", "用户资料修改成功");
					break;
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			map_member.put("updateState", 0);
			map_member.put("updateInfo", "用户资料修改失败");
			flag = false;
			logger.error("异常：" + e);
		}
		logger.info("修改用户资料end");
		Map<String, Object> map = getMap(map_member, flag);
		return map;
	}
	
	@RequestMapping(value = "/seller/detail/order/top5/{sellerid}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getOrderAllTop5(@PathVariable int sellerid) {
		logger.info("获取商家前5条订单start");
		List<Map<String, Object>> list_order = new ArrayList<Map<String,Object>>();
		List<Order> list_sellerOrder = new ArrayList<Order>();
		Tool tool = new Tool();
		boolean flag = true;
		try {
			//订单记录
			list_sellerOrder = sellerService.getOrderAllTop5(sellerid);
			for (int i = 0; i < list_sellerOrder.size(); i++) {
				String orderid = list_sellerOrder.get(i).getOrderid();
				String code = list_sellerOrder.get(i).getCode();
				String state = list_sellerOrder.get(i).getState();
				String addtime = list_sellerOrder.get(i).getAddtime();
				String schedule = list_sellerOrder.get(i).getSchedule();
				String sellerId = list_sellerOrder.get(i).getSellerid();
				String consumerid = list_sellerOrder.get(i).getConsumerid();
				String name = list_sellerOrder.get(i).getName();
				String fidStr = list_sellerOrder.get(i).getFid();
				Files files = sellerService.getFiles(Integer.valueOf(fidStr));
				int fid = Integer.valueOf(files.getFid());
				String extname = files.getExtname();
				String dir;
				if (fid == 0 || extname == null || extname.equals("")) {
					dir = "";
				}else {
					dir = tool.GetFilePath(fid, extname);
				}
				logger.info("member image: " + dir);
				String consuercount = list_sellerOrder.get(i).getConsuercount();
				String total = list_sellerOrder.get(i).getTotal();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("orderid", orderid);
				map.put("code", code);
				map.put("state", state);
				map.put("addtime", addtime);
				map.put("schedule", schedule);
				map.put("sellerid", sellerId);
				map.put("consumerid", consumerid);
				map.put("name", name);
				map.put("dir", dir);
				map.put("consuercount", consuercount);
				map.put("total", total);
				list_order.add(map);
			}
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			logger.error("异常：" + e);
		}
		logger.info("获取商家前5条订单end");
		Map<String, Object> map = getMap(list_order, flag);
		return map;
	}
	
	
	
//	@RequestMapping(value = "/user/member/address/{zoneid}", method = RequestMethod.GET)
//	@ResponseBody
//	public Map<String, Object> getAddress(@PathVariable int zoneid) {
//		logger.info("获取用户省市区start");
//		Map<String, Object> map_address = new HashMap<String, Object>();
//		Map<String, Object> map_zone = new HashMap<String, Object>();
//		Map<String, Object> map_city = new HashMap<String, Object>();
//		Map<String, Object> map_provice = new HashMap<String, Object>();
//		Tool tool = new Tool();
//		boolean flag = true;
//		try {
//			map_zone = zoneService.getZoneDetail(zoneid);
//			int cityid = Integer.valueOf(map_zone.get("cityid") + "");
//			String zoneName = map_zone.get("name") + "";
//			
//			map_city = zoneService.getCityDetail(cityid);
//			int proid = Integer.valueOf(map_city.get("proid") + "");
//			String cityName = map_city.get("name") + "";
//			
//			map_provice = zoneService.getProviceDetail(proid);
//			String proviceName = map_provice.get("name") + "";
//			
//			map_address.put("address", proviceName + cityName + zoneName);
//		} catch (Exception e) {
//			// TODO: handle exception
//			flag = false;
//			map_address.put("address", "");
//			logger.error("异常：" + e);
//		}
//		logger.info("获取用户省市区end");
//		Map<String, Object> map = getMap(map_address, flag);
//		return map;
//	}
	
}
