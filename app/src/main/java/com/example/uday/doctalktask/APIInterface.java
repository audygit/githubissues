package com.example.uday.doctalktask;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by uday on 2017-04-29.
 */

public interface APIInterface {
    @GET("/repos/{owner}/{repo}/issues")
    Observable<ArrayList<IssueResponse>> getIssues(@Path("owner") String owner, @Path("repo") String repo);
}

