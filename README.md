=============================================================
Building Instructions:
-----------------------
1. Open AndroidStudio (developed on AndroidStudio 2.3.1)
2. Click on "Import project" and navigate to TiltMenu
3. Connect an Android phone having Android OS version atleast 5.0 (Marshmallow)
    3.1. Ensure usb debugging is turned ON. If unsure how to do this, please visit: https://goo.gl/FV7K9o 
4. Click on Run> 'Run app'
5. A window will pop up that will ask you to select device to run the application on; find your Android phone and select it and click ok.
6. This app requires write permissions (to save user and survey data).
    6.1. To provide the app with this permission, drag the installed app on the phone over to the top where it says 'App info'
    6.2. Tap on 'Permissions'
    6.3. Toggle switch for 'Storage'
7. Please check your internal home folder. Create an empty directory - 'Documents' if not already present by default.
8. We're ready to rock!

=============================================================
Prototype Instructions:
-----------------------

The prototype is fairly simple to execute. It consists of four screens:
1. Main Screen
2. Two-Zone menu model variant
3. Four-Zone menu model variant
4. Survey screen

1. The prototype begins with the Main Screen where the person conducting the study would have enter Participant ID and Trial Number before selecting
Two-Zone or Four-Zone.
2. Ensure the phone is strapped to the participant before selecting either of the options, and their hand is stretched straight out.
3. Tap Two-Zone or Four-Zone
4. The subsequent screen will display the model variant with menu options. The option the user needs to navigate to is displayed at the bottom of the screen
5. The user has to follow this prompt and accordingly rotate their hand from elbow to move up/down or twist their wrists to move left/right.
6. If correct option is selected, a very short haptic feedback is provided.
7. If incorrect option is selected, a relatively longer haptic feedback is provided.
8. For each model variant per trial, the prototype asks the user to navigate to 8 options in a random manner.
9. After each trial, the prototype switches back to main screen for the conductor to enter trial number and select Two-Zone / Four-Zone variant
10. After the study, the conductor may tap on 'Survey' on the main screen after entering a participant ID.
11, On the following screen, the user rates the two variants on a Likert 7-point scale and mentions which of the two they found better.
12. the user then taps on 'Submit' and the responses are recorded.
13. The data (survey and user performance) are saved in comman separated values (csv) format at the following location:
"/storage/emulated/0/Documents/TiltMenu"
14. These can be opened in a spreadsheet app like Google Sheets or Microsoft Excel or Libreoffice Calc / copied to a computer and opened there. 


