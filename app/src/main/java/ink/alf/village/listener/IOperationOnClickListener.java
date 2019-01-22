package ink.alf.village.listener;

import java.util.List;

public interface IOperationOnClickListener {

    void onFollowClickListener(List<String> followIds, String userId,int position);

    void onCollectClickListener(List<String> collectIds, String userId,int position);
}
