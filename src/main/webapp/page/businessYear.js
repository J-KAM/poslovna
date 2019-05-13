/**
 * Created by JELENA on 3.6.2017.
 */

app.controller('BusinessYearController', function ($scope, $state, $rootScope, $mdDialog, businessYearService) {

    $scope.page.current = 3.7;

    var businessYears = [];

    var loadData = function () {
        businessYearService.read(function (response) {
            $scope.businessYears = response.data;
            businessYears = response.data;
        });
    };

    loadData();

    $scope.$on('search', function(event, args) {
        var searchText = args.searchText;
        if (searchText === null || searchText === "") {
            $scope.businessYears = businessYears;
            return;
        }
        var criteria = searchText.match(/\S+/g);
        $scope.businessYears = [];
        for (var i = 0; i < businessYears.length; i++) {
            for (var j = 0; j < criteria.length; j++) {
                if (businessYears[i].id === Number(criteria) ||
                    String(businessYears[i].year).toLowerCase().match(criteria[j].toLowerCase()) ||
                    (businessYears[i].closed && (criteria[j].toLowerCase().match("d") || criteria[j].toLowerCase().match("da"))) ||
                    (!businessYears[i].closed && (criteria[j].toLowerCase().match("n") || criteria[j].toLowerCase().match("ne")))) {
                    if($scope.businessYears.indexOf(businessYears[i]) === -1) {
                        $scope.businessYears.push(businessYears[i]);
                    }
                }
            }
        }
    });


    var openForm = function (businessYear) {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/businessYearForm.html',
            controller: 'BusinessYearFormController',
            locals: { businessYear: businessYear}
        }).finally(function () {
            loadData();
        });
    };

    $scope.$on('add', function() {
        openForm(null);
    });

    $scope.edit = function (businessYear) {
        openForm(businessYear);
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

