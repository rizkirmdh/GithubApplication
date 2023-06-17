package com.example.githubapplication

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubapplication.databinding.ActivityDetailAccountBinding
import com.example.githubapplication.local.DatabaseModule
import com.example.githubapplication.local.FavoriteEntity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailAccountBinding
    private val detailViewModel by viewModels<DetailViewModel>{
        DetailViewModel.DetailFactory(DatabaseModule(this))
    }

    private lateinit var favoriteEntity: FavoriteEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteEntity = FavoriteEntity(0, "", "")

        val username = intent?.getStringExtra(DATA) ?: ""

        username?.let {
            detailViewModel.getDetailAccount(username)
        }

        detailViewModel.detailAccount.observe(this, { detailAccount ->
            setDetailAccount(detailAccount)
            favoriteEntity = FavoriteEntity(detailAccount.id, detailAccount.login, detailAccount.avatarUrl)
        })

        detailViewModel.isLoading.observe(this, {
            showLoading(it)
        })

        detailViewModel.findFavoriteUser(username){
            binding.fabFavorite.changeIconColor(R.color.red)
        }

        detailViewModel.successFavorite.observe(this, {
            binding.fabFavorite.changeIconColor(R.color.red)
        })

        detailViewModel.deleteFavorite.observe(this, {
            binding.fabFavorite.changeIconColor(R.color.white)
        })

        binding.fabFavorite.setOnClickListener{
            detailViewModel.setFavorite(favoriteEntity)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, username)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

    private fun setDetailAccount(account: ItemsItem) {
        Glide.with(this@DetailAccountActivity)
            .load(account.avatarUrl)
            .into(binding.ivPp)

        val following = this@DetailAccountActivity.resources.getString(R.string.following, account.following)
        val followers = this@DetailAccountActivity.resources.getString(R.string.followers, account.followers)

        binding.tvUsername.text = account.login
        binding.tvName.text = account.name
        binding.tvFollowing.text = following
        binding.tvFollowers.text = followers
    }

    private fun showLoading(state: Boolean) { binding.progressBarDetail.visibility = if (state) View.VISIBLE else View.GONE }

    fun FloatingActionButton.changeIconColor(@ColorRes color: Int){
        imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this.context, color))
    }

    companion object{
        const val DATA = "data"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}