package com.thinksky.rsen.fragment;

import com.thinksky.rsen.RsenUrlUtil;
import com.tox.ToastHelper;
import com.tox.Url;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by dajiao on 16-03-02-002.
 */
public abstract class RNetBaseFragment<T> extends RBaseFragment {


    protected abstract String getUrl();

    protected Map getMap() {
        return null;
    }

    @Override
    protected void initViewData() {
        RsenUrlUtil.execute(getUrl(), new RsenUrlUtil.OnJsonResultListener<T>() {
            @Override
            public Map getMap() {
                return RNetBaseFragment.this.getMap();
            }

            @Override
            public void onNoNetwork(String msg) {
                ToastHelper.showToast(msg, Url.context);
            }

            @Override
            public void onParseJsonBean(List<T> beans, JSONObject jsonObject) {
                try {
                    parseJson(beans, jsonObject);
                } catch (Exception e) {

                }
            }

            @Override
            public void onResult(boolean state, List<T> beans) {
                if (state) {
                    RNetBaseFragment.this.onResult(beans);
                } else {
                    ToastHelper.showToast("请求失败", Url.context);
                }
            }
        });
    }

    protected abstract void parseJson(List<T> beans, JSONObject jsonObject) throws JSONException;

    protected abstract void onResult(List<T> beans);
}
