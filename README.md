# CharterAssignment


This Application is a Springboot Application 







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

