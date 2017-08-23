(function() {
    'use strict';

    angular
        .module('pPhBadanApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('jenisharta', {
            parent: 'entity',
            url: '/jenisharta?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pPhBadanApp.jenisharta.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/jenisharta/jenishartas.html',
                    controller: 'JenishartaController',
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
                    $translatePartialLoader.addPart('jenisharta');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('jenisharta-detail', {
            parent: 'jenisharta',
            url: '/jenisharta/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pPhBadanApp.jenisharta.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/jenisharta/jenisharta-detail.html',
                    controller: 'JenishartaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('jenisharta');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Jenisharta', function($stateParams, Jenisharta) {
                    return Jenisharta.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'jenisharta',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('jenisharta-detail.edit', {
            parent: 'jenisharta-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/jenisharta/jenisharta-dialog.html',
                    controller: 'JenishartaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Jenisharta', function(Jenisharta) {
                            return Jenisharta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('jenisharta.new', {
            parent: 'jenisharta',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/jenisharta/jenisharta-dialog.html',
                    controller: 'JenishartaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                jenis: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('jenisharta', null, { reload: 'jenisharta' });
                }, function() {
                    $state.go('jenisharta');
                });
            }]
        })
        .state('jenisharta.edit', {
            parent: 'jenisharta',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/jenisharta/jenisharta-dialog.html',
                    controller: 'JenishartaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Jenisharta', function(Jenisharta) {
                            return Jenisharta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('jenisharta', null, { reload: 'jenisharta' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('jenisharta.delete', {
            parent: 'jenisharta',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/jenisharta/jenisharta-delete-dialog.html',
                    controller: 'JenishartaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Jenisharta', function(Jenisharta) {
                            return Jenisharta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('jenisharta', null, { reload: 'jenisharta' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
