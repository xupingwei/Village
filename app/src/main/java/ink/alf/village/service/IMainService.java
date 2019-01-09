package ink.alf.village.service;

import ink.alf.village.bean.ActivitiBean;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IMainService {

    @POST("/activiti/create")
    @FormUrlEncoded
    Observable<ResponseBody> createActiviti(String token, ActivitiBean bean);
}
