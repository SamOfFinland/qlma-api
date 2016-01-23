(function () {
    'use strict';
 
    var myApp = angular.module('app');
    myApp
    .constant("API", {
        "URL": "http://localhost:3002", 
    })
    .controller('LoginController', function($scope, $http, $window, API) {
        var login = this;
    
        login.doLogin = function () {
            var username = login.username;
            var password = login.password;
            var credentials = {"username": username, "password": password};
            console.log("Do Login");
            console.log(API.URL + "/login");
            console.log(credentials);
            $http.post(API.URL + '/login', credentials)
                .success(function (data, status, headers, config) {
                    $window.sessionStorage.token = data.token;
                    console.log("success");
                    console.log(data.token);
                    console.log(data);
                    console.log(status);
                    console.log(headers);
                    console.log(config);
            })
            .error(function (data, status, headers, config) {
                console.log(data);
                console.log(status);
                console.log(headers);
                console.log(config);
                // Erase the token if the user fails to log in
                delete $window.sessionStorage.token;

                // Handle login errors here
                console.log("failed");
            });

        };
    });
})();