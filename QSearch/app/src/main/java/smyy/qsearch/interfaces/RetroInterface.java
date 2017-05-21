package smyy.qsearch.interfaces;

import java.util.List;

import okhttp3.MediaType;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import smyy.qsearch.model.BotMessage;
import smyy.qsearch.model.Register;
import smyy.qsearch.model.ResultBot;

/**
 * Created by Sümeyye on 9.4.2017.
 */

public interface RetroInterface {

    @FormUrlEncoded
    @POST("/register")
    Call<Register> Register(@Field("regID") String regID);

    @FormUrlEncoded
    @POST("/updatechoice")
    Call<Register> UpdateChoice(@Field("userid") String userid, @Field("user_choice") List<String> user_choice);

    @FormUrlEncoded
    @POST("/getNotification")
    Call<Register> GetNotification(@Field("userid") String userid);

    @POST("/category/sendMessage")
    Call<ResultBot> sendMessage(@Body BotMessage botMessage);

}
