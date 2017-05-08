package com.example.uday.doctalktask;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by uday on 2017-04-29.
 */

public interface APIInterface {
    @GET("/repos/{owner}/{repo}/issues")
    Observable<ArrayList<IssueResponse>> getIssues(@Path("owner") String owner, @Path("repo") String repo);
    @GET("/repos/{owner}/{repo}/issues/{number}/comments")
    Observable<ArrayList<Comment>> getcomments(@Path("owner")String owner,@Path("repo") String repo,@Path("number")String number);

    @POST("/repos/{owner}/{repo}/issues/{number}/comments")
    Observable<Comment> postComment( @Path("owner")String owner, @Path("repo") String repo, @Path("number")String number, @Header("Authorization") String token,@Body HashMap<String, String> body);
}

