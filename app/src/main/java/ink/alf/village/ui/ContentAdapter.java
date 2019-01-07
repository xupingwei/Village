package ink.alf.village.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ink.alf.village.R;
import ink.alf.village.bean.ActivitiBean;

/**
 * @author 13793
 */
public class ContentAdapter extends BaseAdapter {

    private static final String TAG = "ContentAdapter";

    private Context mContext;
    private List<ActivitiBean> activitiBeans;

    public ContentAdapter(Context mContext, List<ActivitiBean> activitiBeans) {
        this.mContext = mContext;
        this.activitiBeans = activitiBeans;
    }

    @Override
    public int getCount() {
        return activitiBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return activitiBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (null == holder) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_home, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        ActivitiBean bean = activitiBeans.get(position);
        holder.tvUserName.setText(bean.getPushName());
        return convertView;
    }

    static class ViewHolder {

        @BindView(R.id.tv_user_name)
        TextView tvUserName;

        public ViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
        }
    }
}
