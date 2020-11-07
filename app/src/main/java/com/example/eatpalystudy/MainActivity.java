package com.example.eatpalystudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.util.maps.helper.Utility;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.w3c.dom.Text;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    Button buttonFind;
    public String KakaoMapAPI = "1866feb705d92f5c242b83c4691a30bb";
    public MapView mapView;
    public MapPoint mapPoint;
    public LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonFind = findViewById(R.id.buttonFind);
        mapView = new MapView(this);
        ViewGroup mapViewContainer = findViewById(R.id.map_view);
        mapView.setDaumMapApiKey(KakaoMapAPI);
        mapViewContainer.addView(mapView);
        mapView.removeAllPOIItems();
        System.out.println("=======================getKey" + getKeyHash(getApplicationContext()));
        Handler handler = new Handler();
        handler.postAtTime(new Runnable() {
            @Override
            public void run() {
                mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
            }
        },4000);
        locationManager = (LocationManager)getApplicationContext().getSystemService((Context.LOCATION_SERVICE));


//        final MapView mapView = new MapView(this);
////        TextView textView = new TextView(getApplicationContext());
////        textView.setText("TTTTTTT");
//        ViewGroup mapViewContainer = (ViewGroup)findViewById(R.id.map_view);
//        mapViewContainer.addView(mapView);
////        mapViewContainer.addView(textView);
//
//        //mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeadingWithoutMapMoving);
//        mapView.setCurrentLocationRadius(100); // meter
//        mapView.setMapCenterPoint(MapPoint.mapPointWithCONGCoord(37.53737528, 127.00557633), true);
//        mapView.setZoomLevel(7,true);
//        mapView.zoomIn(true);
//        mapView.setShowCurrentLocationMarker(true);
//
//        MapPOIItem marker = new MapPOIItem();
//        marker.setItemName("내가 있는 곳");
//        marker.setTag(0);


    }


    public void onCurrentLocationUpdate(MapView mapView, MapPoint currentLocation, float accuracyInMeters){
        MapPoint.GeoCoordinate mapPointGeo = currentLocation.getMapPointGeoCoord();
        Log.i(">>>>>",String.format(" here %d, %d= %f",mapPointGeo.latitude,mapPointGeo.longitude,accuracyInMeters));
    }

    public String getKeyHash(final Context context)
    {
        PackageInfo packageInfo = Utility.getPackageInfo( context, PackageManager.GET_SIGNATURES );
        if (packageInfo == null) return null; for (Signature signature : packageInfo.signatures)
        {
            try{
                MessageDigest md = MessageDigest.getInstance( "SHA" );
                md.update( signature.toByteArray() );
                return Base64.encodeToString( md.digest(), Base64.NO_WRAP );
            } catch (NoSuchAlgorithmException e) { System.out.println( "디버그 keyHash" + signature ); }
        } return null; }

    }