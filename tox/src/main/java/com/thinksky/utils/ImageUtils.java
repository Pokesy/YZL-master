package com.thinksky.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片处理工具类<BR>
 *
 * @author zhaozeyang
 * @version [Paitao Client V20130911, 2013-10-10]
 */
public class ImageUtils {
  /**
   * 将图片转化给byte[]操作<BR>
   *
   * @param bm 图片对象
   * @param quality 图片质量
   * @return 图片byte[]
   */
  public static byte[] bitmap2Bytes(Bitmap bm, int quality) {
    byte[] bytes;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bm.compress(CompressFormat.JPEG, quality, baos);
    bytes = baos.toByteArray();
    try {
      baos.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return bytes;
  }

  public static File byte2File(byte[] buf, String filePath, String fileName) {
    BufferedOutputStream bos = null;
    FileOutputStream fos = null;
    File file = null;
    try {
      File dir = new File(filePath);
      if (!dir.exists()) {
        dir.mkdirs();
      }
      file = new File(filePath, fileName);
      file.deleteOnExit();
      fos = new FileOutputStream(file);
      bos = new BufferedOutputStream(fos);
      bos.write(buf);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (bos != null) {
        try {
          bos.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (fos != null) {
        try {
          fos.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return file;
  }
}
