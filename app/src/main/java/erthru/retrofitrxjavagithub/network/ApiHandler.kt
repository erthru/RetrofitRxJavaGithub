package erthru.retrofitrxjavagithub.network

import erthru.retrofitrxjavagithub.network.model.UserResponse
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


class ApiHandler{

    companion object {

        const val GITHUB_BASE_URL = "https://api.github.com/"

    }

    private fun retrofit(baseUrl:String?) : Retrofit{

        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

    }

    fun instance(baseUrl:String?) : ApiInterface{
        return retrofit(baseUrl).create(ApiInterface::class.java)
    }

}

interface ApiInterface{

    @GET("users/{user}/repos")
    fun user(
            @Path("user")user:String?
    ) : Observable<List<UserResponse>>

}