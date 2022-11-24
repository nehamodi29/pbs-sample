Feature: Testing the Workday System API's

Scenario: Get Absence Management details
Given I create a new request with https://localhost:8095/api/v1/ service
And I add the absenceManagement endpoint to the service
And I send the GET request to the service
Then I get the 200 response code

Scenario: Get Compensation Grade Details By Using Id 
Given I create a new request with https://localhost:8095/api/v1/ service
And I add the compensation/compensationGradeById?id=111 endpoint to the service
And I send the GET request to the service
Then I get the 200 response code

Scenario: Get Worker details
Given I create a new request with https://localhost:8095/api/v1/ service
And I add the humanResources/workers endpoint to the service
And I send the GET request to the service
Then I get the 200 response code

Scenario: Get Job Profile details
Given I create a new request with https://localhost:8095/api/v1/ service
And I add the humanResources/jobProfile endpoint to the service
And I send the GET request to the service
Then I get the 200 response code

Scenario: Get Locations details
Given I create a new request with https://localhost:8095/api/v1/ service
And I add the humanResources/locations endpoint to the service
And I send the GET request to the service
Then I get the 200 response code

Scenario: Get Company Payment Dates details
Given I create a new request with https://localhost:8095/api/v1/ service
And I add the payroll/companyPaymentDates endpoint to the service
And I send the GET request to the service
Then I get the 200 response code

Scenario: Get Advanced Lookup Tables details
Given I create a new request with https://localhost:8095/api/v1/ service
And I add the payroll/advancedLookupTables endpoint to the service
And I send the GET request to the service
Then I get the 200 response code



