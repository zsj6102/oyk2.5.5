package zsj.com.oyk255.example.ouyiku.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.VolleyError;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.brandjson.BrandInfo;
import zsj.com.oyk255.example.ouyiku.collectjson.IntoShopCar;
import zsj.com.oyk255.example.ouyiku.collectjson.IntoShopCarData;
import zsj.com.oyk255.example.ouyiku.detail.popwindow.Detail_popwindow;
import zsj.com.oyk255.example.ouyiku.detail.popwindow.Share_pop;
import zsj.com.oyk255.example.ouyiku.detailjson.Data;
import zsj.com.oyk255.example.ouyiku.detailjson.DataNet;
import zsj.com.oyk255.example.ouyiku.detailjson.Datainfo;
import zsj.com.oyk255.example.ouyiku.detailjson.DetaiBrandData;
import zsj.com.oyk255.example.ouyiku.detailjson.DetailGraphic2Datum;
import zsj.com.oyk255.example.ouyiku.detailjson.IfSuccess;
import zsj.com.oyk255.example.ouyiku.detailjson.NetredDetail;
import zsj.com.oyk255.example.ouyiku.detailjson.Status;
import zsj.com.oyk255.example.ouyiku.detailskujson.Attrlist;
import zsj.com.oyk255.example.ouyiku.detailskujson.Datum;
import zsj.com.oyk255.example.ouyiku.detailskujson.DetailSKU;
import zsj.com.oyk255.example.ouyiku.detailskujson.Stock;
import zsj.com.oyk255.example.ouyiku.fragment.DetailGoodsinfoFragment;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.PhotoUtil;
import zsj.com.oyk255.example.ouyiku.utils.ScreenUtils;
import zsj.com.oyk255.example.ouyiku.utils.ToastUtils;
import zsj.com.oyk255.example.ouyiku.view.AspectRatioImageView;
import zsj.com.oyk255.example.ouyiku.view.CustomViewPager;
import zsj.com.oyk255.example.ouyiku.view.MyGridView;
import zsj.com.oyk255.example.ouyiku.view.ScrollViewWithListView;
import zsj.com.oyk255.example.ouyiku.view.SingleSelectCheckBoxs;
import zsj.com.oyk255.example.ouyiku.view.ViewpageIndicator;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

import com.google.gson.Gson;

import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.open.utils.ThreadManager;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.umeng.message.PushAgent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NetredDetailActivity extends FragmentActivity implements OnClickListener {
    InputMethodManager im;
    int[] mImgRes = new int[]{R.mipmap.logo, R.mipmap.logo, R.mipmap.logo, R.mipmap.logo};
    private String product_id;//详情页id
    ArrayList<String> mBannerData = new ArrayList<String>();
    ArrayList<String> mGraphicData = new ArrayList<String>();
    ArrayList<DetailGraphic2Datum> graphicData = new ArrayList<DetailGraphic2Datum>();

    ArrayList<Datum> mListData = new ArrayList<Datum>();
    HashMap<Integer, List<Attrlist>> map = new HashMap<Integer, List<Attrlist>>();//尺码颜色列表，里面有尺码，颜色 还有图片id，根据不同listview的position保存起来

    ArrayList<Stock> mStock = new ArrayList<Stock>();//库存列表 里面的use_id是要上传服务器
    private ArrayList<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();//存放属性字
    private ArrayList<Map<Integer, String>> list2 = new ArrayList<Map<Integer, String>>();//存放属性id
    private ArrayList<Map<Integer, String>> list3 = new ArrayList<Map<Integer, String>>();//存放属性图片地址
    private String fpro_id;//网红图文详情传的这个id
    private ScrollViewWithListView mListView;
    private CustomViewPager mLunBoPager;
    private ViewpageIndicator viewpageIndicator;
    private ImageView mDatail_brandlogo;
    private ImageView mDatail_brandColl;
    private TextView mDatail_brandname;
    private TextView mDatail_brandnum;
    private TextView mDatail_brandcollNum;
    private TextView mDatail_brandpers;
    private TextView mDetail_name;
    private TextView mDetail_newprice;
    private TextView old_price;
    private TextView mDetail_report;
    private SharedPreferences sp;
    private String userid;//用户id
    private String token;//用户token
    private String brandId;
    private ImageView mBaby_Coll;
    private TextView mBaby_text;
    private MyGridView mGridView;
    private ImageView mPopLogo;
    private TextView mPopTitle;
    private TextView mPopPrice;
    private TextView mPopKuCun;
    private Data data;

    private int mLastItme = -1;
    private Detail_popwindow popwindow;
    private StringBuffer stringBuffer;
    String[] aaa;
    private Map<Integer, String> mStockIdData;
    private Map<Integer, String> mUserIDData;
    private IWXAPI api;

    private static final String APP_ID = Constant.APPID.WXAPPID;
    private static final String APP_QQID = Constant.APPID.QQAPPID;
    private String share_url;
    private String share_title;
    private String curprice;
    private String marketPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        api = WXAPIFactory.createWXAPI(this, APP_ID, false);
        api.registerApp(APP_ID);

        mTencent = Tencent.createInstance(APP_QQID, this.getApplicationContext());

        PushAgent.getInstance(this).onAppStart();
        im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (savedInstanceState == null) {
            overridePendingTransition(R.anim.push_from_right, R.anim.push_to_left);
        }
        sp = getSharedPreferences("userdata", 0);
        userid = sp.getString("userid", "");
        token = sp.getString("token", "");
        Intent intent = getIntent();
        product_id = intent.getStringExtra("product_id");
        screenWidth = ScreenUtils.getScreenWidth(this);
        initUI();
        initFoot();
//        initGoodsSKU();

    }

    @Override
    protected void onStart() {
        sp = getSharedPreferences("userdata", 0);
        userid = sp.getString("userid", "");
        token = sp.getString("token", "");
        super.onStart();
    }

    //获取商品属性
    private void initGoodsSKU() {
        final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
        progressHUD.setMessage("加载中");
        progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
        progressHUD.show();
        String url = Constant.URL.DetailSKUURL + "&product_id=" + fpro_id;
        HTTPUtils.get(this, url, new VolleyListener() {

            @Override
            public void onResponse(String arg0) {
                progressHUD.dismiss();
                Gson gson = new Gson();
                DetailSKU fromJson = gson.fromJson(arg0, DetailSKU.class);
                zsj.com.oyk255.example.ouyiku.detailskujson.Status status = fromJson.getStatus();
                String succeed = status.getSucceed();
                if (succeed.equals("1")) {
                    List<Datum> listdata = fromJson.getData();//list.size
                    mListData.clear();
                    mListData.addAll(listdata);

                    List<Stock> stock = fromJson.getStock();//点击购买/购物车 返回use_id  需作判断
                    for (int i = 0; i < stock.size(); i++) {
                        mStockIdData.put(i, stock.get(i).getSkunatureId());

                        mUserIDData.put(i, stock.get(i).getUseId());
                    }
                    aaa = new String[mListData.size()];

                    for (int i = 0; i < mListData.size(); i++) {
                        List<Attrlist> attrlist = mListData.get(i).getAttrlist();

                        HashMap<Integer, String> mAnameData = new HashMap<Integer, String>();
                        HashMap<Integer, String> mSkunatureIdData = new HashMap<Integer, String>();
                        HashMap<Integer, String> mImgUrldData = new HashMap<Integer, String>();
                        for (int j = 0; j < attrlist.size(); j++) {
                            mAnameData.put(j, attrlist.get(j).getAname());
                            mSkunatureIdData.put(j, attrlist.get(j).getSkunatureId());
                            mImgUrldData.put(j, attrlist.get(j).getImgUrl());
                        }
                        list.add(i, mAnameData);
                        list2.add(i, mSkunatureIdData);
                        list3.add(i, mImgUrldData);
                    }


                }

            }

            @Override
            public void onErrorResponse(VolleyError arg0) {
                progressHUD.dismiss();
            }
        });

    }

    //	足迹
    private void initFoot() {
        String FootUrl = Constant.URL.DetailFootURL + "&product_id=" + product_id + "&user_id=" + userid;
        HTTPUtils.get(this, FootUrl, new VolleyListener() {

            @Override
            public void onResponse(String arg0) {
            }

            @Override
            public void onErrorResponse(VolleyError arg0) {

            }
        });

    }

    private void initBrand(String brandTitle, String brandtotal, String collectnum, String brandsalenum, String logo, String isbcollect) {

        mDatail_brandname.setText(brandTitle);
        mDatail_brandnum.setText(brandtotal);
        mDatail_brandcollNum.setText(collectnum);
//					mDatail_brandpers.setText(data.getPers());
        mDatail_brandpers.setText(brandsalenum);
        UILUtils.displayImageNoAnim(logo, mDatail_brandlogo);
        initCollectBrand();


    }


    private void initUI() {

        popwindow = new Detail_popwindow(this);
        stringBuffer = new StringBuffer();

        findViewById(R.id.detail_back).setOnClickListener(this);
        findViewById(R.id.detail_bttom_intoshopcar).setOnClickListener(this);
        findViewById(R.id.baby_collectview).setOnClickListener(this);//收藏宝贝
        mBaby_Coll = (ImageView) findViewById(R.id.baby_collect);
        mBaby_text = (TextView) findViewById(R.id.detail_bttom_collect);
//				//详情页轮播图
//				View topView = getLayoutInflater().inflate(R.layout.detail_topview, null);
        mLunBoPager = (CustomViewPager) findViewById(R.id.detail_lunbo);
        viewpageIndicator = (ViewpageIndicator) findViewById(R.id.viewpageIndicator2);

        mDetail_name = (TextView) findViewById(R.id.detai_name);
        mDetail_tshare = (ImageView) findViewById(R.id.mTopView_share);
        mDetail_tshare.setOnClickListener(this);

        mDetail_newprice = (TextView) findViewById(R.id.detail_newprice);
        old_price = (TextView) findViewById(R.id.detail_oldprice);
        old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        mDetail_report = (TextView) findViewById(R.id.detail_report);
        mDetail_report.setOnClickListener(this);

        mDatail_brandlogo = (ImageView) findViewById(R.id.detail_brand_img);
        mDatail_brandColl = (ImageView) findViewById(R.id.detail_collect);
        findViewById(R.id.detail_collectview).setOnClickListener(this);


        mDatail_brandname = (TextView) findViewById(R.id.detail_brandname);
        mDatail_brandnum = (TextView) findViewById(R.id.detail_goodsnum);
        mDatail_brandcollNum = (TextView) findViewById(R.id.detail_colletesnum);
        mDatail_brandpers = (TextView) findViewById(R.id.detail_haoping);
        findViewById(R.id.detail_tobrand).setOnClickListener(this);

        mTitleShare = (TextView) findViewById(R.id.detail_share);
        mTitleShare.setOnClickListener(this);

        findViewById(R.id.detail_bttom_store).setOnClickListener(this);
        mWebView = (WebView) findViewById(R.id.detailwebview);

        mStockIdData = new HashMap<Integer, String>();
        mUserIDData = new HashMap<Integer, String>();
        findViewById(R.id.detail_bttom_share).setOnClickListener(this);
        share_pop = new Share_pop(this);

        findViewById(R.id.detail_bttom_buy).setOnClickListener(this);

    }

    class ViewPageAdapter extends FragmentPagerAdapter {

        public ViewPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            int index = position % mBannerData.size();
            if (mBannerData.size() == 0) {
                DetailGoodsinfoFragment bannerItemFragment = new DetailGoodsinfoFragment(index, mImgRes[index]);
                return bannerItemFragment;
            } else {
                String bannerDatum = mBannerData.get(index);
                DetailGoodsinfoFragment bannerItemFragment2 = new DetailGoodsinfoFragment(index, bannerDatum);
                return bannerItemFragment2;
            }
        }

        @Override
        public int getCount() {
            if (mBannerData.size() == 0) {
                return mImgRes.length;
            } else {
                return mBannerData.size();
            }
        }

    }

    private String sharePicUrl;

    private void initData() {
        final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
        progressHUD.setMessage("加载中");
        progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
        progressHUD.show();
        //详情页数据
        String DetaiBannerUrl = Constant.URL.DetailNetredUrl;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("product_id", product_id);
        HTTPUtils.post(this, DetaiBannerUrl, map, new VolleyListener() {


            @Override
            public void onResponse(String arg0) {
                progressHUD.dismiss();
                Gson gson = new Gson();
                NetredDetail fromJson = gson.fromJson(arg0, NetredDetail.class);
                Status status = fromJson.getStatus();
                String succeed = status.getSucceed();
                DataNet data = fromJson.getData();

//                DataNet data = fromJson.getData();
//                String  time
                if (succeed.equals("1")) {
                    String pr = data.getProductId();
                    String ff = data.getFpro_id();
                    fpro_id = ff;
                    brandId = data.getBrand_id();
                    share_url = data.getShare_url();
                    share_title = data.getTitle();
                    mDetail_name.setText(share_title);
                    curprice = data.getCurrprice();
                    marketPrice = data.getMarketprice();
                    old_price.setText("￥" + marketPrice);
                    mDetail_newprice.setText("￥" + curprice);
                    //图文详情
                    initGraphic(ff);
                    String brandTitle = data.getBrandtitle();
                    String brandtotal = data.getBrandtotal();
                    String collectnum = data.getCollectnum();
                    String brandsalenum = data.getBrandsalenum();
                    String logo = data.getLogo();
                    String isbcollect = data.getIsbcollect();

                    initBrand(brandTitle, brandtotal, collectnum, brandsalenum, logo, isbcollect);
                    List<Datum> listdata = data.getSkudata();//list.size
                    mListData.clear();
                    mListData.addAll(listdata);

                    List<Stock> stock = data.getAttr();//点击购买/购物车 返回use_id  需作判断
                    for (int i = 0; i < stock.size(); i++) {
                        mStockIdData.put(i, stock.get(i).getSkunatureId());

                        mUserIDData.put(i, stock.get(i).getUseId());
                    }
                    aaa = new String[mListData.size()];

                    for (int i = 0; i < mListData.size(); i++) {
                        List<Attrlist> attrlist = mListData.get(i).getAttrlist();

                        HashMap<Integer, String> mAnameData = new HashMap<Integer, String>();
                        HashMap<Integer, String> mSkunatureIdData = new HashMap<Integer, String>();
                        HashMap<Integer, String> mImgUrldData = new HashMap<Integer, String>();
                        for (int j = 0; j < attrlist.size(); j++) {
                            mAnameData.put(j, attrlist.get(j).getAname());
                            mSkunatureIdData.put(j, attrlist.get(j).getSkunatureId());
                            mImgUrldData.put(j, attrlist.get(j).getImgUrl());
                        }
                        list.add(i, mAnameData);
                        list2.add(i, mSkunatureIdData);
                        list3.add(i, mImgUrldData);
                    }
                    List<String> ss = data.getProduct_image();
                    mBannerData.clear();
                    mBannerData.addAll(ss);
                    sharePicUrl = mBannerData.get(0);
//                    List<NetredDetail> data = fromJson.getData();

//					//子线程执行获取分享要用的图片
//					new Thread(new Runnable(){
//
//						@Override
//			            public void run() {
//			            	thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap(sharePicUrl), 120, 120, true);
//			            }
//			        }).start();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    mLunBoPager.setOnPageChangeListener(new ViewPageChangedListener());
                    mLunBoPager.setAdapter(new ViewPageAdapter(fragmentManager));
                }

            }

            @Override
            public void onErrorResponse(VolleyError arg0) {
                progressHUD.dismiss();
            }
        });


    }

    private void initCollectBrand() {

        String BrandUrl = Constant.URL.BrandDetailURL;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("brand_id", brandId);
        //用户id
        params.put("user_id", userid);
        HTTPUtils.post(NetredDetailActivity.this, BrandUrl, params, new VolleyListener() {

            @Override
            public void onResponse(String arg0) {
                Gson gson = new Gson();
                BrandInfo fromJson = gson.fromJson(arg0, BrandInfo.class);
                zsj.com.oyk255.example.ouyiku.brandjson.Status status = fromJson.getStatus();
                String succeed = status.getSucceed();
                if (succeed.equals("1")) {
                    zsj.com.oyk255.example.ouyiku.brandjson.Data data = fromJson.getData();
                    String isCollect = data.getIsCollect();
                    if (isCollect.equals("1")) {
                        mDatail_brandColl.setImageResource(R.mipmap.collect_click);
                    } else {
                        mDatail_brandColl.setImageResource(R.mipmap.collect);

                    }

                } else if (succeed.equals("0")) {
                    ToastUtils.toast(NetredDetailActivity.this, "暂无数据");
                }
            }

            @Override
            public void onErrorResponse(VolleyError arg0) {

//							Toast.makeText(Brand_detailActivity.this, "网络异常", Toast.LENGTH_SHORT).show();

            }
        });
    }

    //图文详情
    private void initGraphic(String ff) {
        final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
        progressHUD.setMessage("加载中");
        progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
        progressHUD.show();
        String DetailGraphicUrl = Constant.URL.DetailWebViewUrl + ff;
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.loadUrl(DetailGraphicUrl);

        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
//	               if (newProgress == 100) {
//	            	   mBar.setVisibility(View.GONE);
//	               } else {
//	                   if (View.INVISIBLE == mBar.getVisibility()) {
//	                	   mBar.setVisibility(View.VISIBLE);
//	                   }
//	                   mBar.setProgress(newProgress);
//	               }
                super.onProgressChanged(view, newProgress);
            }

        });
    }

    //收藏品牌
    private void ClickCollect() {
        final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
        progressHUD.setMessage("加载中");
        progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
        progressHUD.show();
        sp = getSharedPreferences("userdata", 0);
        userid = sp.getString("userid", "");
        String BrandCollectUrl = Constant.URL.DetailBrandCollectURL + "&user_id=" + userid + "&brand_id=" + brandId;
        HTTPUtils.get(this, BrandCollectUrl, new VolleyListener() {

            @Override
            public void onResponse(String arg0) {

                progressHUD.dismiss();
                Gson gson = new Gson();
                IfSuccess fromJson = gson.fromJson(arg0, IfSuccess.class);
                Status status = fromJson.getStatus();
                String succeed = status.getSucceed();
                if (succeed.equals("1")) {
                    initCollectBrand();
                } else {

                    startActivity(new Intent(NetredDetailActivity.this, LoginActivity.class));
                }

            }

            @Override
            public void onErrorResponse(VolleyError arg0) {
                progressHUD.dismiss();
            }
        });
    }

    //收藏商品
    private void ClickCollectBaby() {
        Toast.makeText(NetredDetailActivity.this, "特殊活动不支持收藏", Toast.LENGTH_SHORT).show();
    }

    class ViewPageChangedListener implements OnPageChangeListener {


        @Override
        public void onPageScrolled(int position, float arg1, int arg2) {
            if (mBannerData.size() == 0) {
                position %= 4;
                viewpageIndicator.move(position, arg1, 4);
            } else {
                position %= mBannerData.size();
                viewpageIndicator.move(position, arg1, mBannerData.size());
            }
        }

        @Override
        public void onPageSelected(int arg0) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

    }


    @Override
    public void finish() {
        hideSoftInput();
        super.finish();
        overridePendingTransition(R.anim.push_from_left, R.anim.push_to_right);
    }

    protected void hideSoftInput() {
        View view = getCurrentFocus();
        if (view != null) {
            IBinder binder = view.getWindowToken();
            if (binder != null) {
                im.hideSoftInputFromWindow(binder, 0);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_back:
                finish();
                break;
            case R.id.detail_bttom_intoshopcar:

                if (userid.equals("")) {
                    startActivity(new Intent(NetredDetailActivity.this, LoginActivity.class));

                } else {

                    popwindow.showAtLocation(findViewById(R.id.detail_layout), Gravity.CENTER, 0, 0);
                    mListView = (ScrollViewWithListView) popwindow.view.findViewById(R.id.detail_pop_listview);

                    mPopLogo = (ImageView) popwindow.view.findViewById(R.id.popwindow_img);
                    mPopTitle = (TextView) popwindow.view.findViewById(R.id.popwindow_goodsinfo);
                    mPopPrice = (TextView) popwindow.view.findViewById(R.id.popwindow_price);
                    mPopKuCun = (TextView) popwindow.view.findViewById(R.id.popwindow_inventory);
//
                    if (mListData != null) {
//
                        mListView.setAdapter(new MYLIST(list, list2, list3));
                    }

                    mPopTitle.setText(share_title);
                    mPopPrice.setText("￥" + curprice);
                    mPopDelete = (ImageView) popwindow.view.findViewById(R.id.delete_num);
                    mPopAdd = (ImageView) popwindow.view.findViewById(R.id.add_num);
                    mPopNum = (TextView) popwindow.view.findViewById(R.id.num);
                    TextView mPopSure = (TextView) popwindow.view.findViewById(R.id.pop_buyorshopcar);
                    mPopSure.setText("加入购物车");

                    mPopDelete.setTag("+");
                    mPopAdd.setTag("-");
                    mPopDelete.setOnClickListener(new OnButtonClickListener());
                    mPopAdd.setOnClickListener(new OnButtonClickListener());


                    //加入购物车
                    mPopSure.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (aaa.length > 0) {
                                for (int i = 0; i < aaa.length; i++) {
                                    if (aaa[i] == null || aaa[i].equals("null,") || aaa[i].equals("null")) {
                                        Toast.makeText(NetredDetailActivity.this, "请选择正确的属性", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }

                            }

                            StringBuffer a = new StringBuffer();
                            for (int i = 0; i < aaa.length; i++) {
                                a.append(aaa[i]);
                            }
                            for (int i = 0; i < mStockIdData.size(); i++) {

                                if (a.toString().equals(mStockIdData.get(i))) {

                                    final ZProgressHUD progressHUD = ZProgressHUD.getInstance(NetredDetailActivity.this);
                                    progressHUD.setMessage("加载中");
                                    progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
                                    progressHUD.show();
                                    String use_id = mUserIDData.get(i);
                                    String num = mPopNum.getText().toString();
                                    String url = Constant.URL.EnterShopCaeUrl;
                                    HashMap<String, String> map = new HashMap<String, String>();
                                    map.put("product_id", product_id);
                                    map.put("number", num);//购买数量
                                    map.put("use_id", use_id);
                                    map.put("user_id", userid);
                                    map.put("token", token);
                                    Log.e("use_id", use_id);
                                    HTTPUtils.post(NetredDetailActivity.this, url, map, new VolleyListener() {

                                        @Override
                                        public void onResponse(String arg0) {
                                            progressHUD.dismiss();
                                            Gson gson = new Gson();
                                            IntoShopCar fromJson = gson.fromJson(arg0, IntoShopCar.class);
                                            zsj.com.oyk255.example.ouyiku.collectjson.Status status = fromJson.getStatus();
                                            if (status.getSucceed().equals("1")) {
                                                IntoShopCarData data = fromJson.getData();
                                                String cartId = data.getCartId();
                                                Toast.makeText(NetredDetailActivity.this, "加入购物车成功", Toast.LENGTH_SHORT).show();
                                                popwindow.dismiss();

                                            } else {
                                                Toast.makeText(NetredDetailActivity.this, "加入购物车失败", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onErrorResponse(VolleyError arg0) {
                                            progressHUD.dismiss();
                                        }
                                    });
                                }
                            }


                        }
                    });

                }


                break;
            case R.id.detail_report:

                report();
                break;
            case R.id.detail_collectview:
                ClickCollect();
                break;
            case R.id.baby_collectview:
                ClickCollectBaby();
                break;
            case R.id.detail_tobrand:
                //跳转品牌
                Intent intent = new Intent(this, Brand_detailActivity.class);
                intent.putExtra("mSevenShop1", brandId);
                startActivity(intent);
                break;
            case R.id.detail_bttom_store:
                //跳转品牌
                Intent intent2 = new Intent(this, Brand_detailActivity.class);
                intent2.putExtra("mSevenShop1", brandId);
                startActivity(intent2);
                break;
            case R.id.detail_bttom_buy:
                //立即购买
                if (userid.equals("")) {
                    startActivity(new Intent(NetredDetailActivity.this, LoginActivity.class));

                } else {

                    popwindow.showAtLocation(findViewById(R.id.detail_layout), Gravity.CENTER, 0, 0);
                    mListView = (ScrollViewWithListView) popwindow.view.findViewById(R.id.detail_pop_listview);

                    mPopLogo = (ImageView) popwindow.view.findViewById(R.id.popwindow_img);
                    mPopTitle = (TextView) popwindow.view.findViewById(R.id.popwindow_goodsinfo);
                    mPopPrice = (TextView) popwindow.view.findViewById(R.id.popwindow_price);
                    mPopKuCun = (TextView) popwindow.view.findViewById(R.id.popwindow_inventory);
//
                    if (mListData != null) {
//
                        mListView.setAdapter(new MYLIST(list, list2, list3));
                    }


                    mPopTitle.setText(share_title);
                    mPopPrice.setText("￥" + curprice);


                    mPopDelete = (ImageView) popwindow.view.findViewById(R.id.delete_num);
                    mPopAdd = (ImageView) popwindow.view.findViewById(R.id.add_num);
                    mPopNum = (TextView) popwindow.view.findViewById(R.id.num);
                    TextView mPopSure2 = (TextView) popwindow.view.findViewById(R.id.pop_buyorshopcar);
                    mPopSure2.setText("立即购买");

                    mPopDelete.setTag("+");
                    mPopAdd.setTag("-");
                    mPopDelete.setOnClickListener(new OnButtonClickListener());
                    mPopAdd.setOnClickListener(new OnButtonClickListener());

                    //立即购买
                    mPopSure2.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (aaa.length > 0) {
                                for (int i = 0; i < aaa.length; i++) {
                                    if (aaa[i] == null || aaa[i].equals("null,") || aaa[i].equals("null")) {
                                        Toast.makeText(NetredDetailActivity.this, "请选择正确的属性", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }

                            }

                            StringBuffer a = new StringBuffer();
                            for (int i = 0; i < aaa.length; i++) {
                                a.append(aaa[i]);
                            }
                            for (int i = 0; i < mStockIdData.size(); i++) {

                                if (a.toString().equals(mStockIdData.get(i))) {
                                    final ZProgressHUD progressHUD = ZProgressHUD.getInstance(NetredDetailActivity.this);
                                    progressHUD.setMessage("加载中");
                                    progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
                                    progressHUD.show();
                                    String use_id = mUserIDData.get(i);
                                    String num = mPopNum.getText().toString();
                                    String url = Constant.URL.EnterShopCaeUrl;
                                    HashMap<String, String> map = new HashMap<String, String>();
                                    map.put("product_id", fpro_id);
                                    map.put("number", num);//购买数量
                                    map.put("use_id", use_id);
                                    map.put("user_id", userid);
                                    map.put("token", token);
                                    Log.e("use_id", use_id);
                                    HTTPUtils.post(NetredDetailActivity.this, url, map, new VolleyListener() {

                                        @Override
                                        public void onResponse(String arg0) {
                                            progressHUD.dismiss();
                                            Gson gson = new Gson();
                                            IntoShopCar fromJson = gson.fromJson(arg0, IntoShopCar.class);
                                            zsj.com.oyk255.example.ouyiku.collectjson.Status status = fromJson.getStatus();
                                            if (status.getSucceed().equals("1")) {
                                                IntoShopCarData data = fromJson.getData();
                                                String cartId = data.getCartId();
                                                Intent intent3 = new Intent(NetredDetailActivity.this, ConfirmOrderActivity.class);
                                                intent3.putExtra("cart_id", cartId);
                                                startActivity(intent3);
//											Toast.makeText(DetailActivity.this, "加入购物车成功", Toast.LENGTH_SHORT).show();
                                                popwindow.dismiss();

                                            } else {
                                                Toast.makeText(NetredDetailActivity.this, "购买失败", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onErrorResponse(VolleyError arg0) {
                                            progressHUD.dismiss();
                                        }
                                    });
                                }
                            }


                        }
                    });


                }


                break;
            case R.id.detail_bttom_share:
                //分享
                share_pop.showAtLocation(findViewById(R.id.detail_layout), Gravity.CENTER, 0, 0);
                share_pop.view.findViewById(R.id.iv_share_wx3).setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                Bitmap thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap(sharePicUrl), 120, 120, true);
                                api.registerApp(APP_ID);
                                api.sendReq(createReq(false, thumb));
                            }
                        }).start();


                    }
                });

                share_pop.view.findViewById(R.id.iv_share_pyq3).setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                Bitmap thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap(sharePicUrl), 120, 120, true);
                                api.registerApp(APP_ID);
                                api.sendReq(createReq(true, thumb));
                            }
                        }).start();


                    }
                });
                share_pop.view.findViewById(R.id.iv_share_qq).setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        share();

                    }
                });

                share_pop.view.findViewById(R.id.iv_share_qqzong).setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        ShareQQZone();

                    }
                });
                break;
            case R.id.detail_share:
                //分享
                share_pop.showAtLocation(findViewById(R.id.detail_layout), Gravity.CENTER, 0, 0);
                share_pop.view.findViewById(R.id.iv_share_wx3).setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                Bitmap thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap(sharePicUrl), 120, 120, true);
                                api.registerApp(APP_ID);
                                api.sendReq(createReq(false, thumb));
                            }
                        }).start();

                    }
                });

                share_pop.view.findViewById(R.id.iv_share_pyq3).setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                Bitmap thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap(sharePicUrl), 120, 120, true);
                                api.registerApp(APP_ID);
                                api.sendReq(createReq(true, thumb));
                            }
                        }).start();

                    }
                });
                share_pop.view.findViewById(R.id.iv_share_qq).setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        share();

                    }
                });

                share_pop.view.findViewById(R.id.iv_share_qqzong).setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        ShareQQZone();

                    }
                });
                break;
            case R.id.mTopView_share:
                //分享
                share_pop.showAtLocation(findViewById(R.id.detail_layout), Gravity.CENTER, 0, 0);
                share_pop.view.findViewById(R.id.iv_share_wx3).setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                Bitmap thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap(sharePicUrl), 120, 120, true);
                                api.registerApp(APP_ID);
                                api.sendReq(createReq(false, thumb));
                            }
                        }).start();

                    }
                });

                share_pop.view.findViewById(R.id.iv_share_pyq3).setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                Bitmap thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap(sharePicUrl), 120, 120, true);
                                api.registerApp(APP_ID);
                                api.sendReq(createReq(true, thumb));
                            }
                        }).start();

                    }
                });
                share_pop.view.findViewById(R.id.iv_share_qq).setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        share();

                    }
                });

                share_pop.view.findViewById(R.id.iv_share_qqzong).setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        ShareQQZone();

                    }
                });
                break;

            default:
                break;
        }

    }

    public SendMessageToWX.Req createReq(boolean timeLine, Bitmap thumb) {
//        String ArticleUrl = "http://m.ouyiku.com/?c=good&a=info&id=" + product_id;
//        String title2 = data.getTitle();

        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = share_url;
        final WXMediaMessage msg = new WXMediaMessage(webpage);
//		String title = title2;
//		msg.description = title2;
        msg.title = share_title;
//		Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.tubiao3);
        //子线程执行获取分享要用的图片
//		new Thread(new Runnable(){
//
//			@Override
//            public void run() {
//            	thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap(sharePicUrl), 120, 120, true);
        msg.thumbData = PhotoUtil.bmpToByteArray(thumb, true);
//            }
//        }).start();

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
//		req.scene = SendMessageToWX.Req.WXSceneSession;
        req.scene = timeLine ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        return req;
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }


    //举报
    private void report() {
        String reportUrl = Constant.URL.DetailReportURL + "&product_id=" + product_id;
        HTTPUtils.get(this, reportUrl, new VolleyListener() {

            @Override
            public void onResponse(String arg0) {

                Toast.makeText(NetredDetailActivity.this, "举报成功", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onErrorResponse(VolleyError arg0) {

            }
        });
    }

    //分享QQ
    public void share() {
        Bundle bundle = new Bundle();
        //这条分享消息被好友点击后的跳转URL。
        bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, share_url);
        //分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_	SUMMARY不能全为空，最少必须有一个是有值的。
        bundle.putString(QQShare.SHARE_TO_QQ_TITLE, share_title);
        //分享的图片URL
        bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, mBannerData.get(0));
        //分享的消息摘要，最长50个字
//	bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, "测试");
        //手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
//	bundle.putString(Constants.PARAM_APPNAME, "??我在测试");
        //标识该消息的来源应用，值为应用名称+AppId。
//	bundle.putString(Constants.PARAM_APP_SOURCE, "星期几" + AppId);

        mTencent.shareToQQ(this, bundle, qqShareListener);
    }

    //	 private int shareType = QQShare.SHARE_TO_QQ_TYPE_DEFAULT;
    private int shareType = QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT;

    //分享QQ空间
    public void ShareQQZone() {
        Bundle params = new Bundle();
//		 shareType = QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT;
//
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, shareType);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, share_title);
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, share_url);
        //分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_	SUMMARY不能全为空，最少必须有一个是有值的。
//			bundle.putString(QzoneShare.SHARE_TO_QQ_TITLE, data.getTitle());
//			//分享的图片URL

        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, mBannerData);
        doShareToQzone(params);

    }

    IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onCancel() {
//            if (shareType != QQShare.SHARE_TO_QQ_TYPE_IMAGE) {
//                Util.toastMessage(DetailActivity.this, "onCancel: ");
//                Toast.makeText(DetailActivity.this, "onCancel:", Toast.LENGTH_SHORT).show();
//            }
        }

        @Override
        public void onComplete(Object response) {
            // TODO Auto-generated method stub
//            Util.toastMessage(DetailActivity.this, "onComplete: " + response.toString());
//            Toast.makeText(DetailActivity.this, "onComplete: " + response.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(UiError e) {
            // TODO Auto-generated method stub
//            Util.toastMessage(DetailActivity.this, "onError: " + e.errorMessage, "e");
//            Toast.makeText(DetailActivity.this, "onError: " + e.errorMessage, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, qqShareListener);
    }

    private void doShareToQzone(final Bundle params) {
        // QZone分享要在主线程做
        ThreadManager.getMainHandler().post(new Runnable() {

            @Override
            public void run() {
                if (null != mTencent) {
                    mTencent.shareToQzone(NetredDetailActivity.this, params, qqShareListener);
                }
            }
        });
    }

    class MYLIST extends BaseAdapter {
        //TODO 图片暂时还没做切换
        private ArrayList<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();//名字
        private ArrayList<Map<Integer, String>> list2 = new ArrayList<Map<Integer, String>>();//id
        private ArrayList<Map<Integer, String>> list3 = new ArrayList<Map<Integer, String>>();//图片地址

        public MYLIST(ArrayList<Map<Integer, String>> list, ArrayList<Map<Integer, String>> list2, ArrayList<Map<Integer, String>> list3) {
            this.list = list;
            this.list2 = list2;
            this.list3 = list3;
        }

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(final int pos, View convertView, ViewGroup parent) {

            View view = getLayoutInflater().inflate(R.layout.detail_pop_list_item, null);
            final Datum datum = mListData.get(pos);


            final TextView mTitle = (TextView) view.findViewById(R.id.tv_title);
            final SingleSelectCheckBoxs sscb = (SingleSelectCheckBoxs) view.findViewById(R.id.sscb);
            String name = datum.getName();
            mTitle.setText(name);
            if (mBannerData != null) {
                UILUtils.displayImageNoAnim(mBannerData.get(0), mPopLogo);
            }

            sscb.setOnSelectListener(new SingleSelectCheckBoxs.OnSelectListener() {
                @Override
                public void onSelect(int position) {

                    if (pos == mListData.size() - 1) {
                        aaa[pos] = (list2.get(pos).get(position));
                    } else {
                        aaa[pos] = (list2.get(pos).get(position)) + ",";
                    }

                }
            });
            sscb.setData(list.get(pos));


            return view;
        }

    }


    class GraphicAdaptr extends BaseAdapter {

        @Override
        public int getCount() {
            return graphicData.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View layout = null;
            viewHolder holder = null;
            if (convertView == null) {
                layout = getLayoutInflater().inflate(R.layout.graphic_item, null);
                holder = new viewHolder();
                holder.mGraphic_img = (AspectRatioImageView) layout.findViewById(R.id.graphic_img);
                layout.setTag(holder);
            } else {
                layout = convertView;
                holder = (viewHolder) layout.getTag();
            }
            DetailGraphic2Datum detailGraphic2Datum = graphicData.get(position);
            String url = detailGraphic2Datum.getUrl();
//				String width = detailGraphic2Datum.getWidth();
//				String height = detailGraphic2Datum.getHeight();
//				int width_int = Integer.valueOf(width);
//				Integer heigth_int = Integer.valueOf(height);
//				holder.mGraphic_img.getLayoutParams().width=width_int;
////				float he=heigth_int/width_int;
////				float gao=he*screenWidth;
//				holder.mGraphic_img.getLayoutParams().height=heigth_int;
            UILUtils.displayImageNoAnim(url, holder.mGraphic_img);

            return layout;

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    class viewHolder {
        AspectRatioImageView mGraphic_img;
    }

    int num = 1;
    private ImageView mPopDelete;
    private ImageView mPopAdd;
    private TextView mPopNum;
    private int screenWidth;
    private Share_pop share_pop;
    private Tencent mTencent;
    private TextView mTitleShare;
    private ImageView mDetail_tshare;
    private WebView mWebView;

    //加减判断
    class OnButtonClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            String numString = mPopNum.getText().toString();
            if (numString == null || numString.equals("")) {
                num = 0;
                mPopNum.setText("0");
            } else {
                if (v.getTag().equals("-")) {
                    if (++num <= 0)  //先加，再判断
                    {
                        num--;
//						Toast.makeText(DetailActivity.this, "请输入一个大于0的数字",
//								Toast.LENGTH_SHORT).show();
                    } else {
                        mPopNum.setText(String.valueOf(num));
                    }
                } else if (v.getTag().equals("+")) {
                    if (--num <= 0)  //先减，再判断
                    {
                        num++;
//						Toast.makeText(DetailActivity.this, "请输入一个大于0的数字",
//								Toast.LENGTH_SHORT).show();
                    } else {
                        mPopNum.setText(String.valueOf(num));

                    }
                }
            }
        }
    }


}
