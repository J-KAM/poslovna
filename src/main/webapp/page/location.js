/**
 * Created by Jasmina on 29/05/2017.
 */

app.controller('LocationController', function($scope, $state, $rootScope, $mdDialog, locationService){

    $scope.page.current = 3.2;

    var locations = [];

    var loadData = function () {
        locationService.read(function (response) {
            $scope.locations = response.data;
            locations = response.data;
        });
    };

    loadData();

    $scope.$on('search', function(event, args) {
        var searchText = args.searchText;
        if (searchText === null || searchText === "") {
            $scope.locations = locations;
            return;
        }
        var criteria = searchText.match(/\S+/g);
        $scope.locations = [];
        for (var i = 0; i < locations.length; i++) {
            for (var j = 0; j < criteria.length; j++) {
                if (locations[i].id === Number(criteria) ||
                    String(locations[i].ptt).toLowerCase().match(criteria[j].toLowerCase()) ||
                    locations[i].name.toLowerCase().match(criteria[j].toLowerCase()) ||
                    criteria[j].toLowerCase().match(locations[i].name.toLowerCase())) {
                    if ($scope.locations.indexOf(locations[i]) === -1) {
                        $scope.locations.push(locations[i]);
                    }
                }
            }
        }
    });

    var openForm = function (location) {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/locationForm.html',
            controller: 'LocationFormController',
            locals: {location : location}
        }).finally(function () {
            loadData();
        });
    };

    $scope.$on('add', function () {
        openForm(null);
    });

    $scope.edit = function (location) {
        openForm(location);
    };

    $scope.delete = function (location, event) {
        event.stopPropagation();
        locationService.delete(location.id, function () {
            loadData();
        });
    };

    $scope.options = {
        rowSelection: true,
        boundaryLinks: true
    };

    $scope.query = {
        order: 'name',
        limit: 5,
        page: 1
    };
});