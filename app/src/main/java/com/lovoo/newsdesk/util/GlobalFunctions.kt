package com.lovoo.newsdesk.util

import android.app.Application
import android.content.Context
import android.util.Log
import org.xmlpull.v1.XmlPullParser


/**
 * Created by Arash on 9/19/2018.
 */
class GlobalFunctions {
    companion object {

        fun getHashMapResource(c: Context, hashMapResId: Int): Map<String, String>? {
            var map: MutableMap<String, String>? = null
            val parser = c.resources.getXml(hashMapResId)

            var key: String? = null
            var value: String? = null

            try {
                var eventType = parser.eventType

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_DOCUMENT) {
                        Log.d("utils", "Start document")
                    } else if (eventType == XmlPullParser.START_TAG) {
                        if (parser.name == "map") {
                            val isLinked = parser.getAttributeBooleanValue(null, "linked", false)

                            map = if (isLinked) LinkedHashMap() else HashMap()
                        } else if (parser.name == "entry") {
                            key = parser.getAttributeValue(null, "key")

                            if (null == key) {
                                parser.close()
                                return null
                            }
                        }
                    } else if (eventType == XmlPullParser.END_TAG) {
                        if (parser.name == "entry") {
                            value?.let { key?.let { it1 -> map!!.put(it1, it) } }
                            key = null
                            value = null
                        }
                    } else if (eventType == XmlPullParser.TEXT) {
                        if (null != key) {
                            value = parser.text
                        }
                    }
                    eventType = parser.next()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }

            return map
        }

        fun getDrawableResId(context: Application, name:String): Int {
            return context.resources.getIdentifier(name, "drawable", context.applicationContext.packageName)
        }
    }
}