package com.example.uday.doctalktask;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by uday on 2017-04-29.
 */

public class IssuesRecyclerAdapter  extends RecyclerView.Adapter<IssuesRecyclerAdapter.ViewHolder> {

    private ArrayList<IssueResponse> issueResponses=new ArrayList<>();
    private Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView subTitle;
        ImageView avatar;


        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.issue_title);
            subTitle = (TextView) v.findViewById(R.id.subtitle);
            avatar= (ImageView) v.findViewById(R.id.avatar);
        }
    }

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
        IssueResponse issueResponse=issueResponses.get(position);
        holder.title.setText(issueResponse.getTitle());
        holder.subTitle.setText("#"+issueResponse.getNumber()+" created by "+issueResponse.getUser().getLogin());
        Glide.with(context).load(issueResponse.getUser().getAvatarUrl()).into(holder.avatar);
//        holder.movieDescription.setText(movies.get(position).getOverview());
//        holder.rating.setText(movies.get(position).getVoteAverage().toString());
    }

    @Override
    public int getItemCount() {
        if (issueResponses==null){
            return 0;
        }else {
            return issueResponses.size();
        }
    }
}
