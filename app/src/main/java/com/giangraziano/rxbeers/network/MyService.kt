package com.giangraziano.rxbeers.network

import com.giangraziano.rxbeers.model.Beer
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by giannig on 11/02/18.
 */
interface MyService {

    @GET("v2/beers")
    fun getBeersByAbv(@Query("key") key: String, @Query("format") format: String, @Query("abv") abv: Int): Observable<Beer>

    @GET("v2/beers")
    fun getBeersByName(@Query("key") key: String, @Query("format") format: String, @Query("name") name: String): Observable<Beer>

}