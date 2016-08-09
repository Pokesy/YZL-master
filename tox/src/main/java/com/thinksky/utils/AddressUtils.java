/*
 * 文件名: AddressUtils
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/5/31
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.utils;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/5/31]
 */
public class AddressUtils {

  public static List<AddressModel> getAddress(Context context) {
    List<AddressModel> list = new ArrayList<>();
    InputStream is = null;
    InputStreamReader reader = null;
    BufferedReader bufferedReader = null;
    try {
      is = context.getAssets().open("location.txt");
      reader = new InputStreamReader(is);
      bufferedReader = new BufferedReader(reader);
      String json = bufferedReader.readLine();
      Gson gson = new Gson();
      List<AddressModel> jsonList = gson.fromJson(json, new TypeToken<List<AddressModel>>() {
      }.getType());
      if (null != jsonList) {
        list.addAll(jsonList);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (null != is) {
        try {
          is.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (null != reader) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (null != bufferedReader) {
        try {
          bufferedReader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return list;
  }

  public class AddressModel {

    /**
     * city : [{"id":"110100","level":"2","name":"北京市","upid":"110000"},{"id":"110200","level":"2","name":"县","upid":"110000"}]
     * id : 110000
     * level : 1
     * name : 北京市
     * upid : 0
     */

    private String id;
    private String level;
    private String name;
    private String upid;
    /**
     * id : 110100
     * level : 2
     * name : 北京市
     * upid : 110000
     */

    private List<CityBean> city;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getLevel() {
      return level;
    }

    public void setLevel(String level) {
      this.level = level;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getUpid() {
      return upid;
    }

    public void setUpid(String upid) {
      this.upid = upid;
    }

    public List<CityBean> getCity() {
      return city;
    }

    public void setCity(List<CityBean> city) {
      this.city = city;
    }

    public class CityBean {
      private String id;
      private String level;
      private String name;
      private String upid;

      public String getId() {
        return id;
      }

      public void setId(String id) {
        this.id = id;
      }

      public String getLevel() {
        return level;
      }

      public void setLevel(String level) {
        this.level = level;
      }

      public String getName() {
        return name;
      }

      public void setName(String name) {
        this.name = name;
      }

      public String getUpid() {
        return upid;
      }

      public void setUpid(String upid) {
        this.upid = upid;
      }
    }
  }
}
