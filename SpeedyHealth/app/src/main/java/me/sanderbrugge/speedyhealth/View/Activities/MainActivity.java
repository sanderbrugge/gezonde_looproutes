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
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import me.sanderbrugge.speedyhealth.Model.Looproutes;
import me.sanderbrugge.speedyhealth.Network.ApiService;
import me.sanderbrugge.speedyhealth.Network.DataBuilder;
import me.sanderbrugge.speedyhealth.R;
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
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Looproutes>> call, Throwable t) {
                Log.i(TAG,"in on error with cause: " + t.getCause() + "\n" + t.getStackTrace() + "\n" + t.getMessage());
            }
        });
    }

    protected void loadMap(GoogleMap googleMap) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(-18.142, 178.431), 2));

        // Polylines are useful for marking paths and routes on the map.
        googleMap.addPolyline(new PolylineOptions().geodesic(true)
                .add(new LatLng(-33.866, 151.195))  // Sydney
                .add(new LatLng(-18.142, 178.431))  // Fiji
                .add(new LatLng(21.291, -157.821))  // Hawaii
                .add(new LatLng(37.423, -122.091))  // Mountain View
        );
    }

}
