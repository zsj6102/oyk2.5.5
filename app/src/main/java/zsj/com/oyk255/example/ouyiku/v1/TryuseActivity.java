package zsj.com.oyk255.example.ouyiku.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.volley.VolleyError;

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

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import zsj.com.oyk255.R;
import zsj.com.oyk255.example.ouyiku.detail.popwindow.Share_pop;
import zsj.com.oyk255.example.ouyiku.detailjson.DataNetshare;
import zsj.com.oyk255.example.ouyiku.detailjson.NetShare;
import zsj.com.oyk255.example.ouyiku.detailjson.Status;
import zsj.com.oyk255.example.ouyiku.detailjson.ZeroList;
import zsj.com.oyk255.example.ouyiku.detailjson.ZeroListDatum;
import zsj.com.oyk255.example.ouyiku.pullableview.PullToRefreshLayout;
import zsj.com.oyk255.example.ouyiku.utils.Constant;
import zsj.com.oyk255.example.ouyiku.utils.PhotoUtil;
import zsj.com.oyk255.example.ouyiku.view.ZProgressHUD;
import zsj.com.oyk255.suiyuchen.HTTPUtils;
import zsj.com.oyk255.suiyuchen.UILUtils;
import zsj.com.oyk255.suiyuchen.VolleyListener;

public class TryuseActivity extends OykActivity implements OnClickListener {

    //网红爆款
    ArrayList<ZeroListDatum> mListData = new ArrayList<ZeroListDatum>();
    private ListView mListView;
    private ZeroAdapter zeroAdapter;
    private ImageView mNull;
    private Share_pop share_pop;
    private Tencent mTencent;
    private IWXAPI api;
    private static final String APP_ID = Constant.APPID.WXAPPID;
    private static final String APP_QQID = Constant.APPID.QQAPPID;
    private String sharePicUrl;
    private String share_url;
    private String share_title;
    private String content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tryuse);
        PushAgent.getInstance(this).onAppStart();
        api = WXAPIFactory.createWXAPI(this, APP_ID, false);
        api.registerApp(APP_ID);

        mTencent = Tencent.createInstance(APP_QQID, this.getApplicationContext());
        ((PullToRefreshLayout) findViewById(R.id.refresh_view1))
                .setOnRefreshListener(new MyListener());
        initList();
        initShare();
        initUI();

    }
    private void initShare(){
        final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
        progressHUD.setMessage("加载中");
        progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
        progressHUD.show();
        String netshareUrl = Constant.URL.NetredShare;
        HTTPUtils.get(this, netshareUrl, new VolleyListener() {

            @Override
            public void onResponse(String arg0) {
                progressHUD.dismiss();
                Gson gson = new Gson();
                DataNetshare fromJson = gson.fromJson(arg0, DataNetshare.class);
                NetShare  data = fromJson.getNetShare();
                Status status = fromJson.getStatus();
                String succeed = status.getSucceed();
                if (succeed.equals("1")) {

                     sharePicUrl = data.getShare_img();
                     share_url=data.getShare_url();
                      share_title=data.getShare_title();
                      content =data.getContent();
//                    String url = data.getShare_url();

                }
            }

            @Override
            public void onErrorResponse(VolleyError arg0) {
                progressHUD.dismiss();
            }
        });
    }
    private void initList() {
        final ZProgressHUD progressHUD = ZProgressHUD.getInstance(this);
        progressHUD.setMessage("加载中");
        progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
        progressHUD.show();
        String url = Constant.URL.NetRedURL;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", "1");
        map.put("num", "0");
        HTTPUtils.post(this, url, map, new VolleyListener() {


            @Override
            public void onResponse(String arg0) {
                progressHUD.dismiss();
                Gson gson = new Gson();
                ZeroList fromJson = gson.fromJson(arg0, ZeroList.class);
                Status status = fromJson.getStatus();
                String succeed = status.getSucceed();
                if (succeed.equals("1")) {
                    List<ZeroListDatum> data = fromJson.getData();
                    mListData.addAll(data);

                    if (mListData.size() == 0) {
                        mNull.setVisibility(View.VISIBLE);
                    } else {
                        mNull.setVisibility(View.GONE);
                        zeroAdapter = new ZeroAdapter();
                        mListView.setAdapter(zeroAdapter);

                    }

                }

            }

            @Override
            public void onErrorResponse(VolleyError arg0) {
                progressHUD.dismiss();
            }
        });

    }

    private void initUI() {
        findViewById(R.id.zero_back).setOnClickListener(this);
        findViewById(R.id.tv_share).setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.zero_listView);
        mNull = (ImageView) findViewById(R.id.zeroimg_null);
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String stock = mListData.get(position).getStock();
                if (!stock.equals("0")) {

                    Intent intent = new Intent(TryuseActivity.this, NetredDetailActivity.class);
                    String productId = mListData.get(position).getProductId();
                    String phone1 = mListData.get(position).getPhone1();
                    intent.putExtra("phone1", phone1);
                    intent.putExtra("product_id", productId);
                    startActivity(intent);
                } else {
                    Toast.makeText(TryuseActivity.this, "本期已结束", Toast.LENGTH_SHORT).show();
                }

            }
        });
        share_pop = new Share_pop(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zero_back:
                finish();
                break;
            case R.id.tv_share:
                share_pop.showAtLocation(findViewById(R.id.LinearLayout1), Gravity.CENTER, 0, 0);
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

                                    Bitmap thumb = Bitmap.createScaledBitmap(PhotoUtil.GetLocalOrNetBitmap( sharePicUrl), 120, 120, true);
                                    api.registerApp(APP_ID);
                                    api.sendReq(createReq(false, thumb));

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
        msg.description = content;
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
    //分享QQ
    public void share() {
        Bundle bundle = new Bundle();
        //这条分享消息被好友点击后的跳转URL。
        bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, share_url);
        //分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_	SUMMARY不能全为空，最少必须有一个是有值的。
        bundle.putString(QQShare.SHARE_TO_QQ_TITLE,  share_title);
        //分享的图片URL
        bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, sharePicUrl);
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
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE,  share_title);
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL,  share_url);
        //分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_	SUMMARY不能全为空，最少必须有一个是有值的。
//			bundle.putString(QzoneShare.SHARE_TO_QQ_TITLE, data.getTitle());
//			//分享的图片URL

//        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, content);
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
                    mTencent.shareToQzone(TryuseActivity.this, params, qqShareListener);
                }
            }
        });
    }

    class ZeroAdapter extends BaseAdapter {

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
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            ViewHolder holder;
            if (convertView == null) {
                view = getLayoutInflater().inflate(R.layout.zero_list_item, null);
                holder = new ViewHolder();
                holder.mLogo = (ImageView) view.findViewById(R.id.zeroitem_logo);
//				holder.mBackground = (ImageView) view.findViewById(R.id.zeroitem_qianggouimg);
                holder.mTitle = (TextView) view.findViewById(R.id.zeroitemtitle);
                holder.mNewPrice = (TextView) view.findViewById(R.id.zeroitem_newprice);
                holder.mOldPrice = (TextView) view.findViewById(R.id.zeroitem_oldprice);
                holder.mOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
//				holder.mNum = (TextView) view.findViewById(R.id.zeroitem_num);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            ZeroListDatum zeroListDatum = mListData.get(position);
            String currprice = zeroListDatum.getCurrprice();
            String marketprice = zeroListDatum.getMarketprice();
            String stock = zeroListDatum.getStock();
            String phone1 = zeroListDatum.getPhone1();
            String title = zeroListDatum.getTitle();

            if (stock.equals("0")) {
//				holder.mBackground.setImageResource(R.mipmap.lose);
//				holder.mNum.setVisibility(View.GONE);
            } else {
//				holder.mNum.setText("剩余"+stock);

            }
            holder.mTitle.setText(title);
            holder.mNewPrice.setText("￥" + currprice);
            holder.mOldPrice.setText("￥" + marketprice);
            UILUtils.displayImageNoAnim(phone1, holder.mLogo);
            return view;
        }

    }

    class ViewHolder {
        ImageView mLogo;
        //		ImageView mBackground;
        TextView mTitle;
        TextView mNewPrice;
        TextView mOldPrice;
//		TextView mNum;

    }

    public class MyListener implements PullToRefreshLayout.OnRefreshListener {


        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // 千万别忘了告诉控件刷新完毕了哦！
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 3000);
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            // 加载操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {

                    if (mListData.size() == 0) {
                        initList();
                        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    } else {
                        int size = mListData.size();
                        String url = Constant.URL.NineListURL;
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("type", "2");
                        map.put("num", "" + size);
                        HTTPUtils.post(TryuseActivity.this, url, map, new VolleyListener() {

                            @Override
                            public void onResponse(String arg0) {
                                Log.e("initList", arg0);
                                Gson gson = new Gson();
                                ZeroList fromJson = gson.fromJson(arg0, ZeroList.class);
                                Status status = fromJson.getStatus();
                                String succeed = status.getSucceed();
                                if (succeed.equals("1")) {
                                    List<ZeroListDatum> data = fromJson.getData();
                                    mListData.addAll(data);
                                    zeroAdapter.notifyDataSetChanged();
                                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                                } else {
                                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);

                                }

                            }

                            @Override
                            public void onErrorResponse(VolleyError arg0) {

                            }
                        });
                    }


                }
            }.sendEmptyMessageDelayed(0, 1000);
        }

    }


}
