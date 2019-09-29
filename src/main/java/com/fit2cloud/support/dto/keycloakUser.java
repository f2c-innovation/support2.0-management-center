package com.fit2cloud.support.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class keycloakUser {


    /**
     * id : f:898bae7b-fae2-4caf-b908-bc5cb27868ea:admin@fit2cloud.com
     * origin : 898bae7b-fae2-4caf-b908-bc5cb27868ea
     * username : admin@fit2cloud.com
     * enabled : true
     * totp : false
     * emailVerified : false
     * email : admin@fit2cloud.com
     * attributes : {"phone":[null]}
     * disableableCredentialTypes : []
     * requiredActions : []
     * notBefore : 0
     * access : {"manageGroupMembership":true,"view":true,"mapRoles":true,"impersonate":true,"manage":true}
     */

    @JsonProperty("id")
    private String id;
    @JsonProperty("origin")
    private String origin;
    @JsonProperty("username")
    private String username;
    @JsonProperty("enabled")
    private boolean enabled;
    @JsonProperty("totp")
    private boolean totp;
    @JsonProperty("emailVerified")
    private boolean emailVerified;
    @JsonProperty("email")
    private String email;
    @JsonProperty("attributes")
    private AttributesBean attributes;
    @JsonProperty("notBefore")
    private int notBefore;
    @JsonProperty("access")
    private AccessBean access;
    @JsonProperty("requiredActions")
    private List<String> requiredActions;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isTotp() {
        return totp;
    }

    public void setTotp(boolean totp) {
        this.totp = totp;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AttributesBean getAttributes() {
        return attributes;
    }

    public void setAttributes(AttributesBean attributes) {
        this.attributes = attributes;
    }

    public int getNotBefore() {
        return notBefore;
    }

    public void setNotBefore(int notBefore) {
        this.notBefore = notBefore;
    }

    public AccessBean getAccess() {
        return access;
    }

    public void setAccess(AccessBean access) {
        this.access = access;
    }


    public List<?> getRequiredActions() {
        return requiredActions;
    }

    public void setRequiredActions(List<String> requiredActions) {
        this.requiredActions = requiredActions;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AttributesBean {
        @JsonProperty("phone")
        private List<String> phone;
        @JsonProperty("uid")
        private String uid;
        @JsonProperty("displayName")
        private String displayName;
        @JsonProperty("deptShortName")
        private String deptShortName;

        public String getDeptShortName() {
            return deptShortName;
        }

        public void setDeptShortName(String deptShortName) {
            this.deptShortName = deptShortName;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public List<String> getPhone() {
            return phone;
        }

        public void setPhone(List<String> phone) {
            this.phone = phone;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AccessBean {
        /**
         * manageGroupMembership : true
         * view : true
         * mapRoles : true
         * impersonate : true
         * manage : true
         */

        @JsonProperty("manageGroupMembership")
        private boolean manageGroupMembership;
        @JsonProperty("view")
        private boolean view;
        @JsonProperty("mapRoles")
        private boolean mapRoles;
        @JsonProperty("impersonate")
        private boolean impersonate;
        @JsonProperty("manage")
        private boolean manage;

        public boolean isManageGroupMembership() {
            return manageGroupMembership;
        }

        public void setManageGroupMembership(boolean manageGroupMembership) {
            this.manageGroupMembership = manageGroupMembership;
        }

        public boolean isView() {
            return view;
        }

        public void setView(boolean view) {
            this.view = view;
        }

        public boolean isMapRoles() {
            return mapRoles;
        }

        public void setMapRoles(boolean mapRoles) {
            this.mapRoles = mapRoles;
        }

        public boolean isImpersonate() {
            return impersonate;
        }

        public void setImpersonate(boolean impersonate) {
            this.impersonate = impersonate;
        }

        public boolean isManage() {
            return manage;
        }

        public void setManage(boolean manage) {
            this.manage = manage;
        }
    }
}
