/**
 * Copyright (C), 2014-2018, 大连华信计算机技术股份有限公司
 * FileName: FastJsonConverterFactory
 * Author: 152843
 * Date: 2018/12/3 15:32
 * Description: fastjson转换
 * History:
 */
package ink.alf.village.retrofit.converter;


import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @ClassName: FastJsonConverterFactory
 * @Description: java类作用描述
 * @Author: 152843
 * @Date: 2018/12/3 15:32
 */
public class FastJsonConverterFactory  extends Converter.Factory {
    public static FastJsonConverterFactory create() {
        return new FastJsonConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new FastJsonResponseBodyConverter<>(type);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new FastJsonRequestBodyConverter<>();
    }
}
