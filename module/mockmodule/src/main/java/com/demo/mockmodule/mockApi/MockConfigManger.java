package com.demo.mockmodule.mockApi;

import android.content.res.XmlResourceParser;


import com.demo.common.base.BaseApplication;
import com.demo.common.constant.Constants;
import com.demo.common.utils.CacheUtil;
import com.demo.common.utils.ConvertUtil;
import com.demo.mockmodule.MockBean;
import com.demo.mockmodule.R;

import java.util.HashMap;
import java.util.Map;


public class MockConfigManger {
    private static MockConfigManger mInstance;
    private Map<String, MockBean> mockBeans;

    private MockConfigManger() {
        mockBeans = new HashMap<>();
        initMockBeans();
    }

    public static MockConfigManger getInstance() {
        if (mInstance == null) {
            synchronized (MockConfigManger.class) {
                if (mInstance == null) {
                    mInstance = new MockConfigManger();
                }
            }
        }
        return mInstance;
    }

    public MockBean findMockBean(String key) {
        if (mockBeans == null || mockBeans.size() == 0) {
            initMockBeans();
        }
        return mockBeans.get(key);
    }

    private void initMockBeans() {
        XmlResourceParser xmlParser = BaseApplication.getInstance().getResources().getXml(R.xml.mock_data);
        try {
            int eventType = xmlParser.getEventType();
            // 判断是否到了文件的结尾
            while (eventType != XmlResourceParser.END_DOCUMENT) {

                //文件的内容的起始标签开始，注意这里的起始标签是websites.xml文件里面<websites>标签下面的第一个标签
                if (eventType == XmlResourceParser.START_TAG) {
                    String tagname = xmlParser.getName();
                    if (tagname.equals("Node")) {
                        MockBean bean = new MockBean();
                        bean.setSelected(xmlParser.getAttributeValue(null, "Selected"));
                        bean.setUrlKey(xmlParser.getAttributeValue(null, "Url"));
                        bean.setApi(xmlParser.getAttributeValue(null, "Api"));
                        bean.setDescribe(xmlParser.getAttributeValue(null, "Describe"));
                        bean.setFileName(xmlParser.getAttributeValue(null, "FileName"));
                        bean.setNetWorkDelay(ConvertUtil.String2Int(xmlParser.getAttributeValue(null, "NetworkDelay"), 0));
                        //本地单独设置
                        setSingleSet(bean);
                        mockBeans.put(bean.getUrlKey(), bean);
                    }
                } else if (eventType == XmlResourceParser.END_TAG) {
                } else if (eventType == XmlResourceParser.TEXT) {
                }
                eventType = xmlParser.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSingleSet(MockBean bean) {
        if (bean == null) {
            return;
        }
        String mockClose = CacheUtil.getString(BaseApplication.getInstance(), bean.getUrlKey() + Constants.MOCK_SINGLE_SET_CLOSE);
        bean.setClose("1".equals(mockClose));

        int delay = ConvertUtil.String2Int(CacheUtil.getString(BaseApplication.getInstance(), bean.getUrlKey() + Constants.MOCK_SINGLE_SET_NET_WORK_DELAY, bean.getNetWorkDelay() + ""), 0);
        bean.setNetWorkDelay(delay);

        String select = CacheUtil.getString(BaseApplication.getInstance(), bean.getUrlKey() + Constants.MOCK_SINGLE_SET_SELECTED, bean.getSelected());
        if (!"0".equals(select)) {
            bean.setSelected(select);
        }

    }

    public Map<String, MockBean> getMockBeans() {
        return mockBeans;
    }

    public void refreshConfig() {
        mockBeans = new HashMap<>();
        initMockBeans();
    }
}
