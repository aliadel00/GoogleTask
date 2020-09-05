package com.example.googletask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.annotations.EverythingIsNonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FirstTab extends Fragment {
    private List<LearningModel> learningList;
    String learningUrl = "https://gadsapi.herokuapp.com/";
    private View view;

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        getLearningResponse();
        view = inflater.inflate(R.layout.first_tab_layout,container,false);
        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private void getLearningResponse() {

        Retrofit retrofit = new Retrofit.
                Builder().baseUrl(learningUrl)
                .addConverterFactory(GsonConverterFactory.create()).build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<List<LearningModel>> call = requestInterface.getLearningJson();
        call.enqueue(new Callback<List<LearningModel>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<List<LearningModel>> call, Response<List<LearningModel>> response) {
                learningList = new ArrayList<>(response.body());
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.learning_recycler_view);
                LearningRecyclerViewAdapter learningRecyclerViewAdapter = new LearningRecyclerViewAdapter(getContext(),learningList);
                System.out.println(learningList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(learningRecyclerViewAdapter);
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<List<LearningModel>> call, Throwable t) {
              System.out.println("failed");
            }
        });
    }
}
