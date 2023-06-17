package com.example.githubapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapplication.databinding.ActivityFavoriteBinding
import com.example.githubapplication.local.DatabaseModule
import com.example.githubapplication.local.FavoriteEntity

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel by viewModels<FavoriteViewModel>{
        FavoriteViewModel.FavoriteFactory(DatabaseModule(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvFavorite.addItemDecoration(itemDecoration)

        viewModel.getAllFavoriteUser()

        viewModel.favoriteUser.observe(this, {
            setFavoriteUser(it)
        })
    }

    override fun onResume() {
        super.onResume()

        viewModel.getAllFavoriteUser()
        viewModel.favoriteUser.observe(this, {
            setFavoriteUser(it)
        })
    }

    private fun setFavoriteUser(account: List<FavoriteEntity>) {
        val listUser = ArrayList<String>()
        val listAvatar = ArrayList<String>()
        for (acc in account){
            listUser.add(acc.login)
            listAvatar.add(acc.avatar_url)
        }
        val adapter = AccountAdapter(listUser, listAvatar)
        binding.rvFavorite.adapter = adapter

        adapter.setOnItemClickCallback(object : AccountAdapter.OnItemClickCallback{
            override fun onItemClicked(data: String) {
                val intentDetail = Intent(this@FavoriteActivity, DetailAccountActivity::class.java)
                intentDetail.putExtra(DetailAccountActivity.DATA, data)
                startActivity(intentDetail)
            }
        })
    }
}