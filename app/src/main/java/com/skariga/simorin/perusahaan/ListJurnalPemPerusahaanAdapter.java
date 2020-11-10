package com.skariga.simorin.perusahaan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skariga.simorin.R;
import com.skariga.simorin.helper.Jurnal;

import java.util.List;

public class ListJurnalPemPerusahaanAdapter extends RecyclerView.Adapter<ListJurnalPemPerusahaanAdapter.RecyclerViewAdapter> {

    private Context context;
    private List<Jurnal> jurnals;

    public ListJurnalPemPerusahaanAdapter(Context context, List<Jurnal> jurnals) {
        this.context = context;
        this.jurnals = jurnals;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_jurnal, parent, false);
        return new RecyclerViewAdapter(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter holder, int position) {

    }

    @Override
    public int getItemCount() {
        return jurnals.size();
    }

    public class RecyclerViewAdapter extends RecyclerView.ViewHolder {
        public RecyclerViewAdapter(@NonNull View itemView) {
            super(itemView);
        }
    }
}
