(function () {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngCookies'])
        .config(config)
        .run(run)
        .factory('qlmaService', function() {
            var savedData = {}
            function set(data) {
                savedData = data;
            }
            function get() {
                return savedData;
            }

            return {
                set: set,
                get: get
            }
        })
        .constant("API", {
            "URL": "http://localhost:3000", 
        });

    config.$inject = ['$routeProvider', '$locationProvider'];
    function config($routeProvider, $locationProvider) {
        console.log("Config");
        $routeProvider
           
            .when('/', {
                controller: 'LoginController',
                templateUrl: 'views/login.view.html',
                controllerAs: 'login'
            })

            .when('/messages', {
                controller: 'MessagesController',
                templateUrl: 'views/messages.view.html',
                controllerAs: 'messages'
            })

            .when('/frontpage', {
                controller: 'FrontPageController',
                templateUrl: 'views/frontpage.view.html',
                controllerAs: 'frontpage'
            })

            .otherwise({ redirectTo: '/' });
    }

    run.$inject = ['$rootScope', '$location', '$window', '$http', 'qlmaService'];
    function run($rootScope, $location, $window, $http, qlmaService) {
        console.log("Run");

        $rootScope.$on('$locationChangeStart', function (event, next, current) {

        });       
    }

})();