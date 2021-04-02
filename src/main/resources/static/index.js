(function ($localStorage) {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider, $httpProvider) {
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
        if ($localStorage.currentUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.currentUser.token;
        }
        if (!$localStorage.CartId) {
            $http.post('http://localhost:8189/app/api/v1/cart')
                .then(function (response) {
                    $localStorage.CartId = response.data;
                })
        }
    }
})();

angular.module('app').controller('indexController', function ($scope, $http, $localStorage, $location) {

    $scope.getUser = function () {
        return $localStorage.authUser;
    }

    $scope.getCartCounter = function () {
        return $localStorage.Cart ? $localStorage.Cart.cartItems.length : 0;
    }

    $scope.userLogged = function () {
        return $localStorage.authUser != null;
    };
});