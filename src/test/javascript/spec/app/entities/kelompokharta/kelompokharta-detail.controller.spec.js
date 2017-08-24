'use strict';

describe('Controller Tests', function() {

    describe('Kelompokharta Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockKelompokharta, MockJenisharta;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockKelompokharta = jasmine.createSpy('MockKelompokharta');
            MockJenisharta = jasmine.createSpy('MockJenisharta');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Kelompokharta': MockKelompokharta,
                'Jenisharta': MockJenisharta
            };
            createController = function() {
                $injector.get('$controller')("KelompokhartaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pPhBadanApp:kelompokhartaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
