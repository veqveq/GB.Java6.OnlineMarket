(function () {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'home/home.html',
                controller: 'homeController'
            })
            .when('/products', {
                templateUrl: 'products/products.html',
                controller: 'productsController'
            })
            .when('/cart', {
                templateUrl: 'cart/cart.html',
                controller: 'cartController'
            })
            .when('/auth', {
                templateUrl: 'auth/auth.html',
                controller: 'authController'
            })
            .when('/orders', {
                templateUrl: 'orders/orders.html',
                controller: 'ordersController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http, $localStorage) {
        if ($localStorage.authUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.authUser.token;
        }
        $http({
            url: 'http://localhost:8189/app/api/v1/cart',
            method: 'POST',
            params: {
                cartId: $localStorage.CartId ? $localStorage.CartId : null,
            }
        })
            .then(function (response) {
                $localStorage.CartId = response.data;
            })
    }
})();

angular.module('app').controller('indexController', function ($scope, $http, $localStorage) {

    $scope.getUser = function () {
        return $localStorage.authUser ? $localStorage.authUser.username : null;
    }

    $scope.userLogged = function () {
        return $localStorage.authUser != null;
    };
});