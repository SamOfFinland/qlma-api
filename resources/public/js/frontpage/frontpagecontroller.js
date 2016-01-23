(function () {
    'use strict';

    var myApp = angular.module('app');
    myApp
        .controller('FrontPageController', FrontPageController)
        .factory('GetMessagesFactory', GetMessagesFactory);

    FrontPageController.$inject = ['$rootScope', '$location','$http', '$window', 'API', 'qlmaService', 'GetMessagesFactory'];
    function FrontPageController($rootScope, $location, $http, $window, API, qlmaService, messagesFactory) {
        console.log("Init FrontPageController");
        var frontpage = this;

        var user = qlmaService.get();
        frontpage.user = {}
        frontpage.user.firstname = user.firstname;
        frontpage.user.lastname = user.lastname;

        frontpage.doLogout = function(args) {
            $rootScope.$emit('doLogout', args);
        }

        frontpage.loadMessages = function() {
            console.log("Load messages");

            messagesFactory.getlist()
              .then(function(data){
                 $rootScope.messages = data.data.messages;
               });

        }

        frontpage.loadMessages();        
    }

    function GetMessagesFactory($http, $window, API, $q) {
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
        return this;
    };

})();