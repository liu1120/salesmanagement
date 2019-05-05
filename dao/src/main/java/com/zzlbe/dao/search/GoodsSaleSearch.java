package com.zzlbe.dao.search;

import com.zzlbe.dao.page.BasePageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class GoodsSaleSearch extends BasePageRequest {//用于goods、sale两表联查数据处理
    /**
     * 农化产品id
     */
    private Long id;
    /**
     * 农化产品名字
     */
    private String name;
    /**
     * 农化产品品牌
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
    private Integer point;

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




    /**
     * 销售类型：1正常（默认），2打折，3满减，4满送
     */
    private Integer type;
    /**
     * 打折相关：折扣
     */
    private BigDecimal discount;
    /**
     * 0默认全局sa_area_id不开放；1销售员申请，sa_area_id启用
     */
    private Integer start;
    /**
     * 满减相关：起价
     */
    private BigDecimal reach;
    /**
     * 满减相关：满减
     */
    private BigDecimal minus;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date overTime;
    /**
     * 开启活动区域（多个，中间用英文分号[`;`]隔开）
     */
    private String areaIds;
    /**
     * 开启活动区域(单个)
     */
    private String areaId;
    /**
     * 0默认同意，1不同意
     */
    private Boolean status;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;

}
