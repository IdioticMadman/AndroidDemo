package net.ezbim.modelview.modelnode;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;

import net.ezbim.modelview.ControlTree;
import net.ezbim.modelview.ModelView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xk on 16/3/15.
 */
public class NodeControl {

    /**
     * 上传视口的对象
     *
     * @param IsVisible 单独选择的构件是否显影
     * @param guids     单独选择的构件列表
     * @return 上传的视口对象
     */
    public static UploadViewPort getUploadJson(boolean IsVisible, List<String> guids) {
        ControlTree DCTTreeRoot = ModelControl.getInstance().getDCTTreeRoot();
        if (!DCTTreeRoot.getM_name().equals("root")) {
            return null;
        }
        List<ControlTree> domainList = DCTTreeRoot.getM_children();
        List<NodeSystem> oneNameList = new ArrayList<>();
        for (int i = 0; i < domainList.size(); i++) {
            ControlTree oneChild = domainList.get(i);
            if (oneChild.isVisiable()) {
                List<ControlTree> categoryList = oneChild.getM_children();
                List<NodeCategory> categories = new ArrayList<>();
                for (int j = 0; j < categoryList.size(); j++) {
                    ControlTree secondChild = categoryList.get(j);
                    if (secondChild.isVisiable()) {
                        List<ControlTree> typeList = secondChild.getM_children();
                        List<NodeEntity> entities = new ArrayList<>();
                        int thirdInt = 0;
                        for (int k = 0; k < typeList.size(); k++) {
                            ControlTree thirdChild = typeList.get(k);
                            if (thirdChild.isVisiable()) {
                                thirdInt++;
                                entities.add(new NodeEntity(thirdChild.getM_name(), thirdChild.isVisiable()));
                            }
                        }
                        if (thirdInt == typeList.size()) {
                            entities.clear();
                        }
                        categories.add(new NodeCategory(secondChild.getM_name(), secondChild.isVisiable(), entities));
                    }
                }
                NodeSystem uploadView = new NodeSystem(oneChild.getM_name(), oneChild.isVisiable(), categories);
                oneNameList.add(uploadView);
            }
        }
        ControlTree FloorsTreeRoot = ModelControl.getInstance().getFloorsTreeRoot();
        if (!FloorsTreeRoot.getM_name().equals("root"))
            return null;
        List<ControlTree> FloorsList = FloorsTreeRoot.getM_children();
        List<String> floorList = new ArrayList<>();
        for (ControlTree floor : FloorsList) {
            if (floor.isVisiable()) {
                floorList.add(floor.getM_name());
            }
        }
        UploadViewPort uploadViewPort = new UploadViewPort(floorList, oneNameList, IsVisible, guids);
        return uploadViewPort;
    }

    //设置构件显隐

    public static void setShowByName(NodeSystem rootSystem, NodeCategory nodeCategory, NodeEntity nodeEntity, String pointName, int pointLevel, boolean bvalue, boolean allchild) {
        if (pointLevel == 0) {
            String sname = rootSystem.getSystem();
            if (sname.equals(pointName)) {
                rootSystem.setShowing(bvalue);
                if (allchild) {
                    List<NodeCategory> categories = rootSystem.getCategories();
                    if (categories != null && categories.size() > 0) {
                        for (int i = 0; i < categories.size(); i++) {
                            categories.get(i).setShowing(bvalue);
                            List<NodeEntity> entities = categories.get(i).getEntities();
                            if (entities != null && entities.size() > 0) {
                                for (int j = 0; j < entities.size(); j++) {
                                    entities.get(j).setShowing(bvalue);
                                }
                            }
                        }
                    }
                }
            }
        } else if (pointLevel == 1) {
            String cname = nodeCategory.getName();
            if (cname.equals(pointName)) {
                nodeCategory.setShowing(bvalue);
            }
            if (allchild) {
                List<NodeEntity> entities = nodeCategory.getEntities();
                if (entities != null && entities.size() > 0) {
                    for (int j = 0; j < entities.size(); j++) {
                        entities.get(j).setShowing(bvalue);
                    }
                }
            }
        } else if (pointLevel == 2) {
            String ename = nodeEntity.getName();
            if (ename.equals(pointName)) {
                nodeEntity.setShowing(bvalue);
            }
        }
    }

    //视口参数还原
    public static void viewProtToRootTree(String veString) {
        if (TextUtils.isEmpty(veString)) {
            return;
        }
        if (veString.contains("\"IsVisible\":\"0\"")) {
            veString = veString.replace("\"IsVisible\":\"0\"", "\"IsVisible\":false");
        } else if (veString.contains("\"IsVisible\":\"1\"")) {
            veString = veString.replace("\"IsVisible\":\"1\"", "\"IsVisible\":true");
        }
        UploadViewPort uploadViewPort = new Gson().fromJson(veString, UploadViewPort.class);
        List<String> visibleFloors = uploadViewPort.getVisiblefloor();
        //显示要显示的楼层跟构件，如果没有楼层则全部隐藏，跟现有加载楼层做下对比做加载跟卸载
        ArrayList<String> toLoadFloors = new ArrayList<>();
        ArrayList<String> toUnloadFloors = new ArrayList<>();
        ControlTree FloorsTreeRoot = ModelControl.getInstance().getFloorsTreeRoot();
        if (!FloorsTreeRoot.getM_name().equals("root"))
            return;
        List<ControlTree> FloorsList = FloorsTreeRoot.getM_children();

        if (visibleFloors != null && visibleFloors.size() > 0) {
            for (ControlTree tree : FloorsList) {
                int count = 0;
                for (String visiableFloor : visibleFloors) {
                    if (tree.getM_name().equals(visiableFloor)) {
                        if (!tree.isVisiable()) {
                            toLoadFloors.add(tree.getM_name());
//                            tree.setVisiable(!tree.isVisiable(), false);
                            break;
                        }
                    } else {
                        count++;
                    }
                }
                if (count == visibleFloors.size()) {
                    toUnloadFloors.add(tree.getM_name());
//                    tree.setVisiable(!tree.isVisiable(), false);
                }
            }
            if (toLoadFloors.size() > 0) {
                ModelView.loadByFloor(toLoadFloors);
                for (String floor : toLoadFloors) {
                    for (ControlTree tree : FloorsList) {
                        if (tree.getM_name().equals(floor)) {
                            tree.setVisiable(true, false);
                        }
                    }
                }
            }
            if (toUnloadFloors.size() > 0) {
                ModelView.unloadByFloor(toUnloadFloors);
                for (String floor : toUnloadFloors) {
                    for (ControlTree tree : FloorsList) {
                        if (tree.getM_name().equals(floor)) {
                            tree.setVisiable(false, false);
                        }
                    }
                }
            }
            //todo 显隐控制树还原
            List<NodeSystem> systemList = uploadViewPort.getVisibleEntities();
            ControlTree DCTTreeRoot = ModelControl.getInstance().getDCTTreeRoot();
            if (!DCTTreeRoot.getM_name().equals("root")) {
                return;
            }
            List<ControlTree> systemNowList = DCTTreeRoot.getM_children();
            for (ControlTree systemNowNode : systemNowList) {
                int systemcount = 0;
                for (NodeSystem systemVNode : systemList) {
                    if (!systemNowNode.getM_name().equals(systemVNode.getSystem())) {
                        systemcount++;
                        continue;
                    }
                    List<ControlTree> categories = systemNowNode.getM_children();
                    List<NodeCategory> cateVNodeList = systemVNode.getCategories();
                    for (ControlTree cate : categories) {
                        int catecount = 0;
                        for (NodeCategory cateVNode : cateVNodeList) {
                            if (!cateVNode.getName().equals(cate.getM_name())) {
                                catecount++;
                                continue;
                            }
                            List<ControlTree> EntityTypes = cate.getM_children();
                            List<NodeEntity> typeVNodeList = cateVNode.getEntities();
                            if (typeVNodeList.size() == 0) {
//                                for (ControlTree Nowtype : EntityTypes) {
//                                    ModelView.modelSHEntityType(Nowtype.getM_name(), cate.getM_name(), systemNowNode.getM_name(), true, false);
//                                    BLog.e("NodeControl", "system:" + systemNowNode.getM_name() + "|" + "cate:" + cate.getM_name() + "|" + "type:" + Nowtype.getM_name() +"|"+ String.valueOf(true));
//                                    Nowtype.setVisiable(true, true);
//                                }
                                ModelView.modelSHCategory(cate.getM_name(), systemNowNode.getM_name(), true, false);
                                cate.setVisiable(true, true);
                                break;
                            } else {
                                for (ControlTree NowType : EntityTypes) {
                                    int typecount = 0;
                                    for (NodeEntity Vtype : typeVNodeList) {
                                        if (NowType.getM_name().equals(Vtype.getName())) {
                                            ModelView.modelSHEntityType(NowType.getM_name(), cate.getM_name(), systemNowNode.getM_name(), true, false);
                                            NowType.setVisiable(true, true);
                                        } else {
                                            typecount++;
                                        }
                                    }
                                    if (typecount == typeVNodeList.size()) {
                                        ModelView.modelSHEntityType(NowType.getM_name(), cate.getM_name(), systemNowNode.getM_name(), false, false);
                                        NowType.setVisiable(false, true);
                                    }
                                }
                            }
                        }
                        if (catecount == cateVNodeList.size()) {
                            ModelView.modelSHCategory(cate.getM_name(), systemNowNode.getM_name(), false, false);
                            cate.setVisiable(false, true);
                        }

                    }

                }
                if (systemcount == systemList.size()) {
                    ModelView.modelSHDomain(systemNowNode.getM_name(), false, false);
                    systemNowNode.setVisiable(false, true);
                }
            }
            ArrayList<String> guids = new ArrayList<>();
            guids.addAll(uploadViewPort.getGuids());
            if (guids.size() > 0) {
                if (!uploadViewPort.isVisible()) {
                    for (int o = 0; o < guids.size(); o++) {
//                        ModelView.hiddenEntity(guids.get(o));
                        ModelControl.getInstance().ctrlHideList(guids.get(o), true);
                    }
                } else {
                    ModelControl.getInstance().ctrlOnlyShowList(guids.get(0),true);
                }
            }

        } else {
            //还原参数中没有任何楼层信息，则卸载当前全部楼层
            ArrayList<String> nowfloors = new ArrayList<>();
            for (ControlTree floor : FloorsTreeRoot.getM_children()) {
                if (floor.isVisiable()) {
                    nowfloors.add(floor.getM_name());
                }
            }
            if (nowfloors.size() > 0) {
                ModelView.unloadByFloor(nowfloors);
            }
        }
        ModelControl.getInstance().unTransParentAll();
    }

    public static void reductionToView() {
        //先获得视口对象
        List<ControlTree> nodeSystemList = ModelControl.getInstance().getDCTTreeRoot().getM_children();
        if (nodeSystemList != null && nodeSystemList.size() > 0) {
            //遍历对view进行设置
            for (int i = 0; i < nodeSystemList.size(); i++) {
                ControlTree oneChild = nodeSystemList.get(i);
                String systemName = oneChild.getM_name();
                if (oneChild.isVisiable()) {
                    List<ControlTree> secondList = oneChild.getM_children();
                    for (int j = 0; j < secondList.size(); j++) {
                        ControlTree secondChild = secondList.get(j);
                        String nodeCategoryName = secondChild.getM_name();
                        if (secondChild.isVisiable()) {
                            List<ControlTree> thirdList = secondChild.getM_children();
                            for (int k = 0; k < thirdList.size(); k++) {
                                ControlTree thirdChild = thirdList.get(k);
                                if (!thirdChild.isVisiable()) {
                                    ModelView.modelSHEntityType(thirdChild.getM_name(), nodeCategoryName, systemName, false, false);
                                }
                            }
                        } else {
                            ModelView.modelSHCategory(nodeCategoryName, systemName, false, true);
                        }
                    }
                } else {
                    ModelView.modelSHDomain(systemName, false, true);
                }
            }
        }
    }

}
