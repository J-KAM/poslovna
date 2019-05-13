/**
 * Created by Jasmina on 30/05/2017.
 */
app.controller('WareGroupController', function ($scope, $state, $rootScope, $mdDialog, wareGroupService) {

    $scope.page.current = 3.4;

    var wareGroups = [];

    var loadData = function () {
        wareGroupService.read(function (response) {
            $scope.wareGroups = response.data;
            wareGroups = response.data;
        });
    };

    loadData();

    $scope.$on('search', function(event, args) {
        var searchText = args.searchText;
        if (searchText === null || searchText === "") {
            $scope.wareGroups = wareGroups;
            return;
        }
        var criteria = searchText.match(/\S+/g);
        $scope.wareGroups = [];
        for (var i = 0; i < wareGroups.length; i++) {
            for (var j = 0; j < criteria.length; j++) {
                if (wareGroups[i].id === Number(criteria) ||
                    wareGroups[i].name.toLowerCase().match(criteria[j].toLowerCase()) ||
                    wareGroups[i].company.name.toLowerCase().match(criteria[j].toLowerCase())) {
                    if ($scope.wareGroups.indexOf(wareGroups[i]) === -1) {
                        $scope.wareGroups.push(wareGroups[i]);
                    }
                }
            }
        }
    });

    var openForm = function (wareGroup) {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/wareGroupForm.html',
            controller: 'WareGroupFormController',
            locals: {wareGroup: wareGroup}
        }).finally(function () {
            loadData();
        });
    };
    
    $scope.$on('add', function () {
        openForm(null);
    });
    
    $scope.edit = function (wareGroup) {
        openForm(wareGroup);
    };
    
    $scope.delete = function (wareGroup, event) {
        event.stopPropagation();
        wareGroupService.delete(wareGroup.id, function () {
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