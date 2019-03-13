package com.schoolpartime.schoolpartime.net.request.base;

import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;

public interface RequestResult {
    void success(ResultModel resultModel);
    void fail(Throwable e);

}
