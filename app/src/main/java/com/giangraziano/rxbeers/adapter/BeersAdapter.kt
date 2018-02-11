package com.giangraziano.rxbeers.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.giangraziano.rxbeers.R
import com.giangraziano.rxbeers.model.Data
import com.squareup.picasso.Picasso

/**
 * Created by giannig on 11/02/18.
 */

class BeersAdapter(private var list: MutableList<Data>) : RecyclerView.Adapter<BeersAdapter.BeersViewHolder>() {

    companion object {
        val DEFAULT_IMAGE = "http://blog.marcafermana.it/wp-content/uploads/2014/07/boccale-di-birra.png"
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BeersViewHolder {
        val ctx = parent?.context
        val inflater = LayoutInflater.from(ctx)
        val v = inflater.inflate(R.layout.beer_element_view, parent, false)

        return BeersViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BeersViewHolder?, position: Int) {
        val beer = list[position]

        holder?.setName(beer.name)
        if(beer.labels == null)
            holder?.setImage(DEFAULT_IMAGE)
        else
            holder?.setImage(beer.labels!!.medium)
    }

    class BeersViewHolder(private val view: View?) : RecyclerView.ViewHolder(view) {

        private val image by lazy {
            view?.findViewById<ImageView>(R.id.beer_element_image) as ImageView
        }

        private val nameText by lazy{
            view?.findViewById<TextView>(R.id.beer_name) as TextView
        }

        fun setName(beerName: String){
            nameText.text=beerName
            image.contentDescription = beerName
        }

        fun setImage(imageUrl: String?) {
            Picasso.with(view?.context)
                    .load(imageUrl ?: DEFAULT_IMAGE)
                    .into(image)
        }
    }
}