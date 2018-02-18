package com.giangraziano.rxbeers.utils

import android.content.Context
import android.widget.Toast
import com.giangraziano.rxbeers.common.NetworkData
import com.giangraziano.rxbeers.model.Beer
import com.giangraziano.rxbeers.network.MyService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by giannig on 18/02/18.
 */

fun showToast(ctx: Context, string: String) {
    Toast.makeText(ctx, string, Toast.LENGTH_SHORT).show()
}

fun callService(abv: Int): Observable<Beer> {
    return Retrofit.Builder()
            .baseUrl(NetworkData.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(MyService::class.java)

            .getBeersByAbv(NetworkData.apiKey, "json", abv)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
}