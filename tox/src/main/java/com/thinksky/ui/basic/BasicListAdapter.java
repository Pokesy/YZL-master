/*
 * 文件名: BasicListAdapter.java
 * 版    权：  Copyright Paitao Tech. Co. Ltd. All Rights Reserved.
 * 描    述: 列表数据的基类Adapter，提供简单的add remove操作
 * 创建人: chunjiang.shieh <chunjiang.shieh@gmail.com>
 * 创建时间:2013-10-19
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.ui.basic;

import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 列表数据的基类Adapter，提供简单的add remove操作<BR>
 *
 * @param <T> 绑定到列表的数据类型
 * @author chunjiang.shieh <chunjiang.shieh@gmail.com>
 * @version [Paitao Client V20130911, 2013-10-19]
 */
public abstract class BasicListAdapter<T> extends BaseAdapter {

  /**
   * 绑定到列表的数据集合
   */
  private final List<T> mList;

  /**
   * 无参构造器
   */
  public BasicListAdapter() {
    super();
    this.mList = new ArrayList<T>();
  }

  /**
   * 按原来顺序把返回的列表对象添加到list中<BR>
   *
   * @param list 待添加列表
   * @return 返回添加是否成功
   */
  public boolean addAll(List<? extends T> list) {
    if (null == list) {
      return false;
    }
    return this.mList.addAll(list);
  }

  /**
   * 按原来顺序把返回的列表对象添加到list头部<BR>
   *
   * @param list 待添加列表
   * @return 返回添加是否成功
   */
  public boolean addAllTop(List<? extends T> list) {
    if (null == list) {
      return false;
    }
    return this.mList.addAll(0, list);
  }

  /**
   * 按反序把返回的列表对象添加到List中<BR>
   *
   * @param list 待添加列表
   */
  public void addReverseAll(List<? extends T> list) {
    if (null == list) {
      return;
    }
    Collections.reverse(list);
    mList.addAll(list);
  }

  /**
   * 添加数据到顶部<BR>
   *
   * @param list 要添加的数据
   */
  public void addReverseAllTop(List<? extends T> list) {
    if (null == list) {
      return;
    }
    Collections.reverse(list);
    mList.addAll(0, list);
  }

  /**
   * 获取所有的列表数据<BR>
   *
   * @return 返回所有的集合数据
   */
  public List<T> getAllList() {
    return mList;
  }

  /**
   * 清除集合<BR>
   */
  public void clear() {
    this.mList.clear();
  }

  /**
   * 获取列表个数<BR>
   *
   * @return 返回列表个数
   * @see android.widget.Adapter#getCount()
   */
  @Override
  public int getCount() {
    return mList == null ? 0 : mList.size();
  }

  @Override
  public T getItem(int position) {
    return this.mList.get(position);
  }

  @Override
  public long getItemId(int id) {
    return id;
  }

  @Override
  public boolean hasStableIds() {
    return true;
  }

  @Override
  public boolean isEmpty() {
    return mList == null ? true : mList.isEmpty();
  }

  /**
   * 移除集合中position位置的对象<BR>
   *
   * @param position 位置索引
   * @return 返回移除的对象
   */
  public T remove(int position) {
    return mList.remove(position);
  }

  /**
   * 从列表集合中移除对象<BR>
   *
   * @param t 待移除对象
   */
  public void removeItem(T t) {
    this.mList.remove(t);
  }

  /**
   * 往集合中添加一个对象<BR>
   *
   * @param t 待添加对象
   */
  public void addItem(T t) {
    this.mList.add(t);
  }

  /**
   * 往集合顶部添加一个对象<BR>
   *
   * @param t 待添加对象
   */
  public void addTopItem(T t) {
    this.mList.add(0, t);
  }
}