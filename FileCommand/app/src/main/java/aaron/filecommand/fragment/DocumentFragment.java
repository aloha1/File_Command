package aaron.filecommand.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import aaron.filecommand.R;
import aaron.filecommand.activity.AddCategoryActivity;
import aaron.filecommand.adapter.AppStoreAdapter;
import aaron.filecommand.model.ClassBean;
import aaron.filecommand.model.Storage;

/**
 * Created by Yunwen on 11/8/2017.
 */

public class DocumentFragment extends Fragment{
    String TAG = "DocumentFragment";
    private SharedPreferences sharedPrefs;
    RecyclerView mRecyclerView;
    private AppStoreAdapter appStoreAdapter;
    List<ClassBean> listClass = new ArrayList<>();
    private Context mContext;
    private String dataContent;
    Storage mStorage;

    public void dataFlow(String dataContent){
        this.dataContent = dataContent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_document, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData(view);
        initView(view);
    }

    private void initData(View view){

    }

    private void initView(View view){

    }
}
