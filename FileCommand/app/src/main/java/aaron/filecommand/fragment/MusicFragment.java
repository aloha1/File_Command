package aaron.filecommand.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import aaron.filecommand.R;
import aaron.filecommand.adapter.AppStoreAdapter;
import aaron.filecommand.adapter.DocumentAdapter;
import aaron.filecommand.adapter.PhotoAdapter;
import aaron.filecommand.model.ClassBean;

/**
 * Created by Yunwen on 5/22/2017.
 */

public class MusicFragment extends Fragment {
    String TAG = "PhotoFragment";
    RecyclerView mRecyclerView;
    private AppStoreAdapter appStoreAdapter;
    List<ClassBean> listClass = new ArrayList<>();
    private Context mContext;
    List<String> listImage;

    public MusicFragment(){
        mContext = getActivity();
    }

    public void dataFlow(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView(view);
    }

    private void initData(){

    }

    private void initView(View view){
        initRecycler(view);
    }

    private void initRecycler(View view){
        RecyclerView recyclerView =  view.findViewById(R.id.recyclerview_photo);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        listImage = new ArrayList<>();
        Log.d(TAG,getsonglist().get(0));
        listImage = getsonglist();
        DocumentAdapter adapter = new DocumentAdapter(getActivity(), listImage);
        recyclerView.setAdapter(adapter);
    }

    List<String> getsonglist() {

        List<String> songlist = new ArrayList<>();
        ContentResolver contentResolver = getActivity().getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor == null) {
            // query failed, handle error.
        } else if (!cursor.moveToFirst()) {
            // no media on the device
        } else {
            do {

                String fullPath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                // ...process entry...
                Log.e("Full Path : ", fullPath);

                songlist.add(fullPath);
            } while (cursor.moveToNext());
        }
        return songlist;
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}