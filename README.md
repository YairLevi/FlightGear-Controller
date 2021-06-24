# FlightGear Controller Application

This application allows FlightGear users fly their planes via mobile smartphones, tablets, or any Android based devices.

Whilst FlightGear running on the PC, we can connect our device to control the plane's throttle, rudder and stick, which is a lot easier for the FlightGear's rookie.

## Preview 

![alt text](https://github.com/eladoni1/Pictures_FG_Controller/blob/main/Controller.jpeg?raw=true)


![alt text](https://github.com/eladoni1/Pictures_FG_Controller/blob/main/Connection.jpeg?raw=true)


![Alt Text](https://github.com/eladoni1/Pictures_FG_Controller/blob/main/newGif.gif?raw=true)




### Requirements and installation

Supports android version 5.0 (Lollipop) and above.
For the tests and running of the application we used the lastest stable version of Flight Gear (2020.3.9). (https://www.flightgear.org/download/)[download here]

If you don't want to take a look into the code itself, you can always just use the '.APK' file - just download to your smartphone and install and Voilà!
If indulge with an application code is not your thing, you can always just download the apk file from the [repository page](https://github.com/YairLevi/FlightGear-Controller) and install on your android device.


After FlightGear's installation, we must configure the settings so it can seek directional control from a certain port.

Open FlightGear, and navigate to settings 

![alt text](https://github.com/eladoni1/Pictures_FG_Controller/blob/main/Select_Settings.png?raw=true)

Scroll down and write this line into the 'additional settings' textbox : '''--telnet=socket,in,10,127.0.0.1,6400,tcp''' like so :

![alt text](https://github.com/eladoni1/Pictures_FG_Controller/blob/main/additional_settings.PNG?raw=true)

If port 6400 is already taken by another program, simply replace the line to '''--telnet=socket,in,10,127.0.0.1,PORT_NUM,tcp''' and select whatever port available instead of 'PORT_NUM'.

Select the 'Fly' button, and the FlightGear will commence loading the simulator.



### Instructions

#### Connecting to FlightGear

In our mobile device, after we've downloaded the '.APK' file to our smartphone, run the newely installed 'FlightGear-Joystick' app and select the port we've entered in the FlightGear's settings, and the computer's (which runs the FlightGear) IP. You can simply check :
- Windows : Press the Windows button on your keyboard (or press the windows icon in the taskbar), and write down 'CMD' and press enter. While in the command prompt run the command 'ipconfig' and check what is your IPv4.
- Linux : Press ALT + T and it'll open up the terminal. Write down 'ifconfig', press Enter and search for your PC's IP.

After that is done, press 'Connect to FlightGear'. If there is any problem with the connection it will pop up a message with the error involved.
You'll be welcomed with the 'Ready for takeoff' audio, and now you can use the controllers to control the FlightGear's aircraft!


### How-to-use

The sliders above the joystick represent the throttle (fuel flow) and the rudder :
- Throttle : Basically represent the fuel flow to the engine, or in other words - speed of the vessel (The higher the value - the faster it goes).
- Rudder : Flat hinge in the back of the plane controlling the air-flow which is also the way the aircraft is turning left and right.

Joystick :
- Up and Down : Inverted movement of the aircraft, like in a real vessel - down goes upwards, up goes downwards.
- Left and Right : Turning, or flipping the vessel left and right - left flips the vessel to the left side, and right to the right side.

Video on how to use the program and how the code is organized (in Hebrew) [here](https://www.youtube.com/watch?v=cv0r8JrFNGw&ab_channel=YairLevi).


