package com.manshop.android.ui.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.manshop.android.R;
import com.manshop.android.adapter.ViewPagerAdapter;
import com.manshop.android.ui.base.BaseActivity;
import com.manshop.android.ui.view.fragment.HomeFragment;
import com.manshop.android.ui.view.fragment.MessageFragment;
import com.manshop.android.ui.view.fragment.PersonalFragment;


import java.util.logging.LogRecord;

public class MainActivity extends BaseActivity {
    private Toolbar toolbar;
    //侧边栏
    private DrawerLayout mDrawerLayout;
    private NavigationView navView;
    //侧边栏头部
    private LinearLayout before;
    private RelativeLayout after;
    private RoundedImageView head;
    private TextView tvUsername;
    //中间
    private ViewPager viewPager;
    private MenuItem menuItemFrag;
    private MenuItem toolbarMenu;
    //底部
    private BottomNavigationView bottomNavigationView;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showToolbar();
        init();
        showUser();
        addListener();
    }

    public void init() {
        //侧边栏菜单功能实现
        navView = (NavigationView) findViewById(R.id.nav_info);
        //底部菜单栏界面切换
        viewPager = (ViewPager) findViewById(R.id.vp_min);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_menu);
        //设置缓存，不会重复加载数据
        viewPager.setOffscreenPageLimit(3);
        setupViewPager(viewPager);

        LinearLayout info = (LinearLayout) getLayoutInflater().inflate(R.layout.info_header, null);
//        before = (LinearLayout) info.findViewById(R.id.before_login);
        after = (RelativeLayout) info.findViewById(R.id.after_login);
        head = (RoundedImageView) info.findViewById(R.id.im_head);
        tvUsername = (TextView) info.findViewById(R.id.tv_username);

    }

    public void addListener() {
        //侧边栏监听器
        navView.setCheckedItem(R.id.item_address);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_address:
                        intent = new Intent(MainActivity.this, AddressActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.item_setting:
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.item_logout:
                        intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

        //底部监听器
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_home:
                                viewPager.setCurrentItem(0);
                                Log.d("page", "---------0");
                                toolbar.setTitle("漫街");
                                toolbarMenu.setVisible(true);
                                break;
                            case R.id.item_message:
                                viewPager.setCurrentItem(1);
                                Log.d("page", "---------1");
                                toolbar.setTitle("消息");
                                toolbarMenu.setVisible(false);
                                break;
                            case R.id.item_personal:
                                viewPager.setCurrentItem(2);
                                Log.d("page", "---------2");
                                toolbar.setTitle("我的");
                                toolbarMenu.setVisible(false);
                                break;
                        }
                        return false;
                    }
                });

        //中部页面切换监听器
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //在屏幕滚动过程中不断被调用
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //代表哪个页面被选中
            @Override
            public void onPageSelected(int position) {
                if (menuItemFrag != null) {
                    menuItemFrag.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(position).setChecked(false);
                }
                menuItemFrag = bottomNavigationView.getMenu().getItem(position);
                menuItemFrag.setChecked(true);
                switch (menuItemFrag.getItemId()) {
                    case R.id.item_home:
                        viewPager.setCurrentItem(0);
                        Log.d("page", "---------0");
                        toolbar.setTitle("漫街");
                        toolbarMenu.setVisible(true);
                        break;
                    case R.id.item_message:
                        viewPager.setCurrentItem(1);
                        Log.d("page", "---------1");
                        toolbar.setTitle("消息");
                        toolbarMenu.setVisible(false);
                        break;
                    case R.id.item_personal:
                        viewPager.setCurrentItem(2);
                        Log.d("page", "---------2");
                        toolbar.setTitle("我的");
                        toolbarMenu.setVisible(false);
                        break;
                }
            }

            //在手指操作屏幕的时候发生变化
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private Handler handler = new Handler();
//    {
//        public void handleMessage(Message msg) {
//            SharedPreferences share = getSharedPreferences("User", MODE_PRIVATE);
//            Log.d("user", "login");
//            if (share != null) {
////            before.setVisibility(View.GONE);
////                after.setVisibility(View.VISIBLE);
//                Glide.with(MainActivity.this).load(share.getString("head", "")).into(head);
//                tvUsername.setText("789");
////            tvUsername.setText(share.getString("username",""));
//                Log.d("user", tvUsername.getText().toString());
//                Log.d("user", share.getString("head", ""));
//                Log.d("user", share.getString("username", ""));
//            } else {
////            after.setVisibility(View.INVISIBLE);
////            before.setVisibility(View.VISIBLE);
////            Glide.with(this).load(R.drawable.ic_unlogin).into(head);
////            tvUsername.setText("点击头像立即登录");
//                Log.d("user", "invisible");
//            }
//        }
//    };

    public void showUser() {
        new Thread() {
            @Override
            public void run() {
                try {
                    //你的处理逻辑,这里简单睡眠一秒
                    this.sleep(1000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //你的处理逻辑
                            SharedPreferences share = getSharedPreferences("User", MODE_PRIVATE);
                            Log.d("user", "login");
                            if (share != null) {
                                Glide.with(MainActivity.this).load(share.getString("head", "")).into(head);
                                tvUsername.setText("789");
                                Log.d("user", tvUsername.getText().toString());
                                Log.d("user", share.getString("head", ""));
                                Log.d("user", share.getString("username", ""));
                            } else {
//            Glide.with(this).load(R.drawable.ic_unlogin).into(head);
//            tvUsername.setText("点击头像立即登录");
                                Log.d("user", "invisible");
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void showToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.myDrawer);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_info);
        }
    }

    //加载标题栏
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_toolbar_menu, menu);
        toolbarMenu = menu.findItem(R.id.item_search);
        return true;
    }

    //标题栏按钮功能实现
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.item_search:
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                break;
            default:
        }
        return true;
    }


    //底部菜单栏适配器
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(HomeFragment.newInstance("ic_home"));
        adapter.addFragment(MessageFragment.newInstance("ic_message"));
        adapter.addFragment(PersonalFragment.newInstance("per"));
        viewPager.setAdapter(adapter);
    }
}
