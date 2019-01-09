package ink.alf.village.listener;

public interface UploadMutliListener {
    void onUploadMutliSuccess(String urls);

    void onUploadMutliFail(Error error);
}
