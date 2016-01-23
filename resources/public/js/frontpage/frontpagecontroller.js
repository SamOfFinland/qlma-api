(function () {
    'use strict';

    var myApp = angular.module('app');
    myApp
        .controller('FrontPageController', FrontPageController)
        .factory('ApiGetFactory', ApiGetFactory);

    FrontPageController.$inject = ['$rootScope', '$location','$http', '$window', 'API', 'qlmaService', 'ApiGetFactory'];
    function FrontPageController($rootScope, $location, $http, $window, API, qlmaService, apiGetFactory) {
        console.log("Init FrontPageController");
        var frontpage = this;

        var user = qlmaService.get();

        frontpage.doLogout = function(args) {
            $rootScope.$emit('doLogout', args);
        }

        frontpage.loadMessages = function() {
            console.log("Load messages");

            apiGetFactory.getlist()
              .then(function(data){
                 $rootScope.messages = data.data.messages;
               });
        }

        frontpage.getProfile = function() {
            console.log("Load profile");

            apiGetFactory.getProfile()
                .then(function(data){
                    user.firstname = data.data.message.firstname;
                    user.lastname = data.data.message.lastname;
                    $rootScope.user = user;
                });
        }

        frontpage.loadMessages();    
        frontpage.getProfile();

    }

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