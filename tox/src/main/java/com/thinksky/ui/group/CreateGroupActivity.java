/*
 * 文件名: CreateGroupActivity
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/8/19
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.ui.group;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.squareup.otto.Subscribe;
import com.thinksky.holder.BaseApplication;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.injection.GlobalModule;
import com.thinksky.net.UiRpcSubscriber1;
import com.thinksky.net.UiRpcSubscriberSimple;
import com.thinksky.net.rpc.model.BaseModel;
import com.thinksky.net.rpc.model.GroupDetailModel;
import com.thinksky.net.rpc.model.GroupTypeModelList;
import com.thinksky.net.rpc.service.AppService;
import com.thinksky.net.rpc.service.NetConstant;
import com.thinksky.serviceinjection.DaggerServiceComponent;
import com.thinksky.serviceinjection.ServiceModule;
import com.thinksky.tox.R;
import com.thinksky.ui.common.TitleBar;
import com.thinksky.utils.imageloader.ImageLoader;
import com.tox.Url;
import java.util.List;
import javax.inject.Inject;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/8/19]
 */
public class CreateGroupActivity extends BaseBActivity {
  private static final int REQUEST_CODE_NOTICE = 0x10;
  private static final String BUNDLE_KEY_GROUP_ID = "group_id";
  private static final int EDIT_TYPE_GROUP_NAME = 0;
  private static final int EDIT_TYPE_DESCRIPTION = 1;

  private static final String CATEGORY_HONGYU = "5";
  private static final String CATEGORY_LONGYU = "6";
  private static final String CATEGORY_HUYU = "9";
  private static final String CATEGORY_QICAISHENXIAN = "7";
  private static final String CATEGORY_OTHER = "8";

  @Bind(R.id.title_bar)
  TitleBar titleBar;
  @Bind(R.id.group_logo)
  ImageView mLogoView;
  @Bind(R.id.group_name)
  TextView groupName;
  @Bind(R.id.group_category)
  TextView groupCategory;
  @Bind(R.id.group_type)
  TextView groupType;
  @Bind(R.id.description)
  TextView description;
  @Bind(R.id.notice)
  TextView notice;
  @Bind(R.id.btn_dissolution)
  TextView btnDissolution;

  private String mEditGroupName;
  private String mEditDescription;
  private String mLogoId = null;
  private String mEditType = "0";
  private String mCategoryId;
  private String mGroupId;
  private String mNotice;
  private String mLogoPath;

  @Inject
  AppService mAppService;

  private LogoChoosePresenter mLogoChoosePresenter;
  private String[] mTypeItems;
  private String[] mCategoryItems;
  private List<GroupTypeModelList.ListBean> mCategoryList;

  private AlertDialog mTypeChooseDialog;
  private AlertDialog mCategoryChooseDialog;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    inject();
    mGroupId = getIntent().getStringExtra(BUNDLE_KEY_GROUP_ID);
    mLogoChoosePresenter = new LogoChoosePresenter(this);
    setContentView(R.layout.activity_create_group);
    ButterKnife.bind(this);
    loadData();
  }

  private void loadData() {
    if (TextUtils.isEmpty(mGroupId)) {
      initView();
      return;
    }
    manageRpcCall(mAppService.getGroupDetail(mGroupId), new UiRpcSubscriber1<GroupDetailModel>
        (this) {
      @Override
      protected void onSuccess(GroupDetailModel groupDetailModel) {
        mEditType = groupDetailModel.getList().getType();
        mCategoryId = groupDetailModel.getList().getType_id();
        mEditGroupName = groupDetailModel.getList().getTitle();
        mNotice = groupDetailModel.getList().getNotice();
        mEditDescription = groupDetailModel.getList().getDetail();
        mLogoPath = groupDetailModel.getList().getLogo();
        initView();
      }

      @Override
      protected void onEnd() {

      }
    });
  }

  private void initView() {
    ImageLoader.loadOptimizedHttpImage(this, NetConstant.BASE_URL + mLogoPath).bitmapTransform(new
        CropCircleTransformation(this))
        .placeholder(R.drawable.picture_1_no).into(mLogoView);
    groupName.setText(mEditGroupName);
    description.setText(mEditDescription);
    notice.setText(mNotice);

    mTypeItems = getResources().getStringArray(R.array.group_type_items);
    groupType.setText(TextUtils.isEmpty(mEditType) ? mTypeItems[0] : mTypeItems[Integer.parseInt
        (mEditType)]);

    titleBar.setMiddleTitle(TextUtils.isEmpty(mGroupId) ? R.string.activity_create_group_title :
        R.string.activity_create_group_title_edit);
    titleBar.setLeftImgMenu(R.drawable.arrow_left, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    titleBar.setRightTextBtn(R.string.activity_create_group_title_btn_create, new View
        .OnClickListener() {


      @Override
      public void onClick(View v) {
        if (TextUtils.isEmpty(mEditGroupName)) {
          Toast.makeText(CreateGroupActivity.this, R.string
              .activity_create_group_error_name_null, Toast.LENGTH_SHORT).show();
          return;
        }
        if (TextUtils.isEmpty(mCategoryId)) {
          Toast.makeText(CreateGroupActivity.this, R.string
              .activity_create_group_error_category_null, Toast.LENGTH_SHORT).show();
          return;
        }
        showProgressDialog("", false);
        if (TextUtils.isEmpty(mLogoId)) {
          manageRpcCall(mAppService.createGroupNoLogo(Url.SESSIONID, mEditGroupName, mCategoryId,
              mEditType,
              mEditDescription, mGroupId, mNotice), new UiRpcSubscriber1<BaseModel>
              (CreateGroupActivity
                  .this) {


            @Override
            protected void onSuccess(BaseModel baseModel) {
              Toast.makeText(CreateGroupActivity.this, R.string
                  .activity_create_group_create_success, Toast.LENGTH_SHORT).show();
              getComponent().getGlobalBus().post(new CreateGroupSuccessEvent());
              finish();
            }

            @Override
            protected void onEnd() {
              closeProgressDialog();
            }
          });
        } else {
          manageRpcCall(mAppService.createGroup(Url.SESSIONID, mEditGroupName, mCategoryId,
              mEditType, mLogoId,
              mEditDescription, mGroupId, mNotice), new UiRpcSubscriber1<BaseModel>
              (CreateGroupActivity
                  .this) {


            @Override
            protected void onSuccess(BaseModel baseModel) {
              Toast.makeText(CreateGroupActivity.this, R.string
                  .activity_create_group_create_success, Toast.LENGTH_SHORT).show();
              getComponent().getGlobalBus().post(new CreateGroupSuccessEvent());
              finish();
            }

            @Override
            protected void onEnd() {
              closeProgressDialog();
            }
          });
        }
      }
    });

    btnDissolution.setVisibility(TextUtils.isEmpty(mCategoryId) ? View.GONE : View.VISIBLE);
    if (TextUtils.isEmpty(mCategoryId)) {
      return;
    }

    manageRpcCall(mAppService.getGroupType(), new
        UiRpcSubscriberSimple<GroupTypeModelList>(this) {


          @Override
          protected void onSuccess(GroupTypeModelList questionCategoryModel) {
            mCategoryList = questionCategoryModel.getList();
            mCategoryItems = new String[mCategoryList.size()];
            for (int i = 0; i < mCategoryItems.length; i++) {
              mCategoryItems[i] = mCategoryList.get(i).getTitle();
              if (TextUtils.equals(mCategoryId, mCategoryList.get(i).getId())) {
                groupCategory.setText(mCategoryList.get(i).getTitle());
              }
            }
          }

          @Override
          protected void onEnd() {

          }
        });
  }

  private void inject() {
    DaggerServiceComponent.builder().globalModule(new GlobalModule((BaseApplication)
        getApplication())).serviceModule(new ServiceModule())
        .build().inject(this);
  }

  @OnClick({R.id.menu_logo, R.id.menu_group_name, R.id.menu_group_category, R.id.menu_group_type,
      R.id.menu_description, R.id.menu_notice, R.id.btn_dissolution})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.menu_logo:
        mLogoChoosePresenter.showChooseAvatarDialog();
        break;
      case R.id.menu_group_name:
        showInputDialog(mEditGroupName, EDIT_TYPE_GROUP_NAME);
        break;
      case R.id.menu_group_category:
        getCategory();
        break;
      case R.id.menu_group_type:
        showTypeChooseDialog();
        break;
      case R.id.menu_description:
        showInputDialog(mEditDescription, EDIT_TYPE_DESCRIPTION);
        break;
      case R.id.menu_notice:
        startActivityForResult(NoticeInputActivity.makeIntent(this, mNotice), REQUEST_CODE_NOTICE);
        break;
      case R.id.btn_dissolution:
        dissolutionGroup();
        break;
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mLogoChoosePresenter.onDestroy();
  }

  private void dissolutionGroup() {
    AlertDialog dialog = new AlertDialog.Builder(this).setMessage(R.string
        .activity_create_group_dissolution_alert)
        .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
          }
        }).setPositiveButton(R.string.btn_confirm, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            manageRpcCall(mAppService.endGroup(Url.SESSIONID, mGroupId), new
                UiRpcSubscriber1<BaseModel>
                    (CreateGroupActivity.this) {
                  @Override
                  protected void onSuccess(BaseModel baseModel) {
                    Toast.makeText(CreateGroupActivity.this, R.string
                        .activity_create_group_dissolution_success, Toast.LENGTH_SHORT).show();
                    getComponent().getGlobalBus().post(new DissolutionGroupEvent());
                    finish();
                  }

                  @Override
                  protected void onEnd() {

                  }
                });
          }
        }).create();
    dialog.show();
  }

  @Subscribe
  public void handleUpLoadLogoSuccessEvent(LogoChoosePresenter.UploadLogoEvent event) {
    ImageLoader.loadOptimizedHttpImage(this, NetConstant.BASE_URL + event.path).placeholder(R
        .drawable.picture_1_no)
        .bitmapTransform(new CropCircleTransformation(this))
        .into(mLogoView);
    mLogoId = event.id;
  }

  private void showTypeChooseDialog() {
    if (null == mTypeChooseDialog) {
      mTypeChooseDialog = new AlertDialog.Builder(this).setItems(mTypeItems, new
          DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, final int which) {
              mEditType = String.valueOf(which);
              groupType.setText(mTypeItems[which]);
            }
          }).create();
    }
    if (mTypeChooseDialog.isShowing()) {
      return;
    }
    mTypeChooseDialog.show();
  }

  private void showCategoryChooseDialog() {
    if (null == mCategoryChooseDialog) {
      mCategoryChooseDialog = new AlertDialog.Builder(this).setItems(mCategoryItems, new
          DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, final int which) {
              mCategoryId = mCategoryList.get(which).getId();
              groupCategory.setText(mCategoryList.get(which).getTitle());
            }
          }).create();
    }
    if (mCategoryChooseDialog.isShowing()) {
      mCategoryChooseDialog.show();
    }
    mCategoryChooseDialog.show();
  }

  private void getCategory() {
    if (null == mCategoryItems) {
      manageRpcCall(mAppService.getGroupType(), new
          UiRpcSubscriberSimple<GroupTypeModelList>(this) {


            @Override
            protected void onSuccess(GroupTypeModelList questionCategoryModel) {
              mCategoryList = questionCategoryModel.getList();
              mCategoryItems = new String[mCategoryList.size()];
              for (int i = 0; i < mCategoryItems.length; i++) {
                mCategoryItems[i] = mCategoryList.get(i).getTitle();
                showCategoryChooseDialog();
              }
            }

            @Override
            protected void onEnd() {

            }
          });
    } else {
      showCategoryChooseDialog();
    }
  }

  private void showInputDialog(String initText, final int editType) {
    final EditText editText = (EditText) LayoutInflater.from(this).inflate(R.layout.dialog_input,
        null);
    editText.setText(initText);
    AlertDialog dialog = new AlertDialog.Builder(this).setView(editText).setPositiveButton(R
        .string.btn_confirm, new DialogInterface.OnClickListener() {


      @Override
      public void onClick(DialogInterface dialog, int which) {
        if (TextUtils.isEmpty(editText.getText())) {
          return;
        }
        switch (editType) {
          case EDIT_TYPE_GROUP_NAME:
            mEditGroupName = editText.getText().toString();
            groupName.setText(mEditGroupName);
            break;
          case EDIT_TYPE_DESCRIPTION:
            mEditDescription = editText.getText().toString();
            description.setText(mEditDescription);
            break;
        }
        dialog.cancel();
      }
    }).setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
      }
    }).create();
    dialog.show();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case REQUEST_CODE_NOTICE:
        if (resultCode != RESULT_OK) {
          return;
        }
        mNotice = data.getStringExtra(NoticeInputActivity.BUNDLE_KEY_NOTICE);
        notice.setText(mNotice);
        break;
      default:
        mLogoChoosePresenter.onActivityResult(requestCode, resultCode, data);
        break;
    }
  }

  public static Intent makeIntent(Context context, String groupId) {
    Intent intent = new Intent(context, CreateGroupActivity.class);
    intent.putExtra(BUNDLE_KEY_GROUP_ID, groupId);
    return intent;
  }

  public class CreateGroupSuccessEvent {

  }

  public class DissolutionGroupEvent {

  }
}
