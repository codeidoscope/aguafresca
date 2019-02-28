[![Build Status](https://travis-ci.org/codeidoscope/aguafresca.png)](https://travis-ci.org/codeidoscope/aguafresca) [![codecov](https://codecov.io/gh/codeidoscope/aguafresca/branch/master/graph/badge.svg)](https://codecov.io/gh/codeidoscope/aguafresca) [![Maintainability](https://api.codeclimate.com/v1/badges/ad7ff723d172b8b0eb36/maintainability)](https://codeclimate.com/github/codeidoscope/aguafresca/maintainability)

# aguafresca
The Refreshing HTTP Server

This is an 8th Light Apprenticeship project.

-----

## Setup

#### Requirements
- Java 11 (I followed the instructions provided in [this blog](https://solarianprogrammer.com/2018/09/28/installing-openjdk-macos/) to do so)
- Gradle (Instructions available [here](https://gradle.org/install/))

(I'm intending to have this run on Mac, so if you're on Linux or Windows, please report back as to how it works!)

## Running it

### Running the server

In order to have your very own Aguafresca server, you will need to clone, download or fork this repository.
Pick your weapon and let's get started!

In order to enjoy all the features it offers, I recommend you add the files in 
[this folder](https://drive.google.com/file/d/1Mwj8RdqFh2zgtP9DtBGt4bkZLIYCgb_u/view?usp=sharing) to the `/public` folder.
There are different image types: JPEG (large and small), GIF, PNG; a large and a small MP4 video files; a large and a
small PDF files; and some light text files.

(These were to big to be hosted on Github, hence the separate download).

Once you have managed to copy the repository onto your local machine, run the following:
- `./gradlew jar` - to package the file
- `java -jar build/server.jar --port <port number> --directory <directory path>` - Run this in your terminal window,
and pick a port number and a path (or don't, it should still work if you don't add the `--port` and `--directory` 
arguments).

### Running the tests
In order to run the tests, you can either use Gradle from the CLI, or configure IntelliJ to run the tests for you in the
programme.

- Using Gradle and the CLI: Run `./gradlew check` to run the tests. This should raise errors if there are failing tests.
- Using IntelliJ: Make sure you have configured IntelliJ to use (which you can do via Preferences > Build, Execution, 
Deployment > Build Tools > Gradle > Runner), right-click on the test file and select `Run 'Tests in ...'` or `Run 'Tests 
in ...' with Coverage`.

### Performance testing
This codebase was optimised to support serving large files and large number of requests. If you feel like playing around
with it and push it to its limit, feel free to use the following commands to watch it pass (or crash if you push it too
 hard):
 
- `ab -n 5 -c 1 http://localhost:8080/verylargepdffile.pdf` This will send five requests with a concurrency of one to
fetch a 1GB PDF file.

- `ab -n 10000 -c 100 http://localhost:8080/` This will send 10,000 requests with a concurrency of 100 to the directory
page of the server.

For extra fun, [download VisualVM](https://visualvm.github.io/download.html). Once opened, start the server in the 
command line, select the application process you want to observe (likely `server.jar` if you're running this server),
and select the `Monitor` option in the tabs at the top of the window. You'll be able to see the CPU and heap memory
usage, and if you have no idea what I'm talking about or want to know more about how I optimised my code, well lucky you!
I wrote [an article about it that you can read on Medium](https://medium.com/@codeidoscope/load-testing-and-improving-the-performance-of-my-http-server-ab6ff70ced60).

## Using it

- Use a client such as your browser or HTTPie in your terminal.
- If using your browser, you'll want to connect to the port number you've given your server, or `8080` if you haven't 
given it any. You can do that by typing `localhost:<port number>` (eg. `localhost:8080`). 
- If using HTTPie, enter a request in your terminal in this format: `http GET localhost:<port number>`
(eg. `http GET localhost:8080`).
- Feel free to access any file or folder you fancy, but you should get a 404 error if you're trying to access files
that are not in your `/public` directory.
- You can add files and folders to your directory while the server is running and should be able to see these appear 
when you refresh your directory page.

### Try this!

- Accessing `/form` will display an HTML form page. Clicking the submit button will recap the information your previously
submitted.

## Areas for improvement

- Entering special characters such as `""` in the HTML form fields will result in the content not being parsed properly.
- My `ResponseBuilder` does not include building the body...
- My `Router` can definitely be improved and make better use of polymorphism.
- Error handling
- I'm using a Singleton (booo). I'm using it because it conveniently holds some data that I do not need to transform
once it has been set, but which I need to use in a lot of different places. It was cleaner to use a Singleton than to
make all of my classes and methods inherit that data. It did mean that I had to implement `setUp` methods in my tests to
ensure that it would be reset to the correct port number and path for my tests to work. I have also [written about
Singletons](https://medium.com/@codeidoscope/all-the-single-ton-ladies-2a11c407690e) because someone asked me why they 
could be a bad thing and I couldn't answer, so I did my research.