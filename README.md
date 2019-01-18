# aguafresca
The Refreshing HTTP Server

This is an 8th Light Apprenticeship project that will be linked to a Tic Tac Toe project in the future. Yay...

-----

## Setup

#### Requirements
- Java 10 (I used Homebrew to install it)

(I'm intending to have this run on Mac, so if you're on Linux or Windows, please report back as to how it works!)

#### Running it

In order to have your very own Barbaechoa server, you will need to clone, download or fork this repository. 
Pick your weapon and let's get started!

Once you have managed to copy the repository onto your local machine, run the following:
- `./gradlew jar` - to package the file
- `java -jar build/server.jar --port <port number> --directory <directory path>` - Run this in your terminal window,
and pick a port number and a path (or don't, it should still work).

#### Using it

- Use a client such as your browser or HTTPie in your terminal.
- If using your browser, you'll want to connect to the port number you've given your server, or `8080` if you haven't 
given it any. You can do that by typing `localhost:<port number>` (eg. `localhost:8080`). 
- If using HTTPie, enter a request in your terminal in this format: `http GET localhost:<port number>`
(eg. `http GET localhost:8080`).
- Feel free to access any file or folder you fancy, but you should get a 404 error if you're not trying to access the 
following structure:
```
.
├── public
|   ├── bar.txt
|   ├── baz.txt
|   ├── foo.txt
|   ├── subdirectory
|   |   ├── apple.txt
|   |   ├── mango.txt
|   |   ├── tomato.txt
```
- You can add files and folders to your directory while the server is running and should be able to see these appear 
when you refresh your page.