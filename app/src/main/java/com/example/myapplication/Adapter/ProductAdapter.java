package com.example.myapplication.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Product;
import com.example.myapplication.R;


import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> productList;
    private OnProductClickListener onProductClickListener;

    public ProductAdapter(Context context, List<Product> productList, OnProductClickListener onProductClickListener) {
        this.context = context;
        this.productList = productList;
        this.onProductClickListener = onProductClickListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view, onProductClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        if (product != null) {
            holder.itemView.setTag(product);
            holder.tvProductName.setText(product.getName());
            holder.tvProductPrice.setText(String.valueOf(product.getPrice()));
        } else {
            holder.itemView.setTag(null); // Rất quan trọng để tránh NullPointerException
        }
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvProductName, tvProductPrice;
        ImageView tvProductImgURL;
        OnProductClickListener onProductClickListener;

        public ProductViewHolder(@NonNull View itemView, OnProductClickListener onProductClickListener) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvProductImgURL=itemView.findViewById(R.id.tvProductImgUrl);
            this.onProductClickListener = onProductClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getTag() instanceof Product) {
                onProductClickListener.onProductClick((Product) v.getTag());
            } else {
                Log.d("ProductAdapter", "Product is null, check your data binding.");
            }
        }
    }

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }
}
