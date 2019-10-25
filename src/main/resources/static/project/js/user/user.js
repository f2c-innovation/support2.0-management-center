ProjectApp.controller('UserController', function ($scope, HttpUtils, FilterSearch, $http, Notification, operationArr, eyeService, $state, $stateParams, ProhibitPrompts, UserService, AuthService, Loading) {

        // 定义搜索条件
        $scope.conditions = [
            {key: "id", name: "ID", directive: "filter-contains"},
            {key: "name", name: "姓名", directive: "filter-contains"},
            {key: "email", name: "邮箱", directive: "filter-contains"},
            //增加一个异步字典转换的例子，将请求内容转换为value,label格式
            {
                key: "roleId",
                name: "角色",
                directive: "filter-select-virtual",
                url: "role",
                convert: {value: "id", label: "name"}
            }
        ];

        // 全选按钮，添加到$scope.columns
        $scope.first = {
            default: true,
            sort: false,
            type: "checkbox",
            checkValue: false,
            change: function (checked) {
                $scope.items.forEach(function (item) {
                    item.enable = checked;
                });
            },
            width: "40px"
        };

        $scope.columns = [
            {value: "ID", key: "id", sort: false},
            {value: "姓名", key: "name", sort: false},
            {value: "邮箱", key: "email", sort: false},// 不想排序的列，用sort: false
            {value: "角色", key: "roleName", sort: false},// 不想排序的列，用sort: false
            {value: "公司", key: "company"},
            {value: "状态", key: "active"},
            {value: "手机号码", key: "phone", sort: false},
            {value: "创建日期", key: "user.create_time"}
        ];
        if (AuthService.hasPermissions("USER:READ+EDIT,USER:READ+DELETE,USER:READ+RESET_PASSWORD,USER:READ+LOG")) {
            $scope.columns.push({value: "", default: true});
        }

        if (AuthService.hasPermissions("USER:READ+ADD_ROLE")) {
            $scope.columns.unshift($scope.first);
        }

        // 用于传入后台的参数
        $scope.filters = [];

        $scope.list = function (sortObj) {
            const condition = FilterSearch.convert($scope.filters);
            if (sortObj) {
                $scope.sort = sortObj;
            }
            // 保留排序条件，用于分页
            if ($scope.sort) {
                condition.sort = $scope.sort.sql;
            }
            HttpUtils.paging($scope, "user", condition, function () {
            });
        };

        $scope.list();

        $scope.getItemIds = function (ids) {
            angular.forEach($scope.items, function (item) {
                if (item.enable) {
                    ids.push(item.id);
                }
            });
        };
        $scope.addUserRole = function () {
            $scope.ids = [];
            $scope.getItemIds($scope.ids);
            if ($scope.ids.length === 0) {
                Notification.info("未选择操作项");
            } else {
                $scope.item = {};
                $scope.item.userIdList = $scope.ids;
                $scope.isAddLineAble = true;
                $scope.item.roleInfoList = [];
                $scope.addLine();
                $scope.formUrl = 'project/html/user/user_add_role.html' + '?_t=' + Math.random();
                $scope.toggleForm();
            }
        };

        $scope.submitUserRole = function (item) {
            HttpUtils.post("user/role/add", item, function () {
                Notification.success("添加角色成功");
                $scope.closeToggleForm();
                $scope.list();
            }, function (response) {
                Notification.danger("添加角色失败，" + response.message);
            })
        };

        $scope.create = function () {
            $scope.item = {};
            $scope.isAddLineAble = true;
            $scope.item.roleInfoList = [];
            $scope.addLine();
            $scope.formUrl = 'project/html/user/user_add.html' + '?_t=' + Math.random();
            $scope.toggleForm();
        };

        $scope.addLine = function () {
            let roleInfo = {};
            roleInfo.length = {"width": '100%'};
            roleInfo.selects = [];
            angular.forEach($scope.item.roleInfoList, function (info) {
                roleInfo.selects.push(info.roleId);
            });
            $scope.item.roleInfoList.push(roleInfo);
            if ($scope.item.roleInfoList && $scope.roles) {
                if ($scope.item.roleInfoList.length === $scope.roles.length) {
                    $scope.isAddLineAble = false;
                }
            }
        };
        $scope.subtractLine = function (info) {
            operationArr.removeByValue($scope.item.roleInfoList, info);
            if ($scope.item.roleInfoList.length !== $scope.roles.length) {
                $scope.isAddLineAble = true;
            }
        };

        $scope.filterRole = function (role, roleInfo) {
            let value = true;
            if (roleInfo.selects.length === 0) {
                value = true;
            }
            angular.forEach(roleInfo.selects, function (roleId) {
                if (role.id === roleId) {
                    value = false;
                }
            });
            return value;
        };

        $scope.showResources = function (item) {
            if ($scope.selected === item.$$hashKey) {
                $scope.closeInformation();
                $scope.selected = null;
                return;
            }
            $scope.selected = item.$$hashKey;
            $scope.showResourceInfo = {user: item};
            $scope.showInformation();

        };

        $scope.showInformation = function () {
            $scope.infoUrl = 'project/html/user/resource_list.html' + '?_t=' + Math.random();
            $scope.toggleInfoForm(true);
        };

        $scope.closeInformation = function () {
            $scope.toggleInfoForm(false);
        };


        $scope.changeActive = function (user) {
            let message = null;
            if (!user.active) {
                message = "启用";
            } else {
                message = "禁用";
            }
            $scope.item = {};
            $scope.item.id = user.id;
            $scope.item.roleId = user.roleId;
            $scope.item.active = !user.active;
            $scope.item.roleIds = user.roleIds;
            HttpUtils.post("user/active", $scope.item, function () {
                Notification.success(message + "成功");
                $scope.item = {};
            }, function (rep) {
                $scope.list();
                Notification.danger(rep.message);
            })
        };

        $scope.isRePassword = function (item) {
            let flag = true;
            if (item.source === "extra") {
                return false;
            } else {
                if ($scope.currentRole === $scope.roleConst.orgAdmin) {
                    angular.forEach(item.roles, function (currentRole) {
                        if (currentRole.parentId === $scope.roleConst.orgAdmin || currentRole.parentId === $scope.roleConst.admin) {
                            flag = false;
                        }
                    })
                }
                return flag;
            }
        };

        $scope.changeType = function (id) {
            ProhibitPrompts.changeType(id);
        };

        $scope.edit = function (data) {
            $scope.item = angular.copy(data);
            $scope.select = {};
            $scope.isAddLineAble = true;
            Loading.add(
                $http.get("user/role/info/" + data.id).then(function (rep) {
                    $scope.item.roleInfoList = [];
                    angular.forEach(rep.data.data, function (roleInfo) {
                        let parentId = $scope.getParentId(roleInfo.roleId);
                        roleInfo.selects = [];
                        if ($scope.currentRole === $scope.roleConst.orgAdmin) {
                            if (parentId === $scope.roleConst.orgAdmin) {
                                roleInfo.roleType = $scope.roleConst.orgAdmin;
                                Loading.add(HttpUtils.get("user/ids/" + data.id, function (rep) {
                                    $scope.select.organizationIds = rep.data;
                                }));
                                $scope.item.roleInfoList.push(roleInfo)
                            }
                            if (parentId === $scope.roleConst.user) {
                                roleInfo.roleType = $scope.roleConst.user;
                                Loading.add(HttpUtils.get("user/ids/" + data.id, function (rep) {
                                    $scope.select.workspaceIds = rep.data;
                                }));
                                $scope.item.roleInfoList.push(roleInfo)
                            }

                        }
                        if ($scope.currentRole === $scope.roleConst.admin) {
                            if (parentId === $scope.roleConst.orgAdmin) {
                                roleInfo.roleType = $scope.roleConst.orgAdmin;
                                Loading.add(HttpUtils.get("user/ids/" + data.id, function (rep) {
                                    $scope.select.organizationIds = rep.data;
                                }));
                            }
                            if (parentId === $scope.roleConst.user) {
                                roleInfo.roleType = $scope.roleConst.user;
                                Loading.add(HttpUtils.get("user/ids/" + data.id, function (rep) {
                                    $scope.select.workspaceIds = rep.data;
                                }));
                            }
                            if (parentId === $scope.roleConst.admin) {
                                roleInfo.roleType = $scope.roleConst.admin;
                            }
                            if (parentId === 'other') {
                                roleInfo.roleType = 'other';
                            }
                            $scope.item.roleInfoList.push(roleInfo);
                        }
                    });
                    if ($scope.item.roleInfoList.length === $scope.roles.length) {
                        $scope.isAddLineAble = false;
                    }
                }));
            $scope.editLoadingLayer = Loading.load();
            $scope.formUrl = 'project/html/user/user-edit.html' + '?_t=' + Math.random();
            $scope.toggleForm();
        };

        $scope.changeRole = function (roleInfo, roleId) {
            roleInfo.roleType = $scope.getParentId(roleId);
            switch (roleInfo.roleType) {
                case $scope.roleConst.admin:
                    roleInfo.length = {"width": '100%'};
                    break;
                case $scope.roleConst.orgAdmin:
                    roleInfo.length = {"width": '50%'};
                    break;
                case $scope.roleConst.user:
                    roleInfo.length = {"width": '33%'};
                    break;
            }
            if ($scope.item.roleInfoList.length === $scope.roles.length) {
                $scope.isAddLineAble = false;
            }
        };

        $scope.getParentId = function (roleId) {
            let parentId = null;
            angular.forEach($scope.roles, function (role) {
                if (role.id === roleId) {
                    if (role.type !== 'System') {
                        parentId = role.parentId;
                    } else {
                        parentId = roleId;
                    }
                }
            });
            return parentId;
        };

        $scope.resetPassword = function (item) {
            $scope.resetItem = item;
            $scope.formUrl = 'project/html/user/user-reset-password.html' + '?_t=' + Math.random();
            $scope.toggleForm();
        };
        $scope.acquisitionConditions = function () {
            HttpUtils.get("role", function (rep) {
                $scope.roles = rep.data;
            });
            if ($scope.currentRole === $scope.roleConst.orgAdmin) {
                HttpUtils.get("company/currentCompany", function (rep) {
                    $scope.orgs = rep.data;
                });
            } else {
                HttpUtils.get("company", function (rep) {
                    $scope.orgs = rep.data;
                });
            }

            HttpUtils.get("department", function (rep) {
                $scope.workspaces = rep.data;
            });
        };

        $scope.acquisitionConditions();

        $scope.submit = function (type, data) {
            if (type === 'add') {
                data.source = 'local';
                HttpUtils.post("user/add", data, function () {
                    Notification.success("新建成功");
                    $scope.closeToggleForm();
                    $scope.list();
                    $scope.acquisitionConditions();
                }, function (rep) {
                    Notification.danger(rep.message);
                })
            }
            if (type === 'edit') {
                HttpUtils.post('user/update', data, function () {
                    Notification.success("编辑成功");
                    $scope.closeToggleForm();
                    $scope.list();
                });
            }
            if (type === 'resetPassword') {
                HttpUtils.post("user/reset/password", data, function () {
                    Notification.success("修改成功");
                    $scope.closeToggleForm();
                    $scope.list();
                }, function (rep) {
                    Notification.danger(rep.message);
                })
            }
        };

        $scope.delete = function (user) {
            Notification.confirm("将删除用户：" + user.name + "，确认删除？", function () {
                $http.get("user/delete/" + user.id).then(function () {
                    Notification.success("删除成功");
                    $scope.list();
                }, function (rep) {
                    Notification.danger(rep.data.message)
                })
            });
        };


        $scope.closeToggleForm = function () {
            $scope.item = {};
            $scope.toggleForm();
            $scope.resetItem = {}
        };

        $scope.view = function (password, eye) {
            eyeService.view("#" + password, "#" + eye);
        };

        $scope.goExtraUsers = function () {
            $scope.item = {};
            $scope.isAddLineAble = true;
            $scope.item.roleInfoList = [];
            $scope.formUrl = 'project/html/user/import_users.html' + '?_t=' + Math.random();
            $scope.toggleForm();
            $scope.show = true;
        };

        $scope.export = function (sortObj) {
            let condition = FilterSearch.convert($scope.filters);
            if (sortObj) {
                $scope.sort = sortObj;
            }
            if ($scope.sort) {
                condition.sort = $scope.sort.sql;
            }
            let columns = $scope.columns.filter(function (c) {
                return c.checked !== false && c.key;
            });

            $scope.loadingLayer = HttpUtils.download('user/export', {
                columns: columns,
                params: condition
            }, '用户列表.xlsx', 'application/octet-stream');
        };
    }
);


ProjectApp.controller('ResourceController', function ($scope, HttpUtils, $http) {

    $scope.columns = [
        {value: "名称", key: "name", sort: false},
        {value: "创建时间", key: "createTime", sort: false},
    ];

    $scope.roleTree = function () {
        $scope.showInfoName = $scope.showResourceInfo.user.name + "的角色";
        $scope.treeData = [];
        $scope.noroot = {};
        $http.get("user/switch/source/" + $scope.showResourceInfo.user.id).then(function (rep) {
            $scope.rawData = rep.data.data;
            angular.forEach($scope.rawData, function (data) {
                data.collapsed = false;
                if (data.parentId == null) {
                    if (data.switchable) {
                        data.name = data.name + "[" + data.desc + "]"
                    }
                    $scope.treeData.push(data);
                }
            });
            angular.forEach($scope.treeData, function (treeData) {
                treeData.children = [];
                angular.forEach($scope.rawData, function (data) {
                    if (data.parentId === treeData.id) {
                        if (data.switchable) {
                            data.name = data.name + "[" + data.desc + "]"
                        }
                        treeData.children.push(data)
                    }
                });
                if (treeData.children.length === 0) {
                    treeData.children = null;
                }
            })

        }, function (rep) {

        });
    };
    $scope.roleTree();
});

ProjectApp.controller('ImportExtraUserController', function ($scope, HttpUtils, FilterSearch, $http, Notification, operationArr) {

    $scope.addLine();

    $scope.wizard = {
        setting: {
            title: "标题",
            subtitle: "子标题",
            closeText: "取消",
            submitText: "保存",
            nextText: "下一步",
            prevText: "上一步",
        },
        // 按顺序显示,id必须唯一并需要与页面中的id一致，select为分步初始化方法，next为下一步方法(最后一步时作为提交方法)
        steps: [
            {
                id: "1",
                name: "选择用户",
                select: function () {
                },
                next: function () {
                    let ids = [];
                    $scope.getItemIds(ids);
                    if (ids.length === 0) {
                        Notification.info("请选择导入的用户！");
                        return false
                    } else {
                        $scope.item.ids = ids;
                        return true;
                    }
                }
            },
            {
                id: "2",
                name: "选择角色",
                select: function () {
                },
                next: function () {
                    if ($scope.item.roleInfoList.length === 1 && angular.isUndefined($scope.item.roleInfoList[0].roleId)) {
                        Notification.warn("请选择角色！");
                        return false;
                    } else {
                        $http.post("extra/user/import", $scope.item.roleInfoList, {headers: {ids: $scope.item.ids}}).then(function () {
                            Notification.success("导入成功！");
                            $scope.closeToggleForm();
                            $scope.list();
                        }, function (rep) {
                            Notification.danger(rep.data.message);
                        });
                    }
                }
            }
        ],
        // 嵌入页面需要指定关闭方法
        close: function () {
            $scope.show = false;
            $scope.closeToggleForm();
        }
    };

    // 全选按钮，添加到$scope.columns
    $scope.first = {
        default: true,
        sort: false,
        type: "checkbox",
        checkValue: false,
        change: function (checked) {
            $scope.items.forEach(function (item) {
                item.enable = checked;
            });
        },
        width: "40px"
    };

    $scope.getItemIds = function (ids) {
        angular.forEach($scope.items, function (item) {
            if (item.enable) {
                ids.push(item.id);
            }
        });
    };

    $scope.clickAllCheck = function (check) {
        $scope.items.forEach(function (item) {
            item.enable = !check;
        });
    };

    $scope.conditions = [
        {key: "name", name: "ID", directive: "filter-contains"},
        {key: "displayName", name: "姓名", directive: "filter-contains"},
        {key: "email", name: "邮箱", directive: "filter-contains"}
    ];

    $scope.filters = [];
    $scope.extraUsers = function (sortObj) {
        const condition = FilterSearch.convert($scope.filters);
        if (sortObj) {
            $scope.sort = sortObj;
        }
        // 保留排序条件，用于分页
        if ($scope.sort) {
            condition.sort = $scope.sort.sql;
        }
        HttpUtils.paging($scope, "extra/user", condition, function () {
            angular.forEach($scope.items, function (item) {
                item.enable = false;
            })
        });
    };

    $scope.extraUsers();

    $scope.importExtraUsers = function () {
        $scope.formUrl = 'project/html/user/import_extra_users.html' + '?_t=' + window.appversion;
        $scope.toggleForm();
    };
});
