package com.zzlbe.web.controller;

import com.zzlbe.core.business.UserBusiness;
import com.zzlbe.core.common.GenericResponse;
import com.zzlbe.core.dto.UserInfoDTO;
import com.zzlbe.core.request.LoginForm;
import com.zzlbe.core.request.RegisterForm;
import com.zzlbe.core.request.UserInfoModifyForm;
import com.zzlbe.core.request.UserPasswordModifyForm;
import com.zzlbe.core.util.DateUtil;
import com.zzlbe.dao.entity.*;
import com.zzlbe.dao.mapper.*;
import com.zzlbe.dao.search.UserSearch;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static com.zzlbe.core.util.DateUtil.getDateByStr;

@RestController
@RequestMapping("user")
@CrossOrigin("http://localhost:8080")
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
        List<GoodsEntity> list = goodsMapper.selectGoodsByName(goodsname);
        return list;
    }

    @GetMapping(value = "getGoodsList")//查找全部商品
    public List<GoodsEntity> getGoodsList() {
        List<GoodsEntity> list = goodsMapper.selectAll();
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

    @GetMapping(value = "getGiftCreditById")//根据礼品id返回所需积分
    public long getGiftCreditById(@RequestParam("id") long id) {
        return giftMapper.selectById(id).getCredit();
    }
    @GetMapping(value = "getGoodsSort")//查找全部商品分类
    public List<GoodssortEntity> getGoodsSort() {
        List<GoodssortEntity> list = goodssortMapper.selectAll();
        return list;
    }

    /**
     *用户使用积分兑换产品只可兑换一份礼品
     * 用户的积分大于礼品积分，处理：减去用户积分，产生送礼品。
     * @param uid 用户id
     * @param adid 下单地址adid
     * @param id 礼品id
     * @return 0失败，1成功
     */
    @GetMapping(value = "getGift")
    public int getGift(@RequestParam("uid") long uid,@RequestParam("id") long id,@RequestParam("adid") long adid) {
        UserEntity userEntity=userMapper.selectById(uid);
        GiftEntity giftEntity=giftMapper.selectById(id);
        AddressEntity addressEntity=addressMapper.selectById(adid);
        if(userEntity.getCredit()<giftEntity.getCredit())return 0;
        userEntity.setCredit(userEntity.getCredit()-giftEntity.getCredit());//更新用户积分
        userMapper.update(userEntity);

        SentgiftEntity sentgiftEntity=new SentgiftEntity();
        sentgiftEntity.setToid(uid);//需要制定派送人
        sentgiftEntity.setTophone(userEntity.getPhone());
        sentgiftEntity.setAddress(adid+"");
        sentgiftEntity.setType(0);
        sentgiftEntity.setNum(1);
        sentgiftEntity.setCredit(giftEntity.getCredit());
        sentgiftEntity.setStatus(0);
        DateUtil du=new DateUtil();
        getDateByStr(du.getDateTime());
        return 1;
    }



    @GetMapping(value = "getGoodsBySort")//查找某一分类下的商品
    public List<GoodsEntity> getGoodsBySort(@RequestParam("sortid") long sortid) {
        List<GoodsEntity> list = goodsMapper.getGoodsBySort(sortid);
        return list;
    }


    @GetMapping(value = "getSaleGoods")//查找所有促销商品
    public List<GoodsEntity> getSaleGoods(@RequestParam("sortid") long sortid) {
        List<GoodsEntity> list = goodsMapper.getGoodsBySort(sortid);
        return list;
    }

    @GetMapping(value = "getSaleGoodsById")//通过商品id查找促销商品
    public SaleEntity getSaleGoodsById(@RequestParam("goodsid") long goodsid) {
        SaleEntity saleEntity = saleMapper.selectSaleById(goodsid);
        return saleEntity;
    }

    @GetMapping(value = "selectCreditById")//通过用户id查找积分
    public long selectCreditById(@RequestParam("uid") long uid) {
        return userMapper.selectById(uid).getCredit();
    }

    @GetMapping(value = "getOrderList")//查找用户下的订单
    public List<OrderEntity> getOrderList(@RequestParam("uid") long uid) {
        List<OrderEntity> list = orderMapper.selectByUid(uid);
        return list;
    }

    @GetMapping(value = "getOrder")//查找用户下的订单
    public OrderEntity getOrder(@RequestParam("id") long id) {
        OrderEntity orderEntity = orderMapper.selectById(id);
        return orderEntity;
    }

    @GetMapping(value = "getOrderStatus")//查看订单的状态
    public long getOrderStatus(@RequestParam("id") long id) {
        return orderMapper.selectById(id).getOrStatus();
    }

    @GetMapping(value = "addComments")//为已完成的订单添加评论。1成功，0失败.同时修改订单表状态，为已评价
    public int addComments(@RequestParam("comment") String comment,@RequestParam("orderid") long orderid) {
        OrderEntity orderEntity=orderMapper.selectById(orderid);
        if (orderEntity.getOrStatus()!=7)return 0;
        GoodstopicEntity goodstopicEntity=new GoodstopicEntity();
        goodstopicEntity.setGoodsid(orderEntity.getOrGoodsId());

        DateUtil du=new DateUtil();
        goodstopicEntity.setCreatetime(getDateByStr(du.getDateTime()));
        goodstopicEntity.setContent(comment);
        goodstopicEntity.setUid(orderEntity.getOrUserId());

        UserEntity userEntity=userMapper.selectById(orderEntity.getOrUserId());
        goodstopicEntity.setUname(userEntity.getName());

        goodstopicMapper.insert(goodstopicEntity);//对商品进行评价
        orderEntity.setOrStatus(8);//修改状态为8 已评价
        orderMapper.update(orderEntity);
        return 1;
    }

    @GetMapping(value = "getAddressByUid")//查看订单的状态
    public List<AddressEntity> getAddressByUid(@RequestParam("uid") long uid) {
        List<AddressEntity> list=addressMapper.selectByUid(uid);
        return list;
    }

    @GetMapping(value = "delectAddresByid")//删除地址。（隐藏地址）
    public int delectAddresByid(@RequestParam("id") long id) {
        AddressEntity addressEntity=addressMapper.selectById(id);
        addressEntity.setStatus(1);
        addressMapper.update(addressEntity);
        return 0;
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


}
