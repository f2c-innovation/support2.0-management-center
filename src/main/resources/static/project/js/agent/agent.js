ProjectApp.controller('agentController', function ($scope, HttpUtils, FilterSearch, $http, Notification, operationArr, $state) {

    // 定义搜索条件
    $scope.conditions = [
        {key: "name", name: "名称", directive: "filter-contains"}
    ];

    // 用于传入后台的参数
    $scope.filters = [];
    $scope.ids = [];
    // 全选按钮，添加到$scope.columns
    $scope.first = {
        default: true,
        sort: false,
        type: "checkbox",
        checkValue: false,
        change: function (checked) {
            $scope.items.forEach(function (item) {
                if (!item.subscriptionCount > 0) {
                    item.enable = checked;
                    $scope.singleClick(checked, item, true);
                }
            });
        },
        width: "40px"
    };

    $scope.clickChecked = function (checked, item, isSelectAll) {
        $scope.singleClick(checked, item, isSelectAll);
    };

    $scope.singleClick = function (checked, item, isSelectAll) {
        if (checked === true) {
            $scope.ids.push(item.id);
        } else {
            if (isSelectAll) {
                $scope.ids = [];
            } else {
                operationArr.removeByValue($scope.ids, item.id);
                if ($scope.ids.length === 0) {
                    $scope.first.checkValue = false;
                }
            }
        }
    };

    $scope.columns = [
        {value: "名称", key: "name", sort: false},
        {value: "关联客户", key: "companyCount"},// 不想排序的列，用sort: false
        {value: "订阅", key: "subscriptionCount"},// 不想排序的列，用sort: false
        {value: "用户", key: "userCount"},
        {value: "创建时间", key: "create_time"},
        {value: "操作", key: "operate"}
    ];

    $scope.list = function (sortObj) {
        var condition = FilterSearch.convert($scope.filters);
        if (sortObj) {
            $scope.sort = sortObj;
        }
        // 保留排序条件，用于分页
        if ($scope.sort) {
            condition.sort = $scope.sort.sql;
        }
        HttpUtils.paging($scope, "agent", condition, function () {
            angular.forEach($scope.items, function (item) {
                item.enable = false;
            });
        });
    };
    $scope.list();

    $scope.create = function () {
        $scope.formUrl = 'project/html/agent/agent_add.html' + '?_t=' + Math.random();
        $scope.toggleForm();
    };

    $scope.edit = function (data) {
        $scope.item = angular.copy(data);
        $scope.formUrl = 'project/html/agent/agent_edit.html' + '?_t=' + Math.random();
        $scope.toggleForm();
    };

    $scope.submit = function (type, data) {
        if (type === 'add') {
            HttpUtils.post("agent/add", data, function () {
                $scope.list();
                Notification.success("新建成功");
                $scope.closeToggleForm();
            }, function (rep) {
                Notification.danger(rep.message);
            })
        }
        if (type === 'edit') {
            $http.post('agent/update', data).then(function () {
                $scope.list();
                Notification.success("编辑成功");
                $scope.closeToggleForm();
            }, function (rep) {
                Notification.danger(rep.data.message);
            });
        }
    };
    $scope.delete = function () {
        if ($scope.ids.length === 0) {
            Notification.info("请先选择公司！")
        } else {
            Notification.confirm("将删除所选公司，确认删除？", function () {
                $http.post("agent/delete", $scope.ids).then(function () {
                    Notification.success("删除成功");
                    $scope.list();
                }, function (rep) {
                    Notification.danger(rep.data.message)
                })
            })
        }
    };

    $scope.closeToggleForm = function () {
        $scope.toggleForm();
        $scope.item = {};
    };

    $scope.linkDept = function (item) {
        sessionStorage.setItem("orgParam", angular.toJson({
                label: item.name,
                value: item.id
            }
        ));
        $state.go("workspace")
    };

    $scope.linkCompanyAdmin = function (item) {
        if ($scope.selected === item.$$hashKey) {
            $scope.closeInformation();
            return;
        }
        $scope.selected = item.$$hashKey;
        $scope.orgId = item.id;
        $scope.infoUrl = 'project/html/agent/agent-link-orgAdmin.html' + '?_t=' + Math.random();
        $scope.toggleInfoForm(true);
    };

    $scope.closeInformation = function () {
        $scope.item = {};
        $scope.selected = "";
        $scope.toggleInfoForm(false);
    };
});
