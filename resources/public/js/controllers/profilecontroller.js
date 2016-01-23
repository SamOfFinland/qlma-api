(function () {
    'use strict';

    var myApp = angular.module('app');
    myApp
        .controller('ProfileController', ProfileController);

    ProfileController.$inject = ['$rootScope', '$location','$http', '$window', 'API', 'qlmaService', 'ApiFactory'];
    function ProfileController($rootScope, $location, $http, $window, API, qlmaService, apiFactory) {
        console.log("Init ProfileController");
        var profile = this;
        var user = qlmaService.get();

        profile.getProfile = function() {
            console.log("Load profile");

            apiFactory.getProfile()
                .then(function(data){
                    user.firstname = data.data.message.firstname;
                    user.lastname = data.data.message.lastname;
                    $rootScope.user = user;
                });
        }

        profile.getProfile();

    }

})();
