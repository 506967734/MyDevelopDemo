package cn.zhudi.mvpdemo.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.demo.common.utils.ToastCompat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import cn.zhudi.mvpdemo.R;
import cn.zhudi.mvpdemo.adapter.PersonAdapter;
import cn.zhudi.mvpdemo.base.BaseListMVPFragment;
import cn.zhudi.mvpdemo.model.entities.User;
import cn.zhudi.mvpdemo.persenter.FragmentPersonPresenter;
import cn.zhudi.mvpdemo.view.FragmentPersonView;

import static android.R.layout.simple_list_item_2;

/**
 * 类名称：个人中心
 * 类描述：
 * 创建人：zhudi
 */
public class FragmentPerson extends BaseListMVPFragment<FragmentPersonView, FragmentPersonPresenter> implements FragmentPersonView {

    private static final Logger logger = LoggerFactory.getLogger("FragmentPerson");
    private PersonAdapter<User> mAdapter;

    public static FragmentPerson newInstance(String s) {
        FragmentPerson fragment = new FragmentPerson();
        Bundle bundle = new Bundle();
        bundle.putString("string", s);
        fragment.setArguments(bundle);
        return fragment;
    }

    protected int getLayoutView() {
        return R.layout.fragment_person;
    }

    @Override
    protected FragmentPersonPresenter initPresenter() {
        return new FragmentPersonPresenter(getActivity());
    }

    @Override
    protected void refresh() {
        logger.debug("refresh");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.refresh(initData());
                requestFinish();
            }
        }, 2000);
    }

    @Override
    protected void loadMore() {
        logger.debug("loadMore");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.loadMore(initData());
                requestFinish();
                lastPage();
            }
        }, 2000);
    }

    @Override
    protected RecyclerView.Adapter setRecyclerViewAdapter() {
        mAdapter = new PersonAdapter<>(getActivity(), initData(), simple_list_item_2, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ToastCompat.show(getActivity(), i + "----" + mAdapter.getItem(i).getUserName(), Toast.LENGTH_LONG);
            }
        });
        return mAdapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        mAdapter.setOpenAnimationEnable(false);
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    public void showMoney(String str) {

    }

    private List<User> initData() {
        return Arrays.asList(new User("zhudi"), new User("zhudi"), new User("zhudi"), new User("zhudi"), new User("zhudi"), new User("zhudi"),
                new User("zhudi"), new User("zhudi"), new User("zhudi"), new User("zhudi"), new User("zhudi"), new User("zhudi"), new User("zhudi"),
                new User("zhudi"), new User("zhudi"), new User("zhudi"), new User("zhudi"), new User("zhudi"), new User("zhudi"));
    }
}
