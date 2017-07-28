package com.crossit.collegeoffinearts.Tab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.crossit.collegeoffinearts.Tab.Fragment.Exhibition;
import com.crossit.collegeoffinearts.Tab.Fragment.Free;
import com.crossit.collegeoffinearts.Tab.Fragment.UsedArticle;
import com.crossit.collegeoffinearts.Tab.Fragment.Find;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    // Count number of tabs
    private int tabCount;
    private Bundle Write;

    //글쓰기 메뉴 접근
    private boolean writeFlag;
    private boolean SearchFlag;

    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        Write = new Bundle();

        this.tabCount = tabCount;
        SearchFlag = false;
    }

    //글쓰기 메뉴로 접근시 현재 어느 게시판인지 알려주기 위함
    public void setBundle(String txt,String type)
    {
        Write.putString("send",txt);
        Write.putString("type",type);
    }

    public void clearBundle()
    {
        Write.clear();
    }

    public int getBundleCount()
    {
        return Write.size();
    }

    //ViewPager 새로고침시 필요한 부분들
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
    //ViewPager 새로고침 End

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            default:
            case 0:
                fragment = new UsedArticle();
                break;
            case 1:
                fragment = new Exhibition();
                break;
            case 2:
                fragment = new Find();
                break;
            case 3:
                fragment = new Free();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
