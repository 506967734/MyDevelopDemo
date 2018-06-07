package cn.zhudi.mvpdemo.ui.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import cn.lightsky.infiniteindicator.InfiniteIndicator;
import cn.lightsky.infiniteindicator.OnPageClickListener;
import cn.lightsky.infiniteindicator.Page;
import cn.zhudi.mvpdemo.R;
import cn.zhudi.mvpdemo.base.BaseFragment;

/**
 * 类名称：首页fragment
 * 类描述：
 * 创建人：zhudi
 */
public class FragmentHome extends BaseFragment implements ViewPager.OnPageChangeListener, OnPageClickListener {
    private String showString;
    @BindView(R.id.infinite_anim_circle)
    InfiniteIndicator mAnimCircleIndicator;
    @BindView(R.id.ivAd)
    ImageView ivAd;

    private ArrayList<Page> pageViews;


    public static FragmentHome newInstance(String s) {
        FragmentHome fragment = new FragmentHome();
        Bundle bundle = new Bundle();
        bundle.putString("string", s);
        fragment.setArguments(bundle);
        return fragment;
    }

    protected int getLayoutView() {
        return R.layout.fragment_home;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            showString = bundle.getString("string");
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
    }

    private void initView() {
//        int width = ZhudiScreenUtil.getScreenWidth(getActivity());
//        ZhudiScreenUtil.setHeightNumber(mAnimCircleIndicator, width * 15 / 32);
//        IndicatorConfiguration configuration = new IndicatorConfiguration.Builder()
//                .internal(3 * 1000)
//                .imageLoader(new GlideLoader())
//                .isStopWhileTouch(true)
//                .onPageChangeListener(this)
//                .onPageClickListener(this)
//                .direction(RIGHT)
//                .position(IndicatorConfiguration.IndicatorPosition.Center_Bottom)
//                .build();
//        mAnimCircleIndicator.init(configuration);
//        mAnimCircleIndicator.notifyDataChange(pageViews);
//
//        ZhudiScreenUtil.setHeightNumber(ivAd, width * 3 / 16);
//        Glide.with(this)
//                .load("https://m.360buyimg.com/mobilecms/jfs/t4345/96/1968072461/98672/e426c4e9/58c93d65N4afafdc0.jpg!q70.jpg.webp")
//                .into(ivAd);


    }

    private void initData() {
        pageViews = new ArrayList<>();
        pageViews.add(new Page("A", "http://m.360buyimg.com/mobilecms/s720x322_jfs/t3307/335/8500148396/100188/d44031b2/58c60cacN74c303f6.jpg!q70.jpg.webp", this));
        pageViews.add(new Page("B", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/b.jpg", this));
        pageViews.add(new Page("C", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/c.jpg", this));
        pageViews.add(new Page("D", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/d.jpg", this));

    }

    //To avoid memory leak ,you should release the res
    @Override
    public void onPause() {
        super.onPause();
        //mAnimCircleIndicator.stop();
    }


    @Override
    public void onResume() {
        super.onResume();
        //mAnimCircleIndicator.start();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageClick(int position, Page page) {
        Toast.makeText(getActivity(), " click page --- " + position, Toast.LENGTH_SHORT).show();
    }
}
