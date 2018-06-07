package com.demo.common.broacast;

/**
 * 广播action项配置
 */
public class BroadcastConfig {
    // 扫描工具内应用设置的广播名称action;
    public final static String BROADCAST_SETTING = "com.android.scanner.service_settings";

    // 扫描工具内开发者项，广播名称的 key；
    public final static String BROADCAST_KEY = "action_barcode_broadcast";

    // 自定义扫描工具内开发者项内广播名称value；
    public final static String CUSTOM_NAME = "com.example.chinaautoid";

    // 扫描工具内应用设置下的条码发送方式的广播名称key；
    public final static String SEND_KEY = "barcode_send_mode";

    // 扫描工具内应用设置下的条码结束符广播名称key；
    public final static String END_KEY = "endchar";

    // 扫描工具内应用设置下的扫描声音广播名称key
    public final static String SOUND_KEY = "sound_play";

    // 扫描工具内应用设置下的振动广播名称key
    public final static String VIBERATE_KEY = "viberate";

    // 扫描工具内应用设置下的连续扫描 (循环扫描) 对应的广播key
    public final static String CONTINIU_KEY = "scan_continue";

    // 调用模拟按键button来实现扫描开启的广播action
    public final static String SCAN_START = "com.scan.onStartScan";

    // ͣ调用模拟按键button来实现扫描关闭的广播action
    public final static String SCAN_STOP = "com.scan.onEndScan";

    // 打开或者关闭扫描工具的广播action，此项只是控制扫描灯而不会停止扫描工具服务
    public final static String SCAN_LIGHT = "com.android.scanner.ENABLED";

    // 扫描工具下，条码设置对应的广播action
    public final static String ACTION_PARAM_SETTINGS = "com.seuic.scanner.action.PARAM_SETTINGS";

    public final static String SCANACTION="com.example.broadcast";
}
