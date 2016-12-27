package com.example;

/**
 * Created by robert on 2016/11/25.
 */

public class Project {

    /**
     * id : ac8e849a5b914653883cf38852aaa9f3
     * isNewRecord : false
     * createDate : 2016-11-24 17:56:07
     * updateDate : 2016-11-24 17:56:07
     * name : 阿诗丹顿撒
     * intro : 撒
     * version : 撒
     * createUserId : d9640b1d3966421da01952116a6356fd
     * createUser : admin
     * userId : d9640b1d3966421da01952116a6356fd
     * locationAddr : 啥的
     * modelList : [{"isNewRecord":true,"createDate":"2016-11-24 17:56:07","updateDate":"2016-11-24 17:56:07","modelId":"7","modelName":"apache-maven-3.3.9-bin","modelFileId":"665436d6c894424f8b3cf967ead4ea38","projectId":"ac8e849a5b914653883cf38852aaa9f3","modelThumbnailId":"e0c4f7e3a58042b89019b065b0965e6c","modelThumbnailFile":{"id":"e0c4f7e3a58042b89019b065b0965e6c","isNewRecord":false,"name":"温度检测.jpg","length":209427,"md5":"6ab8692636ce2d3ceed4cc8361ab3316","fileType":"jpg","filePath":"e0c4f7e3a58042b89019b065b0965e6c"}},{"isNewRecord":true,"createDate":"2016-11-24 17:56:07","updateDate":"2016-11-24 17:56:07","modelId":"12","modelName":"MxVPN.Client.2013-08-26_new","modelFileId":"77316e7a48e34fcfae5d85435fc8c864","projectId":"ac8e849a5b914653883cf38852aaa9f3","modelThumbnailId":"e0c4f7e3a58042b89019b065b0965e6c","modelThumbnailFile":{"id":"e0c4f7e3a58042b89019b065b0965e6c","isNewRecord":false,"name":"温度检测.jpg","length":209427,"md5":"6ab8692636ce2d3ceed4cc8361ab3316","fileType":"jpg","filePath":"e0c4f7e3a58042b89019b065b0965e6c"},"modelFile":{"id":"77316e7a48e34fcfae5d85435fc8c864","isNewRecord":false,"name":"ChromeStandalone_50.0.2661.87_Setup.1461306176.exe","length":46888864,"md5":"7d416d5a0e1ef5548513d34676925c28","fileType":"exe","filePath":"77316e7a48e34fcfae5d85435fc8c864","uploadDate":"2016-11-25 13:49:12","contentType":"application/x-msdownload"}},{"isNewRecord":true,"createDate":"2016-11-24 17:56:07","updateDate":"2016-11-24 17:56:07","modelId":"34","modelName":"jre-8u101-windows-x64.tar","modelFileId":"3878ebf125024099a19be3fbb8cc20f2","projectId":"ac8e849a5b914653883cf38852aaa9f3","modelThumbnailId":"e0c4f7e3a58042b89019b065b0965e6c","modelThumbnailFile":{"id":"e0c4f7e3a58042b89019b065b0965e6c","isNewRecord":false,"name":"温度检测.jpg","length":209427,"md5":"6ab8692636ce2d3ceed4cc8361ab3316","fileType":"jpg","filePath":"e0c4f7e3a58042b89019b065b0965e6c"}},{"isNewRecord":true,"createDate":"2016-11-24 17:56:07","updateDate":"2016-11-24 17:56:07","modelId":"35","modelName":"apache-tomcat-7.0.65","modelFileId":"bee5ff7303bc48aaa048d9328b6126ef","projectId":"ac8e849a5b914653883cf38852aaa9f3","modelThumbnailId":"e0c4f7e3a58042b89019b065b0965e6c","modelThumbnailFile":{"id":"e0c4f7e3a58042b89019b065b0965e6c","isNewRecord":false,"name":"温度检测.jpg","length":209427,"md5":"6ab8692636ce2d3ceed4cc8361ab3316","fileType":"jpg","filePath":"e0c4f7e3a58042b89019b065b0965e6c"}}]
     */

    private String id;
    private boolean isNewRecord;
    private String createDate;
    private String updateDate;
    private String name;
    private String intro;
    private String version;
    private String createUserId;
    private String createUser;
    private String userId;
    private String locationAddr;
//    private List<ModelListBean> modelList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIsNewRecord() {
        return isNewRecord;
    }

    public void setIsNewRecord(boolean isNewRecord) {
        this.isNewRecord = isNewRecord;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLocationAddr() {
        return locationAddr;
    }

    public void setLocationAddr(String locationAddr) {
        this.locationAddr = locationAddr;
    }

//    public List<ModelListBean> getModelList() {
//        return modelList;
//    }
//
//    public void setModelList(List<ModelListBean> modelList) {
//        this.modelList = modelList;
//    }

    public static class ModelListBean {
        /**
         * isNewRecord : true
         * createDate : 2016-11-24 17:56:07
         * updateDate : 2016-11-24 17:56:07
         * modelId : 7
         * modelName : apache-maven-3.3.9-bin
         * modelFileId : 665436d6c894424f8b3cf967ead4ea38
         * projectId : ac8e849a5b914653883cf38852aaa9f3
         * modelThumbnailId : e0c4f7e3a58042b89019b065b0965e6c
         * modelThumbnailFile : {"id":"e0c4f7e3a58042b89019b065b0965e6c","isNewRecord":false,"name":"温度检测.jpg","length":209427,"md5":"6ab8692636ce2d3ceed4cc8361ab3316","fileType":"jpg","filePath":"e0c4f7e3a58042b89019b065b0965e6c"}
         * modelFile : {"id":"77316e7a48e34fcfae5d85435fc8c864","isNewRecord":false,"name":"ChromeStandalone_50.0.2661.87_Setup.1461306176.exe","length":46888864,"md5":"7d416d5a0e1ef5548513d34676925c28","fileType":"exe","filePath":"77316e7a48e34fcfae5d85435fc8c864","uploadDate":"2016-11-25 13:49:12","contentType":"application/x-msdownload"}
         */

        private boolean isNewRecord;
        private String createDate;
        private String updateDate;
        private String modelId;
        private String modelName;
        private String modelFileId;
        private String projectId;
        private String modelThumbnailId;
        private ModelThumbnailFileBean modelThumbnailFile;
        private ModelFileBean modelFile;

        public boolean isIsNewRecord() {
            return isNewRecord;
        }

        public void setIsNewRecord(boolean isNewRecord) {
            this.isNewRecord = isNewRecord;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getModelId() {
            return modelId;
        }

        public void setModelId(String modelId) {
            this.modelId = modelId;
        }

        public String getModelName() {
            return modelName;
        }

        public void setModelName(String modelName) {
            this.modelName = modelName;
        }

        public String getModelFileId() {
            return modelFileId;
        }

        public void setModelFileId(String modelFileId) {
            this.modelFileId = modelFileId;
        }

        public String getProjectId() {
            return projectId;
        }

        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }

        public String getModelThumbnailId() {
            return modelThumbnailId;
        }

        public void setModelThumbnailId(String modelThumbnailId) {
            this.modelThumbnailId = modelThumbnailId;
        }

        public ModelThumbnailFileBean getModelThumbnailFile() {
            return modelThumbnailFile;
        }

        public void setModelThumbnailFile(ModelThumbnailFileBean modelThumbnailFile) {
            this.modelThumbnailFile = modelThumbnailFile;
        }

        public ModelFileBean getModelFile() {
            return modelFile;
        }

        public void setModelFile(ModelFileBean modelFile) {
            this.modelFile = modelFile;
        }

        public static class ModelThumbnailFileBean {
            /**
             * id : e0c4f7e3a58042b89019b065b0965e6c
             * isNewRecord : false
             * name : 温度检测.jpg
             * length : 209427
             * md5 : 6ab8692636ce2d3ceed4cc8361ab3316
             * fileType : jpg
             * filePath : e0c4f7e3a58042b89019b065b0965e6c
             */

            private String id;
            private boolean isNewRecord;
            private String name;
            private int length;
            private String md5;
            private String fileType;
            private String filePath;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public boolean isIsNewRecord() {
                return isNewRecord;
            }

            public void setIsNewRecord(boolean isNewRecord) {
                this.isNewRecord = isNewRecord;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getLength() {
                return length;
            }

            public void setLength(int length) {
                this.length = length;
            }

            public String getMd5() {
                return md5;
            }

            public void setMd5(String md5) {
                this.md5 = md5;
            }

            public String getFileType() {
                return fileType;
            }

            public void setFileType(String fileType) {
                this.fileType = fileType;
            }

            public String getFilePath() {
                return filePath;
            }

            public void setFilePath(String filePath) {
                this.filePath = filePath;
            }
        }

        public static class ModelFileBean {
            /**
             * id : 77316e7a48e34fcfae5d85435fc8c864
             * isNewRecord : false
             * name : ChromeStandalone_50.0.2661.87_Setup.1461306176.exe
             * length : 46888864
             * md5 : 7d416d5a0e1ef5548513d34676925c28
             * fileType : exe
             * filePath : 77316e7a48e34fcfae5d85435fc8c864
             * uploadDate : 2016-11-25 13:49:12
             * contentType : application/x-msdownload
             */

            private String id;
            private boolean isNewRecord;
            private String name;
            private int length;
            private String md5;
            private String fileType;
            private String filePath;
            private String uploadDate;
            private String contentType;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public boolean isIsNewRecord() {
                return isNewRecord;
            }

            public void setIsNewRecord(boolean isNewRecord) {
                this.isNewRecord = isNewRecord;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getLength() {
                return length;
            }

            public void setLength(int length) {
                this.length = length;
            }

            public String getMd5() {
                return md5;
            }

            public void setMd5(String md5) {
                this.md5 = md5;
            }

            public String getFileType() {
                return fileType;
            }

            public void setFileType(String fileType) {
                this.fileType = fileType;
            }

            public String getFilePath() {
                return filePath;
            }

            public void setFilePath(String filePath) {
                this.filePath = filePath;
            }

            public String getUploadDate() {
                return uploadDate;
            }

            public void setUploadDate(String uploadDate) {
                this.uploadDate = uploadDate;
            }

            public String getContentType() {
                return contentType;
            }

            public void setContentType(String contentType) {
                this.contentType = contentType;
            }
        }
    }

    @Override
    public String toString() {
        return "Project{" +
                "id='" + id + '\'' +
                ", isNewRecord=" + isNewRecord +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", name='" + name + '\'' +
                ", intro='" + intro + '\'' +
                ", version='" + version + '\'' +
                ", createUserId='" + createUserId + '\'' +
                ", createUser='" + createUser + '\'' +
                ", userId='" + userId + '\'' +
                ", locationAddr='" + locationAddr + '\'' +
//                ", modelList=" + modelList +
                '}';
    }
}
