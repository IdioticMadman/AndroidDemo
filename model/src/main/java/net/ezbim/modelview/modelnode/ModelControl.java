package net.ezbim.modelview.modelnode;


import android.content.Context;
import android.content.res.AssetManager;
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

/**
 * Created by xk on 16/3/24.
 */
public class ModelControl {

    private static ModelControl modelControl;

    private ModelControl() {

    }

    public static ModelControl getModelControl() {
        if (modelControl == null) {
            synchronized (ModelControl.class) {
                if (modelControl == null) {
                    modelControl = new ModelControl();
                }
            }
        }
        return modelControl;
    }

    public static void resetModelControl() {
        modelControl = new ModelControl();
    }

    public static final int FAILED = 0;
    public static final int SUCCESS = 1;
    public static final int NOHAVE = 2;
    public static final int ALREADYHAVE = 3;

    //    private List<NodeSystem> nodeSystemList;//构件显影list
    private ControlTree DCTTreeRoot;

    private ControlTree FloorsTreeRoot;
    private List<String> hideEntities;//隐藏的构件list
    private List<String> onlyShowEntities;//只显的构件list
    private List<String> highEntities;//高亮的构件list

    public List<String> getHideEntities() {
        if (hideEntities == null) {
            hideEntities = new ArrayList<>();
        }
        return hideEntities;
    }

    public List<String> getOnlyShowEntities() {
        if (onlyShowEntities == null) {
            onlyShowEntities = new ArrayList<>();
        }
        return onlyShowEntities;
    }

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
        if (highEntities != null && highEntities.size() > 0) {
            for (String uuid : highEntities) {
                ModelView.highlight(uuid);
            }
        }
        ModelView.updateSeveralTimes(6);
    }

    public int ctrlHideList(String uuid, boolean isadd) {
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
                ModelView.updateSeveralTimes(6);
                hideEntities.remove(uuid);
                return SUCCESS;
            }
        }
    }

    public void ctrlOnlyShowList(String uuid) {
        onlyShowEntities = new ArrayList<>();
        onlyShowEntities.add(uuid);
        ModelView.hiddenOtherEntities(uuid);
    }

    public void ctrlOnlyShowList(ArrayList<String> uuids) {
        onlyShowEntities = uuids;
        ModelView.hiddenOtherEntities(uuids.get(0));
        ModelView.unHiddenEntities(uuids);
//        ModelView.hiddenOtherEntitiesArray(uuids);
//        if (onlyShowEntities == null) {
//            onlyShowEntities = new ArrayList<>();
//        }
//        if (ismore) {
////            if (onlyShowEntities.contains(uuid)) {
////                for (String enitiy : onlyShowEntities) {
////                    ModelView.unHiddenEntity(enitiy);
////                }
////                return ALREADYHAVE;
////            } else {
////                ModelView.hiddenOtherEntities(uuid);
////                for (String enitiy : onlyShowEntities) {
////                    ModelView.unHiddenEntity(enitiy);
////                }
////                ModelView.updateSeveralTimes(3);
////                onlyShowEntities.add(uuid);
////                return SUCCESS;
////            }
//            if (isadd) {
//                ModelView.hiddenOtherEntities(uuid);
//                if (!onlyShowEntities.isEmpty()) {
//                    for (String entity : onlyShowEntities) {
//                        ModelView.unHiddenEntity(entity);
//                    }
//                }
//                if (!onlyShowEntities.contains(uuid)) {
//                    onlyShowEntities.add(uuid);
//                }
//            } else {
//                if (!onlyShowEntities.isEmpty()) {
//                    if (!onlyShowEntities.contains(uuid)) {
//                        ModelView.unHiddenEntity(uuid);
//                    }
//                }
//
//            }
//            return SUCCESS;
//        } else {
////            if (!onlyShowEntities.contains(uuid)) {
////                onlyShowEntities.clear();
////                ModelView.hiddenOtherEntities(uuid);
////                onlyShowEntities.add(uuid);
////                return NOHAVE;
////            } else {
////                ModelView.unHiddenOtherEntities(uuid);
////                ModelView.updateSeveralTimes(3);
////                onlyShowEntities.clear();
////                onlyShowEntities.add(uuid);
////                return SUCCESS;
////            }
//            onlyShowEntities.clear();
//            ModelView.hiddenOtherEntities(uuid);
//            onlyShowEntities.add(uuid);
//            return SUCCESS;
//        }

    }

    public int ctrlHighList(String uuid, boolean isadd) {
        if (highEntities == null) {
            highEntities = new ArrayList<>();
        }
        if (isadd) {
            if (!highEntities.contains(uuid)) {
                highEntities.add(uuid);
            }
            if (uuid != null && !uuid.equals("")) {
                ModelView.highlight(uuid);
                ModelView.updateSeveralTimes(6);
            }
            return SUCCESS;
        } else {
            if (highEntities.contains(uuid)) {
                highEntities.remove(uuid);
            }
            if (uuid != null && !uuid.equals("")) {
                ModelView.unHighlight(uuid);
                ModelView.updateSeveralTimes(6);
            }
            return SUCCESS;
        }
    }

    public int ctrlHighList(List<String> uuids, boolean isadd) {
        if (highEntities == null) {
            highEntities = new ArrayList<>();
        }
        if (isadd) {
            for (String uuid : uuids) {
                if (!highEntities.contains(uuid)) {
                    highEntities.add(uuid);
                    ModelView.highlight(uuid);
                }
            }
            ModelView.updateSeveralTimes(6);
        } else {
            for (String uuid : uuids) {
                if (highEntities.contains(uuid)) {
                    highEntities.remove(uuid);
                    ModelView.unHighlight(uuid);
                }
            }
            ModelView.updateSeveralTimes(6);
        }
        return SUCCESS;
    }

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
//            for (String uuid : onlyShowEntities) {
//                ModelView.unHiddenOtherEntities(uuid);
//            }
            onlyShowEntities.clear();
        }
        if (highEntities != null && highEntities.size() > 0) {
            for (String uuid : highEntities) {
                ModelView.unHighlight(uuid);
            }
            highEntities.clear();
        }
        ModelView.initCameraMoveAndRotateCenterPos();
        ModelView.updateSeveralTimes(6);
    }

    public void loadByFloor(ArrayList<String> arrayList, boolean withHide) {
//        boolean needRoot = true;
        List<ControlTree> floorDataList = getFloorsTreeRoot().getM_children();
        if (floorDataList != null && floorDataList.size() > 0) {
            int floorCount = 0;
            for (ControlTree floorData : floorDataList) {
                if (floorData.isVisiable()) {
                    floorCount++;
                }
            }
            if (floorCount == 0) {
//                needRoot = false;
            } else {
                ArrayList<String> floors = new ArrayList<>();
                for (ControlTree floorData : floorDataList) {
                    if (floorData.isVisiable()) {
                        floors.add(floorData.getM_name());
                    }
                }
//                needRoot = ModelView.getComponentByFloorName(floors);
            }
            ModelView.loadByFloor(arrayList);
//            ebimView.setFloorList(arrayList);
//            if (!needRoot) {
//                ModelView.zoomRoot();
//                ModelView.mouseMoveEvent(-0.785f, -0.785f);
//            }
        } else {
            ModelView.loadByFloor(arrayList);
            ModelView.zoomRoot();
        }
        if (withHide) {
            NodeControl.reductionToView();
        }
        ModelView.updateSeveralTimes(6);
    }

    public void loadByFloorName(String floorName) {
//        boolean needRoot = true;
        List<ControlTree> floorDataList = getFloorsTreeRoot().getM_children();
        if (floorDataList != null && floorDataList.size() > 0) {
            int floorCount = 0;
            for (ControlTree floorData : floorDataList) {
                if (floorData.isVisiable()) {
                    floorCount++;
                }
            }
            if (floorCount == 0) {
//                needRoot = false;
            } else {
                ArrayList<String> floors = new ArrayList<>();
                for (ControlTree floorData : floorDataList) {
                    if (floorData.isVisiable()) {
                        floors.add(floorData.getM_name());
                    }
                }
//                needRoot = ModelView.getComponentByFloorName(floors);
            }
            ModelView.loadByFloorName(floorName);
//            if (!needRoot) {
//                ModelView.zoomRoot();
//                ModelView.mouseMoveEvent(-0.785f, -0.785f);
//            }
        } else {
            ModelView.loadByFloorName(floorName);
            ModelView.zoomRoot();
        }
    }


    public synchronized ControlTree getDCTTreeRoot() {
        if (DCTTreeRoot == null)
            DCTTreeRoot = ModelView.getDCTTreeRoot();
        return DCTTreeRoot;
    }

    public synchronized ControlTree getFloorsTreeRoot() {
        if (FloorsTreeRoot == null)
            FloorsTreeRoot = ModelView.getFloorTreeRoot();
        return FloorsTreeRoot;
    }

    public void setDCTTreeRoot(ControlTree DCTTreeRoot) {
        this.DCTTreeRoot = DCTTreeRoot;
    }

    public void setFloorsTreeRoot(ControlTree floorsTreeRoot) {
        FloorsTreeRoot = floorsTreeRoot;
    }

    public void onHome() {
        this.clearAllStatus();
        ModelView.onHome();
        ModelView.zoomRoot();
        ModelView.initCameraMoveAndRotateCenterPos();
    }

    public void initSixCube(Context context) {
        AssetManager assetManager = context.getAssets();
        String[] files = null;
        String path = context.getFilesDir().getAbsolutePath() + File.separator + "SixCube/";
        File filepath = new File(path);
//        if (filepath.exists() && filepath.isDirectory() && filepath.listFiles().length > 0) {
//            return;
//        }
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
                if (!newfile.exists()) {
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

    public void createSixCube(Context context) {
        String path = context.getFilesDir().getAbsolutePath() + File.separator + "SixCube/";
        File filepath = new File(path);
//        if (filepath.exists() && filepath.isDirectory() && filepath.listFiles().length > 0) {
        ModelView.createSixCube(path);
//        }
    }

    public void setSixCubePosition(Context context, int x, int y, boolean haveToolBar) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
//        int statusHeight = (int) Math.ceil(25 * dm.density);
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.actionBarSize, typedValue, true);
        int toolbarHeight = (int) typedValue.getDimension(dm);
        int cubeWidth = (int) (x / 4.5f);
        int postionx = x - cubeWidth - 10;
        int postiony = y - cubeWidth - 10 - (haveToolBar ? toolbarHeight : toolbarHeight - 20);
        ModelView.setSixCubePosition(postionx, postiony, cubeWidth, cubeWidth);
    }

}
