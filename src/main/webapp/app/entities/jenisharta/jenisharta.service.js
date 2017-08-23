(function() {
    'use strict';
    angular
        .module('pPhBadanApp')
        .factory('Jenisharta', Jenisharta);

    Jenisharta.$inject = ['$resource'];

    function Jenisharta ($resource) {
        var resourceUrl =  'api/jenishartas/:id';

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
