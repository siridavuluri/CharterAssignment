# CharterAssignment

This is a Springboot Application which mainly exposes two API's (One POST API and One GET API). POST request is used for entering the data which takes User Id, Amount, Date and Time as a payload. Once we enter multiple POST requests to insert the data, we can use GET API to retrieve the data for all the users on monthly basis (Three months, Four months etc.,) and it will give the points of the given user. Also there is an Intergration Test which has a dataset of Three months data. This Test covers the functionality.

Curl to enter data::

curl --location --request POST 'localhost:8080/points' \
--header 'Content-Type: application/json' \
--data-raw '{
    "userId": "2",
    "payment": {
        "currency": "$",
        "amount": 53.0
    },
    "dateTime": "02-12-2022 02:02:10"
}'

Curl to get the result of all users::

curl --location --request GET 'localhost:8080/points/last/3'

