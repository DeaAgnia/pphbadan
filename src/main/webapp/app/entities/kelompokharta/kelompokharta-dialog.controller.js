(function() {
    'use strict';

    angular
        .module('pPhBadanApp')
        .controller('KelompokhartaDialogController', KelompokhartaDialogController);

    KelompokhartaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Kelompokharta', 'Jenisharta'];

    function KelompokhartaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Kelompokharta, Jenisharta) {
        var vm = this;

        vm.kelompokharta = entity;
        vm.clear = clear;
        vm.save = save;
        vm.jenishartas = Jenisharta.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.kelompokharta.id !== null) {
                Kelompokharta.update(vm.kelompokharta, onSaveSuccess, onSaveError);
            } else {
                Kelompokharta.save(vm.kelompokharta, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pPhBadanApp:kelompokhartaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
