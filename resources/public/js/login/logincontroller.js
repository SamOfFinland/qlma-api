(function () {
    'use strict';
     

    function LoginController($rootScope, $location, $http, $window, API, qlmaService) {
        var login = this;
        
        $rootScope.$on('doLogout', function(event, args) {
            login.doLogout();
        });
        

        login.doLogin = function () {
            var username = login.username;
            var password = login.password;
            var credentials = {"username": username, "password": password};

            $http.post(API.URL + '/login', credentials)
                .success(function (data, status, headers, config) {
                    $window.sessionStorage.token = data.token;
                    var user = {
                        firstname: "not impl",
                        lastname: "yet"
                    }
                    qlmaService.set(
                        user                       
                    );
                    $location.path("/frontpage")

                })
                .error(function (data, status, headers, config) {
                    // Erase the token if the user fails to log in
                    delete $window.sessionStorage.token;
                });

        };

        login.doLogout = function () {
            delete $window.sessionStorage.token;
            $location.path("/login")
        }
    }

    var myApp = angular.module('app');
    myApp
        .controller("LoginController", LoginController);

    LoginController.$inject = ['$rootScope', '$location', '$http', '$window', 'API', 'qlmaService'];

})();