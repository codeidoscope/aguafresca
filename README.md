[![Build Status](https://travis-ci.org/codeidoscope/aguafresca.png)](https://travis-ci.org/codeidoscope/aguafresca) [![codecov](https://codecov.io/gh/codeidoscope/aguafresca/branch/master/graph/badge.svg)](https://codecov.io/gh/codeidoscope/aguafresca) [![Maintainability](https://api.codeclimate.com/v1/badges/ad7ff723d172b8b0eb36/maintainability)](https://codeclimate.com/github/codeidoscope/aguafresca/maintainability)

# aguafresca
The Refreshing HTTP Server

This is an 8th Light Apprenticeship project.

-----

## Setup

#### Requirements
- Java 11 (I followed the instructions provided in [this blog](https://solarianprogrammer.com/2018/09/28/installing-openjdk-macos/) to do so)
- Gradle 5.2.1 (Instructions available [here](https://gradle.org/install/))

(I'm intending to have this run on Mac, so if you're on Linux or Windows, please report back as to how it works!)

## Running it

### Running the server

In order to have your very own Aguafresca server, you will need to clone, download or fork this repository.
Pick your weapon and let's get started!

In order to enjoy all the features it offers, I recommend you add the files in 
[this folder](https://drive.google.com/open?id=1hiuCHiDQaZyQjT8GH5H7BWR6eGFaA06K) to the `/public` folder.
There are different image types: JPEG (large and small), GIF, PNG; a large and a small MP4 video files; a large and a
small PDF files; and some light text files.

(These were to big to be hosted on Github, hence the separate download).

Once you have managed to copy the repository onto your local machine, make sure that you are using the correct versions 
of Java and Gradle, and run the following:
- `./gradlew jar` - to package the file
- `./gradlew build` - to assemble and test the project
- `java -jar build/libs/aguafresca-1.0-SNAPSHOT.jar --port 8080 --directory ./public` - Run this in 
your terminal window. Feel free to use another port or another directory, or not give any arguments.

Head over to "Using it" to play around with it.

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

## How does it work?

Please find attached a diagram of the entire application and its flow ([click here for full size](https://raw.githubusercontent.com/codeidoscope/aguafresca/master/aguafresca.jpg?raw=true)):

![Diagram of the application](https://raw.githubusercontent.com/codeidoscope/aguafresca/master/aguafresca.jpg?)

- The server is created using the arguments given by the user (port and directory), or using the default settings if no
argument is provided. The input validation is limited, and the server will start with the default settings if it only
gets one of the arguments required. 

- The server itself is started in the main method, and calls `startServer` in the `ServerRunner`. This method creates a 
`ServerSocket`, then launches an executor that will create a new `Socket` and call `run()` on it.

- In the `run()` method, the application gets input from the client, and sends it to the parser, which returns a `Request`
object that has a body and headers.

- That request is then routed via the `serverRouter`, which will direct it to the appropriate handler according to the
type of file that is requested. Note that `Routes` are stored in this class as although it breaks the Open/Closed
principle, this implementation did not call for a more sophisticated way to store the routes. An alternative location
for these routes would be to have them created in the `Server`, to allow dynamic creation at runtime, for example...

- The `Handler` classes all take a `Request` and read or generate files before `Header` and `Body` objects are created
via a `ResponseBuilder` that will return a new `Response` object.

- The `Response` object is then passed to the `ResponseSender` class, which converts the header to bytes and send it to
the `OutputStream` before chunking the body of the `Response` if it is larger than 1024 bytes. This ensures the file is
streamed, which improves the performance of the server when numerous requests are made simultaneously. The chunk (or
file, if small enough) is then converted to bytes and sent to the client's `OutputStream`.

- The socket is then closed, and the request-response process repeats until the server is closed.


## Areas for improvement

- Entering special characters such as `""` in the HTML form fields will result in the content not being parsed properly.

- My `ResponseBuilder` could be more sophisticated and handle creating the body of the `Response` as well.

- My `Router` can definitely be improved and make better use of polymorphism.

- I'm using a Singleton (booo). I'm using it because it conveniently holds some data that I do not need to transform
once it has been set, but which I need to use in a lot of different places. It was cleaner to use a Singleton than to
make all of my classes and methods inherit that data. It did mean that I had to implement `setUp` methods in my tests to
ensure that it would be reset to the correct port number and path for my tests to work. I have also [written about
Singletons](https://medium.com/@codeidoscope/all-the-single-ton-ladies-2a11c407690e) because someone asked me why they 
could be a bad thing and I couldn't answer, so I did my research.

- Error handling is something I'm not very well-versed about, and the error handling throughout this codebase, from the
thrown errors to the logger, is not very sophisticated either.

- We all know web browsers are delightful and completely logical and reasonable, but Chrome - when I left my server and
an open window in Chrome idle for a while - would send my server a `null` request, which would make it die. As a result,
I know mitigating for this behaviour by creating a request that has `null` headers and body. This request gets routed to
my `DefaultHandler`, which responds with a 404. This is not ideal behaviour, but it is adequate as that `null` request
never appears on the actual browser, so whatever page you were on will still be the same when Chrome's Ping Of Death is 
sent and my server catches it.

- A number of errors are caught and shown in the command line when serving some text files. The server behaves as 
expected and will serve everything just fine, but I have not spent time looking into the reason why these errors 
are displayed or how to mitigate for them. These errors also appear when serving media files (videos and sound files),
but this is due to the streaming process, and this behaviour is expected for these files.