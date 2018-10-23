package com.app.knowit.ui.view

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.view.View
import com.app.knowit.R
import com.app.knowit.data.Info
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fact_item_view.view.*

/**
 * Represents individual items in [FactAdapter]
 */
class FactItemView(context: Context) : ConstraintLayout(context) {
    init {
        layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        View.inflate(context, R.layout.fact_item_view, this)
    }

    fun bind(info: Info) {
        headerText.text = info.title
        if (info.description != null) {
            contentText.text = info.description
            contentText.visibility = View.VISIBLE
        } else {
            contentText.visibility = View.GONE
        }

        if (info.imageHref != null) {
            Glide.with(context)
                .load(info.imageHref)
                .into(image)
            image.visibility = View.VISIBLE
        } else {
            image.visibility = View.GONE
        }
    }
}