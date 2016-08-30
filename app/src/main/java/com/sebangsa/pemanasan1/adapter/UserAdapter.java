package com.sebangsa.pemanasan1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sebangsa.pemanasan1.R;
import com.sebangsa.pemanasan1.model.User;

import java.util.List;

/**
 * Created by sebangsa on 8/30/16.
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {

    private List<User> listUser;
    private LayoutInflater inflater;
    private Context c;

    public UserAdapter(List<User> listUsers, Context c) {
        inflater = LayoutInflater.from(c);
        this.listUser = listUsers;
        this.c = c;
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {
        User user = listUser.get(position);
        holder.username.setText("@"+user.getUsername());
        holder.name.setText(user.getName());
        //holder.imageAvatar.setText(user.getName());

        Glide.with(c)
                .load(user.getAvatar().getMedium().trim())
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(holder.imageAvatar);
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    class UserHolder extends RecyclerView.ViewHolder {

        private TextView username;
        private TextView name;
        private ImageView imageAvatar;
        private View container;

        public UserHolder(View itemView) {
            super(itemView);

            username = (TextView) itemView.findViewById(R.id.textView_username);
            name = (TextView) itemView.findViewById(R.id.textView_name);
            imageAvatar = (ImageView) itemView.findViewById(R.id.imageView_avatar);
            container = itemView.findViewById(R.id.root_view);
        }
    }
}
