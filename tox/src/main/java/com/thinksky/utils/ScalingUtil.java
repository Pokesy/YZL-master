package com.thinksky.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * 对图片进行解码和缩放的工具类<BR>
 *
 * @author chunjiang.shieh <chunjiang.shieh@gmail.com>
 * @version [Paitao Client V20130911, 2013-10-30]
 */
public class ScalingUtil {

  /**
   * 解码文件图片，指定解码后图片的宽高，并按缩放逻辑进行缩放(fit or crop)
   *
   * @param pathName 文件的路径名称
   * @param dstWidth 解码后图片最大的宽度区域
   * @param dstHeight 解码后图片最大的高度区域
   * @param scalingLogic 使用缩放的逻辑，以避免图片拉伸变形
   * @return 返回解码后的位图
   */
  public static Bitmap decodeFile(String pathName, int dstWidth, int dstHeight,
                                  ScalingLogic scalingLogic) {
    Options options = new Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(pathName, options);
    int inSampleSize = calculateSampleSize(options, dstWidth, dstHeight, scalingLogic);

    options.inJustDecodeBounds = false;
    options.inSampleSize = inSampleSize;

    return BitmapFactory.decodeFile(pathName, options);
  }

  /**
   * 创建现有位图的缩放位图
   *
   * @param unscaledBitmap 准备缩放的源位图
   * @param dstWidth 希望缩放后位图的宽度
   * @param dstHeight 希望缩放后位图的高度
   * @param scalingLogic 使用缩放的逻辑，以避免图片拉伸变形
   * @return 返回缩放后的新位图
   */
  public static Bitmap createScaledBitmap(Bitmap unscaledBitmap, int dstWidth, int dstHeight,
                                          ScalingLogic scalingLogic) {
    if (unscaledBitmap == null) {
      return null;
    }
    Rect srcRect =
        calculateSrcRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(), dstWidth, dstHeight,
            scalingLogic);
    Rect dstRect =
        calculateDstRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(), dstWidth, dstHeight,
            scalingLogic);
    Bitmap scaledBitmap = Bitmap.createBitmap(dstRect.width(), dstRect.height(), Config.ARGB_8888);
    Canvas canvas = new Canvas(scaledBitmap);
    canvas.drawBitmap(unscaledBitmap, srcRect, dstRect, new Paint(Paint.FILTER_BITMAP_FLAG));

    return scaledBitmap;
  }

  /**
   * 位图的缩放逻辑<BR>
   *
   * @author chunjiang.shieh <chunjiang.shieh@gmail.com>
   * @version [Paitao Client V20130911, 2013-10-30]
   */
  public enum ScalingLogic {
    /**
     * 在最大区域内裁剪图片
     */
    CROP,
    /**
     * 图片适应最大区域
     */
    FIT
  }

  /**
   * 计算图片的缩放比<BR>
   *
   * @param options Options对象
   * @param dstWidth 目标图片的宽度
   * @param dstHeight 目标图片的高度
   * @param scalingLogic 缩放逻辑
   * @return 返回解码时的缩放因子
   */
  public static int calculateSampleSize(Options options, int dstWidth, int dstHeight,
                                        ScalingLogic scalingLogic) {

    int srcWidth = options.outWidth;
    int srcHeight = options.outHeight;
    if (scalingLogic == ScalingLogic.FIT) {
      final float srcAspect = (float) srcWidth / (float) srcHeight;
      final float dstAspect = (float) dstWidth / (float) dstHeight;

      if (srcAspect > dstAspect) {
        return srcWidth / dstWidth;
      } else {
        return srcHeight / dstHeight;
      }
    } else {
      final float srcAspect = (float) srcWidth / (float) srcHeight;
      final float dstAspect = (float) dstWidth / (float) dstHeight;

      if (srcAspect > dstAspect) {
        return srcHeight / dstHeight;
      } else {
        return srcWidth / dstWidth;
      }
    }
  }

  /**
   * 计算源矩形进行缩放位图
   *
   * @param srcWidth 源图片的宽度
   * @param srcHeight 源图片的高度
   * @param dstWidth 目标宽度
   * @param dstHeight 目标高度
   * @param scalingLogic 使用缩放的逻辑，以避免图片拉伸变形
   * @return 返回优化后的源矩形
   */
  public static Rect calculateSrcRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight,
                                      ScalingLogic scalingLogic) {
    if (scalingLogic == ScalingLogic.CROP) {
      final float srcAspect = (float) srcWidth / (float) srcHeight;
      final float dstAspect = (float) dstWidth / (float) dstHeight;

      if (srcAspect > dstAspect) {
        final int srcRectWidth = (int) (srcHeight * dstAspect);
        final int srcRectLeft = (srcWidth - srcRectWidth) / 2;
        return new Rect(srcRectLeft, 0, srcRectLeft + srcRectWidth, srcHeight);
      } else {
        final int srcRectHeight = (int) (srcWidth / dstAspect);
        final int scrRectTop = (srcHeight - srcRectHeight) / 2;
        return new Rect(0, scrRectTop, srcWidth, scrRectTop + srcRectHeight);
      }
    } else {
      return new Rect(0, 0, srcWidth, srcHeight);
    }
  }

  /**
   * 计算目标矩形进行缩放位图
   *
   * @param srcWidth 源图片的宽度
   * @param srcHeight 源图片的高度
   * @param dstWidth 目标宽度
   * @param dstHeight 目标高度
   * @param scalingLogic 使用缩放的逻辑，以避免图片拉伸变形
   * @return 返回优化后的目标矩形
   */
  public static Rect calculateDstRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight,
                                      ScalingLogic scalingLogic) {
    if (scalingLogic == ScalingLogic.FIT) {
      final float srcAspect = (float) srcWidth / (float) srcHeight;
      final float dstAspect = (float) dstWidth / (float) dstHeight;

      if (srcAspect > dstAspect) {
        return new Rect(0, 0, dstWidth, (int) (dstWidth / srcAspect));
      } else {
        return new Rect(0, 0, (int) (dstHeight * srcAspect), dstHeight);
      }
    } else {
      return new Rect(0, 0, dstWidth, dstHeight);
    }
  }
}
