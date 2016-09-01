package com.sebangsa.pemanasan1.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.sebangsa.pemanasan1.R;
import com.sebangsa.pemanasan1.model.Community;
import com.sebangsa.pemanasan1.model.User;

import java.util.List;

/**
 * Created by sebangsa on 8/30/16.
 */
public class SebangsaRecyclerViewAdapter extends RecyclerView.Adapter<SebangsaRecyclerViewAdapter.SebangsaRecyclerViewHolder> {

    private List<User> listUser;
    private List<Community> listCommunities;
    private LayoutInflater inflater;
    private Context c;
    private String type;

    public SebangsaRecyclerViewAdapter(List list, Context c, String type) {
        inflater = LayoutInflater.from(c);
        if (type.equals("User")) {
            listUser = (List<User>) list;
        } else {
            listCommunities = (List<Community>) list;
        }
        this.c = c;
        this.type = type;
    }

    @Override
    public SebangsaRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new SebangsaRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SebangsaRecyclerViewHolder holder, final int position) {
        if (type.equals("User")) {
            final User user = listUser.get(position);
            holder.username.setText("@" + user.getUsername());
            holder.name.setText(user.getName());
            holder.members.setVisibility(View.INVISIBLE);

            Glide.with(c).load(user.getAvatar().getMedium().trim()).asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.imageAvatar) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(c.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    holder.imageAvatar.setImageDrawable(circularBitmapDrawable);
                }
            });

            holder.buttonFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User.Action a = new User.Action();
                    if (user.getAction().isFollow()) {
                        a.setFollow(false);
                        user.setAction(a);
                    } else {
                        a.setFollow(true);
                        user.setAction(a);
                    }
                    setImageButtonUser(user, holder);
                }
            });

            setImageButtonUser(user, holder);
        } else if (type.equals("Community")) {
            final Community community = listCommunities.get(position);
            holder.username.setText("+" + community.getName());
            String description = community.getDescription().trim();
            if (community.getDescription().length() > 50) {
                description = community.getDescription().substring(0, 49) + "...";
            }

            if (description.length() == 0) {
                holder.name.setText(community.getStatistic().getUser() + " Members");
                holder.members.setVisibility(View.INVISIBLE);
            } else {
                holder.name.setText(description);
                holder.members.setText(community.getStatistic().getUser() + " Members");
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.container.getLayoutParams();
                if (description.length() < 30) {
                    params.height = 170;
                } else {
                    params.height = 190;
                }
                holder.container.setLayoutParams(params);
            }

            Glide.with(c).load(community.getAvatar().getMedium().trim()).asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.imageAvatar) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(c.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    holder.imageAvatar.setImageDrawable(circularBitmapDrawable);
                }
            });
            holder.buttonFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Community.Action a = new Community.Action();

                    if (community.getAction().isMember()) {
                        a.setMember(false);
                        community.setAction(a);
                    } else {
                        a.setMember(true);
                        community.setAction(a);
                    }
                    setImageButtonCommunity(community, holder);
                }
            });
            setImageButtonCommunity(community, holder);
        }
    }

    private void setImageButtonUser(User user, SebangsaRecyclerViewHolder holder) {
        if (user.getAction().isFollow()) {
            holder.buttonFollow.setImageResource(R.drawable.i_followed);
            holder.buttonFollow.setBackgroundResource(R.drawable.rounded_corners_imagebutton_green);
        } else {
            holder.buttonFollow.setImageResource(R.drawable.i_follow);
            holder.buttonFollow.setBackgroundResource(R.drawable.rounded_corners_imagebutton_white);
        }
    }

    private void setImageButtonCommunity(Community community, SebangsaRecyclerViewHolder holder) {
        if (community.getAction().isMember()) {
            holder.buttonFollow.setImageResource(R.drawable.i_joined);
            holder.buttonFollow.setBackgroundResource(R.drawable.rounded_corners_imagebutton_green);
        } else {
            holder.buttonFollow.setImageResource(R.drawable.i_join);
            holder.buttonFollow.setBackgroundResource(R.drawable.rounded_corners_imagebutton_white);
        }
    }

    @Override
    public int getItemCount() {
        if (type.equals("User")) {
            return listUser.size();
        } else {
            return listCommunities.size();
        }
    }

    class SebangsaRecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView username;
        private TextView name;
        private ImageView imageAvatar;
        private ImageButton buttonFollow;
        private TextView members;
        private View container;

        public SebangsaRecyclerViewHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.textView_username);
            name = (TextView) itemView.findViewById(R.id.textView_name);
            imageAvatar = (ImageView) itemView.findViewById(R.id.imageView_avatar);
            buttonFollow = (ImageButton) itemView.findViewById(R.id.imageButton_follow);
            members = (TextView) itemView.findViewById(R.id.textView_members);
            container = itemView.findViewById(R.id.root_view);
        }
    }
}
