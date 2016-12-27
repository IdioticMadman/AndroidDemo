package net.ezbim.modelview.simple;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import net.ezbim.modelview.ModelView;
import net.ezbim.modelview.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class EntityPropertiesActivity extends AppCompatActivity {

    public static final String ENTITY_PROP_UUID = "ENTITY_PROP_UUID";
    private RecyclerView propertyRecyclerView;
    private Context context;
    private List<EntityProperty> entityProperties;
    private EntityPropertiesAdapter entityPropertiesAdapter;
    private String entityUUID;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity_properties);
        propertyRecyclerView = (RecyclerView)findViewById(R.id.rv_aty_entityproperties_list);
        entityUUID = getIntent().getStringExtra(ENTITY_PROP_UUID);
        context = this;
        getSupportActionBar().setTitle("构件属性");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        propertyRecyclerView.setLayoutManager(linearLayoutManager);
        entityProperties = new ArrayList<>();
        entityPropertiesAdapter = new EntityPropertiesAdapter(context, entityProperties);
        propertyRecyclerView.setAdapter(entityPropertiesAdapter);
        showProgress("加载中");
        if (!TextUtils.isEmpty(entityUUID)) {
            loadProperties();
            entityPropertiesAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(context, "暂无被选中的构件属性",Toast.LENGTH_SHORT).show();
        }
        dismissProgress();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        entityUUID = null;
        entityProperties.clear();
        super.onDestroy();
    }

    private void showProgress(String msg) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
        }
        progressDialog.setMessage(msg);
        progressDialog.show();
    }


    private void dismissProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void loadProperties(){
        HashMap<String, HashMap<String, String>> entityinfo = ModelView.getEntityInfo(entityUUID);
        if (entityinfo != null && entityinfo.size() > 0) {
            for (String group : entityinfo.keySet()) {
                HashMap<String, String> properties = entityinfo.get(group);
                for (String propertykey : properties.keySet()) {
                    EntityProperty entityProperty = new EntityProperty();
                    entityProperty.setPGroupName(group);
                    entityProperty.setPropertiesKey(propertykey);
                    entityProperty.setPropertiesValue(properties.get(propertykey));
                    if (entityProperty.getPGroupName().equals("基础属性")) {
                        entityProperties.add(0, entityProperty);
                    } else {
                        entityProperties.add(entityProperty);
                    }
                }
            }
        }
    }
}
