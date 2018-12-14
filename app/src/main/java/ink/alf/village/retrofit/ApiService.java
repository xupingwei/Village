/**
 * Copyright (C), 2014-2018, 大连华信计算机技术股份有限公司
 * FileName: ApiService
 * Author: 152843
 * Date: 2018/12/3 17:51
 * Description: 接口信息
 * History:
 */
package ink.alf.village.retrofit;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @ClassName: ApiService
 * @Description: java类作用描述
 * @Author: 152843
 * @Date: 2018/12/3 17:51
 */
public interface ApiService {

    @POST("{url}")
    @FormUrlEncoded
    Observable<ResponseBody> executePost(
            @Path("url") String url,
            @FieldMap Map<String, String> maps);

}
