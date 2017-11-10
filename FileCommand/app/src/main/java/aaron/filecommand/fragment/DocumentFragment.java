package aaron.filecommand.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import aaron.filecommand.R;
import aaron.filecommand.activity.AddCategoryActivity;
import aaron.filecommand.adapter.AppStoreAdapter;
import aaron.filecommand.adapter.DocumentAdapter;
import aaron.filecommand.model.ClassBean;
import aaron.filecommand.model.Storage;

import static android.R.attr.path;

/**
 * Created by Yunwen on 11/8/2017.
 */

public class DocumentFragment extends Fragment{
    String TAG = "DocumentFragment";
    private SharedPreferences sharedPrefs;
    RecyclerView mRecyclerView;
    private DocumentAdapter documentAdapter;
    List<ClassBean> listClass = new ArrayList<>();
    private Context mContext;
    private String dataContent;
    Storage mStorage;
    private TextView mPathView;

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
    private void showFiles(String path) {
        mPathView.setText(path);
        mStorage = new Storage(getActivity());
        List<File> files = mStorage.getFiles(path);
//        if (files != null) {
//            Collections.sort(files, OrderType.NAME.getComparator());
//        }

        RecyclerView recyclerView =  getView().findViewById(R.id.recyclerview_document);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        List<String> listImage = new ArrayList<>();
        for(int i = 0; i< files.size(); i++){
            listImage.add(files.get(i).toString());
            Log.d(TAG,files.get(i).toString());
        }
        DocumentAdapter adapter = new DocumentAdapter(getActivity(), listImage);
        recyclerView.setAdapter(adapter);
    }
    private void initView(View view){
        mPathView = view.findViewById(R.id.text_path);
        showFiles(dataContent);


    }
}
