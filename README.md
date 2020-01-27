# Showoff Test

### Initial consideration: The Instagram API is currently deprecated, so obtaining user data and images is not possible by this means. In this case I followed the Instagram access guide through the Facebook API ([new Instagram Graph API](https://developers.facebook.com/products/instagram/))

![Instagram deprecated](/screenshot.png)

## Login
In the login you must enter the credentials of Facebook.\
**IMPORTANT**: The account you login must be linked to instagram.\
![Login](/login.gif)

## Home
In **Home** you will find all the user's recent posts. You can scrolldown to review them.
It shows the *place*, the *image* and the *message* associated with this image.\
You can logout by clicking on the upper right button, it will take you back to the Login screen. \
If the user already has his session open, when opening the application he will find the Home screen directly. \
![Home](/home.gif)

## Architecture
There are three modules: Data, Domain and App. Each of one with a single responsability and applying MVVM architecture.

#### Architectural Components of Google

In this project the *Architectural components of Google* are used, for example **LiveData**, **Navigation Component** and **Databinding**. Code made in **Kotlin**.

#### Third-party libraries used
- Coroutines
- AndroidX
- Dagger2 for the dependency injection.
- Retrofit for the API calls.
- Gson
- Facebook
- Glide
- Mockito and JUnit for unit testing.

