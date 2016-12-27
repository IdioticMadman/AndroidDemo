package net.ezbim.modelview.modelnode;

/**
 * Created by hdk on 2016/3/18.
 */
public class Node {

    private String name;
    private boolean showing;

    public Node(String name, boolean showing) {
        this.name = name;
        this.showing = showing;
    }

    public Node(boolean showing) {
        this.showing = showing;
    }

    public Node() {
    }

    public Node(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isShowing() {
        return showing;
    }

    public void setShowing(boolean showing) {
        this.showing = showing;
    }
}
