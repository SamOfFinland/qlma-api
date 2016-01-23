(function () {
    'use strict';

    var myApp = angular.module('app');
    myApp
        .controller('IndexController', IndexController);

    IndexController.$inject = ['$rootScope', '$location'];
    function IndexController($rootScope, $location) {
        var index = this;
        index.test = "TEST";
        console.log("Init IndexController");

        index.goToLogin = function($location) {
	    	console.log("goToLogin");
	    	$location.path( "/login" );
	    }

    }

  
})();