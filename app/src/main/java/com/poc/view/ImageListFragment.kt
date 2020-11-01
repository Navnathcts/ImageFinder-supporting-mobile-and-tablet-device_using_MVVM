package com.poc.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.poc.*
import com.poc.data.remote.ApiClient
import com.poc.data.response.ImageItem
import com.poc.data.response.Status
import com.poc.utility.*
import com.poc.viewmodel.HomeViewModel
import com.poc.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.error_view.*
import kotlinx.android.synthetic.main.image_list_fragment_view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class ImageListFragment : Fragment(),
    ImagesListAdapter.ItemClickListener {
    private var isTablet = false
    private var navHostFragment: NavHostFragment? = null
    private var mView: View? = null
    private var homeViewModel: HomeViewModel? = null
    private var imageListAdapter: ImagesListAdapter? = null
    private var searchText: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isTablet = context?.resources?.getBoolean(R.bool.isTablet) ?: false
        ApiClient.apiService?.let {
            homeViewModel = ViewModelProviders.of(
                this@ImageListFragment,
                ViewModelFactory(it)
            ).get(HomeViewModel::class.java)
        }
        imageListAdapter = ImagesListAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mView = inflater.inflate(R.layout.image_list_fragment_view, container, false)
        when {
            isTablet -> {
                navHostFragment =
                    childFragmentManager.findFragmentById(R.id.item_detail_container) as NavHostFragment
            }
        }
        return mView
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnTryAgain?.setOnClickListener {
            getImageResult(etSearchInput?.text.toString())
        }
        etSearchInput?.textInputAsFlow()
            ?.map {
                return@map it.toString()
            }
            ?.debounce(AppConstants.DEBOUNCE_TIME) // delay to prevent searching immediately on every character input
            ?.onEach {
                if (!it.isBlank() && it != searchText) {
                    searchText = it
                    getImageResult(it)
                }
            }
            ?.launchIn(lifecycleScope)
    }

    /**
     * Fetches images based on search parameter.
     */
    private fun getImageResult(searchKeyword: String) {
        homeViewModel?.getImageByKeyword(searchKeyword)?.observe(requireActivity(), Observer {
            when (it.status) {
                Status.LOADING -> {
                    etSearchInput?.hideKeyboard(requireContext())
                    rvImageList?.hideView()
                    cvErrorView?.hideView()
                    pbLoader?.showView()
                }
                Status.SUCCESS -> {
                    rvImageList?.showView()
                    pbLoader?.hideView()
                    cvErrorView?.hideView()
                    imageListAdapter?.setImageList(it.data)
                }
                Status.ERROR -> {
                    etSearchInput?.hideKeyboard(requireContext())
                    pbLoader?.hideView()
                    rvImageList?.hideView()
                    cvErrorView?.showView()
                    txtErrorMsg.text = it.message
                }
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView(rvImageList)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = imageListAdapter
        recyclerView.layoutManager = GridLayoutManager(requireActivity(), 2)
    }

    override fun showLargeImage(imageItem: ImageItem) {
        val data = bundleOf(
            AppConstants.KEY_IMAGE_ITEM to imageItem,
            AppConstants.KEY_PAGE_TITLE to imageItem.title
        )
        when {
            isTablet -> {
                navHostFragment?.navController?.navigate(R.id.imageDetailFragment, data)
            }
            else -> {
                findNavController().navigate(
                    R.id.action_imageListFragment_to_imageDetailFragment,
                    data
                )
            }
        }
    }

}
