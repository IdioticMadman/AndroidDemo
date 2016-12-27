package net.ezbim.modelview.modelnode;

import java.util.List;

/**
 * Created by xk on 16/3/15.
 */
public class NodeCategory extends Node implements Cloneable {

    private List<NodeEntity> entities;
    private String sysName;

    public NodeCategory(){

    }

    public NodeCategory(String name, boolean showing, List<NodeEntity> entities) {
        super(name, showing);
        this.entities = entities;
    }

    public NodeCategory(String name, boolean showing) {
        super(name, showing);
    }

    public NodeCategory(String name, List<NodeEntity> entities) {
        super(name);
        this.entities = entities;
    }

    public int getLevel() {
        return 1;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public List<NodeEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<NodeEntity> entities) {
        this.entities = entities;
    }

    public Object clone() {
        NodeCategory o = null;
        try {
            o = (NodeCategory) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }
}
