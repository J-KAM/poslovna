/**
 * Created by Jasmina on 05/06/2017.
 */
app.service('documentUnitService', function ($http) {
    return{
        read: function (onSuccess, onError) {
            $http.get('api/documentUnits').then(onSuccess, onError);
        },
        read: function (documentId, onSuccess, onError) {
            $http.get('api/documentUnits/documents/' + documentId).then(onSuccess, onError);
        },
        create: function (documentUnit, onSuccess, onError) {
            $http.post('api/documentUnits', documentUnit).then(onSuccess, onError);
        },
        update: function (documentUnit, onSuccess, onError) {
            $http.patch('api/documentUnits/' + documentUnit.id, documentUnit).then(onSuccess, onError);
        },
        delete: function (id, onSuccess, onError) {
            $http.delete('api/documentUnits/' + id).then(onSuccess, onError);
        }
    }
});
