package com.poc.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.poc.utility.AppConstants
import com.poc.R
import com.poc.data.response.ImageItem
import com.poc.utility.loadImage
import com.poc.utility.showView
import kotlinx.android.synthetic.main.image_detail_fragment_view.*


class ImageDetailFragment : Fragment() {

    private var imageItem: ImageItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageItem = arguments?.getSerializable(AppConstants.KEY_IMAGE_ITEM) as? ImageItem
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.image_detail_fragment_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imgItem.showView()
        imgItem?.apply {
            showView()
            loadImage(context= requireActivity(),link =  imageItem?.link, errorResId = R.drawable.ic_error, placeHolderRes = R.drawable.ic_launcher_background, pbImageLoader = pbImageLoader)
        }
    }
}
