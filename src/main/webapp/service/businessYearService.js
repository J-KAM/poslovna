/**
 * Created by JELENA on 3.6.2017.
 */

app.service('businessYearService', function ($http) {
    return {
        read: function (onSuccess, onError) {
            $http.get('/api/businessYears').then(onSuccess, onError);
        },
        create: function (businessYear, onSuccess, onError) {
            $http.post('/api/businessYears', businessYear).then(onSuccess, onError);
        },
        update: function (businessYear, onSuccess, onError) {
            $http.patch('/api/businessYears/' + businessYear.id, businessYear).then(onSuccess, onError);
        },
        delete: function (id, onSuccess, onError) {
            $http.delete('/api/businessYears/' + id).then(onSuccess, onError);
        }
    }
});