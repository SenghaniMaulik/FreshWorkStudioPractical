package com.demo.freshworkstudiopractical.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.demo.freshworkstudiopractical.R
import com.demo.freshworkstudiopractical.adapter.PagerAdapter
import com.demo.freshworkstudiopractical.databinding.ActivityMainBinding
import com.demo.freshworkstudiopractical.ui.fragments.GifListingFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    val tabIcons = intArrayOf(
        R.drawable.ic_baseline_home_24,
        R.drawable.ic_baseline_favorite_24,
    )
    lateinit var llHome: LinearLayout
    lateinit var llFavourite: LinearLayout


    lateinit var imgHome: ImageView
    lateinit var imgFavourite: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setView()
    }

    private fun setView() {
        setBottomMenu(tabIcons)
    }


    private fun setBottomMenu(tabIcons: IntArray) {
        binding.viewPager.offscreenPageLimit = tabIcons.size
        binding.viewPager.isUserInputEnabled = false
        setupPager()

        //Adding here custom tab because we can customize it as par our requirement
        val headerView =
            (this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                R.layout.custom_tab,
                null,
                false
            )

        llHome = headerView.findViewById(R.id.llHome)
        llFavourite = headerView.findViewById(R.id.llFavourite)

        imgHome = headerView.findViewById(R.id.imgHome)
        imgFavourite = headerView.findViewById(R.id.imgFavourite)

        // assigning tabs to our custom views
        binding.tabLayout.getTabAt(0)?.customView = llHome
        binding.tabLayout.getTabAt(1)?.customView = llFavourite

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                selectTab(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }


    // setting up tab layout and binding it with viewpager
    private fun setupPager() {
        val fragments = ArrayList<Fragment>()
        fragments.add(GifListingFragment())
        fragments.add(GifListingFragment())
        val resultBundle = Bundle()
        val pagerAdapter = PagerAdapter(
            resultBundle,
            fragments,
            this
        )
        binding.viewPager.apply {
            adapter = pagerAdapter
        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager, false, false) { tab, position ->
        }.attach()
    }


    private fun selectTab(position: Int) {
        imgHome.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_outline_home_24
            )
        )
        imgFavourite.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_baseline_favorite_border_24
            )
        )
        when (position) {
            0 -> {
                imgHome.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_baseline_home_24
                    )
                )
            }
            1 -> {
                imgFavourite.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_baseline_favorite_24
                    )
                )
            }
        }
    }
}