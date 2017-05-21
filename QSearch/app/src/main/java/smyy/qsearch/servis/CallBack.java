package smyy.qsearch.servis;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smyy.qsearch.helper.Config;
import smyy.qsearch.interfaces.RetroInterface;
import smyy.qsearch.model.BotMessage;
import smyy.qsearch.model.Register;
import smyy.qsearch.model.ResultBot;

/**
 * Created by Sümeyye on 1.5.2017.
 */

public class CallBack {

    RetroInterface retroInterface;
    RetroInterface retroInterfaceBot;
    Context context;

    public CallBack(Context context) {
        retroInterface = ServiceGenerator.createService(RetroInterface.class);

        this.context = context;
    }

    public void Register(final String regID) {
        Call<Register> call;
        call = retroInterface.Register(regID);
        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                Register register = response.body();
                if (register.getSuccess()) {
                    SharedPreferences pref = context.getSharedPreferences(Config.SHARED_PREF, 0);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("userid", register.getUserid());
                    editor.commit();
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });

    }

    public void UpdateChoice(final String userid, final List<String> user_choice) {
        Call<Register> call;
        call = retroInterface.UpdateChoice(userid, user_choice);

        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                Register register = response.body();
                Toast.makeText(context, register.getMessage(), Toast.LENGTH_SHORT);

            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }

    public void GetNotification(final String userid) {
        Call<Register> call;
        call = retroInterface.GetNotification(userid);

        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }

    public void sendMessage(final String text) {
        String recipientId = "984941DA1767E119E6184E6E56E566E0";
        String token = "57ed2268e4b081dd2c1bd8a7";
        String response=null;
        String json_data = "{\n" +
                "            \"recipientId\": \"984941DA1767E119E6184E6E56E566E0\",\n" +
                "            \"token\": \"590c6ed1e4b00f3ba094f56c\",\n" +
                "            \"text\": \"merhaba\"\n" +
                "        }";
        try {
            response = ServiceGenerator.post("http://dahi.ai/category/sendMessage", json_data);
            response.length();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
