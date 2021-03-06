package com.hengyi.baseandroiddemo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hengyi.baseandroidcore.activity.ActivityRouter;
import com.hengyi.baseandroidcore.browser.XBaseBrowserActivity;
import com.hengyi.baseandroidcore.statusbar.StatusBarCompat;
import com.hengyi.baseandroidcore.utils.ColorUtils;
import com.hengyi.baseandroidcore.utils.CommonUtils;
import com.hengyi.baseandroidcore.utils.SystemUtils;
import com.hengyi.baseandroidcore.utils.VersionUtils;
import com.hengyi.baseandroidcore.weight.XBaseTitleBar;
import com.hengyi.baseandroiddemo.base.BaseActivity;
import com.hengyi.baseandroiddemo.database.User;
import com.hengyi.baseandroiddemo.database.UserDao;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.titleBar)XBaseTitleBar easeTitleBar;
    @BindView(R.id.cid)TextView cid;
    @BindView(R.id.version)TextView version;


    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public int setContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        userDao = new UserDao(this);

        StatusBarCompat.setStatusBarColor(this, Color.parseColor(ColorUtils.changeColor(this,R.color.my_main_color)));
        easeTitleBar.setBackgroundColor(getResources().getColor(R.color.my_main_color));

        easeTitleBar.setLeftLayoutClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                CommonUtils.kill();
            }
        });
        easeTitleBar.hideLeftLayout();
        easeTitleBar.hideRightLayout();

        version.setText("当前版本：" + VersionUtils.getVersionName(this,"1.0.0.0"));

        cid.setText("您的永久CID：" + SystemUtils.getClientID() +"\n" + "您的临时CID："+SystemUtils.getShortClientID(this)+"\n（重装失效）");
    }


    @OnClick({R.id.xbase_home,R.id.xbase_mui,R.id.xbase_sui,R.id.xbase_youku,R.id.xbase_loading,R.id.xbase_permission,R.id.xbase_db_add,R.id.xbase_image_display})
    public void Click(View view){
        switch(view.getId()){
            case R.id.xbase_home:
                ActivityRouter.getInstance()
                        .add(XBaseBrowserActivity.WEB_URL,XBaseBrowserActivity.ANDROID_ASSSET_PATH + "template/index.html")//加载本地asset加上XBaseBrowserActivity.ANDROID_ASSSET_PATH + 你的html路径。
                        .add(XBaseBrowserActivity.SHOW_TITLE_BAR,true)//是否显示标题栏
                        .add(XBaseBrowserActivity.SHOW_REFRESH,true)//是否可以下拉刷新
                        .add(XBaseBrowserActivity.STATUS_COLOR,R.color.colorPrimaryDark)//设置状态栏颜色，默认是蓝色
                        .add(XBaseBrowserActivity.START_CACHE,false)//是否开启缓存
                        .add(XBaseBrowserActivity.SHOW_CLOSE_APP_DIALOG,false)
                        .add(XBaseBrowserActivity.SHOW_CLOSE_APP_MSG,"请问真的要退出吗？")
                        .startActivity(this,XBaseBrowserActivity.class);
            break;

            case R.id.xbase_mui:
                ActivityRouter.getInstance()
                        .add(XBaseBrowserActivity.WEB_URL,"http://www.dcloud.io/hellomui/list.html?v=1")
                        .add(XBaseBrowserActivity.SHOW_TITLE_BAR,true)
                        .add(XBaseBrowserActivity.STATUS_COLOR,R.color.my_main_color)//设置状态栏颜色，默认是蓝色
                        .startActivity(this,XBaseBrowserActivity.class);
                break;

            case R.id.xbase_sui:
                ActivityRouter.getInstance()
                        .add(XBaseBrowserActivity.WEB_URL,"http://m.sui.taobao.org/demos/")
                        .add(XBaseBrowserActivity.SHOW_TITLE_BAR,true)
                        .startActivity(this,XBaseBrowserActivity.class);
                break;
            case R.id.xbase_youku:
                ActivityRouter.getInstance()
                        .add(XBaseBrowserActivity.WEB_URL,"https://www.youku.com/")
                        .add(XBaseBrowserActivity.SHOW_TITLE_BAR,true)
                        .startActivity(this,XBaseBrowserActivity.class);
                break;

            case R.id.xbase_loading:
                ActivityRouter.getInstance().startActivity(this,LoadingActivity.class);
                break;

            case R.id.xbase_permission:
                ActivityRouter.getInstance().startActivity(this,TestActivity.class);
                break;

            case R.id.xbase_db_add:
                User user = new User();
                user.setName("董志平");
                int res = userDao.add(user);
                toast("操作结果：" + (res > 0));
                break;

            case R.id.xbase_image_display:
                ActivityRouter.getInstance().startActivity(this,ImageActivity.class);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonUtils.kill();
    }

}
