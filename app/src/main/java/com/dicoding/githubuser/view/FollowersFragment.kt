package com.dicoding.githubuser.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.R
import com.dicoding.githubuser.databinding.FragmentFollowersBinding
import com.dicoding.githubuser.model.UserSearch
import com.dicoding.githubuser.viewmodel.FollowersViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FollowersFragment : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowersViewModel
    private lateinit var adapter: UserSearchAdapter

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        val username: String = args?.getString(FollowersFollowingActivity.EXTRA_NAME).toString()

        _binding = FragmentFollowersBinding.bind(view)

        adapter = UserSearchAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : UserSearchAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserSearch) {
                val UserDetailIntent = Intent(this@FollowersFragment.context, UserDetailActivity::class.java)
                UserDetailIntent.putExtra(UserDetailActivity.EXTRA_NAME, data.login)
                startActivity(UserDetailIntent)
            }
        })

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowersViewModel::class.java)

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@FollowersFragment.context)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter
        }

        viewModel.setFollowers(username)
        showLoading(true)

        viewModel.getFollowers().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FollowersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}