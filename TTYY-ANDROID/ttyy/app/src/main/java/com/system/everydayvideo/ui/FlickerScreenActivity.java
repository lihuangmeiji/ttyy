package com.system.everydayvideo.ui;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;
import android.widget.Toast;

import com.system.everydayvideo.Bean.MenuListBean;
import com.system.everydayvideo.R;
import com.system.everydayvideo.present.FlickerScreenPresent;
import com.system.everydayvideo.util.ConstUtils;
import com.system.everydayvideo.util.DataHelper;
import com.system.everydayvideo.util.DataKeeper;
import com.system.everydayvideo.util.InstallApk;
import com.system.everydayvideo.util.SoftUpdate;
import com.system.everydayvideo.widget.StateView;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.net.NetError;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class FlickerScreenActivity extends XActivity<FlickerScreenPresent> {
    StateView errorView;
    Location location;
    // 获取位置管理服务
    LocationManager locationManager;

    @Override
    public void initData(Bundle savedInstanceState) {
        FlickerScreenActivityPermissionsDispatcher.showLocationWithCheck(FlickerScreenActivity.this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getP().loadData();
            }
        }, 3000);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_flicker_screen;
    }

    @Override
    public FlickerScreenPresent newP() {
        return new FlickerScreenPresent();
    }


    public void setMenuList(MenuListBean gankResults){
        finish();
        Intent intent=new Intent(context,MainActivity.class);
        intent.putExtra("menuListBean",gankResults);
        startActivity(intent);
        //MainActivity.launch(context,gankResults);
    }



    // 单个权限
    // @NeedsPermission(Manifest.permission.CAMERA)
    // 多个权限
    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION})
    void showLocation() {
        String serviceName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) this.getSystemService(serviceName);
        // 查找到服务信息
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗
        String provider = locationManager.getBestProvider(criteria, true); // 获取GPS信息
        /**这段代码不需要深究，是locationManager.getLastKnownLocation(provider)自动生成的，不加会出错**/
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //location = locationManager.getLastKnownLocation(provider); // 通过GPS获取位置
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, mLocationListener01);
        //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, mLocationListener01);
    }

    // 向用户说明为什么需要这些权限（可选）
    @OnShowRationale({Manifest.permission.ACCESS_FINE_LOCATION})
    void showRationaleForCamera(final PermissionRequest request) {
     /*   new AlertDialog.Builder(this)
                .setMessage("软件更新")
                .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();//再次执行请求
                    }
                })
                .show();*/
        request.proceed();//再次执行请求
    }

    // 用户拒绝授权回调（可选）
    @OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION})
    void showDeniedForCamera() {
        Toast.makeText(this, "小主，给个权限吧！", Toast.LENGTH_SHORT).show();
    }

    // 用户勾选了“不再提醒”时调用（可选）
    @OnNeverAskAgain({Manifest.permission.ACCESS_FINE_LOCATION})
    void showNeverAskForCamera() {
        Toast.makeText(this, "很开心，以后不打扰你了！", Toast.LENGTH_SHORT).show();
    }


    // 设置监听器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米
    public final LocationListener mLocationListener01 = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            updateToNewLocation(location);
        }


        @Override
        public void onProviderDisabled(String provider) {
            updateToNewLocation(null);
        }

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

    private Location updateToNewLocation(Location location1) {
        System.out.println("--------zhixing--2--------");
        String latLongString;
        double lat = 0;
        double lng=0;

        if (location1 != null) {
            lat = location1.getLatitude();
            lng = location1.getLongitude();
            latLongString = "纬度:" + lat + "\n经度:" + lng;
            //System.out.println("经度："+lng+"纬度："+lat);
            Geocoder gc = new Geocoder(FlickerScreenActivity.this, Locale.getDefault());
            List<Address> locationList = null;
            try {
                locationList = gc.getFromLocation(lat, lng, 1);
                Address address = locationList.get(0);//得到Address实例
                String countryName = address.getCountryName();//得到国家名称，比如：中国
                String locality = address.getLocality();//得到城市名称，比如：北京市
                DataHelper.putStringSF(FlickerScreenActivity.this,"locality",locality);
            } catch (Exception e) {
                DataHelper.putStringSF(FlickerScreenActivity.this,"locality",null);
            }
            DataHelper.putStringSF(FlickerScreenActivity.this,"lat",lat+"");
            DataHelper.putStringSF(FlickerScreenActivity.this,"lng",lng+"");
        } else {
            latLongString = "无法获取地理信息，请稍后...";
        }
        if(lat!=0){
            System.out.println("--------反馈信息----------"+ String.valueOf(lat));
        }
        //Toast.makeText(getApplicationContext(), latLongString, Toast.LENGTH_SHORT).show();
        return location;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(locationManager!=null){
            locationManager.removeUpdates(mLocationListener01);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 代理权限处理到自动生成的方法
        FlickerScreenActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
