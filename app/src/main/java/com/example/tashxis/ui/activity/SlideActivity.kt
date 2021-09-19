package com.example.tashxis.ui.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.example.tashxis.MyAdapter
import com.example.tashxis.R
import com.example.tashxis.databinding.ActivitySlideBinding
import com.example.tashxis.ui.auth.RegisterActivity

class SlideActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySlideBinding
    private lateinit var myAdapter: MyAdapter
    private lateinit var dotsTv: Array<TextView?>
    private lateinit var layouts: IntArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySlideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!isFirstTimeAppStart()){
            setAppStartStatus(true)
            startActivity(Intent(this , RegisterActivity::class.java))
            finish()
        }

        statusBarTransparent()

        binding.btnNext.setOnClickListener{
            val currentPage: Int = binding.viewPager.currentItem + 1

            if (currentPage < layouts.size){
                binding.viewPager.currentItem = currentPage
            }else{
                setAppStartStatus(true)
                startActivity(Intent(this , RegisterActivity::class.java))
                finish()
            }
        }

        layouts = intArrayOf(R.layout.slide_1 , R.layout.slide_2)
        myAdapter = MyAdapter(layouts , applicationContext)
        binding.viewPager.adapter = myAdapter
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{

            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                //
            }

        })
        setDots(0)
    }

    private fun isFirstTimeAppStart(): Boolean{
        val pref = applicationContext.getSharedPreferences("SLIDER_APP" , Context.MODE_PRIVATE)
        return pref.getBoolean("APP_START" , true)
    }

    private fun setAppStartStatus(status: Boolean){
        val pref = applicationContext.getSharedPreferences("SLIDER_APP" , Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putBoolean("APP_START" , status)
        editor.apply()
    }

    private fun statusBarTransparent() {
        if (Build.VERSION.SDK_INT >= 21){
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    private fun setDots(page: Int){
        binding.dotsLayout.removeAllViews()
        dotsTv = arrayOfNulls(layouts.size)

        for (i in dotsTv.indices){
            dotsTv[i] = TextView(this)
            dotsTv[i]!!.text = Html.fromHtml("&#8226;")
            dotsTv[i]!!.textSize = 30f
            dotsTv[i]!!.setTextColor(Color.parseColor("#a9b4bb"))
            binding.dotsLayout.addView(dotsTv[i])
        }

        if (dotsTv.isNotEmpty()){
            dotsTv[page]!!.setTextColor(Color.parseColor("#ffffff"))
        }
    }

}