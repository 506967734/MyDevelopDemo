package com.demo.testmodule;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.demo.common.base.BaseApplication;
import com.demo.common.constant.Constants;
import com.demo.common.utils.CacheUtil;
import com.demo.mockmodule.MockBean;
import com.demo.mockmodule.mockApi.MockConfigManger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MockTestActivity extends Activity {
    Toast mToast;
    ListView mListview;
    List<MockBean> mockBeans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mock_activity);

        Map<String,MockBean> map = MockConfigManger.getInstance().getMockBeans();
        if(map!=null&&map.size()>0){
            mockBeans = new ArrayList<>(map.values());
        }

        initViews();
        mToast = Toast.makeText(this,"", Toast.LENGTH_SHORT);
    }

    private void initViews() {
        String mock = CacheUtil.getString(BaseApplication.getInstance(), Constants.MOCK_OPEN,"");
        ((CheckBox)findViewById(R.id.ch_mock)).setChecked("1".equals(mock));


        ((CheckBox)findViewById(R.id.ch_mock)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String va = isChecked?"1":"";
                CacheUtil.putString(BaseApplication.getInstance(),Constants.MOCK_OPEN,va);
                String msg = isChecked?"mock已经开启":"mock已经关闭";
                mToast.setText(msg);
                mToast.show();
            }
        });

        mListview = (ListView) findViewById(R.id.lst_mockdata);
        mListview.setAdapter(new MockAdapter());
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(MockTestActivity.this,MockSetActivity.class);
                it.putExtra(Constants.MOCK_SET_BEAN,mockBeans.get(position));
                startActivity(it);
            }
        });
    }

    class MockAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if(mockBeans==null)
            return 0;
            return mockBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return mockBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        ViewHolder holder;
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView = LayoutInflater.from(MockTestActivity.this).inflate(R.layout.mock_bean,null);
                holder = new ViewHolder(convertView);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            initHolder(holder,position);
            convertView.setTag(holder);
            return convertView;
        }

        private void initHolder(ViewHolder holder, int position) {
            MockBean bean = mockBeans.get(position);
            holder.tvApi.setText(bean.getApi()+"");
            holder.tvDescribe.setText(bean.getDescribe()+"");
            holder.tvUrl.setText(bean.getUrlKey());
        }

        class ViewHolder{
            View convertView;

            TextView tvDescribe;
            TextView tvApi;
            TextView tvUrl;

            public ViewHolder(View view){
                this.convertView = view;
                tvDescribe = (TextView) view.findViewById(R.id.tv_desc);
                tvApi = (TextView) view.findViewById(R.id.tv_api);
                tvUrl = (TextView) view.findViewById(R.id.tv_url);
            }
        }
    }

}
