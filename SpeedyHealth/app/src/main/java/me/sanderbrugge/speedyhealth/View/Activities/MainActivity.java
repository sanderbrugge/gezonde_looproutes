package me.sanderbrugge.speedyhealth.View.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.maps.android.PolyUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import me.sanderbrugge.speedyhealth.Model.Looproutes;
import me.sanderbrugge.speedyhealth.Network.ApiService;
import me.sanderbrugge.speedyhealth.Network.DataBuilder;
import me.sanderbrugge.speedyhealth.R;
import me.sanderbrugge.speedyhealth.Util.TileProviderFactory;
import me.sanderbrugge.speedyhealth.Util.WMSTileProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private ArrayList<Looproutes> looproutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        looproutes = new ArrayList<>();
        getLooproutes();
    }

    private void getLooproutes() {
        ApiService service = DataBuilder.getInstance();

        service.getLooproutes().enqueue(new Callback<ArrayList<Looproutes>>() {

            @Override
            public void onResponse(Call<ArrayList<Looproutes>> call, Response<ArrayList<Looproutes>> response) {
                if(response.isSuccessful()) {
                    looproutes = response.body();
                    drawMap();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Looproutes>> call, Throwable t) {
                Log.i(TAG,"in on error with cause: " + t.getCause() + "\n" + t.getStackTrace() + "\n" + t.getMessage());
            }
        });
    }

    private void drawMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap map) {
                    loadMap(map);
                }
            });
        } else {
            Toast.makeText(this, "Error - Map Fragment was null!!", Toast.LENGTH_SHORT).show();
        }
    }

    protected void loadMap(GoogleMap googleMap) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(51.0543422, 3.717424299999948), 10)
        );

        List<List<LatLng>> decodedPaths = new ArrayList<>();
        for(int i = 0; i <= 29; i++) {
            List<LatLng> decodedPath = PolyUtil.decode(looproutes.get(i).getLineGoogle());
            decodedPaths.add(decodedPath);
        }

        for (List<LatLng> decodedPath : decodedPaths) {
            googleMap.addPolyline(new PolylineOptions().addAll(decodedPath));
        }

        TileProvider tileProvider = TileProviderFactory.getOsgeoWmsTileProvider();
        TileOverlay tileOverlay = googleMap.addTileOverlay(new TileOverlayOptions().tileProvider(tileProvider));

        tileOverlay.setTransparency(0.5f);
        tileOverlay.setVisible(true);
        tileOverlay.setFadeIn(true);

        Log.i(TAG,"TILE OVERLAY: " + tileOverlay.getTransparency() + "\n" + tileOverlay.toString());
    }

}
