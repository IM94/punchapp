package fr.imane.punchapp;

import retrofit2.Call;
import java.util.List;
import retrofit2.http.GET;


public interface JsonPunchlines {

    @GET("punch")
    Call<List<Punchline>> getContent();

}
