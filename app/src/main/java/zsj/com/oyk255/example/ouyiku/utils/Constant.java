package zsj.com.oyk255.example.ouyiku.utils;

public class Constant {

	public static final class URL{
		public static final String HomeSeverStoreURL=
				"http://a.ouyiku.com/?c=Index&a=merchant_top";
		public static final String HomeBannerURL=
				"http://a.ouyiku.com/?c=Index&a=pic_carousel";
		public static final String HomeTUijianURL=
				"http://a.ouyiku.com/?c=Index&a=product_top";
		
		
		public static final String BrandDetailURL=
				"http://a.ouyiku.com/?c=Index&a=branddetail";
		public static final String BrandUnloginBrandURL=
				"http://a.ouyiku.com/?c=Index&a=newpro&brand_id=";// 
		public static final String BrandAllBrandURL=
				"http://a.ouyiku.com/?c=Index&a=allpro&brand_id=";//  
		
		
		public static final String GroupbannerURL=
				"http://a.ouyiku.com/?c=Index&a=fen_carousel";
		public static final String GroupFirstURL=
				"http://a.ouyiku.com/?c=Index&a=catlist";
		public static final String GroupSecondURL=
				"http://a.ouyiku.com/?c=Index&a=scatlist";
		public static final String GroupSecondTopURL=
				"http://a.ouyiku.com/?c=Index&a=fpic_carousel";
		
		public static final String GroupBrandURL=
				"http://a.ouyiku.com/?c=Index&a=fbrand";
		public static final String GroupMeiBrandURL=
				"http://a.ouyiku.com/?c=Index&a=cbrand";
		public static final String GroupTuijianURL=
				"http://a.ouyiku.com/?c=Index&a=cproduct_top";
		public static final String GroupLastURL=
				"http://a.ouyiku.com/?m=home&c=Index&a=classify";
		
		
		public static final String DetailBannerURL=
				"http://a.ouyiku.com/?c=Index&a=pro_url";
		public static final String DetailDataURL=
				"http://a.ouyiku.com/?c=index&a=topimg&product_id=";
		public static final String DetailUnloginBrandURL=
				"http://a.ouyiku.com/?c=Index&a=brand&product_id=";
//		public static final String DetailloginBrandURL=
//				"http://a.ouyiku.com/?c=Index&a=brand&product_id=";//  后面加上&user_id=
		public static final String DetailReportURL=
				"http://a.ouyiku.com/?c=Index&a=report";
		public static final String DetailBrandCollectURL=
				"http://a.ouyiku.com/?c=Index&a=collect";
		public static final String DetailFootURL=
				"http://a.ouyiku.com/?c=index&a=footprint";
		
		public static final String DetailSKUURL=
				"http://a.ouyiku.com/?c=Index&a=productattr";//商品属性
		
		public static final String DetailNetredUrl=
				"http://a.ouyiku.com/?c=huodong2&a=baokuandetail";//网红爆款
		public static final String NetredShare = "http://a.ouyiku.com/?c=Share&a=appshare&type=8";//网红分享
		public static final String NineShare = "http://a.ouyiku.com/?c=Share&a=appshare&type=7";//9.9分享
		public static final String DetailGraphicURL=
				"http://a.ouyiku.com/?c=Index&a=img_str&product_id=";
		
		
		public static final String 	RegisSendYZMURL="http://a.ouyiku.com/?c=User&a=send&mobile=";//获取验证码
		public static final String 	RegisVerifyURL="http://a.ouyiku.com/?c=User&a=o_r&mobile=";//验证验证码  15201023139&code=850729&invitation=
		public static final String 	RegisSetPassURL="http://a.ouyiku.com/?c=User&a=setP";//验证验证码  15201023139&code=850729&invitation=
		
		public static final String 	ForgetSendYZMURL="http://a.ouyiku.com/?c=User&a=getcodew";//获取验证码
		public static final String 	ForgetVerifyURL="http://a.ouyiku.com/?c=User&a=checkcode";//验证验证码  15201023139&code=850729&invitation=
	
		
		public static final String 	LoginURL="http://a.ouyiku.com/?c=User&a=login";
		
		public static final String 	PersonInfoURL="http://a.ouyiku.com/?c=Userinfo&a=userz";//个人信息get
		public static final String 	PersonInfoChangeURL="http://a.ouyiku.com/?c=Userinfo&a=saveinfo";//修改个人信息get
		
		public static final String 	ChangePhoneYZMURL="http://a.ouyiku.com/?c=Userinfo&a=fcode";//修改绑定手机验证码get
		public static final String 	ChangePhoneVerifyURL="http://a.ouyiku.com/?c=Userinfo&a=bmobile";//确认更改绑定手机get
		
		public static final String 	ChangePassYZMURL="http://a.ouyiku.com/?c=Userinfo&a=xcode";//修改密码发送验证码get
		
		public static final String 	MyOrderURL="http://a.ouyiku.com/?m=home&c=Userorder&a=orderlist";//我的订单
		public static final String 	OrderDetailURL="http://a.ouyiku.com/?m=home&c=Userorder&a=orderinfo";//订单详情
		public static final String 	DeleteOrderURL="http://a.ouyiku.com/?m=home&c=Userorder&a=deleteorder";//删除订单
		public static final String 	CheckDeliveryURL="http://a.ouyiku.com/?m=home&c=Userorder&a=getorder";//确认收货
		
		public static final String 	InviteURL="http://a.ouyiku.com/?c=Userinfo&a=invest";//邀请
		public static final String 	InviteRecordURL="http://a.ouyiku.com/?c=Userinfo&a=inj";//邀请记录
		
		public static final String 	AddAddressURL="http://a.ouyiku.com/?c=Userinfo&a=addressa";//增加/编辑地址
		public static final String 	GetAreaURL="http://a.ouyiku.com/?c=Userinfo&a=addressi";//获取全国省区市
		public static final String 	GetAddressURL="http://a.ouyiku.com/?c=Userinfo&a=address";//获取收货地址列表
		public static final String 	DeleteAddressURL="http://a.ouyiku.com/?c=Userinfo&a=deleteaddress";//删除地址
		
		public static final String 	OpinionURL="http://a.ouyiku.com/?c=Userinfo&a=opinion";//意见反馈
		
		public static final String 	CollectBrandURL="http://a.ouyiku.com/?c=Index&a=cob";//收藏品牌
		public static final String 	CollectBabyURL="http://a.ouyiku.com/?c=Index&a=cop";//收藏商品
		public static final String 	DeleteCollectURL="http://a.ouyiku.com/?c=Userinfo&a=codel";//删除收藏
		
		public static final String 	FootprintURL="http://a.ouyiku.com/?c=PersonInfo&a=zujilist";//足迹
		public static final String 	DeleteFootprintURL="http://a.ouyiku.com/?c=PersonInfo&a=zujidelete";//清空足迹
		
		public static final String 	WuLiuListURL="http://a.ouyiku.com/?m=home&c=Userorder&a=wuliulist";//物流列表
		public static final String 	WuLiuDetailURL="http://ouyiku.com/kuaidi100/postorder.php";//物流详情
		
		public static final String 	WalletTopURL="http://a.ouyiku.com/?c=Userinfo&a=mpagei";//我的钱包头布局
		
		
		
		public static final String 	FashionManBannerURL="http://a.ouyiku.com/?m=home&c=Talent&a=pic_carousel";//红人馆banner post
		public static final String 	FashionManFIVEURL="http://a.ouyiku.com/?m=home&c=Talent&a=tp_classify";//红人馆5个入口 post
		public static final String 	FashionManHOTNETURL="http://a.ouyiku.com/?m=home&c=Talent&a=listhottalent";//红人馆热门网红水平listview get
		public static final String 	FashionManATTENTIONURL="http://a.ouyiku.com/?m=home&c=Talent&a=t_follow";//红人馆热门网红关注 get
		public static final String 	FashionManHotPictureURL="http://a.ouyiku.com/?m=home&c=Talent&a=hottalentclassify";//红人馆热门美图 get
		
		public static final String 	HotNetRedDetailURL="http://a.ouyiku.com/?m=home&c=Talent&a=t_userinfo";//热门网红详情页 get
		public static final String 	HotPictureDetailURL="http://a.ouyiku.com/?m=home&c=Talent&a=t_hiscontent";//热门美图详情页 get
		public static final String 	HotPictureCommentlURL="http://a.ouyiku.com/?m=home&c=Talent&a=t_comment";//热门美图详情页评论 get
		public static final String 	HotPictureSendCommentlURL="http://a.ouyiku.com/?m=home&c=Talent&a=addmessage";//热门美图详情页发布评论 get
		public static final String 	HotPictureZANlURL="http://a.ouyiku.com/?m=home&c=Talent&a=t_goodclick";//热门美图点赞 get
		
		public static final String 	TodayNewsURL="http://a.ouyiku.com/?c=Tomorrow&a=tlist";//今日新品
		public static final String 	NewsTopviewURL="http://a.ouyiku.com/?c=Tomorrow&a=pic_carousel";//今日新品轮播图
		
		public static final String 	DrandNewsURL="http://a.ouyiku.com/?c=Index&a=allpro";//品牌新品
		public static final String 	DrandNewsTimeURL="http://a.ouyiku.com/?c=Tomorrow&a=gettime";//品牌新品时间
		public static final String 	BrandTommorrowURL="http://a.ouyiku.com/?c=Tomorrow&a=tplist";//明日列表


		public static final String 	NineListURL="http://a.ouyiku.com/?c=huodong2&a=jiubuylist&type=7";//9.9元列表
		public static final String 	NineDetailURL="http://a.ouyiku.com/?c=huodong2&a=getdetail";//9.9元详情页
		public static final String 	NineBannerURL="http://a.ouyiku.com/?c=huodong2&a=getbannerimg&type=1";//9.9元轮播
		public static final String 	NineBuyURL="http://a.ouyiku.com/?c=huodong1&a=buy";//9.9元购买

		public static final String NetRedURL="http://a.ouyiku.com/?c=huodong2&a=wanghonglist&type=6";//网红爆款

		public static final String 	HotSaleURL="http://a.ouyiku.com/?c=Index&a=hotbest1";//热卖精选
		
		public static final String 	SeaShopURL="http://a.ouyiku.com/?c=haiwai&a=hlist";//海外淘
		public static final String 	SeaShopBannerURL="http://a.ouyiku.com/?c=haiwai&a=pic_carousel";//海外淘轮播
		
		public static final String 	SearchURL="http://a.ouyiku.com/?c=Index&a=product_search2";//搜索
		
		public static final String 	HorSaleBannerURL="http://a.ouyiku.com/?m=home&c=Banner&a=banner_pic";//热卖轮播
		
		public static final String 	MessageURL="http://a.ouyiku.com/?c=PersonInfo&a=messagelist";//个人中心消息
		public static final String 	UpLoadHeadImg="http://a.ouyiku.com/?c=PersonInfo&a=updloadIcon";//头像
		
		public static final String 	CreatHotPeople="http://a.ouyiku.com/?m=home&c=Talent&a=addtalent";//创建红人馆
		
		public static final String 	SetPayPassUrl="http://a.ouyiku.com/?c=userinfo&a=spaypassword";//提现密码
		public static final String 	SetPayPassVerifyYZMUrl="http://a.ouyiku.com/?c=Userinfo&a=xpassword";//提现密码验证验证码
		
		public static final String 	SetNewsLoginUrl="http://a.ouyiku.com/?c=Userinfo&a=spassword";//设置新登录密码
		
		public static final String 	BinkListUrl="http://a.ouyiku.com/?c=userinfo&a=getbanklist";//银行列表
		public static final String 	BindBankUrl="http://a.ouyiku.com/?c=userinfo&a=setbank";//绑定银行卡
		public static final String 	GetBankInfoUrl="http://a.ouyiku.com/?c=userinfo&a=getbank";//银行卡数据
		public static final String 	UserBankInfoUrl="http://a.ouyiku.com/?c=userinfo&a=banklist";//用户银行卡信息
		public static final String 	DeleteBankUrl="http://a.ouyiku.com/?c=userinfo&a=deletebank";//删除银行卡
		public static final String 	TixianUrl="http://a.ouyiku.com/?c=pay&a=tixian";//提现
		public static final String 	JiaoYiJiluUrl="http://a.ouyiku.com/?c=userinfo&a=jiaoyilist";//交易记录
		
		public static final String 	ATTENTListUrl="http://a.ouyiku.com/?m=home&c=Talent&a=t_hisfollowlist";//红人库关注列表
		public static final String 	FANSListUrl="http://a.ouyiku.com/?m=home&c=Talent&a=t_hisfanslist";//红人库粉丝列表
		
		public static final String 	EnterShopCaeUrl="http://a.ouyiku.com/?c=Order&a=carta";//加入购物车
		public static final String 	ShopCarListUrl="http://a.ouyiku.com/?c=Order&a=cartl";//购物车列表
		
		public static final String 	ShopCarNumUrl="http://a.ouyiku.com/?c=Order&a=cartnum";//更改购物车数量
		public static final String 	DeleteShopCarUrl="http://a.ouyiku.com/?c=Order&a=cartd";//删除购物车
		
		public static final String 	ConfirmOrderListUrl="http://a.ouyiku.com/?c=Order&a=getcart";//确认订单列表
		public static final String 	MoRenAddressUrl="http://a.ouyiku.com/?c=Order&a=getaddress";//默认收货地址
		public static final String 	SubmitOrderUrl="http://a.ouyiku.com/?c=Order&a=setorder";//确认订单
		public static final String 	AliPayARGUrl="http://a.ouyiku.com/?c=pay&a=getalipay1";//获取支付宝参数
		
		public static final String 	PaySuccrssAddressUrl="http://a.ouyiku.com/?c=pay&a=getaddress";//支付成功地址
		public static final String 	WeChatUrl="http://a.ouyiku.com/?c=wxpay&a=index";//微信支付
		public static final String 	WalletPayUrl="http://a.ouyiku.com/?c=pay&a=paywithsite";//钱包支付
		
		public static final String 	HomeBrandUrl="http://a.ouyiku.com/?c=Index&a=brand_top";//首页品牌推荐
		
		public static final String 	TimeUrl="http://a.ouyiku.com/?c=huodong1&a=qianggoutime";//限时秒杀时间表
		public static final String 	TimeListUrl="http://a.ouyiku.com/?c=huodong1&a=qiangoulist";//限时秒杀列表
		
		public static final String 	TimeDetailUrl="http://a.ouyiku.com/?c=huodong1&a=getdetail";//限时秒杀详情
		
		public static final String 	TopTabslUrl="http://a.ouyiku.com/?c=hot&a=hotcats";//榜单分类
		public static final String 	TopListlUrl="http://a.ouyiku.com/?c=hot&a=hotlist";//榜单列表
		
		public static final String 	HomeThreePicUrl="http://a.ouyiku.com/?c=index&a=activeImg1";//首页三张图片
		
		public static final String 	IsSingUrl="http://a.ouyiku.com/?c=sign&a=issign";//是否签到
		public static final String 	SignInUrl="http://a.ouyiku.com/?c=sign&a=signin";//签到
		public static final String 	SignCouponUrl="http://a.ouyiku.com/?c=sign&a=signget";//签到奖励
		public static final String 	GetSignCouponUrl="http://a.ouyiku.com/?c=sign&a=signgetcheck";//领取优惠券
		
		public static final String 	UpDataUrl="http://a.ouyiku.com/?c=index&a=Androidv";//版本更新
		public static final String 	RedBagListUrl="http://a.ouyiku.com/?c=Coupon&a=hongbaolist";//商家红包列表
		public static final String 	GetHongBaoUrl="http://a.ouyiku.com/?c=Coupon&a=gethongbao";//领取红包
		public static final String 	UserRedBagListUrl="http://a.ouyiku.com/?c=Coupon&a=userlist";//用户红包
		public static final String 	PinTuanBannerUrl="http://a.ouyiku.com/?c=Pintuan&a=pic_carousel";//拼团banner
		public static final String 	PinTuanListUrl="http://a.ouyiku.com/?c=Pintuan&a=plist";//拼团列表

		public static final String 	PinTuanDetailUrl="http://a.ouyiku.com/?c=Pintuan&a=detail";//拼团详情
		public static final String 	PinTuanGoodsCollUrl="http://a.ouyiku.com/?c=Pintuan&a=addcollect";//拼团收藏
		
		public static final String 	PinTuanSingleBuyUrl="http://a.ouyiku.com/?c=Pintuan&a=checkalone";//拼团单独购买确认订单
		public static final String 	PinTuanSubMitUrl="http://a.ouyiku.com/?c=Pintuan&a=buyalone";//拼团单独单独提交订单
		public static final String 	PinTuanSubMitTuanUrl="http://a.ouyiku.com/?c=Pintuan&a=buytuan1";//拼团开团提交订单
		
		public static final String 	CanTuanDetailUrl="http://a.ouyiku.com/?c=Pintuan&a=ptinfo";//参团详情
		public static final String 	MyPinTuanUrl="http://a.ouyiku.com/?c=Pintuan&a=userptlist";//我的拼团
		public static final String  PinTuanTuijianUrl="http://a.ouyiku.com/?c=Pintuan&a=golist";//拼团推荐
		
		public static final String  SaleAfterListUrl="http://a.ouyiku.com/?c=userorder&a=refunlist";//售后列表
		public static final String  ApplySaleAfterUrl="http://a.ouyiku.com/?m=home&c=Userorder&a=refun";//申请售后
		public static final String  NOApplySaleAfterUrl="http://a.ouyiku.com/?c=userorder&a=refunnot";//取消售后
		
		public static final String  HomeHuoDongUrl="http://a.ouyiku.com/?c=envelop";//首页活动
		public static final String  SaleAfterDetailUrl="http://a.ouyiku.com/?c=userorder&a=refuninfo";//售后详情
		public static final String  ShareSuccessUrl="http://a.ouyiku.com/?c=envelop&a=getenvelop";//分享成功录入数据库
		public static final String  SalePhoneUrl="http://a.ouyiku.com/?c=userorder&a=serviceInfo";//联系售后
		public static final String  LuRuKuaiDIUrl="http://a.ouyiku.com/?c=userorder&a=refundsetexpress";//录入快递
		
		public static final String  CheckShareUrl="http://a.ouyiku.com/?c=envelop&a=checkshare";//活动分享资格认证
		public static final String  DetailWebViewUrl="http://a.ouyiku.com/?c=index&a=productdetail&product_id=";//普通商品图文详情webview
		public static final String  PinSearchUrl="http://a.ouyiku.com/?c=Pintuan&a=search_p";//搜索团
		public static final String  NotableUrl="http://a.ouyiku.com/?c=Pintuan&a=successdName";//拼团成员列表
		
		
		
	}
	
	public static final class INTENT
	{
		
		public static final int LOGIN_RESULT = 1;//登录成功返回的
		public static final int REGIS_RESULT = 2;//注册成功返回的
		public static final int PERSON_DATA_RESULT = 3;//个人资料返回的
		public static final int ADDRESS_REQUEST = 7;//增加地址
		public static final int ADDRESS_REQUEST2 = 8;//编辑地址
		
		public static final int FASHION_ZHUBO = 13;//红人馆主播
		public static final int FASHION_MOTE = 9;//红人馆模特
		public static final int FASHION_MINGXING = 10;//红人馆明星
		public static final int FASHION_XUESHENG = 11;//红人馆学生
		public static final int FASHION_LAMA = 12;//红人馆主辣妈
		
		//获取跟中资料头像
		public static final int PHOTO_REQUEST_CAMERA = 4;// 拍照
		public static final int PHOTO_REQUEST_GALLERY = 5;// 从相册中选择
		public static final int PHOTO_REQUEST_CUT = 6;// 结果
		
		public static final int WECHATRESULT = 14;// 微信支付结果
	}
	
	
	
	
	
	
	public static final class WebUrl{
		public static final String 	ChangjianURL="http://ouyiku.com/pact/about_question.html";
		public static final String 	LevelURL="http://ouyiku.com/pact/level.html";
		public static final String 	About_usURL="http://ouyiku.com/pact/about_us.html";
		public static final String 	TryURL="http://ouyiku.com/pact/product_trials.html";
		public static final String 	SaleServiceURL="http://ouyiku.com/pact/post_sales_service.html";
		public static final String 	ShenQingURL="http://ouyiku.com/pact/withdraw_deposit.html";
		public static final String 	UserXieyiURL="http://ouyiku.com/pact/read_terms_of_use.html";
		public static final String  TixianXieyiURL="http://ouyiku.com/pact/withdraw_deposit.html";
	}
	public static final class APPID{
		public static final String 	WXAPPID="wx246edc55db723ecb";
		public static final String 	QQAPPID="1105451394";
		
	}
	
	/** 自定义相册 */
	public static final class PHOTO {
		/** 引用自定义相册---图片可选择的数量 */
		public static final String INTNET_COUNT = "图片可选择的数量";
		/** 选择图片类型 */
		public static final String TYPE = "选择图片类型";
		/** 选择多张图片 */
		public static final String MULTIPLE = "选择多张图片";
		/** 选择单张图片 */
		public static final String SINGLE = "选择单张图片";
		/** 相册获取图片 */
		public static final int RESULT_PHOTO_IMAGE = 111;
		/** 相机拍照获取图片 */
		public static final int RESULT_CAMERA_IMAGE = 222;
		/** 选择图片 */
		public static final String ADDPIC = "添加图片";
		/** 自定义文件名字 */
		public static final String FILE_NAME = "wjk";
	}
}
 