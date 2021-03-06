ProjectApp.controller('categoryController', function ($scope, $mdDialog, $document, $state, $stateParams, HttpUtils, FilterSearch, Notification, AuthService) {
    $scope.dictionaryParam = $stateParams.dictionaryParam ? $stateParams.dictionaryParam : angular.fromJson(sessionStorage.getItem("dictionaryParam")) || {};
    sessionStorage.removeItem("dictionaryParam");

    $scope.conditions = [
        {key: "id", name: "分组标识", directive: "filter-contains"},
        {key: "name", name: "分组名称", directive: "filter-contains"}
    ];

    // 用于传入后台的参数
    $scope.filters = $scope.dictionaryParam.filters || [];
    // 全选按钮，添加到$scope.columns
    $scope.first = {
        default: true,
        type: "checkbox",
        checkValue: false,
        change: function (checked) {
            $scope.items.forEach(function (item) {
                item.enable = checked;
            });
        },
        width: "40px"
    };

    $scope.showDetail = function (item) {
        $scope.detail = item;
    };

    $scope.columns = [
        {value: "分组标识", key: "id"},
        {value: "分组别名", key: "name"},
        {value: "状态", key: "status"},// 不想排序的列，用sort: false
        {value: "操作", key: "operate"}
    ];
    if (AuthService.hasPermissions("DICTIONARY:READ+EDIT,DICTIONARY:READ+DELETE,DICTIONARY:READ")) {
        $scope.columns.push({value: "", default: true});
    }

    $scope.editCategoryForm = function (item) {
        $scope.item = item;
        $scope.formUrl = 'project/html/system/dictionary/category_edit.html' + '?_t=' + window.appversion;
        $scope.toggleForm();
    };

    $scope.editCategorySubmit = function (item) {
        var message = item.dictionaryId ? '修改完成' : '新增完成';
        item.status = 1
        var url = item.id ? 'dictionary/category/update' : 'dictionary/category/add';
        HttpUtils.post(url, item, function () {
            $scope.toggleForm();
            $scope.list();
            Notification.success(message);
        });
    };

    $scope.editCategoryStatus = function (item) {
        HttpUtils.post("dictionary/category/update/status", item, function () {
            Notification.success('修改完成');
        });
    };

    $scope.deleteCategory = function (item) {
        Notification.confirm('确认删除此一级字典?', function () {
            // 确认删除
            HttpUtils.get('dictionary/category/delete/' + item.id, function (response) {
                $scope.list();
                Notification.success("删除成功");
            });
        });
    };

    $scope.editValues = function (item) {
        $state.go("dictionary-values-edit", {dictionary: item, dictionaryParam: {pagination: $scope.pagination, filters: $scope.filters}});
    };

    // 保证从标签值页面返回的时候分页正常显示
    $scope.pagination = $scope.dictionaryParam.pagination || {
        page: 1,
        limit: 10,
        limits: [10, 20, 50, 100]
    };

    $scope.list = function (sortObj, page, limit) {
        var condition = FilterSearch.convert($scope.filters);
        HttpUtils.paging($scope, 'dictionary/category', condition)
    };

    $scope.list();
});

ProjectApp.controller('DictionaryValuesController', function ($scope, $mdDialog, $state, $stateParams, $document, HttpUtils, FilterSearch, Notification, AuthService) {
    $scope.dictionary = $stateParams.dictionary ? $stateParams.dictionary : angular.fromJson(sessionStorage.getItem("dictionary"));
    sessionStorage.setItem("dictionary", angular.toJson($scope.dictionary));

    $scope.dictionaryParam = $stateParams.dictionaryParam ? $stateParams.dictionaryParam : angular.fromJson(sessionStorage.getItem("dictionaryParam"));
    sessionStorage.setItem("dictionaryParam", angular.toJson($scope.dictionaryParam));


    $scope.conditions = [
        {key: "dictionaryValue", name: "标签值", directive: "filter-contains"},
        {key: "dictionaryValueAlias", name: "标签值别名", directive: "filter-contains"}
    ];

    // 用于传入后台的参数
    $scope.filters = [{key: "dictionaryKey", name: "标签", value: $scope.dictionary.dictionaryKey, default: true, operator: "="}];
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

    $scope.goDictionarys = function () {
        $state.go('dictionary')
    };

    $scope.deleteDictionaryValue = function (item) {
        Notification.confirm('确认删除此标签值?', function () {
            // 确认删除
            HttpUtils.get('dictionary/value/delete/' + item.id, function (response) {
                Notification.success("删除成功");
                $scope.list();
            });
        });
    };

    $scope.columns = [
        {value: "标签", key: "dictionary_key", sort: false},
        {value: "标签值", key: "dictionary_value"},
        {value: "标签值别名", key: "dictionary_value_alias"}
    ];
    if (AuthService.hasPermissions("DICTIONARY:READ+DICTIONARY_VALUE:READ+EDIT,DICTIONARY:READ+DICTIONARY_VALUE:READ+DELETE")) {
        $scope.columns.push({value: "", default: true, sort: false});
    }
    $scope.editDictionaryValueForm = function (item) {
        item.dictionaryKey = $scope.dictionary.dictionaryKey;
        $scope.item = item.id ? item : {dictionaryKey: $scope.dictionary.dictionaryKey};
        $scope.formUrl = 'project/html/dictionary/dictionary-value-edit.html' + '?_t=' + window.appversion;
        $scope.toggleForm();
    };

    $scope.editDictionaryValueSubmit = function (item) {
        var message = item.id ? '修改完成' : '创建完成';
        var url = item.id ? 'dictionary/value/update' : 'dictionary/value/add';
        HttpUtils.post(url, item, function () {
            $scope.list();
            Notification.success(message);
            $scope.toggleForm();
        });
    };

    $scope.uploadFile = function (file, isClear) {
        if (!file) {
            Notification.info('请选择文件');
            return;
        }

        var formData = new FormData();
        formData.append("dictionaryKey", $scope.dictionary.dictionaryKey);
        formData.append("isClear", !!isClear);
        formData.append('file', file[0]);
        HttpUtils.post('dictionary/value/import', formData, function (response) {
            Notification.success("导入成功. 共导入" + response.data + "条记录");
            $scope.toggleForm();
            $scope.list();
        }, function (response) {
            Notification.danger("导入失败. " + response.message);
        }, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        });
    };

    $scope.list = function (sortObj, page, limit) {
        var condition = FilterSearch.convert($scope.filters);
        if (sortObj) {
            $scope.sort = sortObj;
        }
        // 保留排序条件，用于分页
        if ($scope.sort) {
            condition.sort = $scope.sort.sql;
        }
        HttpUtils.paging($scope, 'dictionary/value/list', condition)
    };

    $scope.list();
});
