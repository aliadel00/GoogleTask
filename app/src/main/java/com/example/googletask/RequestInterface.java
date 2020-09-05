package com.example.googletask;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RequestInterface {
    @GET("api/hours")
    Call<List<LearningModel>> getLearningJson();
    @GET("api/skilliq")
    Call<List<SkillModel>> getSkillJson();
    @FormUrlEncoded
    @POST("FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse")
    Call<ResponseBody> SendData(
            @Field("entry.1877115667")String Name,
            @Field("entry.2006916086")String LastName,
            @Field("entry.1824927963")String EmailAddress,
            @Field("entry.284483984")String LinkToProject
    );
}
