package aaron.filecommand.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import aaron.filecommand.R;
import aaron.filecommand.dao.Category;
import aaron.filecommand.dao.CategoryRepo;

/**
 * Created by Yunwen on 10/30/2017.
 */

public class AddCategoryActivity extends AppCompatActivity {
    private String TAG = "AddCategoryActivity";
    private Toolbar toolbar;
    private TextView toolbarTitle, textTitle, textContent;
    private ImageView imageToolbar;
    private CardView cardView;

    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcategory);
        initView();
    }

    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbarTitle = (TextView) findViewById(R.id.text_toolbar_title);
        setToolbarHome();



    }

    private void setToolbarHome(){
        toolbarTitle.setText("Add Category");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void checkAll() {
        int _algorithm_id = 0;
        CategoryRepo repo = new CategoryRepo(this);
        Category category = new Category();
        Log.d(TAG, "The id is: " + _algorithm_id);
        category = repo.getColumnById(_algorithm_id);
        ArrayList<HashMap<String, String>> algorithmList = repo.getAlgorithmList();
        if (algorithmList.size() != 0) {//Show Db list
            checkAllTick(algorithmList);
        } else {
            Toast.makeText(this, "No Content!", Toast.LENGTH_SHORT).show();
        }
    }

    public void listAll() {
        int _algorithm_id = 0;
        CategoryRepo repo = new CategoryRepo(this);
        Category category = new Category();
        Log.d(TAG, "The id is: " + _algorithm_id);
        category = repo.getColumnById(_algorithm_id);
        ArrayList<HashMap<String, String>> algorithmList = repo.getAlgorithmList();
        if (algorithmList.size() != 0) {//Show Db list
            initRecyclerView(algorithmList);
        } else {
            Toast.makeText(this, "No Content!", Toast.LENGTH_SHORT).show();
        }
    }
    private void checkAllTick(ArrayList<HashMap<String, String>> algorithmList){
        for(int i = 0; i < algorithmList.size(); i++){
            //checkTick(algorithmList.get(i));
        }
    }
    private void initRecyclerView(ArrayList<HashMap<String, String>> algorithmList){

    }
    private void checkTick(ImageView image, TextView textView){
        String data = "";
        CategoryRepo repo = new CategoryRepo(this);
        Category category = repo.getColumnByTopic(data);
        try{
            if(category.topic.equals(data)){
                image.setImageResource(R.drawable.tick);
            }else{
                image.setImageResource(R.drawable.square);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void changeImageLike(View view, Integer position, ImageView imageTarget){
        //ImageView imageView =  (ImageView) view.findViewById(R.id.image_discover_like);
        //assert(R.id.image_discover_like == imageTarget.getId());

        Integer integer = (Integer) imageTarget.getTag();
        integer = integer == null ? 0 : integer;
        switch(integer) {
            case R.drawable.tick:
                imageTarget.setImageResource(R.drawable.square);
                imageTarget.setTag(R.drawable.square);
                //deleteFavorite(listClass,position);
                break;
            case R.drawable.square:
            default:
                imageTarget.setImageResource(R.drawable.tick);
                imageTarget.setTag(R.drawable.tick);
                //addToFavorite(listClass,position);
                break;
        }
    }

    private void deleteFavorite(List<ClassBean> listClassBean, Integer position) {
        String topic = listClassBean.get(position).getTitle();
        MeFavoriteHistoryRepo repo = new MeFavoriteHistoryRepo(mContext);
        MeFavoriteHistory dbFavorite = repo.getColumnByTopic(topic);
        Log.d(TAG, "delete:"+topic);
        repo.delete(dbFavorite.dbId);
        Toast.makeText(mContext, "删除", Toast.LENGTH_SHORT).show();
    }

    private void addToFavorite(List<ClassBean> listClassBean, Integer position) {
        String title = listClassBean.get(position).getTitle(),
                lectureId = listClassBean.get(position).getId(),
                coverImageUri = listClassBean.get(position).getCoverImageUri(),
                teacher = listClassBean.get(position).getTeacherName();
        String topic = listClassBean.get(position).getTitle();
        MeFavoriteHistoryRepo repo = new MeFavoriteHistoryRepo(mContext);
        MeFavoriteHistory dbFavorite = repo.getColumnByTopic(topic);
        try {
            if (dbFavorite.topic.equals(topic)) {
                repo.update(dbFavorite);
                Toast.makeText(mContext, "没有内容", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            dbFavorite.time = 25;
            dbFavorite.content = "";
            dbFavorite.topic = topic;
            dbFavorite.title = title;
            dbFavorite.lectureId = lectureId;
            dbFavorite.coverImageUri = coverImageUri;
            dbFavorite.teacher = teacher;
            dbFavorite.dbId = _algorithm_id;
            _algorithm_id = repo.insert(dbFavorite);
            Log.d(TAG, "add:"+dbFavorite.topic);
            Toast.makeText(mContext, "收藏", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
