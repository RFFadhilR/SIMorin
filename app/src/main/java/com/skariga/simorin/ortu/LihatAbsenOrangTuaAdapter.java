package com.skariga.simorin.ortu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.skariga.simorin.R;
import com.skariga.simorin.helper.Absen;
import com.skariga.simorin.perusahaan.ListAbsenPemPerusahaanAdapter;

import java.util.List;

public class LihatAbsenOrangTuaAdapter extends RecyclerView.Adapter<LihatAbsenOrangTuaAdapter.RecyclerViewAdapter> {

    private Context context;
    private List<Absen> absens;
    private ItemClickListerner itemClickListerner;

    public LihatAbsenOrangTuaAdapter(Context context, List<Absen> absens, ItemClickListerner itemClickListerner) {
        this.context = context;
        this.absens = absens;
        this.itemClickListerner = itemClickListerner;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_absen_ortu, parent, false);
        return new RecyclerViewAdapter(v, itemClickListerner);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter holder, int position) {
        Absen absen = absens.get(position);
        holder.tv_tanggal.setText(absen.getTanggal());
        String akses = absen.getAkses();
        if (akses.equals(1)) {
            absen.setKeterangan("HADIR");
            holder.tv_keterangan.setText(absen.getKeterangan());
        } else {
            absen.setKeterangan("ALPHA");
            holder.tv_keterangan.setText(absen.getKeterangan());
        }
    }

    @Override
    public int getItemCount() {
        return absens.size();
    }

    public class RecyclerViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_tanggal, tv_keterangan;
        CardView cardView;
        ItemClickListerner itemClickListerner;

        public RecyclerViewAdapter(View itemView, ItemClickListerner itemClickListerner) {
            super(itemView);

            tv_tanggal = itemView.findViewById(R.id.tv22);
            tv_keterangan = itemView.findViewById(R.id.tv222);
            cardView = itemView.findViewById(R.id.card_view);

            this.itemClickListerner = itemClickListerner;
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListerner.onItemClick(v, getAdapterPosition());
        }
    }

    public interface ItemClickListerner {
        void onItemClick(View view, int position);
    }


}
