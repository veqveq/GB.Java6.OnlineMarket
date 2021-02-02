angular.module('app', []).controller('indexController', function ($scope, $http) {
    const rootPath = 'http://localhost:8189/app';
    const apiPath = rootPath + '/api/v1';
    $scope.authorized = false;

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

    $scope.createNewProduct = function () {
        $http.post(apiPath + '/products', $scope.NewProduct)
            .then(function (response) {
                $scope.NewProduct = null;
                $scope.fillTable();
            });
    };

    $scope.deleteProductById = function (id) {
        $http.delete(apiPath + '/products/' + id)
            .then(function (response) {
                $scope.fillTable();
            });
    };

    $scope.addProductInCart = function (id) {
        $http.get(apiPath + '/cart/add/' + id)
            .then(function (response) {
                $scope.fillCart();
            })
    };

    $scope.fillCart = function () {
        $http.get(apiPath + '/cart/')
            .then(function (response) {
                console.log(response.data)
                $scope.Cart = response.data;
                $scope.CartList = $scope.Cart.orders;
                $scope.CartSum = $scope.Cart.totalPrice;
            })
    };

    $scope.deleteProductByCart = function (id) {
        $http.get(apiPath + '/cart/delete/' + id)
            .then(function (response) {
                $scope.fillCart();
            });
    };

    $scope.decCountProductInCart = function (id) {
        $http.get(apiPath + '/cart/dec/' + id)
            .then(function (response) {
                $scope.fillCart();
            });
    };

    $scope.cleanCart = function () {
        $http.get(apiPath + '/cart/clean')
            .then(function (response) {
                $scope.fillCart();
            })
    };

    $scope.makeOrder = function () {
        $http.post(apiPath + '/cart', $scope.Cart)
            .then(function (response) {
                window.alert("Заказ успешно оформлен!")
                $scope.cleanCart();
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

    $scope.tryToAuth = function () {
        $http.post(rootPath + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $scope.user.username = null;
                    $scope.user.password = null;
                    $scope.authorized = true;
                    $scope.authUser = response.data.username;
                }
            }, function errorCallback(response) {
                window.alert("Error");
            })
    };

    $scope.exchangeUser = function () {
        $http.defaults.headers.common.Authorization = null;
        $scope.authorized = false;
    };

    $scope.findAllProducts();
    $scope.fillCart();
});
