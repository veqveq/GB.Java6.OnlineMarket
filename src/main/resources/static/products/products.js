angular.module('app').controller('productsController', function ($scope, $http, $localStorage, $location) {
    const rootPath = 'http://localhost:8189/app';
    const apiPath = rootPath + '/api/v1';

    $scope.fillTable = function (pageIndex) {
        $scope.filter && $scope.filter.id != null ? $scope.findProductById() : $scope.findAllProducts(pageIndex);
    };

    $scope.findAllProducts = function (pageIndex = 1) {
        $http({
            url: apiPath + '/products',
            method: 'GET',
            params: {
                min: $scope.filter ? $scope.filter.min : null,
                max: $scope.filter ? $scope.filter.max : null,
                title: $scope.filter ? $scope.filter.title : null,
                size: $scope.filter ? $scope.filter.size : 10,
                numb: pageIndex
            },
        }).then(function (response) {
            $scope.ProductsPage = response.data;
            $scope.ProductsList = $scope.ProductsPage.content;
            console.log($scope.ProductsPage)
            if ($scope.ProductsPage.empty && pageIndex !== 1) {
                $scope.fillTable(pageIndex--);
            }
            let minPageIndex = pageIndex - 2;
            let maxPageIndex = pageIndex + 2;
            if (minPageIndex < 1) minPageIndex = 1;
            if (maxPageIndex > $scope.ProductsPage.totalPages) maxPageIndex = $scope.ProductsPage.totalPages;
            $scope.pageCount = $scope.createPagesArray(minPageIndex, maxPageIndex);
        });
    };

    $scope.findProductById = function () {
        $http.get(apiPath + '/products/' + $scope.filter.id)
            .then(function (response) {
                $scope.ProductsList = [response.data];
                console.log($scope.ProductsList)
                $scope.PageArray = [1];
            });
    };

    $scope.createPagesArray = function (start, end) {
        let array = [];
        for (let i = start; i <= end; i++) {
            array.push(i);
        }
        $scope.PageArray = array;
    };

    $scope.changePagination = function () {
        let selectElement = document.getElementById("pagination").options.selectedIndex;
        let val = document.getElementById("pagination").options[selectElement].value;
        if ($scope.value !== val) {
            $scope.value = val;
            $scope.pageIndex = 1;
            $scope.fillTable();
        }
    };

    $scope.cleanFilter = function () {
        $scope.filter ? $scope.filter.title = null : null;
        $scope.filter ? $scope.filter.min = null : null;
        $scope.filter ? $scope.filter.max = null : null;
        $scope.fillTable();
    };

    $scope.userLogged = function () {
        return $scope.getUser() != null;
    }

    $scope.getUser = function () {
        return $localStorage.authUser;
    }

    $scope.findAllProducts();
});