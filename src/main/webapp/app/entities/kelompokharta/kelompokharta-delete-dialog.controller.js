(function() {
    'use strict';

    angular
        .module('pPhBadanApp')
        .controller('KelompokhartaDeleteController',KelompokhartaDeleteController);

    KelompokhartaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Kelompokharta'];

    function KelompokhartaDeleteController($uibModalInstance, entity, Kelompokharta) {
        var vm = this;

        vm.kelompokharta = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Kelompokharta.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
