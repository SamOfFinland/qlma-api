(function () {
    'use strict';

    var myApp = angular.module('app');
    myApp
        .controller('MessagesController', MessagesController);

    MessagesController.$inject = ['$rootScope', '$location', '$http', 'API', '$window', 'ApiFactory'];
    function MessagesController($rootScope, $location, $http, API, $window, apiFactory) {
        var messages = this;
        console.log("Init MessagesController");

        var reply = "";

        messages.createMessage = function() {
            var config = { headers:  {
                'Authorization': 'Token ' + $window.sessionStorage.token,
                }
            };
            console.log(messages)

            var subject = messages.subject;
            var recipient = messages.recipient;
            var messagebody = messages.message;
            var data = { "to": parseInt(recipient, 10), "message": messagebody, "parent_id": 1};
            
            $http.post(API.URL + '/messages', data, config)
                .success(function (data, status, headers, config) {
                    $rootScope.reply = "Viesti lähetetty!";
                })
                .error(function (data, status, headers, config) {
                    $rootScope.reply = "Viesti ei lähetetty, yritäthän uudestaan!";
                });

            
        }

    }

  
})();