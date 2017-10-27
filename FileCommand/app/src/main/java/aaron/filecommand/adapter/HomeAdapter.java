package aaron.filecommand.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import aaron.filecommand.R;
import aaron.filecommand.model.ClassBean;

import static android.content.Context.MODE_PRIVATE;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public enum ITEM_TYPE {
        ITEM_TYPE_IMAGE,
        ITEM_TYPE_TEXT
    }
    private SharedPreferences sharedPrefs;
    private final static String TAG = "HomeAdapter";
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private List<ClassBean> dataList;

    public HomeAdapter(Context context, List<ClassBean> dataList) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.dataList = dataList;
        sharedPrefs = mContext.getSharedPreferences("myPrefs", MODE_PRIVATE);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new TextViewHolder(mLayoutInflater.inflate(R.layout.card_content, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof TextViewHolder) {
        }
    }

    @Override
    public int getItemCount() {
        return (dataList.size() >= 5)?dataList.size():5;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? ITEM_TYPE.ITEM_TYPE_IMAGE.ordinal() : ITEM_TYPE.ITEM_TYPE_TEXT.ordinal();
    }

    public static class TextViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        CardView mCardView;
        ImageView imageView;

        TextViewHolder(View view) {
            super(view);
            mCardView =  view.findViewById(R.id.card_normal);
            imageView =  view.findViewById(R.id.image_content);
            mTextView = view.findViewById(R.id.text_content);
        }
    }
}