# Android Project - Where to eat?

## General

This is an application made for Android systems, which is using Retrofit to get data(in our case restaurants) from an api (https://ratpark-api.imok.space/).
Used components: Recycler View, Room local database, Retrofit, Glide, Fragments, Bottom navigation menu, Coroutines.
 
## Description

### Login and register

After launching the application, the Splash screen appears. After a few seconds, the user is navigated to the Login page. In order to sign in, the user should first complete the registration. This can be done by clicking on the Register button at the Login page, which navigates to the Register page. The user should complete the registration with some information(Name, E-mail, Phone number, Username, Password), and if the typed e-mail, username and phone number does not exists, the registration was completed successfully. User's data will be stored at a local Room database. After this, the user will be redirected to the login page, where he/she can sign in. 
 
### Main screen

After user's login, the main screen, with restaurants will be loaded. Here, users can search restaurants by cities. There are two options to filter data. There is a spinner element and an input element. Also, restaurants can be checked paginated. There are cities, where more than one page contains restaurant. In this case, next and previous buttons are visible. 
At the main screen, the restaurants are implemented with Recycler view, and five things are represented: picture about the place(loaded with Glide), its name, address and price, and if it is liked by the user. 
By loading the main screen, a bottom navigation menu appears. This contains two actions: Menu and Profile. 
By pressing the Menu, the main screen with restaurants will be loaded. If the Profile is pressed, information about the user will be shown.

### Detail page

If the user clicks on a restaurant at the main screen, he/she will be redirected to the restaurant's detail page. There, more information about the restaurant will be shown: name, address, postal code, city, state, area, country, phone number, and if it is liked or not. Also, there is the option to call the place by clicking on the "Call number" button.
Users have the option to like or dislike resturants by clicking on the favourite button. Depending on the user's choice, the restaurant will be added to the database or deleted from it.

### Profile page

On the profile page, users have the possibility to see their personal information and their favourite restaurants, implemented with another RecyclerView. 
