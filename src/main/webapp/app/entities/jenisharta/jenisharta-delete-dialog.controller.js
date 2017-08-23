(function() {
    'use strict';

    angular
        .module('pPhBadanApp')
        .controller('JenishartaDeleteController',JenishartaDeleteController);

    JenishartaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Jenisharta'];

    function JenishartaDeleteController($uibModalInstance, entity, Jenisharta) {
        var vm = this;

        vm.jenisharta = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Jenisharta.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
