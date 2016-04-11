package com.thinksky.rsen.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.thinksky.rsen.RViewHolder;
import com.thinksky.tox.R;

/**
 * Created by dajiao on 16-03-20-020.
 */
public abstract class RBaseAppActivity extends AppCompatActivity {

    public static Toast T;
    protected ImageView backImage;
    protected TextView titleView;
    protected LinearLayoutCompat menuLayout;
    protected RViewHolder mHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        T = Toast.makeText(this, "", Toast.LENGTH_LONG);

        setContentView(R.layout.activity_base_layout);

        mHolder = new RViewHolder(findViewById(R.id.rootLayout));

        LayoutInflater.from(this).inflate(layoutId(), ((ViewGroup) findViewById(R.id.contain)));
        initBackImage();

        onCreateAfter();
    }

    protected abstract void onCreateAfter();

    protected abstract int layoutId();

    private void initBackImage() {
        backImage = mHolder.imgV(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBack()) {
                    onBackPressed();
                }
            }
        });
    }

    protected void setTitle(String title) {
        mHolder.tV(R.id.title).setText(title);
    }

    private LinearLayoutCompat.LayoutParams getLayoutParams() {
        return new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    protected void addRightMenu(String title, View.OnClickListener listener) {
        TextView view = new TextView(this);
        view.setText(title);
        view.setTextColor(Color.WHITE);
        view.setLayoutParams(getLayoutParams());
        view.setGravity(Gravity.CENTER);
        view.setPadding(20, 0, 20, 0);
        view.setBackgroundResource(R.drawable.default_layout_bg_selector);
        addRightMenu(view, listener);
    }

    protected void addRightMenu(View view, View.OnClickListener listener) {
        mHolder.groupV(R.id.menuLayout).addView(view);
        view.setOnClickListener(listener);
    }

    public static void show(String msg) {
        T.setText(msg);
        T.show();
    }

    /*点击返回图标,可以拦截返回事件*/
    protected boolean onBack() {
        return true;
    }
}
