/**
 * Created by Olivera on 3.7.2017..
 */
app.service('warehouseCardService', function ($http) {
    return {
        readAll: function(onSuccess, onError){
            $http.get('/api/warehouseCards').then(onSuccess, onError);
        },
        read: function(warehouse, onSuccess, onError) {
            $http.get('/api/warehouseCards/' + warehouse.id).then(onSuccess, onError);
        },
        generateReport: function(data, onSuccess, onError){
            $http.post('/api/warehouseCards/generateReport', data).then(onSuccess, onError);
        },
        level: function (warehouseCard, onSuccess, onError) {
            $http.post('/api/warehouseCards/level', warehouseCard).then(onSuccess, onError);
        },
        getCardForWare: function (ware, onSuccess, onError) {
            $http.post('/api/warehouseCards/card', ware).then(onSuccess, onError);
        }
    }
});
