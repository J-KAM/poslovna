/**
 * Created by Jasmina on 05/06/2017.
 */
app.service('documentService', function ($http) {
    return {
        read: function(onSuccess, onError){
            $http.get('/api/documents').then(onSuccess, onError);
        },
        readByWarehouse: function(warehouse, onSuccess, onError){
            $http.get('/api/documents/warehouse/' + warehouse.id).then(onSuccess, onError);
        },
        create: function(document, onSuccess, onError){
            $http.post('/api/documents', document).then(onSuccess, onError);
        },
        update: function(document, onSuccess, onError){
            $http.patch('/api/documents/' + document.id, document).then(onSuccess, onError);
        },
        delete: function (id, onSuccess, onError) {
            $http.delete('/api/documents/' + id).then(onSuccess, onError);
        },
        book: function (document, onSuccess, onError) {
            $http.post('/api/documents/book/' + document.id, document).then(onSuccess, onError);
        }
    }
});