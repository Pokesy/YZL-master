package com.thinksky.rsen.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.widget.TextView;

import com.thinksky.tox.R;

/**
 * Created by dajiao on 16-03-20-020.
 */
public class AnswerDialogFragment extends RBaseDialogFragment {

    private TextView cancel;
    public static class ArgBean {
        //保存参数信息
    }

    public static void show(FragmentManager manager, ArgBean bean) {
        AnswerDialogFragment fragment = new AnswerDialogFragment();
        Bundle arg = new Bundle();
        {
            //参数转换,到Bundle中.
        }
        fragment.setArguments(arg);
        fragment.show(manager, fragment.getClass().getSimpleName());
    }


    @Override
    protected int getContentView() {
        return R.layout.answer_dialog_layout;
    }

    @Override
    protected int getGravity() {
        return Gravity.TOP;
    }

    @Override
    protected void initArguments(Bundle arguments) {
//        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

//        mViewHolder.v(R.id.rootLayout).addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                Log.e("", "");
//            }
//        });

    }
}
