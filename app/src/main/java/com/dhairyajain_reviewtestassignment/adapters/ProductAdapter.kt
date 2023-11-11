package com.dhairyajain_reviewtestassignment.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dhairyajain_reviewtestassignment.*
import com.dhairyajain_reviewtestassignment.models.Product

class ProductAdapter(private val context: Context, private var product: List<Product>): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    val productList: List<Product> = product

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val productHolder = LayoutInflater.from(context).inflate(R.layout.raw_product_cell, parent, false)
        return ProductViewHolder(productHolder)
    }

    override fun getItemCount(): Int {
        return product.count()
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.productTitle.text = product[position].title
        holder.productDescription.text = product[position].description
        holder.reviewCount.text = buildString {
            append("Reviews: ")
            append(product[position].rating.count)
        }
        holder.productPrice.text = buildString {
            append("Price: $")
            append(product[position].price)
        }
        holder.productRating.text = buildString {
            append(product[position].rating.rate)
            append(" / ")
            append("10")
        }
        Glide.with(context).load(product[position].image).into(holder.productImage)
        
        holder.productCard.tag = position
        holder.productCard.setOnClickListener {
            val pos = it.tag as Int
            val product = productList[pos]
            val productTitle = product.title
            val productPrice = product.price
            val productRating = product.rating.rate
            val productDescription = product.description
            val productImage = product.image
            val productReviewCount = product.rating.count
            val productCategory = product.category
            val productID = product.id
            val productDetailsFragment = ProductDetailsFragment()
            val bundle = Bundle()
            bundle.putString("productTitle", productTitle)
            bundle.putDouble("productPrice", productPrice)
            bundle.putDouble("productRating", productRating)
            bundle.putString("productDescription", productDescription)
            bundle.putString("productImage", productImage)
            bundle.putInt("productReviewCount", productReviewCount)
            bundle.putString("productCategory", productCategory)
            bundle.putInt("productID", productID)
            productDetailsFragment.arguments = bundle
            (context as MainActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, productDetailsFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filteredList: ArrayList<Product>) {
        product = filteredList
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val productTitle: TextView = itemView.findViewById(R.id.titleTv)
        val productPrice: TextView = itemView.findViewById(R.id.priceTv)
        val productRating: TextView = itemView.findViewById(R.id.ratingTv)
        val productImage: ImageView = itemView.findViewById(R.id.productImg)
        val productDescription: TextView = itemView.findViewById(R.id.descriptionTv)
        val reviewCount: TextView = itemView.findViewById(R.id.reviewsTv)
        val productCard: View = itemView.findViewById(R.id.productCardView)
    }
}
