package com.triginandri.chatika.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.triginandri.chatika.DetailNews;
import com.triginandri.chatika.Model.Saved;
import com.triginandri.chatika.Model.User;
import com.triginandri.chatika.R;
import com.triginandri.chatika.SendMessage;
import com.triginandri.chatika.helper.SharedPrefManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.MyViewHolder> {

    private final Context context;

    public SavedAdapter(List<Saved> saveList, Context context) {
        this.saveList = saveList;
        this.context = context;

    }

    List<Saved> saveList;


    @NonNull
    @Override
    public SavedAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View iceCreamView = layoutInflater.inflate(R.layout.item_saved,null);
        MyViewHolder viewHolder = new MyViewHolder(iceCreamView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SavedAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Saved s = saveList.get(position);

        holder.tv_title.setText(s.getPembukaan());
        holder.tv_sub.setText(s.getTujuan());
        holder.tv_numb.setText(position+1+". ");
//        holder.iv_thumb.setImageResource(baverages.getImage());
        holder.cv_menu.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popup = new PopupMenu(view.getContext(), holder.cv_menu);
                popup.inflate(R.menu.saved_menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:
                                saveList.remove(position);
                                notifyDataSetChanged();
                                Context context = view.getContext();
                                SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                Gson gson = new Gson();

                                String json = gson.toJson(saveList);
                                editor.putString(SharedPrefManager.KEY_MESSAGE, json);
                                editor.apply();

                                Toast.makeText(view.getContext(), "Terhapus", Toast.LENGTH_SHORT).show();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();

                return false;
            }
        });
        holder.cv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pembuka = s.getPembukaan();
                String tujuan = s.getTujuan();
                String pertanyaan = s.getPertanyaan();
                String penutup = s.getPenutupan();
                User user = SharedPrefManager.getInstance(context).getUser();

                String perkenalan = "Saya " + user.getName() + " dengan NIM " + user.getNim() + " mahasiswa program studi " + user.getProdi();
                String pesan = pembuka + " \n" + perkenalan +".\n\n" + tujuan + ". " + pertanyaan + ".\n\n" + penutup +".";

                tinjauPesan(pesan);
            }
        });
    }

    @Override
    public int getItemCount() {
        return saveList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public final TextView tv_title;
        public final TextView tv_sub;
        public final TextView tv_numb;
        public final CardView cv_menu;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_title);
            tv_sub = itemView.findViewById(R.id.tv_sub);
            tv_numb = itemView.findViewById(R.id.tv_num);
            cv_menu = itemView.findViewById(R.id.cv_menu);

        }
    }

    private void tinjauPesan(String pesan){
        String currentTime = new SimpleDateFormat("HH", Locale.getDefault()).format(new Date());

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        alertDialogBuilder.setTitle("Kirim lagi pesan ini?");

        alertDialogBuilder
                .setMessage(pesan)
                .setIcon(R.drawable.logo_blue)
                .setCancelable(false)
                .setPositiveButton("Ya, Kirim",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        if (Integer.parseInt(currentTime) < 8 || Integer.parseInt(currentTime) > 17 ){
                            tinjauWaktu(pesan);
                        }else{
                            sendMessage(pesan);
                            dialog.dismiss();
                        }

                    }
                })
                .setNegativeButton("Baru",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(context, SendMessage.class);
                        context.startActivity(i);
                    }
                })
                .setNeutralButton("Batal",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void tinjauWaktu(String pesan){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setTitle("Peringatan!");

        alertDialogBuilder
                .setMessage("Waktu menujukan sudah melebihi jam kerja. Apakah kamu yakin akan tetap mengirim pesan?")
                .setIcon(R.drawable.logo_blue)
                .setCancelable(false)
                .setPositiveButton("Ya, Kirim",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        sendMessage(pesan);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Batal",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void sendMessage(String message)
    {
        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.setType("text/plain");
        intent.setPackage("com.whatsapp");

        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (intent.resolveActivity(context.getPackageManager()) == null) {
            Toast.makeText(
                    context,
                    "Kamu belum menginstall whatsapp",
                    Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        context.startActivity(intent);
    }
}
