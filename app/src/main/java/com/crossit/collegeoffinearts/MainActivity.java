package com.crossit.collegeoffinearts;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.crossit.collegeoffinearts.Tab.Adapter.GlobalChecker;
import com.crossit.collegeoffinearts.Tab.Dialog.Loading;
import com.crossit.collegeoffinearts.Tab.Dialog.LoadingDialog;
import com.crossit.collegeoffinearts.Tab.TabIcon;
import com.crossit.collegeoffinearts.Tab.TabPagerAdapter;
import com.crossit.collegeoffinearts.Tab.WriteBoard;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabPagerAdapter pagerAdapter;

    //상단 탭 레이아웃
    private TabLayout tabLayout;
    private TabIcon[] tabIcons = new TabIcon[4];
    private int tab_n[] = {R.drawable.menu1, R.drawable.menu2, R.drawable.menu3, R.drawable.menu4};//기본 리소스
    private int tab_p[] = {R.drawable.menu1_p, R.drawable.menu2_p, R.drawable.menu3_p, R.drawable.menu4_p};//클릭 리소스

    //상단 메뉴
    ImageView mypage;
    ImageView likePage;


    //로그인 체크
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setPermission(); // 카메라를 위한 권한 물어보기
        TabInit();
        WriteBtnInit();
        searchBtnInit();
        myPageBtnInit();
        likePageBtnInit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadingDialog.loading = new Loading(this);
    }

    private void TabInit() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); //앱 이름 안보이게 설정
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //아이콘 설정
        for (int i = 0; i < 4; i++) {
            if (i == 0)
                tabIcons[0] = new TabIcon(getApplicationContext(), tab_p[0]);
            else
                tabIcons[i] = new TabIcon(getApplicationContext(), tab_n[i]);

            tabLayout.addTab(tabLayout.newTab().setCustomView(tabIcons[i].getView()));
        }

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(4);

        // 뷰 페이저 설정
        pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // 탭 메뉴 선택 이벤트 (아이콘 변경 수행)
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if(tab.getPosition()==0)
                    GlobalChecker.usedArticleFlag = 2;
                else if(tab.getPosition()==1)
                    GlobalChecker.usedArticleFlag = 3;

                viewPager.setCurrentItem(tab.getPosition());
                tabIcons[tab.getPosition()].changeImage(tab_p[tab.getPosition()]);
                tabLayout.getTabAt(tab.getPosition()).setCustomView(tabIcons[tab.getPosition()].getView());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tabIcons[tab.getPosition()].changeImage(tab_n[tab.getPosition()]);
                tabLayout.getTabAt(tab.getPosition()).setCustomView(tabIcons[tab.getPosition()].getView());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    //글쓰기 버튼 접근
    private void WriteBtnInit() {
        LinearLayout write_btn = (LinearLayout) findViewById(R.id.write_btn);
        write_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyAuth.userId == null || MyAuth.userId.equals("non"))
                {
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), WriteBoard.class);
                    intent.putExtra("write", String.valueOf(tabLayout.getSelectedTabPosition()));
                    startActivity(intent);
                }
            }
        });
    }

    private void searchBtnInit(){
        LinearLayout search_btn = (LinearLayout)findViewById(R.id.search_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    //back버튼 클릭시 원래 메뉴로
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void myPageBtnInit()
    {
        mypage = (ImageView)findViewById(R.id.mypage);
        mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mypage.setImageResource(R.drawable.my_p);
                pagerAdapter.setBundle(String.valueOf(tabLayout.getSelectedTabPosition()),"mypage");
                pagerAdapter.notifyDataSetChanged();
            }
        });
    }

    private void likePageBtnInit()
    {
        likePage = (ImageView)findViewById(R.id.likepage);
        likePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likePage.setImageResource(R.drawable.like_p);
                pagerAdapter.setBundle(String.valueOf(tabLayout.getSelectedTabPosition()),"like");
                pagerAdapter.notifyDataSetChanged();
            }
        });

    }


    //카메라 갤러리 권한
    void setPermission()
    {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
            // 이 권한을 필요한 이유를 설명해야하는가?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA)) {
                // 다이어로그같은것을 띄워서 사용자에게 해당 권한이 필요한 이유에 대해 설명합니다
                // 해당 설명이 끝난뒤 requestPermissions()함수를 호출하여 권한허가를 요청해야 합니다
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.CAMERA}, 1);
                // 필요한 권한과 요청 코드를 넣어서 권한허가요청에 대한 결과를 받아야 합니다
            }
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            // 이 권한을 필요한 이유를 설명해야하는가?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // 다이어로그같은것을 띄워서 사용자에게 해당 권한이 필요한 이유에 대해 설명합니다
                // 해당 설명이 끝난뒤 requestPermissions()함수를 호출하여 권한허가를 요청해야 합니다
            } else {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                // 필요한 권한과 요청 코드를 넣어서 권한허가요청에 대한 결과를 받아야 합니다
            }
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            // 이 권한을 필요한 이유를 설명해야하는가?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // 다이어로그같은것을 띄워서 사용자에게 해당 권한이 필요한 이유에 대해 설명합니다
                // 해당 설명이 끝난뒤 requestPermissions()함수를 호출하여 권한허가를 요청해야 합니다
            } else {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                // 필요한 권한과 요청 코드를 넣어서 권한허가요청에 대한 결과를 받아야 합니다
            }
        }

    }


}
