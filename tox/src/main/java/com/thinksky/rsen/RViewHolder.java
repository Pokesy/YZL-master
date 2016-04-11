package com.thinksky.rsen;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.lang.reflect.Field;

/**
 * Created by jiao on 16-02-24-024.
 */
public class RViewHolder extends RecyclerView.ViewHolder {
    public View itemView;

    public RViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
    }

    public View v(@IdRes int resId) {
        return itemView.findViewById(resId);
    }

    public View view(@IdRes int resId) {
        return v(resId);
    }

    /**
     * 返回 TextView
     */
    public TextView tV(@IdRes int resId) {
        return (TextView) v(resId);
    }

    public TextView textView(@IdRes int resId) {
        return tV(resId);
    }
    /**
     * 返回 EditText
     */
    public EditText ET(@IdRes int resId) {
        return (EditText) v(resId);
    }

    public EditText editText(@IdRes int resId) {
        return ET(resId);
    }


    /**
     * 返回 ImageView
     */
    public ImageView imgV(@IdRes int resId) {
        return (ImageView) v(resId);
    }

    public ImageView imageView(@IdRes int resId) {
        return imgV(resId);
    }

    /**
     * 返回 ViewGroup
     */
    public ViewGroup groupV(@IdRes int resId) {
        return (ViewGroup) v(resId);
    }

    public ViewGroup viewGroup(@IdRes int resId) {
        return groupV(resId);
    }

    public View viewByName(String name) {
        View view = v(getIdByName(name, "id"));
        return view;
    }

    /**
     * 根据name, 在主题中 寻找资源id
     */
    private int getIdByName(String name, String type) {
        Context context = itemView.getContext();
        return context.getResources().getIdentifier(name, type, context.getPackageName());
    }

    public void fillView(Object bean) {
        fillView(bean, true);
    }

    public void fillView(Object bean, boolean imgRound) {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field f : fields) {
            String name = f.getName();
            try {
                String value = (String) f.get(bean);
                View view = viewByName(name);
                if (view instanceof TextView) {
                    ((TextView) view).setText(value);
                } else if (view instanceof ImageView) {
                    if (imgRound) {
                        ResUtil.setRoundImage(value, (ImageView) view);
                    } else {
                        ImageLoader.getInstance().displayImage(value, (ImageView) view);
                    }
                }

            } catch (Exception e) {
            }
        }
    }
}
