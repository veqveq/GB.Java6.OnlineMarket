angular.module('app').controller('ordersController', function ($scope, $location, $http, $localStorage) {
    const apiPath = 'http://localhost:8189/app/api/v1/orders';

    $scope.makeOrder = function () {
        $http({
            url: apiPath,
            method: 'POST',
            params: {
                address: $scope.order ? $scope.order.address : null,
                cartId: $localStorage.CartId,
            },
        })
            .then(function successCallback() {
                window.alert("Заказ успешно оформлен!")
                delete $localStorage.Cart;
                $scope.getOrders();
                $location.path('/orders');
            }, function errorCallback() {
                window.alert("Ошибка оформления заказа");
            })
    };

    $scope.getOrdersHistory = function () {
        return $localStorage.OrdersHistory ? $localStorage.OrdersHistory : null;
    }

    $scope.getOrders = function () {
        $http.get(apiPath)
            .then(function (response) {
                $localStorage.OrdersHistory = response.data;
            })
    };
    if ($localStorage.authUser) $scope.getOrders();
});