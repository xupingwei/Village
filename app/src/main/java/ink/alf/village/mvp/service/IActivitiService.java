package ink.alf.village.mvp.service;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IActivitiService {

    @POST("/activiti/create")
    @FormUrlEncoded
    Observable<ResponseBody> createActiviti(@Field("token") String token,
                                            @FieldMap Map<String, Object> bean);


    /**
     * activiti  最新
     */
    @POST("/activiti/listNewer")
    @FormUrlEncoded
    Observable<ResponseBody> listNewer(@FieldMap Map<String, Object> bean);

    /**
     * 分類查詢
     *
     * @param bean 參數
     * @return
     */
    @POST("/activiti/listCatagory")
    @FormUrlEncoded
    Observable<ResponseBody> listCatagory(@FieldMap Map<String, Object> bean);

    /**
     * activiti 點讚
     *
     * @param bean 參數
     * @return
     */
    @POST("/activiti/follow")
    @FormUrlEncoded
    Observable<ResponseBody> follow(@FieldMap Map<String, Object> bean);


    /**
     * activiti 收藏
     *
     * @param bean 參數
     * @return
     */
    @POST("/activiti/collect")
    @FormUrlEncoded
    Observable<ResponseBody> collect(@FieldMap Map<String, Object> bean);

}
