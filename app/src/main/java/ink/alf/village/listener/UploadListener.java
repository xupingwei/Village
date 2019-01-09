package ink.alf.village.listener;

public interface UploadListener {
    void onUploadSuccess();

    void onUploadFail(Error error);
}
