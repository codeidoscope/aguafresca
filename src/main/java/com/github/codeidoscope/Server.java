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
        RouteHandler defaultRouteHandler = new DefaultRouteHandler();

        serverRouter.setHandlerForRoute(new Route("/foo.txt", "GET"), textFileHandler);
        serverRouter.setHandlerForRoute(new Route("/bar.txt", "GET"), textFileHandler);
        serverRouter.setHandlerForRoute(new Route("/baz.txt", "GET"), textFileHandler);

        serverRouter.setHandlerForRoute(new Route("/", "GET"), defaultRouteHandler);

        return serverRouter;
    }
}
