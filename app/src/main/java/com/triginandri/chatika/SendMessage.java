package com.triginandri.chatika;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.triginandri.chatika.Model.Saved;
import com.triginandri.chatika.Model.User;
import com.triginandri.chatika.helper.SharedPrefManager;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SendMessage extends AppCompatActivity {

    TextInputEditText fldPembuka, fldTujuan, fldPenutup, fldPertanyaan;
    ImageButton btnBack, btnOpPembuka, btnOpTujuan, btnOpPenutup, btnOpPertanyaan;
    AppCompatButton btnSend,btnSave;
    private RadioGroup list_opsi_gender;

    String gender = "";
    boolean[] selectedPembukaan;
    boolean[] selectedPenutupan;
    boolean[] selectedTujuan;
    boolean[] selectedPertanyaan;
    ArrayList<Integer> pembukaanlist = new ArrayList<>();
    ArrayList<Integer> tujuanlist = new ArrayList<>();
    ArrayList<Integer> pertanyaanlist = new ArrayList<>();
    ArrayList<Integer> penutuplist = new ArrayList<>();
    String[] pembukaanArray = {
            "Assalamu'alaikum Wr. Wb.", "Shalom", "Namo Buddhaya", "Om Swasiastu", "Salam Kebajikan",
            "Selamat pagi", "Selamat siang", "Selamat sore", "Selamat malam", "Mohon maaf mengganggu waktunya", "Mohon maaf sebelumnya"};

    String[] tujuanArrayB = {"Saya ingin bertanya", "Saya ingin asesment", "Saya ingin mengecek", "Saya ingin evaluasi", "Saya ingin meminta izin",
            "Saya ingin meminta saran", "Saya ingin meminta bimbingan", "Saya ingin mengundang", "Saya ingin meminta persetujuan"};

    String[] pertanyaanArrayB = {"Apakah hari ini Bapak ada waktu?", "Apakah boleh saya keruangan Bapak?", "Apakah boleh saya telpon Bapak?", "Kapan kira-kira Bapak ada waktu luang?"};
    String[] pertanyaanArrayI = {"Apakah hari ini Ibu ada waktu?", "Apakah boleh saya keruangan Ibu?", "Apakah boleh saya telpon Ibu?", "Kapan kira-kira Ibu ada waktu luang?"};

    String[] penutupArray = {"Terima kasih", "Terima kasih Banyak", "Terima kasih sebelumnya", "Terima kasih atas perhatiannya", "Terima kasih atas waktunya"};
    private static final int REQUEST_READ_CONTACTS_PERMISSION = 0;
    private static final int SELECT_PHONE_NUMBER= 1;

    List<Saved> savedList = new ArrayList<>();


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_READ_CONTACTS_PERMISSION && grantResults.length > 0)
        {
            updateButton(grantResults[0] == PackageManager.PERMISSION_GRANTED);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHONE_NUMBER && resultCode == RESULT_OK) {
            // Get the URI and query the content provider for the phone number
            Uri contactUri = data.getData();
            String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
            Cursor cursor = this.getContentResolver().query(contactUri, projection,
                    null, null, null);

            // If the cursor returned is valid, get the phone number
            if (cursor != null && cursor.moveToFirst()) {
                int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberIndex);
//                fldNoDosen.setText(number.replaceAll("[-+.^:,]",""));

            }

            cursor.close();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        setupView();
        setupFunction();

    }

    public void setupView(){
//        fldNoDosen = findViewById(R.id.fld_nomor_dosen);
        fldPembuka = findViewById(R.id.fld_pembuka);
        fldTujuan= findViewById(R.id.fld_tujuan);
        fldPenutup = findViewById(R.id.fld_penutup);
        fldPertanyaan = findViewById(R.id.fld_pertanyaan);
//        btnContact = findViewById(R.id.btn_contact);
        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btn_simpan);
        btnSend = findViewById(R.id.btn_kirim);
        btnOpPembuka = findViewById(R.id.btn_opsi_pembuka);
        btnOpTujuan = findViewById(R.id.btn_opsi_tujuan);
        btnOpPertanyaan = findViewById(R.id.btn_opsi_pertanyaan);
        btnOpPenutup = findViewById(R.id.btn_opsi_penutup);
        list_opsi_gender = findViewById(R.id.opsi_gender);

    }

    public void  setupFunction(){
        LoadDataMessage();
        list_opsi_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id){
                    case R.id.laki:
                        gender = "laki";
                        break;
                    case R.id.perempuan:
                        gender = "perempuan";
                        break;
                }
            }
        });

        list_opsi_gender.check(R.id.laki);


        btnOpPembuka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SendMessage.this);

                builder.setTitle("Masukan Pembukaan");

                builder.setCancelable(false);

                builder.setMultiChoiceItems(pembukaanArray, selectedPembukaan, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            pembukaanlist.add(i);
                            Collections.sort(pembukaanlist);
                        } else {
                            pembukaanlist.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int j = 0; j < pembukaanlist.size(); j++) {
                            stringBuilder.append(pembukaanArray[pembukaanlist.get(j)]);
                            if (j != pembukaanlist.size() - 1) {
                                stringBuilder.append(" ");
                            }
                        }
                        if (gender == "perempuan"){
                            fldPembuka.setText(stringBuilder.toString() + " Bu.");
                        }else{
                            fldPembuka.setText(stringBuilder.toString() + " Pak.");
                        }
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });

        btnOpTujuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SendMessage.this);

                builder.setTitle("Masukan Tujuan");

                builder.setCancelable(false);

                    builder.setMultiChoiceItems(tujuanArrayB, selectedTujuan, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                            if (b) {
                                tujuanlist.add(i);
                                Collections.sort(tujuanlist);
                            } else {
                                tujuanlist.remove(Integer.valueOf(i));
                            }
                        }
                    });

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            StringBuilder stringBuilder = new StringBuilder();
                            for (int j = 0; j < tujuanlist.size(); j++) {
                                stringBuilder.append(tujuanArrayB[tujuanlist.get(j)]);
                                if (j != tujuanlist.size() - 1) {
                                    stringBuilder.append(". ");
                                }
                            }
                            fldTujuan.setText(stringBuilder.toString());

                        }
                    });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });

        btnOpPertanyaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SendMessage.this);

                builder.setTitle("Masukan Pertanyaan");

                builder.setCancelable(false);
                if (gender == "perempuan"){
                    builder.setMultiChoiceItems(pertanyaanArrayI, selectedPertanyaan, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                            if (b) {
                                pertanyaanlist.add(i);
                                Collections.sort(pertanyaanlist);
                            } else {
                                pertanyaanlist.remove(Integer.valueOf(i));
                            }
                        }
                    });

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            StringBuilder stringBuilder = new StringBuilder();
                            for (int j = 0; j < pertanyaanlist.size(); j++) {
                                stringBuilder.append(pertanyaanArrayI[pertanyaanlist.get(j)]);
                                if (j != pertanyaanlist.size() - 1) {
                                    stringBuilder.append("");
                                }
                            }
                            fldPertanyaan.setText(stringBuilder.toString());
                        }
                    });

                }else{
                    builder.setMultiChoiceItems(pertanyaanArrayB, selectedPertanyaan, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                            if (b) {
                                pertanyaanlist.add(i);
                                Collections.sort(pertanyaanlist);
                            } else {
                                pertanyaanlist.remove(Integer.valueOf(i));
                            }
                        }
                    });

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            StringBuilder stringBuilder = new StringBuilder();
                            for (int j = 0; j < pertanyaanlist.size(); j++) {
                                stringBuilder.append(pertanyaanArrayB[pertanyaanlist.get(j)]);
                                if (j != tujuanlist.size() - 1) {
                                    stringBuilder.append(" ");
                                }
                            }
                            fldPertanyaan.setText(stringBuilder.toString());
                        }
                    });
                }

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });

        btnOpPenutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SendMessage.this);

                builder.setTitle("Masukan Penutup");

                builder.setCancelable(false);

                builder.setMultiChoiceItems(penutupArray, selectedPenutupan, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            penutuplist.add(i);
                            Collections.sort(penutuplist);
                        } else {
                            penutuplist.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int j = 0; j < penutuplist.size(); j++) {
                            stringBuilder.append(penutupArray[penutuplist.get(j)]);
                            if (j != penutuplist.size() - 1) {
                                stringBuilder.append(" ");
                            }
                        }
                        fldPenutup.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();

            }
        });

//        btnContact.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(Intent.ACTION_PICK);
//                i.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
//                startActivityForResult(i, SELECT_PHONE_NUMBER);            }
//        });

        requestContactsPermission();
        updateButton(hasContactsPermission());


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pembuka = fldPembuka.getText().toString();
                String tujuan = fldTujuan.getText().toString();
                String pertanyaan = fldPertanyaan.getText().toString();
                String penutup = fldPenutup.getText().toString();
                User user = SharedPrefManager.getInstance(SendMessage.this).getUser();


                if (TextUtils.isEmpty(pembuka)) {
                    Toast.makeText(SendMessage.this, "Masukan kalimat pembuka", Toast.LENGTH_SHORT).show();
                    fldPembuka.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(tujuan)) {
                    Toast.makeText(SendMessage.this, "Masukan tujuan pesanmu ", Toast.LENGTH_SHORT).show();
                    fldTujuan.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(penutup)) {
                    Toast.makeText(SendMessage.this, "Masukan kalimat penutup", Toast.LENGTH_SHORT).show();
                    fldPenutup.requestFocus();
                    return;
                }

                String perkenalan = "Saya " + user.getName() + " dengan NIM " + user.getNim() + " mahasiswa program studi " + user.getProdi();
                String pesan = pembuka + " \n" + perkenalan +".\n\n" + tujuan + ". " + pertanyaan + ".\n\n" + penutup +".";

                tinjauPesan(pesan);

//                Toast.makeText(SendMessage.this, nohp + "\n\n" + pesan, Toast.LENGTH_SHORT).show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pembuka = fldPembuka.getText().toString();
                String tujuan = fldTujuan.getText().toString();
                String pertanyaan = fldPertanyaan.getText().toString();
                String penutup = fldPenutup.getText().toString();

                if (TextUtils.isEmpty(pembuka)) {
                    Toast.makeText(SendMessage.this, "Masukan kalimat pembuka", Toast.LENGTH_SHORT).show();
                    fldPembuka.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(tujuan)) {
                    Toast.makeText(SendMessage.this, "Masukan tujuan pesanmu ", Toast.LENGTH_SHORT).show();
                    fldTujuan.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(penutup)) {
                    Toast.makeText(SendMessage.this, "Masukan kalimat penutup", Toast.LENGTH_SHORT).show();
                    fldPenutup.requestFocus();
                    return;
                }

                savedList.add(new Saved(gender, pembuka,tujuan,pertanyaan,penutup));
                SaveMessage();
                Toast.makeText(SendMessage.this, "Pesan Tersimpan", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void SaveMessage(){

        SharedPreferences sharedPreferences =  getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();

        String json = gson.toJson(savedList);
        editor.putString(SharedPrefManager.KEY_MESSAGE, json);
        editor.apply();
    }

    public void LoadDataMessage(){

        SharedPreferences sharedPreferences = SendMessage.this.getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        Gson gson = new Gson();

        String json = sharedPreferences.getString(SharedPrefManager.KEY_MESSAGE, null);

        Type type = new TypeToken<ArrayList<Saved>>() {}.getType();

        savedList = gson.fromJson(json, type);

        if (savedList == null) {
            savedList = new ArrayList<>();
        }

    }


    private void tinjauPesan(String pesan){
        String currentTime = new SimpleDateFormat("HH", Locale.getDefault()).format(new Date());

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        alertDialogBuilder.setTitle("Apa pesan kamu sudah benar?");

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
                            SendMessage.this.finish();
                        }

                    }
                })
                .setNegativeButton("Edit Pesan",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void tinjauWaktu(String pesan){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        alertDialogBuilder.setTitle("Peringatan!");

        alertDialogBuilder
                .setMessage("Waktu menujukan sudah melebihi jam kerja. Apakah kamu yakin akan tetap mengirim pesan?")
                .setIcon(R.drawable.logo_blue)
                .setCancelable(false)
                .setPositiveButton("Ya, Kirim",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        sendMessage(pesan);
                        SendMessage.this.finish();
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

        // Creating new intent
        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.setType("text/plain");
        intent.setPackage("com.whatsapp");

        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (intent.resolveActivity(getPackageManager()) == null) {
            Toast.makeText(
                    this,
                    "Kamu belum menginstall whatsapp",
                    Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        // Starting Whatsapp
        startActivity(intent);
    }

    public void updateButton(boolean enable)
    {
//        btnContact.setEnabled(enable);
    }

    private boolean hasContactsPermission()
    {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) ==
                PackageManager.PERMISSION_GRANTED;
    }

    private void requestContactsPermission()
    {
        if (!hasContactsPermission())
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_READ_CONTACTS_PERMISSION);
        }
    }

}