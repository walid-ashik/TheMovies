package com.skydoves.themovies.view.viewholder

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.skydoves.themovies.api.Api
import com.skydoves.themovies.models.Movie
import kotlinx.android.synthetic.main.item_poster.view.*

/**
 * Developed by skydoves on 2018-08-12.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class MovieListViewHolder(view: View, private val delegate: Delegate): BaseViewHolder(view) {

    interface Delegate {
        fun onItemClick(movie: Movie)
    }

    private lateinit var movie: Movie

    @Throws(Exception::class)
    override fun bindData(data: Any) {
        if(data is Movie) {
            movie = data
            drawItem()
        }
    }

    private fun drawItem() {
        itemView.run {
            item_poster_title.text = movie.title
            movie.poster_path?.let {
                Glide.with(context)
                        .load(Api.getPosterPath(it))
                        .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                        .listener(GlidePalette.with(Api.getPosterPath(it))
                                .use(BitmapPalette.Profile.VIBRANT)
                                .intoBackground(item_poster_palette)
                                .crossfade(true))
                        .into(item_poster_post)
            }
        }
    }

    override fun onClick(v: View?) {
        delegate.onItemClick(movie)
    }

    override fun onLongClick(v: View?) = false
}