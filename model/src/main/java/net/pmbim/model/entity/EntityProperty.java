package net.pmbim.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 描述：
 *
 * @author hdk
 * @version 1.00
 * @created 2015年7月23日 下午2:03:52
 */
public class EntityProperty implements Parcelable {

    /**
     *
     */
    private String PGroupName;
    private String propertiesKey;
    private String propertiesValue;


    public String getPGroupName() {
        return PGroupName;
    }

    public void setPGroupName(String pGroupName) {
        PGroupName = pGroupName;
    }

    public String getPropertiesKey() {
        return propertiesKey;
    }

    public void setPropertiesKey(String propertiesKey) {
        this.propertiesKey = propertiesKey;
    }

    public String getPropertiesValue() {
        return propertiesValue;
    }

    public void setPropertiesValue(String propertiesValue) {
        this.propertiesValue = propertiesValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(propertiesKey);
        dest.writeString(propertiesValue);
        dest.writeString(PGroupName);
    }

    public EntityProperty(Parcel source) {
        this.propertiesKey = source.readString();
        this.propertiesValue = source.readString();
        this.PGroupName = source.readString();
    }

    public static final Creator<EntityProperty> CREATOR = new Creator<EntityProperty>() {

        @Override
        public EntityProperty createFromParcel(Parcel source) {
            return new EntityProperty(source);
        }

        @Override
        public EntityProperty[] newArray(int size) {
            return new EntityProperty[size];
        }
    };

    public EntityProperty() {
    }

    public EntityProperty(String propertiesKey, String propertiesValue,
                          String PGroupName) {
        super();
        this.propertiesKey = propertiesKey;
        this.propertiesValue = propertiesValue;
        this.PGroupName = PGroupName;
    }

    @Override
    public String toString() {
        return "EntityProperty [propertiesKey=" + propertiesKey
                + ", propertiesValue=" + propertiesValue + "]";
    }

}
