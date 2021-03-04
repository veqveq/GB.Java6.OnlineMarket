angular.module('app').controller('authController', function ($scope, $http, $localStorage, $location) {
    const rootPath = 'http://localhost:8189/app';
    const apiPath = rootPath + '/api/v1';


    $scope.tryToAuth = function () {
        $http.post(rootPath + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $localStorage.authUser = response.data.username;
                    $scope.authUser = response.data.username;
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $scope.user.username = null;
                    $scope.user.password = null;
                    $location.path('/orders');
                    $scope.getOrdersHistory();
                    $scope.fillCart();
                }
            }, function errorCallback(response) {
                window.alert("Ошибка авторизации. Неверный логин/пароль");
            });
    };

    $scope.getOrdersHistory = function () {
        $http.get(apiPath + '/orders')
            .then(function (response) {
                $localStorage.OrdersHistory = response.data;
            });
    };

    $scope.fillCart = function () {
        $http.get(apiPath + '/cart/')
            .then(function (response) {
                console.log(response.data)
                $localStorage.Cart = response.data;
            })
    };

    $scope.doRegistration = function () {
        $http({
            url: rootPath + '/reg',
            method: 'POST',
            params: {
                username: $scope.user ? $scope.user.username : null,
                password: $scope.user ? $scope.user.password : null,
            },
        }).then(function successCallback(response) {
            $scope.tryToAuth();
        }, function errorCallback(response) {
            if (response.data.status == 409) {
                window.alert("Пользователь с ником " + $scope.user.username + " существует")
            } else if (response.data.status == 400) {
                window.alert("Ошибка заполнения формы регистрации!")
            } else {
                window.alert("Ошибка регистрации!");
            }
            $scope.user.username = null;
            $scope.user.password = null;
        })
    };

    $scope.logout = function () {
        $http.defaults.headers.common.Authorization = null;
        delete $localStorage.authUser;
        delete $localStorage.Cart;
        delete $localStorage.OrdersHistory;
    };

});