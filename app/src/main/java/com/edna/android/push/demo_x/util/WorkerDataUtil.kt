package com.edna.android.push.demo_x.util

import android.os.Parcel
import android.os.Parcelable
import androidx.work.Data

fun Data.Builder.putMap(key: String, map: Map<String, String?>): Data.Builder {
    putStringArray(key, map.keys.toTypedArray())
    for ((key1, value) in map.entries) {
        putString(key1, value)
    }
    return this
}

@Suppress("UNCHECKED_CAST")
fun Data.getMap(key: String): Map<String, String?> {
    val map = mutableMapOf<String, String?>()
    getStringArray(key)?.forEach {
        map[it] = getString(it)
    }
    return map
}

fun Data.Builder.putParcelable(key: String, parcelable: Parcelable): Data.Builder {
    val parcel = Parcel.obtain()
    try {
        parcelable.writeToParcel(parcel, 0)
        putByteArray(key, parcel.marshall())
    } finally {
        parcel.recycle()
    }
    return this
}

fun Data.Builder.putParcelableList(key: String, list: List<Parcelable>): Data.Builder {
    list.forEachIndexed { i, item ->
        putParcelable("$key$i", item)
    }
    return this
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Parcelable> Data.getParcelable(key: String): T? {
    val parcel = Parcel.obtain()
    try {
        val bytes = getByteArray(key) ?: return null
        parcel.unmarshall(bytes, 0, bytes.size)
        parcel.setDataPosition(0)
        val creator = T::class.java.getField("CREATOR").get(null) as Parcelable.Creator<T>
        return creator.createFromParcel(parcel)
    } finally {
        parcel.recycle()
    }
}

inline fun <reified T : Parcelable> Data.getParcelableList(key: String): MutableList<T> {
    val list = mutableListOf<T>()
    with(keyValueMap) {
        while (containsKey("$key${list.size}")) {
            list.add(getParcelable<T>("$key${list.size}") ?: break)
        }
    }
    return list
}
