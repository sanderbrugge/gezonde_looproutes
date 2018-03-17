package me.sanderbrugge.speedyhealth.View.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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
}
