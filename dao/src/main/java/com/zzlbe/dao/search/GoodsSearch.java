package com.zzlbe.dao.search;

import com.zzlbe.dao.page.BasePageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Data
public class GoodsSearch extends BasePageRequest {

    /**
     * 农化产品id
     */
    private Long id;
    /**
     * 农化产品名字
     */
    private String name;
    /**
     * 农化产品分类
     */
    private String sort;
    /**
     * 农化产品型号
     */
    private String version;
    /**
     * 农化产品价格
     */
    private BigDecimal price;
    /**
     * 农化产品底价
     */
    private BigDecimal minPrice;
    /**
     * 农化产品介绍、说明、图片
     */
    private String introduce;
    /**
     * 农化产品图片存放路径
     */
    private String newImgPath;
    /**
     * 农化产品点击量
     */
    private String point;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 农化产品是否展示（默认1展示,0不展示）
     */
    private Integer isShow;
    /**
     * 农化产品库存
     */
    private Integer num;
    /**
     * 农化产品积分
     */
    private Integer credit;



}
