package com.demo.utils;

import com.demo.mall.bean.MallSearchBean;

/**
 * Created by Administrator on 2016/8/15 0015.
 */
public class URL {
//    public static final String IP = "https://www.cfdzhsd.com/cfdScenic/";
    public static final String IP = "http://47.104.238.102:8080/cfdScenic/";
    //public static final String IP = "http://192.168.1.149/cfdScenic/";
    //public static final String IP = "http://59.110.61.137/cfdScenic/";
    //public static final String IP = "http://139.129.167.224:88/cfdScenic/";
    //public static final String IP = "http://139.129.167.238:88/cfdScenic/";

    //首页-轮播图
    public static final String carouselImgHome=IP +"consumerUser/carouselImg";
    //首页-首页2个广告位
    public static final String twoImg=IP+"consumerUser/adPositionId";
    //首页-推荐商品
    public static final String homeRecommend=IP+"consumerUser/recommendShop";
    //首页-热门景区
    public static final String homeHotSe=IP+"consumerUser/tagsVisitors";
    //首页-三个热门景区
    public static final String homeThree=IP+"consumerUser/indexVisitors";
    //获取验证码
    public static final String SendCode=IP +"consumerUser/checkCode";
    //注册
    public static final String Register=IP +"consumerUser/register";
    //登录
    public static final String SignIn=IP +"consumerUser/login";
    //修改密码
    public static final String ModifyPassword=IP +"consumerUser/editPsw";
    //找回密码
    public static final String RetrievePassword=IP +"consumerUser/findPsw";
    //意见反馈
    public static final String opinion=IP +"userOpinion/opinion";
    //个人中心-上传图片
    public static final String upload=IP +"img/upload";
    //个人中心-修改个人资料
    public static final String editDatum=IP +"consumerUser/editDatum";
    //个人中心-获取个人资料
    public static final String getDatum=IP +"consumerUser/getDatum";
    //收货地址列表
    public static final String addressList=IP +"userAddress/addressList";
    //添加收货地址
    public static final String receiptAddress=IP +"userAddress/receiptAddress";
    //编辑收货地址
    public static final String editAddress=IP +"userAddress/editAddress";
    //删除收货地址
    public static final String deleteAddress=IP +"userAddress/deleteAddress";
    //设为默认地址
    public static final String defaultAddress=IP +"userAddress/defaultAddress";
    //我的钱包--余额和交易记录
    public static final String myBalance=IP +"interFace/MyPurse/myBalance";
    //我的钱包--获取银行卡
    public static final String getBank=IP +"interFace/MyPurse/getBank";
    //我的钱包--判断有没有支付密码
    public static final String payPassWord=IP +"interFace/MyPurse/payPassWord";
    //我的钱包--添加银行卡
    public static final String saveBank=IP +"interFace/MyPurse/saveBank";
    //我的钱包--更新支付密码
    public static final String updatePayPassWord=IP +"interFace/MyPurse/updatePayPassWord";
    //我的钱包--重置支付密码
    public static final String resetPayPassWord=IP +"interFace/MyPurse/resetPayPassWord";
    //我的钱包--身份验证 - （忘记密码）
    public static final String authentication=IP +"interFace/MyPurse/authentication";
    //我的钱包--充值
    public static final String recharge=IP +"interFace/MyPurse/recharge";
    //我的钱包--提现
    public static final String withdraw=IP +"interFace/MyPurse/withdraw";
    //我的钱包--我的积分--积分兑换商品列表
    public static final String integralShopGoods=IP +"interFace/MyPurse/integralShopGoods";
    //我的钱包--我的积分--兑换商品详情页
    public static final String integralGoodsDetail=IP +"interFace/MyPurse/integralGoodsDetail";
    //我的钱包--我的积分--提交订单回显
    public static final String selectVisitorsOrder=IP +"interFace/MyPurse/selectVisitorsOrder";
    //我的钱包--我的积分--确认兑换
    public static final String saveIntegralOrder=IP +"interFace/MyPurse/saveIntegralOrder";
    //我的钱包--我的积分--确认支付
    public static final String confirmPayment=IP +"userAccountLog/confirmPayment";
    //我的钱包--我的积分--兑换交易记录
    public static final String myIntegral=IP +"interFace/MyPurse/myIntegral";
    //我的钱包--我的积分--兑换列表
    public static final String selectDealMessage=IP +"userAccountLog/selectDealMessage";
    //我的钱包--我的积分--兑换列表详情
    public static final String selectVisitorsOrderDetail=IP +"interFace/MyPurse/selectVisitorsOrderDetail";
    //我的钱包--我的积分--支付成功回显
    public static final String MyPurse=IP +"interFace/MyPurse/selectVisitorsOrderFinsh";
    //我的钱包 - 获取认证的手机号和真实姓名
    public static final String getConsumerInfo=IP +"interFace/MyPurse/getConsumerInfo";
    //我的钱包 - 提现 – 支付密码判断
    public static final String isPassWord=IP +"interFace/MyPurse/isPassWord";
    //我的 - 我的门票
    public static final String getMyTickets=IP +"interFace/myTickets/getMyTickets";
    //我的 - 我的门票 - 删除订单
    public static final String deleteMyTickets=IP +"interFace/myTickets/deleteMyTickets";
    //我的 - 我的门票 - 申请退款
    public static final String refund=IP +"interFace/myTickets/refund";
    //我的 - 我的门票 - 订单详情
    public static final String orderDetail=IP +"interFace/myTickets/orderDetail";
    //我的 - 我的门票 - 评论上传图片
    public static final String myTickets_upload=IP +"interFace/myTickets/upload";
    //我的 - 我的门票 - 评论
    public static final String evaluate=IP +"interFace/myTickets/evaluate";
    //我的 - 我的收藏 - 获取列表
    public static final String select=IP +"interFace/collect/select";
    //我的 - 我的收藏 - 删除订单
    public static final String collect_Delete=IP +"interFace/collect/delete";
    //我的 - 我的门票 - 角标
    public static final String getWaitPay=IP +"interFace/myTickets/getWaitPay";
    //我的 - 我的游乐圈 - 景区评价
    public static final String myComment=IP +"interFace/PlayCircle/myComment";
    //我的 - 我的订单--商品,酒店，饭店
    public static final String findOrder=IP +"interFace/orderDetail/findOrder";
    //我的 - 我的订单--申请退款
    public static final String backMoney=IP +"interFace/orderDetail/backMoney";
    //我的 - 我的订单--订单详情
    public static final String findOrderDetail=IP +"interFace/orderDetail/findOrderDetail";
    //我的 - 我的订单-- 删除我的订单
    public static final String deleteMyOrder=IP +"interFace/orderDetail/deleteMyTickets";
    //我的 - 我的订单-- 商品退款原因
    public static final String refundCauseInfo=IP +"interFace/orderDetail/refundCauseInfo";
    //我的 - 我的退款订单-- 列表
    public static final String refundOrder=IP +"interFace/orderDetail/refundOrder";
    //我的 - 我的退款订单-- 物流信息
    public static final String saveExpressOfUser=IP +"interFace/orderDetail/saveExpressOfUser";
    //我的 - 第三方登陆
    public static final String thirdLoginin=IP+"consumerUser/loginOther";
    //我的 - 关于我们
    public static final String getAboutUs=IP+"shopUser/getAboutUs";
    //我的 -消息中心
    public static final String messageCenter=IP+"shopUser/myMessage";
    //我的 -消息中心详情
    public static final String messageCenterDetail=IP+"shopUser/myMessageDetail";
    // 购物车列表
    public static final String getShopCartByUserId=IP +"interFace/eShop/getShopCartByUserId";
    //购物车 - 添加商品
    public static final String saveShopCart=IP +"interFace/eShop/saveShopCart";
    //购物车 - 角标
    public static final String getNumber=IP +"interFace/eShop/getNumber";
    //购物车 - 修改商品数量
    public static final String updateShopCart=IP +"interFace/eShop/updateShopCart";
    //购物车 - 删除购物车
    public static final String deleteShopCartById=IP +"interFace/eShop/deleteShopCartById";
    //购物车 - 移到收藏夹
    public static final String saveCollection=IP +"interFace/eShop/saveCollection";
    //购物车 - 订单回显
    public static final String findGoodsOrderById=IP +"interFace/eShop/findGoodsOrderById";


    //景区-首页轮播图
    public static final String carouselImg=IP +"advertisement/carouselImg";
    //景区-首页推进
    public static final String scenicSpotList=IP +"visitors/scenicSpotList";
    //景区-景点列表
    public static final String findVisitorsList=IP +"visitors/findVisitorsList";
    //景区-景点主页(主页)
    public static final String scenicSpotParticulars=IP +"visitors/scenicSpotParticulars";
    //景区-景点评论
    public static final String scenicSpotComment=IP +"visitors/scenicSpotComment";
    //景区-湿地简介 ---- 景区规划
    public static final String getPlanningOrIntroduce=IP +"visitors/getPlanningOrIntroduce";
    //景区-新闻须知列表
    public static final String pressList=IP +"newsNotice/pressList";
    //景区-新闻须知详情
    public static final String pressDetails=IP +"newsNotice/pressDetails";
    //景区-确认订单
    public static final String affirmOrder=IP +"visitorsOrder/affirmOrder";
    //景区-订单回显
    public static final String getVisitorsOrderById=IP +"visitorsOrder/getVisitorsOrderById";
    //景区-支付订单
    public static final String payOrder=IP +"visitorsOrder/payOrder";
    //景区-订单支付成功回显
    public static final String payOrderFinsh=IP +"visitorsOrder/payOrderFinsh";
    //景区-须知
    public static final String getVisitorsNotice=IP +"visitors/getVisitorsNotice";
    //景区-搜索
    public static final String obscureSelect=IP +"visitors/obscureSelect";
    //景区-查看图集
    public static final String findAtlasByVisitorsId=IP +"visitors/findAtlasByVisitorsId";
    //景区-周边
    public static final String getSurrounding=IP +"visitors/getSurrounding";
    //景区-推荐景点
    public static final String getScenceRecommend=IP+"way/getWay";
    //商城 - 商城轮播图
    public static final String findPic=IP +"interFace/eShop/findPic";
    //商城 - 为你推荐
    public static final String selectRecommend=IP +"interFace/eShop/selectRecommend";
    //商城 – 全部分类
    public static final String getType=IP +"interFace/eShop/getType";
    //商城 - 特产 - 分页查询
    public static final String findFeature=IP +"interFace/eShop/findFeatureTwo";
    //商城 - 小吃 - 分页查询
    public static final String findAllSnackTwo=IP +"interFace/eShop/findAllSnackTwo";
    //商城 – 全部分类 -店铺详细信息
    public static final String selectById=IP +"interFace/eShop/selectById";
    //商城 – 特产商品 - 查看商品详情--没登陆
    public static final String findDetailByGoodsId=IP +"interFace/eShop/findDetailByGoodsId";
    //商城 – 特产商品 - 查看商品详情--登录
    public static final String findDetailByGoodsIdAndUid=IP +"interFace/eShop/findDetailByGoodsIdAndUid";
    //商城 – 特产 – 商品分页查询
    public static final String findFeatureShop=IP +"interFace/eShop/findFeatureShopTwo";
    //商城 - 酒店饭店主页图
    public static final String getPhotoOfHomePage =IP +"interFace/eShop/getPhotoOfHomePage";
    //商城 – 特产 – 确认支付
    public static final String updatePayOrder=IP +"interFace/eShop/payOrder";
    //商城 - 酒店 - 分页查询酒店
    public static final String findHotelTwo=IP +"interFace/eShop/findHotelTwo";
    //商城 - 酒店 - 酒店详细信息
    public static final String findHotelDetail=IP +"interFace/eShop/findHotelDetail";
    //商城 - 酒店 - 创建订单
    public static final String saveHotelOrder=IP +"interFace/eShop/saveHotelOrder";
    //商城 - 酒店 - 余额支付
    public static final String balancePay=IP +"interFace/eShop/balancePay";
    //商城 - 酒店 - 支付完成后的回显
    public static final String payFinshShow=IP +"interFace/eShop/payFinshShow";
    //商城 - 饭店 - 查询所有的饭店
    public static final String findAllRestaurant=IP +"interFace/eShop/findAllRestaurantTwo";
    //商城 - 饭店 - 饭店详细信息
    public static final String findPackageByPackageId=IP +"interFace/eShop/findPackageByPackageId";
    //商城 - 饭店 - 确认订单
    public static final String saveRestaurantOrder=IP +"interFace/eShop/saveRestaurantOrder";
    //商城 - 饭店 -
    public static final String findAllRestaurantDetail=IP +"interFace/eShop/findAllRestaurantDetail";
    //商城 - 饭店 - 饭店单品确认订单订单回显
    public static final String showOrder=IP +"interFace/eShop/showOrder";
    //商城 -收藏
    public static final String save=IP +"interFace/collect/save";
    //商城 -取消收藏
    public static final String delete=IP +"interFace/collect/deleteByGoodsId";
    //商城 -购物车 - 创建订单
    public static final String createGoodsOrder=IP +"interFace/eShop/createGoodsOrder";
    //商城 -购物车 – 收货地址
    public static final String address=IP +"interFace/eShop/address";
    //商城 -搜索
    public static final String MALLSEARCH=IP+"interFace/eShop/linkSelect";
    //商品 -特产 小吃 -支付结果回显
    public static final String smallPayShow=IP+"interFace/eShop/findGoodsOrderById";
    //商城 -搜索热门
    public static final String mallSearchHot=IP+"interFace/eShop/getHotGoods";
    //商品 -特产 小吃 最后支付结果回调
    public static final String mallLastShow=IP+"interFace/eShop/payOrderFinsh";
    //商品 -购物车最后支付回显
    public static final String mallCarLastShow=IP+"interFace/eShop/findGoodsOrderById";
    //商品 -店铺内搜索
    public static final String shopSearch=IP+"interFace/eShop/searchGoodsByName";

    //游乐圈 -首页游记
    public static final String circle_wondertravel=IP+"interFace/PlayCircle/getTraveLogs";
    //游乐圈 - 精彩游记--视频攻略--必去清单（我的游乐圈公用）
    public static final String circleAllGrid=IP+"interFace/PlayCircle/getWonderful";
    //游乐圈- 景区活动列表 (活动和结伴游活动公用)   isTeam是否是结伴游活动（0否1是）
    public static final String circleScenicActivity=IP+"interFace/PlayCircle/getActivity";
    //游乐圈- 景区活动详情 (活动和结伴游活动公用)
    public static final String circleScenicMore=IP+"interFace/PlayCircle/getActivityDetail";
    //游乐圈- 分享
    public static final String circleShare=IP+"interFace/PlayCircle/saveShare";
    //游乐圈- 点赞和取消赞
    public static final String circleZan=IP+"interFace/PlayCircle/saveFavor";
    //游乐圈- 根据游记的id获取游记的评论
    public static final String circleComment=IP+"interFace/PlayCircle/getCommentById";
    //游乐圈- 写游记
    public static final String circleWrite=IP+"interFace/PlayCircle/writeCollect";
    //游乐圈- 写游记 获取游玩景点
    public static final String circleGetWrite=IP+"visitors/scenicSpotList";
    //游乐圈- 游记评论
    public static final String circleCommentOther=IP+"interFace/PlayCircle/collectComment";
    //微信支付url
    public static final String WXPay=IP+"interFace/WxPayController/getPrePayId";
    //游乐圈 -点击头像
    public static final String circleGetTravel=IP+"interFace/PlayCircle/getTravelsByUserId";
    //游乐圈 -点击头像-评论景区
    public static final String circleGetComment=IP+"interFace/PlayCircle/getCommentTravelsByUserId";
    //游乐圈 -分享点开后的连接
    public static final String circleSharePoint=IP+"share/toShare";
    //获取支付宝信息
    public static final String getAlipay=IP+"shopUser/getAlipayInfo";
    //监控
    public static final String realTime=IP+"monitorPoint/getAllMonitor";

    //监控
    public static final String videoList=IP+"monitorVideos/getCamerasEx";
    //我的游乐圈-删除
    public static final String deleteMycircle=IP+"interFace/PlayCircle/deleteMyTravelLog";
    //获取大华摄像头ip地址
    public  static final String getshexiangtouid=IP+"monitorPoint/getIP";
    //获取景点新
    public  static final String getwayInfo=IP+"way/getVisitorsInfo";
    //观鸟，湿地，招商
    public static final String threeInclude=IP+"/background/advertisingPage/getAdvertisingPageById";
    //活动景区3张图片
    public static final String threePic=IP+"advertisement/getCarouselImg";
}
