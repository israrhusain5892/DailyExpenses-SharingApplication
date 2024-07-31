## Objective
 Design and implement a backend for a daily-expenses sharing application. This
application will allow users to add expenses and split them based on three
different methods: exact amounts, percentages, and equal splits. The
application should manage user details, validate inputs, and generate
downloadable balance sheets.

## Project Steps

To implement the backend for the Daily Expenses Sharing Application in Java using Spring Boot, we will follow these steps:

#### 1.Project Setup: Initialize a Spring Boot project.
#### 2.User Management: Implement user management features.
#### 3.Expense Management: Implement expense addition and splitting.
#### 4.Balance Sheet Generation: Generate downloadable balance sheets.
#### 5.API Endpoints: Define the required API endpoints.
#### 6.Data Validation: Ensure proper input validation.

## Project Structure
![image](https://github.com/user-attachments/assets/bb5e20dd-9ac3-43e1-ab14-37a0f354aaf8)



## Step-by-Step Implementation

#### 1. Project Setup
Create a Spring Boot project with dependencies for JPA, Web, and Lombok. go to https://start.spring.io/

#### 2.User Management

Create User Model and User Service
Use Login feature with jwt authentication

![image](https://github.com/user-attachments/assets/37b1f51b-1751-43ec-a884-934fc7007dac)

#### Create User:
Endpoint: POST (http://localhost:8080/public/user/register)
 ![image](https://github.com/user-attachments/assets/da4f7662-b20d-4129-94a5-4c7f1337dea4)

This endpoint creates a new user in the system.

#### 3. Expense Management
Create Expense Model and Expense Service
Create Participnt Model and it service
![image](https://github.com/user-attachments/assets/7818b1aa-51cd-44a9-91ac-f6d58f7e809a)

#### 4. Balance Sheet Generation
   ###### Download Balance Sheet:
    Endpoint: GET /api/balance-sheet/{expenseId}?format={csv|pdf}
    This endpoint generates and downloads the balance sheet for a specific user in the requested format (CSV or PDF).
![image](https://github.com/user-attachments/assets/802565d4-e6bf-4849-beb2-903a3e15e8df)

