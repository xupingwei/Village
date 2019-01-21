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
import ink.alf.village.bean.Region;

/**
 * @author 13793
 */
public class AreaAdapter extends BaseAdapter {

    private Context mContext;
    private List<Region> regions;

    public AreaAdapter(Context mContext, List<Region> regions) {
        this.mContext = mContext;
        this.regions = regions;
    }

    @Override
    public int getCount() {
        return regions.size();
    }

    @Override
    public Object getItem(int position) {
        return regions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_grid_area, parent,
                    false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.itemTvArea.setText(regions.get(position).getDistrict());
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.item_tv_area)
        TextView itemTvArea;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
