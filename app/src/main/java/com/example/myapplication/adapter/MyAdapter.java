package com.example.myapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.myapplication.R;
import com.example.myapplication.entity.Bean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder> {
    private Context context;
    private List<Bean.RankingBean> list;

    public MyAdapter(Context context, List<Bean.RankingBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(View.inflate(context,R.layout.item,null));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.text_id.setText(list.get(position).getRank());
        holder.text_name.setText(list.get(position).getName());
        Glide.with(context).load(list.get(position).getAvatar())
                .error(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.ic_launcher_background)
                .transform(new RoundedCorners(10))
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onClick(list.get(position).getName());
            }
        });
    }
    OnItemClick onItemClick;
    public interface OnItemClick{
        void onClick(String s);
    }
    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_id)
        TextView text_id;
        @BindView(R.id.text_name)
        TextView text_name;
        @BindView(R.id.imageView)
        ImageView imageView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
