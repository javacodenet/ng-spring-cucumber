(function (angular) {
    function employeeController($scope, employeeService, toaster) {
        var _this = this;

        _this.getEmployees = function () {
            employeeService.getEmployees().then(function (response) {
                _this.employees = response ? response : [];
                if(!_this.employees || _this.employees.length == 0) {
                    toaster.pop('info', null, 'No Employees Present');
                }
            });
        };

        _this.saveEmployee = function (employee) {
            employeeService.saveEmployee(employee).then(function (success) {
                if(success) {
                    _this.isEmployeeSaved = true;
                    _this.getEmployees();
                    toaster.pop('success', null, employee.name + ' saved successfully');
                }
            });
        };

        _this.updateEmployee = function (employee) {
            employeeService.updateEmployee(employee).then(function (success) {
                if(success) {
                    _this.isEmployeeUpdated = true;
                    employee.isEditMode = false;
                    toaster.pop('success', null, employee.name + ' updated successfully');
                }
            });
        };

        _this.deleteEmployee = function (employee) {
            employeeService.deleteEmployee(employee).then(function (success) {
                if(success) {
                    _this.getEmployees();
                    toaster.pop('success', null, employee.name + ' deleted successfully');
                }
            });
        };
    }

    employeeController.$inject = ['$scope', 'employeeService', 'toaster'];
    angular.module("employeeApp").controller("employeeController", employeeController);
}(angular));