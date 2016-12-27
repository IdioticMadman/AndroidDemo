package net.ezbim.modelview.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import net.ezbim.modelview.ControlTree;
import net.ezbim.modelview.ModelView;
import net.ezbim.modelview.R;
import net.ezbim.modelview.modelnode.ModelControl;
import net.ezbim.modelview.modelnode.SystemControlAdapter;

/**
 * Created by hdk on 2016/1/26.
 */
public class EntityShowActivity extends AppCompatActivity {

    private ListView lvEntityshowList;
    private ControlTree rootTree;
    private SystemControlAdapter controlAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entityshow);
        lvEntityshowList = (ListView) findViewById(R.id.lv_entityshow_list);
        getSupportActionBar().setTitle("类型专业");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rootTree = ModelControl.getInstance().getDCTTreeRoot();
        controlAdapter = new SystemControlAdapter(this, rootTree, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControlTree treeNode = (ControlTree) view.getTag();
                if (treeNode != null) {
                    treeNode.setVisiable(!treeNode.isVisiable(), true);
                    updateModelDisplay(treeNode);
                    controlAdapter.notifyDataSetChanged();
                }
            }
        });
        lvEntityshowList.setAdapter(controlAdapter);
        lvEntityshowList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ControlTree treeNode = (ControlTree) parent.getItemAtPosition(position);
                if (treeNode != null) {
                    if (treeNode.getM_children() != null) {
                        controlAdapter.setCurrentTree(treeNode);
                        controlAdapter.notifyDataSetChanged();
                    } else {
                        treeNode.setVisiable(!treeNode.isVisiable(), true);
                        updateModelDisplay(treeNode);
                        controlAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    void updateModelDisplay(ControlTree treeNode) {
        if (treeNode.getM_type().equals("Domain")) {
            ModelView.modelSHDomain(treeNode.getM_name(), treeNode.isVisiable(), false);
        } else if (treeNode.getM_type().equals("Category")) {
            ModelView.modelSHCategory(treeNode.getM_name(), treeNode.getM_parent().getM_name(), treeNode.isVisiable(), false);
        } else if (treeNode.getM_type().equals("Template")) {
            ModelView.modelSHEntityType(treeNode.getM_name(), treeNode.getM_parent().getM_name(), treeNode.getM_parent().getM_parent().getM_name(), treeNode.isVisiable(), false);
        } else {

        }
    }

    //返回时判断返回的节点
    @Override
    public void onBackPressed() {
        if (controlAdapter == null || controlAdapter.getCurrentTree() == rootTree) {
            super.onBackPressed();
        } else {
            backList();
        }
    }

    private void backList() {
        if (controlAdapter != null) {
            ControlTree controlTree = controlAdapter.getCurrentTree();
            if (controlTree != null && controlTree.getM_parent() != null) {
                controlAdapter.setCurrentTree(controlTree.getM_parent());
                controlAdapter.notifyDataSetChanged();
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
