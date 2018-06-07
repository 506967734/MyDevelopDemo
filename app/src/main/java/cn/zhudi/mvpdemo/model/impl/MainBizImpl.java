package cn.zhudi.mvpdemo.model.impl;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.zhudi.mvpdemo.R;
import cn.zhudi.mvpdemo.base.BaseFragment;
import cn.zhudi.mvpdemo.impl.MainListener;
import cn.zhudi.mvpdemo.model.IMainModelBiz;
import cn.zhudi.mvpdemo.ui.fragment.FragmentCar;
import cn.zhudi.mvpdemo.ui.fragment.FragmentHome;
import cn.zhudi.mvpdemo.ui.fragment.FragmentPerson;
import cn.zhudi.mvpdemo.ui.fragment.FragmentShop;

/**
 * Created by apple on 2017/11/29.
 */

public class MainBizImpl implements IMainModelBiz {
    /**
     * 三个界面的fragment
     */
    private List<BaseFragment> fragments;
    /**
     * 当前在哪个菜单栏下
     */
    private int currentTab = 0;
    /**
     * 三个界面的标题名称
     */
    private int[] titleArray = new int[]{R.string.home, R.string.category, R.string.personal, R.string.car};

    private MainListener mainListener;

    @Override
    public boolean isEmpty(String name) {
        return false;
    }

    @Override
    public void initFragment(FragmentActivity fragment, int id, List<View> tabView, MainListener listener) {
        fragments = new ArrayList<>();

        fragments.add(FragmentHome.newInstance("1"));
        fragments.add(FragmentShop.newInstance("2"));
        fragments.add(FragmentPerson.newInstance("3"));
        fragments.add(FragmentCar.newInstance("4"));

        this.mainListener = listener;

        new FragmentTabAdapter(fragment, fragments, id, tabView);
    }

    class FragmentTabAdapter implements View.OnClickListener {
        FragmentActivity fragmentActivity;
        List<BaseFragment> fragments;
        int fragmentContentId;
        private List<View> list;

        public FragmentTabAdapter(FragmentActivity fragmentActivity, List<BaseFragment> fragments, int fragmentContentId, List<View> tabView) {
            this.fragmentActivity = fragmentActivity;
            this.fragments = fragments;
            this.fragmentContentId = fragmentContentId;
            //加载第一次界面
            fragmentActivity.getSupportFragmentManager().beginTransaction().add(fragmentContentId,fragments.get(currentTab)).commitAllowingStateLoss();

            list = new ArrayList<>();
            for (int i = 0; i < tabView.size(); i++) {
                list.add(tabView.get(i));
                tabView.get(i).setOnClickListener(this);
            }
            list.get(currentTab).setSelected(true);
            //显示标题名称
            mainListener.showTitleListener(titleArray[currentTab]);
        }


        @Override
        public void onClick(View v) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId() == v.getId()) {
                    mainListener.showTitleListener(titleArray[i]);
                    Fragment fragment = fragments.get(i);
                    FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction();
                    getCurrentFragment().onPause(); // 暂停当前tab
                    getCurrentFragment().onStop(); // 暂停当前tab
                    if (fragment.isAdded()) {
                        fragment.onStart(); // 启动目标tab的onStart()
                        fragment.onResume(); // 启动目标tab的onResume()
                    } else {
                        ft.add(fragmentContentId, fragment);
                    }
                    showTab(i); // 显示目标tab
                    //ft.commit();
                    ft.commitAllowingStateLoss();
                    list.get(i).setSelected(true);
                } else {
                    list.get(i).setSelected(false);
                }
            }
        }


        private void showTab(int idx) {
            for (int i = 0; i < fragments.size(); i++) {
                Fragment fragment = fragments.get(i);
                FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction();
                if (idx == i) {
                    ft.show(fragment);
                } else {
                    ft.hide(fragment);
                }
                //ft.commit();
                ft.commitAllowingStateLoss();
            }
            currentTab = idx; // 更新目标tab为当前tab
        }


//        public int getCurrentTab() {
//            return currentTab;
//        }

        public Fragment getCurrentFragment() {
            return fragments.get(currentTab);
        }
    }
}
