package net.ezbim.modelview.modelnode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.ezbim.modelview.ControlTree;
import net.ezbim.modelview.R;

/**
 * Created by hdk on 16/9/1.
 */
public class SystemControlAdapter extends BaseAdapter {

    Context context;
    ControlTree rootTree;
    ControlTree currentTree;
    View.OnClickListener displayControlListener;

    public SystemControlAdapter(Context context, ControlTree rootTree, View.OnClickListener displayControlListener) {
        this.context = context;
        this.rootTree = rootTree;
        this.currentTree = rootTree;
        this.displayControlListener = displayControlListener;
    }

    public ControlTree getRootTree() {
        return rootTree;
    }

    public void setRootTree(ControlTree rootTree) {
        this.rootTree = rootTree;
    }

    public ControlTree getCurrentTree() {
        return currentTree;
    }

    public void setCurrentTree(ControlTree currentTree) {
        this.currentTree = currentTree;
    }

    @Override
    public int getCount() {
        return (currentTree == null || currentTree.getM_children() == null) ? 0 : currentTree.getM_children().size();
    }

    @Override
    public ControlTree getItem(int i) {
        return (currentTree == null || currentTree.getM_children() == null) ? null : currentTree.getM_children().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_aty_entityshow, viewGroup, false);
            viewHolder = new ViewHolder(view);
            viewHolder.ivItemEntityshow.setOnClickListener(displayControlListener);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ControlTree controlTree = getItem(i);
        viewHolder.tvItemEntityname.setText(controlTree.getM_name());
        viewHolder.ivItemEntityshow.setTag(controlTree);
        if (controlTree.isVisiable()) {
            viewHolder.ivItemEntityshow.setImageResource(R.drawable.ic_node_show);
        } else {
            viewHolder.ivItemEntityshow.setImageResource(R.drawable.ic_node_hide);
        }
        return view;
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
}
