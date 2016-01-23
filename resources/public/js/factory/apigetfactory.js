(function () {
    'use strict';

    var myApp = angular.module('app');
    myApp
        .factory('ApiGetFactory', ApiGetFactory);

    function ApiGetFactory($http, $window, API, $q) {
        var config = { headers:  {
            'Authorization': 'Token ' + $window.sessionStorage.token,
            }
        };

        this.getlist = function() {            
            return $http.get(API.URL + '/messages', config)
                .success(function (data, status, headers, config) {
                    return data;                
               })
                .error(function (data, status, headers, config) {
                    return data                   
                });          
        }

        this.getProfile = function() {
            return $http.get(API.URL + '/profile', config)
                .success(function (data, status, headers, config) {
                    return data;                
               })
                .error(function (data, status, headers, config) {
                    return data                   
            }); 
        }
        return this;
    };



})();