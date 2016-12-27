package net.ezbim.modelview.modelnode;

import java.util.List;

/**
 * Created by xk on 16/3/15.
 */
public class NodeSystem extends Node implements Cloneable{
    private String system;
    private List<NodeCategory> categories;

    public NodeSystem(){

    }

    public NodeSystem(String system, boolean showing, List<NodeCategory> categories) {
        super(showing);
        this.system = system;
        this.categories = categories;
    }

    public NodeSystem(String system, boolean showing) {
        super(showing);
        this.system = system;
    }

    public NodeSystem(String system, List<NodeCategory> categories) {
        this.system = system;
        this.categories = categories;
    }

    public int getLevel() {
        return 0;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public List<NodeCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<NodeCategory> categories) {
        this.categories = categories;
    }

    public Object clone() {
        NodeSystem o = null;
        try {
            o = (NodeSystem) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }
}
