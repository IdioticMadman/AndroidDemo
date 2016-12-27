//package net.pmbim.model.activity;
//
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.Toolbar;
//import android.text.TextUtils;
//import android.view.Menu;
//import android.view.MenuItem;
//
//import net.pmbim.model.R;
//import net.yzbim.androidapp.adapter.EntityPropertiesAdapter;
//import net.yzbim.androidapp.application.BimApplication;
//import net.yzbim.androidapp.entity.EntityProperty;
//import net.yzbim.androidapp.events.EntityPropertiesEvent;
//import net.yzbim.androidapp.jobs.LoadCustomPropertyJob;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.ButterKnife;
//import butterknife.InjectView;
//import de.greenrobot.event.EventBus;
//
//public class EntityPropertiesActivity extends AppCompatActivity {
//
//    public static final String TAG = "EntityPropertiesActivity";
//    @InjectView(R.id.rv_aty_entityproperties_list)
//    RecyclerView propertyRecyclerView;
//    @InjectView(R.id.toolbar)
//    Toolbar toolbar;
//    private Context context;
//    private List<EntityProperty> entityProperties;
//    private EntityPropertiesAdapter entityPropertiesAdapter;
//    private String uuid;
//    private ProgressDialog progressDialog;
//    private final int ACTION_SCREEN_102 = 102;
//    private List<EntityProperty> customProperties;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_entity_properties);
//        ButterKnife.inject(this);
//        EventBus.getDefault().register(this);
//        uuid = getIntent().getStringExtra("uuid");
//        context = this;
//        toolbar.setTitle(R.string.entitydetail);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        propertyRecyclerView.setLayoutManager(linearLayoutManager);
//        entityProperties = new ArrayList<>();
//        customProperties = new ArrayList<>();
//        entityPropertiesAdapter = new EntityPropertiesAdapter(context, entityProperties);
//        propertyRecyclerView.setAdapter(entityPropertiesAdapter);
//        progressDialog = new ProgressDialog(context);
//        progressDialog.show();
//        if (!TextUtils.isEmpty(uuid)) {
//            getJobManager().addJobInBackground(new LoadCustomPropertyJob(TAG, uuid, BimApplication.getInstance().getProjectId()));
//        } else {
//            getShortToast(context, "暂无被选中的构件属性");
//        }
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.aty_entity_properties, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                break;
//            case R.id.aty_entity_menu_screen:
//                startActivityForResult(new Intent(context, PropertiesScreenActivity.class), ACTION_SCREEN_102);
//                break;
//            default:
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    public void onEventMainThread(EntityPropertiesEvent entityPropertiesEvent) {
//        if (progressDialog != null && progressDialog.isShowing()) {
//            progressDialog.dismiss();
//        }
//        if (TAG.equals(entityPropertiesEvent.getMsg())) {
//            entityProperties = entityPropertiesEvent.getProperties();
//            if (entityProperties != null && entityProperties.size() > 0) {
//                entityPropertiesAdapter.setEntityProperties(entityProperties);
//                entityPropertiesAdapter.notifyDataSetChanged();
//            } else {
//                getShortToast(context, "此构件暂无属性");
//            }
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (ACTION_SCREEN_102 == requestCode && resultCode == Activity.RESULT_OK && data != null) {
//            List<String> showNames = data.getStringArrayListExtra("SHOW_NAMES");
//            filterProperties(showNames);
//            for (EntityProperty entityProperty : entityProperties) {
//                if ("扩展属性".equals(entityProperty.getPGroupName())) {
//                    customProperties.add(entityProperty);
//                }
//            }
//            entityPropertiesAdapter.setEntityProperties(entityProperties);
//            entityPropertiesAdapter.notifyDataSetChanged();
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        uuid = null;
//        entityProperties.clear();
//        if (EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().unregister(this);
//        }
//        super.onDestroy();
//    }
//
//}
