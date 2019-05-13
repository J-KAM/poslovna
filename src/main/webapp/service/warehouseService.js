/**
 * Created by JELENA on 4.6.2017.
 */

app.service('warehouseService', function($http, $window) {

    var LOCAL_STORAGE_KEY = 'warehouse';
    var LOCAL_STORAGE_INSTANCE = $window.localStorage;

    return{
        read: function (onSuccess, onError) {
            $http.get('api/warehouses').then(onSuccess, onError);
        },

        create: function (warehouse, onSuccess, onError) {
            $http.post('api/warehouses', warehouse).then(onSuccess, onError);
        },

        update: function (warehouse, onSuccess, onError) {
            $http.patch('api/warehouses/' + warehouse.id, warehouse).then(onSuccess, onError);
        },

        delete: function (id, onSuccess, onError) {
            $http.delete('api/warehouses/' + id).then(onSuccess, onError);
        },

        generateReport:  function (warehouse, onSuccess, onError) {
            $http.post('api/warehouses/generateReport', warehouse).then(onSuccess, onError);
        },

        getActiveWarehouse: function () {
            if (LOCAL_STORAGE_INSTANCE) {
                var warehouse = LOCAL_STORAGE_INSTANCE.getItem(LOCAL_STORAGE_KEY);
                if (warehouse) {
                    return JSON.parse(warehouse);
                }
            }
        },
        setActiveWarehouse: function (warehouse) {
            if (LOCAL_STORAGE_INSTANCE) {
                LOCAL_STORAGE_INSTANCE.setItem(LOCAL_STORAGE_KEY, JSON.stringify(warehouse));
            }
        }
    }
});
