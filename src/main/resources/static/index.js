angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/app/api/v1/products';

    $scope.fillTable = function (pageIndex) {
        $scope.filter && $scope.filter.id != null ? $scope.findProductById() : $scope.findAllProducts(pageIndex);
    }

    $scope.findAllProducts = function (pageIndex) {
        $http({
            url: contextPath,
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
    }

    $scope.findProductById = function () {
        $http.get(contextPath + '/' + $scope.filter.id)
            .then(function (response) {
                $scope.ProductsList = [response.data];
                console.log($scope.ProductsList)
                $scope.PageArray = [1];
            });
    };

    // $scope.createPagesArray = function (size) {
    //     if (size == null) size = 1;
    //     let array = [];
    //     for (let i = 0; i <= size; i++) {
    //         array.push(i);
    //     }
    //     $scope.PageCount = array;
    // };

    $scope.createPagesArray = function (start, end) {
        let array = [];
        for (let i = start; i <= end; i++) {
            array.push(i);
        }
        $scope.PageArray = array;
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

    $scope.cleanFilter = function () {
        $scope.filter ? $scope.filter.title = null : null;
        $scope.filter ? $scope.filter.min = null : null;
        $scope.filter ? $scope.filter.max = null : null;
        $scope.fillTable();
    };

    $scope.findAllProducts();
})
;
