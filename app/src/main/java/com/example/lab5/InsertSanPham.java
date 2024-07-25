package com.example.lab5;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface InsertSanPham {
    @FormUrlEncoded
    @POST("insert.php")
    Call<ResSanPham> insertSanPham(
            @Field("MaSP") String MaSp,
            @Field("TenSP") String TenSP,
            @Field("MoTa") String MoTa
    );
}
