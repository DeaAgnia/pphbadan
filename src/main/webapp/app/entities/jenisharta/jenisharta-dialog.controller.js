(function() {
    'use strict';

    angular
        .module('pPhBadanApp')
        .controller('JenishartaDialogController', JenishartaDialogController);

    JenishartaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Jenisharta'];

    function JenishartaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Jenisharta) {
        var vm = this;

        vm.jenisharta = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.jenisharta.id !== null) {
                Jenisharta.update(vm.jenisharta, onSaveSuccess, onSaveError);
            } else {
                Jenisharta.save(vm.jenisharta, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pPhBadanApp:jenishartaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
