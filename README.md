# Rate Me!

RateMe! is an online platform, where users can upload and rate each others picture in fast and fun way.
## Introduction
This project was done for the course [“Software Engineering Lab” (SoPra)](https://hasel.dev/teachings/fs22-sopra/) at the [University of Zurich](https://www.uzh.ch/en.html). It should teach how to actually develop software in a team by creating our own bigger software project.

## Goals and Motivation
The main idea was to separate the picture from its uploader so the person who rates a pictures does not know, who's the uploader. This creates a more objective rating and every user has the abillity to get great ratings. The previous works do not influece the new ratings and the picture must stand for themselves.
We want to create a platform wich is fun and easy to use. Everyone should have to possibility to be a star and be featured on the highlights page.
## Built With

* [Springboot](https://spring.io/) - Java framework to create a micro service
* [H2](https://www.h2database.com/html/main.html) - Database
* [React](https://reactjs.org/docs/getting-started.html) - Javascript library for the whole frontend
* [MUI](https://mui.com/) - CSS Component library
* [Github Projects](https://github.com/explore) - Project Management
* [Balsamiq](https://balsamiq.com/) - Mockups
* [Heroku](https://www.heroku.com/) - Deployment
* [SonarCloud](https://sonarcloud.io/ ) - Testing & Feedback of code quality

## Getting Started

The following passages will show you how to set up the project on your local machine.

### Prerequisites

We recommend to use the following IDE's:
#### Client
[Visual Studio](https://visualstudio.microsoft.com/de/) or
[Webstorm](https://www.jetbrains.com/webstorm/)
#### Server
[IntelliJ](https://www.jetbrains.com/idea/)


### Installing

For the client, you need to install the needed modules via npm:
```
npm install
```

And to start the client you use:

```
npm run dev
```

For the server, you have to run the [Application](https://github.com/sopra-fs22-group-20/server/blob/main/src/main/java/ch/uzh/ifi/hase/soprafs22/Application.java) inside IntelliJ.

## Running the tests

To run the test for the server, you have to run the [test directory](https://github.com/sopra-fs22-group-20/server/tree/main/src/test/java/ch/uzh/ifi/hase/soprafs22)


## Deployment

To deploy the software to heroku, you only need to push the code to the main branch. Then, it will get automatically deployed.

## High-level components
### User
The user itself is one of the most important classes in the whole project. 1 user is owning n images and the user and it's credentials are mandatory to use the service. The object user is representing a real user of the service. See [user class](https://github.com/sopra-fs22-group-20/server/blob/main/src/main/java/ch/uzh/ifi/hase/soprafs22/entity/User.java). 
### Image
The image entity is a representation of an image uploaded by a user. So each image must be owned by a user, but the same user can have several images. The image itself gets also rated by different other users, so the rating, an attribute of image gets changed according to the ratings of the users. An image can only be rated once by a specific user. An image has also the attribute category, to define, which kind of image it is, for exemple dog or car.
See [Image class](https://github.com/sopra-fs22-group-20/server/blob/main/src/main/java/ch/uzh/ifi/hase/soprafs22/entity/Image.java).
### Game
The game is the real-time component of our project and the user is able to play the game agains another user. With the earned trophies, the user is able to boost his image, so the image is shown to more other user.
See [Game class](https://github.com/sopra-fs22-group-20/server/blob/main/src/main/java/ch/uzh/ifi/hase/soprafs22/entity/Game.java).
### category
A category is an attribute by the image and defines what kind of image it is. It is important for the highlights page, as you can filter, which are the top images of a specific category.
See [Category class](https://github.com/sopra-fs22-group-20/server/blob/main/src/main/java/ch/uzh/ifi/hase/soprafs22/entity/Category.java).
## Illustrations: In your client repository, briefly describe and illustrate the main user flow(s)
of your interface. How does it work (without going into too much detail)? Feel free to
include a few screenshots of your application.
## Roadmap
The following features could be implemented next to improve RateMe!
### Tournament mode in the game
The game could get a new mode, where the users could earn more trophies by adding several game rounds and be the champion!
### Sharing directly to social media
Another improvement would be, that the posters of new images could share their images directly via social media (twitter, fb, etc).
### Advanced user profiles
The user profiles will be getting more settings, so the user could invidualise his profile by adding more credentials and a profile image. New user should get a generated profile image.
## Authors

* **Marcel Eymany (group leader)** - *Frontend, Mockups* - [Github](https://github.com/kone54)
* **Omar Abo Hamida** - *Frontend, Setup of project in Github* - [Github](https://github.com/oaboha)
* **Jamo Sharif** - *Backend, Testing* - [Github](https://github.com/JSha91)
* **Jonathan Contreras Urzua** - *Backend, Database, Testing* - [Github](https://github.com/JonathanContrerasM)
* **Louis Huber** - *Frontend, Testing, Mockups* - [Github](https://github.com/L-Huber)|[Linkedin](https://www.linkedin.com/in/louis-huber/)


See also the list of [contributors for the server](https://github.com/sopra-fs22-group-20/server/graphs/contributors) and [contributors for the client](https://github.com/sopra-fs22-group-20/server/graphs/contributors) who participated in this project.

## License

 ????????All rights reserved.
## Acknowledgments
We want to thank [Melih](https://github.com/melihcatal) for his help as a tutor.
