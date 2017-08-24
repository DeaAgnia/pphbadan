(function() {
    'use strict';
    angular
        .module('pPhBadanApp')
        .factory('Kelompokharta', Kelompokharta);

    Kelompokharta.$inject = ['$resource'];

    function Kelompokharta ($resource) {
        var resourceUrl =  'api/kelompokhartas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
