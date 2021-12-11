package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.myapplication.ui.home.HomeFragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class mission extends AppCompatActivity implements View.OnClickListener {
    private final int GET_GALLERY_IMAGE = 200;
    private ImageView imageView;
    private static final int MY_PERMISSION_CAMERA = 1111;
    private static final int REQUEST_TAKE_PHOTO = 2222;
    private static final int REQUEST_TAKE_ALBUM = 3333;
    private static final int REQUEST_IMAGE_CROP = 4444;
    

    DBHelper dbHelper;
    DBHelper  dbHelper2;

    String mCurrentPhotoPath;
    Uri imageURI;
    Uri photoURI, albumURI;
    public static final int REQUESTCODE = 101;

    EditText nameView;
    EditText typeView;
    EditText dateView;
    EditText codeView;
    TextView codeTxt;
    Button addBtn;
    Button codeSearch;


    ArrayList<BarCdVo> barCdVoArrayList ;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listmain);

        dbHelper = new DBHelper(getApplicationContext(),"tb_contact.db",null,1);
        dbHelper2 = new DBHelper(getApplicationContext(),"API.db",null,3);


        barCdVoArrayList = ManagePublicData.getInstance().getBarCdVoArrayList();
        dbHelper2.insertData(barCdVoArrayList);
        dbHelper2.close();
        Log.v("MISSION시작:",Integer.toString(barCdVoArrayList.size()));


//        imageView = (ImageView) findViewById(R.id.imageView);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PopupMenu pop = new PopupMenu(getApplicationContext(), view);
//                getMenuInflater().inflate(R.menu.main_menu, pop.getMenu());
//                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem menuItem) {
//                        switch (menuItem.getItemId()) {
//                            case R.id.one:
//                                captureCamera();
//                                break;
//                            case R.id.two:
//                                getAlbum();
//                                break;
//
//                        }
//                        return true;
//                    }
//
//                });
//                pop.show();
//                checkPermission();
//            }
//
//        });



        codeSearch=(Button)findViewById(R.id.codeSearch);
        codeSearch .setOnClickListener(this);
        codeView = (EditText)findViewById(R.id.edit_code);
        nameView = (EditText)findViewById(R.id.edit_name);

        final Spinner mSpinner=findViewById(R.id.edit_type);
        String[] models= getResources().getStringArray(R.array.foodtype);
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item,models);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.getSelectedItem().toString();
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        dateView = (EditText)findViewById(R.id.edit_date);

        addBtn=(Button)findViewById(R.id.btn_add);
        addBtn.setOnClickListener(this);

    }
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) ||
                    (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))) {
                new AlertDialog.Builder(this).setTitle("알림").setMessage("저장소 권한이 거부되었습니다.").setNeutralButton("설정", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package: " + getPackageName()));
                        startActivity(intent);
                    }
                }).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).setCancelable(false).create().show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, MY_PERMISSION_CAMERA);
            }
        }
    }

    @Override
    public void onClick(View v){
        final Spinner mSpinner=findViewById(R.id.edit_type);
        if(v==codeSearch){

            SQLiteDatabase db=dbHelper2.getWritableDatabase();
            dbHelper2.getItems();

            db.close();
            Log.d("시작:","getItems");

            Intent intent=new Intent(this, scanner.class);
            startActivityForResult(intent,10);
        }
        if(v==addBtn){
            nameView=(EditText)findViewById(R.id.edit_name);
            dateView=(EditText)findViewById(R.id.edit_date);
            codeView=(EditText)findViewById(R.id.edit_code);

            String name = nameView.getText().toString();
            String type = mSpinner.getSelectedItem().toString();
            String period = dateView.getText().toString();
            String code=codeView.getText().toString();
            if(name==null || name.equals("")){
                Toast t=Toast.makeText(this, "상품명이 입력되지 않았습니다. ", Toast.LENGTH_SHORT);
                t.show();
            }else if(period==null || period.equals("")){
                Toast t=Toast.makeText(this, "유통기한이 입력되지 않았습니다. ", Toast.LENGTH_SHORT);
                t.show();
            }else {
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                dbHelper.insert(name,type,period,code);
                db.close();
                Log.v("DB삽입 시작", "name:" + name + ", type: " + type + ", period: " + period+ ", code: " + code);

                Toast t=Toast.makeText(this, "새로운 리스트가 등록되었습니다.", Toast.LENGTH_SHORT);
                t.show();
                reset();

                Intent intent=new Intent(this, missionresult.class);
                startActivity(intent);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults[0] == 0) {
                Toast.makeText(this, "카메라 권한 승인완료", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "카메라 권한 승인거절", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        Log.i("REQUEST_TAKE_PHOTO", "OK!!!!!!");
                        galleryAddPic();
                        imageView.setImageURI(imageURI);
                    } catch (Exception e) {
                        Log.e("REQUEST_TAKE_PHOTO", e.toString());
                    }
                } else {
                    Toast.makeText(mission.this, "저장공간에 접근할 수 없는 기기 입니다.", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_TAKE_ALBUM:
                if (resultCode == Activity.RESULT_OK) {
                    if (data.getData() != null) {
                        try {
                            File albumFile = null;
                            albumFile = createImageFile();
                            photoURI = data.getData();
                            albumURI = Uri.fromFile(albumFile);

                        } catch (IOException e) {
                            Log.e("TAKE_ALBUM_SINLE_ERROR", e.toString());
                        }
                    }
                }
                break;

            case 10 :
                if(resultCode ==Activity.RESULT_OK) {
                    try {
                        String autoCode =data.getStringExtra("barcode");
                        codeView.setText(autoCode);
                        settingAuto(autoCode);
                    } catch (Exception e) {
                        Log.e("Barcode Error", e.toString());
                    }
                }
                break;
        }
    }

    private void settingAuto(String autoCode){
        int end = barCdVoArrayList.size();
        //Log.v("바코드num", Integer.toString((end)));
        String RBarCode = autoCode.substring(0,9);
        //Log.v("바코드",RBarCode);
        String sqlSelect ="SELECT substr(Bar_CD,0,10)," +
                "Product_NM,Product_DCNM," +
                "Product_DAYCNT FROM API " +
                "WHERE substr(Bar_CD,0,10) = ?";
        SQLiteDatabase db=dbHelper2.getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlSelect,new String[]{RBarCode});
        //Log.v("바코드", String.valueOf(cursor.getCount()));
        cursor.moveToFirst();
        try {
            if (cursor != null) {
                Log.v("바코드:", "커서");
                String pname=cursor.getString(1);
                String ptype=cursor.getString(2);
                String pdate=cursor.getString(3);
                Log.v("바코드:", "pname"+pname+"ptype"+
                        ptype+"pdate"+pdate);
                nameView.setText(pname);
                dateView.setText(pdate);
            } else {
                Log.v("바코드:", "값없음");
                Toast.makeText(mission.this,
                        "일치하는항목이 없습니다.직접입력해주세요.",
                        Toast.LENGTH_SHORT).show();
            }
        }
        finally {
            if(cursor!=null){
                Log.v("바코드:", "끝");
                cursor.close();
            }
        }
        cursor.close();
        db.close();
    }
    private void getAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_TAKE_ALBUM);
    }

    private void captureCamera() {
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(takePictureIntent.resolveActivity(getPackageManager()) != null){
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(photoFile != null){
                    Uri providerUri = FileProvider.getUriForFile(this,getPackageName(),photoFile);
                    imageURI = providerUri;

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,providerUri);
                    startActivityForResult(takePictureIntent,REQUEST_TAKE_PHOTO);
                }
            }else{
                Toast.makeText(this,"접근 불가능 합니다",Toast.LENGTH_SHORT).show();
                return;
            }
        }

    }

    private File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";
        File imageFile = null;
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures");

        if(!storageDir.exists()){
            storageDir.mkdirs();
        }
        imageFile = new File(storageDir, imageFileName);
        mCurrentPhotoPath = imageFile.getAbsolutePath();

        return imageFile;
    }


    // 갤러리에 사진 추가 함수
    private void galleryAddPic(){
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(mCurrentPhotoPath);
        Uri contentURI = Uri.fromFile(file);
        mediaScanIntent.setData(contentURI);
        sendBroadcast(mediaScanIntent);
        Toast.makeText(this,"앨범에 저장되었습니다.",Toast.LENGTH_SHORT).show();
    }

    public void reset(){

    }

}

