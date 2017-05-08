package com.example.uday.doctalktask;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by meruguabhishek on 2017-05-08.
 */

public class CommentsRecyclerAdapter extends RecyclerView.Adapter<CommentsRecyclerAdapter.ViewHolder> {

    private ArrayList<Comment> comments=new ArrayList<>();
    private Context context;


    public CommentsRecyclerAdapter(ArrayList<Comment> comments,  Context context) {
        this.comments = comments;
        this.context = context;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public CommentsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_list_item, parent, false);
        return new CommentsRecyclerAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(CommentsRecyclerAdapter.ViewHolder holder, final int position) {
        Comment comment=comments.get(position);
        holder.title.setText(comment.getBody());
        holder.subTitle.setText("commented by "+comment.getUser().getLogin());
        Glide.with(context).load(comment.getUser().getAvatarUrl()).into(holder.avatar);
//        holder.issueContainer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if (comments==null){
            return 0;
        }else {
            return comments.size();
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView subTitle;
        ImageView avatar;
        LinearLayout issueContainer;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.issue_title);
            subTitle = (TextView) v.findViewById(R.id.subtitle);
            avatar= (ImageView) v.findViewById(R.id.avatar);
            issueContainer= (LinearLayout) v.findViewById(R.id.issue_container);

        }
    }

}
