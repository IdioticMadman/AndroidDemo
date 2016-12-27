package net.ezbim.modelview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModelView {

    public ModelView() {
    }

    static {
        System.loadLibrary("modelView");
        System.loadLibrary("gnustl_shared");
    }


//    public static native void startModelView();

    public static native void init(int width, int height);

    public static native ControlTree getFloorTreeRoot();

    public static native ControlTree getDCTTreeRoot();

    public static native boolean step();

    public static native void createSixCube(String path);

    public static native void setSixCubePosition(float x,float y,float width,float height);

    public static native void pickNavHudFace(float x,float y);

    public static native void syncSixCube();

    public static native int loadObject(String address);//

    public static native void loadObject(String address, String name);

    public static native void loadTest();

//    public static native void mouseButtonPressEvent(float x, float y, int button);
//
//    public static native void mouseButtonReleaseEvent(float x, float y, int button);

    public static native void mouseMoveEvent(float x, float y);

//    public static native void keyboardDown(int key);
//
//    public static native void keyboardUp(int key);

    public static native void updateSeveralTimes(int times);

    public static native void loadIVE(String address);

    public static native ArrayList<String> getFloorNames();

    public static native void zoomModel(float dx, float dy);

    public static native void zoomModelOLD(float scale, boolean isbool);

    public static native void onHome();

    public static native void onPanModel(double eventTimeDelta, float x, float y);

    public static native int preProcessBIMFile(String address, boolean byFloor);//true按层分

    public static native void startRendering();

    public static native void loadByFloor(ArrayList<String> arrayList);

    public static native void unloadByFloor(ArrayList<String> arrayList);

    public static native void loadByFloorName(String floorName);

    public static native void unLoadByFloorName(String floorName);

    public static native void unLoadAll();

    public static native void beginCamera();//开启Camera移动

    public static native void setFrameIgnore(boolean isIgnore);//设置Frame时是否延时绘制，在点击时使用不进行延时绘制。

    public static native void setCanceling(boolean isCanceling);

    public static native HashMap<String, ArrayList<String>> getViewportCameraView();//获得视口参数

    public static native void zoomToViewPortCenter(HashMap<String, ArrayList<String>> viewMap);//还原视口参数 v3.0.0

    public static native void zoomToViewPortsPosition(HashMap<String, ArrayList<String>> viewMap);//还原老的视口参数 v2.1.0

    public static native void setSkyBox(String imgPath);//设置天空盒

    public static native void updateSkyBox();

    public static native String clickSelection(double doubleX, double doubleY);//返回被选中的构件ID或者组ID或者为“”空字符（不是NULL）

    public static native void highlight(String entityID);//高亮指定ID的构件或者组

    public static native void unHighlight(String entityID);//取消指定ID的高亮

    public static native boolean needRenderNow();

//    public static native void exitModelView();//退出ModelView，清理ModelView在JNI的引用。如果需要重新调用，需要重新initModelView

    public static native void hiddenEntity(String entityId); //隐藏某个构件

    public static native void hiddenEntities(List<String> entityIds); //隐藏多个构件

    public static native void hiddenOtherEntities(String entityId);//只显示当前的构件，隐藏其他构件

    public static native void hiddenOtherEntitiesArray(ArrayList<String> entityIds);//只显示当前的构件，隐藏其他构件

    public static native void unHiddenEntity(String entityId);//取消隐藏某个构件

    public static native void unHiddenEntities(List<String> entityIds); //隐藏多个构件

    public static native void unHiddenOtherEntities(String entityId);//取消（只显示当前的构件，隐藏其他构件）的状态

    public static native void unHiddenOtherEntitiesArray(ArrayList<String> entityIds);//取消（只显示当前的构件，隐藏其他构件）的状态

    public static native void resizeView(int x, int y, int weight, int height);//不影响适口的清空下改变窗口大小

    public static native boolean zoomToEntity(String entityId);//定位到某个构件，其他构件处于透明状态。

    public static native void zoomToEntities(ArrayList<String> entities);//定位到一组构件，其他构件处于透明状态。

    public static native void transParentAll();//设置全部透明状态

    public static native void unTransParentAll();//取消全部透明状态

    public static native HashMap<String, HashMap<String, String>> getEntityInfo(String entityId);

    //KEY:baseInfo  value的key为floor,domain,category,name,revitId
    //KEY:其他属性，如果属性只是一个键值对，则该KEY跟其VALUE里的KEY键值一致。
    public static native ArrayList<String> getDomainNames();//第一层大类

    public static native ArrayList<String> getCategoryNames(String domain);//第二层构件种类

    public static native ArrayList<String> getEntityTypeNames(String domain, String category);//第三层构件类型

    // child == true  自上而下设置该节点以及包含的内容全部隐藏或显示。
    // child == false 自下而上设置，在该节点对应的子节点执行了全部隐藏的操作，反馈到这些子节点的父节点时，要设置父节点隐藏或显示。
    public static native void modelSHDomain(String domain, boolean hide, boolean child);

    public static native void modelSHCategory(String categoryName, String domainName, boolean hide, boolean child);

    public static native void modelSHEntityType(String entityTypeName, String categoryName, String domainName, boolean hide, boolean child);

    public static native void modelSHCategories(List<String> categoryNames, String domainName, boolean hide, boolean child);

    public static native void modelSHEntityTypes(List<String> entityTypeNames, String categoryName, String domainName, boolean hide, boolean child);

    public static native byte[] glToUIImage();

    public static native void setToTransparent(String uuid);//设置单个透明

    public static native void setStateColorToConstructs(String uuid, int red, int greed, int blue);//设置颜色

    public static native void revertStateColorOfConstructs(String uuid);//恢复颜色

    public static native boolean getComponentByFloorName(ArrayList<String> floors);//根据楼层查看该楼层下是否有构件

    public static native void zoomRoot();//第一次加载后需要调用此接口，复位视口。

    public static native int getComponentsCount();//获取内存中的构件数量

    public static native void hideComponens(int hideLevel);//取值范围为1-10 越大减少的越小。在滑动前只执行一次。

    public static native double getScalValue();

    //getPropertyGroupNames
    public static native ArrayList<String> getPropertyGroupNames();//获得项目下所有的属性组的名字

    public static native void setCameraMoveAndRotateCenterPos(float x, float y);//设置旋转中心

    public static native void initCameraMoveAndRotateCenterPos();//取消旋转中心

    public static native void rotationModelViewPos(float x, float y);//按照中心旋转

    public static native void panModelViewPos(float x, float y);//按照中心点平移

    public static native void zoomModelViewPos(float scale);//按照中心点缩放
}
