package net.ezbim.modelview.simple;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.ezbim.modelview.R;

import java.util.List;

/**
 * Created by hdk on 2016/3/11.
 */
public class EntityPropertiesAdapter extends RecyclerView.Adapter<EntityPropertiesAdapter.ViewHolder> {

    private LayoutInflater minflater;
    private List<EntityProperty> entityProperties;

    public EntityPropertiesAdapter(Context context, List<EntityProperty> entityProperties) {
        this.minflater = LayoutInflater.from(context);
        this.entityProperties = entityProperties;
    }

    public void setEntityProperties(List<EntityProperty> entityProperties) {
        this.entityProperties = entityProperties;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = minflater.inflate(R.layout.item_aty_entityproperties_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        EntityProperty property = entityProperties.get(position);
        holder.tvPropertytypeItemEntitypropertiesList.setText(property.getPGroupName());
        holder.tvPropertykeyItemEntitypropertiesList.setText(property.getPropertiesKey());
        holder.tvPropertyvalueItemEntitypropertiesList.setText(property.getPropertiesValue());
        int preposition = position - 1;
        if (preposition != -1 && entityProperties.get(preposition).getPGroupName().equals(property.getPGroupName())) {
            holder.tvPropertytypeItemEntitypropertiesList.setVisibility(View.GONE);
        } else {
            holder.tvPropertytypeItemEntitypropertiesList.setVisibility(View.VISIBLE);
        }
        holder.itemView.setTag(property);
    }

    @Override
    public int getItemCount() {
        return entityProperties == null ? 0 : entityProperties.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPropertytypeItemEntitypropertiesList;
        TextView tvPropertykeyItemEntitypropertiesList;
        TextView tvPropertyvalueItemEntitypropertiesList;

        public ViewHolder(View itemView) {
            super(itemView);
            tvPropertytypeItemEntitypropertiesList = (TextView)itemView.findViewById(R.id.tv_propertytype_item_entityproperties_list);
            tvPropertykeyItemEntitypropertiesList = (TextView)itemView.findViewById(R.id.tv_propertykey_item_entityproperties_list);
            tvPropertyvalueItemEntitypropertiesList = (TextView)itemView.findViewById(R.id.tv_propertyvalue_item_entityproperties_list);
        }
    }


}
