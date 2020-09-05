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

public class SecondTab extends Fragment {
    private RecyclerView recyclerView;
    private List<SkillModel> SkillList = new ArrayList<>();
    private View view;
    private String skillUrl = "https://gadsapi.herokuapp.com/";
    private List<SkillModel> skillList;
    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        getSkillResponse();
        view = inflater.inflate(R.layout.second_tab_layout,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.skill_recycler_view);
        SkillRecyclerViewAdapter skillRecyclerViewAdapter = new SkillRecyclerViewAdapter(getContext(),SkillList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(skillRecyclerViewAdapter);
        return view;
    }

    private void getSkillResponse() {
        Retrofit retrofit = new Retrofit.
                Builder().baseUrl(skillUrl)
                .addConverterFactory(GsonConverterFactory.create()).build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<List<SkillModel>> call = requestInterface.getSkillJson();
        call.enqueue(new Callback<List<SkillModel>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<List<SkillModel>> call, Response<List<SkillModel>> response) {
                skillList = new ArrayList<>(response.body());
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.skill_recycler_view);
                SkillRecyclerViewAdapter skillRecyclerViewAdapter = new SkillRecyclerViewAdapter(getContext(),skillList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(skillRecyclerViewAdapter);
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<List<SkillModel>> call, Throwable t) {
                System.out.println("failed");
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
