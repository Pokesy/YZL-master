package com.thinksky.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.thinksky.holder.BaseApplication;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.info.WendaXianqingInfo;
import com.thinksky.injection.GlobalModule;
import com.thinksky.net.UiRpcSubscriber1;
import com.thinksky.net.UiRpcSubscriberSimple;
import com.thinksky.net.rpc.model.BaseModel;
import com.thinksky.net.rpc.model.QuestionDetailModel;
import com.thinksky.net.rpc.service.AppService;
import com.thinksky.net.rpc.service.NetConstant;
import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.serviceinjection.DaggerServiceComponent;
import com.thinksky.serviceinjection.ServiceModule;
import com.thinksky.tox.ImagePagerActivity;
import com.thinksky.tox.R;
import com.thinksky.ui.common.TitleBar;
import com.thinksky.ui.profile.ProfileIntentFactory;
import com.thinksky.utils.MyJson;
import com.thinksky.utils.UserUtils;
import com.thinksky.utils.imageloader.ImageLoader;
import com.tox.BaseApi;
import com.tox.BaseFunction;
import com.tox.ToastHelper;
import com.tox.Url;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import org.json.JSONObject;


public class QuestionDetailActivity extends BaseBActivity implements View.OnClickListener {
  private static final String CATEGORY_HONGYU = "5";
  private static final String CATEGORY_LONGYU = "6";
  private static final String CATEGORY_HUYU = "9";
  private static final String CATEGORY_QICAISHENXIAN = "7";
  private static final String CATEGORY_OTHER = "8";
  private TextView title;
  private TextView best_answer;
  private TextView money;
  private TextView creat_time;
  private TextView content;
  private TextView nickname;
  private TextView huida;
  private ListView listView;
  private Button btn_huida;
  List<WendaXianqingInfo> mListData;
  AnswerListAdapter mListAdapter;
  private String mQuestionId;
  private BaseApi baseApi;
  private String session_id;
  private ImageView iv1, iv2, iv3, wutu;
  public static boolean upFlag = false;
  public static boolean mHasBestAnswer = false;
  private static boolean mIsAuthor = false;
  private LinearLayout reply_button;
  private LinearLayout reply_box;
  private EditText reply_editText;
  private TextView sendPostButton;
  private List<String> listFlag = new ArrayList<>();
  private String string = null;
  private String mAuthorId;
  private ProgressDialog mProgressDialog;
  private RelativeLayout img_layout;
  private ScrollView mScrollView;
  private TitleBar mTitleBar;
  private TextView mAuthorNameView;
  private ImageView mAuthorAvatarView;

  @Inject
  AppService mAppService;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    inject();
    setContentView(R.layout.activity_question_detail);
    mScrollView = (ScrollView) findViewById(R.id.post_scroll);
    title = (TextView) findViewById(R.id.question_title);
    best_answer = (TextView) findViewById(R.id.best_answer);
    img_layout = (RelativeLayout) findViewById(R.id.img_layout);
    money = (TextView) findViewById(R.id.money);
    creat_time = (TextView) findViewById(R.id.creat_time);
    content = (TextView) findViewById(R.id.content);
    nickname = (TextView) findViewById(R.id.nickname);
    wutu = (ImageView) findViewById(R.id.wutu);
    huida = (TextView) findViewById(R.id.huida);
    listView = (ListView) findViewById(R.id.listView);
    mTitleBar = (TitleBar) findViewById(R.id.title_bar);
    mAuthorNameView = (TextView) findViewById(R.id.author);
    mAuthorAvatarView = (ImageView) findViewById(R.id.author_avatar);

    btn_huida = (Button) findViewById(R.id.btn_huida);
    iv1 = (ImageView) findViewById(R.id.iv_1);
    iv2 = (ImageView) findViewById(R.id.iv_2);
    iv3 = (ImageView) findViewById(R.id.iv_3);


    reply_button = (LinearLayout) findViewById(R.id.reply_button);
    reply_box = (LinearLayout) findViewById(R.id.reply_box);
    reply_editText = (EditText) findViewById(R.id.reply_editText);
    sendPostButton = (TextView) findViewById(R.id.sendPostButn);
    //发表回复文本框的事件监听器
    reply_editText.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (count != 0) {
          sendPostButton.setBackgroundResource(R.drawable.forum_enable_btn_send);
          sendPostButton.setTextColor(Color.WHITE);
        } else {
          sendPostButton.setBackgroundResource(R.drawable.border);
          sendPostButton.setTextColor(Color.parseColor("#A9ADB0"));
        }
      }

      @Override
      public void afterTextChanged(Editable s) {
        if (s.length() != 0) {
          sendPostButton.setBackgroundResource(R.drawable.forum_enable_btn_send);
          sendPostButton.setTextColor(Color.WHITE);
        } else {
          sendPostButton.setBackgroundResource(R.drawable.border);
          sendPostButton.setTextColor(Color.parseColor("#A9ADB0"));
        }
      }
    });

    btn_huida.setOnClickListener(this);
    sendPostButton.setOnClickListener(this);
    mListData = new ArrayList<>();
    baseApi = new BaseApi();
    session_id = baseApi.getSeesionId();
    mQuestionId = getIntent().getStringExtra("question_id");
    initDetailData();
    mScrollView.smoothScrollTo(0, 0);

    mTitleBar.setLeftImgMenu(R.drawable.icon_title_bar_back, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    mTitleBar.setMiddleTitle("问答");
  }

  private void inject() {
    DaggerServiceComponent.builder().globalModule(new GlobalModule(BaseApplication.getApplication
        ())).serviceModule(new ServiceModule())
        .build().inject(this);
  }


  private void showProgressDialog() {
    if (null == mProgressDialog) {
      mProgressDialog = new ProgressDialog(this);
    }
    if (mProgressDialog.isShowing()) {
      mProgressDialog.dismiss();
    }
    mProgressDialog.show();
  }

  private void cancelDialog() {
    if (null != mProgressDialog && mProgressDialog.isShowing()) {
      mProgressDialog.cancel();
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    cancelDialog();
  }

  private void initDetailData() {
    manageRpcCall(mAppService.getQuestionDetail(Url.SESSIONID, mQuestionId), new
        UiRpcSubscriber1<QuestionDetailModel>(this) {


          @Override
          protected void onSuccess(QuestionDetailModel questionDetailModel) {
            final QuestionDetailModel.ListBean bean = questionDetailModel.getList().get(0);
            mAuthorId = bean.getUid();
            mIsAuthor = TextUtils.equals(mAuthorId, Url.USERID);
            if (mIsAuthor) {
              mTitleBar.setSearchBtn(R.drawable.icon_delete, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  AlertDialog dialog = new AlertDialog.Builder(QuestionDetailActivity.this).
                      setMessage(R.string.activity_question_detail_delete_alert)
                      .setPositiveButton(R.string.btn_confirm, new DialogInterface
                          .OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                          dialog.cancel();
                          // 删除
                          manageRpcCall(mAppService.deleteQuestion(Url.SESSIONID, bean.getId()),
                              new UiRpcSubscriberSimple<BaseModel>(QuestionDetailActivity.this) {


                                @Override
                                protected void onSuccess(BaseModel baseModel) {
                                  Toast.makeText(QuestionDetailActivity.this, R.string
                                      .activity_question_detail_delete_success, Toast
                                      .LENGTH_SHORT).show();
                                  getComponent().getGlobalBus().post(new AnswerChangedEvent());
                                  finish();
                                }

                                @Override
                                protected void onEnd() {

                                }
                              });
                        }
                      }).setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener
                          () {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                          dialog.cancel();
                        }
                      }).create();
                  dialog.show();
                }
              });
            } else {
              mTitleBar.getSearchView().setSelected(bean.getIs_collection() == 1);
              mTitleBar.setSearchBtn(R.drawable.icon_collect_white_selector, new View
                  .OnClickListener() {


                @Override
                public void onClick(View v) {
                  // TODO 收藏
                  if (bean.getIs_collection() == 1) {
                    manageRpcCall(mAppService.cancelQuestionBookmark(Url.SESSIONID, bean.getId())
                        , new
                            UiRpcSubscriberSimple<BaseModel>(QuestionDetailActivity.this) {


                              @Override
                              protected void onSuccess(BaseModel baseModel) {
                                bean.setIs_collection(0);
                                mTitleBar.getSearchView().setSelected(bean.getIs_collection() == 1);
                                getComponent().getGlobalBus().post(new AnswerChangedEvent());
                              }

                              @Override
                              protected void onEnd() {

                              }
                            });
                  } else {
                    manageRpcCall(mAppService.questionBookmark(Url.SESSIONID, bean.getId())
                        , new
                            UiRpcSubscriberSimple<BaseModel>(QuestionDetailActivity.this) {


                              @Override
                              protected void onSuccess(BaseModel baseModel) {
                                bean.setIs_collection(1);
                                mTitleBar.getSearchView().setSelected(bean.getIs_collection() == 1);
                                getComponent().getGlobalBus().post(new AnswerChangedEvent());
                              }

                              @Override
                              protected void onEnd() {

                              }
                            });
                  }
                }
              });
            }

            final List<String> images = bean.getQuestionimages();
            if (null != images && images.size() > 0) {
              img_layout.setVisibility(View.VISIBLE);
              int size = images.size();
              iv1.setVisibility(size > 0 ? View.VISIBLE : View.GONE);
              iv2.setVisibility(size > 1 ? View.VISIBLE : View.GONE);
              iv3.setVisibility(size > 2 ? View.VISIBLE : View.GONE);

              for (int i = 0; i < size; i++) {
                String url = RsenUrlUtil.URL_BASE + images.get(i);

                ImageView imageView = null;
                if (i == 0) {
                  imageView = iv1;
                } else if (i == 1) {
                  imageView = iv2;
                } else if (i == 2) {
                  imageView = iv3;
                }

                if (imageView != null) {
                  try {
                    ImageLoader.loadOptimizedHttpImage(QuestionDetailActivity.this, url).error(R
                        .drawable
                        .picture_no).placeholder(R.drawable.picture_no).into(imageView);
                  } catch (Exception e) {
                    e.printStackTrace();
                  }
                  final int in = i;
                  imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      Intent intent = new Intent(QuestionDetailActivity.this, ImagePagerActivity
                          .class);
                      Bundle bundle = new Bundle();
                      bundle.putStringArrayList("image_urls", (ArrayList<String>) images);
                      bundle.putInt("image_index", in);
                      intent.putExtras(bundle);
                      startActivity(intent);
                    }
                  });
                }
              }

            } else {
              img_layout.setVisibility(View.GONE);
            }

            mAuthorNameView.setText(UserUtils.getUserName(QuestionDetailActivity.this, bean
                .getUid(), null == bean.getUser() ? "" : bean.getUser().getNickname()));
            ImageLoader.loadOptimizedHttpImage(QuestionDetailActivity.this, null == bean.getUser
                () ? "" : NetConstant.BASE_URL
                + bean.getUser().getAvatar64()).placeholder(R.drawable.side_user_avatar)
                .bitmapTransform(new CropCircleTransformation(QuestionDetailActivity.this)).into
                (mAuthorAvatarView);
            findViewById(R.id.author_container).setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                if (TextUtils.isEmpty(bean.getUid())) {
                  return;
                }
                startActivity(new Intent(ProfileIntentFactory.makeIntent(QuestionDetailActivity
                    .this, bean.getUid())));
              }
            });

            //问题
            title.setText(bean.getTitle());
            if (TextUtils.equals(bean.getBest_answer(), "0")) {
              best_answer.setText("求助中");
              best_answer.setSelected(false);
              mHasBestAnswer = false;
            } else {
              best_answer.setText("已解决");
              best_answer.setSelected(true);
              mHasBestAnswer = true;
            }
            money.setText(bean.getScore());
            creat_time.setText(bean.getCreate_time());
            content.setText(bean.getDescription1().replace("\\n", "\n"));

            switch (bean.getCategory()) {
              case CATEGORY_HONGYU:
                nickname.setText(R.string.fish_category_hongyu);
                break;
              case CATEGORY_HUYU:
                nickname.setText(R.string.fish_category_huyu);
                break;
              case CATEGORY_LONGYU:
                nickname.setText(R.string.fish_category_longyu);
                break;
              case CATEGORY_QICAISHENXIAN:
                nickname.setText(R.string.fish_category_qicaishenxian);
                break;
              case CATEGORY_OTHER:
                nickname.setText(R.string.fish_category_other);
                break;
            }

            huida.setText(bean.getAnswer_num() + "条回答");
            if (("0").equals(bean.getAnswer_num())) {
              wutu.setVisibility(View.VISIBLE);
            } else {
              wutu.setVisibility(View.GONE);
              mListAdapter = new AnswerListAdapter(QuestionDetailActivity.this, bean
                  .getQuestionAnswer());
              listView.setAdapter(mListAdapter);

            }
            mScrollView.smoothScrollTo(0, 0);
          }

          @Override
          protected void onEnd() {

          }
        });

  }

  @Override
  public void onClick(View v) {
    int id = v.getId();
    switch (id) {
      //回复按钮
      case R.id.btn_huida:
        if (BaseFunction.isLogin()) {

          reply_button.setVisibility(View.GONE);
          reply_box.setVisibility(View.VISIBLE);
          //自动打开软键盘并获取焦点
          reply_editText.setFocusable(true);
          reply_editText.requestFocus();
          InputMethodManager imm = (InputMethodManager) getSystemService(Context
              .INPUT_METHOD_SERVICE);
          imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

        } else {
          ToastHelper.showToast("请登录后操作", this);
        }
        break;
      //发表评论按钮
      case R.id.sendPostButn:
        showProgressDialog();
        if (reply_editText.getText().length() > 0 && !"".equals(reply_editText.getText().toString
            ().trim())) {
          sendPostButton.setBackgroundResource(R.drawable.border);
          sendPostButton.setTextColor(Color.parseColor("#A9ADB0"));
          reply_button.setVisibility(View.VISIBLE);
          reply_box.setVisibility(View.GONE);
          InputMethodManager imm2 = (InputMethodManager) getSystemService(Context
              .INPUT_METHOD_SERVICE);
          if (imm2.isActive()) {
            imm2.hideSoftInputFromWindow(reply_editText.getWindowToken(), InputMethodManager
                .HIDE_NOT_ALWAYS);
          }
          Log.e("评论内容>>>>>>>>>>", reply_editText.getText().toString());
          send();

//                    initFlag(false,true);
//                    groupApi.postComment(post_id+"",reply_editText.getText().toString());
          reply_editText.setText(null);

        } else {
          ToastHelper.showToast("评论不能为空", this);
        }
        break;

      case R.id.back_menu:
        finish();
        break;
      default:
        break;
    }
  }

  private void send() {

    RsenUrlUtil.execute(this, RsenUrlUtil.URL_SEND_QUESTION_ANSWER, new RsenUrlUtil
        .OnJsonResultListener<DiscoverFragment.FXBean>() {
      @Override
      public void onNoNetwork(String msg) {
        ToastHelper.showToast(msg, Url.context);
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            cancelDialog();
          }
        });

      }

      @Override
      public Map getMap() {
        Map map = new HashMap();
        map.put("session_id", session_id);
        map.put("content", reply_editText.getText().toString().trim());
        map.put("question_id", mQuestionId);
        return map;

      }

      @Override
      public void onParseJsonBean(List<DiscoverFragment.FXBean> beans, JSONObject jsonObject) {
        String result = jsonObject.toString();
        DiscoverFragment.FXBean discoverInfo = JSON.parseObject(result, DiscoverFragment.FXBean
            .class);
        beans.add(discoverInfo);
      }

      @Override
      public void onResult(boolean state, List beans) {
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            cancelDialog();
            initDetailData();
            wutu.setVisibility(View.GONE);
            getComponent().getGlobalBus().post(new AnswerChangedEvent());
          }
        });
      }
    });

  }

  public class AnswerListAdapter extends BaseAdapter {

    private Context context;

    private List<QuestionDetailModel.ListBean
        .QuestionAnswerBean> list;

    public AnswerListAdapter(Context context, List<QuestionDetailModel.ListBean
        .QuestionAnswerBean> list) {
      this.context = context;
      this.list = list;
    }


    @Override
    public int getCount() {
      if (list == null) {
        return 0;
      }
      return list.size();
    }

    @Override
    public Object getItem(int position) {
      return list.get(position);
    }

    @Override
    public long getItemId(int position) {
      return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
      final ViewHolder holder;
      if (convertView == null) {
        convertView = LayoutInflater.from(context).inflate(R.layout
            .activity_wentixiangqing_list_item_zjw, null);
        holder = new ViewHolder();
        holder.avatar32 = (ImageView) convertView.findViewById(R.id.avatar32);
        holder.dianzan = (ImageView) convertView.findViewById(R.id.dianzan);
        holder.accept_watermark = (ImageView) convertView.findViewById(R.id.accept_watermark);
        holder.nickname = (TextView) convertView.findViewById(R.id.nickname);
        holder.content = (TextView) convertView.findViewById(R.id.content);
        holder.creat_time = (TextView) convertView.findViewById(R.id.creat_time);
        holder.reply_count = (TextView) convertView.findViewById(R.id.reply_count);
        holder.btnAccept = (TextView) convertView.findViewById(R.id.accept);
        holder.mIconBestAnswer = (ImageView) convertView.findViewById(R.id.icon_best_answer);
        convertView.setTag(holder);
      } else {
        holder = (ViewHolder) convertView.getTag();
      }

      final QuestionDetailModel.ListBean
          .QuestionAnswerBean bean = list.get(position);
      // 如果登录并且当前登录账号是问题的作者并且回答不是当前登录的账号
      if (BaseFunction.isLogin() && mIsAuthor
          && !TextUtils.equals(bean.getUid(), Url.USERID)) {
        holder.btnAccept.setVisibility(mHasBestAnswer ? View.GONE : View.VISIBLE);
      } else {
        holder.btnAccept.setVisibility(View.GONE);
      }
      holder.mIconBestAnswer.setVisibility(bean.getIsbest() == 0 ? View.GONE : View.VISIBLE);
      holder.accept_watermark.setVisibility(bean.getIsbest() == 0 ? View.GONE : View.VISIBLE);
      try {
        ImageLoader.loadOptimizedHttpImage(QuestionDetailActivity.this, RsenUrlUtil.URL_BASE + bean
            .getUser().getAvatar32())
            .bitmapTransform(new CropCircleTransformation(QuestionDetailActivity.this)).placeholder
            (R.drawable.side_user_avatar).error(R.drawable.side_user_avatar)
            .dontAnimate().into(holder.avatar32);
      } catch (Exception e) {
        e.printStackTrace();
      }
      holder.nickname.setText(UserUtils.getUserName(QuestionDetailActivity.this, bean.getUser()
          .getUid(), bean.getUser().getNickname()));
      holder.avatar32.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (TextUtils.isEmpty(bean.getUser().getUid())) {
            return;
          }
          startActivity(ProfileIntentFactory.makeIntent(QuestionDetailActivity.this, bean.getUser
              ().getUid()));
        }
      });

      holder.nickname.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (TextUtils.isEmpty(bean.getUser().getUid())) {
            return;
          }
          startActivity(ProfileIntentFactory.makeIntent(QuestionDetailActivity.this, bean.getUser
              ().getUid()));
        }
      });
      holder.content.setText(bean.getContent().replace("\\n", "\n"));
      holder.creat_time.setText(MyJson.getStandardDate(bean.getCreate_time()));
      holder.reply_count.setText(bean.getSupport_count());


      if (!(bean.getIs_supported().equals("0"))) {//已点赞
        holder.dianzan.setBackgroundResource(R.drawable.icon_like_blue_stroke);
        string = bean.getIs_supported();
        listFlag.add(string);

      } else {
        holder.dianzan.setBackgroundResource(R.drawable.icon_like_blue);
        upFlag = false;

      }


            /*点击图标和文本,都支持点赞操作*/
      holder.dianzan.setOnClickListener(new DianZanListener(holder, bean));
      holder.reply_count.setOnClickListener(new DianZanListener(holder, bean));
//
      holder.reply_count.setOnClickListener(new DianZanListener(holder, bean));

            /*采纳*/
      holder.btnAccept.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          // 如果我是问题的提问者，并且当前答案啊不是自己的答案
          if (mIsAuthor && !bean.getUid().equals(Url.USERID)) {

            manageRpcCall(mAppService.acceptAnswer(session_id, list.get(position).getId()), new
                UiRpcSubscriber1<BaseModel>(QuestionDetailActivity.this) {


                  @Override
                  protected void onSuccess(BaseModel baseModel) {
                    runOnUiThread(new Runnable() {
                      @Override
                      public void run() {
                        holder.btnAccept.setVisibility(View.GONE);
                        holder.accept_watermark.setVisibility(View.VISIBLE);
                        initDetailData();
                        getComponent().getGlobalBus().post(new AnswerChangedEvent());
                      }
                    });

                    notifyDataSetChanged();
                  }

                  @Override
                  protected void onEnd() {

                  }
                });
          } else {
            ToastHelper.showToast("不能采纳自己的回答", Url.context);
          }

        }
      });

      return convertView;
    }

    /*点赞的实现方法*/
    public class DianZanListener implements View.OnClickListener {

      final ViewHolder holder;
      final QuestionDetailModel.ListBean
          .QuestionAnswerBean bean;

      DianZanListener(ViewHolder holder, QuestionDetailModel.ListBean
          .QuestionAnswerBean bean) {
        this.holder = holder;
        this.bean = bean;
      }


      @Override
      public void onClick(View v) {
        if (BaseFunction.isLogin()) {
          if (TextUtils.equals(Url.USERID, bean.getUid())) {
            Toast.makeText(context, "不能给自己点赞", Toast.LENGTH_SHORT).show();
            return;
          }
          if (bean.getIs_supported().equals("0")) {

            holder.reply_count.setText(Integer.parseInt(bean.getSupport_count()) + 1 + "");
            holder.dianzan.setBackgroundResource(R.drawable.icon_like_blue_stroke);
//
            RsenUrlUtil.execute(QuestionDetailActivity.this, RsenUrlUtil
                .URL_SUPPORT_QUESTION_ANSWER, new
                RsenUrlUtil.OnJsonResultListener<DiscoverFragment.FXBean>() {
                  @Override
                  public void onNoNetwork(String msg) {
                    ToastHelper.showToast(msg, Url.context);
                  }

                  @Override
                  public Map getMap() {
                    Map map = new HashMap();
                    map.put("session_id", session_id);
                    map.put("answerid", bean.getId());
                    return map;

                  }

                  @Override
                  public void onParseJsonBean(List<DiscoverFragment.FXBean> beans, JSONObject
                      jsonObject) {
                    String result = jsonObject.toString();
                    DiscoverFragment.FXBean discoverInfo = JSON.parseObject(result, DiscoverFragment
                        .FXBean.class);
                    beans.add(discoverInfo);
                  }

                  @Override
                  public void onResult(boolean state, List beans) {

                    if (state) {
                      bean.setIs_supported("1");
                      ToastHelper.showToast("点赞成功", Url.context);
                    } else {
                      ToastHelper.showToast("请求失败", Url.context);
                    }
//

                  }
                });
          } else {
            ToastHelper.showToast("点赞失败，重复点赞", QuestionDetailActivity.this);
          }

        } else {
          ToastHelper.showToast("请先登录", QuestionDetailActivity.this);
        }
      }
    }

  }

  static class ViewHolder {
    ImageView avatar32;
    ImageView dianzan;
    ImageView accept_watermark;
    ImageView mIconBestAnswer;
    TextView nickname;
    TextView content;
    TextView creat_time;
    TextView reply_count;
    TextView btnAccept;
  }

  public static class DianzanBean {
    public String success;
    public String message;
  }

  public class AnswerChangedEvent {

  }
}
