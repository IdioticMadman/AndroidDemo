package com.example;

import java.util.List;

/**
 * Description:
 * Created by xk on 16/12/20.20.34
 */

public class FormPreviewBean {


    /**
     * data : [{"key":"string","value":"string"},{"key":"string","value":"string"},{"key":"string","value":"string"}]
     * templateId : string
     */

    private String templateId;
    private List<DataBean> data;


    public FormPreviewBean(List<DataBean> data, String templateId) {
        this.data = data;
        this.templateId = templateId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }


    public static class DataBean {

        public DataBean(String key, String value) {
            this.key = key;
            this.value = value;
        }

        /**
         * key : string
         * value : string
         */

        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }


}
