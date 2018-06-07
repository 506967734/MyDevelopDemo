package com.demo.testmodule;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.demo.common.base.BaseApplication;
import com.demo.common.constant.Constants;
import com.demo.common.utils.CacheUtil;
import com.demo.mockmodule.MockBean;
import com.demo.mockmodule.mockApi.MockConfigManger;


public class MockSetActivity extends Activity {
    MockBean mockBean;

    CheckBox chk_mock;
    TextView api,desc,file_name,url;
    EditText ed_select,net_work_delay;
    Button btn_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mock_set_activity);
        initValues();
        initViews();

    }

    private void initValues() {
        mockBean = (MockBean) getIntent().getSerializableExtra(Constants.MOCK_SET_BEAN);
    }

    private void initViews() {
        chk_mock = (CheckBox) findViewById(R.id.chk_mock);
        api = (TextView) findViewById(R.id.api);
        desc = (TextView) findViewById(R.id.desc);
        file_name = (TextView) findViewById(R.id.file_name);
        url = (TextView) findViewById(R.id.url);
        ed_select = (EditText) findViewById(R.id.ed_select);
        net_work_delay = (EditText) findViewById(R.id.net_work_delay);
        btn_ok = (Button) findViewById(R.id.btn_ok);

        String mockClose = CacheUtil.getString(BaseApplication.getInstance(),mockBean.getUrlKey()+ Constants.MOCK_SINGLE_SET_CLOSE,"");
        chk_mock.setChecked("1".equals(mockClose));
        api.setText(mockBean.getApi()+"");
        desc.setText(mockBean.getDescribe()+"");
        file_name.setText(mockBean.getFileName()+"");
        url.setText(mockBean.getUrlKey()+"");
        ed_select.setText(mockBean.getSelected()+"");
        net_work_delay.setText(mockBean.getNetWorkDelay()+"");

        chk_mock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CacheUtil.putString(BaseApplication.getInstance(),mockBean.getUrlKey()+ Constants.MOCK_SINGLE_SET_CLOSE,chk_mock.isChecked()?"1":"");
                MockConfigManger.getInstance().refreshConfig();
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CacheUtil.putString(BaseApplication.getInstance(),mockBean.getUrlKey()+ Constants.MOCK_SINGLE_SET_NET_WORK_DELAY,net_work_delay.getText()+"");
                CacheUtil.putString(BaseApplication.getInstance(),mockBean.getUrlKey()+ Constants.MOCK_SINGLE_SET_SELECTED,ed_select.getText()+"");
                MockConfigManger.getInstance().refreshConfig();
            }
        });
    }
}
