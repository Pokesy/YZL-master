package com.thinksky.fragment;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.tox.R;


public class VideoPlayActivity extends BaseBActivity {
  VideoView videoView;
  MediaController mController;
  private String url;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.zjsp);

    showProgressDialog("正在加载视频", false);
    videoView = (VideoView) findViewById(R.id.video);
    mController = new MediaController(this);
    Bundle id = this.getIntent().getExtras();
    String url = id.getString("url");
    Uri uri = Uri.parse(url);
    videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
      @Override
      public void onPrepared(MediaPlayer mp) {
        videoView.start();
        closeProgressDialog();
      }
    });
    videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
      @Override
      public boolean onError(MediaPlayer mp, int what, int extra) {
        finish();
        return false;
      }
    });
    videoView.setVideoURI(uri);
    videoView.setMediaController(mController);
  }

  @Override
  public void onBackPressed() {
    //super.onBackPressed();
    finish();
  }
}