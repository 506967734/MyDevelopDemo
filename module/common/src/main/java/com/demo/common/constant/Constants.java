package com.demo.common.constant;

public class Constants {
    /**
     * lbs日志前缀
     */
    public static final String APP_LOG = "app_log";
    public static final String LBS_LOG = "lbs_log";
    public static final String AGENT_LOG = "agent_log";
    public static final String EVENT_LOG = "event_log";
    public static final String NET_LOG = "RPC";

    /**
     * 日志后缀
     */
    public static final String REAL_TIME_LOG = "Real-Time";

    /**
     * 百度地图
     */
    public static final String BD_COOR_TYPE = "gcj02";

    //子前缀
    public static final String NET_LOG_HEADERS = "RPC(HEADERS)";
    public static final String NET_LOG_BODYS = "RPC(BODY)";

    //开启mock功能
    public static final String MOCK_OPEN = "open_mock_data";
    //mock设置参数传递
    public static final String MOCK_SET_BEAN = "mock_set_bean";
    //单独设置mock关闭
    public static final String MOCK_SINGLE_SET_CLOSE = "mock_single_set_close";
    //单独设置网络延迟
    public static final String MOCK_SINGLE_SET_NET_WORK_DELAY = "mock_single_set_network_delay";
    //单独设置mock数据源选择
    public static final String MOCK_SINGLE_SET_SELECTED = "mock_single_set_selected";
}
