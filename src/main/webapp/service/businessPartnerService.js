/**
 * Created by JELENA on 3.6.2017.
 */

app.service('businessPartnerService', function($http) {
    return {
        read: function (onSuccess, onError) {
            $http.get('api/businessPartners').then(onSuccess, onError);
        },
        create: function(businessPartner, onSuccess, onError) {
            $http.post('/api/businessPartners', businessPartner).then(onSuccess, onError);
        },
        update: function (businessPartner, onSuccess, onError) {
            $http.patch('/api/businessPartners/' + businessPartner.id, businessPartner).then(onSuccess, onError);
        },
        delete: function (id, onSuccess, onError) {
            $http.delete('/api/businessPartners/' + id).then(onSuccess, onError);
        }
    }
});
