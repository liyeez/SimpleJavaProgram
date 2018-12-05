# SimpleJavaProgram: Hotel Booking System

Aims:

    Practice how to apply a systematic object-oriented design process

    Gain experience in implementing an object-oriented program with multiple interacting classes

    Learn more about the Java class libraries

In this assignment, you will implement a prototype system that could serve as the "back-end" of a hotel reservation system (imagine wotif.com). Customers can make, change and cancel bookings. A hotel consists of a set of numbered rooms: each room is either a single, double or triple room (so has capacity 1, 2 or 3). A booking is made under a name and consists of one or more room requests for a number of rooms of a certain type (e.g. two single rooms and one double room) for a period of time given by an arrival date and a number of nights (assume all bookings are for 2018). A request is either granted in full or is completely rejected by the system (a request cannot be partially fulfilled).

Assessment will be based on the design of your program in addition to correctness. You should submit at least a UML class diagram used for the design of your program, i.e. not generated from code afterwards.

All input will be a sequence of lines of the following form, and all hotel rooms will be declared before any other commands are issued (you are not required to handle any invalidly formatted input):

Hotel <hotelname> <roomnumber> <capacity>
          # specify that hotel with <hotelname> has a room with number <roomnumber> that has the size <capacity>
Booking <name> <month> <date> <numdays> <type1> <number1> <type2> <number2> . . .
          # booking under <name> starting on <month> and <date> for <numdays> of <number1> rooms of <type1>, etc.
Change <name> <month> <date> <numdays> <type1> <number1> <type2> <number2> . . .
          # change booking under <name> to start on <month> and <date> for <numdays> of <number1> rooms of <type1>, etc.
Cancel <name>
          # cancel booking under <name> (if it exists) and free up rooms
Print <hotelname>
          # print occupancy of each room in the hotel <hotelname>



---------------------------------------------------------------------------------------------------------------------------
SAMPLE INPUT

For example, the format and meaning of the input is as follows (comments are for explanation and should not appear in the actual input):

Hotel Surfers 101 2      # Hotel Surfers has room 101 with capacity 2 ("double" room)

Hotel Surfers 102 2      # Hotel Surfers has room 102 with capacity 2 ("double" room)

Hotel Surfers 103 1      # Hotel Surfers has room 103 with capacity 1 ("single" room)

Hotel Burleigh 101 2     # Hotel Burleigh has room 101 with capacity 2 ("double" room)

Booking Aarthi Jan 25 2 single 1 double 1
          # Aarthi's booking request is for 1 single and 1 double room starting on Jan 25 for 2 nights
          # Assign rooms 101 and 103 of Hotel Surfers (output Booking Aarthi Surfers 101 103)

Booking Rob Jan 24 4 double 1
          # Rob's booking request is for 1 double room starting on Jan 24 for 4 nights
          
          # Assign room 102 of Hotel Surfers since room 101 is occupied (output Booking Rob Surfers 102)

Booking Stephanie Jan 26 1 double 1
          # Stephanie's booking request is for 1 double room starting on Jan 26 for 1 night
          
          # Assign room 101 of Hotel Burleigh (output Booking Stephanie Burleigh 101)

Change Aarthi Jan 27 3 single 1
          # Change Aarthi's booking to 1 single room starting on Jan 27 for 3 nights
          
          # Deassign rooms 101 and 103 of Hotel Surfers and assign room 103 (output Change Aarthi Surfers 103)

Booking Gary Jan 25 2 single 1
          # Gary's booking request is for 1 single room starting on Jan 25 for 2 nights
          
          # Assign room 103 of Hotel Surfers (output Booking Gary Surfers 103)

Cancel Stephanie
          # Cancel booking 3
          
          # Deassign room 101 of Hotel Burleigh (output Cancel Stephanie)

Booking Hussein Jan 26 1 single 1
          # Hussein's Booking request is for 1 single room starting on Jan 26 for 1 night
          
          # Request cannot be fulfilled (output Booking rejected)

Print Surfers
          # Print out occupancy of all rooms at Hotel Surfers, in order of room declarations, then date

---------------------------------------------------------------------------------------------------------------------------
SAMPLE OUTPUT

The output corresponding to the above input is as follows:

Booking Aarthi Surfers 101 103

Booking Rob Surfers 102

Booking Stephanie Burleigh 101

Change Aarthi Surfers 103

Booking Gary Surfers 103

Cancel Stephanie

Booking rejected

Surfers 101

Surfers 102 Jan 24 4

Surfers 103 Jan 25 2 Jan 27 3

---------------------------------------------------------------------------------------------------------------------------
Written using Eclipse Oxygen IDE
