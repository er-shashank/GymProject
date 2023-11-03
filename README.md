
# Gym Tracker Web App

This project can help a regular Gym goer to track his workout history, customize his workout plan.



## Schema

![gymplan_schema](https://github.com/er-shashank/GymProject/assets/60037888/5b408e3a-1687-42a4-afed-c8adfc76b1dd)

## Documentation

Tech stack used:

    1. Spring boot 2.1.6 for backend
    2. ReactJS for FrontEnd
    3. MYSql as RDBMS
    4. Redis for Caching
    5. Docker for Containerization
    
Worklow:

    1. User signup by using /api/auth/signup public api (unique username and Email needs to be provided). With the OTP verification sent via email signup completes.
    2. User can login using /api/auth/login public api using username and password.
    3. In the home page user can see and edit current workout plans
    4. User can navigate to the today's plan and save their personal records.
    5. user can see all the historical records.
    
## Upcoming


Here are some upcoming ideas to be implemented:
    
    1. customizing plan on the fly
    2. note for future gym session
    3. visualization for weight tracking
    4. goal setting ->> weight, no of days in week
    5. notfication for each day for todays plan and weekly progress
