package cn.zhudi.mvpdemo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;
import cn.zhudi.mvpdemo.R;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public abstract class BaseListMVPFragment<V, T extends BasePresenter<V>> extends BaseFragment {
    private Context mContext;
    @BindView(R.id.refreshLayout)
    public SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    public RecyclerView recyclerView;

    /**
     * 控制器
     */
    public T presenter;

    /**
     * 初始化控制器
     *
     * @return
     */
    protected abstract T initPresenter();

    /**
     * 下拉刷新
     */
    protected abstract void refresh();

    /**
     * 上拉加载
     */
    protected abstract void loadMore();

    /**
     * Adapter设置
     *
     * @return adapter
     */
    protected abstract RecyclerView.Adapter setRecyclerViewAdapter();

    /**
     * 最后一页
     */
    protected void lastPage() {
        if (refreshLayout != null) {
            refreshLayout.setEnableLoadMore(false);
        }
    }

    /**
     * 加载完成
     */
    protected void requestFinish() {
        if (refreshLayout != null) {
            refreshLayout.finishRefresh();
            refreshLayout.finishLoadMore();
        }
    }

    /**
     * 设置ItemAnimator
     *
     * @return
     */
    protected RecyclerView.ItemAnimator setRecyclerViewItemAnimator() {
        return new DefaultItemAnimator();
    }

    /**
     * 设置LayoutManager
     *
     * @return
     */
    protected RecyclerView.LayoutManager setRecyclerViewLayoutManager() {
        return new LinearLayoutManager(mContext);
    }

    /**
     * 设置ItemDecoration
     *
     * @return
     */
    protected RecyclerView.ItemDecoration setRecyclerViewItemDecoration() {
        return new DividerItemDecoration(mContext, VERTICAL);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = initPresenter();//创建Presenter
        presenter.attach((V) this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRefreshLayout();
    }

    /**
     * 初始化RefreshLayout
     */
    public void initRefreshLayout() {
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshLayout.setEnableLoadMore(true);
                refresh();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                loadMore();
            }
        });
        recyclerView.setItemAnimator(setRecyclerViewItemAnimator());
        recyclerView.setLayoutManager(setRecyclerViewLayoutManager());
        recyclerView.addItemDecoration(setRecyclerViewItemDecoration());
        recyclerView.setAdapter(setRecyclerViewAdapter());
    }

    @Override
    public void onDestroy() {
        presenter.dettach();
        super.onDestroy();
    }
}
