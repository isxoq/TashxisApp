package com.example.tashxis

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.viewpager.widget.PagerAdapter
import kotlinx.coroutines.withContext

class MyAdapter(var layouts: IntArray ,var context: Context): PagerAdapter() {

    private lateinit var inflater: LayoutInflater

    override fun getCount(): Int {
        return layouts.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(layouts[position] , container , false)
        container.addView(v)
        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val v = `object` as View
        container.removeView(v)
    }

}