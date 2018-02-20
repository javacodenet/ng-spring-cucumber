(function (angular) {
    function employeeService(RestangularService) {
        return {
            getEmployees: getEmployees,
            saveEmployee: saveEmployee,
            updateEmployee: updateEmployee,
            deleteEmployee: deleteEmployee
        };

        function getEmployees() {
            return RestangularService.one('employee').get();
        }

        function saveEmployee(employee) {
            return RestangularService.all('employee').post(employee);
        }

        function updateEmployee(employee) {
            return RestangularService.one('employee').customPUT(employee);
        }

        function deleteEmployee(employee) {
            return RestangularService.one('employee').one(employee.id).remove();
        }
    }

    employeeService.$inject = ['RestangularService'];
    angular.module("employeeApp").factory("employeeService", employeeService);
}(angular));