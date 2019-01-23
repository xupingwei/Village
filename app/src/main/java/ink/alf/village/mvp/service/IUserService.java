package ink.alf.village.mvp.service;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IUserService {

    @POST("/user/register")
    @FormUrlEncoded
    Observable<ResponseBody> register(@FieldMap Map<String, Object> params);


    @POST("/user/loginWithPhone")
    @FormUrlEncoded
    Observable<ResponseBody> loginWithPhone(@FieldMap Map<String, Object> params);
}
