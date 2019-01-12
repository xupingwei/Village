package ink.alf.village.service;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IMainService {

    @POST("/activiti/create")
    @FormUrlEncoded
    Observable<ResponseBody> createActiviti(@Field("token") String token,
                                            @FieldMap Map<String, Object> bean);
}
