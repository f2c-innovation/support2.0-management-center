ProjectApp.controller('DeptartmentController', function ($scope, HttpUtils, FilterSearch, $http, Notification, $state) {
    $scope.orgParam = angular.fromJson(sessionStorage.getItem("orgParam"));
    sessionStorage.removeItem("orgParam");
    // 定义搜索条件
    $scope.conditions = [
        {key: "name", name: "名称", directive: "filter-contains"},
    ];

    if ($scope.currentRole === $scope.roleConst.admin) {
        $scope.conditions.push({
            key: "companyId",
            name: "公司",
            directive: "filter-select-virtual",
            url: "company",
            search: true,
            convert: {value: "id", label: "name"}
        })
    }
    $scope.filters = [];
    if ($scope.orgParam && $scope.currentRole === $scope.roleConst.admin) {
        $scope.filters = [{
            key: "companyId",
            name: "公司",
            label: $scope.orgParam.label,
            value: $scope.orgParam.value
        }];
    }

    $scope.columns = [
        {value: "名称", key: "name", sort: false},
        {value: "所属公司", key: "companyName", sort: false},// 不想排序的列，用sort: false
        {value: "授权用户", key: "countAuthorizedUser"},// 不想排序的列，用sort: false
        {value: "描述", key: "description"},
        {value: "操作", key: "operate"}
    ];

    $scope.list = function (sortObj) {
        const condition = FilterSearch.convert($scope.filters);
        if (sortObj) {
            $scope.sort = sortObj;
        }
        // 保留排序条件，用于分页
        if ($scope.sort) {
            condition.sort = $scope.sort.sql;
        }
        HttpUtils.paging($scope, "department", condition);
    };
    $scope.list();

    $scope.create = function () {
        $scope.acquisitionConditions();
        $scope.formUrl = 'project/html/department/department_add.html' + '?_t=' + Math.random();
        $scope.toggleForm();
    };

    $scope.edit = function (data) {
        $scope.item = angular.copy(data);
        $scope.acquisitionConditions();
        $scope.formUrl = 'project/html/department/department_edit.html' + '?_t=' + Math.random();
        $scope.toggleForm();
    };

    $scope.acquisitionConditions = function () {
        HttpUtils.get("company", function (rep) {
            $scope.orgs = rep.data;
        });
    };


    $scope.submit = function (type, data) {
        if (type === 'add') {
            HttpUtils.post("department/add", data, function () {
                $scope.list();
                Notification.success("新建成功");
                $scope.closeToggleForm();
            }, function (rep) {
                Notification.danger(rep.message);
            })
        }
        if (type === 'edit') {
            $http.post('department/update', data).then(function () {
                $scope.list();
                Notification.success("编辑成功");
                $scope.closeToggleForm();
            }, function (rep) {
                Notification.danger(rep.data.message);
            });
        }
    };

    $scope.delete = function (company) {
        Notification.confirm("将删除公司:" + company.name + "，确认删除？", function () {
            $http.get("department/delete/" + company.id).then(function () {
                Notification.success("删除成功");
                $scope.list();
            }, function (rep) {
                Notification.danger(rep.message);
            })
        })
    };

    $scope.closeToggleForm = function () {
        $scope.toggleForm();
        $scope.item = {};
    };

    $scope.departmentAuthorize = function (item) {
        if ($scope.selected === item.$$hashKey) {
            $scope.closeInformation();
            return;
        }
        $scope.selected = item.$$hashKey;
        $scope.selectDeptartmentId = item.id;
        $scope.infoUrl = 'project/html/department/department_authorize.html' + '?_t=' + Math.random();
        $scope.toggleInfoForm(true);
    };

    $scope.closeInformation = function () {
        $scope.selected = "";
        $scope.toggleInfoForm(false);
    };
});
ProjectApp.controller('DeptartmentAuthorizeController', function ($scope, HttpUtils) {

    $scope.columns = [
        {value: "用户名", key: "name", sort: false},
        {value: "邮箱", key: "name", sort: false},
        {value: "电话", key: "phone", sort: false},
    ];

    $scope.list = function () {
        HttpUtils.paging($scope, "department/user/" + $scope.selectDeptartmentId, {})
    };
    $scope.list();
});
