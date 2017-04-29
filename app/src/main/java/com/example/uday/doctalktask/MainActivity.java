package com.example.uday.doctalktask;

import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.Subject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private IssuesRecyclerAdapter issuesRecyclerAdapter;
    private ArrayList<IssueResponse> issueResponsesList=new ArrayList<>();
    private ImageButton searchBtn;
    private EditText searchFld;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView= (RecyclerView) findViewById(R.id.issue_list);
        issuesRecyclerAdapter=new IssuesRecyclerAdapter(issueResponsesList,MainActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(issuesRecyclerAdapter);
        searchBtn=(ImageButton) findViewById(R.id.searchBtn);
        searchFld= (EditText) findViewById(R.id.searchFld);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key=searchFld.getText().toString();
                if (key!=null&&!key.isEmpty()) {
                    try {
                       String x= key.split("/")[0] ;
                        String y=key.split("/")[1];
                        search(x,y);
                    }catch (Exception ex){
                        Toast.makeText(MainActivity.this,"No repos found",Toast.LENGTH_LONG).show();
                        issueResponsesList.clear();
                        issuesRecyclerAdapter.setIssueResponses(issueResponsesList);
                        issuesRecyclerAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

    }
    private void search(String owner,String repo){
        APIInterface apiService =
                APIClient.getClient().create(APIInterface.class);
        Observable<ArrayList<IssueResponse>> london = apiService.getIssues(owner,repo);
        london
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subject<ArrayList<IssueResponse>>() {
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
                    protected void subscribeActual(Observer<? super ArrayList<IssueResponse>> observer) {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArrayList<IssueResponse> issueResponses) {
                        issueResponsesList=issueResponses;
                        issuesRecyclerAdapter.setIssueResponses(issueResponses);
                        issuesRecyclerAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this,"No repos found",Toast.LENGTH_LONG).show();
                        issueResponsesList.clear();
                        issuesRecyclerAdapter.setIssueResponses(issueResponsesList);
                        issuesRecyclerAdapter.notifyDataSetChanged();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
