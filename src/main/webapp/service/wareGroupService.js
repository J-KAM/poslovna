/**
 * Created by Jasmina on 30/05/2017.
 */
app.service('wareGroupService', function ($http) {
    return{
        read: function (onSuccess, onError) {
            $http.get('api/wareGroups').then(onSuccess, onError);
        },

        create: function (wareGroup, onSuccess, onError) {
            $http.post('api/wareGroups', wareGroup).then(onSuccess, onError);
        },

        update: function (wareGroup, onSuccess, onError) {
            $http.patch('api/wareGroups/' + wareGroup.id, wareGroup).then(onSuccess, onError);
        },

        delete: function (id, onSucces, onError) {
            $http.delete('api/wareGroups/' + id).then(onSucces, onError);
        }
    }
});