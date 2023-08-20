# CafeBarApplication

The CafeBarApplication is a user-friendly web-site designed to order coffee and create it in the nearest coffee machine. Every automaton has a short code number and it can be found on the map (this feature is in development).

This app also contains admin panel for controlling users and orders lists.

The project was created in the likeness of the content of the book "Spring in Action, Sixth Edition" by Craig Walls. I remade many features to study material myself. Also I want to make an ultimate project which contains all needed modern technologies for self development.

Below are links to *.md files to save space:
-
- [explanations.md](/studyMaterial/explanations.md). This document explains hard to understand concepts of Spring Framework.
- [annotations.md](/studyMaterial/annotations.md). This document contains description of all used annotations.
- [postgresql.md](/studyMaterial/postgresql.md). This document contains interaction rules with PostgreSQL environment
- [testing.md](/studyMaterial/testing.md). This document explains concepts of testing with JUnit and Mockito

**Technology stack:**
* Java
* Spring Framework
  * Spring Boot
  * Spring Data
  * Spring Security
* Hibernate (JPA)
* PostgreSQL
* Thymeleaf
* Junit
* Mockito
* OAuth

## How to develop this app
0. Download java 17
1. Download VSCode
2. Install Java Extension Pack in VSCode
3. Install maven with console
4. Download PostgreSQL
5. Inject login and password

If "...Connection to localhost:5432 refused..." error shown, run in console
```
sudo lsof -n -u postgres | grep LISTEN
```
and look for the port, if port in console and in `application.yml` are not the same, resolve dismatch
