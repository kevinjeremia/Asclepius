package com.dicoding.asclepius.view.articles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.data.remote.response.ArticlesItem
import com.dicoding.asclepius.databinding.FragmentArticlesBinding
import com.dicoding.asclepius.view.adapter.ArticlesAdapter
import com.dicoding.asclepius.view.adapter.HeaderAdapter

class ArticlesFragment : Fragment() {
    private var _binding: FragmentArticlesBinding? = null
    private val binding get() = _binding!!

    private val articlesViewModel by activityViewModels<ArticlesViewModel>()

    private lateinit var articlesHeaderAdapter: HeaderAdapter
    private lateinit var articlesAdapter: ArticlesAdapter
    private lateinit var concatAdapter: ConcatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticlesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupHeaderRecyclerView()
        setupArticlesRecyclerView()
        concatAdapter = ConcatAdapter(articlesHeaderAdapter, articlesAdapter)
        setupWholeRecyclerView()

        articlesViewModel.news.observe(viewLifecycleOwner) { items ->
            concatAdapter.removeAdapter(articlesAdapter)
            setupArticlesRecyclerView(items)
            concatAdapter.addAdapter(articlesAdapter)
            concatAdapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupHeaderRecyclerView() {
        articlesHeaderAdapter = HeaderAdapter(HeaderAdapter.ARTICLES)
    }

    private fun setupArticlesRecyclerView(newsList: List<ArticlesItem>? = null) {
        articlesAdapter = ArticlesAdapter(ArticlesAdapter.ARTICLES, newsList)
    }

    private fun setupWholeRecyclerView() {
        binding.rvArticles.layoutManager = LinearLayoutManager(context)
        binding.rvArticles.adapter = concatAdapter
    }
}