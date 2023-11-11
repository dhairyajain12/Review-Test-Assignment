package com.dhairyajain_reviewtestassignment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.*
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.*
import com.dhairyajain_reviewtestassignment.adapters.ProductAdapter
import com.dhairyajain_reviewtestassignment.databinding.FragmentHomeBinding
import com.dhairyajain_reviewtestassignment.interfaces.*
import com.dhairyajain_reviewtestassignment.models.Product
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val _binding get() = binding
    private val baseURL = "https://fakestoreapi.com/"
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var filter: ImageView
    private lateinit var productAdapter: ProductAdapter
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = _binding.rvHome
        searchView = _binding.searchView
        filter = _binding.btnFilter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        displayProducts()
        
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filter(query.toString())
                return true
            }
            
            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText.toString())
                return true
            }
        })
        
        filter.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext())
            val dialogView = layoutInflater.inflate(R.layout.filter_dialog, null)
            dialog.setView(dialogView)
            dialog.setTitle("Filter")
            dialog.setPositiveButton("OK") { dialog, _ ->
                val minPrice = dialogView.findViewById<EditText>(R.id.et_min_price).text.toString()
                val maxPrice = dialogView.findViewById<EditText>(R.id.et_max_price).text.toString()
                val minRating = dialogView.findViewById<EditText>(R.id.et_min_rating).text.toString()
                val maxRating = dialogView.findViewById<EditText>(R.id.et_max_rating).text.toString()
                val minReviewCount = dialogView.findViewById<EditText>(R.id.et_min_reviews).text.toString()
                val maxReviewCount = dialogView.findViewById<EditText>(R.id.et_max_reviews).text.toString()
                val retrofitBuilder = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseURL)
                    .build()
                    .create(ProductInterface::class.java)
                val retrofitData = retrofitBuilder.getProductData()
                retrofitData.enqueue(object: Callback<List<Product>?> {
                    override fun onResponse(
                        call: Call<List<Product>?>,
                        response: Response<List<Product>?>) {
                        val productList = response.body()!!
                        val filteredList = ArrayList<Product>()
                        for (item in productList) {
                            productList.filter { prod ->
                                val priceInRange = prod.price in minPrice.toDouble()..maxPrice.toDouble()
                                val ratingInRange = prod.rating.rate in minRating.toDouble()..maxRating.toDouble()
                                val reviewCountInRange = prod.rating.count in minReviewCount.toInt()..maxReviewCount.toInt()

                                priceInRange && ratingInRange && reviewCountInRange
                            }
                        }
                        productAdapter.filterList(filteredList)
                    }
                    
                    override fun onFailure(call: Call<List<Product>?>, t: Throwable) {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
                        Log.i("HomeFragment<<<<<displayProduct()", t.toString())
                    }
                })
                dialog.dismiss()
            }
            dialog.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            dialog.show()
        }
    }
    
    private fun displayProducts() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseURL)
            .build()
            .create(ProductInterface::class.java)
        
        val retrofitData = retrofitBuilder.getProductData()
        retrofitData.enqueue(object: Callback<List<Product>?> {
            override fun onResponse(
                call: Call<List<Product>?>,
                response: Response<List<Product>?>) {
                val productList = response.body()!!
                productAdapter = ProductAdapter(requireContext(), productList)
                recyclerView.adapter = productAdapter
            }

            override fun onFailure(call: Call<List<Product>?>, t: Throwable) {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
                Log.i("HomeFragment<<<<<displayProduct()", t.toString())
            }
        })
    }
    
    private fun filter(text: String) {
        val filteredList = ArrayList<Product>()
        for (item in productAdapter.productList.toList().toTypedArray()) {
            if (item.title.lowercase().contains(text.lowercase().trim())) {
                filteredList.add(item)
            }
        }
        productAdapter.filterList(filteredList)
    }
}
