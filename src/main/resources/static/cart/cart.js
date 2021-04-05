angular.module('app').controller('cartController', function ($scope, $http, $localStorage, $location) {
    const rootPath = 'http://localhost:8189/app';
    const apiPath = rootPath + '/api/v1/';

    $scope.fillCart = function () {
        $http({
            url: apiPath + 'cart/get',
            method: 'POST',
            params: {
                cartId: $localStorage.CartId,
            }
        })
            .then(function (response) {
                $localStorage.Cart = response.data;
            })
    }

    $scope.getCart = function () {
        return $localStorage.Cart;
    }

    $scope.deleteProductByCart = function (id) {
        $http({
            url: apiPath + 'cart/del',
            method: 'POST',
            params: {
                productId: id,
                cartId: $localStorage.CartId,
            }
        })
            .then(function () {
                $scope.fillCart();
            });
    };

    $scope.decCountProductInCart = function (id) {
        $http({
            url: apiPath + 'cart/dec',
            method: 'POST',
            params: {
                productId: id,
                cartId: $localStorage.CartId,
            }
        })
            .then(function () {
                $scope.fillCart();
            });
    };

    $scope.addProductInCart = function (id) {
        $http({
            url: apiPath + 'cart/add',
            method: 'POST',
            params: {
                productId: id,
                cartId: $localStorage.CartId,
            }
        })
            .then(function () {
                $scope.fillCart();
            })
    };

    $scope.cleanCart = function () {
        $http({
            url: apiPath + 'cart/clean',
            method: 'POST',
            params: {
                cartId: $localStorage.CartId,
            }
        })
            .then(function () {
                $scope.fillCart();
            })
    };

    $scope.makeOrder = function () {
        $http({
            url: apiPath + 'orders',
            method: 'POST',
            params: {
                address: $scope.order ? $scope.order.address : null,
                cartId: $localStorage.CartId,
            },
        })
            .then(function successCallback() {
                window.alert("Заказ успешно оформлен!")
                $scope.fillCart();
                $location.path('/orders');
            }, function errorCallback() {
                window.alert("Ошибка оформления заказа");
            })
    };
});