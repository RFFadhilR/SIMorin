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
import com.skariga.simorin.model.JurnalOrtu;
import com.skyhope.showmoretextview.ShowMoreTextView;

import java.util.List;

public class LihatJurnalOrangTuaAdapter extends RecyclerView.Adapter<LihatJurnalOrangTuaAdapter.RecyclerViewAdapter> {

    private Context context;
    private List<JurnalOrtu> jurnalOrtus;
    private ItemClickListener itemClickListener;

    public LihatJurnalOrangTuaAdapter(Context context, List<JurnalOrtu> jurnalOrtus, ItemClickListener itemClickListener) {
        this.context = context;
        this.jurnalOrtus = jurnalOrtus;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_jurnal_ortu, parent, false);
        return new RecyclerViewAdapter(v, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter holder, int position) {
        JurnalOrtu ortu = jurnalOrtus.get(position);
        holder.tv_tanggal.setText(ortu.getTanggal());
        holder.tv_kegiatan.setText(ortu.getKegiatan());
        holder.tv_kegiatan.setShowingLine(2);
        holder.tv_kegiatan.addShowLessText("Lebih Dikit");
        holder.tv_kegiatan.addShowMoreText("Lebih Banyak");
    }

    @Override
    public int getItemCount() {
        return jurnalOrtus.size();
    }

    public class RecyclerViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_tanggal;
        ShowMoreTextView tv_kegiatan;
        CardView cardView;
        ItemClickListener itemClickListener;

        public RecyclerViewAdapter(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            tv_tanggal = itemView.findViewById(R.id.tv22);
            tv_kegiatan = itemView.findViewById(R.id.kegiatan);
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
