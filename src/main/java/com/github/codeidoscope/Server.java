package com.github.codeidoscope;

public class Server {

    public static void main(String[] args) {
        ServerConnection serverConnection = new TCPServerConnection();
        ServerRouter serverRouter = createValidPathList(new ServerRouter());
        ServerRunner serverRunner = new HttpServerRunner(serverConnection, serverRouter);
        UserInputValidator userInputValidator = new UserInputValidator();

        userInputValidator.validate(args);
        serverRunner.startServer(Configuration.getInstance().getPortNumber());
    }

    private static ServerRouter createValidPathList(ServerRouter serverRouter) {
        RouteHandler textFileHandler = new TextFileHandler();
        RouteHandler defaultRouteHandler = new DefaultHandler();
        RouteHandler directoryHandler = new DirectoryHandler();

        serverRouter.setHandlerForRoute(new Route("/foo.txt", "GET"), textFileHandler);
        serverRouter.setHandlerForRoute(new Route("/bar.txt", "GET"), textFileHandler);
        serverRouter.setHandlerForRoute(new Route("/baz.txt", "GET"), textFileHandler);

        serverRouter.setHandlerForRoute(new Route("/mango.txt", "GET"), textFileHandler);
        serverRouter.setHandlerForRoute(new Route("/apple.txt", "GET"), textFileHandler);
        serverRouter.setHandlerForRoute(new Route("/tomato.txt", "GET"), textFileHandler);

        serverRouter.setHandlerForRoute(new Route("/public", "GET"), directoryHandler);
        serverRouter.setHandlerForRoute(new Route("/subdirectory", "GET"), directoryHandler);

        serverRouter.setHandlerForRoute(new Route("/", "GET"), defaultRouteHandler);

        return serverRouter;
    }
}
