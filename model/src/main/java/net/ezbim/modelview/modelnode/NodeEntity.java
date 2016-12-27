package net.ezbim.modelview.modelnode;

/**
 * Created by xk on 16/3/15.
 */
public class NodeEntity extends Node implements Cloneable{


    private String categoryName;

    public NodeEntity(String name) {
        super(name);
    }

    public NodeEntity(String name, boolean showing) {
        super(name, showing);
    }

    public int getLevel() {
        return 2;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Object clone() {
        NodeEntity o = null;
        try {
            o = (NodeEntity) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }

    public NodeEntity(){

    }
}
