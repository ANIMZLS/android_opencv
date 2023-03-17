package com.example.myandroidopencv;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myandroidopencv.databinding.ActivityMainBinding;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView img;
    private Button btn_load, btn_gray;
    private Bitmap bitmap;

    // Used to load the 'myandroidopencv' library on application startup.
    static {
        System.loadLibrary("myandroidopencv");
    }

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        img = findViewById(R.id.img);
        btn_load = findViewById(R.id.btn_load);
        btn_gray = findViewById(R.id.btn_gray);

        btn_load.setOnClickListener(this);
        btn_gray.setOnClickListener(this);

    }

    /**
     * A native method that is implemented by the 'myandroidopencv' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_load:
                loadImg();
                break;
            case R.id.btn_gray:
                doChgGray();
                break;
        }
    }
    private void loadImg(){
        Intent intent = new Intent();
        intent.setType("image/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }
    private void doChgGray(){
        if (bitmap==null){
            return;
        }
        Bitmap bit = bitmap.copy(Bitmap.Config.ARGB_8888,false);
        Mat src = new Mat(bit.getHeight(), bit.getWidth(), CvType.CV_8UC(3));
        Utils.bitmapToMat(bit,src);
        Imgproc.cvtColor(src,src,Imgproc.COLOR_BGR2GRAY);
        Utils.matToBitmap(src,bitmap);
        Message message = new Message();
        message.what = 1;
        handler.sendMessage(message);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //用户操作完成，结果返回码是-1
        if (resultCode==RESULT_OK){
            Uri uri = data.getData();
            Log.e("uri",uri.toString());
            ContentResolver cr = this.getContentResolver();
            try {
                bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                img.setImageBitmap(bitmap);
            }catch(FileNotFoundException ex){
                Log.e("Exception",ex.getMessage(),ex);
            }
        }else{
            //操作错误，或没有选择图片
            Log.i("MainActivity", "操作错误");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()){
            Log.i("cv","未找到内部的opencv库，使用OpenCV Manager进行初始化");
        }else {
            Log.i("cv","发现了内置的opencv库，使用它来进行操作");
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case 1:
                    img.setImageBitmap(bitmap);
                    break;
            }
        }
    };
}