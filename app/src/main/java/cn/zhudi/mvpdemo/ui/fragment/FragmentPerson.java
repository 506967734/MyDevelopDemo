package cn.zhudi.mvpdemo.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.demo.common.adapter.BaseRecyclerAdapter;
import com.demo.common.adapter.SmartViewHolder;
import com.demo.common.utils.ToastCompat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;

import cn.zhudi.mvpdemo.R;
import cn.zhudi.mvpdemo.base.BaseFragment;
import cn.zhudi.mvpdemo.base.BaseListMVPFragment;
import cn.zhudi.mvpdemo.base.BasePresenter;
import cn.zhudi.mvpdemo.persenter.FragmentPersonPresenter;
import cn.zhudi.mvpdemo.view.FragmentPersonView;

import static android.R.layout.simple_list_item_2;
import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

/**
 * 类名称：个人中心
 * 类描述：
 * 创建人：zhudi
 */
public class FragmentPerson extends BaseListMVPFragment<FragmentPersonView, FragmentPersonPresenter> implements FragmentPersonView {

    private static final Logger logger = LoggerFactory.getLogger("FragmentPerson");
    private BaseRecyclerAdapter<Void> mAdapter;

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
    protected String requestData() {
        return null;
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
            }
        }, 2000);
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
        recyclerView.setAdapter(mAdapter = new BaseRecyclerAdapter<Void>(initData(), simple_list_item_2, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                logger.debug("onItemClick----onItemClick---onItemClick");
                ToastCompat.show(getActivity(), i + "", Toast.LENGTH_LONG);
            }
        }) {
            @Override
            protected void onBindViewHolder(SmartViewHolder holder, Void model, int position) {
                holder.text(android.R.id.text1, getString(R.string.item_example_number_title, position));
                holder.text(android.R.id.text2, getString(R.string.item_example_number_abstract, position));
                //holder.textColorId(android.R.id.text2, R.color.colorTextAssistant);
            }
        });
    }


    @Override
    public void showMoney(String str) {

    }

    private Collection<Void> initData() {
        return Arrays.asList(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }
}
