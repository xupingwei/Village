package ink.alf.village.view;

public interface ILoginView {
    void wxLoginSuccess(String uuid);

    void wxLoginFailed(String msg, int code);
}
