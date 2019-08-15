/**
 * Created by Jasmina on 05/06/2017.
 */
app.controller('DocumentFormController', function ($scope, $http, $state, $mdDialog, documentService, warehouseService, businessYearService, document, companyService, businessPartnerService) {

    var editingMode = document !== null;
    if (editingMode) {
        $scope.document = JSON.parse(JSON.stringify(document));
    }

    //Document types
    $scope.documentTypes = {};
    $scope.documentTypes["RECEIPT"] = "Primka";
    $scope.documentTypes["DISPATCH"] = "Otpremnica";
    $scope.documentTypes["INTER_WAREHOUSE_TRAFFIC"] = "Međumagacinski promet";

    //All warehouses possible for user to see
    warehouseService.read(function (response) {
        $scope.warehouses = response.data;
    });

    $scope.submit = function () {
        if ($scope.document.documentType === 'RECEIPT' || $scope.document.documentType === 'DISPATCH') {
            $scope.document.innerWarehouse = null;
        } else {
            $scope.document.businessPartner = null;
        }
        // We removed editing mode for now from the specifications, who know's, maybe it will be back some day...
        if (!editingMode) {
            documentService.create($scope.document, function () {
                $scope.close();
            });
        }else {
            documentService.update($scope.document, function () {
                $scope.close();
            });
        }
    };

    //Warehouse company
    $scope.updateWarehouse = function () {
        if ($scope.document.warehouse === null) {
            return;
        }

        warehouseService.readByCompany($scope.document.warehouse.company.id, function (response) {
            $scope.innerWarehouses = response.data;
        });
        businessPartnerService.readByCompany($scope.document.warehouse.company.id, function (response) {
            $scope.businessPartners = response.data;
        });
    };


    //All business years not closed
    businessYearService.read(function (response) {
        var openedYears = [];
        for (var i = 0; i < response.data.length; i++) {
            if (!response.data[i].closed) {
                openedYears.push(response.data[i]);
            }
        }
        $scope.businessYears = openedYears;
    });

    //All warehouses
    $scope.queryWarehouses = function (searchText) {
        if (searchText === null) {
            return $scope.warehouses;
        }
        return $scope.warehouses.filter(function(warehouse) {
            return warehouse.name.toLowerCase().match(searchText.toLowerCase());
        });
    };

    //All business partners based on selected document type and warehouse
    $scope.queryBusinessPartners = function (searchText) {
        if ($scope.document.documentType === null || searchText === null) {
            return [];
        }
        return $scope.businessPartners.filter(function (businessPartner) {
            if ($scope.document.documentType === 'RECEIPT') {
                return (businessPartner.partnershipType === 'SUPPLIER' || businessPartner.partnershipType === 'ALL') &&
                    (businessPartner.name.toLowerCase().match(searchText.toLowerCase())
                    || businessPartner.pib === parseInt(searchText));
            } else if ($scope.document.documentType === 'DISPATCH') {
                return (businessPartner.partnershipType === 'BUYER' || businessPartner.partnershipType === 'ALL') &&
                    (businessPartner.name.toLowerCase().match(searchText.toLowerCase())
                    || businessPartner.pib === parseInt(searchText));
            } else {
                return true;
            }
        });
    };

    //All business partners based on selected document type and warehouse
    $scope.queryInnerWarehouses = function (searchText) {
        if ($scope.document.documentType === null || searchText === null) {
            return [];
        }
        return $scope.innerWarehouses.filter(function (warehouse) {
            return warehouse.id !== $scope.document.warehouse.id && warehouse.name.toLowerCase().match(searchText.toLowerCase());
        });
    };

    $scope.close = function () {
        $mdDialog.hide();
    };
});