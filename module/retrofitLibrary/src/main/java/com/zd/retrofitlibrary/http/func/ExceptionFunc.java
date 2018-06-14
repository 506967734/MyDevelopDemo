package com.zd.retrofitlibrary.http.func;

import com.zd.retrofitlibrary.exception.FactoryException;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import rx.Observable;
import rx.functions.Func1;

/**
 * 异常处理
 * Created by WZG on 2017/3/23.
 */

public class ExceptionFunc implements Func1<Throwable, Observable> {
    private static final Logger logger = LoggerFactory.getLogger("ExceptionFunc");

    @Override
    public Observable call(Throwable throwable) {
        logger.debug(throwable.getMessage());
        return Observable.error(FactoryException.analysisException(throwable));
    }
}
