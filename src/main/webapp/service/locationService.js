/**
 * Created by Jasmina on 25/05/2017.
 */
app.service('locationService', function ($http) {
    return{
        read: function (onSuccess, onError) {
            $http.get('api/locations').then(onSuccess, onError);
        },

        create: function (location, onSuccess, onError) {
            $http.post('api/locations', location).then(onSuccess, onError);
        },

        update: function (location, onSuccess, onError) {
            $http.patch('api/locations/' + location.id, location).then(onSuccess, onError);
        },

        delete: function (id, onSuccess, onError) {
            $http.delete('api/locations/' + id).then(onSuccess, onError);
        }
    }
});