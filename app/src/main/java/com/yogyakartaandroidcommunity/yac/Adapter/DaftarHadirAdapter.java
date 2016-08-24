package com.yogyakartaandroidcommunity.yac.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yogyakartaandroidcommunity.yac.R;
import com.yogyakartaandroidcommunity.yac.Serialize.DaftarHadirSerialize;

import java.util.List;

/**
 * Created by Septian on 22-Aug-16.
 */
public class DaftarHadirAdapter extends RecyclerView.Adapter<DaftarHadirAdapter.HadirViewHolder> {

    private List<DaftarHadirSerialize> hadirList;

    public class HadirViewHolder extends RecyclerView.ViewHolder {
        public TextView listNamaHadir, listMemberIdHadir, listDateHadir;

        public HadirViewHolder(View view) {
            super(view);
            listNamaHadir = (TextView) view.findViewById(R.id.textViewListNamaHadir);
            listMemberIdHadir = (TextView) view.findViewById(R.id.textViewListMemberIdHadir);
            listDateHadir = (TextView) view.findViewById(R.id.textViewListDateHadir);
        }
    }

    public DaftarHadirAdapter(List<DaftarHadirSerialize> hadirList) {
        this.hadirList = hadirList;
    }

    @Override
    public HadirViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_daftar_hadir, parent, false);
        return new HadirViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HadirViewHolder holder, int position) {
        DaftarHadirSerialize daftarHadirSerialize = hadirList.get(position);
        holder.listNamaHadir.setText(daftarHadirSerialize.getMemberId());
        holder.listMemberIdHadir.setText(daftarHadirSerialize.getMemberId());
        holder.listDateHadir.setText(daftarHadirSerialize.getHadir());
    }

    @Override
    public int getItemCount() {
        return hadirList.size();
    }
}
