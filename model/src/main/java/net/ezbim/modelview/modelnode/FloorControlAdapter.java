package net.ezbim.modelview.modelnode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.ezbim.modelview.ControlTree;
import net.pmbim.model.R;

/**
 * Created by hdk on 16/9/1.
 */
public class FloorControlAdapter extends BaseAdapter {

    Context context;
    ControlTree rootTree;
    ControlTree currentTree;

    public FloorControlAdapter(Context context, ControlTree rootTree) {
        this.context = context;
        this.rootTree = rootTree;
        this.currentTree = rootTree;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_aty_modelfloor, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ControlTree controlTree = getItem(i);
        viewHolder.tvItemAtyModelfloorName.setText(controlTree.getM_name());
        if (controlTree.isVisiable()) {
            viewHolder.ivItemAtyModelfloorChoise.setVisibility(View.VISIBLE);
        } else {
            viewHolder.ivItemAtyModelfloorChoise.setVisibility(View.INVISIBLE);
        }
        return view;
    }


    public class ViewHolder {
        ImageView ivItemAtyModelfloorChoise;
        TextView tvItemAtyModelfloorName;

        public ViewHolder(View v) {
            ivItemAtyModelfloorChoise = (ImageView) v.findViewById(R.id.iv_item_aty_modelfloor_choise);
            tvItemAtyModelfloorName = (TextView) v.findViewById(R.id.tv_item_aty_modelfloor_name);
        }
    }
}
