package com.edna.android.push.demo_x.binding

import android.content.ClipData
import android.content.ClipboardManager
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.edna.android.push.demo_x.R
import com.edna.android.push.demo_x.data.dto.ButtonAction
import com.edna.android.push.demo_x.data.dto.Push
import com.edna.android.push.demo_x.pushdetail.ActionAdapter
import com.edna.android.push.demo_x.pushlist.PushAdapter


/**
 * [BindingAdapter]s for the [Push]s list.
 */
@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Push>?) {
    if (items != null && listView.adapter != null)
        (listView.adapter as PushAdapter).submitList(items)
}

/**
 * [BindingAdapter]s for the [ButtonAction]s list.
 */
@BindingAdapter("app:items")
fun setButtonActionItems(listView: RecyclerView, items: List<ButtonAction>?) {
    if (items != null && listView.adapter != null)
        (listView.adapter as ActionAdapter).submitList(items)
}

@BindingAdapter("setImageUrl", "setImagePlaceHolder", "setRoundingRadius", requireAll = false)
fun setImageUrl(imageView: ImageView?, url: String?, placeholderRes: Int?, roundingRadius: Int?) {
    if (imageView == null) return
    if (url.isNullOrEmpty() && placeholderRes == null) {
        imageView.setImageDrawable(null)
        return
    }

    Glide.with(imageView)
        .load(url)
        .apply {
            if (placeholderRes != null)
                placeholder(placeholderRes)
            if (roundingRadius != null) {
                transform(CenterCrop(), RoundedCorners(roundingRadius))
            }
        }
        .into(imageView)
}

@BindingAdapter("copyToBuffer")
fun copyToBuffer(textView: TextView?, copyFun: () -> String) = textView?.let {
    textView.setOnClickListener {
        val text = copyFun.invoke()
        val clipboard = getSystemService(it.context, ClipboardManager::class.java)
        val clip = ClipData.newPlainText("label", text)
        clipboard?.setPrimaryClip(clip)
        Toast.makeText(it.context, R.string.toast_copy_ok, Toast.LENGTH_LONG)
            .show()
    }
}

@BindingAdapter("setSrcRes", requireAll = false)
fun setSrcRes(view: ImageView?, res: Int?) {
    if (view != null && res != null && res != 0) {
        Glide.with(view)
            .load(res)
            .into(view)
    }
}

@BindingAdapter("setViewGone")
fun setViewGone(view: View?, isGone: Boolean?) {
    isGone?.let {
        view?.visibility = if (it) View.GONE else View.VISIBLE
    }
}