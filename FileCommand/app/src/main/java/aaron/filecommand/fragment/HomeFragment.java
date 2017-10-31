package aaron.filecommand.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import aaron.filecommand.R;
import aaron.filecommand.adapter.HomeAdapter;
import aaron.filecommand.adapter.helper.SimpleItemTouchHelperCallback;
import aaron.filecommand.model.ClassBean;

/**
 * Created by Yunwen on 5/22/2017.
 */

public class HomeFragment extends Fragment {
    String TAG = "HistoryFragment";
    private SharedPreferences sharedPrefs;
    RecyclerView mRecyclerView;
    private HomeAdapter homeAdapter;
    List<ClassBean> listClass = new ArrayList<>();
    private Context mContext;
    private SearchView searchView;
    private EditText editText;
    private ImageView imageViewSearch;
    public HomeFragment(){
        mContext = getActivity();
    }
    private ItemTouchHelper mItemTouchHelper;
    public void dataFlow(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView(view);
    }

    private void initData(){
        ClassBean c1 = new ClassBean();
        c1.setTagString("Pictues");
        listClass.add(c1);
        ClassBean c2 = new ClassBean();
        c2.setTagString("Music");
        listClass.add(c2);
        ClassBean c3 = new ClassBean();
        c3.setTagString("Videos");
        listClass.add(c3);
        ClassBean c4 = new ClassBean();
        c4.setTagString("Documents");
        listClass.add(c4);
        ClassBean c5 = new ClassBean();
        c5.setTagString("Acheives");
        listClass.add(c5);
    }

    private void initView(View view){
        initRecycler(view);
    }

    private void initRecycler(View view){
        mRecyclerView = view.findViewById(R.id.recyclerview_home);
        homeAdapter = new HomeAdapter(getActivity(),listClass);

        mRecyclerView.setAdapter(homeAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));




        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(homeAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

    }
    @Override
    public void onStop() {
        super.onStop();
    }
}