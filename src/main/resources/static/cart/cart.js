angular.module('app').controller('cartController', function ($scope, $http, $localStorage, $location) {
    const rootPath = 'http://localhost:8189/app';
    const apiPath = rootPath + '/api/v1';
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
        $http({
            url: apiPath + '/orders',
            method: 'POST',
            params: {
                address: $scope.order ? $scope.order.address : null,
            },
        })
            .then(function successCallback(response) {
                window.alert("Заказ успешно оформлен!")
                $scope.fillCart();
                $scope.getOrdersHistory();
                $location.path('/orders');
            }, function errorCallback(response) {
                window.alert("Ошибка оформления заказа");
            })
    };

    $scope.fillCart = function () {
        $http.get(apiPath + '/cart/')
            .then(function (response) {
                console.log(response.data)
                $localStorage.Cart = response.data;
            })
    };

    $scope.addProductInCart = function (id) {
        $http.get(apiPath + '/cart/add/' + id)
            .then(function (response) {
                $scope.fillCart();
            })
    };

    $scope.getCart = function () {
        return $localStorage.Cart;
    }

    $scope.getOrdersHistory = function () {
        $http.get(apiPath + '/orders')
            .then(function (response) {
                $localStorage.OrdersHistory = response.data;
            });
    };

});