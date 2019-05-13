app.service('authenticationService', function ($http, $window, warehouseService) {

    var LOCAL_STORAGE_KEY = 'user';
    var LOCAL_STORAGE_INSTANCE = $window.localStorage;

    return {
        login: function (user, successCallback, errorCallback) {
            $http.post('/api/login', user, {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                transformRequest: function (obj) {
                    var str = [];
                    for (var p in obj)
                        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                    return str.join("&");
                }
            }).success(successCallback).error(errorCallback);
        },
        logout: function (successCallback, errorCallback) {
            var loggedInUser = this.getUser();
            if (loggedInUser) {
                $http.post("/api/logout", loggedInUser.id).success(successCallback).error(errorCallback);
            }
            warehouseService.setActiveWarehouse(null);
        },
        getUser: function () {
            if (LOCAL_STORAGE_INSTANCE) {
                var loggedInUser = LOCAL_STORAGE_INSTANCE.getItem(LOCAL_STORAGE_KEY);
                if (loggedInUser) {
                    return JSON.parse(loggedInUser);
                }
            }
        },
        setUser: function (user) {
            if (LOCAL_STORAGE_INSTANCE && user) {
                LOCAL_STORAGE_INSTANCE.setItem(LOCAL_STORAGE_KEY, JSON.stringify(user));
            }
        },
        register: function (user, successCallback, errorCallback) {
            $http.post("/api/register", user).success(successCallback).error(errorCallback);
        },
        removeUser: function () {
            LOCAL_STORAGE_INSTANCE && LOCAL_STORAGE_INSTANCE.removeItem(LOCAL_STORAGE_KEY);
        },
        isAdmin: function () {
            return this.getUser().role === 'ADMIN';
        },
        isEmployee: function () {
            return this.getUser().role === 'EMPLOYEE';
        }
    }
});