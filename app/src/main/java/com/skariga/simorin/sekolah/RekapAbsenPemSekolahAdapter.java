package com.skariga.simorin.sekolah;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.skariga.simorin.R;
import com.skariga.simorin.model.Perusahaan;
import com.skariga.simorin.perusahaan.ListAbsenPemPerusahaanAdapter;

import java.util.List;

public class RekapAbsenPemSekolahAdapter extends RecyclerView.Adapter<RekapAbsenPemSekolahAdapter.RecyclerViewAdapter> {

    private Context context;
    private List<Perusahaan> perusahaans;
    private ItemClickListener itemClickListener;

    public RekapAbsenPemSekolahAdapter(Context context, List<Perusahaan> perusahaans, ItemClickListener itemClickListener) {
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
        Perusahaan pers = perusahaans.get(position);
        holder.tv_perusahaan.setText(pers.getPerusahan());
        holder.tv_alamat.setText(pers.getAlamat());
        holder.tv_total.setText("Total Siswa : " + pers.getTotal_siswa() + " Siswa");
    }

    @Override
    public int getItemCount() {
        return perusahaans.size();
    }

    public class RecyclerViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_perusahaan, tv_alamat, tv_total;
        LinearLayout linearLayout;
        CardView cardView;
        ItemClickListener itemClickListener;

        public RecyclerViewAdapter(@NonNull View itemView, ItemClickListener itemClickListeners) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.btn_perusahaan);
            tv_perusahaan = itemView.findViewById(R.id.tv_perusahaan);
            tv_alamat = itemView.findViewById(R.id.tv_alamat);
            tv_total = itemView.findViewById(R.id.tv_total);
            cardView = itemView.findViewById(R.id.crs);

            itemClickListener = itemClickListeners;
            cardView.setOnClickListener(this);
            linearLayout.setOnClickListener(this);
            tv_perusahaan.setOnClickListener(this);
            tv_total.setOnClickListener(this);
            tv_alamat.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClickListener(v, getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onClickListener(View view, int position);
    }
}
