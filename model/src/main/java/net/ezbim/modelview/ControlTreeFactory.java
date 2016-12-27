package net.ezbim.modelview;

import android.support.annotation.Keep;

import java.util.ArrayList;

/**
 * Created by hdk on 16/8/26.
 */
public class ControlTreeFactory {

    @Keep
    public ControlTree createControlTree(String m_name, String m_type, boolean visiable) {
//        BLog.e("ModelView", "create");
        return new ControlTree(m_name, m_type, visiable);
    }

    @Keep
    public void addChildToParent(ControlTree parent, ControlTree child) {
        if (parent != null) {
            if (parent.getM_children() == null)
                parent.setM_children(new ArrayList<ControlTree>());
            parent.getM_children().add(child);
//            BLog.e("ModelView", "addChildToParent");
        }
    }

    @Keep
    public void setParentForChild(ControlTree child, ControlTree parent) {
        child.setM_parent(parent);
//        BLog.e("ModelView", "setParentForChild");
    }

    @Keep
    public void addcompIDForNode(ControlTree node, String compid) {
        if (node.getM_compID() == null) {
            node.setM_compID(new ArrayList<String>());
        }
        node.getM_compID().add(compid);
//        BLog.e("ModelView", "addcompIDForNode");
    }
}
