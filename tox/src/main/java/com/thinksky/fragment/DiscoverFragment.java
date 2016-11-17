package com.thinksky.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MapViewLayoutParams;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.squareup.otto.Subscribe;
import com.thinksky.log.Logger;
import com.thinksky.net.rpc.service.NetConstant;
import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.tox.DiscoverSelectActivity;
import com.thinksky.tox.DiscoverSendActivity;
import com.thinksky.tox.ImagePagerActivity;
import com.thinksky.tox.MainActivity;
import com.thinksky.tox.R;
import com.thinksky.tox.SegmentControl;
import com.thinksky.ui.basic.BasicFragment;
import com.thinksky.utils.imageloader.ImageLoader;
import com.tox.BaseApi;
import com.tox.BaseFunction;
import com.tox.ToastHelper;
import com.tox.Url;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import org.json.JSONObject;

import static android.support.v7.widget.StaggeredGridLayoutManager.TAG;

public class DiscoverFragment extends BasicFragment implements View.OnClickListener {
  private static final String CATEGORY_PERSONAL = "1";
  private static final String CATEGORY_FACTORY = "2";
  private static final String IS_SELF = "1";
  private static final String IS_DISPLAY = "1";
  private TextView name;
  private TextView dizhi;
  private TextView dianhua;
  private ImageView iv_round;
  private View mUserInfoContainer;
  public LocationClient mLocationClient = null;
  public BDLocationListener myListener = new MyLocationListener();
  private LatLng ll = new LatLng(37.463175, 121.400244);
  private SegmentControl mSegmentControl;
  private View view;
  private String session_id;
  private String userUid;
  private BaseApi baseApi;
  private List<ImageView> imgViewList;
  private ImageView iv_1;
  private ImageView iv_2;
  private ImageView iv_3;
  private Button mark;
  public static boolean marks = false;
  private String mCurrentType = CATEGORY_PERSONAL;
  private ImageView mIconView;
  /**
   * 点击POI后弹出的泡泡
   */
  private LinearLayout mPopView, images;
  MapView mMapView;
  BaiduMap mBaiduMap;
  // 初始化全局 bitmap 信息，不用时及时 recycle
  BitmapDescriptor fish_personal = BitmapDescriptorFactory.fromResource(R.drawable.fish_location);
  BitmapDescriptor fish_personal_myself = BitmapDescriptorFactory.fromResource(R.drawable
      .fish_location_myselef);
  BitmapDescriptor fish_factory = BitmapDescriptorFactory.fromResource(R.drawable.placelocation);
  BitmapDescriptor fish_factory_myself = BitmapDescriptorFactory.fromResource(R.drawable
      .place_location_myself);

  /**
   * 当前POI的坐标
   */

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  private void initLocation() {
    LocationClientOption option = new LocationClientOption();
    option.setLocationMode(LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
    option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
    int span = 1000;
    option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
    option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
    option.setOpenGps(true);//可选，默认false,设置是否使用gps
    option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
    option.setIsNeedLocationDescribe(
        true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
    option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
    option.setIgnoreKillProcess(
        false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
    option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
    option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
    mLocationClient.setLocOption(option);
  }

  @Override
  public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    if (hidden && !isVisible()) {
      setMyLocationConfig();

      //  设置默认缩放级别。放大我的位置周边信息
      MapStatusUpdate statusUpdate = MapStatusUpdateFactory.zoomTo(18);
      mBaiduMap.setMapStatus(statusUpdate);
    }
  }


  public void initMarker(final String type) {
    mCurrentType = type;
    mBaiduMap.clear();

    RsenUrlUtil.execute(this.getActivity(), RsenUrlUtil.URL_FX + "&session_id=" + Url.SESSIONID,
        new RsenUrlUtil.OnNetHttpResultListener() {
          @Override
          public void onNoNetwork(String msg) {
            ToastHelper.showToast(msg, Url.context);
          }

          @Override
          public void onResult(boolean state, String result, JSONObject jsonObject) {
            if (state && TextUtils.equals(mCurrentType, type)) {
              final FXBean wendaBean = JSON.parseObject(result, FXBean.class);

              for (FXBean.ResultEntity info : wendaBean.getResult()) {
                if (TextUtils.equals(info.getIsdisplay(), IS_DISPLAY) && !TextUtils.isEmpty(info
                    .getLatitude())) {
                  String d = info.getLatitude();
                  String f = info.getLongitude();
                  double dd = Double.parseDouble(d);
                  double ff = Double.parseDouble(f);
                  if (TextUtils.equals(userUid, info.getUid())) {
                    marks = true;
                  }
                  LatLng llA = new LatLng(dd, ff);
                  BitmapDescriptor descriptor;
                  if (TextUtils.equals(type, CATEGORY_PERSONAL)) {
                    if (TextUtils.equals(info.getIsmyself(), IS_SELF)) {
                      descriptor = fish_personal_myself;
                    } else {
                      descriptor = fish_personal;
                    }
                  } else {
                    if (TextUtils.equals(info.getIsmyself(), IS_SELF)) {
                      descriptor = fish_factory_myself;
                    } else {
                      descriptor = fish_factory;
                    }
                  }
                  MarkerOptions options = new MarkerOptions().position(llA).icon(descriptor)
                      .draggable
                          (true);
                  OverlayOptions ooA;
                  if (TextUtils.equals(info.getIsmyself(), IS_SELF)) {
                    ooA = options.zIndex(999);
                  } else {
                    ooA = options.zIndex(9);
                  }
                  Marker marker = (Marker) mBaiduMap.addOverlay(ooA);
                  Bundle bundle = new Bundle();
                  bundle.putSerializable("info", info);
                  marker.setExtraInfo(bundle);
                }
              }
              if (marks) {
                mark.setText("编辑位置");
              } else {
                mark.setText("去标记");
              }
            }
          }
        });
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.bt_traffic:
        //  判断当前是否启用交通图，如果没有就打开，如果有，就关闭
        mBaiduMap.setTrafficEnabled(!mBaiduMap.isTrafficEnabled());
        break;

      case R.id.bt_satellite:
        //  判断当前是否是卫星图，如果是，切换到默认底图，如果不是， 切换到卫星图
        mBaiduMap.setMapType(
            mBaiduMap.getMapType() == BaiduMap.MAP_TYPE_SATELLITE ? BaiduMap.MAP_TYPE_NORMAL
                : BaiduMap.MAP_TYPE_SATELLITE);
        break;
      case R.id.bt_myloc://  设置定位为跟随模式，我的位置重新作为屏幕中心
        setMyLocationConfig();

        //  设置默认缩放级别。放大我的位置周边信息
        MapStatusUpdate statusUpdate = MapStatusUpdateFactory.zoomTo(18);
        mBaiduMap.setMapStatus(statusUpdate);
        break;
    }
  }

  private void setMyLocationConfig() {
    MyLocationConfiguration config =
        new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, false,
            null);
    mBaiduMap.setMyLocationConfigeration(config);
  }

  @Subscribe
  public void handleMarkEvent(DiscoverSendActivity.MarkEvent event) {
    initMarker(event.isFactory);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_discover, container, false);
    mMapView = (MapView) view.findViewById(R.id.bmapView);
    mPopView = (LinearLayout) view.findViewById(R.id.pop);
    images = (LinearLayout) view.findViewById(R.id.images);
    dizhi = (TextView) view.findViewById(R.id.dizhi);
    dianhua = (TextView) view.findViewById(R.id.dianhua);
    mark = (Button) view.findViewById(R.id.button_mark);
    name = (TextView) view.findViewById(R.id.name);
    iv_round = (ImageView) view.findViewById(R.id.iv_round);
    mUserInfoContainer = view.findViewById(R.id.user_info_container);
    iv_1 = (ImageView) view.findViewById(R.id.iv_1);
    iv_2 = (ImageView) view.findViewById(R.id.iv_2);
    iv_3 = (ImageView) view.findViewById(R.id.iv_3);
    mIconView = (ImageView) view.findViewById(R.id.icon_contact);

    mSegmentControl = (SegmentControl) view.findViewById(R.id.segment_control);
    baseApi = new BaseApi();
    session_id = baseApi.getSeesionId();
    userUid = baseApi.getUid();

    mMapView.showZoomControls(false);
    view.findViewById(R.id.bt_traffic).setOnClickListener(this);// 交通图打开与关闭
    view.findViewById(R.id.bt_satellite).setOnClickListener(this);// 卫星图打开与关闭
    view.findViewById(R.id.bt_myloc).setOnClickListener(this);// 定位我的位置
    mBaiduMap = mMapView.getMap();
    // 开启定位图层
    mBaiduMap.setMyLocationEnabled(true);
    mLocationClient = new LocationClient(this.getContext());     //声明LocationClient类
    mLocationClient.registerLocationListener(myListener);    //注册监听函数
    initLocation();
    mLocationClient.start();
    initMarker(CATEGORY_PERSONAL);
    mSegmentControl.setSelectedTextColor(getResources().getColor(android.R.color.white));
    mSegmentControl.setOnSegmentControlClickListener(
        new SegmentControl.OnSegmentControlClickListener() {
          @Override
          public void onSegmentControlClick(int index) {

            switch (index) {
              case 0:
                mPopView.setVisibility(View.GONE);
                initMarker(CATEGORY_PERSONAL);
                break;
              case 1:
                mPopView.setVisibility(View.GONE);
                initMarker(CATEGORY_FACTORY);
                break;
              default:
                break;
            }
          }
        });

    //        initPop();
    mBaiduMap.setOnMapClickListener(mOnMapClickListener);
    mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
      public boolean onMarkerClick(final Marker marker) {
        final FXBean.ResultEntity bean = (FXBean.ResultEntity) marker.getExtraInfo().get("info");

        mPopView.setVisibility(View.VISIBLE);
        mark.setVisibility(View.GONE);
        if (TextUtils.equals(CATEGORY_PERSONAL, bean.isfactory)) {
          mIconView.setImageResource(R.drawable.weixin);
          dianhua.setText(bean.getWechat());
        } else if (TextUtils.equals(CATEGORY_FACTORY, bean.isfactory)) {
          dianhua.setText(bean.getMobile1());
          mIconView.setImageResource(R.drawable.phone);
        } else {
          mIconView.setImageResource(R.drawable.weixin);
        }
        dizhi.setText(bean.getAddress());
        //dianhua.setText(bean.getMobile1());
        if (TextUtils.equals(mCurrentType, CATEGORY_FACTORY)) {
          name.setText(bean.getFactory_name());
        } else if (TextUtils.equals(mCurrentType, CATEGORY_PERSONAL)) {
          name.setText(bean.getNickname());
        }

        //ResUtil.setRoundImage(RsenUrlUtil.URL_BASE + bean.getAvatar().getAvatar32(), iv_round);
        ImageLoader.loadOptimizedHttpImage(getActivity(), NetConstant.BASE_URL + bean.getAvatar()
            .getAvatar32()).bitmapTransform(new CropCircleTransformation(getActivity()))
            .placeholder(R.drawable.default_avatar).dontAnimate()
            .into(iv_round);
        mUserInfoContainer.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            baseApi.goUserInfo(getActivity(), bean.getUid());
          }
        });
        if (bean.getImages() != null && bean.getImages().size() > 0) {
          images.setVisibility(View.VISIBLE);
          int size = bean.getImages().size();
          iv_1.setVisibility(size > 0 ? View.VISIBLE : View.GONE);
          iv_2.setVisibility(size > 1 ? View.VISIBLE : View.GONE);
          iv_3.setVisibility(size > 2 ? View.VISIBLE : View.GONE);

          for (int i = 0; i < size; i++) {
            String url = RsenUrlUtil.URL_BASE + bean.getImages().get(i);
            ImageView imageView = null;
            if (i == 0) {
              imageView = iv_1;
            } else if (i == 1) {
              imageView = iv_2;
            } else if (i == 2) {
              imageView = iv_3;
            }
            if (imageView != null) {
              //ImageLoader.getInstance().displayImage(url, imageView);
              try {
                ImageLoader.loadOptimizedHttpImage(getActivity(), url).into(imageView);
              } catch (Exception e) {
                e.printStackTrace();
              }
              final int in = i;
              imageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                  Intent intent = new Intent(getActivity(), ImagePagerActivity.class);
                  Bundle bundle = new Bundle();
                  bundle.putStringArrayList("image_urls", (ArrayList<String>) bean.getImages());
                  bundle.putInt("image_index", in);
                  intent.putExtras(bundle);
                  startActivity(intent);
                }
              });
            }
          }
        } else {
          images.setVisibility(View.GONE);
        }

        return true;
      }
    });
    initView();
    return view;
  }

  @Subscribe
  public void handleEnterMapEvent(MainActivity.EnterMapEvent enterMapEvent) {
    if (enterMapEvent.isFactory) {
      mSegmentControl.setCurrentIndex(1);
    } else {
      mSegmentControl.setCurrentIndex(0);
    }
    MyLocationData locData = new MyLocationData.Builder() //设置精确米数
        .latitude(Double.parseDouble(enterMapEvent.lat))//设置纬度
        .longitude(Double.parseDouble(enterMapEvent.lng))//设置经度
        .build();

    // 把定位信息显示地图上
    mBaiduMap.setMyLocationData(locData);
    ll = new LatLng(Double.parseDouble(enterMapEvent.lat), Double.parseDouble(enterMapEvent.lng));
    MapStatus.Builder builder = new MapStatus.Builder();
    builder.target(ll).zoom(10.0f);
    //把定位设置为普通模式，该模式下，每次位置更新就不会将地图拖到我的位置。这样不影响拖动地图查看其他位置信息
    setMyLocationConfig();
    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
  }

  private void initView() {

    mark.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (BaseFunction.isLogin()) {
          Intent intent = new Intent(getActivity(), DiscoverSelectActivity.class);
          getActivity().startActivity(intent);
        } else {

          ToastHelper.showToast("未登陆，请登陆", getActivity());
        }
      }
    });
  }

  /**
   * 定位SDK监听函数
   */
  public class MyLocationListener implements BDLocationListener {

    @Override
    public void onReceiveLocation(BDLocation location) {
      // map view 销毁后不在处理新接收的位置
      //Receive Location
      if (location == null || mMapView == null) {
        return;
      }
      if (location.getLocType() == BDLocation.TypeGpsLocation || location.getLocType() ==
          BDLocation.TypeNetWorkLocation) {
        // 构造定位数据
        MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
            //设置精确米数
            .latitude(location.getLatitude())//设置纬度
            .longitude(location.getLongitude())//设置经度
            .build();

        // 把定位信息显示地图上
        mBaiduMap.setMyLocationData(locData);

        ll = new LatLng(location.getLatitude(), location.getLongitude());
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(10.0f);
        //把定位设置为普通模式，该模式下，每次位置更新就不会将地图拖到我的位置。这样不影响拖动地图查看其他位置信息
        setMyLocationConfig();
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
      } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {
        Logger.d(TAG, "Offline");
      } else {
        Logger.d(TAG, "exception");
        Toast.makeText(getActivity(), R.string.address_tips_location_fail, Toast.LENGTH_SHORT)
            .show();
      }
      mLocationClient.stop();
    }

    public void onReceivePoi(BDLocation poiLocation) {
    }

  }

  @Override
  public void onDestroy() {

    //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
    mBaiduMap.setMyLocationEnabled(false);
    mMapView.onDestroy();
    mMapView = null;
    super.onDestroy();
  }

  @Override
  public void onResume() {
    super.onResume();
    //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
    mMapView.onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
    initLocation();
    //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
    mMapView.onPause();
  }

  /**
   * 地图图层的 摸事件
   */
  private BaiduMap.OnMapClickListener mOnMapClickListener = new BaiduMap.OnMapClickListener() {

    private TextView mPopText;
    private MapViewLayoutParams.Builder mPopParamsBuilder;

    /**
     *  点击到POI时 发
     */
    @Override
    public boolean onMapPoiClick(MapPoi poi) {

      return true;
    }

    /**
     * 点击到地图的某个点时 发
     */
    @Override
    public void onMapClick(LatLng lat) {
      mPopView.setVisibility(View.GONE);
      mark.setVisibility(View.VISIBLE);
    }
  };

  @Subscribe
  public void handleReLocationEvent(ReLocationEvent event) {
    mPopView.setVisibility(View.GONE);
    initMarker(mCurrentType);
  }

  public static class ReLocationEvent {
  }

  public static class FXBean implements Serializable {

    /**
     * success : true
     * error_code : 0
     * message : 获取成功
     * result : [{"uid":"102","nickname":"唐德骏","sex":"1","birthday":"0000-00-00",
     * "qq":"284675291","login":"44","reg_ip":"3232239396","reg_time":"1453701779",
     * "last_login_ip":"3232239365","last_login_time":"1458613563","status":"1",
     * "last_login_role":"1","show_role":"1","signature":"我养鱼我快乐","pos_province":null,
     * "pos_city":{"id":"370600","name":"烟台市","level":"2","upid":"370000"},"pos_district":null,
     * "pos_community":"0","score1":"315","score2":"0","score3":"0","score4":"0","con_check":"1",
     * "total_check":"2","isfactory":"2","factory_name":"天虹渔业","longitude":"116.3926079355504",
     * "latitude":"39.91939456451247","isdisplay":"1","data":false,"mobile1":"123456789000",
     * "address":"北京市 城区 华门大 ","expand_info":{"qq":"","生日":"2016-03-30"},
     * "avatar":{"avatar32":"/opensns/Uploads/Avatar/2016-03-16/56e90f5f484cb_32_32.png",
     * "avatar64":"/opensns/Uploads/Avatar/2016-03-16/56e90f5f484cb_64_64.png",
     * "avatar128":"/opensns/Uploads/Avatar/2016-03-16/56e90f5f484cb_128_128.png",
     * "avatar256":"/opensns/Uploads/Avatar/2016-03-16/56e90f5f484cb_256_256.png",
     * "avatar512":"/opensns/Uploads/Avatar/2016-03-16/56e90f5f484cb_512_512.png"},"mobile":"",
     * "cover_url":[""],"images":["/opensns/Public/images/nopic.png"],"is_follow":0},
     * {"uid":"106","nickname":"飞过山","sex":"1","birthday":"0000-00-00","qq":"","login":"16",
     * "reg_ip":"3232239365","reg_time":"1455500723","last_login_ip":"3232239365",
     * "last_login_time":"1458876046","status":"1","last_login_role":"1","show_role":"1",
     * "signature":"从养鱼中发现生活的真知真 ","pos_province":{"id":"370000","name":"山东省","level":"1",
     * "upid":"0"},"pos_city":{"id":"370600","name":"烟台市","level":"2","upid":"370000"},
     * "pos_district":{"id":"370613","name":"莱山区","level":"3","upid":"370600"},
     * "pos_community":"0","score1":"287","score2":"0","score3":"0","score4":"0","con_check":"0",
     * "total_check":"0","isfactory":"1","factory_name":"南神","longitude":"121.4988404957559",
     * "latitude":"37.79427362810932","isdisplay":"1","data":"","mobile1":"15253539423",
     * "address":"烟台市蓝海 1号","expand_info":{"qq":"","生日":"2016-03-30"},
     * "avatar":{"avatar32":"/opensns/Uploads/Avatar/2016-03-21/56ef932f49fd2_32_32.png",
     * "avatar64":"/opensns/Uploads/Avatar/2016-03-21/56ef932f49fd2_64_64.png",
     * "avatar128":"/opensns/Uploads/Avatar/2016-03-21/56ef932f49fd2_128_128.png",
     * "avatar256":"/opensns/Uploads/Avatar/2016-03-21/56ef932f49fd2_256_256.png",
     * "avatar512":"/opensns/Uploads/Avatar/2016-03-21/56ef932f49fd2_512_512.png"},"mobile":"",
     * "cover_url":[""],"images":["/opensns/Public/images/nopic.png"],"is_follow":0},
     * {"uid":"113","nickname":"18660587271","sex":"0","birthday":"0000-00-00","qq":"",
     * "login":"0","reg_ip":"0","reg_time":"0","last_login_ip":"0","last_login_time":"0",
     * "status":"1","last_login_role":"0","show_role":"0","signature":"","pos_province":null,
     * "pos_city":null,"pos_district":null,"pos_community":"0","score1":"10","score2":"0",
     * "score3":"0","score4":"0","con_check":"0","total_check":"0","isfactory":"2",
     * "factory_name":"我们在这里","longitude":"121.4860402118306","latitude":"37.46217811659226",
     * "isdisplay":"1","data":"158,142,159","mobile1":"1111111111","address":"山东省烟台市莱山区港城东大 ",
     * "expand_info":{"qq":"","生日":"2016-03-30"},
     * "avatar":{"avatar32":"/opensns/Public/images/default_avatar_32_32.jpg",
     * "avatar64":"/opensns/Public/images/default_avatar_64_64.jpg",
     * "avatar128":"/opensns/Public/images/default_avatar_128_128.jpg",
     * "avatar256":"/opensns/Public/images/default_avatar_256_256.jpg",
     * "avatar512":"/opensns/Public/images/default_avatar_512_512.jpg"},"mobile":"",
     * "cover_url":["158","142","159"],
     * "images":["/opensns/Uploads/Picture/2016-03-15/56e75fe7783dd_100_100.png",
     * "/opensns/Uploads/Picture/2016-03-10/56e13b6510038_100_100.png",
     * "/opensns/Uploads/Picture/2016-03-15/56e75ff6f1cbe_100_100.png"],"is_follow":0}]
     */

    private boolean success;
    private int error_code;
    private String message;
    /**
     * uid : 102
     * nickname : 唐德骏
     * sex : 1
     * birthday : 0000-00-00
     * qq : 284675291
     * login : 44
     * reg_ip : 3232239396
     * reg_time : 1453701779
     * last_login_ip : 3232239365
     * last_login_time : 1458613563
     * status : 1
     * last_login_role : 1
     * show_role : 1
     * signature : 我养鱼我快乐
     * pos_province : null
     * pos_city : {"id":"370600","name":"烟台市","level":"2","upid":"370000"}
     * pos_district : null
     * pos_community : 0
     * score1 : 315
     * score2 : 0
     * score3 : 0
     * score4 : 0
     * con_check : 1
     * total_check : 2
     * isfactory : 2
     * factory_name : 天虹渔业
     * longitude : 116.3926079355504
     * latitude : 39.91939456451247
     * isdisplay : 1
     * data : false
     * mobile1 : 123456789000
     * address : 北京市 城区 华门大
     * expand_info : {"qq":"","生日":"2016-03-30"}
     * avatar : {"avatar32":"/opensns/Uploads/Avatar/2016-03-16/56e90f5f484cb_32_32.png",
     * "avatar64":"/opensns/Uploads/Avatar/2016-03-16/56e90f5f484cb_64_64.png",
     * "avatar128":"/opensns/Uploads/Avatar/2016-03-16/56e90f5f484cb_128_128.png",
     * "avatar256":"/opensns/Uploads/Avatar/2016-03-16/56e90f5f484cb_256_256.png",
     * "avatar512":"/opensns/Uploads/Avatar/2016-03-16/56e90f5f484cb_512_512.png"}
     * mobile :
     * cover_url : [""]
     * images : ["/opensns/Public/images/nopic.png"]
     * is_follow : 0
     */

    private List<ResultEntity> result;

    public boolean isSuccess() {
      return success;
    }

    public void setSuccess(boolean success) {
      this.success = success;
    }

    public int getError_code() {
      return error_code;
    }

    public void setError_code(int error_code) {
      this.error_code = error_code;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

    public List<ResultEntity> getResult() {
      return result;
    }

    public void setResult(List<ResultEntity> result) {
      this.result = result;
    }

    public static class ResultEntity implements Serializable {
      private String uid;
      private String nickname;
      private String sex;
      private String birthday;
      private String qq;
      private String login;
      private String reg_ip;
      private String reg_time;
      private String last_login_ip;
      private String last_login_time;
      private String status;
      private String last_login_role;
      private String show_role;
      private String signature;
      private Object pos_province;
      /**
       * id : 370600
       * name : 烟台市
       * level : 2
       * upid : 370000
       */

      private Object pos_district;
      private String pos_community;
      private String score1;
      private String score2;
      private String score3;
      private String score4;
      private String con_check;
      private String total_check;
      private String isfactory;
      private String factory_name;
      private String longitude;
      private String latitude;
      private String isdisplay;
      private String data;
      private String mobile1;
      private String address;
      private String wechat;
      private String ismyself;
      /**
       * qq :
       * 生日 : 2016-03-30
       */

      private ExpandInfoEntity expand_info;
      /**
       * avatar32 : /opensns/Uploads/Avatar/2016-03-16/56e90f5f484cb_32_32.png
       * avatar64 : /opensns/Uploads/Avatar/2016-03-16/56e90f5f484cb_64_64.png
       * avatar128 : /opensns/Uploads/Avatar/2016-03-16/56e90f5f484cb_128_128.png
       * avatar256 : /opensns/Uploads/Avatar/2016-03-16/56e90f5f484cb_256_256.png
       * avatar512 : /opensns/Uploads/Avatar/2016-03-16/56e90f5f484cb_512_512.png
       */

      private AvatarEntity avatar;
      private String mobile;
      private int is_follow;
      private List<String> cover_url;
      private List<String> images;

      public String getIsmyself() {
        return ismyself;
      }

      public void setIsmyself(String ismyself) {
        this.ismyself = ismyself;
      }

      public String getWechat() {
        return wechat;
      }

      public void setWechat(String wechat) {
        this.wechat = wechat;
      }

      public String getUid() {
        return uid;
      }

      public void setUid(String uid) {
        this.uid = uid;
      }

      public String getNickname() {
        return nickname;
      }

      public void setNickname(String nickname) {
        this.nickname = nickname;
      }

      public String getSex() {
        return sex;
      }

      public void setSex(String sex) {
        this.sex = sex;
      }

      public String getBirthday() {
        return birthday;
      }

      public void setBirthday(String birthday) {
        this.birthday = birthday;
      }

      public String getQq() {
        return qq;
      }

      public void setQq(String qq) {
        this.qq = qq;
      }

      public String getLogin() {
        return login;
      }

      public void setLogin(String login) {
        this.login = login;
      }

      public String getReg_ip() {
        return reg_ip;
      }

      public void setReg_ip(String reg_ip) {
        this.reg_ip = reg_ip;
      }

      public String getReg_time() {
        return reg_time;
      }

      public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
      }

      public String getLast_login_ip() {
        return last_login_ip;
      }

      public void setLast_login_ip(String last_login_ip) {
        this.last_login_ip = last_login_ip;
      }

      public String getLast_login_time() {
        return last_login_time;
      }

      public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
      }

      public String getStatus() {
        return status;
      }

      public void setStatus(String status) {
        this.status = status;
      }

      public String getLast_login_role() {
        return last_login_role;
      }

      public void setLast_login_role(String last_login_role) {
        this.last_login_role = last_login_role;
      }

      public String getShow_role() {
        return show_role;
      }

      public void setShow_role(String show_role) {
        this.show_role = show_role;
      }

      public String getSignature() {
        return signature;
      }

      public void setSignature(String signature) {
        this.signature = signature;
      }

      public Object getPos_province() {
        return pos_province;
      }

      public void setPos_province(Object pos_province) {
        this.pos_province = pos_province;
      }

      public Object getPos_district() {
        return pos_district;
      }

      public void setPos_district(Object pos_district) {
        this.pos_district = pos_district;
      }

      public String getPos_community() {
        return pos_community;
      }

      public void setPos_community(String pos_community) {
        this.pos_community = pos_community;
      }

      public String getScore1() {
        return score1;
      }

      public void setScore1(String score1) {
        this.score1 = score1;
      }

      public String getScore2() {
        return score2;
      }

      public void setScore2(String score2) {
        this.score2 = score2;
      }

      public String getScore3() {
        return score3;
      }

      public void setScore3(String score3) {
        this.score3 = score3;
      }

      public String getScore4() {
        return score4;
      }

      public void setScore4(String score4) {
        this.score4 = score4;
      }

      public String getCon_check() {
        return con_check;
      }

      public void setCon_check(String con_check) {
        this.con_check = con_check;
      }

      public String getTotal_check() {
        return total_check;
      }

      public void setTotal_check(String total_check) {
        this.total_check = total_check;
      }

      public String getIsfactory() {
        return isfactory;
      }

      public void setIsfactory(String isfactory) {
        this.isfactory = isfactory;
      }

      public String getFactory_name() {
        return factory_name;
      }

      public void setFactory_name(String factory_name) {
        this.factory_name = factory_name;
      }

      public String getLongitude() {
        return longitude;
      }

      public void setLongitude(String longitude) {
        this.longitude = longitude;
      }

      public String getLatitude() {
        return latitude;
      }

      public void setLatitude(String latitude) {
        this.latitude = latitude;
      }

      public String getIsdisplay() {
        return isdisplay;
      }

      public void setIsdisplay(String isdisplay) {
        this.isdisplay = isdisplay;
      }

      public String isData() {
        return data;
      }

      public void setData(String data) {
        this.data = data;
      }

      public String getMobile1() {
        return mobile1;
      }

      public void setMobile1(String mobile1) {
        this.mobile1 = mobile1;
      }

      public String getAddress() {
        return address;
      }

      public void setAddress(String address) {
        this.address = address;
      }

      public ExpandInfoEntity getExpand_info() {
        return expand_info;
      }

      public void setExpand_info(ExpandInfoEntity expand_info) {
        this.expand_info = expand_info;
      }

      public AvatarEntity getAvatar() {
        return avatar;
      }

      public void setAvatar(AvatarEntity avatar) {
        this.avatar = avatar;
      }

      public String getMobile() {
        return mobile;
      }

      public void setMobile(String mobile) {
        this.mobile = mobile;
      }

      public int getIs_follow() {
        return is_follow;
      }

      public void setIs_follow(int is_follow) {
        this.is_follow = is_follow;
      }

      public List<String> getCover_url() {
        return cover_url;
      }

      public void setCover_url(List<String> cover_url) {
        this.cover_url = cover_url;
      }

      public List<String> getImages() {
        return images;
      }

      public void setImages(List<String> images) {
        this.images = images;
      }

      public static class PosCityEntity {
        private String id;
        private String name;
        private String level;
        private String upid;

        public String getId() {
          return id;
        }

        public void setId(String id) {
          this.id = id;
        }

        public String getName() {
          return name;
        }

        public void setName(String name) {
          this.name = name;
        }

        public String getLevel() {
          return level;
        }

        public void setLevel(String level) {
          this.level = level;
        }

        public String getUpid() {
          return upid;
        }

        public void setUpid(String upid) {
          this.upid = upid;
        }
      }

      public static class ExpandInfoEntity {
        private String qq;
        private String 生日;

        public String getQq() {
          return qq;
        }

        public void setQq(String qq) {
          this.qq = qq;
        }

        public String get生日() {
          return 生日;
        }

        public void set生日(String 生日) {
          this.生日 = 生日;
        }
      }

      public static class AvatarEntity {
        private String avatar32;
        private String avatar64;
        private String avatar128;
        private String avatar256;
        private String avatar512;

        public String getAvatar32() {
          return avatar32;
        }

        public void setAvatar32(String avatar32) {
          this.avatar32 = avatar32;
        }

        public String getAvatar64() {
          return avatar64;
        }

        public void setAvatar64(String avatar64) {
          this.avatar64 = avatar64;
        }

        public String getAvatar128() {
          return avatar128;
        }

        public void setAvatar128(String avatar128) {
          this.avatar128 = avatar128;
        }

        public String getAvatar256() {
          return avatar256;
        }

        public void setAvatar256(String avatar256) {
          this.avatar256 = avatar256;
        }

        public String getAvatar512() {
          return avatar512;
        }

        public void setAvatar512(String avatar512) {
          this.avatar512 = avatar512;
        }
      }
    }
  }
}
