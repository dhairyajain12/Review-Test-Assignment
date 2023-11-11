package com.dhairyajain_reviewtestassignment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.dhairyajain_reviewtestassignment.databinding.FragmentProductDetailsBinding

class ProductDetailsFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: FragmentProductDetailsBinding
    private val _binding get() = binding
    private lateinit var quantitySpinner: Spinner
    private var productID: Int = 0
    private var productTitle: String = ""
    private var productPrice: Double = 0.0
    private var productDescription: String = ""
    private var productCategory: String = ""
    private var productImage: String = ""
    private var productRating: Double = 0.0
    private var productReviewCount: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        productID = arguments?.getInt("productID")!!
        productTitle = arguments?.getString("productTitle")!!
        productPrice = arguments?.getDouble("productPrice")!!
        productDescription = arguments?.getString("productDescription")!!
        productCategory = arguments?.getString("productCategory")!!
        productImage = arguments?.getString("productImage")!!
        productRating = arguments?.getDouble("productRating")!!
        productReviewCount = arguments?.getInt("productReviewCount")!!
        quantitySpinner = _binding.productQuantitySpinner
        return _binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ArrayAdapter.createFromResource(requireContext(), R.array.quantity_array, android.R.layout.simple_spinner_item).also { 
            adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            quantitySpinner.adapter = adapter
        }
        quantitySpinner.onItemSelectedListener = this
        val quantity = quantitySpinner.selectedItem.toString()
        binding.tvProductName.text = productTitle
        binding.tvProductDescription.text = productDescription
        binding.tvReviewCount.text = buildString {
            append("Reviews: ")
            append(productReviewCount)
        }
        binding.tvProductPrice.text = buildString {
            append("Price: $")
            append(productPrice)
        }
        binding.tvProductRating.text = buildString {
            append("Ratings: ")
            append(productRating)
            append(" / ")
            append("10")
        }
        Glide.with(requireContext()).load(productImage).into(binding.productImage)
        
        binding.btnAddToCart.setOnClickListener {
            val cartFragment = CartFragment()
            val bundle = Bundle()
            bundle.putString("productTitle", productTitle)
            bundle.putDouble("productPrice", productPrice)
            bundle.putDouble("productRating", productRating)
            bundle.putString("productDescription", productDescription)
            bundle.putString("productImage", productImage)
            bundle.putInt("productReviewCount", productReviewCount)
            bundle.putString("productCategory", productCategory)
            bundle.putInt("productID", productID)
            bundle.putString("productQuantity", quantity)
            cartFragment.arguments = bundle
            (requireContext() as MainActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, cartFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        parent?.getItemAtPosition(position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        return
    }
}
