## Author

This applications was created by **Aaron Corns** for their *SDEV-200: Java Programming* course.

## Purpose

This application allows a user to create appointments for a pseudo massage business. The application starts by allowing input to create an application. An appointment cannot be created unless all required fields are filled out as well as ensuring that the phone number is in the proper format. Scrubs are optional, but a massage type is always required. The program will determine if a client is new based on the phone number. If the phone number is new, a new client will be created. The application allows the user to also edit and delete appointments that are selected from the Appointment Table. Client data can be edited when editing the appointment. The new client information will be sent to the database. MongoDB Atlas is being used as the database.  
  
When running this application JavaFX and MongoDB referenced libraries are required. The required MongoDB driver files are already
included and are located in the 'lib' folder. Please see the .docx file for documentation on using the program.

## Folder Structure

You will find all source files within _/src/massageapp_ folder.  
You will find Mongo DB referenced libraries within _/lib_ folder.  

## Dependency Management

Before running this program, please ensure that you have JavaFX installed. You will also need to make sure that the MongoDB references libraries in _/lib/_ can be properly found by your classpath.

