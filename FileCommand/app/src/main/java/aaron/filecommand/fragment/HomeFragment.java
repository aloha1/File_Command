package aaron.filecommand.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import aaron.filecommand.R;
import aaron.filecommand.activity.AddCategoryActivity;
import aaron.filecommand.adapter.HomeAdapter;
import aaron.filecommand.adapter.helper.SimpleItemTouchHelperCallback;
import aaron.filecommand.dao.Category;
import aaron.filecommand.dao.CategoryRepo;
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
    private ImageView imageViewSearch, imageAnalyzer;
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
        listAll();
//        ClassBean c1 = new ClassBean();
//        c1.setTagString("Pictues");
//        listClass.add(c1);
//        ClassBean c2 = new ClassBean();
//        c2.setTagString("Music");
//        listClass.add(c2);
//        ClassBean c3 = new ClassBean();
//        c3.setTagString("Videos");
//        listClass.add(c3);
//        ClassBean c4 = new ClassBean();
//        c4.setTagString("Documents");
//        listClass.add(c4);
//        ClassBean c5 = new ClassBean();
//        c5.setTagString("Acheives");
//        listClass.add(c5);
    }

    private void initView(View view){
        initRecycler(view);
        imageAnalyzer = view.findViewById(R.id.image_home_analyzer);
        imageAnalyzer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddCategoryActivity.class);
                getActivity().startActivity(intent);
            }
        });
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

    public void listAll() {
        int _algorithm_id = 0;
        CategoryRepo repo = new CategoryRepo(getActivity());
        Category category = new Category();
        Log.d(TAG, "The id is: " + _algorithm_id);
        category = repo.getColumnById(_algorithm_id);
        ArrayList<HashMap<String, String>> algorithmList = repo.getAlgorithmList();
        if (algorithmList.size() != 0) {//Show Db list
            initRecyclerView(algorithmList);
        } else {
            Toast.makeText(getActivity(), "No Content!", Toast.LENGTH_SHORT).show();
        }
    }

    private void initRecyclerView(ArrayList<HashMap<String, String>> algorithmList){
        for(int i = 0; i< algorithmList.size();i++){
            String data = algorithmList.get(i).get("topic");
            Log.d(TAG,"The "+i+"th is: "+data);
            //transfer to resId
            int resId = getResources().getIdentifier(data, "id", getActivity().getPackageName());
            Log.d(TAG,"The "+i+"th ID is: "+resId);
            //transfer image name into image id and text,
            ClassBean classBean = new ClassBean();
            classBean.setTagString(data.replace("image_tick_",""));
            classBean.setCoverImageUri(data);
            listClass.add(classBean);
        }
    }
    @Override
    public void onStop() {
        super.onStop();
    }
}