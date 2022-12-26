# IT School final exam project
An application should be created that manages airplane tickets for users. Commands will be read from a file called input.txt, and all messages will be written to the output.txt file (on a separate line each). These files will be in the resources folder.

Application function
The application will allow a user to register. They will need to use a unique, unused email address. They also need to have a password of at least 8 characters, which they must confirm. If these conditions are met, the user will be added to the application's collection of users. However, only one user can use the application at a time. To do this, they will need to log in using the correct credentials. If they are successful in connecting, they will be able to view their flights, add new flights, or cancel them. In addition to these actions, all flights can be viewed, new flights can be added, and existing flights can be deleted. When a flight is deleted, all users who had a ticket will be notified and the flight will be removed. In addition, there will be two commands that allow the data held in the application in two tables: users and flights.

There is also a bonus class with 6 unimplemented methods, which expose various statistics of the application.

# User Instructions

# SIGNUP email name password confirmation_password
if the user already exists, write "User already exists!"
if it does not exist, then add it to the collection used for storage and write "User with email: <email> was successfully added!"
The following checks are made:
password to be identical to confirmation_password, otherwise write "Cannot add user! The passwords are different!"
The password must be at least 8 characters long, otherwise write "Cannot add user! Password too short!"
# LOGIN email password
If the user does not exist, write "Cannot find user with email: <email>", where the email address sent as a command parameter will be used
If the user exists, but the password is incorrect, write "Incorrect password!"
If the credentials are correct, the user will be the current user, used for the following operations and write "User with email <email> is the current user started from <current date and time>"
If there is already a connected user, write in the file "Another user is already connected!"
# LOGOUT email
If the user was not logged in, write in the file "The user with email <email> was not connected!"
If the user was logged in, it is disconnected and writes: "User with email email successfully disconnected at <current date and time>!". This means that it is no longer the current user and another user can log in.
# DISPLAY_MY_FLIGHTS
If there is no logged in user, write "There is no connected user!"
If there is a logged in user, write the user's flights in the form: "Flight from <from> to <to>, date <date>, duration <duration>"
# ADD_FLIGHT flight_id
If the user is not logged in, write "There is no connected user!"
If the flight with id flight_id does not exist, then write "The flight with id <flight_id> does not exist!"
If he already has a ticket for this flight, write "The user with email <email> already has a ticket for flight with id <flight_id>"
Otherwise, add this flight for the current user, and write the message: "The flight with id <flight_id> was successfully added for user with email <current_user_email>.".

# CANCEL_FLIGHT flight_id
- If the user is not logged in, write "There is no connected user!"
- If the flight with id flight_id does not exist, then write "The flight with id <flight_id> does not exist!"
- If a logged-in user, but does not have a ticket on this plane, write "The user with email <email> does not have a ticket for the flight with id <flight_id>"
- If the validations are passed, delete that flight and write: "The user with email <email> has successfully canceled his ticket for flight with id <flight_id>"
------------------
# Flight Instructions
# ADD_FLIGHT_DETAILS id from to date duration
The id must be unique. If there is already a flight with this id, display: "Cannot add flight! There is already a flight with id = <id>"
The date is in YYYY-MM-DD format
Duration will be an int representing the number of minutes
For simplicity, there may be a single flight from a city to a destination on a given day.
Write "Flight from <from> to <to>, date <date>, duration <duration> successfully added!"
# DELETE_FLIGHT flight_id
Delete the flight from the collection used for storage and write: "Flight with id <flight_id> successfully deleted".
If the flight with that ID does not exist, write "The flight with id <flight_id> does not exist!"
All users who already had a ticket will be notified and write, for each "The user with email <email> was notified that the flight with id <flight_id> was canceled!". This entry will be deleted from the users' flights.
# DISPLAY_FLIGHTS
Write all flights in the form: "Flight from <from> to <to>, date <date>, duration <duration>
------------------
# Table Instructions
# PERSIST_FLIGHTS
Add all flights to the database and write: "The flights were successfully saved in the database at <current_time>!"
# PERSIST_USERS
Add all users from the collection to the dedicated table and write: "The users were successfully saved in the database at <current_time>!
------------------
# Bonus: Statistics
There will be a class called AirlineStatistics, which will contain the following methods (not implemented):
# private static String findMostUsedCityAsDepartureForFlights(AirLineManager airLineManager);
Will return the city from which there are the most flights
# private static User findUserWhoTravelTheMost(AirLineManager manager)
Will return the user whose flights total the most minutes (not the one with the most flights)
# private static List<User> findAllUsersWhoTraveledToCity(AirlineManager manager, String city);
Returns a list of all users who have traveled to the city sent as a parameter (case insensitive - it does not matter if it is written in capital letters or not)
# private static List<Flight> findAllFlightsBetweenDates(AirLineManager manager, LocalDate startDate, LocalDate endDate)
Returns all flights that took place between the two calendar dates
# private static Flight findShortestFlight(AirLineManager manager)
Returns the flight with the shortest duration. If there are several with the same duration, return the one with the smallest id.
# private static List<User> findAllUsersWhoTraveledIn(AirLineManager manager, LocalDate date)
Returns all users who traveled on that day. 
