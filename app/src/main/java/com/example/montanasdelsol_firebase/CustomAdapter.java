package com.example.montanasdelsol_firebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    private List<Product> lstProduct = new ArrayList<>();

    public CustomAdapter(Context context, List<Product> lstProduct){
        this.context = context;
        this.lstProduct = lstProduct;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fila, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        holder.filaName.setText(lstProduct.get(position).getName()+" | "+ lstProduct.get(position).getCategory());
        holder.filaDescription.setText(lstProduct.get(position).getPresentation());
        holder.filaPrice.setText(lstProduct.get(position).getPrice().toString());
    }

    @Override
    public int getItemCount() {
        return lstProduct.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView filaName, filaDescription, filaPrice;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            filaName = itemView.findViewById(R.id.filaName);
            filaDescription = itemView.findViewById(R.id.filaDescription);
            filaPrice = itemView.findViewById(R.id.filaPrice);
        }
    }
}
