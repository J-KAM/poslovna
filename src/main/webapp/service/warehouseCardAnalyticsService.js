/**
 * Created by JELENA on 3.7.2017.
 */

app.service('warehouseCardAnalyticsService', function ($http) {
    return{
        read: function (warehouseCardId, onSuccess, onError) {
            $http.get('/api/warehouseCardAnalytics/warehouseCards/' + warehouseCardId).then(onSuccess, onError);
        }

    }
});
