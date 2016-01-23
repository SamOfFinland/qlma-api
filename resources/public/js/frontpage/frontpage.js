(function () {
    'use strict';

    function FrontPageController($rootScope, $location, $http, $window, API, qlmaService) {
        var frontpage = this;
        console.log("Init FrontPageController");
        console.log(qlmaService.get());
    }

    FrontPageController.$inject = ['$rootScope', '$location','$http', '$window', 'API', 'qlmaService'];
    var myApp = angular.module('app');
    myApp
        .controller('FrontPageController', FrontPageController);


})();