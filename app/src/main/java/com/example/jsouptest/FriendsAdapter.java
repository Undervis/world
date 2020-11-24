package com.example.jsouptest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {

    private List<Friend> friends;
    private LayoutInflater inflater;

    public FriendsAdapter(Context context, List<Friend> friends){
        this.friends = friends;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public FriendsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.friends_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsAdapter.ViewHolder holder, int position) {
        Friend friend = friends.get(position);
        holder.tv_full_name.setText(friend.getFirst_name() + " " + friend.getLast_name());
        holder.tv_info.setText(friend.getBdate() + friend.getCity() + friend.getDomain());
        Picasso.get().load(friend.getPhoto()).error(R.drawable.layout_background).into(holder.im_photo);
        holder.im_photo.setClipToOutline(true);
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView im_photo;
        TextView tv_full_name;
        TextView tv_info;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            im_photo = itemView.findViewById(R.id.im_photo);
            tv_full_name = itemView.findViewById(R.id.tv_full_name);
            tv_info = itemView.findViewById(R.id.tv_info);
        }
    }
}
