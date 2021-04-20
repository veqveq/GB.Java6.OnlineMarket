angular.module('app').controller('authController', function ($scope, $http, $localStorage, $location) {
    const rootPath = 'http://localhost:8189/app';

    $scope.tryToAuth = function () {
        $http.post(rootPath + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $localStorage.authUser = response.data;
                    $scope.authUser = response.data.username;
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $scope.user.username = null;
                    $scope.user.password = null;
                    $location.path('/orders');
                }
            }, function errorCallback() {
                window.alert("Ошибка авторизации. Неверный логин/пароль");
            });
    };

    $scope.doRegistration = function () {
        $http({
            url: rootPath + '/reg',
            method: 'POST',
            params: {
                username: $scope.user ? $scope.user.username : null,
                password: $scope.user ? $scope.user.password : null,
            },
        }).then(function successCallback() {
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
        delete $localStorage.CartId;
        $location.path('/');
        window.location.reload();
    };
});