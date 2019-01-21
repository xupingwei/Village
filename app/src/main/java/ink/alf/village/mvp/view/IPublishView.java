package ink.alf.village.mvp.view;

/**
 * @author 13793
 */
public interface IPublishView {
    /**
     * 上传图片成功
     *
     * @param imagesPath 图片路径
     */
    void uploadImageSuccess(String imagesPath);

    /**
     * 图片上传失败
     *
     * @param message   失败信息
     * @param errorCode 失败code
     */
    void uploadImageFailure(String message, int errorCode);


    void insertActivitiSuccess();

    void insertActivitiFailure(String message, int code);
}
