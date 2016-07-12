package com.thinksky.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.thinksky.tox.R;
import com.thinksky.utils.LoadImg;
import com.thinksky.utils.imageloader.ImageLoader;
import com.tox.ToastHelper;
import com.tox.Url;
import java.io.File;
import java.io.FileOutputStream;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;
import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;

public class ImageDetailFragment extends Fragment {
  private String mImageUrl;
  private PhotoView mImageView;
  private ProgressBar progressBar;
  private LinearLayout saveImage;
  private PhotoViewAttacher mAttacher;
  private Context ctx;
  private LoadImg loadImg;

  public static ImageDetailFragment newInstance(String imageUrl) {
    final ImageDetailFragment f = new ImageDetailFragment();

    final Bundle args = new Bundle();
    args.putString("url", imageUrl);
    f.setArguments(args);

    return f;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mImageUrl = getArguments() != null ? getArguments().getString("url") : null;

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
      savedInstanceState) {
    final View v = inflater.inflate(R.layout.image_detail_fragment, container, false);
    ctx = v.getContext();
    loadImg = new LoadImg(ctx);
    mImageView = (PhotoView) v.findViewById(R.id.image);
    saveImage = (LinearLayout) v.findViewById(R.id.save_image);

    mAttacher = new PhotoViewAttacher(mImageView);
    mAttacher.setScaleType(ImageView.ScaleType.CENTER);
    saveImage.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        if (mImageView.getDrawable() != null) {
          String saveImagePath = (String) mImageView.getTag();
          Bitmap saveBitmap = mImageView.getDrawingCache();
          File file = new File(Url.SAVEIMAGE);
          file.mkdirs();
          saveImagePath = saveImagePath.substring(saveImagePath.lastIndexOf("/", saveImagePath
              .lastIndexOf("/") - 1) + 1, saveImagePath.length());
          saveImagePath = Url.SAVEIMAGE + saveImagePath.replaceAll("[/]", "");
          try {
            FileOutputStream fileOutputStream = new FileOutputStream(saveImagePath);
            saveBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            ToastHelper.showToast("保存到tox/SaveImage文件", ctx);

          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    });
    mAttacher.setOnPhotoTapListener(new OnPhotoTapListener() {

      @Override
      public void onPhotoTap(View arg0, float arg1, float arg2) {
        getActivity().finish();
      }

      @Override
      public void onOutsidePhotoTap() {

      }
    });

    progressBar = (ProgressBar) v.findViewById(R.id.loading);
    return v;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    progressBar.setVisibility(View.VISIBLE);
    final String largeImageUrl = mImageUrl.replace("_100_100", "");
    mImageView.setTag(largeImageUrl);
    final String url = (Url.IMAGE + largeImageUrl).replace("com//", "com/").replace("cn//",
        "cn/").replace("com///", "com/").replace(Url.IMAGE + Url.IMAGE, Url.IMAGE);
    ImageLoader.loadOptimizedHttpImage(getActivity(), url).listener(new RequestListener<String,
        GlideDrawable>() {

      @Override
      public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean
          isFirstResource) {
        progressBar.setVisibility(View.GONE);
        return false;
      }

      @Override
      public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable>
          target, boolean isFromMemoryCache, boolean isFirstResource) {
        progressBar.setVisibility(View.GONE);
        mAttacher.update();
        return false;
      }
    }).placeholder(R.drawable.picture_no).error(R.drawable.picture_no).into(mImageView);

  }
}


