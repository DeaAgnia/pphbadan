(function() {
    'use strict';

    angular
        .module('pPhBadanApp')
        .controller('JenishartaDetailController', JenishartaDetailController);

    JenishartaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Jenisharta'];

    function JenishartaDetailController($scope, $rootScope, $stateParams, previousState, entity, Jenisharta) {
        var vm = this;

        vm.jenisharta = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pPhBadanApp:jenishartaUpdate', function(event, result) {
            vm.jenisharta = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
