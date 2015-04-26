package com.shmtu.myprojectforsmu.setting;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.shmtu.myprojectforsmu.BaseActivity;
import com.shmtu.myprojectforsmu.R;

public class SettingMap extends BaseActivity implements
OnGetGeoCoderResultListener{

	private MapView mMapView = null;
	private BaiduMap mBaiduMap = null;
	private LatLng position;
	private boolean isFirstLoc = true;// 是否首次定位
	private LocationClient mLocClient;// 定位相关
	BitmapDescriptor bitmap;
	private RelativeLayout mMarkerInfo;
	private LatLng ll = null;
	GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用
	private boolean flag = false;
	private MyLocationListenner myListener = new MyLocationListenner();

	/**
	 * 定位设备当前位置
	 * @param context
	 */
	public static void startSettingMapLocation(Context context) {
		Intent intent = new Intent(context, SettingMap.class);
		context.startActivity(intent);
	}
	
	/**
	 * 在地图上标注出房子所在位置
	 * @param context
	 * @param city	所在城市
	 * @param address	详细地址
	 */
	public static void startSettingMapSearch(
			Context context, String city,
			String address) {
		Intent intent = new Intent(context, SettingMap.class);
		intent.putExtra("city", city);
		intent.putExtra("address", address);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//在使用SDK各组件之前初始化context信息，传入ApplicationContext  
		//注意该方法要再setContentView方法之前实现  
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.setting_map);
		//获取地图控件引用  
		mMapView = (MapView) findViewById(R.id.setting_mapview);
		mMarkerInfo = (RelativeLayout) findViewById(R.id.id_marker_info);
		mBaiduMap = mMapView.getMap();

		//定义Maker坐标点  
		// 北京天安门的经纬度：39.915, 116.404 * 1E6
		// 上海市浦东新区的GPS纬度经度值:31.224078,121.540419
		LatLng point = new LatLng(31.224078,121.540419); 

		//设置地图的初始位置以及显示级别（比例尺大小：15是指比例尺为1:500）
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().target(point).zoom(15).build()));
		//构建Marker图标  
		bitmap = BitmapDescriptorFactory  
				.fromResource(R.drawable.icon_gcoding);
		// 初始化搜索模块，注册事件监听
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(this);

		Intent intent = getIntent();
		String city = intent.getStringExtra("city");
		String address = intent.getStringExtra("address");
		Toast.makeText(this, city + "::" + address, Toast.LENGTH_SHORT).show();
		//如果city和address为空，则定位当前位置，否则定位到address所在位置
		if (city == null || address == null || "".equals(city) 
				|| "".equals(address)) {
			//定位初始化
			mLocClient = new LocationClient(this);
			//开启定位图层
			mBaiduMap.setMyLocationEnabled(true);
			//定位初始化
			mLocClient = new LocationClient(this);
			// 初始化搜索模块，注册事件监听
			mSearch = GeoCoder.newInstance();
			mSearch.setOnGetGeoCodeResultListener(this);
			mLocClient.registerLocationListener(myListener);
			LocationClientOption option = new LocationClientOption();
			option.setOpenGps(true);// 打开gps
			option.setCoorType("bd09ll"); // 设置坐标类型
			option.setScanSpan(1000);
			mLocClient.setLocOption(option);
			mLocClient.start();
		} else {
			mSearch.geocode(new GeoCodeOption()
			.city(city)
			.address(address));
		}
		
		/*//flag为false时--定位；flag为true是--标注
		if (flag) {
			mSearch.geocode(new GeoCodeOption()
			.city("上海")
			.address("浦东新区浦建路725弄2号"));
		} else {

			//定位初始化
			mLocClient = new LocationClient(this);
			//开启定位图层
			mBaiduMap.setMyLocationEnabled(true);
			//定位初始化
			mLocClient = new LocationClient(this);
			// 初始化搜索模块，注册事件监听
			mSearch = GeoCoder.newInstance();
			mSearch.setOnGetGeoCodeResultListener(this);
			mLocClient.registerLocationListener(myListener);
			LocationClientOption option = new LocationClientOption();
			option.setOpenGps(true);// 打开gps
			option.setCoorType("bd09ll"); // 设置坐标类型
			option.setScanSpan(1000);
			mLocClient.setLocOption(option);
			mLocClient.start();

			//构建MarkerOption，用于在地图上添加Marker  
			OverlayOptions options = new MarkerOptions()
			.position(point)  //设置marker的位置
			.icon(bitmap)  //设置marker图标
			.zIndex(15)  //设置marker所在层级
			.draggable(true);  //设置手势拖拽
			//将marker添加到地图上
			marker = (Marker) (mBaiduMap.addOverlay(options));
			//在地图上添加Marker，并显示  
			mBaiduMap.addOverlay(options);
		}*/

		mBaiduMap.setOnMapClickListener(new OnMapClickListener() {

			@Override
			public boolean onMapPoiClick(MapPoi arg0) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onMapClick(LatLng arg0) {
				// TODO Auto-generated method stub
				mMarkerInfo.setVisibility(View.GONE);
				mBaiduMap.hideInfoWindow();
			}
		});

		//点击监听
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker marker) {
				// TODO Auto-generated method stub

				position = marker.getPosition();
				// 反Geo搜索
				mSearch.reverseGeoCode(new ReverseGeoCodeOption()
				.location(position));

				return false;
			}
		});

	}

	@Override  
	protected void onDestroy() {  
		super.onDestroy();  
		//在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理  
		mMapView.onDestroy();  
	}  
	@Override  
	protected void onResume() {  
		super.onResume();  
		//在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
		mMapView.onResume();  
	}  
	@Override  
	protected void onPause() {  
		super.onPause();  
		//在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
		mMapView.onPause();  
	}

	@Override
	public void onGetGeoCodeResult(GeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(SettingMap.this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
			.show();
			return;
		}
		mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.icon_marka)));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
				.getLocation()));
		String strInfo = String.format("纬度：%f 经度：%f",
				result.getLocation().latitude, result.getLocation().longitude);
	}

	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(SettingMap.this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
			.show();
			return;
		}
		mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.icon_marka)));

		//设置弹出框
		InfoWindow mInfoWindow;
		// 生成一个TextView用户在地图中显示InfoWindow
		TextView location = new TextView(getApplicationContext());
		location.setBackgroundResource(R.drawable.location_tips);
		location.setPadding(30, 20, 30, 50);
		location.setText(result.getAddress());
		// 将marker所在的经纬度的信息转化成屏幕上的坐标
		ll = result.getLocation();
		Point p = mBaiduMap.getProjection().toScreenLocation(ll);
		p.y -= 10;
		LatLng llInfo = mBaiduMap.getProjection().fromScreenLocation(p);
		// 为弹出的InfoWindow添加点击事件
		mInfoWindow = new InfoWindow(location, llInfo, 0);
		// 显示InfoWindow
		mBaiduMap.showInfoWindow(mInfoWindow);
		// 设置详细信息布局为可见
		mMarkerInfo.setVisibility(View.VISIBLE);
		// 根据商家信息为详细信息布局设置信息
		popupInfo(mMarkerInfo, result);

	}

	private void popupInfo(RelativeLayout layout,
			ReverseGeoCodeResult result) {
		// TODO Auto-generated method stub

		ViewHolder viewHolder = null;
		if(layout.getTag() == null){
			viewHolder = new ViewHolder();
			viewHolder.tvName = (TextView) layout
					.findViewById(R.id.tv_name);
			viewHolder.tvAddress = (TextView) layout.findViewById(R.id.tv_address);
			viewHolder.btnOk = (Button) layout.findViewById(R.id.btn_ok);
			viewHolder.btnCancel = (Button) layout.findViewById(R.id.btn_cancel);

			layout.setTag(viewHolder);
		}
		viewHolder = (ViewHolder) layout.getTag();
		viewHolder.tvName.setText("地址");
		viewHolder.tvAddress.setText(result.getAddress());
		viewHolder.btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(SettingMap.this, "OK", Toast.LENGTH_LONG).show();
			}
		});
		viewHolder.btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(SettingMap.this, "CANCEL", Toast.LENGTH_LONG).show();
			}
		});
	}

	private class ViewHolder
	{
		TextView tvName;
		TextView tvAddress;
		Button btnOk;
		Button btnCancel;
	}

	/**
	 * 定位SDK监听函数
	 */
	private class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder()
			.accuracy(location.getRadius())
			// 此处设置开发者获取到的方向信息，顺时针0-360
			.latitude(location.getLatitude())
			.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);


				mBaiduMap.addOverlay(new MarkerOptions().position(ll)
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.icon_gcoding)));
				mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(ll));
				/*// 反Geo搜索
				mSearch.reverseGeoCode(new ReverseGeoCodeOption()
				.location(ll));*/
				/*mSearch.geocode(new GeoCodeOption()
					.city("上海")
					.address("浦东新区浦建路725弄2号"));*/
			}
		}
	}
}
