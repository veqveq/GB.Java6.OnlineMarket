angular.module('app').controller('cartController', function ($scope, $http, $localStorage) {
    const apiPath = 'http://localhost:8189/app/api/v1/';

    $scope.fillCart = function () {
        $http({
            url: apiPath + 'cart/get',
            method: 'POST',
            params: {
                cartId: $localStorage.CartId ? $localStorage.CartId : null,
            }
        })
            .then(function successCallback(response) {
                    $localStorage.Cart = response.data;
                }, function errorCallback(response) {
                    if (response.data.status = 404) {
                        delete $localStorage.Cart;
                        delete $localStorage.authUser;
                    }
                }
            )
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

    $scope.getCartCounter = function () {
        return $localStorage.Cart ? $localStorage.Cart.cartItems.length : 0;
    }

    $scope.fillCart();
});