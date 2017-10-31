package aaron.filecommand.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import aaron.filecommand.R;
import aaron.filecommand.adapter.helper.ItemTouchHelperAdapter;
import aaron.filecommand.adapter.helper.OnStartDragListener;
import aaron.filecommand.model.ClassBean;

import static android.content.Context.MODE_PRIVATE;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter {
    public enum ITEM_TYPE {
        ITEM_TYPE_IMAGE,
        ITEM_TYPE_TEXT
    }
    private SharedPreferences sharedPrefs;
    private final static String TAG = "HomeAdapter";
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private List<ClassBean> dataList;
   // private final OnStartDragListener mDragStartListener;

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
            ((TextViewHolder)holder).mTextView.setText(dataList.get(position).getTagString());
        }
    }

    @Override
    public int getItemCount() {
        return (dataList.size() >= 1)?dataList.size():1;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? ITEM_TYPE.ITEM_TYPE_IMAGE.ordinal() : ITEM_TYPE.ITEM_TYPE_TEXT.ordinal();
    }

    public static class TextViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        CardView mCardView;
        ImageView imageView;

        public TextViewHolder(View view) {
            super(view);
            mCardView =  view.findViewById(R.id.card_normal);
            imageView =  view.findViewById(R.id.image_content);
            mTextView = view.findViewById(R.id.text_content);
        }

    }

    @Override
    public void onItemDismiss(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(dataList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }


}