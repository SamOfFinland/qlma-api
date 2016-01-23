(function () {
    'use strict';

    var myApp = angular.module('app');
    myApp
        .controller('FrontPageController', FrontPageController);

    FrontPageController.$inject = ['$rootScope', '$location','$http', '$window', 'API', 'qlmaService', 'ApiFactory'];
    function FrontPageController($rootScope, $location, $http, $window, API, qlmaService, apiFactory) {
        console.log("Init FrontPageController");
        var frontpage = this;

        var user = qlmaService.get();

        frontpage.doLogout = function(args) {
            $rootScope.$emit('doLogout', args);
        }

        frontpage.loadMessages = function() {
            console.log("Load messages");

            apiFactory.getMessages()
              .then(function(data){
                 $rootScope.messages = data.data.messages;
               });
        }

        frontpage.getProfile = function() {
            console.log("Load profile");

            apiFactory.getProfile()
                .then(function(data){
                    user.firstname = data.data.message.firstname;
                    user.lastname = data.data.message.lastname;
                    $rootScope.user = user;
                });
        }

        frontpage.loadMessages();    
        frontpage.getProfile();

    }

})();