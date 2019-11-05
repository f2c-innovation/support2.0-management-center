package com.fit2cloud.support.common.constants;

public class PermissionConstants {


    //start USER相关权限
    public static final String USER_READ = "USER:READ";
    public static final String USER_CREATE = "USER:READ+CREATE";
    public static final String USER_EDIT = "USER:READ+EDIT";
    public static final String USER_ADD_ROLE = "USER:READ+ADD_ROLE";
    public static final String USER_DELETE = "USER:READ+DELETE";
    public static final String USER_RESET_PASSWORD = "USER:READ+RESET_PASSWORD";
    public static final String USER_DISABLED = "USER:READ+DISABLED";
    public static final String USER_IMPORT = "USER:READ+IMPORT";
    public static final String USER_KEY_READ = "USER:READ+USER_KEY:READ";
    public static final String USER_KEY_EDIT = "USER:READ+USER_KEY:READ+EDIT";
    //end  user相关权限

    //start role相关权限
    public static final String ROLE_READ = "ROLE:READ";
    public static final String ROLE_CREATE = "ROLE:READ+CREATE";
    public static final String ROLE_EDIT = "ROLE:READ+EDIT";
    public static final String ROLE_DELETE = "ROLE:READ+DELETE";
    //end  role相关权限

    //start dept相关权限
    public static final String DEPARTMENT_READ = "DEPARTMENT:READ";
    public static final String DEPARTMENT_CREATE = "DEPARTMENT:READ+CREATE";
    public static final String DEPARTMENT_EDIT = "DEPARTMENT:READ+EDIT";
    public static final String DEPARTMENT_DELETE = "DEPARTMENT:READ+DELETE";
    //end  dept相关权限

    // start company相关权限
    public static final String COMPANY_READ = "COMPANY:READ";
    public static final String COMPANY_CREATE = "COMPANY:READ+CREATE";
    public static final String COMPANY_EDIT = "COMPANY:READ+EDIT";
    public static final String COMPANY_DELETE = "COMPANY:READ+DELETE";
    //end  company相关权限

    // start agent相关权限
    public static final String AGENT_READ = "AGENT:READ";
    public static final String AGENT_CREATE = "AGENT:READ+CREATE";
    public static final String AGENT_EDIT = "AGENT:READ+EDIT";
    public static final String AGENT_DELETE = "AGENT:READ+DELETE";
    //end  agent相关权限

    // start 标签相关权限
    public static final String DICTIONARY_READ = "DICTIONARY:READ";
    public static final String DICTIONARY_CREATE = "DICTIONARY:CREATE";
    public static final String DICTIONARY_EDIT = "DICTIONARY:EDIT";
    public static final String DICTIONARY_DELETE = "DICTIONARY:DELETE";
    // end 标签相关权限

    //start 插件管理
    public static final String PLUGIN_READ = "PLUGIN:READ";
    //end 插件管理

    // start 系统日志权限
    public static final String SYSTEM_LOG_READ = "SYSTEM_LOG:READ";
    public static final String SYSTEM_LOG_KEEP_MONTH_UPDATE = "SYSTEM_LOG:READ+KEEP_MONTH_UPDATE";
    // end 系统日志权限

    // start UI设置
    public static final String KEYCLOAK_SETTING_READ = "KEYCLOAK_SETTING:READ";
    public static final String KEYCLOAK_SETTING_SYNC = "KEYCLOAK_SETTING:SYNC";
    // end 系统日志权限

    //START OS
    public static final String DICTIONARY_OS_READ = "DICTIONARY_OS:READ";
    public static final String DICTIONARY_OS_CREATE = "DICTIONARY_OS:READ+CREATE";
    public static final String DICTIONARY_OS_EDIT = "DICTIONARY_OS:READ+EDIT";
    public static final String DICTIONARY_OS_DELETE = "DICTIONARY_OS:READ+DELETE";
    public static final String DICTIONARY_OS_VERSION_CREATE = "DICTIONARY_OS:READ+VERSION_CREATE";
    public static final String DICTIONARY_OS_VERSION_DELETE = "DICTIONARY_OS:READ+VERSION_DELETE";
    //END OS

    // start mail设置
    public static final String UI_SETTING_READ = "UI_SETTING:READ";
    public static final String UI_SETTING_EDIT = "UI_SETTING:READ+EDIT";
    // end mail设置

    // start mail设置
    public static final String MAIL_SETTING_READ = "MAIL_SETTING:READ";
    public static final String MAIL_SETTING_EDIT = "MAIL_SETTING:READ+EDIT";
    // end mail设置

    // start mail设置
    public static final String LICENSE_READ = "LICENSE:READ";
    public static final String LICENSE_UPDATE = "LICENSE:READ+UPDATE";
    // end mail设置

    // module
    public static final String MODULE_READ = "MODULE:READ";
    public static final String MODULE_ACTIVE = "MODULE:READ+ACTIVE";
    public static final String MODULE_CREATE = "MODULE:READ+CREATE";
    public static final String MODULE_EDIT = "MODULE:READ+EDIT";
    public static final String MODULE_DELETE = "MODULE:READ+DELETE";


    //dashboard
    public static final String DASHBOARD_ACCOUNT = "DASHBOARD:ACCOUNT";
    public static final String DASHBOARD_CLOUD_ACCOUNT = "DASHBOARD:CLOUD_ACCOUNT";

    //sys_stats
    public static final String SYS_STATS = "SYS_STATS_READ:READ";


}
