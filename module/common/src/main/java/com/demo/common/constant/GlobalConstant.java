package com.demo.common.constant;

import com.demo.common.base.BaseApplication;

public interface GlobalConstant {

	public static final String PKG_NAME = BaseApplication.getInstance().getPackageName();

	public static final int DB_VERSION = 1;

	public final static int VERSION = Integer.valueOf(android.os.Build.VERSION.SDK_INT);

	/** 微信平台AppId */
	public static final String WX_APP_ID = "wxcb9d3b1de8acfca5";
	/** QQ平台AppId 1103373270 */
	public static final String QQ_APP_ID = "1103373270";

	/*会员相关*/
	public static String sourceCode = "0303010101";
	public static String dataSource = "hmandroidapp";

	/*会员获取验证码返回code*/
	public final static String PARTY_CODE_W51001 = "W51001";//请输入合法手机号
	public final static String PARTY_CODE_W51002 = "W51002";//请输入图片验证码
	public final static String PARTY_CODE_W51003 = "W51003";//图片验证码错误
	public final static String PARTY_CODE_W51004 = "W51004";//频率过快,请一分钟以后尝试
	public final static String PARTY_CODE_W51005 = "W51005";//请求次数过多,请明天再试
	public final static String PARTY_CODE_W51006 = "W51006";//短信发送失败
}
