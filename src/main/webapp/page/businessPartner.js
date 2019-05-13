/**
 * Created by JELENA on 3.6.2017.
 */
app.controller('BusinessPartnerController', function ($scope, $state, $rootScope, $mdDialog, businessPartnerService) {

    $scope.page.current = 3.6;

    var businessPartners = [];

    $scope.partnershipType = {};
    $scope.partnershipType["BUYER"] = "Kupac";
    $scope.partnershipType["SUPPLIER"] = "Dobavljač";
    $scope.partnershipType["ALL"] = "Kupac/Dobavljač";

    var loadData = function () {
        businessPartnerService.read(function (response) {
            $scope.businessPartners = response.data;
            businessPartners = response.data;
        });
    };

    loadData();

    $scope.$on('search', function(event, args) {
        var searchText = args.searchText;
        if (searchText === null || searchText === "") {
            $scope.businessPartners = businessPartners;
            return;
        }
        var criteria = searchText.match(/\S+/g);
        $scope.businessPartners = [];
        for(var i = 0; i < businessPartners.length; i++) {
            for(var j = 0; j < criteria.length; j++) {
                if (businessPartners[i].id === Number(criteria) ||
                    businessPartners[i].name.toLowerCase().match(criteria[j].toLowerCase()) ||
                    String(businessPartners[i].pib).toLowerCase().match(criteria[j].toLowerCase()) ||
                    $scope.partnershipType[businessPartners[i].partnershipType.toString()].toLowerCase().match(criteria[j].toLowerCase()) ||
                    businessPartners[i].address.toLowerCase().match(criteria[j].toLowerCase()) ||
                    businessPartners[i].location.name.toLowerCase().match(criteria[j].toLowerCase()) ||
                    businessPartners[i].company.name.toLowerCase().match(criteria[j].toLowerCase())) {
                    if($scope.businessPartners.indexOf(businessPartners[i]) === -1) {
                        $scope.businessPartners.push(businessPartners[i]);
                    }
                }
            }
        }
    });

    var openForm = function (businessPartner) {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/businessPartnerForm.html',
            controller: 'BusinessPartnerFormController',
            locals: { businessPartner: businessPartner}
        }).finally(function () {
            loadData();
        });
    };

    $scope.$on('add', function() {
        openForm(null);
    });

    $scope.edit = function (businessPartner) {
        openForm(businessPartner);
    };

    $scope.delete = function (businessPartner, event) {
        event.stopPropagation();
        businessPartnerService.delete(businessPartner.id, function () {
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
