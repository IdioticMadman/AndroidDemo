package net.ezbim.modelview;

import android.support.annotation.Keep;

import java.util.ArrayList;

/**
 * Created by hdk on 16/8/25.
 */

public class ControlTree implements Cloneable {
    String m_name;

    ///节点类型
    String m_type;

    ///父节点
    ControlTree m_parent;

    boolean visiable;

    ///子节点
    ArrayList<ControlTree> m_children;

    ///节点包含的构件UniqueID集合
    ArrayList<String> m_compID;

    @Keep
    public ControlTree(String m_name, String m_type, boolean visiable) {
        this.m_name = m_name;
        this.m_type = m_type;
        this.visiable = visiable;
//        setM_children(new ArrayList<ControlTree>());
//        setM_compID(new ArrayList<String>());
    }

    @Keep
    public String getM_name() {
        return m_name;
    }

    @Keep
    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    @Keep
    public String getM_type() {
        return m_type;
    }

    @Keep
    public void setM_type(String m_type) {
        this.m_type = m_type;
    }

    @Keep
    public ControlTree getM_parent() {
        return m_parent;
    }

    @Keep
    public void setM_parent(ControlTree m_parent) {
        this.m_parent = m_parent;
    }

    @Keep
    public ArrayList<ControlTree> getM_children() {
        return m_children;
    }

    @Keep
    public void setM_children(ArrayList<ControlTree> m_children) {
        this.m_children = m_children;
    }

    public ArrayList<String> getM_compID() {
        return m_compID;
    }

    public void setM_compID(ArrayList<String> m_compID) {
        this.m_compID = m_compID;
    }

    @Keep
    public boolean isVisiable() {
        return visiable;
    }

    @Keep
    public void setVisiable(boolean visiable, boolean ergodicChildren) {
        this.visiable = visiable;
        if (ergodicChildren) {
            if (m_children != null)
                for (ControlTree tree : m_children) {
                    tree.setVisiable(visiable, true);
                }
        }
        if (m_parent != null) {
            boolean pvisiable = false;
            for (ControlTree tree : m_parent.getM_children()) {
                if (tree.isVisiable())
                    pvisiable = true;
            }
            m_parent.setVisiable(pvisiable, false);
        }
    }

    @Override
    public ControlTree clone() {
        ControlTree tree = null;
        try {
            tree = (ControlTree) super.clone();
            if (m_children != null) {
                ArrayList<ControlTree> newChildren = new ArrayList<>();
                for (ControlTree child : m_children) {
                    newChildren.add(child.clone());
                }
                tree.setM_children(newChildren);
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return tree;
    }
}
