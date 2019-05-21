package com.zzlbe.web.controller;

import com.alibaba.fastjson.JSON;
import com.zzlbe.core.business.OrderBusiness;
import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.util.DateUtil;
import com.zzlbe.core.util.MD5Utils;
import com.zzlbe.dao.entity.*;
import com.zzlbe.dao.mapper.*;
import com.zzlbe.dao.search.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zzlbe.core.util.DateUtil.getDateByStr;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    SellerMapper sellerMapper;
    @Autowired
    SaleMapper saleMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    SuggestMapper suggestMapper;
    @Autowired
    SuggestTopicMapper suggestTopicMapper;
    @Autowired
    OrderBusiness orderBusiness;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    AreaMapper areaMapper;
    @Autowired
    NoticeMapper noticeMapper;

    @Autowired
    AttendanceMapper attendanceMapper;
    @Autowired
    SentgiftMapper sentgiftMapper;

    //展示登录界面
    @RequestMapping("/login")
    public String login() {
        return "admin/z_login";
    }

    /**
     * 管理员端登录功能 成功返回主页面，失败返回登录界面
     * session 静态资源
     */
    @PostMapping(value = "index")
    public ModelAndView index(@RequestParam("login_username") String uname, @RequestParam("username") String username, @RequestParam("password") String password) {
        ModelAndView mav = new ModelAndView();
        if (uname != null && uname != "") {//本地已经登陆
            mav.addObject("login_username", uname);
            int usernum = userMapper.userCount();//用户总人数
            System.out.println("用户总人数:" + usernum);
            int adminernum = sellerMapper.adminerCount();//销售员总数
            int suggestnum1 = suggestMapper.suggestionUndeal1();//未处理好意见
            int suggestnum2 = suggestMapper.suggestionUndeal2();//未处理意见

            List<AmountSearch> amountSearchs = orderMapper.getTotalAmountByMonth();
            AmountSearch amountSearch = amountSearchs.get(amountSearchs.size() - 1);
            mav.addObject("amountSearch", amountSearch);

            mav.addObject("usernum", usernum);
            mav.addObject("adminernum", adminernum);
            mav.addObject("suggestnum1", suggestnum1);
            mav.addObject("suggestnum2", suggestnum2);
            mav.addObject("getTotalAmountFormat", getTotalAmountFormat());

            mav.setViewName("admin/index");
        } else if (uname == null && username == null && password == null) {
            mav.setViewName("admin/z_login");
        } else {
            if (StringUtils.isAnyBlank(username, password)) {
                mav.addObject("errorinfo", "请输入用户名或密码：");
            } else {
                SellerEntity sellerEntity = sellerMapper.selectSeller(username);
                if (sellerEntity != null) {
                    MD5Utils md5 = new MD5Utils();
                    password = md5.getMD5(password);
                    if (sellerEntity.getPassword().equals(password) && sellerEntity.getRank() == 1) {
                        List<AmountSearch> amountSearchs = orderMapper.getTotalAmountByMonth();
                        AmountSearch amountSearch = amountSearchs.get(amountSearchs.size() - 1);

                        mav.addObject("amountSearch", amountSearch);
                        mav.addObject("usernum", userMapper.userCount());
                        mav.addObject("adminernum", sellerMapper.adminerCount());
                        mav.addObject("suggestnum1", suggestMapper.suggestionUndeal1());
                        mav.addObject("suggestionUndeal2", suggestMapper.suggestionUndeal2());
                        mav.addObject("login_username", username);
                        mav.setViewName("admin/index");
                        //queryIndex();

                        return mav;
                    } else {
                        mav.addObject("errorinfo", "请输入正确的用户名或密码！");
                    }
                } else {
                    mav.addObject("errorinfo", "用户不存在！");
                }
            }
            mav.setViewName("admin/z_login");
        }
        return mav;
    }

    @RequestMapping(value = "index")
    public String index() {
        String uname = null, username = null, password = null;
//        index(uname,username,password);
        return "admin/z_login";
    }


    //返回echart所需的格式
    @GetMapping(value = "getTotalAmountFormat")
    @ResponseBody
    public String getTotalAmountFormat() {
        List<AmountSearch> amountSearchs = orderMapper.getTotalAmountByMonth();
        String str = JSON.toJSONString(amountSearchs);
        return str;
    }


    @PostMapping("getTotalAmountByMonth")
    public GenericResponse getTotalAmountByMonth() {
        List<AmountSearch> amountSearchs = orderMapper.getTotalAmountByMonth();
        return new GenericResponse<>(amountSearchs);
    }

    @RequestMapping("getTotalAmount")//当前月销售额
    public GenericResponse getTotalAmount() {
        List<AmountSearch> amountSearchs = orderMapper.getTotalAmountByMonth();
        AmountSearch amountSearch = amountSearchs.get(amountSearchs.size() - 1);
        return new GenericResponse<>(amountSearch);
    }

    @RequestMapping("/test")
    public List<AttendanSearch> test() {
        AttendanceSearch attendanceSearch = new AttendanceSearch();
        List<AttendanSearch> attendanSearches = attendanceMapper.select2ByPage(attendanceSearch);
        System.out.println("attendanSearches:" + attendanSearches.toString());
        System.out.println("attendanSearches:" + attendanSearches);
        System.out.println("json:" + JSON.toJSONString(attendanSearches));
        return attendanSearches;
    }


    @RequestMapping("salesvolume")//当前月销售额
    public ModelAndView salesvolume() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/ch_month.html");
        return mv;
    }

    @RequestMapping("selectAllGoods")//josn,选择农化产品
    @ResponseBody
    public String selectAllGoods() {
        List<GoodsEntity> goodsEntities = goodsMapper.selectAll();
        String str = JSON.toJSONString(goodsEntities);
        return str;
    }

    @RequestMapping("getGoodSellTop10")//josn,商品6月销售量前10
    @ResponseBody
    public String getGoodSellTop10() {
        List<Goodsselltop10Search> goodsselltop10Searches = orderMapper.getGoodSellTop10();
        String str = JSON.toJSONString(goodsselltop10Searches);
        return str;
    }

    @RequestMapping("getGoodSell")//json,商品6个月销售量变化
    @ResponseBody
    public String getGoodSell(@RequestParam("goodsid") long goodsid) {
        if (goodsid == 0) {
            List<Goodsselltop10Search> goodsselltop10Searches = orderMapper.getGoodSellTop10();
            goodsid = goodsselltop10Searches.get(0).getId();
        }
        List<GoodssellSearch> goodssellSearches = orderMapper.getGoodSell(goodsid);
        String str = JSON.toJSONString(goodssellSearches);
        return str;
    }


    @RequestMapping("getSellerSell")//json,销售员6个月销售量变化
    @ResponseBody
    public String getSellerSell(@RequestParam("sellerid") long sellerid) {
        if (sellerid == 0) {
            List<Sellertop10Search> Sellertop10Search = orderMapper.getSellerTop10();
            sellerid = Sellertop10Search.get(0).getId();
        }
        List<GoodssellSearch> goodssellSearches = orderMapper.getSellerSell(sellerid);
        String str = JSON.toJSONString(goodssellSearches);
        return str;
    }

    @RequestMapping("sellersvolume")//当前月销售额
    public ModelAndView sellersvolume() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/ch_person.html");
        return mv;
    }

    @RequestMapping("getSellerTop10")//josn,销售员6月销售量前10
    @ResponseBody
    public String getSellerTop10() {
        List<Sellertop10Search> Sellertop10Searches = orderMapper.getSellerTop10();
        String str = JSON.toJSONString(Sellertop10Searches);
        return str;
    }

    @RequestMapping("getSellerAll")//josn,选择农化产品
    @ResponseBody
    public String getSellerAll() {
        List<SellersellSearch> SellersellSearches = sellerMapper.getSellerAll();
        String str = JSON.toJSONString(SellersellSearches);
        return str;
    }

    @GetMapping("areavolume")//当前地区销售额
    public ModelAndView areavolume() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/ch_area.html");
        return mv;
    }


    @GetMapping("question")//产看技术咨询列表
    public ModelAndView question(Integer pageNo, Integer status) {
        ModelAndView mv = new ModelAndView();
        SuggestSearch suggestSearch = new SuggestSearch();
        if (pageNo != null) {
            suggestSearch.setPage(pageNo);
        }
        if (status != null && status != 8) {
            suggestSearch.setStatus(status);
        }
        suggestSearch.setType(2);
        List<SuggestQuerySearch> suggestQuerySearches = suggestMapper.select2ByPage(suggestSearch);
        Integer total = suggestMapper.selectByPageTotal(suggestSearch);
        int size = suggestSearch.getSize(); //页码大小
        int page = suggestSearch.getPage(); //当前页
        int totalPage = total / size + 1;
        int[] arr = new int[totalPage];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i + 1;
        }
        mv.addObject("arr", arr);
        mv.addObject("totalPage", totalPage);
        mv.addObject("total", total);
        mv.addObject("size", size);
        mv.addObject("page", page);
        mv.addObject("suggestQuerySearches", suggestQuerySearches);
        mv.setViewName("admin/z_consult.html");
        return mv;
    }

    @PostMapping("sendSuggestInfo")//咨询消息，发送
    public ModelAndView sendSuggestInfo(Integer result, String sellerId, String info, long id) {
        SellerEntity sellerEntity = sellerMapper.selectSeller(sellerId);
        SuggestTopicEntity suggestTopicEntity = new SuggestTopicEntity();
        System.out.println("name");
        suggestTopicEntity.setSuggestId(id);
        suggestTopicEntity.setUserId(sellerEntity.getId());
        suggestTopicEntity.setUserName(sellerEntity.getName());
        if (info != null && !(info.equals(""))) {
            suggestTopicEntity.setContent(info);
        }
        suggestTopicEntity.setCreateTime(new Date());
        suggestTopicMapper.insert(suggestTopicEntity);
        SuggestEntity suggestEntity = suggestMapper.selectById(id);
        if (suggestEntity.getStatus() != 2) {//状态不为好  就更新  咨询主题
            suggestEntity.setStatus(result);
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("id", id);//主问题
        mv.setViewName("redirect:/admin/questionInfo");
        return mv;
    }


    @GetMapping("questionInfo")//后台查看-查看具体某一问题咨询  类型1 技术咨询
    public ModelAndView questionInfo(@RequestParam("id") Long id) {

        SuggestEntity suggestEntity = suggestMapper.selectById(id);//获得此问题具体信息

        SuggestTopicSearch suggestTopicSearch = new SuggestTopicSearch();//获取此咨询问题的所有咨询
        suggestTopicSearch.setSuggestId(suggestEntity.getId());
        suggestTopicSearch.setSize(100);
        List<SuggestTopicQuerySearch> SuggestTopicQuerySearches = suggestTopicMapper.select2ByPage(suggestTopicSearch);

        ModelAndView mv = new ModelAndView();
        if (suggestEntity.getUpdateTime() != null) {
            DateUtil du = new DateUtil();
            String date = du.getDateTime(suggestEntity.getUpdateTime());
            mv.addObject("date", date);
        } else {
            suggestEntity.setUpdateTime(new Date());
            suggestMapper.update(suggestEntity);
            mv.addObject("date", suggestEntity.getUpdateTime());
        }
        if (suggestEntity.getStatus() == 0) {
            suggestEntity.setStatus(1);
            suggestMapper.update(suggestEntity);
        }
        mv.addObject("suggestEntity", suggestEntity);//主问题

        mv.addObject("suggestTopicEntities", SuggestTopicQuerySearches);//子问题
        mv.setViewName("admin/z_consultDetail.html");
        return mv;
    }

    @GetMapping("suggest")//产看投诉或者建议
    public ModelAndView suggest(Integer pageNo, Integer status) {
        ModelAndView mv = new ModelAndView();
        SuggestSearch suggestSearch = new SuggestSearch();

        if (status != null) {
            if (status != 8)
                suggestSearch.setStatus(status);
        } else {
            status = new Integer(8);
        }
        mv.addObject("status", status);
        if (pageNo != null) suggestSearch.setPage(pageNo);
        suggestSearch.setType(0);
        List<SuggestQuerySearch> suggestQuerySearches = suggestMapper.select2ByPage(suggestSearch);
        Integer total = suggestMapper.selectByPageTotal(suggestSearch);//两种类型的条数相加
        int page = suggestSearch.getPage(); //当前页
        int size = suggestSearch.getSize(); //页码大小
        int totalPage = total / size + 1;
        int[] arr = new int[totalPage];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i + 1;
        }
        mv.addObject("arr", arr);
        mv.addObject("totalPage", totalPage);
        mv.addObject("total", total);
        mv.addObject("size", size);
        mv.addObject("page", page);
        mv.addObject("suggestQuerySearches", suggestQuerySearches);
        mv.setViewName("admin/z_suggest.html");
        return mv;
    }


    @GetMapping("suggestInfo")//后台查看-查看具体某一问题投诉或者建议  类型2 3 技术咨询
    public ModelAndView suggestInfo(@RequestParam("id") Long id) {
        SuggestEntity suggestEntity = suggestMapper.selectById(id);//获得此问题具体信息
        SuggestTopicSearch suggestTopicSearch = new SuggestTopicSearch();//获取此咨询问题的所有咨询
        suggestTopicSearch.setSuggestId(suggestEntity.getId());
        suggestTopicSearch.setSize(100);
        List<SuggestTopicQuerySearch> SuggestTopicQuerySearches = suggestTopicMapper.select2ByPage(suggestTopicSearch);
        ModelAndView mv = new ModelAndView();
        if (suggestEntity.getUpdateTime() != null) {
            DateUtil du = new DateUtil();
            String date = du.getDateTime(suggestEntity.getUpdateTime());
            mv.addObject("date", date);
        } else {
            suggestEntity.setUpdateTime(new Date());
            suggestMapper.update(suggestEntity);
            mv.addObject("date", suggestEntity.getUpdateTime());
        }
        if (suggestEntity.getStatus() == 0) {
            suggestEntity.setStatus(1);
            suggestMapper.update(suggestEntity);
        }
        mv.addObject("suggestTopicEntities", SuggestTopicQuerySearches);//子问题
        mv.addObject("suggestEntity", suggestEntity);//主问题
        mv.setViewName("admin/z_suggestDetail.html");
        return mv;
    }


    @GetMapping("selfinfo")//后台查看-个人信息修改
    public ModelAndView selfinfo(@RequestParam("str") String name) {

        ModelAndView mv = new ModelAndView();
        SellerSearch sellerSearch = new SellerSearch();
        sellerSearch.setName(name);
        Integer in = sellerMapper.selectByPageTotal(sellerSearch);//根据name查询个人信息信息
        if (in < 1) {//查无此人
            System.out.println("需要重新登录");
            mv.setViewName("admin/z_clear.html");
        } else {//有此人，查出此人
            SellerEntity sellerEntity = sellerMapper.selectSeller(sellerSearch.getName());
            System.out.println("不需要重新登录");
            mv.addObject("seller", sellerEntity);
            mv.setViewName("admin/r_infoSelf.html");
        }
        return mv;
    }

    @GetMapping("gift")//后台查看-礼品领取记录
    public ModelAndView gift(Integer pageNo, Integer status) {
        ModelAndView mv = new ModelAndView();
        GiftSearch giftSearch = new GiftSearch();
        List<SentgiftSearch> sentgiftEntities = sentgiftMapper.select2ByPage(giftSearch);
        mv.addObject("sentgift", sentgiftEntities);
        System.out.println("sentgiftEntities:" + sentgiftEntities);
        Integer total = sentgiftMapper.selectByPageTotal(giftSearch);//两种类型的条数相加
        int page = giftSearch.getPage(); //当前页
        int size = giftSearch.getSize(); //页码大小
        int totalPage = total / size + 1;
        int[] arr = new int[totalPage];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i + 1;
        }

        mv.addObject("total", total);
        mv.addObject("arr", arr);
        mv.addObject("totalPage", totalPage);
        mv.addObject("page", page);
        mv.addObject("size", size);
        mv.setViewName("admin/gi_list.html");
        return mv;
    }

    @GetMapping("/activity")//农产品活动列表
    public ModelAndView activity(Integer pageNo, Integer type) {
        ModelAndView mv = new ModelAndView();
        SaleSearch saleSearch = new SaleSearch();
        if (pageNo != null) {
            saleSearch.setPage(pageNo);
        }
        if (type != null) {
            mv.addObject("type", type);
            if (type != 8) {
                saleSearch.setType(type);
            }
        } else {
            type = new Integer(8);
            mv.addObject("type", type);
        }
        List<GoodsSaleSearch> goodsSaleSearch = saleMapper.select2ByPage(saleSearch);

        Integer total = saleMapper.select2ByPageTotal(saleSearch);
        mv.addObject("total", total);
        int size = saleSearch.getSize(); //页码大小
        mv.addObject("size", size);
        int page = saleSearch.getPage();
        mv.addObject("page", page);
        int totalPage = total / size + 1;
        mv.addObject("totalPage", totalPage);
        int[] arr = new int[totalPage];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i + 1;
        }
        mv.addObject("arr", arr);
        mv.addObject("goodsSale", goodsSaleSearch);
        mv.setViewName("admin/ac_list");
        return mv;
    }

    @GetMapping("/activityInfo")
    public ModelAndView activityInfo(@RequestParam("said") long id) {
        ModelAndView mv = new ModelAndView();
        GoodsSaleSearch goodsSaleSearch = saleMapper.select2ById(id);
        if (goodsSaleSearch == null) {//若sale表无goods数据则插入数据
            SaleEntity saleEntity = new SaleEntity();
            saleEntity.setId(id);
            saleEntity.setCreateTime(new Date());
            saleMapper.insert(saleEntity);
            goodsSaleSearch = saleMapper.select2ById(id);
        }
        if (goodsSaleSearch.getStart() != null && goodsSaleSearch.getStart() == 1) {//若为销售员申请，就加载区域
            String str = goodsSaleSearch.getAreaIds();
            String[] areaid = str.split(";");
            Map<Long, String> map = new HashMap<Long, String>();
            AreaEntity areaEntity = null;
            for (int i = 0; i < areaid.length; i++) {
                areaEntity = areaMapper.select2One(Long.parseLong(areaid[i]));
                map.put(areaEntity.getCountycode(), areaEntity.getCountyname());
            }
            if (areaid.length > 0) {
                mv.addObject("map", map);
                mv.addObject("provincename", areaEntity.getProvincename());
                mv.addObject("cityname", areaEntity.getCityname());
                System.out.println("map:" + map.toString());
            }
        }
        mv.addObject("goodsSale", goodsSaleSearch);
        mv.setViewName("admin/ac_set.html");
        System.out.println("mv" + mv);
        return mv;
    }

    @PostMapping("/saveActivityInfo")
    public ModelAndView saveActivityInfo(@RequestParam("id") Long id, Integer type, BigDecimal discount, Integer start,
                                         BigDecimal reach, BigDecimal minus, String startTime, String overTime) {
        ModelAndView mv = new ModelAndView();
        SaleEntity saleEntity = new SaleEntity();
        saleEntity.setId(id);
        saleEntity.setType(type);
        if (discount != null) saleEntity.setDiscount(discount);
        saleEntity.setStart(start);
        if (reach != null && minus != null) {
            saleEntity.setReach(reach);
            saleEntity.setMinus(minus);
        }
        System.out.println("starTime:" + startTime);
        System.out.println("overTime:" + overTime);
        if (startTime != null && !startTime.equals("") && overTime != null && !overTime.equals("")) {
            saleEntity.setStartTime(getDateByStr(startTime));
            saleEntity.setOverTime(getDateByStr(overTime));
        }
        saleEntity.setUpdateTime(new Date());
        saleMapper.update(saleEntity);
        mv.addObject("said", id);
        mv.setViewName("redirect:/admin/activityInfo");
        System.out.println("mv" + mv);
        return mv;
    }


    @GetMapping("notice")//后台-发布公告
    public ModelAndView notice(Integer pageNo) {
        ModelAndView mv = new ModelAndView();
        NoticeSearch noticeSearch = new NoticeSearch();

        if (pageNo != null) noticeSearch.setPage(pageNo);

        List<NoticeSearch> noticeSearches = noticeMapper.select2ByPage(noticeSearch);
        Integer total = noticeMapper.selectByPageTotal();//两种类型的条数相加
        int page = noticeSearch.getPage(); //当前页
        int size = noticeSearch.getSize(); //页码大小
        int totalPage = total / size + 1;
        int[] arr = new int[totalPage];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i + 1;
        }
        mv.addObject("arr", arr);
        mv.addObject("totalPage", totalPage);
        mv.addObject("size", size);
        mv.addObject("total", total);
        mv.addObject("page", page);
        mv.addObject("noticeSearches", noticeSearches);
        mv.setViewName("admin/z_notice.html");
        return mv;
    }

    @GetMapping("noticeDelete")//后台-公告删除
    public ModelAndView noticeDelete(@RequestParam("id") Integer id) {
        int result=noticeMapper.delete(id);
        ModelAndView mv=new ModelAndView();
        mv.setViewName("redirect:/admin/notice");
        return mv;
    }
    @GetMapping("noticeInfo")//后台-公告删除
    public ModelAndView noticeInfo(@RequestParam("id") Integer id) {
        NoticeEntity noticeEntity=noticeMapper.selectById(id);

        ModelAndView mv=new ModelAndView();
        mv.addObject("notice", noticeEntity);
        System.out.println("noticeEntity:"+noticeEntity);
        mv.setViewName("admin/z_noticeInfo.html");
        return mv;
    }
    @GetMapping("noticeAdd")//后台-公告添加
    public ModelAndView noticeAdd() {
        ModelAndView mv=new ModelAndView();
        mv.setViewName("admin/z_noticeDetail.html");
        return mv;
    }
    @PostMapping("noticeSave")//后台-公告添加
    public ModelAndView noticeSave(String title,String ueInfo) {
        ModelAndView mv=new ModelAndView();
        NoticeEntity noticeEntity=new NoticeEntity();
        noticeEntity.setContent(ueInfo);
        noticeEntity.setTitle(title);
        noticeEntity.setTime(new Date());
        noticeEntity.setNum(new Long(0));
        System.out.println("noticeEntity:"+noticeEntity);
        noticeMapper.insert(noticeEntity);
        mv.setViewName("redirect:/admin/notice");
        return mv;
    }

}

