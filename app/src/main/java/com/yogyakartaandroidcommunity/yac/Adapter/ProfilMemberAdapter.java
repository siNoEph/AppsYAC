package com.yogyakartaandroidcommunity.yac.Adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yogyakartaandroidcommunity.yac.Helper.SQLHelper;
import com.yogyakartaandroidcommunity.yac.NewProfilActivity;
import com.yogyakartaandroidcommunity.yac.ProfilMemberActivity;
import com.yogyakartaandroidcommunity.yac.R;
import com.yogyakartaandroidcommunity.yac.Serialize.AnggotaSerialize;

import java.util.List;

/**
 * Created by Septian on 22-Aug-16.
 */
public class ProfilMemberAdapter extends RecyclerView.Adapter<ProfilMemberAdapter.MemberViewHolder> {

    private List<AnggotaSerialize> memberList;

    public class MemberViewHolder extends RecyclerView.ViewHolder {
        public TextView listId, listNama, listMemberId;

        public MemberViewHolder(final View view) {
            super(view);
            listId = (TextView) view.findViewById(R.id.textViewListId);
            listNama = (TextView) view.findViewById(R.id.textViewListNama);
            listMemberId = (TextView) view.findViewById(R.id.textViewListMemberId);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final CharSequence[] dialogitem = {"Edit", "Delete"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("List member option ?");
                    builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            switch (item) {
                                case 0:
                                    // Edit
                                    Intent intent = new Intent(view.getContext(), NewProfilActivity.class);
                                    intent.putExtra("codeIntent", "edit");
                                    try {
                                        SQLHelper dbAnggota = new SQLHelper(view.getContext());
                                        AnggotaSerialize anggotaSerialize = dbAnggota.getAnggota(listMemberId.getText().toString());

                                        intent.putExtra("id", anggotaSerialize.getId());
                                        intent.putExtra("memberId", anggotaSerialize.getMemberId());
                                        intent.putExtra("nama", anggotaSerialize.getNama());
                                        intent.putExtra("email", anggotaSerialize.getEmail());
                                        intent.putExtra("hp", anggotaSerialize.getHp());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    view.getContext().startActivity(intent);
                                    break;
                                case 1:
                                    // Delete
                                    int id = Integer.parseInt(listId.getText().toString());
                                    String idMember = listMemberId.getText().toString();
                                    try {
                                        SQLHelper db = new SQLHelper(view.getContext());
                                        db.deleteAnggota(new AnggotaSerialize(id, idMember));
                                        db.deleteAllMemberId(new AnggotaSerialize(id, idMember));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    ProfilMemberActivity.objProfilMember.getDataMember();
                                    Toast.makeText(view.getContext(), "Data Member telah dihapus !", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    });
                    builder.create().show();
                    return true;
                }
            });
        }
    }

    public ProfilMemberAdapter(List<AnggotaSerialize> memberList) {
        this.memberList = memberList;
    }

    @Override
    public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_profil_member, parent, false);
        return new MemberViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MemberViewHolder holder, int position) {
        AnggotaSerialize anggotaSerialize = memberList.get(position);
        holder.listId.setText(String.valueOf(anggotaSerialize.getId()));
        holder.listNama.setText(anggotaSerialize.getNama());
        holder.listMemberId.setText(anggotaSerialize.getMemberId());
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }
}
