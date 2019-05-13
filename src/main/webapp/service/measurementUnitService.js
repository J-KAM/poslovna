/**
 * Created by Alex on 5/21/17.
 */
app.service('measurementUnitService', function($http){
    return {
        read: function(onSuccess, onError){
            $http.get('/api/measurementUnits').then(onSuccess, onError);
        },
        create: function(measurementUnit, onSuccess, onError){
            $http.post('/api/measurementUnits', measurementUnit).then(onSuccess, onError);
        },
        update: function(measurementUnit, onSuccess, onError){
            $http.patch('/api/measurementUnits/' + measurementUnit.id, measurementUnit).then(onSuccess, onError);
        },
        delete: function (id, onSuccess, onError) {
            $http.delete('/api/measurementUnits/' + id).then(onSuccess, onError);
        }
    }
});