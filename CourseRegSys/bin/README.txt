Course Registration System - Michael Mironidis
Last modified: 10/12/2020

I have deleted students.ser and courses.ser to let you play with this program from scratch.
Upon first running the program, it will throw an error because it doesn't find those files.
Ignore this, as the serialization files will be written at the end of the first run and
the error will never occur again.
For convenience, I have a part of the code commented out that fills a course with students.
This is to test printing all full classes to a file (an Admin privilege).

There is one persistent quirk that I put on the backburner to figure out and would like
feedback on: when using Scanners in methods, if I close the scanner at the end of the method,
whenever the method gets called, it bugs out the code. I think it closes the main Scanner object.
Any way to responsibly use Scanners in methods (without resource leak)?

Thank you to Ethan Shekib for pointing out in class that serialization changes the addresses of objects
so that the pointers no longer point to the same object, thus requiring a .equals method for each of my classes.