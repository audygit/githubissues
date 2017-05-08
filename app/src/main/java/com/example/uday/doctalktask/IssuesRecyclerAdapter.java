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
 * Created by uday on 2017-04-29.
 */

public class IssuesRecyclerAdapter  extends RecyclerView.Adapter<IssuesRecyclerAdapter.ViewHolder> {

    private ArrayList<IssueResponse> issueResponses=new ArrayList<>();
    private Context context;
    private String owner;
    private String repo;


    public IssuesRecyclerAdapter(ArrayList<IssueResponse> issueResponses,  Context context) {
        this.issueResponses = issueResponses;
        this.context = context;
    }

    public void setIssueResponses(ArrayList<IssueResponse> issueResponses) {
        this.issueResponses = issueResponses;
    }

    @Override
    public IssuesRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final IssueResponse issueResponse=issueResponses.get(position);
        holder.title.setText(issueResponse.getTitle());
        holder.subTitle.setText("#"+issueResponse.getNumber()+" created by "+issueResponse.getUser().getLogin());
        Glide.with(context).load(issueResponse.getUser().getAvatarUrl()).into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        if (issueResponses==null){
            return 0;
        }else {
            return issueResponses.size();
        }
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
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
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IssueResponse issueResponse=issueResponses.get(getAdapterPosition());
                    Intent intent=new Intent(context,CommentsActivity.class);
                    intent.putExtra("issue",issueResponse);
                    intent.putExtra("owner",owner);
                    intent.putExtra("repo",repo);
                    context.startActivity(intent);
                }
            });
        }
    }

}
