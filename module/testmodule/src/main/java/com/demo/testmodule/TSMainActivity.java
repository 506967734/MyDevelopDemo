package com.demo.testmodule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

public class TSMainActivity extends Activity {

    private Button mBtnRpcSetting;
    private Button ts_btn_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ts_activity_main);

        findViewById(R.id.ts_btn_mock).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TSMainActivity.this, MockTestActivity.class);
                startActivity(intent);
            }
        });
    }
}
