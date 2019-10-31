# FlowControl
FlowControl is an example application illustrating use of Spring Boot, Spring Security and Spring Data. As such it should not be considered as suitable for deployment in production.

###Install
1. Setup a PostgreSQL instance and add the following environment variables:
    * `DATABASE_URL` (PostgreSQL connection string)
    * `DATABASE_USERNAME`
    * `DATABASE_PASSWORD`
2. In `application.yml` file change the following to credentials of your choice:
    ```yaml
    flowcontrol:
      admin:
        username: admin
        password: admin
    ```
3. Run the application
