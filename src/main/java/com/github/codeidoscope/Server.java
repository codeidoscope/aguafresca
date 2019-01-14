package com.github.codeidoscope;

public class Server {

    public static void main(String[] args) {
        ServerConnection serverConnection = new TCPServerConnection();
        ServerRouter serverRouter = createValidPathList(new ServerRouter());
        ServerRunner serverRunner = new ServerRunner(serverConnection, serverRouter);
        UserInputValidator userInputValidator = new UserInputValidator();

        userInputValidator.validate(args);
        serverRunner.startServer(Configuration.getInstance().getPortNumber());
    }

    private static ServerRouter createValidPathList(ServerRouter serverRouter) {
        Route fooTextFileRoute = new Route("/foo.txt", "GET");
        RouteHandler fooTextFileHandler = new FooTextFileHandler();
        serverRouter.setHandlerForRoute(fooTextFileRoute, fooTextFileHandler);

        Route defaultRoute = new Route("/", "GET");
        RouteHandler defaultRouteHandler = new DefaultRouteHandler();
        serverRouter.setHandlerForRoute(defaultRoute, defaultRouteHandler);

        return serverRouter;
    }
}
