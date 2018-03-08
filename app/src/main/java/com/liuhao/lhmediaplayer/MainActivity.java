package com.liuhao.lhmediaplayer;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;

import com.liuhao.lhmediaplayer.videocontroller.HaoVideoPlayer;
import com.liuhao.lhmediaplayer.videocontroller.HaoVideoPlayerManager;
import com.liuhao.lhmediaplayer.videocontroller.TxVideoPlayerController;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permission != PackageManager.PERMISSION_GRANTED){
            explainDialog();
        }


        HaoVideoPlayer videoPlayer = (HaoVideoPlayer) findViewById(R.id.videoplayer);

//        File file = new File(Environment.getExternalStorageDirectory() ,"MediaUtils");//内部存储根目录下
        String url = Environment.getExternalStorageDirectory() + "/MediaUtils/123.mp4";

//        String videoUrl = Environment.getExternalStorageDirectory().getPath().concat("/123.mp4");
        videoPlayer.setUp(url, null);

        TxVideoPlayerController controller = new TxVideoPlayerController(this);
        controller.setTitle("电影天堂666");
        controller.setLenght(98000);

        videoPlayer.setController(controller);

    }

    @Override
    protected void onStop() {
        super.onStop();
        HaoVideoPlayerManager.instance().releaseHaoVideoPlayer();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

        }else{
            Toast.makeText(this,"用户拒绝了",Toast.LENGTH_SHORT).show();
        }
    }


    private void explainDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("应用需要获取您的读取文件权限,是否授权？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //请求权限
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    }
                }).setNegativeButton("取消", null)
                .create().show();
    }

}
