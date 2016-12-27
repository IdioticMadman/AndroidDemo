package net.pmbim.model.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import net.ezbim.modelview.ControlTree;
import net.ezbim.modelview.modelnode.FloorControlAdapter;
import net.ezbim.modelview.modelnode.ModelControl;
import net.pmbim.model.R;

import java.util.List;

/**
 * Created by hdk on 2016/1/21.
 */
public class ModelFloorActivity extends AppCompatActivity {

    private ControlTree FloorRootTree;
    private FloorControlAdapter modelFloorListAdapter;
    private Context context;
    private boolean isAddAll = false;
    private Toolbar toolbar;
    private ListView lvAtyModelfloor;
    private LinearLayout ll_addAll;
    private ImageView iv_addAll;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modelfloor);
        initView();
        toolbar.setTitle(R.string.choice_floor);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        FloorRootTree = ModelControl.getModelControl().getFloorsTreeRoot().clone();
        modelFloorListAdapter = new FloorControlAdapter(context, FloorRootTree);
        lvAtyModelfloor.setAdapter(modelFloorListAdapter);
        lvAtyModelfloor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ControlTree data = modelFloorListAdapter.getItem(position);
                data.setVisiable(!data.isVisiable(), true);
                doShowHide();
                modelFloorListAdapter.notifyDataSetChanged();
            }
        });
        doShowHide();
        ll_addAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAddAll) {
                    doShowHide(false);
                } else {
                    doShowHide(true);
                }
                doShowHide();
//                modelFloorListAdapter.setFloors(floorDatas);
                modelFloorListAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lvAtyModelfloor = (ListView) findViewById(R.id.lv_aty_modelfloor);
        ll_addAll = (LinearLayout) findViewById(R.id.ll_aty_modelfloor);
        iv_addAll = (ImageView) findViewById(R.id.iv_aty_modelfloor_choise);
    }

    private void doShowHide() {
        List<ControlTree> floors = FloorRootTree.getM_children();
        if (floors != null && floors.size() > 0) {
            int fSize = floors.size();
            int trueSize = 0;
            for (int i = 0; i < fSize; i++) {
                if (floors.get(i).isVisiable()) {
                    trueSize += 1;
                }
            }
            if (trueSize == fSize) {
                iv_addAll.setVisibility(View.VISIBLE);
                isAddAll = true;
            } else {
                iv_addAll.setVisibility(View.INVISIBLE);
                isAddAll = false;
            }
        }
    }

    private void doShowHide(boolean show) {
        List<ControlTree> floors = FloorRootTree.getM_children();
        if (floors != null && floors.size() > 0) {
            int fSize = floors.size();
            for (int i = 0; i < fSize; i++) {
                floors.get(i).setVisiable(show, true);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.comm_ok, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.comm_menu_ok:
//                Intent data = new Intent();
//                data.putParcelableArrayListExtra("tempfloors", floorDatas);
//                setResult(RESULT_OK, data);
//                EventBus.getDefault().post(FloorRootTree);
//                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
