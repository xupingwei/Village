/**
 * Copyright (C), 2014-2018, 大连华信计算机技术股份有限公司
 * FileName: FastJsonResponseBodyConverter
 * Author: 152843
 * Date: 2018/12/3 15:41
 * Description: fastjson转换
 * History:
 */
package ink.alf.village.retrofit.converter;


import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Converter;

/**
 * @ClassName: FastJsonResponseBodyConverter
 * @Description: java类作用描述
 * @Author: 152843
 * @Date: 2018/12/3 15:41
 */
public class FastJsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Type type;

    public FastJsonResponseBodyConverter(Type type) {
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        BufferedSource bufferedSource = Okio.buffer(value.source());
        String tempStr = bufferedSource.readUtf8();
        bufferedSource.close();
        return JSON.parseObject(tempStr, type);

    }
}