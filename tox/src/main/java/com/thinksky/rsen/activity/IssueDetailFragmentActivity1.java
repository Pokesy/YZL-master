package com.thinksky.rsen.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.thinksky.rsen.fragment.IssueDetailFragment;
import com.thinksky.rsen.fragment.RBaseFragment;

/**
 * Created by dajiao on 16-03-02-002.
 */
public class IssueDetailFragmentActivity1 extends RBaseFragmentActivity {

    public static void launch(Context context, String id) {
        Intent intent = new Intent(context, IssueDetailFragmentActivity1.class);
        Bundle args = new Bundle();
        args.putString(IssueDetailFragment.KEY, id);
        intent.putExtras(args);
        context.startActivity(intent);
    }

    @Override
    protected RBaseFragment getBaseFragment() {
        return IssueDetailFragment.newInstance(getIntent().getExtras().getString(IssueDetailFragment.KEY));
    }
}
