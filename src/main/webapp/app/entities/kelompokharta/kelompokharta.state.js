(function() {
    'use strict';

    angular
        .module('pPhBadanApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('kelompokharta', {
            parent: 'entity',
            url: '/kelompokharta?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pPhBadanApp.kelompokharta.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/kelompokharta/kelompokhartas.html',
                    controller: 'KelompokhartaController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('kelompokharta');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('kelompokharta-detail', {
            parent: 'kelompokharta',
            url: '/kelompokharta/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pPhBadanApp.kelompokharta.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/kelompokharta/kelompokharta-detail.html',
                    controller: 'KelompokhartaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('kelompokharta');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Kelompokharta', function($stateParams, Kelompokharta) {
                    return Kelompokharta.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'kelompokharta',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('kelompokharta-detail.edit', {
            parent: 'kelompokharta-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/kelompokharta/kelompokharta-dialog.html',
                    controller: 'KelompokhartaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Kelompokharta', function(Kelompokharta) {
                            return Kelompokharta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('kelompokharta.new', {
            parent: 'kelompokharta',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/kelompokharta/kelompokharta-dialog.html',
                    controller: 'KelompokhartaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nama: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('kelompokharta', null, { reload: 'kelompokharta' });
                }, function() {
                    $state.go('kelompokharta');
                });
            }]
        })
        .state('kelompokharta.edit', {
            parent: 'kelompokharta',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/kelompokharta/kelompokharta-dialog.html',
                    controller: 'KelompokhartaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Kelompokharta', function(Kelompokharta) {
                            return Kelompokharta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('kelompokharta', null, { reload: 'kelompokharta' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('kelompokharta.delete', {
            parent: 'kelompokharta',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/kelompokharta/kelompokharta-delete-dialog.html',
                    controller: 'KelompokhartaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Kelompokharta', function(Kelompokharta) {
                            return Kelompokharta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('kelompokharta', null, { reload: 'kelompokharta' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
