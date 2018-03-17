package me.sanderbrugge.speedyhealth.Network;

import java.util.ArrayList;

import me.sanderbrugge.speedyhealth.Model.Looproutes;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/cultuursportvrijetijd/routeyoulooproutes.json")
    Call<ArrayList<Looproutes>> getLooproutes();
}
