/**
 * Created by Alex on 5/30/17.
 */
app.service('companyService', function($http){
    return {
        read: function(onSuccess, onError){
            $http.get('/api/companies').then(onSuccess, onError);
        },
        readById: function(id, onSuccess, onError){
            $http.get('/api/companies/' + id).then(onSuccess, onError);
        },
        create: function(company, onSuccess, onError){
            $http.post('/api/companies', company).then(onSuccess, onError);
        },
        update: function(company, onSuccess, onError){
            $http.patch('/api/companies/' + company.id, company).then(onSuccess, onError);
        },
        delete: function (id, onSuccess, onError) {
            $http.delete('/api/companies/' + id).then(onSuccess, onError);
        }
    }
});