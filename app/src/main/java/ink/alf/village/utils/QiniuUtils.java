package ink.alf.village.utils;

import com.qiniu.android.utils.StringUtils;
import com.qiniu.android.utils.UrlSafeBase64;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.GeneralSecurityException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import ink.alf.village.common.QiniuConsts;

/**
 * @author 13793
 */
public class QiniuUtils {

    private SecretKeySpec secretKey;

    /**
     * 下载签名
     *
     * @param baseUrl 待签名文件url，如 http://img.domain.com/u/3.jpg 、
     *                http://img.domain.com/u/3.jpg?imageView2/1/w/120
     * @return
     */
    public static String privateDownloadUrl(String baseUrl) {
        return privateDownloadUrl(baseUrl, 3600);
    }

    /**
     * 下载签名
     *
     * @param baseUrl 待签名文件url，如 http://img.domain.com/u/3.jpg 、
     *                http://img.domain.com/u/3.jpg?imageView2/1/w/120
     * @param expires 有效时长，单位秒。默认3600s
     * @return
     */
    private static String privateDownloadUrl(String baseUrl, long expires) {
        long deadline = System.currentTimeMillis() / 1000 + expires;
        return privateDownloadUrlWithDeadline(baseUrl, deadline);
    }

    private static String privateDownloadUrlWithDeadline(String baseUrl, long deadline) {
        StringBuilder b = new StringBuilder();
        b.append(baseUrl);
        int pos = baseUrl.indexOf("?");
        if (pos > 0) {
            b.append("&e=");
        } else {
            b.append("?e=");
        }
        b.append(deadline);
        String token = sign(StringUtils.utf8Bytes(b.toString()));
        b.append("&token=");
        b.append(token);
        return b.toString();
    }


    private static Mac createMac() {
        Mac mac;
        try {
            mac = javax.crypto.Mac.getInstance("HmacSHA1");
            byte[] sk = StringUtils.utf8Bytes(QiniuConsts.SECRETKEY);
            SecretKeySpec secretKeySpec = new SecretKeySpec(sk, "HmacSHA1");
            mac.init(secretKeySpec);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
        return mac;
    }

    private static String sign(byte[] data) {
        Mac mac = createMac();
        String encodedSign = UrlSafeBase64.encodeToString(mac.doFinal(data));
        return QiniuConsts.ACCESSKEY + ":" + encodedSign;
    }

    private static String sign(String data) {
        return sign(StringUtils.utf8Bytes(data));
    }


    private static final String MAC_NAME = "HmacSHA1";
    private static final String ENCODING = "UTF-8";

    /**
     * @return
     */
    public static String getQiniuToken() {
        JSONObject json = new JSONObject();
        long deadline = System.currentTimeMillis() / 1000 + 3600;
        try {
            json.put("deadline", deadline);// 有效时间为一个小时
            json.put("scope", QiniuConsts.BUCKETNAME);//存储空间的名字
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String encodedPutPolicy = UrlSafeBase64.encodeToString(json
                .toString().getBytes());
        byte[] sign = new byte[0];

        try {
            sign = HmacSHA1Encrypt(encodedPutPolicy, QiniuConsts.SECRETKEY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String encodedSign = UrlSafeBase64.encodeToString(sign);
        String uploadToken = QiniuConsts.ACCESSKEY + ':' + encodedSign + ':'
                + encodedPutPolicy;
        return uploadToken;
    }

    public static byte[] HmacSHA1Encrypt(String encryptText, String encryptKey)
            throws Exception {
        byte[] data = encryptKey.getBytes(ENCODING);
        // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKeySpec secretKey = new SecretKeySpec(data, MAC_NAME);
        // 生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance(MAC_NAME);
        // 用给定密钥初始化 Mac 对象
        mac.init(secretKey);
        byte[] text = encryptText.getBytes(ENCODING);
        // 完成 Mac 操作
        return mac.doFinal(text);
    }
}
