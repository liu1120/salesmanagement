package com.zzlbe.web.controller;

import com.alibaba.fastjson.JSON;
import com.zzlbe.core.business.UserBusiness;
import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.dto.UserInfoDTO;
import com.zzlbe.core.request.*;
import com.zzlbe.core.response.AreaVO;
import com.zzlbe.core.util.DateUtil;
import com.zzlbe.dao.entity.*;
import com.zzlbe.dao.mapper.*;
import com.zzlbe.dao.search.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static com.zzlbe.core.util.DateUtil.getDateByStr;
import static com.zzlbe.core.util.DateUtil.getDateByStr2;

@RestController
@RequestMapping("user")
public class UserController {
    @Resource
    private UserMapper userMapper;
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private SaleMapper saleMapper;
    @Resource
    private AreaMapper areaMapper;
    @Resource
    private SentgiftMapper sentgiftMapper;
    @Resource
    private GoodstopicMapper goodstopicMapper;

    @Resource
    private GiftMapper giftMapper;

    @Resource
    private AddressMapper addressMapper;

    @Resource
    private GoodssortMapper goodssortMapper;
    @Resource
    private UserBusiness userBusiness;

    //依赖注入

    @GetMapping(value = "login")
    public UserEntity login(@RequestParam("username") String username, @RequestParam("password") String password) {
        //调用dao层
        UserEntity userEntity = userMapper.userLogin(username, password);

        return userEntity;
    }

    @GetMapping(value = "getGoodsInfo")
    public GoodsEntity getGoodsInfo(@RequestParam("goodsid") long goodsid) {
        GoodsEntity goodsEntity = goodsMapper.selectById(goodsid);
        return goodsEntity;
    }

    @GetMapping(value = "selectGoodsByName")//通过商品名模糊查找
    public List<GoodsEntity> selectGoodsByName(@RequestParam("goodsname") String goodsname) {
//        GoodsSearch goodsSearch=new GoodsSearch();
        List<GoodsEntity> list = goodsMapper.selectGoodsByName(goodsname);
        System.out.println("goodsname:" + goodsname);
        for (int i = 0; i < list.size(); i++) {

            System.out.println("list:" + list.get(i).toString());
        }
        return list;
    }

    @GetMapping(value = "getGoodsList")//查找全部商品
    public List<GoodsEntity> getGoodsList() {
        List<GoodsEntity> list = goodsMapper.selectAll();
        return list;
    }

    @GetMapping(value = "selectGoodsBySort")//查找全部商品根据分类
    public List<GoodsEntity> selectGoodsBySort(String sortid) {
        GoodsSearch goodsSearch = new GoodsSearch();
        goodsSearch.setSort(sortid);
        List<GoodsEntity> list = goodsMapper.selectByPage(goodsSearch);
        return list;
    }

    @GetMapping(value = "selectGoodsByGname")//查找全部商品根据商品名
    public List<GoodsEntity> selectGoodsByGname(String gname) {
        GoodsSearch goodsSearch = new GoodsSearch();
        goodsSearch.setName(gname);
        List<GoodsEntity> list = goodsMapper.selectByPage(goodsSearch);
        return list;
    }

    @GetMapping(value = "getCommentsByGoodsid")//查找全部商品评论
    public List<GoodstopicEntity> getCommentsByGoodsid(@RequestParam("goodsid") long goodsid) {
        List<GoodstopicEntity> list = goodstopicMapper.selectByGoodsid(goodsid);
        return list;
    }

    @GetMapping(value = "getGifts")//查找全部礼品
    public List<GiftEntity> getGifts() {
        List<GiftEntity> list = giftMapper.selectAll();
        return list;
    }
    @GetMapping(value = "getGiftsByUid")//查找用户兑换全部礼品
    public List<SentgiftSearch> getGiftsByUid(@RequestParam("uid") long uid) {
        GiftSearch giftSearch=new GiftSearch();
        giftSearch.setToid(uid);

        List<SentgiftSearch> list = sentgiftMapper.select2ByPage(giftSearch);
        return list;
    }

    @GetMapping(value = "getGiftCreditById")//根据礼品id返回所需积分
    public long getGiftCreditById(@RequestParam("id") long id) {
        return giftMapper.selectById(id).getCredit();
    }

    @GetMapping(value = "getGiftById")//根据礼品id返回礼品详情
    public GiftEntity getGiftById(@RequestParam("id") long id) {
        return giftMapper.selectById(id);
    }

    @GetMapping(value = "getGoodsSort")//查找全部商品分类
    public List<GoodssortEntity> getGoodsSort() {
        List<GoodssortEntity> list = goodssortMapper.selectAll();
        return list;
    }

    @GetMapping(value = "getUsedCredit")//积分使用记录
    public List<CreditConsumeBySendGiftVO> getUsedCredit(@RequestParam("uid") long uid) {
        List<CreditConsumeBySendGiftVO> list = sentgiftMapper.sendGiftByUser(uid);
        return list;
    }

    @GetMapping(value = "getCreditDetail")//积分兑换详情
    public SentgiftEntity getCreditDetail(@RequestParam("sid") long sid) {
        SentgiftEntity sentgiftEntity = sentgiftMapper.selectById(sid);
        return sentgiftEntity;
    }

    @GetMapping(value = "getCreditAdd")//积分添加记录
    public List<OrderEntity> getCreditAdd(@RequestParam("uid") long uid) {
        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setOrUserId(uid);//评价有积分。
        orderSearch.setOrStatus(8);
        List<OrderEntity> orderEntity = orderMapper.selectByPage(orderSearch);
        return orderEntity;
    }

    @GetMapping(value = "getGoodsSortName")//查找商品分类对应的name ==admin位置报错  adminadminadmin
    public String getGoodsSortName(Long id) {
        GoodssortEntity goodssortEntity = goodssortMapper.selectById(id);
        System.out.println("goodssortEntity.getName():" + goodssortEntity.getName());
        return goodssortEntity.getName();
    }

    /**
     * 用户使用积分兑换产品只可兑换一份礼品
     * 用户的积分大于礼品积分，处理：减去用户积分，产生送礼品。
     *
     * @param uid  用户id
     * @param adid 下单地址adid
     * @param id   礼品id
     * @return 0失败，1成功
     */
    @GetMapping(value = "getGift")
    public int getGift(@RequestParam("uid") long uid, @RequestParam("id") long id, @RequestParam("adid") long adid) {
        UserEntity userEntity = userMapper.selectById(uid);
        GiftEntity giftEntity = giftMapper.selectById(id);
        AddressEntity addressEntity = addressMapper.selectById(adid);
        if (userEntity.getCredit() < giftEntity.getCredit()) return 0;
        userEntity.setCredit(userEntity.getCredit() - giftEntity.getCredit());//更新用户积分
        userMapper.update(userEntity);


        AreaVO areavo = JSON.parseObject(addressEntity.getAddress(), AreaVO.class);


        Long formid = areaMapper.selectOne(areavo.getTowncode()).getSpid();

        SentgiftEntity sentgiftEntity = new SentgiftEntity();
        sentgiftEntity.setGiftId(id);
        sentgiftEntity.setToid(uid);//需要制定派送人
        sentgiftEntity.setFromid(formid);
        sentgiftEntity.setTophone(userEntity.getPhone());
        sentgiftEntity.setAddress(adid + "");
        sentgiftEntity.setType(0);
        sentgiftEntity.setNum((long) 1);
        sentgiftEntity.setCredit(giftEntity.getCredit());
        sentgiftEntity.setStatus(0);
        DateUtil du = new DateUtil();
        sentgiftEntity.setDate(getDateByStr(du.getDateTime()));
        sentgiftMapper.insert(sentgiftEntity);
        return 1;
    }

    @GetMapping(value = "getGoodsBySort")//查找某一分类下的商品
    public List<GoodsEntity> getGoodsBySort(@RequestParam("sortid") long sortid) {
        List<GoodsEntity> list = goodsMapper.getGoodsBySort(sortid);
        return list;
    }

    /**
     * 添加收货地址
     *
     * @return 收货地址id
     */
    @GetMapping(value = "addAddress2")
    public Long addAddress(@RequestParam("provincecode") Long provincecode, @RequestParam("countycode") Long countycode,
                           @RequestParam("towncode") Long towncode, @RequestParam("citycode") Long citycode,
                           @RequestParam("info") String info, @RequestParam("uid") Long uid,
                           @RequestParam("name") String name, @RequestParam("phone") Long phone) {
        AreaVO areaVO = new AreaVO();
        areaVO.setProvincecode(provincecode);
        areaVO.setCitycode(citycode);
        areaVO.setCountycode(countycode);
        areaVO.setTowncode(towncode);
        String areaStr = JSON.toJSONString(areaVO);
        AddressEntity addressEntity = new AddressEntity(null, areaStr, info, uid, name, phone, 0);
        addressMapper.insert(addressEntity);
        return addressEntity.getId();
    }

    /**
     * 添加收货地址
     *
     * @return 收货地址id
     */
    @PostMapping(value = "addAddress")
    public GenericResponse addAddress(@RequestBody @Valid UserAddressForm userAddressForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            return GenericResponse.ERROR.setMessage(list.get(0).getDefaultMessage());
        }
        if (userAddressForm.getUid() == null) {
            return GenericResponse.ERROR;
        }
        AreaVO areaVO = new AreaVO();
        areaVO.setProvincecode(userAddressForm.getProvincecode());
        areaVO.setCitycode(userAddressForm.getCitycode());
        areaVO.setCountycode(userAddressForm.getCountycode());
        areaVO.setTowncode(userAddressForm.getTowncode());
        String areaStr = JSON.toJSONString(areaVO);

        AddressEntity addressEntity = new AddressEntity(null, areaStr,
                userAddressForm.getInfo(), userAddressForm.getUid(),
                userAddressForm.getName(), userAddressForm.getPhone(), 0);
        addressMapper.insert(addressEntity);
        return new GenericResponse<>(addressEntity.getId());
    }

    /**
     * 添加收货地址
     *
     * @return 收货地址id
     */
    @PostMapping(value = "modifyAddress")
    public GenericResponse modifyAddress(@RequestBody @Valid UserAddressForm userAddressForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            return GenericResponse.ERROR.setMessage(list.get(0).getDefaultMessage());
        }
        if (userAddressForm.getId() == null) {
            return GenericResponse.ERROR;
        }
        AreaVO areaVO = new AreaVO();
        areaVO.setProvincecode(userAddressForm.getProvincecode());
        areaVO.setCitycode(userAddressForm.getCitycode());
        areaVO.setCountycode(userAddressForm.getCountycode());
        areaVO.setTowncode(userAddressForm.getTowncode());
        String areaStr = JSON.toJSONString(areaVO);

        AddressEntity addressEntity = new AddressEntity(userAddressForm.getId(), areaStr,
                userAddressForm.getInfo(), null,
                userAddressForm.getName(), userAddressForm.getPhone(), userAddressForm.getStatus());
        addressMapper.update(addressEntity);

        return new GenericResponse<>(addressEntity.getId());
    }

    @GetMapping(value = "getSaleGoods")//查找所有促销商品
    public List<GoodsEntity> getSaleGoods(@RequestParam("sortid") long sortid) {
        List<GoodsEntity> list = goodsMapper.getGoodsBySort(sortid);
        return list;
    }

    @GetMapping(value = "getSaleGoodsById")//通过商品id查找促销商品
    public SaleEntity getSaleGoodsById(@RequestParam("goodsid") long goodsid) {
        SaleEntity saleEntity = saleMapper.selectById(goodsid);
        return saleEntity;
    }

    @GetMapping(value = "selectCreditById")//通过用户id查找积分
    public String selectCreditById(@RequestParam("uid") long uid) {
        long credit = userMapper.selectById(uid).getCredit();
        String str = "{score:" + credit + "}";
        return str;
    }

    @GetMapping(value = "getOrderList")//查找用户下的订单
    public List getOrderList(@RequestParam("uid") long uid) {
        /*return orderMapper.selectByUid(uid);*/
        return orderMapper.selectByUid2(uid);
    }

    @GetMapping(value = "getOrder")//查找用户下的订单
    public OrderEntity getOrder(@RequestParam("id") long id) {
        OrderEntity orderEntity = orderMapper.selectById(id);
        return orderEntity;
    }

    @GetMapping(value = "getOrderByStatus")//查看不同状态下订单
    public List<OrderEntity> getOrderByStatus(@RequestParam("uid") long uid, Integer status) {
        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setOrUserId(uid);
        if (status != null) {
            orderSearch.setOrStatus(status);
        }
        return orderMapper.selectByPage(orderSearch);
    }

    @GetMapping(value = "getOrderStatus")//查看订单状态
    public Integer getOrderStatus(@RequestParam("orid") long orid) {
        return orderMapper.selectById(orid).getOrStatus();
    }

    @GetMapping(value = "addComments")//为已完成的订单添加评论。1成功，0失败.同时修改订单表状态，为已评价
    public int addComments(@RequestParam("comment") String comment, @RequestParam("orderid") long orderid) {
        OrderEntity orderEntity = orderMapper.selectById(orderid);
        if (orderEntity.getOrStatus() != 7) return 0;
        GoodstopicEntity goodstopicEntity = new GoodstopicEntity();
        goodstopicEntity.setGoodsid(orderEntity.getOrGoodsId());

        DateUtil du = new DateUtil();
        goodstopicEntity.setCreatetime(getDateByStr(du.getDateTime()));
        goodstopicEntity.setContent(comment);
        goodstopicEntity.setUid(orderEntity.getOrUserId());

        UserEntity userEntity = userMapper.selectById(orderEntity.getOrUserId());
        goodstopicEntity.setUname(userEntity.getName());

        goodstopicMapper.insert(goodstopicEntity);//对商品进行评价
        orderEntity.setOrStatus(8);//修改状态为8 已评价
        orderMapper.update(orderEntity);

        GoodsEntity goodsEntity = goodsMapper.selectById(orderEntity.getOrGoodsId());//获取商品的积分
        long credit = goodsEntity.getCredit();
        long count = orderEntity.getOrCount();
        long personCredit = userEntity.getCredit();

        userEntity.setCredit(personCredit + credit * count);//增加用户积分
        userMapper.update(userEntity);
        return 1;
    }

    @GetMapping(value = "getAddressByUid")//得到地址
    public List<AddressEntity> getAddressByUid(@RequestParam("uid") long uid) {
        List<AddressEntity> list = addressMapper.selectByUid(uid);
        for (int i = 0; i < list.size(); i++) {
            String addressids = list.get(i).getAddress();
            AreaVO areavo = JSON.parseObject(addressids, AreaVO.class);
            AreaEntity areaEntity = areaMapper.select2One(areavo.getCountycode());
            if (areaEntity == null) {
                continue;
            }
            String str = areaEntity.getProvincename() + "-" + areaEntity.getCityname() + "-" + areaEntity.getCountyname();
            list.get(i).setAddress(str);
        }
        return list;
    }
    @GetMapping(value = "getAddressById")//得到地址
    public String getAddressById(@RequestParam("adid") long adid) {
        AddressEntity addressEntity=addressMapper.selectById(adid);


        AreaVO areavo = JSON.parseObject(addressEntity.getAddress(), AreaVO.class);
        AreaEntity areaEntity = areaMapper.select2One(areavo.getCountycode());
        String str = areaEntity.getProvincename() + "-" + areaEntity.getCityname() + "-" + areaEntity.getCountyname();


        return str+" "+addressEntity.getInfo();
    }
    @GetMapping(value = "delectAddresByid")//删除地址。（隐藏地址）
    public int delectAddresByid(@RequestParam("id") long id) {
        AddressEntity addressEntity = addressMapper.selectById(id);
        addressEntity.setStatus(1);
        addressMapper.update(addressEntity);
        return 0;
    }


    @GetMapping(value = "getAddresName")//将对应的json地址id转化为json中文地址
    public AreaEntity getAddresName(@RequestParam("addressids") String addressids) {
//        String addressids="{ \"province_code\":\"410000000000\" , \"city_code\":\"410100000000\"  , \"county_code\":\"410102000000\"  , \"town_code\":\"410102005000\"}";
        AreaVO areavo = JSON.parseObject(addressids, AreaVO.class);
        AreaEntity areaEntity = areaMapper.selectOne(areavo.getTowncode());
        return areaEntity;
    }


    @GetMapping(value = "getDateBystr")//日期转换成标准格式
    public String getDateBystr(@RequestParam("date") String date) {
        return getDateByStr2(date);
    }

    @PostMapping(value = "login")
    public UserEntity postLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
        //调用dao层
        UserEntity userEntity = userMapper.userLogin(username, password);
        return userEntity;
    }

    @PostMapping("login2")
    public GenericResponse login2(@RequestBody @Valid LoginForm loginForm, BindingResult bindingResult) {
        // 参数校验
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            return GenericResponse.ERROR.setMessage(list.get(0).getDefaultMessage());
        }
        return userBusiness.login(loginForm);
    }

    @PostMapping("register")
    public GenericResponse register(@RequestBody @Valid RegisterForm registerForm, BindingResult bindingResult) {
        // 参数校验
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            return GenericResponse.ERROR.setMessage(list.get(0).getDefaultMessage());
        }
        return userBusiness.register(registerForm);
    }


    @PostMapping("modify")
    public GenericResponse modify(@RequestBody @Valid UserInfoModifyForm modifyForm, BindingResult bindingResult) {
        // 参数校验
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            return GenericResponse.ERROR.setMessage(list.get(0).getDefaultMessage());
        }
        return userBusiness.modify(modifyForm);
    }

    @PostMapping("passwordModify")
    public GenericResponse passwordModify(@RequestBody @Valid UserPasswordModifyForm passwordModifyForm, BindingResult bindingResult) {
        // 参数校验
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            return GenericResponse.ERROR.setMessage(list.get(0).getDefaultMessage());
        }

        return userBusiness.passwordModify(passwordModifyForm);
    }


    @PostMapping("userAll")
    public GenericResponse userAll(@RequestBody UserSearch userSearch) {
        return userBusiness.userAll(userSearch);
    }

    @PostMapping("userInfo")
    public GenericResponse userInfo(@RequestBody UserInfoDTO userInfoDTO) {
        return userBusiness.userInfo(userInfoDTO);
    }

    /**
     * 获取积分记录（暂仅支持查询订单中获取的积分，积分业务复杂的话，需增加积分变动记录表）
     *
     * @param userId 用户编号
     * @return GenericResponse
     */
    @GetMapping("creditGet")
    public GenericResponse creditGet(@RequestParam("userId") Long userId) {

        return userBusiness.creditGet(userId);
    }

    /**
     * 积分消费记录（暂仅支持查询礼品中消费的积分，积分业务复杂的话，需增加积分变动记录表）
     *
     * @param userId 用户编号
     * @return GenericResponse
     */
    @GetMapping("creditConsume")
    public GenericResponse creditConsume(@RequestParam("userId") Long userId) {

        return userBusiness.creditConsume(userId);
    }

    /**
     * 修复用户积分
     */
    @GetMapping("fixCredit")
    public GenericResponse fixCredit(@RequestParam("userId") Long userId) {

        return userBusiness.fixCredit(userId);
    }

}
