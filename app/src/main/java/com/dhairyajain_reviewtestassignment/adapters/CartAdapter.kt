package com.dhairyajain_reviewtestassignment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dhairyajain_reviewtestassignment.R
import com.dhairyajain_reviewtestassignment.models.Cart

class CartAdapter(private val context: Context, private val cartList: List<Cart>): RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.CartViewHolder {
        val cartHolder = LayoutInflater.from(context).inflate(R.layout.raw_cart_cell, parent, false)
        return CartViewHolder(cartHolder)
    }

    override fun onBindViewHolder(holder: CartAdapter.CartViewHolder, position: Int) {
        holder.cartTitle.text = cartList[position].products.id.toString()
        holder.cartPrice.text = cartList[position].products.price.toString()
        holder.cartRating.text = buildString {
            append(cartList[position].products.rating.rate)
            append(" / ")
            append(cartList[position].products.rating.count)
        }
    }

    override fun getItemCount(): Int {
        return cartList.count()
    }

    inner class CartViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val cartTitle: TextView = itemView.findViewById(R.id.titleTv)
        val cartPrice: TextView = itemView.findViewById(R.id.priceTv)
        val cartRating: TextView = itemView.findViewById(R.id.ratingTv)
    }
}
