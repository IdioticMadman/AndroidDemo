package net.ezbim.modelview.modelnode;

import java.util.List;

/**
 * Created by xk on 16/3/15.
 */
public class UploadViewPort {

    private List<String> visiblefloor;
    private List<NodeSystem> visibleEntities;
    private boolean IsVisible;
    private List<String> guids;

    public UploadViewPort() {
    }

    public UploadViewPort(List<String> visiblefloor, List<NodeSystem> visibleEntities, boolean isVisible, List<String> guids) {
        this.visiblefloor = visiblefloor;
        this.visibleEntities = visibleEntities;
        IsVisible = isVisible;
        this.guids = guids;
    }

    public List<String> getVisiblefloor() {
        return visiblefloor;
    }

    public void setVisiblefloor(List<String> visiblefloor) {
        this.visiblefloor = visiblefloor;
    }

    public List<NodeSystem> getVisibleEntities() {
        return visibleEntities;
    }

    public void setVisibleEntities(List<NodeSystem> visibleEntities) {
        this.visibleEntities = visibleEntities;
    }

    public boolean isVisible() {
        return IsVisible;
    }

    public void setIsVisible(boolean isVisible) {
        IsVisible = isVisible;
    }

    public List<String> getGuids() {
        return guids;
    }

    public void setGuids(List<String> guids) {
        this.guids = guids;
    }
}
