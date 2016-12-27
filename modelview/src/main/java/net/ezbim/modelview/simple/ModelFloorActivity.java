package net.ezbim.modelview.simple;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import net.ezbim.modelview.ControlTree;
import net.ezbim.modelview.R;
import net.ezbim.modelview.modelnode.FloorControlAdapter;
import net.ezbim.modelview.modelnode.ModelControl;

import java.util.List;

/**
 * Created by hdk on 2016/1/21.
 */
public class ModelFloorActivity extends AppCompatActivity {

    private ListView lvAtyModelfloor;
    private LinearLayout ll_addAll;
    private ImageView iv_addAll;
    public static ControlTree floorRootTree;
    private FloorControlAdapter modelFloorListAdapter;
    private Context context;
    private boolean isAddAll = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modelfloor);
        getSupportActionBar().setTitle("楼层选择");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lvAtyModelfloor = (ListView) findViewById(R.id.lv_aty_modelfloor);
        ll_addAll = (LinearLayout) findViewById(R.id.ll_aty_modelfloor);
        iv_addAll = (ImageView) findViewById(R.id.iv_aty_modelfloor_choise);
        context = this;
        lvAtyModelfloor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ControlTree data = modelFloorListAdapter.getItem(position);
                data.setVisiable(!data.isVisiable(), true);
                doShowHide();
                modelFloorListAdapter.notifyDataSetChanged();
            }
        });
        ll_addAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAddAll) {
                    doShowHide(false);
                } else {
                    doShowHide(true);
                }
                doShowHide();
                modelFloorListAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ModelControl.getInstance() != null && ModelControl.getInstance().getFloorsTreeRoot() != null) {
            floorRootTree = ModelControl.getInstance().getFloorsTreeRoot().clone();
        }
        doShowHide();
        modelFloorListAdapter = new FloorControlAdapter(context, floorRootTree);
        lvAtyModelfloor.setAdapter(modelFloorListAdapter);
    }

    private void doShowHide() {
        List<ControlTree> floors = floorRootTree.getM_children();
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
        List<ControlTree> floors = floorRootTree.getM_children();
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
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        if (item.getItemId() == R.id.comm_menu_ok) {
            setResult(RESULT_OK);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
