package uz.tashxis.client.presentation.ui.activity

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.viewpager.widget.ViewPager
import uz.tashxis.client.R
import uz.tashxis.client.databinding.ActivitySlideBinding
import uz.tashxis.client.presentation.adapters.MyAdapter

class SlideActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySlideBinding
    private lateinit var myAdapter: MyAdapter
    private lateinit var dotsTv: Array<TextView?>
    private lateinit var layouts: IntArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySlideBinding.inflate(layoutInflater)
        setContentView(binding.root)
        statusBarTransparent()

        binding.btnNext.setOnClickListener {
            requestLocationPermissions.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            val currentPage: Int = binding.viewPager.currentItem + 1

            if (currentPage < layouts.size) {
                binding.viewPager.currentItem = currentPage
            } else {
                binding.btnNext.isEnabled = false
            }
        }

        layouts = intArrayOf(R.layout.slide_1, R.layout.slide_2)
        myAdapter = MyAdapter(layouts, applicationContext)
        binding.viewPager.adapter = myAdapter
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                binding.btnNext.isVisible = position == 1
            }

            override fun onPageSelected(position: Int) {
                setDots(position)
            }

        })
        setDots(0)
    }

    private fun statusBarTransparent() {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
    }

    private fun setDots(page: Int) {
        binding.dotsLayout.removeAllViews()
        dotsTv = arrayOfNulls(layouts.size)

        for (i in dotsTv.indices) {
            dotsTv[i] = TextView(this)
            dotsTv[i]?.let {
                it.text = HtmlCompat.fromHtml("&#8226;", HtmlCompat.FROM_HTML_MODE_LEGACY)
                it.textSize = 30f
                it.setTextColor(Color.parseColor("#a9b4bb"))
                binding.dotsLayout.addView(it)
            }
        }

        if (dotsTv.isNotEmpty()) {
            dotsTv[page]?.setTextColor(Color.parseColor("#ffffff"))
        }
    }

    private val requestLocationPermissions =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(this, "You gave that fucking permission", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, RegisterActivity::class.java))
                finish()
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (this.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", this.packageName, null)
                        intent.data = uri
                        startActivity(intent)
                    }
                }
                Toast.makeText(this, "You must give permission on something", Toast.LENGTH_SHORT)
                    .show()
            }
        }


}