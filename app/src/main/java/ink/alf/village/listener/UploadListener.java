package ink.alf.village.listener;

public interface UploadListener {
    void onUploadSuccess(String key);

    void onUploadFail(Error error);
}
