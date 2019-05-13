app.service('userService', function($http){
    return {
        create: function(employee, onSuccess, onError){
            $http.post('/api/users/employees', employee).then(onSuccess, onError);
        },
        read: function (onSuccess, onError) {
            $http.get('/api/users/employees').then(onSuccess, onError);
        }
    }
});