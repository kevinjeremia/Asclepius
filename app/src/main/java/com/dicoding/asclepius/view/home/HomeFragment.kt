package com.dicoding.asclepius.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.data.local.entity.ScansEntity
import com.dicoding.asclepius.data.remote.response.ArticlesItem
import com.dicoding.asclepius.databinding.FragmentHomeBinding
import com.dicoding.asclepius.view.adapter.ArticlesAdapter
import com.dicoding.asclepius.view.adapter.HeaderAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel by activityViewModels<HomeViewModel>{
        ViewModelFactory.getInstance(requireActivity().application)
    }

    private lateinit var navController: NavController
    private lateinit var homeHeaderAdapter: HeaderAdapter
    private lateinit var articlesAdapter: ArticlesAdapter
    private lateinit var concatAdapter: ConcatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        setupHeaderRecyclerView()
        setupArticlesRecyclerView()
        concatAdapter = ConcatAdapter(homeHeaderAdapter, articlesAdapter)
        setupWholeRecyclerView()

        homeViewModel.news.observe(viewLifecycleOwner) { items ->
            concatAdapter.removeAdapter(articlesAdapter)
            setupArticlesRecyclerView(items)
            concatAdapter.addAdapter(articlesAdapter)
            concatAdapter.notifyDataSetChanged()
        }

        homeViewModel.getRecentScans().observe(viewLifecycleOwner) { recent ->
            if ((homeViewModel.size.value ?: 0) > 0) {
                concatAdapter.removeAdapter(homeHeaderAdapter)
                concatAdapter.removeAdapter(articlesAdapter)
                setupHeaderRecyclerView(true, recent)
                concatAdapter.addAdapter(homeHeaderAdapter)
                concatAdapter.addAdapter(articlesAdapter)
                concatAdapter.notifyDataSetChanged()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupHeaderRecyclerView(anyRecent: Boolean = false, scansEntity: ScansEntity? = null) {
        homeHeaderAdapter = HeaderAdapter(HeaderAdapter.HOME, navController, anyRecent, scansEntity)
    }

    private fun setupArticlesRecyclerView(newsList: List<ArticlesItem>? = null) {
        articlesAdapter = ArticlesAdapter(ArticlesAdapter.HOME, newsList)
    }

    private fun setupWholeRecyclerView() {
        binding.rvHome.layoutManager = LinearLayoutManager(context)
        binding.rvHome.adapter = concatAdapter
    }

}