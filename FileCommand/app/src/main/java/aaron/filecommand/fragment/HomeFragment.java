package aaron.filecommand.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
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
import aaron.filecommand.activity.InterAd_Activity;
import aaron.filecommand.adapter.HomeAdapter;
import aaron.filecommand.adapter.helper.SimpleItemTouchHelperCallback;
import aaron.filecommand.dao.Category;
import aaron.filecommand.dao.CategoryRepo;
import aaron.filecommand.model.ClassBean;

/**
 * Created by Yunwen on 5/22/2017.
 */

public class HomeFragment extends Fragment {
    String TAG = "HomeFragment";
    private SharedPreferences sharedPrefs;
    RecyclerView mRecyclerView;
    private HomeAdapter homeAdapter;
    List<ClassBean> listClass = new ArrayList<>();
    private Context mContext;
    private SearchView searchView;
    private EditText editText;
    private ImageView imageEditor, imageAnalyzer;
    public HomeFragment(){
        mContext = getActivity();
    }
    private ItemTouchHelper mItemTouchHelper;
    private int mTreeSteps = 1;

    public void dataFlow(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData(view);
        initView(view);
    }

    private void initData(View view){
        listAll(view);
    }

    private void initView(View view){
        imageAnalyzer = view.findViewById(R.id.image_home_analyzer);
        imageEditor = view.findViewById(R.id.image_home_editor);
        imageEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddCategoryActivity.class);
                getActivity().startActivity(intent);
            }
        });
        CardView cardView = view.findViewById(R.id.card_normal);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), InterAd_Activity.class);
                getActivity().startActivity(intent);
            }
        });
    }

    private void initRecycler(View view, List<ClassBean> listTemp){
        mRecyclerView = view.findViewById(R.id.recyclerview_home);
        homeAdapter = new HomeAdapter(getActivity(),listTemp);

        mRecyclerView.setAdapter(homeAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(homeAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    public void listAll(View view) {
        int _algorithm_id = 0;
        CategoryRepo repo = new CategoryRepo(getActivity());
        Category category = new Category();
        Log.d(TAG, "The id is: " + _algorithm_id);
        category = repo.getColumnById(_algorithm_id);
        ArrayList<HashMap<String, String>> algorithmList = repo.getAlgorithmList();
        if (algorithmList.size() != 0) {//Show Db list
            initRecyclerView(algorithmList, view);
        } else {
            Toast.makeText(getActivity(), "No Content!", Toast.LENGTH_SHORT).show();
        }
    }

    private void initRecyclerView(ArrayList<HashMap<String, String>> algorithmList, View view){
        List<ClassBean> listTemp = new ArrayList<>();
        for(int i = 0; i< algorithmList.size();i++){
            String data = algorithmList.get(i).get("topic");
            Log.d(TAG,"The "+i+"th is: "+data);
            //transfer to resId
            int resId = getResources().getIdentifier(data, "id", getActivity().getPackageName());
            Log.d(TAG,"The "+i+"th ID is: "+resId);
            //transfer image name into image id and text,
            ClassBean classBean = new ClassBean();
            classBean.setTagString(data.replace("image_tick_",""));
            classBean.setDescription(data);
            classBean.setCoverImageUri(data);
            listTemp.add(classBean);
            initRecycler(view, listTemp);
        }
    }
    @Override
    public void onStop() {
        super.onStop();
    }
    @Override
    public void onResume() {
        listAll(getView());
        super.onResume();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}