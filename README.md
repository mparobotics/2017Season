# 2017Season - Keir
MPAROR's repository for all code made for Keir, our robot for the STEAMWORKS competition.

##Repository Structure
* ###GRIP files

   These are files for vision processing with the GRIP program. We ended up using GRIP as mainly a template for vision processing as opposed to the end all solution, here is why...
   1. GRIP gave us a lot of problems when transfering generated code from our computers to the RaspberryPi that we are using as a co-processor to the roboRIO
   2. Using OpenCV by ourselves, we know exactly what is happening in the code. This makes debugging issues much easier and we know what values we need to change to make tweeks.
   3. GRIP doesn't currently do anything for camera configuration. Our team uses the Microsoft HD Lifecam 3000, which doesn't have web configuration like the Axis camera. What we needed to do was to use the linux command v4l2-ctl so that the exposure on the camera wasn't off. (Turn most lighting settings off, this camera is meant for faces so it makes things pretty bright.
   
* ###Keir Release

   This is the actual code used for this years robot. If you're looking to see how something on our robot works, than this is the place to go.<br>
   For this years Javadocs, look <a href="https://mparobotics.github.io/2017Season/">at this page</a><br>
   If you are looking to deploy this code, you should not try to do it in eclipse. This project uses <a href="https://github.com/Open-RIO/GradleRIO">GradleRIO</a>, so there is no need to do anything to build and deploy except just cd into the project directory and type either ./gradlew build deploy (if on Mac or Linux) or gradlew build deploy if you're on Windows.

* ###KlugesIntelliJSettings

   (Kluge writing in the first person here) I firmly belive that God intended for people to code in a very specific way, and that way is the K&R standard for everything. That way is proper Javadoc on everything, even private variables and methods. That way is consistent spacing between parentheses (and not inside them). That way is to put spaces between arithmetic operators (like + this). And please, for everyones sanity, wrap your lines to at most 120 characters. IntelliJ IDEA Community is just one of the tools that JetBrains lets people use for free. Whether you are completly new to programming, or have years of experience, it will make your life better. (Also we can deploy to the robot without Eclipse now because GradleRIO exists). These settings are what I usually use to cleanup my code. They will not do everything for you. You still need to code properly yourself in the first place otherwise they won't work.
   
* ###RaspberryPi
   These are the files used on our team's RaspberryPi co-processor for vision tracking. These include...
   * Image processing files (to do the heavy work and find the coordinates of targets to use in the robot's code)
   * Installation files (to install this on a RaspberryPi)
   * Video streaming files (to stream the processed image to the SmartDashboard)
   
* ###Research and Testing
   Each of the files in here are miscalaneous files from when the code team (William Kluge, Joe Brooksbank, Annie Portoghese, and Ben Lash) was doing testing/learing during the season. These files are not nessesarily meant to be used as examples of how systems should be made, and we can't garentee that all of these projects would work, but if you're interested in exploring some code you can look here. Here are some excerpts from this directory that contain notes or code that might be valuable.
   * <b>William/EncoderTestingII</b><br>
      This project is a basic example of a PID loop implimented with the command based methodology. This code has been tested and works. If you are stuck on how to get started with a PID loop, this might be helpful to you.
   * <b>William/CommandBasedTesting/src/org/usfirst/frc/team3926/subsystems/NetworkVisionProcessing.java</b><br>
      At the top of this file are a bunch of notes when we were first doing vision processing. This was before we figured out how to calibrate the camera with v4l2-ctl so it isn't nessesarily useful for Keir, but if you can't figure out how to get good results using only OpenCV than check out the filtering methods here.
   * <b>Joe/</b><br>
      If you're looking for OpenCV and RaspberryPi stuff, Joe did most of his testing within <a href="https://github.com/mparobotics/2017Season/tree/master/RaspberryPi"> the RaspberryPi</a> folder; look there.
   * <b>Ben/ and Annie/</b><br>
      All of their projects are when they were learning to code, so these projects are all them exploring WPILib and Java. They are not all be perfect, but may act as a good reference on where to start with a particular type of sensor.
      
* ###Template Projects/TestCommand
   This is a basic drive base set up using the Command (Event) Based programming methodology. If you're new to command based, this is a good place to go to farmiliarize yourself.
   
* ###docs
   These are the generated Javadoc from KeirRelease
