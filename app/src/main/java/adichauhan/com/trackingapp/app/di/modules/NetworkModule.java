package adichauhan.com.trackingapp.app.di.modules;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import java.io.File;
import java.io.IOException;

import adichauhan.com.trackingapp.BuildConfig;
import adichauhan.com.trackingapp.app.di.annotations.ApplicationScope;
import adichauhan.com.trackingapp.app.service.ApiService;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by adityachauhan on 23/06/18.
 *
 */

@Module(includes = ApplicationContextModule.class)
public class NetworkModule {

    @ApplicationScope
    @Provides
    HttpLoggingInterceptor httpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                if(BuildConfig.DEBUG) {
                    Log.d("Network",message);
                }
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @ApplicationScope
    @Provides
    Cache cache(File file) {
        return new Cache(file,10*1000*1000);
    }

    @ApplicationScope
    @Provides
    File file(Context context) {
        return new File(context.getCacheDir(),"okhttp_cache");
    }

    @ApplicationScope
    @Provides
    OkHttpClient okHttpClient(Cache cache,HttpLoggingInterceptor httpLoggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request.Builder builder = originalRequest.newBuilder();
                        String auth = "test/candidate:c00e-4764";
                        builder.addHeader("Authorization","Basic ".concat(
                                Base64.encodeToString(auth.getBytes(),Base64.DEFAULT)).trim());
                        return chain.proceed(builder.build());
                    }
                })
                .cache(cache)
                .build();
    }

    @Provides
    @ApplicationScope
    ApiService apiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    @ApplicationScope
    ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JodaModule());
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return objectMapper;
    }

    @Provides
    @ApplicationScope
    Retrofit retrofit(OkHttpClient okHttpClient,ObjectMapper objectMapper) {
        return new Retrofit.Builder()
                .baseUrl("https://locus-api.com/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

}
