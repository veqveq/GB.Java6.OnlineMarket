angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/app/api/v1';

    $scope.fillTable = function (pageIndex) {
        $scope.filter && $scope.filter.id != null ? $scope.findProductById() : $scope.findAllProducts(pageIndex);
    }

    $scope.findAllProducts = function (pageIndex = 1) {
        $http({
            url: contextPath + '/products',
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
        $http.get(contextPath + '/products/' + $scope.filter.id)
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

    $scope.createNewProduct = function () {
        $http.post(contextPath + '/products', $scope.NewProduct)
            .then(function (response) {
                $scope.NewProduct = null;
                $scope.fillTable();
            });
    };

    $scope.deleteProductById = function (id) {
        $http.delete(contextPath + '/products/' + id)
            .then(function (response) {
                $scope.fillTable();
            });
    };

    $scope.addProductInCart = function (id) {
        $http.get(contextPath + '/cart/add/' + id)
            .then(function (response) {
                $scope.fillCart();
            })
    }

    $scope.fillCart = function () {
        $http.get(contextPath + '/cart/')
            .then(function (response) {
                console.log(response.data)
                $scope.CartList = response.data;
                $scope.cartCalculation();
            })
    }

    $scope.deleteProductByCart = function (id) {
        $http.get(contextPath + '/cart/delete/' + id)
            .then(function (response) {
                $scope.fillCart();
            });
    }

    $scope.cartCalculation = function () {
        $scope.CartSum = 0;
        for (let i = 0; i < $scope.CartList.length; i++) {
            $scope.CartSum = $scope.CartSum + $scope.CartList[i].product.cost * $scope.CartList[i].count;
        }
    }

    $scope.cleanCart = function (){
        $http.get(contextPath + '/cart/clean')
            .then(function (response) {
                $scope.fillCart();
            })
    }


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
    $scope.fillCart();
})
;
