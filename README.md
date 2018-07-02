# ticket-service-walmart
A ticket reservation service to be used by a fictional venue.
# Ticket Service Coding Challenge
Implement a simple ticket service that facilitates the discovery, temporary hold, and final reservation of seats within a high-demand performance
venue.
- For example, see the seating arrangement below.
```
----------[[ STAGE ]]----------
---------------------------------
sssssssssssssssssssssssssssssssss
sssssssssssssssssssssssssssssssss
sssssssssssssssssssssssssssssssss
sssssssssssssssssssssssssssssssss
sssssssssssssssssssssssssssssssss
sssssssssssssssssssssssssssssssss
sssssssssssssssssssssssssssssssss
sssssssssssssssssssssssssssssssss
sssssssssssssssssssssssssssssssss
```

## Problem Description

Your homework assignment is to design and write a Ticket Service that provides the
following functions:
- Find the number of seats available within the venue
Note: available seats are seats that are neither held nor reserved.
- Find and hold the best available seats on behalf of a customer
Note: each ticket hold should expire within a set number of seconds.
- Reserve and commit a specific group of held seats for a customer

## Requirements
- The ticket service implementation should be written in Java
- The solution and tests should build and execute entirely via the command line using either Maven or Gradle as the build tool
- A README file should be included in your submission that documents your assumptions and includes instructions for building the
solution and executing the tests
- Implementation mechanisms such as disk-based storage, a REST API, and a front-end GUI are not required

## Assumptions
- The venue is a fixed size of 200 seats organized by 20 rows
and 10 columns.
- The best seating is a usually a personal preference. In this
scenario it is assumed that the best seating is closest to the stage and
rows alternate filling from left to right and vice versa with the
thought of keeping groups as close together as possible.
- All seats are assumed to be the same price and are purely
based on a first come first served basis.
- Seat holds expire and return to being available after 10
seconds with no reservation made.
- To make it easier to read the Confirmation Code is provided
in the format WV-XXXX-XXXXX, where the X's are random numbers.

# Solution
The solution was created using JDK 9.0.4 and Maven 3.5.4.
## How to build with Maven
1. Download the project files to your computer.
2. Shift + Right Click on the folder containing the pom.xml file.
3. Click on "Open command window here".
4. Type the following command to build the project.
```
    mvn package
```
5. Type the following command to run the project tests.
```
    mvn test
```
