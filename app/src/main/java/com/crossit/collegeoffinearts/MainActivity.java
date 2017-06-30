package com.crossit.collegeoffinearts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.crossit.collegeoffinearts.Tab.TabIcon;
import com.crossit.collegeoffinearts.Tab.TabPagerAdapter;
import com.crossit.collegeoffinearts.Tab.WriteBoard;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

        TabInit();
        WriteBtnInit();
        myPageBtnInit();
        likePageBtnInit();


    }

    private void TabInit() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); //앱 이름 안보이게 설정

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
                if(pagerAdapter.getBundleCount()>0) {
                    resReset();
                    pagerAdapter.notifyDataSetChanged();
                }
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
                Log.d("qwe",myAuth.userId);
                if(myAuth.userId == null || myAuth.userId.equals("non"))
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


    //back버튼 클릭시 원래 메뉴로
    @Override
    public void onBackPressed() {
        if(pagerAdapter.getBundleCount()>0)
        {
            viewPager.setCurrentItem(tabLayout.getSelectedTabPosition());
            resReset();
            pagerAdapter.notifyDataSetChanged();
        }
        else
           super.onBackPressed();
    }

    private void myPageBtnInit()
    {
        mypage = (ImageView)findViewById(R.id.mypage);
        mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resReset();
                mypage.setImageResource(R.drawable.my_p);
                pagerAdapter.setBundle(String.valueOf(tabLayout.getSelectedTabPosition()),"mypage");
                pagerAdapter.notifyDataSetChanged();
            }
        });
    }

    private void resReset()
    {
        pagerAdapter.clearBundle();
        likePage.setImageResource(R.drawable.like);
        mypage.setImageResource(R.drawable.my);
    }

    private void likePageBtnInit()
    {
        likePage = (ImageView)findViewById(R.id.likepage);
        likePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resReset();
                likePage.setImageResource(R.drawable.like_p);
                pagerAdapter.setBundle(String.valueOf(tabLayout.getSelectedTabPosition()),"like");
                pagerAdapter.notifyDataSetChanged();
            }
        });

    }

}
