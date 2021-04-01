angular.module('app').controller('ordersController', function ($scope, $http, $localStorage) {
    const rootPath = 'http://localhost:8189/app';

    $scope.userLogged = function () {
        return $localStorage.authUser != null;
    }

    $scope.getOrdersHistory = function () {
        return $localStorage.OrdersHistory ? $localStorage.OrdersHistory : null;
    }
    // $scope.getOrdersHistory();
});