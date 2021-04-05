angular.module('app').controller('ordersController', function ($scope, $http, $localStorage) {
    const rootPath = 'http://localhost:8189/app/api/v1/';

    $scope.userLogged = function () {
        return $localStorage.authUser != null;
    }

    $scope.getOrdersHistory = function () {
        return $localStorage.OrdersHistory ? $localStorage.OrdersHistory : null;
    }

    $scope.getOrders = function () {
        $http.get(rootPath + 'orders')
            .then(function (response) {
                $localStorage.OrdersHistory = response.data;
            })
    };
    $scope.getOrders();
});