package com.fit2cloud.support.common.constants;

/**
 * Author: caixiaoqiu1987
 * Date: 2019/9/26  下午12:45
 * Description:
 */
public interface UserConstants {

    public String getValue();

    public enum Source implements UserConstants {

        LOCAL("local"), LDAP("ldap"), EXTRA("extra"), API("API");

        private String value;

        Source(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public enum Status implements UserConstants {

        ACTIVE("active"), DISABLED("disabled");

        private String value;

        Status(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
