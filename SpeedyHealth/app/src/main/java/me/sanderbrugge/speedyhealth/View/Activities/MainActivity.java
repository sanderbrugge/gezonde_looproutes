package me.sanderbrugge.speedyhealth.View.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.sanderbrugge.speedyhealth.Model.Looproutes;
import me.sanderbrugge.speedyhealth.Network.ApiService;
import me.sanderbrugge.speedyhealth.Network.DataBuilder;
import me.sanderbrugge.speedyhealth.R;
import me.sanderbrugge.speedyhealth.Util.TileProviderFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private ArrayList<Looproutes> looproutes;
    private List<List<LatLng>> decodedPaths = new ArrayList<>();
    private int position = 0;
    private GoogleMap map;

    @BindView(R.id.name)
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        name.setText("Loading...");
        hideActionBar();
        getLooproutes();
    }

    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void getLooproutes() {
        looproutes = new ArrayList<>();
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
        map = googleMap;
       for(int i = 0; i <= 29; i++) {
            List<LatLng> decodedPath = PolyUtil.decode(looproutes.get(i).getLineGoogle());
            decodedPaths.add(decodedPath);
        }

        List<LatLng> decodedPath = PolyUtil.decode(looproutes.get(position).getLineGoogle());
        decodedPaths.add(decodedPath);
        name.setText(looproutes.get(position).getName());
        LatLng center = decodedPath.get(decodedPath.size()/2);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(center.latitude, center.longitude), 12)
        );

        googleMap.addPolyline(
                new PolylineOptions()
                        .addAll(decodedPath)
                        .color(getResources().getColor(R.color.colorPrimary,null))
                        .width(4)
        );

        TileProvider tileProvider = TileProviderFactory.getOsgeoWmsTileProvider();
        TileOverlay tileOverlay = googleMap.addTileOverlay(new TileOverlayOptions().tileProvider(tileProvider));
        tileOverlay.setTransparency(0.5f);
        tileOverlay.setVisible(true);
    }

    @OnClick(R.id.previous)
    public void previous() {
        List<LatLng> decodedPath;
        try {
            position = position - 1;
            decodedPath = PolyUtil.decode(looproutes.get(position).getLineGoogle());
        } catch(IndexOutOfBoundsException exc) {
            position = looproutes.size()-1;
            decodedPath = PolyUtil.decode(looproutes.get(looproutes.size()-1).getLineGoogle());
        }
        name.setText(looproutes.get(position).getName());
        decodedPaths.add(decodedPath);
        name.setText(looproutes.get(position).getName());
        LatLng center = decodedPath.get(decodedPath.size()/2);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(center.latitude, center.longitude), 12)
        );

        map.addPolyline(
                new PolylineOptions()
                        .addAll(decodedPath)
                        .color(getResources().getColor(R.color.colorPrimary,null))
                        .width(4)
        );
    }

    @OnClick(R.id.next)
    public void next() {
        List<LatLng> decodedPath;
        try {
            position = position + 1;
            decodedPath = PolyUtil.decode(looproutes.get(position).getLineGoogle());
        } catch(IndexOutOfBoundsException exc) {
            position = 0;
            decodedPath = PolyUtil.decode(looproutes.get(0).getLineGoogle());
        }

        decodedPaths.add(decodedPath);
        name.setText(looproutes.get(position).getName());
        LatLng center = decodedPath.get(decodedPath.size()/2);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(center.latitude, center.longitude), 12)
        );

        map.addPolyline(
                new PolylineOptions()
                        .addAll(decodedPath)
                        .color(getResources().getColor(R.color.colorPrimary,null))
                        .width(4)
        );
    }

}
