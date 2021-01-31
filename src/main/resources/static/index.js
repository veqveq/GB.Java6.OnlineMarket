angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/app/api/v1/products';

    $scope.fillTable = function () {
        $scope.filter && $scope.filter.id != null ? $scope.findProductById() : $scope.findAllProducts();
    }

    $scope.findAllProducts = function () {
        $http({
            url: contextPath,
            method: 'GET',
            params: {
                min: $scope.filter ? $scope.filter.min : null,
                max: $scope.filter ? $scope.filter.max : null,
                size: $scope.filter ? $scope.filter.size : 10,
                numb: $scope.pageIndex
            },
        }).then(function (response) {
            $scope.ProductsPage = response.data;
            $scope.ProductsList = $scope.ProductsPage.content;
            console.log($scope.ProductsList)
            if ($scope.ProductsPage.empty && $scope.pageIndex !== 1) {
                $scope.pageIndex = $scope.pageIndex - 1;
                $scope.fillTable();
            }
            $scope.pageCount = $scope.createPagesArray($scope.ProductsPage.totalPages);
        });
    }

    $scope.findProductById = function () {
        $http.get(contextPath + '/' + $scope.filter.id)
            .then(function (response) {
                $scope.ProductsList = [response.data];
                console.log($scope.ProductsList)
                $scope.pageCount = $scope.createPagesArray($scope.ProductsList.length);
            });
    };

    $scope.createPagesArray = function (size) {
        if (size == null) size = 1;
        let array = [];
        for (let i = 0; i < size; i++) {
            array.push(i + 1);
        }
        $scope.PageCount = array;
    };

    $scope.createNewProduct = function () {
        $http.post(contextPath, $scope.NewProduct)
            .then(function (response) {
                $scope.NewProduct = null;
                $scope.fillTable();
            });
    };

    $scope.deleteProductById = function (id) {
        $http.delete(contextPath + '/' + id)
            .then(function (response) {
                $scope.fillTable();
            });
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

    $scope.changePage = function (index) {
        $scope.pageIndex = index;
        $scope.fillTable();
    }

    $scope.cleanFilter = function () {
        $scope.filter ? $scope.filter.min = null : null;
        $scope.filter ? $scope.filter.max = null : null;
        $scope.fillTable();
    };

    $scope.findAllProducts();
})
;
