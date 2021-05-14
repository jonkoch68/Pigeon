# Pigeon
Project for Syracuse University Course CSE 687 


* * * * * * * * * * * * * * * * * * * * * * * * * 
*    Note this is only 1 of 2 readme files      *
*    Please check backend half to see other     *
*    Readme file for how to set up backend      *
* * * * * * * * * * * * * * * * * * * * * * * * *


Install Instructions

Option 1
1. Donwload Android Studio from link provided
    https://developer.android.com/studio/?gclid=CjwKCAjwv_iEBhASEiwARoemvJefgSSBZJxu96CyyUK-Ez6PvWBcEubLY3NzPBKusOMwcKjK5zKIAhoCcHYQAvD_BwE&gclsrc=aw.ds
2. Go through install process
3. When Android Studio IDE is finished Installing, Goto File -> New -> 
    - Can choose from either existing project (If you decided to unzip the project itself)
    - Or choose from 'from version control'
        -Select git from dropdown menu and input link https://github.com/jonkoch68/Pigeon.git
4. When it is done setting up project, select run option in top right of the toolbar (Looks like a green play arrow)

Option 2
1. Unzip project file
2. [Project Root Dir]\app\build\outputs\apk\debug
3. Extract file app-debug.apk
4. On selected android phone enable allow unknown sources
    - This will varry from device to device, best to check under security settings
5. Using a file explorer .apk file can be added to the /data/app folder on local android device. 
6. App should be runable from app launcher now

How to use Pigeon
- Upon startup you will be at the sign up page. Enter a username passwork and confirm password to create an accout
- You will then be dropped into the dashboard page. To enter a new package you can begin to type a tracking ID into 
    the textbox in the dashboard,
- Press New Package to bring you to the new package activity 
- Fill out fields and press submit to send info to server
- Pull down from top to refresh
- Package will appear below and will have options to set as favorite or edit. 
- To view package in readonly mode press on name of package
- To edit package press on the pencil icon 
- To log out, press android system back key to be brought back to login screen

Notes and Non-fucntional components
- Forgot password function is not enabled at this time
- In dashboard users can create Deployments but will not be able to be viewed
    -This is diabled at the moment due to large costs of reading from the database
    -Cloud Functions would enable us to circumvent this issue but this is a paid only feature
- Camera Icon in dashboard does nothing, Was sereved as a stretch goal for QR code scanning 
- Account Icon Does nothing, Was served to be a button to transistion user to modify account info
- Real-Time syncing is not working, state can be synced by using pulldown to refresh feature

Design Patterns used
- In our front end UI we have implemented a Model View Controller System throughout or code.
- Many of our models come in the form of List with some custom datatype to ease the flow of information
- The activity classes act as the controller in an android application project
- The listeners are stored inside of the activiy classes and are used to look for stimulus from the user
    through a series of event listeners
- Listeners are connected to the different UI elements in java object form.
- Views are the xml files listed in the /res/layout section
- The Java objects are linked to the xml views by way of ID's that are listed in. 
- In the complete workflow the Controller will listen for changes done to the objects. if any change is made
    a notify will be called and the controller will modify the value associated with the view and will then cause
    a rerender and the view will be in sync the rest of the system
- There are some nested MVC principles used in the front end like with a the Recycler View which is what allowed us to implement
    the list like scrolling views used throughout the UI. 
- The contollers of these list are the Adapter classes which extend the android recycler view system. 
- This allows us to update and notify changed of individual items in the list without having to rerender the entire list
- Inside the adapter classes are holder classes which is the subcontroller for the individual items for the list. 
- Inside the holder class this is where event listeners would be added for individual memeber that would allow on
    click actions to be implements to allow nested controll of our UI.