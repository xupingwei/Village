package ink.alf.village.mvp.service;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.POST;

public interface IMainService {

    @POST("/region/findAll")
    Observable<ResponseBody> listRegions();
}
