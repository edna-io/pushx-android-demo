package com.edna.android.push.demo_x.login

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckedTextView

class IdentifierTypeAdapter(context: Context) :
    ArrayAdapter<LoginDialog.IdentifierType?>(context, 0, LoginDialog.IdentifierType.values()) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val text = if (convertView == null) {
            LayoutInflater.from(context)
                .inflate(android.R.layout.simple_spinner_dropdown_item, parent, false) as CheckedTextView
        } else convertView as CheckedTextView

        text.setText(getItem(position)?.resource ?: 0)
        return text
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val text = if (convertView == null) {
            LayoutInflater.from(context)
                .inflate(android.R.layout.simple_spinner_dropdown_item, parent, false) as CheckedTextView
        } else convertView as CheckedTextView
        text.setText(getItem(position)?.resource ?: 0)
        return text
    }
}