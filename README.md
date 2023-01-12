# CharterAssignment


This Application is a Springboot Application which mainly exposes two main API's . One Api which is a POST request is used for entering the data which take in userid , price , Date and Time . Once we enter multiple POST requests we can use another API which is GET API to get the data for all the users on monthly basis (Three months , Four Months) and it will give the points of particluar user . Also their is an Intergration Test which has a dataset of Three months  data.The Test covers the functionality .


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

