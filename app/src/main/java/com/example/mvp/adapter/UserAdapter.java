package com.example.mvp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mvp.R;
import com.example.mvp.model.User;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<User> userList;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;

    public UserAdapter(Context context, ArrayList<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM:
                viewHolder = new UserListHolder(inflater.inflate(R.layout.user_item, parent, false));
                break;
            case LOADING:
                viewHolder = new LoadingHolder(inflater.inflate(R.layout.layout_progressbar, parent, false));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case ITEM:
                UserListHolder holder = (UserListHolder) viewHolder;
                Glide.with(context).
                        applyDefaultRequestOptions(new RequestOptions().fitCenter().placeholder(R.drawable.profile_image).error(R.drawable.error_image))
                        .load(userList.get(position).getAvatar()).into(holder.ivAvatar);
                holder.tvName.setText(String.format("%s %s", userList.get(position).getFirstName(),
                        userList.get(position).getLastName()));

                holder.tvEmail.setText(userList.get(position).email);

                break;
            case LOADING:
                break;
        }
    }

    @Override
    public int getItemCount()
    {
        return userList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == userList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    static class UserListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_avatar)
        AppCompatImageView ivAvatar;
        @BindView(R.id.tv_name)
        AppCompatTextView tvName;
        @BindView(R.id.tv_email)
        AppCompatTextView tvEmail;

        public UserListHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    static class LoadingHolder extends RecyclerView.ViewHolder {

        public LoadingHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        userList.add(new User());
        notifyItemInserted(userList.size() - 1);
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
        int position = userList.size() - 1;
        if (userList.size() > 0 && userList.size() >= position) {
            userList.remove(position);
            notifyItemRemoved(position);
        }
    }

}
