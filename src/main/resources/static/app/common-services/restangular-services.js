(function (angular) {
    function restangularService(Restangular) {
        return Restangular.withConfig(function (RestangularConfigurer) {
            var newBaseUrl = window.location.href.substring(0, window.location.href);
            RestangularConfigurer.setBaseUrl(newBaseUrl);
        });
    }

    angular.module("employeeApp")
        .factory('RestangularService', ['Restangular', restangularService]);

}(angular));