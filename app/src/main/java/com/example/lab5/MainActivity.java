package com.example.lab5;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText txt1, txt2, txt3;
    Button btnInsert;
    TextView tvKQ;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        tvKQ = findViewById(R.id.tvKQ);

        btnInsert = findViewById(R.id.btnInsert);

        btnInsert.setOnClickListener(v -> {
            insertData(txt1, txt2, txt3, tvKQ);
        });

    }

    private void insertData(EditText txt1, EditText txt2, EditText txt3, TextView tvKQ) {
        SanPham s = new SanPham(txt1.getText().toString(),
                                txt2.getText().toString(),
                                txt3.getText().toString());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.5/000/api_check/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InsertSanPham insertSanPham = retrofit.create(InsertSanPham.class);
        Call<ResSanPham> call = insertSanPham.insertSanPham(s.getMaSP(),s.getTenSP(),s.getMoTa());
        call.enqueue(new Callback<ResSanPham>() {
            @Override
            public void onResponse(Call<ResSanPham> call, Response<ResSanPham> response) {
                ResSanPham res = response.body();
                tvKQ.setText(res.getMessage());
            }

            @Override
            public void onFailure(Call<ResSanPham> call, Throwable throwable) {
                tvKQ.setText(throwable.getMessage());
            }
        });

    }
}