package com.example.uday.doctalktask;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.Subject;

public class CommentsActivity extends AppCompatActivity {

    private RecyclerView commentsListView;
    private ArrayList<Comment> comments=new ArrayList<>();
    private CommentsRecyclerAdapter commentsRecyclerAdapter;
    private IssueResponse issueResponse;
    private LinearLayoutManager layoutManager;
    private String owner;
    private String repo;
    private ImageButton send;
    private EditText commentFld;
    private TextView noComments;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        issueResponse= (IssueResponse) getIntent().getSerializableExtra("issue");
        owner=getIntent().getStringExtra("owner");
        repo=getIntent().getStringExtra("repo");
        findViews();
        progressBar.setVisibility(View.VISIBLE);
        getComments();
    }
    private void findViews(){
        send= (ImageButton) findViewById(R.id.send);
        noComments= (TextView) findViewById(R.id.noComments);
        progressBar= (ProgressBar) findViewById(R.id.progressBar);
        commentFld= (EditText) findViewById(R.id.addComment);
        commentsListView= (RecyclerView) findViewById(R.id.comments_list);
        commentsRecyclerAdapter=new CommentsRecyclerAdapter(comments,CommentsActivity.this);
        layoutManager=new LinearLayoutManager(CommentsActivity.this);
        commentsListView.setLayoutManager(layoutManager);
        commentsListView.setAdapter(commentsRecyclerAdapter);
        commentFld.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String commentText=s.toString();
                if (!commentText.isEmpty()){
                    send.setVisibility(View.VISIBLE);
                }else {
                    send.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String commentText=commentFld.getText().toString();
              postComment(commentText);
            }
        });
    }
    private void postComment(String commentText){
        progressBar.setVisibility(View.VISIBLE);
        hideKeyBoard();
        HashMap<String,String> body=new HashMap<>();
        commentFld.getText().clear();
        body.put("body",commentText);
        APIInterface apiService =
                APIClient.getClient().create(APIInterface.class);
        Observable<Comment> london = apiService.postComment(owner,repo, String.valueOf(issueResponse.getNumber()),"token 3bcce34e1989077390802196f8903391c4002832",body);
        london
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subject<Comment>() {
                    @Override
                    protected void subscribeActual(Observer<? super Comment> observer) {

                    }

                    @Override
                    public boolean hasObservers() {
                        return false;
                    }

                    @Override
                    public boolean hasThrowable() {
                        return false;
                    }

                    @Override
                    public boolean hasComplete() {
                        return false;
                    }

                    @Override
                    public Throwable getThrowable() {
                        return null;
                    }



                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Comment comment) {
                        comments.add(comment);
                        commentsRecyclerAdapter.setComments(comments);
                        commentsRecyclerAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        if (comments.size()==0){
                            noComments.setVisibility(View.VISIBLE);
                        }else {
                            layoutManager.scrollToPositionWithOffset(comments.size()-1, 0);
                            noComments.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(CommentsActivity.this,"Error posting the comment",Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    private void hideKeyBoard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void getComments(){
        APIInterface apiService =
                APIClient.getClient().create(APIInterface.class);
        Observable<ArrayList<Comment>> london = apiService.getcomments(owner,repo, String.valueOf(issueResponse.getNumber()));
        london
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subject<ArrayList<Comment>>() {
                    @Override
                    public boolean hasObservers() {
                        return false;
                    }

                    @Override
                    public boolean hasThrowable() {
                        return false;
                    }

                    @Override
                    public boolean hasComplete() {
                        return false;
                    }

                    @Override
                    public Throwable getThrowable() {
                        return null;
                    }

                    @Override
                    protected void subscribeActual(Observer<? super ArrayList<Comment>> observer) {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArrayList<Comment> commentsList) {
                        comments=commentsList;
                        commentsRecyclerAdapter.setComments(comments);
                        commentsRecyclerAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        if (comments.size()==0){
                            noComments.setVisibility(View.VISIBLE);
                        }else {
                            noComments.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(CommentsActivity.this,"No comments found",Toast.LENGTH_LONG).show();
                        comments.clear();
                        progressBar.setVisibility(View.GONE);
                        commentsRecyclerAdapter.setComments(comments);
                        commentsRecyclerAdapter.notifyDataSetChanged();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
