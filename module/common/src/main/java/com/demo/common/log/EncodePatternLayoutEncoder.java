package com.demo.common.log;

/**
 * Created by lenovo on 2016/7/6.
 */



import com.demo.common.log.utils.EncryptUtil;

import java.io.IOException;

import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;


public class EncodePatternLayoutEncoder extends PatternLayoutEncoder {
    private String key;
    private Boolean doEncryption = true;
    private String test = "123";

    public void setKey(String key){
        this.key = key;
    }

    public void setDoEncryption(Boolean doEncryption) {
        this.doEncryption = doEncryption;
    }

    public void setTest(String test) {
        this.test = test;
    }

    private byte[] convertToBytes(String s) {
        return s.getBytes();
    }
    @Override
    public void doEncode(ILoggingEvent event) throws IOException {
        String txt = layout.doLayout(event);
        if(this.doEncryption){
            txt = EncryptUtil.encode(txt,this.key)+"\n";
        }
        outputStream.write(convertToBytes(txt));
        if(isImmediateFlush()){
            outputStream.flush();
        }
    }


}
