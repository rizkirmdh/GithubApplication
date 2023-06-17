package com.example.githubapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapplication.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {

    companion object{
        const val ARG_POSITION = "arg_position"
        const val ARG_USERNAME = "username"
    }

    private lateinit var followViewModel: FollowViewModel
    private lateinit var binding: FragmentFollowBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followViewModel = ViewModelProvider(this).get(FollowViewModel::class.java)
        var position = 0
        var username = ""

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollow.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvFollow.addItemDecoration(itemDecoration)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME).toString()
        }

        if (position == 1){
            followViewModel.getFollowersAccount(username)
            followViewModel.followers.observe(viewLifecycleOwner){
                setFollowers(it)
            }
        } else{
            followViewModel.getFollowingAccount(username)
            followViewModel.following.observe(viewLifecycleOwner){
                setFollowing(it)
            }
        }

        followViewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })
    }

    private fun setFollowers(account: List<ItemsItem>) {
        val listUser = ArrayList<String>()
        val listAvatar = ArrayList<String>()
        for (acc in account){
            listUser.add(acc.login)
            listAvatar.add(acc.avatarUrl)
        }
        val adapter = FollowAdapter(listUser, listAvatar)
        binding.rvFollow.adapter = adapter
    }

    private fun setFollowing(account: List<ItemsItem>) {
        val listUser = ArrayList<String>()
        val listAvatar = ArrayList<String>()
        for (acc in account){
            listUser.add(acc.login)
            listAvatar.add(acc.avatarUrl)
        }
        val adapter = FollowAdapter(listUser, listAvatar)
        binding.rvFollow.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarFollow.visibility = View.VISIBLE
        } else {
            binding.progressBarFollow.visibility = View.GONE
        }
    }

}