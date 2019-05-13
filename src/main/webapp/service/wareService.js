/**
 * Created by JELENA on 3.6.2017.
 */

app.service('wareService', function($http) {
   return {
       read: function(onSuccess, onError) {
           $http.get('/api/wares').then(onSuccess, onError);
       },
       create: function(ware, onSuccess, onError) {
           $http.post('/api/wares', ware).then(onSuccess, onError);
       },
       update: function(ware, onSuccess, onError) {
           $http.patch('/api/wares/' + ware.id, ware).then(onSuccess, onError);
       },
       delete: function(id, onSuccess, onError) {
           $http.delete('/api/wares/' + id).then(onSuccess, onError);
       }
   }
});