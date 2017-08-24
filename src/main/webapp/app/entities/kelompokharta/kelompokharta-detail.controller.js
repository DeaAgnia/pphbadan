(function() {
    'use strict';

    angular
        .module('pPhBadanApp')
        .controller('KelompokhartaDetailController', KelompokhartaDetailController);

    KelompokhartaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Kelompokharta', 'Jenisharta'];

    function KelompokhartaDetailController($scope, $rootScope, $stateParams, previousState, entity, Kelompokharta, Jenisharta) {
        var vm = this;

        vm.kelompokharta = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pPhBadanApp:kelompokhartaUpdate', function(event, result) {
            vm.kelompokharta = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
