(function () {
    'use strict';

    var myApp = angular.module('app');
    myApp
        .controller('MessagesController', MessagesController);

    MessagesController.$inject = ['$rootScope', '$location', '$http', '$window', 'API', 'qlmaService'];
    function MessagesController($rootScope, $location, $http, $window, API, qlmaService) {
        var messages = this;
        console.log("Init MessagesController");


        messages.createMessage = function() {
            
        }

    }

  
})();