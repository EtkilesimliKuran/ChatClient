package smyy.qsearch.servis;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static smyy.qsearch.helper.Config.baseUrl;
import static smyy.qsearch.helper.Config.botUrl;

/**
 * Created by figengungor on 24.09.2016.
 */
public class ServiceGenerator {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    public static Retrofit retrofit;

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl);

    private static Retrofit.Builder builder_bot =
            new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(botUrl);

    private static OkHttpClient getClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .build();
        return client;
    }
    public static <S> S createService(Class<S> serviceClass) {

        builder.client(getClient());
        retrofit = builder.build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createServiceBot(Class<S> serviceClass) {

        builder_bot.client(getClient());
        retrofit = builder_bot.build();
        return retrofit.create(serviceClass);
    }

    OkHttpClient client = new OkHttpClient();

    public static String post(String url, String json) throws IOException {
        OkHttpClient client =new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
