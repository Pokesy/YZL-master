package com.thinksky.fragment;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import com.thinksky.holder.BaseBActivity;
import com.thinksky.tox.R;


public class VedioView extends BaseBActivity {
    VideoView videoView;
    MediaController mController;
    private String url;
    private Dialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//		getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.zjsp);
        // ?????????VideoView???

//        dialog= ProgressDialog.show(this, "正在加载…", "三枪马上开始");

        videoView = (VideoView) findViewById(R.id.video);
        // ????MediaController????
        mController = new MediaController(this);
        Bundle id = this.getIntent().getExtras();
        String url = id.getString("url");
        Uri uri = Uri.parse(url);
        videoView.setVideoURI(uri);
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.FILL_PARENT,
//                RelativeLayout.LayoutParams.FILL_PARENT);
//        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        videoView.setLayoutParams(layoutParams);
        videoView.start();

    }
}