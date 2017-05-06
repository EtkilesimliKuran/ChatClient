package smyy.qsearch.servis;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static smyy.qsearch.helper.Config.baseUrl;

/**
 * Created by figengungor on 24.09.2016.
 */
public class ServiceGenerator {


    public static Retrofit retrofit;

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl);

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
}
