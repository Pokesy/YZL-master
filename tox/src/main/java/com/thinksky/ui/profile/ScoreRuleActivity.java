/*
 * 文件名: ScoreRuleActivity
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/9/21
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.ui.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.tox.R;
import com.thinksky.ui.common.TitleBar;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/9/21]
 */
public class ScoreRuleActivity extends BaseBActivity {
  @Bind(R.id.title_bar)
  TitleBar titleBar;
  @Bind(R.id.score_login)
  TextView scoreLogin;
  @Bind(R.id.score_login_max)
  TextView scoreLoginMax;
  @Bind(R.id.score_question)
  TextView scoreQuestion;
  @Bind(R.id.score_question_max)
  TextView scoreQuestionMax;
  @Bind(R.id.score_best_answer)
  TextView scoreBestAnswer;
  @Bind(R.id.score_best_answer_max)
  TextView scoreBestAnswerMax;
  @Bind(R.id.score_comment_question)
  TextView scoreCommentQuestion;
  @Bind(R.id.score_comment_question_max)
  TextView scoreCommentQuestionMax;
  @Bind(R.id.score_send_activity)
  TextView scoreSendActivity;
  @Bind(R.id.score_send_activity_max)
  TextView scoreSendActivityMax;
  @Bind(R.id.score_comment_activity)
  TextView scoreCommentActivity;
  @Bind(R.id.score_comment_activity_max)
  TextView scoreCommentActivityMax;
  @Bind(R.id.score_send_post)
  TextView scoreSendPost;
  @Bind(R.id.score_send_post_max)
  TextView scoreSendPostMax;
  @Bind(R.id.score_comment_post)
  TextView scoreCommentPost;
  @Bind(R.id.score_comment_post_max)
  TextView scoreCommentPostMax;
  @Bind(R.id.score_set_question_award)
  TextView scoreSetQuestionAward;
  @Bind(R.id.score_set_question_award_max)
  TextView scoreSetQuestionAwardMax;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_score_rule);
    ButterKnife.bind(this);
    initTitleBar();
  }

  private void initTitleBar() {
    titleBar.setLeftImgMenu(R.drawable.icon_title_bar_back, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onBackPressed();
      }
    });
    titleBar.setMiddleTitle(R.string.activity_score_rule_title);
  }
}
