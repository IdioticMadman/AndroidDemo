package net.ezbim.modelview.modelnode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.ezbim.modelview.R;

import java.util.List;

/**
 * Created by xk on 16/3/16.
 */
public class NodeAdapter<T extends Node> extends BaseAdapter {
    public static final String ACTION_SYSTEM_SHOW_STATE_CHANGED = "action_system_show_state_changed";
    private Context mContext;
    private List<T> nodeList;

    public NodeAdapter(Context mContext, List<T> nodeEntityList) {
        this.mContext = mContext;
        this.nodeList = nodeEntityList;
    }

    @Override
    public int getCount() {
        return nodeList.size();
    }

    @Override
    public T getItem(int position) {
        return nodeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        ImgClickListener imgClickListener = null;
        if (null == convertView) {
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            convertView = mInflater.inflate(R.layout.item_aty_entityshow, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        imgClickListener = new ImgClickListener(position);
        viewHolder.ivItemEntityshow.setOnClickListener(imgClickListener);
        if (nodeList != null && nodeList.size() > 0) {
            T node = getItem(position);
            if (node instanceof NodeSystem) {
                viewHolder.tvItemEntityname.setText(((NodeSystem) node).getSystem());
            }
            if (node instanceof NodeEntity) {
                viewHolder.tvItemEntityname.setText(node.getName());
                viewHolder.ivItemEntityarrow.setVisibility(View.INVISIBLE);
            }
            if (node instanceof NodeCategory) {
                viewHolder.tvItemEntityname.setText(node.getName());
            }
            if (node.isShowing()) {
                viewHolder.ivItemEntityshow.setImageResource(R.drawable.ic_node_show);
            } else {
                viewHolder.ivItemEntityshow.setImageResource(R.drawable.ic_node_hide);
            }
        }
        return convertView;
    }

    public class ViewHolder {
        TextView tvItemEntityname;
        ImageView ivItemEntityarrow;
        ImageView ivItemEntityshow;
        public ViewHolder(View v) {
            tvItemEntityname = (TextView) v.findViewById(R.id.tv_item_entityname);
            ivItemEntityarrow = (ImageView) v.findViewById(R.id.iv_item_entityarrow);
            ivItemEntityshow = (ImageView) v.findViewById(R.id.iv_item_entityshow);
        }
    }

    //必须要给他设置一个独立的listener 要不然顺序错乱
    private class ImgClickListener implements View.OnClickListener{
        int mpos;

        public ImgClickListener(int mpos) {
            this.mpos = mpos;
        }
        @Override
        public void onClick(View v) {
//            EventBus.getDefault().post(new SimpleEvent(EntityShowActivity.TAG, ACTION_SYSTEM_SHOW_STATE_CHANGED, mpos));
        }
    }
}
