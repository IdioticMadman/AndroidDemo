package net.ezbim.modelview.modelnode;


import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import net.ezbim.modelview.ControlTree;
import net.ezbim.modelview.ModelView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static net.ezbim.modelview.modelnode.ModelControl.ModelResult.ALREADYHAVE;
import static net.ezbim.modelview.modelnode.ModelControl.ModelResult.FAILED;
import static net.ezbim.modelview.modelnode.ModelControl.ModelResult.NOHAVE;
import static net.ezbim.modelview.modelnode.ModelControl.ModelResult.SUCCESS;

/**
 * Created by xk on 16/3/24.
 */
public class ModelControl {

    public enum ModelResult {
        FAILED, SUCCESS, NOHAVE, ALREADYHAVE
    }

    private boolean isTransparentAll = false;
    private float opacity = 0.6f;
    private ControlTree DCTTreeRoot;
    private ControlTree floorsTreeRoot;
    private List<String> onlyShowEntities;//只显的构件list
    private List<String> highLightEntities;//高亮的构件list
    private List<String> hideEntities;//隐藏的构件list

    private static class SingletonHolder {
        private static final ModelControl INSTANCE = new ModelControl();
    }

    private ModelControl() {
    }

    public static final ModelControl getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 初始化Modelview
     *
     * @param width
     * @param height
     */
    public void initModelView(int width, int height) {
        ModelView.init(width, height);
    }

    /**
     * 初始化modelview跟六面体
     *
     * @param context
     * @param width
     * @param height
     */
    public void initModelViewAndSixCube(Context context, int width, int height) {
        ModelView.init(width, height);
        this.initSixCube(context);
        this.createSixCube(context);
        this.setSixCubePosition(context, width, height, true);
    }

    /**
     * 设置不透明度
     *
     * @param opint
     */
    private void updateOpacity(int opint) {
        opacity = 0.4f + (0.5f - (opint / 100.0f));
    }

    /**
     * 获取隐藏的构件集合
     *
     * @return
     */
    public List<String> getHideEntities() {
        if (hideEntities == null) {
            hideEntities = new ArrayList<>();
        }
        return hideEntities;
    }

    /**
     * 获取只显的构件集合
     *
     * @return
     */
    public List<String> getOnlyShowEntities() {
        if (onlyShowEntities == null) {
            onlyShowEntities = new ArrayList<>();
        }
        return onlyShowEntities;
    }

    /**
     * 重置构件的隐藏、高亮、只显
     *
     * @return
     */
    public void restoreHistory() {
        if (hideEntities != null && hideEntities.size() > 0) {
            for (String uuid : hideEntities) {
                ModelView.hiddenEntity(uuid);
            }
        }
        if (onlyShowEntities != null && onlyShowEntities.size() > 0) {
            for (String uuid : onlyShowEntities) {
                ModelView.hiddenOtherEntities(uuid);
            }
        }
        if (highLightEntities != null && highLightEntities.size() > 0) {
            for (String uuid : highLightEntities) {
                ModelView.highlightWithOpacity(uuid, opacity, isTransparentAll);
            }
        }
    }

    /**
     * 控制构件隐藏
     *
     * @param uuid
     * @param isadd true:
     *              增加隐藏的构件
     *              <p>
     *              false:
     *              移除隐藏的构件
     * @return
     */
    public ModelResult ctrlHideList(String uuid, boolean isadd) {
        if (hideEntities == null) {
            hideEntities = new ArrayList<>();
        }
        if (isadd) {
            if (hideEntities.contains(uuid)) {
                return ALREADYHAVE;
            } else {
                ModelView.hiddenEntity(uuid);
                hideEntities.add(uuid);
                return SUCCESS;
            }
        } else {
            if (!hideEntities.contains(uuid)) {
                return NOHAVE;
            } else {
                ModelView.unHiddenEntity(uuid);
                hideEntities.remove(uuid);
                return SUCCESS;
            }
        }
    }

    /**
     * 控制构件只显
     *
     * @param uuid
     * @param isadd true:
     *              增加只显的构件
     *              <p>
     *              false:
     *              移除只显的构件
     * @return
     */
    public ModelResult ctrlOnlyShowList(String uuid, boolean isadd) {
        if (!TextUtils.isEmpty(uuid)) {
            if (onlyShowEntities == null) {
                onlyShowEntities = new ArrayList<>();
            }
            if (isadd) {
                if (onlyShowEntities.contains(uuid)) {
                    return ALREADYHAVE;
                } else {
                    ModelView.hiddenOtherEntities(uuid);
                    onlyShowEntities.add(uuid);
                    return SUCCESS;
                }
            } else {
                if (!onlyShowEntities.contains(uuid)) {
                    return NOHAVE;
                } else {
                    ModelView.unHiddenOtherEntities(uuid);
                    onlyShowEntities.remove(uuid);
                    return SUCCESS;
                }
            }
        }
        return FAILED;
    }

    /**
     * 控制构件高亮
     *
     * @param uuid
     * @param isadd true:
     *              增加高亮的构件
     *              <p>
     *              false:
     *              移除高亮的构件
     * @return
     */
    public ModelResult ctrlHighList(String uuid, boolean isadd) {
        if (highLightEntities == null) {
            highLightEntities = new ArrayList<>();
        }
        if (isadd) {
            if (!highLightEntities.contains(uuid)) {
                highLightEntities.add(uuid);
            }
            if (uuid != null && !uuid.equals("")) {
                ModelView.highlightWithOpacity(uuid, opacity, isTransparentAll);
            }
        } else {
            if (highLightEntities.contains(uuid)) {
                highLightEntities.remove(uuid);
            }
            if (uuid != null && !uuid.equals("")) {
                ModelView.unHighlight(uuid);
            }
        }
        return SUCCESS;
    }

    /**
     * 控制构件高亮集合
     *
     * @param uuids
     * @param isadd true:
     *              增加高亮的构件
     *              <p>
     *              false:
     *              移除高亮的构件
     * @return
     */
    public ModelResult ctrlHighList(List<String> uuids, boolean isadd) {
        if (highLightEntities == null) {
            highLightEntities = new ArrayList<>();
        }
        if (isadd) {
            for (String uuid : uuids) {
                if (!highLightEntities.contains(uuid)) {
                    highLightEntities.add(uuid);
                    ModelView.highlightWithOpacity(uuid, opacity, isTransparentAll);
                }
            }
        } else {
            for (String uuid : uuids) {
                if (highLightEntities.contains(uuid)) {
                    highLightEntities.remove(uuid);
                }
                if (uuid != null && !uuid.equals("")) {
                    ModelView.unHighlight(uuid);
                }
            }
        }
        return SUCCESS;
    }

    /**
     *  清除所有高亮的构件
     */
    public void clearHighStatus() {
        if (highLightEntities != null && highLightEntities.size() > 0) {
            for (String uuid : highLightEntities) {
                ModelView.unHighlight(uuid);
            }
            highLightEntities.clear();
        }
    }

    /**
     *  清除所有的状态
     *
     */
    public void clearAllStatus() {
        if (hideEntities != null && hideEntities.size() > 0) {
            for (String uuid : hideEntities) {
                ModelView.unHiddenEntity(uuid);
            }
            hideEntities.clear();
        }
        if (onlyShowEntities != null && onlyShowEntities.size() > 0) {
            ModelView.unHiddenOtherEntities(onlyShowEntities.get(0));
            ModelView.unHiddenEntity(onlyShowEntities.get(0));
            onlyShowEntities.clear();
        }
        if (highLightEntities != null && highLightEntities.size() > 0) {
            for (String uuid : highLightEntities) {
                ModelView.unHighlight(uuid);
            }
            highLightEntities.clear();
        }
        if (isTransparentAll) {
            this.unTransParentAll();
        }
        ModelView.initCameraMoveAndRotateCenterPos();
    }

    /**
     * @param arrayList
     * @param withHide
     */
    public void loadByFloor(ArrayList<String> arrayList, boolean withHide) {
        //获得当前所有的楼层
        List<ControlTree> floorDataList = getFloorsTreeRoot().getM_children();
        if (floorDataList != null && floorDataList.size() > 0) {
            ArrayList<String> floors = new ArrayList<>();
            //跟现在加载的楼层做对比，只加载还没有加载过的楼层
            int loadFloor = 0;
            for (ControlTree floorData : floorDataList) {
                if (floorData.isVisiable()) {
                    loadFloor++;
                }
                if (!floorData.isVisiable() && arrayList.contains(floorData.getM_name())) {
                    floors.add(floorData.getM_name());
                    floorData.setVisiable(true, true);
                }
            }
            if (floors != null && floors.size() > 0) {
                ModelView.loadByFloor(floors);
            }
            //如果当前没有加载过楼层
            if (loadFloor == 0) {
                ModelView.zoomRoot();
            }
        }
        if (withHide) {
            NodeControl.reductionToView();
        }
    }

    /**
     * 加载单个楼层
     *
     * @param floorName
     * @param withHide
     */
    public void loadByFloorName(String floorName, boolean withHide) {
        if (!TextUtils.isEmpty(floorName)) {
            ArrayList<String> floorNames = new ArrayList<>();
            floorNames.add(floorName);
            loadByFloor(floorNames, withHide);
        }
    }

    /**
     * 卸载楼层，需要对单例对象中的floorDataList中的元素设置visiable属性
     *
     * @param arrayList
     */
    public void unLoadByFloor(ArrayList<String> arrayList) {
        //获得当前所有的楼层
        List<ControlTree> floorDataList = getFloorsTreeRoot().getM_children();
        if (floorDataList != null && floorDataList.size() > 0) {
            ArrayList<String> floors = new ArrayList<>();
            for (ControlTree floorData : floorDataList) {
                if (floorData.isVisiable() && arrayList.contains(floorData.getM_name())) {
                    floors.add(floorData.getM_name());
                    floorData.setVisiable(false, true);
                }
            }
            if (floors != null && floors.size() > 0) {
                ModelView.unloadByFloor(floors);
            }
        }
    }

    /**
     * 卸载单个楼层
     *
     * @param floorName
     */
    public void unLoadByFloorName(String floorName) {
        if (!TextUtils.isEmpty(floorName)) {
            ArrayList<String> floorNames = new ArrayList<>();
            floorNames.add(floorName);
            unLoadByFloor(floorNames);
        }
    }

    /**
     *  定位模型
     * @param entityID
     */
    public boolean zoomToEntity(String entityID) {
        boolean result = ModelView.zoomToEntity(entityID);
        if (result) {
            this.transParentAll();
            this.ctrlHighList(entityID, true);
        }
        return result;
    }

    /**
     * 从楼层选择界面，加载返回的楼层，更现有已经加载的楼层数据做对比再加载
     * @param tree
     */
    public void loadFloorFromFloorTree(ControlTree tree) {
        ArrayList<ControlTree> tempFloors = tree.getM_children();
        List<ControlTree> floors = this.getFloorsTreeRoot().getM_children();
        ArrayList<String> unloadfloors = new ArrayList<>();
        ArrayList<String> addloadfloors = new ArrayList<>();
        if (tempFloors != null) {
            for (ControlTree floor : floors) {
                if (floor.isVisiable()) {
                    for (ControlTree tempfloor : tempFloors) {
                        if (!tempfloor.isVisiable() && tempfloor.getM_name().equals(floor.getM_name())) {
                            unloadfloors.add(tempfloor.getM_name());
                            break;
                        }
                    }
                }
            }
            for (ControlTree tempfloor : tempFloors) {
                if (tempfloor.isVisiable()) {
                    for (ControlTree floor : floors) {
                        if (!floor.isVisiable() && tempfloor.getM_name().equals(floor.getM_name())) {
                            addloadfloors.add(tempfloor.getM_name());
                            break;
                        }
                    }
                }
            }
            if (addloadfloors != null && addloadfloors.size() > 0) {
                this.loadByFloor(addloadfloors, true);
            }
            if (unloadfloors != null && unloadfloors.size() > 0) {
                this.unLoadByFloor(unloadfloors);
            }
        }
    }

    /**
     * 重新把内存里的楼层加载一遍
     */
    public void reloadFloorsFromThis() {
        List<ControlTree> floors = this.getFloorsTreeRoot().getM_children();
        if (floors != null && floors.size() > 0) {
            ArrayList<String> floorNames = new ArrayList<>();
            for (ControlTree controlTree : floors) {
                if (controlTree.isVisiable()) {
                    floorNames.add(controlTree.getM_name());
                }
            }
            if (floorNames != null && floorNames.size() > 0) {
//            this.loadByFloor(floorNames,true);
                /**
                 * 上面的方法会对比楼层，但是实际是没加载但是内存里显示加载。所以需要直接加载楼层，并做视口还原操作
                 */
                ModelView.loadByFloor(floorNames);
                ModelView.zoomRoot();
            }
        }
    }

    /**
     * 获取模型专业的树结构
     * @return
     */
    public synchronized ControlTree getDCTTreeRoot() {
        if (DCTTreeRoot == null)
            DCTTreeRoot = ModelView.getDCTTreeRoot();
        return DCTTreeRoot;
    }

    /**
     * 获取模型楼层的树结构
     * @return
     */
    public synchronized ControlTree getFloorsTreeRoot() {
        if (floorsTreeRoot == null) {
            floorsTreeRoot = ModelView.getFloorTreeRoot();
        }
        return floorsTreeRoot;
    }

    /**
     *  设置模型专业树，状态更改时设置
     * @param DCTTreeRoot
     */
    public void setDCTTreeRoot(ControlTree DCTTreeRoot) {
        this.DCTTreeRoot = DCTTreeRoot;
    }

    /**
     *  设置楼层专业树
     * @param floorsTreeRoot
     */
    public void setFloorsTreeRoot(ControlTree floorsTreeRoot) {
        floorsTreeRoot = floorsTreeRoot;
    }

    /**
     *  模型复位并且清除所有高亮等状态
     */
    public void onHome() {
        this.clearAllStatus();
        onHomeNoClearStatus();
    }

    /**
     * 模型复位不清除高亮状态
     */
    public void onHomeNoClearStatus(){
        ModelView.onHome();
        ModelView.setCanceling(true);
        ModelView.zoomRoot();
        ModelView.initCameraMoveAndRotateCenterPos();
    }


    /**
     *  初始化六面体
     * @param context
     */
    public void initSixCube(Context context) {
        AssetManager assetManager = context.getAssets();
        String[] files = null;
        String path = context.getFilesDir().getAbsolutePath() + File.separator + "SixCube/";
        File filepath = new File(path);
        if (!filepath.exists()) {
            filepath.mkdirs();
        }
        try {
            files = assetManager.list("SixCube");
            for (String file : files != null ? files : new String[0]) {
                InputStream in = null;
                in = assetManager.open("SixCube/" + file);
                String fpath = context.getFilesDir().getAbsolutePath() + File.separator + "SixCube/" + file;
                File newfile = new File(fpath);
                if (newfile.exists() && newfile.length() > 0) {
                    return;
                } else {
                    newfile.createNewFile();
                }
                OutputStream out = new FileOutputStream(fpath);
                byte[] buffer = new byte[65536];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }
                in.close();
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  创建六面体
     * @param context
     */
    public void createSixCube(Context context) {
        String path = context.getFilesDir().getAbsolutePath() + File.separator + "SixCube/";
        ModelView.createSixCube(path);
    }

    /**
     * 初始化字体
     * @param context
     */
    public void initFont(Context context,String fontName) {
        AssetManager assetManager = context.getAssets();
        InputStream in = null;
//        String fontName = "simhei.ttf";
        String fpath = context.getFilesDir().getAbsolutePath() + File.separator + fontName;
        try {
            in = assetManager.open(fontName);
            File newFile = new File(fpath);
            if (!newFile.exists()) {
                newFile.createNewFile();
            }
            OutputStream out = new FileOutputStream(fpath);
            byte[] buffer = new byte[65536];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ModelView.initAxisGridFontPath(fpath);
    }

    /**
     *
     * @param context
     * @param x
     * @param y
     * @param haveToolBar
     */
    public void setSixCubePosition(Context context, int x, int y, boolean haveToolBar) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.actionBarSize, typedValue, true);
        int toolbarHeight = (int) typedValue.getDimension(dm);
        int cubeWidth = (int) (x / 4.5f);
        int postionx = x - cubeWidth - 10;
        int postiony = y - cubeWidth - 10 - (haveToolBar ? toolbarHeight : toolbarHeight - 20);
        ModelView.setSixCubePosition(postionx, postiony, cubeWidth, cubeWidth);
    }

    /**
     *  初始化轴网
     */
    public void initAxisGrid() {
        ModelView.unLoadAxisGrid();
        ModelView.loadAxisGrid();
    }

    /**
     *  设置更新轴网高度
     * @param isChecked
     *
     *
     */
    public void updateAxisGridHeigh(boolean isChecked) {
        if (!isChecked) {
            ModelView.hideAxisGrid();
            return;
        }
        ControlTree floorTree = getFloorsTreeRoot();
        if (floorTree == null || floorTree.getM_children().size() == 0)
            return;
        List<ControlTree> floors = floorTree.getM_children();
        String firstFloorName = null;
        for (ControlTree floor : floors) {
            if (floor.isVisiable()) {
                firstFloorName = floor.getM_name();
                break;
            }
        }
        if (firstFloorName != null) {
            ModelView.unHideAxisGrid();
            ModelView.setAxisGridHeightByFloorName(firstFloorName);
        } else {
            ModelView.hideAxisGrid();
        }
    }

    /**
     *  设置固定帧率，使模型构件是否在操作时隐藏
     * @param isModelHide
     *          true:
     *              设置显影，隐藏构件
     *          false
     *              不设置显影，不隐藏构件
     *
     */
    public void updateModelHide(boolean isModelHide) {
        ModelView.setFrameIgnore(isModelHide);
    }

    /**
     *  控制
     * @param isShown
     * @param isShown
     */
    public void ctrlAxisGrid(boolean isShown) {
        this.updateAxisGridHeigh(isShown);
    }

    /**
     *
     * @param opint
     */
    public void updateModelOpacity(int opint) {
        updateOpacity(opint);
        if (isTransparentAll) {
            ModelView.transParentAllWithOpacity(opacity);
        }
        resetHighLightOpacity();
    }

    /**
     *
     */
    public void resetHighLightOpacity() {
        if (highLightEntities != null && highLightEntities.size() > 0) {
            for (String enitiy : highLightEntities) {
                ModelView.highlightWithOpacity(enitiy, opacity, isTransparentAll);
            }
        }
    }

    /**
     *
     */
    public void unTransParentAll() {
        isTransparentAll = false;
        ModelView.unTransParentAll();
    }

    /**
     *
     */
    public void transParentAll() {
        isTransparentAll = true;
        ModelView.transParentAllWithOpacity(opacity);
    }


}
