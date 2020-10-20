<p align="center">
  <h3 align="center">basket-service</h3>
  <p align="center">
    A simple REST API for a Shopping Basket
  </p>
</p>

<!-- TABLE OF CONTENTS -->
## Table of Contents

* [About the Project](#about-the-project)
  * [Built With](#built-with)
* [Getting Started](#getting-started)
  * [Prerequisites](#prerequisites)
  * [Installation](#installation)
* [Usage](#usage)

<!-- ABOUT THE PROJECT -->
## About The Project
A simple REST API for a Shopping Basket. All data are persisted in H2 (in-memory database).
1. Create a basket for the customer
2. Add and remove items inside the basket
3. A list of all the items of a basket
4.  Get the total price on all the baskets of the customer

### Built With

* Java 8
* Spring (Boot, Web, Data JPA, Validation)
* Gradle - dependency

<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these simple steps.

### Prerequisites

* Java 8
* Gradle

### Local Execution

1. Build the project using Gradle wrapper
```sh
./gradlew clean build
```
2. Run the jar file
```sh
java -jar build/libs/basket-service-0.0.1.jar
```

## Usage

#### Create a Basket
```
curl --location --request POST 'http://localhost:8080/baskets' \
--header 'Content-Type: application/json' \
--data-raw '{
	"user": "test"
}'
```
##### Response
A unique UUID will be returned with HTTP status `201 - Created`

#### Add item to an existing basket
```
curl --location --request PUT 'http://localhost:8080/baskets/' \
--header 'Content-Type: application/json' \
--data-raw '{
	"basketId": "8b779ed8-8081-46fa-af23-1bed1856d266",
	"item" : {
		"itemId": "item-2",
		"name": "more meat",
		"price": 10.20,
		"quantity": 5
	},
	"action" : "ADD"
}'
```
##### Response
`Basket Updated!` with HTTP status `200 - OK`

#### Remove an existing item from an existing basket
```
curl --location --request PUT 'http://localhost:8080/baskets/' \
--header 'Content-Type: application/json' \
--data-raw '{
	"basketId": "8b779ed8-8081-46fa-af23-1bed1856d266",
	"item" : {
		"itemId": "item-2"
	},
	"action" : "REMOVE"
}'
```
##### Response
`Basket Updated!` with HTTP status `200 - OK`

#### View item of an existing basket
```
curl --location --request GET 'http://localhost:8080/baskets/8b779ed8-8081-46fa-af23-1bed1856d266'
```
##### Response
Json Data with HTTP status `200 - OK`
```
{
    "basket": {
        "user": "test",
        "basketId": "8b779ed8-8081-46fa-af23-1bed1856d266",
        "status": "ACTIVE",
        "items": [
            {
                "itemId": "item-2",
                "name": "more meat",
                "price": 10.20,
                "quantity": 5
            }
        ]
    },
    "total": 51.00
}
```