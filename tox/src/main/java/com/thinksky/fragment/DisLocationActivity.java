package com.thinksky.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.tox.DiscoverSendActivity;
import com.thinksky.tox.R;

/**
 * o
 * Created by jiao on 2016/4/8.
 */
public class DisLocationActivity extends BaseBActivity implements OnGetGeoCoderResultListener {
  GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用
  BaiduMap mBaiduMap = null;
  MapView mMapView = null;
  private EditText geocodekey;
  private Button geocode;
  public BDLocationListener myListener = new MyLocationListener();
  public LocationClient mLocationClient = null;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_geocoder);

    geocodekey = (EditText) findViewById(R.id.geocodekey);
    geocode = (Button) findViewById(R.id.geocode);
    // 地图初始化
    mMapView = (MapView) findViewById(R.id.bmapView);
    mBaiduMap = mMapView.getMap();
    // 开启定位图层
    mBaiduMap.setMyLocationEnabled(true);
    mLocationClient = new LocationClient(this);     //声明LocationClient类
    mLocationClient.registerLocationListener(myListener);    //注册监听函数
    initLocation();
    mLocationClient.start();
    // 初始化搜索模块，注册事件监听
    mSearch = GeoCoder.newInstance();
    mSearch.setOnGetGeoCodeResultListener(this);
    mBaiduMap.setOnMapLongClickListener(new BaiduMap.OnMapLongClickListener() {
      @Override
      public void onMapLongClick(final LatLng latLng) {
        LatLng ptCenter = new LatLng(latLng.latitude, latLng.longitude);
        // 反Geo搜索
        mSearch.reverseGeoCode(new ReverseGeoCodeOption()
            .location(ptCenter));
        geocode.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent = new Intent(DisLocationActivity.this, DiscoverSendActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("latitude", String.valueOf(latLng.latitude));
            bundle.putString("longitude", String.valueOf(latLng.longitude));
            bundle.putString("address", String.valueOf(geocodekey.getText()));
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
          }
        });
      }
    });


  }

  private void initLocation() {
    LocationClientOption option = new LocationClientOption();
    option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
      //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
    option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
    int span = 1000;
    option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
    option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
    option.setOpenGps(true);//可选，默认false,设置是否使用gps
    option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
    option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation
      // .getLocationDescribe里得到，结果类似于“在北京天安门附近”
    option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
    option.setIgnoreKillProcess(false);
      //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
    option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
    option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
    mLocationClient.setLocOption(option);
  }

  /**
   * 发起搜索
   */
  public void searchButtonProcess(View v) {

  }

  @Override
  protected void onPause() {
    mMapView.onPause();
    super.onPause();
  }

  @Override
  protected void onResume() {
    mMapView.onResume();
    super.onResume();

  }

  @Override
  protected void onDestroy() {

    //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
    mBaiduMap.setMyLocationEnabled(false);

    mMapView.onDestroy();
    mSearch.destroy();
    super.onDestroy();
  }

  @Override
  public void onGetGeoCodeResult(GeoCodeResult result) {
    if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
      Toast.makeText(DisLocationActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
          .show();
      return;
    }
    mBaiduMap.clear();
    mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
        .icon(BitmapDescriptorFactory
            .fromResource(R.drawable.icon_marka)));
    mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
        .getLocation()));
    String strInfo = String.format("纬度：%f 经度：%f",
        result.getLocation().latitude, result.getLocation().longitude);
    Toast.makeText(DisLocationActivity.this, strInfo, Toast.LENGTH_LONG).show();
    geocodekey.setText(result.getAddress());
  }

  @Override
  public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
    if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
      Toast.makeText(DisLocationActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
          .show();
      return;
    }
    mBaiduMap.clear();
    mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
        .icon(BitmapDescriptorFactory
            .fromResource(R.drawable.icon_marka)));
    mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
        .getLocation()));
    geocodekey.setText(result.getAddress());


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


      // 构造定位数据
      MyLocationData locData = new MyLocationData.Builder()
          .accuracy(location.getRadius()) //设置精确米数
          .latitude(location.getLatitude())//设置纬度
          .longitude(location.getLongitude())//设置经度
          .build();

      // 把定位信息显示地图上
      mBaiduMap.setMyLocationData(locData);

      LatLng ll = new LatLng(location.getLatitude(),
          location.getLongitude());
      MapStatus.Builder builder = new MapStatus.Builder();
      builder.target(ll).zoom(10.0f);
      //把定位设置为普通模式，该模式下，每次位置更新就不会将地图拖到我的位置。这样不影响拖动地图查看其他位置信息
      MyLocationConfiguration config = new MyLocationConfiguration(
          MyLocationConfiguration.LocationMode.NORMAL, false, null);
      mBaiduMap.setMyLocationConfigeration(config);
      mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
      mLocationClient.stop();
    }

    public void onReceivePoi(BDLocation poiLocation) {
    }
  }

}