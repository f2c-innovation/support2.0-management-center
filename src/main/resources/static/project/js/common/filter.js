ProjectApp.filter('userSource', function () {
    return function (input) {
        if (input === 'local') {
            return '本地创建'
        }
        if (input === 'extra') {
            return '第三方'
        }

        return input;
    }
});

ProjectApp.filter('roleType', function () {
    return function (input) {
        if (input === 'System') {
            return '系统'
        }
        if (input === 'Additional') {
            return '自定义'
        }
    }
});


ProjectApp.filter('roleParentType', function () {
    return function (input) {
        if (input === 'ADMIN') {
            return '系统管理员'
        }
        if (input === 'ORGADMIN') {
            return '管理员'
        }
        if (input === 'USER') {
            return '用户'
        }
    }
});

ProjectApp.filter('moduleType', function () {
    return function (input) {
        if (input === 'management-center') {
            return '管理中心'
        }
    }
});

ProjectApp.filter('resourceType', function () {
    return function (input) {
        if (input === 'USER') {
            return '用户'
        }
        if (input === 'DEPARTMENT') {
            return '部门'
        }
        if (input === 'VIRTUALMACHINE') {
            return '云主机'
        }
        if (input === 'PRODUCT') {
            return '产品'
        }
    }
});

