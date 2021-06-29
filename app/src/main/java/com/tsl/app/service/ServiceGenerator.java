package com.tsl.app.service;


import android.util.Base64;
import android.util.Log;


import com.tsl.app.BuildConfig;
import com.tsl.app.util.GsonUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Main service generator, it will create a service and keep in lookup table
 */
public class ServiceGenerator {

    private static String TAG = ServiceGenerator.class.getName();

    private static ServiceGenerator instance = null;

    public ServiceGenerator getInstance() {
        instance = (instance == null) ? new ServiceGenerator() : instance;
        return instance;
    }

    public static final String API_BASE_URL = BuildConfig.API_SERVER_URL;
    private static String API_USER;
    private static String API_PASSWORD;

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    /**
     * Duplicate client, it is without Authorization Header
     */
    private static OkHttpClient.Builder httpClient2 = new OkHttpClient.Builder()
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));

    /**
     * Retrofit builder to build service instance
     */
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(GsonUtil.getGson()));


    /**
     * Service without Header Authorization token
     *
     * @param serviceClass
     * @param <S>
     * @return
     */
    private static <S> S createServiceNoHeader(Class<S> serviceClass) {

        Retrofit retrofit = builder.client(httpClient2.build()).build();

        return retrofit.create(serviceClass);
    }


    /**
     * Service generator with basic authentication
     *
     * @param serviceClass
     * @param <S>
     * @return
     */

    private static <S> S createService(Class<S> serviceClass) {

        setHeaderInterceptor();

        Retrofit retrofit = builder.client(httpClient.build()).build();

        return retrofit.create(serviceClass);
    }

    /**
     * Check service is in table, if exist then return existing service otherwise generate
     * the service and store in table
     *
     * @param className
     * @param <S>
     * @return Service
     */

    public static <S> S getService(Class<S> className) {
        // create a new instance and keep it in table
        return createService(className);
    }

    /**
     * Generate service, it will not check the lookup table, Always return a new service instance.
     *
     * @param className
     * @param includeAuth if yes, than include basic authorization otherwise create empty header
     * @param <S>
     * @return
     */

    public static <S> S getProxyService(Class<S> className, boolean includeAuth) {
        return includeAuth ? getService(className) : createServiceNoHeader(className);
    }

    public static com.tsl.app.service.Response getErrorResponse(ResponseBody response) {
        com.tsl.app.service.Response data = new com.tsl.app.service.Response();
        String err = "";

        try {
            if (response != null) {
                err = response.string();
            } else {
                err = "No response found!";
            }
            // 1. Parse our error response
            data = GsonUtil.fromJson(err, com.tsl.app.service.Response.class);



        } catch (Exception e) {
            // 2. try for InternalServerError, which can't be parsed in Response

            String internalServerError = GsonUtil.getJsonValue(err, "error").toString();
            data.getError().setDetail(internalServerError);

            Log.e(TAG, e.getMessage());
        }

        return data;
    }

    public static String getApiUser() {
        return API_USER;
    }

    public static void setApiUser(String apiUser) {
        API_USER = apiUser;
    }

    public static String getApiPassword() {
        return API_PASSWORD;
    }

    public static void setApiPassword(String apiPassword) {
        API_PASSWORD = apiPassword;
    }

    public static void setHeaderInterceptor() {

//        if (UfrilApp.getUserProfile() != null && httpClient.interceptors().isEmpty()) {
//            setHeaderInterceptor(UfrilApp.getUserProfile().getUsername(),
//                    UfrilApp.getUserProfile().getPassword());
//        }
    }

    public static void setHeaderInterceptor(String username, String password) {

        setApiUser(username);
        setApiPassword(password);

        String credentials = String.format("%s:%s", username, password);
        final String basicAuthToken = String.format("Basic %s",
                Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP));
        //"Basic YWRtaW46YWRtaW4xMjM=";

        Interceptor headerAuth = new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization", basicAuthToken)
                        .header("Accept", "application/json")
                        .method(original.method(), original.body());

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };


        httpClient.interceptors().clear();
        httpClient.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC));
        httpClient.addInterceptor(headerAuth);
    }


}
