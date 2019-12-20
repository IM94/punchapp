package fr.imane.punchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textViewResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_view_result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/IM94/Punchlines/master/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPunchlines JsonPunchlines = retrofit.create(JsonPunchlines.class);

        Call<List<Punchline>> call = JsonPunchlines.getContent();

        call.enqueue(new Callback<List<Punchline>>() {
            @Override
            public void onResponse(Call<List<Punchline>> call, Response<List<Punchline>> response) {

                if (!response.isSuccessful()){
                    textViewResult.setText("Code: "+ response.code());
                    return;
                }

                List<Punchline> punchlines = response.body();

                for (Punchline punchline : punchlines){
                    String content ="";
                    content += "punchline: " +punchline.getPunchline() + "\n";
                    content += "auteur: " +punchline.getAuthor() + "\n";
                    content += "album: " +punchline.getAlbum() + "\n";
                    content += "titre: " +punchline.getTitle() + "\n\n";

                    textViewResult.append(content);

                }
            }

            @Override
            public void onFailure(Call<List<Punchline>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}
