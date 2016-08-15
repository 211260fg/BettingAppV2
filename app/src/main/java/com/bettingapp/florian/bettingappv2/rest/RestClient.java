package com.bettingapp.florian.bettingappv2.rest;

import com.bettingapp.florian.bettingappv2.model.Bet;
import com.bettingapp.florian.bettingappv2.model.User;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by floriangoeteyn on 22-Apr-16.
 */
public class RestClient {

    private static Retrofit client;

    public RestClient(String username, String password){
        client = createClient(username, password);
    }

    //creëer en return de clients
    public ItemApiInterface getItemClient(){
        return client.create(ItemApiInterface.class);
    }

    public UserApiInterface getUserClient(){
        return client.create(UserApiInterface.class);
    }

    //standaard retrofit client
    private Retrofit createClient(final String username, final String password){


        OkHttpClient okClient = new OkHttpClient();

        /*okClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                final String credentials = username + ":" + password;
                final String encodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                final String basic =
                        "Basic " + encodedCredentials;

                Request request = chain.request();
                request = request.newBuilder()
                        .addHeader("Authorization", basic)
                        .build();
                Response response = chain.proceed(request);
                return response;
            }
        });*/

        okClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());
                return response;
            }
        });


        return new Retrofit.Builder()
                .baseUrl(Values.URL_BASE)
                .addConverter(String.class, new ToStringConverter())
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public Call<List<Bet>> getBetCall(){
        return getItemClient().getBets();
    }

    public Call<List<User>> getUserCall(){
        return getUserClient().getUsers();
    }





    //Api calls naar elk json bestand
    public interface ItemApiInterface{
        @GET(Values.URL_BETS)
        Call<List<Bet>> getBets();

        @DELETE(Values.URL_BETS+"/{bet_id}")
        Call<ResponseBody> deleteBet(@Path("betid") String betid);

        @PUT(Values.URL_BETS+"/{bet_id}")
        Call<ResponseBody> putBet(@Path("betid") String betid);


    }

    public interface UserApiInterface{
        @GET(Values.URL_USERS)
        Call<List<User>> getUsers();

        @GET(Values.URL_USERS+"/{userid}")
        Call<User> getUserById(@Path("item_id") String userId);

    }

}
