package com.fit2cloud.support.common.constants;

public enum RepositoryConst {
    OSS, S3, LOCATION;

    public enum Attribute {
        OSS_ACCESS_KEY("oss.accessKey"),
        OSS_SECRET_KEY("oss.secretKey"),
        OSS_BUCKET_NAME("oss.bucketName"),
        OSS_LOCATION("oss.location"),
        BUCKET_TICKET_ATTACHMENT("bucketTicketAttachment"),

        S3_ACCESS_KEY("s3.accessKey"),
        S3_SECRET_KEY("s3.secretKey"),
        S3_BUCKET_NAME("s3.bucketName"),
        S3_LOCATION("s3.location");

        private String value;

        Attribute(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }
    }

    public enum ModuleType {
        BILLING,INVOICE,TICKET,USER
    }


}
