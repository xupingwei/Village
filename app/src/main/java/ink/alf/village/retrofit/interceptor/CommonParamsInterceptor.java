/**
 * Copyright (C), 2014-2018, 大连华信计算机技术股份有限公司
 * FileName: CommonParamsInterceptor
 * Author: 152843
 * Date: 2018/12/3 15:16
 * Description: 公有参数
 * History:
 */
package ink.alf.village.retrofit.interceptor;


import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: CommonParamsInterceptor
 * @Description: java类作用描述
 * @Author: 152843
 * @Date: 2018/12/3 15:16
 */
public class CommonParamsInterceptor {

    public CommonParamsInterceptor() {
    }

    public BasicParamsInterceptor getInterceptor() {
        Map<String, String> paramsMap = new HashMap<>();
//        if (StringUtils.isNotEmpty(InfoData.mc)) {
//            paramsMap.put("mc", InfoData.mc);
//            paramsMap.put("storeId", String.valueOf(InfoData.storeId));
//            paramsMap.put("opid", String.valueOf(InfoData.opid));
//            paramsMap.put("terminal", "PHONE");
//            paramsMap.put("atoken", InfoData.atoken);
//            paramsMap.put("register", "0");
//        }
        BasicParamsInterceptor.Builder basic = new BasicParamsInterceptor.Builder();
        basic.addParamsMap(paramsMap);
        basic.addHeaderParam("User-Agent", backUA());
        return basic.build();
    }

    private String backUA() {
//        return new StringBuffer().append(AppUtil.getAppName()).append(";")
//                .append(AppUtil.getAppVersionName()).append(";")
//                .append("official_distribution").append(";")
//                .append(AppUtil.getSystem()).append(";")
//                .append(AppUtil.getAndroidSystemVersion()).append(";")
//                .append(AppUtil.getAndroidModel()).append(";")
//                .append(AppUtil.getAndroidBrand()).append(";")
//                .append(String.valueOf(InfoData.mc)).append(";")
//                .append(String.valueOf(InfoData.storeId)).toString();
        return "";
    }

}
