package com.dam.recyclerjsonvolley;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.ColorSpace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class AdapterItem extends RecyclerView.Adapter<AdapterItem.myViewHolder> {

    Context context;
    ArrayList<ModelItem> itemArrayList;

    public AdapterItem(Context context, ArrayList<ModelItem> itemArrayList) {
        this.context = context;
        this.itemArrayList = itemArrayList;
    }

    public AdapterItem() {
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler, parent, false);
        return new myViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        ModelItem currentItem = itemArrayList.get(position);

        String imageUrl = currentItem.getImageUrl();
        String creator = currentItem.getCreator();
        int likes = currentItem.getLikes();

        holder.tvCreator.setText(creator);
        holder.tvLikes.setText("Likes : " + likes);

        //Glide
        RequestOptions gestionDesErreurs = new RequestOptions()
                .centerCrop()
                .error(R.mipmap.ic_launcher_round) // Image erreur
                .placeholder(R.mipmap.ic_launcher); // Image par défaut

        Context context = holder.ivImage.getContext();
        Glide.with(context)
                .load(imageUrl)
                .apply(gestionDesErreurs)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivImage;
        private TextView tvCreator, tvLikes;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvCreator = itemView.findViewById(R.id.tvCreator);
            tvLikes = itemView.findViewById(R.id.tvLikes);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClickListener != null){
                        int pos = getBindingAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            onItemClickListener.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
