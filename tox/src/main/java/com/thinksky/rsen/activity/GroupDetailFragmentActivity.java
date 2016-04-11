package com.thinksky.rsen.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.thinksky.rsen.fragment.GroupDetailFragment;
import com.thinksky.rsen.fragment.RBaseFragment;

/**
 * Created by dajiao on 16-03-02-002.
 */
public class GroupDetailFragmentActivity extends RBaseFragmentActivity {

    public static void launch(Context context, String group_id) {
        Intent intent = new Intent(context, GroupDetailFragmentActivity.class);
        Bundle args = new Bundle();
        args.putString(GroupDetailFragment.KEY, group_id);
        intent.putExtras(args);
        context.startActivity(intent);
    }

    @Override
    protected RBaseFragment getBaseFragment() {
        return GroupDetailFragment.newInstance(getIntent().getExtras().getString(GroupDetailFragment.KEY));
    }
}
