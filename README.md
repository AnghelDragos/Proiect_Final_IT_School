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
If the user is not logged in, write "There is no connected user!"
If the flight with id flight_id does not exist, then write "The flight with id <flight_id> does not exist!"
If a logged-in user, but does not have a ticket on this plane, write "The user with email <email> does not have a ticket for the flight with id <flight_id>"
If the validations are passed, delete that flight and write: "The user with email <email> has successfully canceled his ticket for flight with id <flight_id>"
