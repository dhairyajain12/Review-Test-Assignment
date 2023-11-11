package com.dhairyajain_reviewtestassignment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dhairyajain_reviewtestassignment.adapters.CartAdapter
import com.dhairyajain_reviewtestassignment.databinding.FragmentCartBinding
import com.dhairyajain_reviewtestassignment.interfaces.CartInterface
import com.dhairyajain_reviewtestassignment.models.Cart
import com.dhairyajain_reviewtestassignment.models.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private val _binding get() = binding
    private lateinit var cartAdapter: CartAdapter
    private lateinit var rvCart: RecyclerView
    private var productID: Int = 0
    private var productTitle: String = ""
    private var productPrice: Double = 0.0
    private var productDescription: String = ""
    private var productCategory: String = ""
    private var productImage: String = ""
    private var productRating: Double = 0.0
    private var productReviewCount: Int = 0
    private var productQuantity: Int = 0
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        productID = arguments?.getInt("productID")!!
        productTitle = arguments?.getString("productTitle")!!
        productPrice = arguments?.getDouble("productPrice")!!
        productDescription = arguments?.getString("productDescription")!!
        productCategory = arguments?.getString("productCategory")!!
        productImage = arguments?.getString("productImage")!!
        productRating = arguments?.getDouble("productRating")!!
        productReviewCount = arguments?.getInt("productReviewCount")!!
        productQuantity = arguments?.getInt("productQuantity")!!
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        rvCart = _binding.cartRv
        rvCart.setHasFixedSize(true)
        rvCart.layoutManager = LinearLayoutManager(requireContext())
        displayCart()
        
        binding.checkoutBtn.setOnClickListener { 
            Toast.makeText(requireContext(), "Checkout Successful", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun displayCart() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://fakestoreapi.com/")
            .build()
            .create(CartInterface::class.java)
        
        val retrofitData = retrofitBuilder.getCartData()
        retrofitData.enqueue(object: Callback<List<Cart>?> {
            override fun onResponse(call: Call<List<Cart>?>, response: Response<List<Cart>?>) {
                val responseBody = response.body()!!
                cartAdapter = CartAdapter(requireContext(), responseBody)
                rvCart.adapter = cartAdapter
            }

            override fun onFailure(call: Call<List<Cart>?>, t: Throwable) {
                Toast.makeText(requireContext(), "Error Fetching Data", Toast.LENGTH_SHORT).show()
                Log.d("CartFragment", "onFailure: ${t.message}")
            }
        })
    }
}
