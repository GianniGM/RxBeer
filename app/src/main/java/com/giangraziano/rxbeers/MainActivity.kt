package com.giangraziano.rxbeers

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.giangraziano.rxbeers.adapter.BeersAdapter
import com.giangraziano.rxbeers.common.NetworkConfig
import com.giangraziano.rxbeers.common.setColumnsLayout
import com.giangraziano.rxbeers.network.MyService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private val recyclerView: RecyclerView by lazy {
        beers_list.setColumnsLayout(this)
        beers_list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setApi()
    }

    private fun setApi() {
        Retrofit.Builder()
                .baseUrl(NetworkConfig.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(MyService::class.java)

                .getBeers(NetworkConfig.apiKey, "json", 4)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { result -> recyclerView.adapter = BeersAdapter(result.data) },
                        { error -> showToast(error) }
                )
    }

    private fun showToast(error: Throwable) {
        Log.e(TAG, error.localizedMessage)
        Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
    }
}
