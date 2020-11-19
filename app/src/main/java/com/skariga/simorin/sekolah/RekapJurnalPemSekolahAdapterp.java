package com.skariga.simorin.sekolah;

import android.content.Context;
import android.content.PeriodicSync;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.skariga.simorin.R;
import com.skariga.simorin.model.Perusahaan;

import java.util.List;

public class RekapJurnalPemSekolahAdapterp extends RecyclerView.Adapter<RekapJurnalPemSekolahAdapterp.RecyclerViewAdapter> {

    private Context context;
    private List<Perusahaan> perusahaans;
    private ItemClickListener itemClickListener;

    public RekapJurnalPemSekolahAdapterp(Context context, List<Perusahaan> perusahaans, ItemClickListener itemClickListener) {
        this.context = context;
        this.perusahaans = perusahaans;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_perusahaan, parent, false);
        return new RecyclerViewAdapter(v, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter holder, int position) {
        Perusahaan perusahaan = perusahaans.get(position);
        holder.tv_perusahaan.setText(perusahaan.getPerusahan());
        holder.tv_alamat.setText(perusahaan.getAlamat());
        holder.tv_total.setText("Total Siswa : "+ perusahaan.getTotal_siswa());
    }

    @Override
    public int getItemCount() {
        return perusahaans.size();
    }

    public class RecyclerViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemClickListener itemClickListener;
        TextView tv_perusahaan, tv_alamat, tv_total;
        CardView cardView;

        public RecyclerViewAdapter(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            tv_alamat = itemView.findViewById(R.id.tv_alamat);
            tv_perusahaan = itemView.findViewById(R.id.tv_perusahaan);
            tv_total = itemView.findViewById(R.id.tv_total);
            cardView = itemView.findViewById(R.id.card_view);

            this.itemClickListener = itemClickListener;
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClickListener(v, getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onClickListener(View v, int position);
    }
}
