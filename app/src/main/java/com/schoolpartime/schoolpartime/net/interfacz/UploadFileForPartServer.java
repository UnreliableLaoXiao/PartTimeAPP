package com.schoolpartime.schoolpartime.net.interfacz;

import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;

import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

public interface UploadFileForPartServer {

    @Multipart
    @POST("/imgs/upload")
    Observable<ResultModel<String>> uploadFile(@Part MultipartBody.Part part, @Part(value = "id") long id,@Part(value = "type") int type);

}
