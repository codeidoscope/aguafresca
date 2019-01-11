package com.github.codeidoscope;

public class Server {

    public static void main(String[] args) {
        ServerConnection serverConnection = new TCPServerConnection();
        ServerRouter serverRouter = createValidPathList(new ServerRouter());
        ServerRunner serverRunner = new ServerRunner(serverConnection, serverRouter);

        int portNumber = Integer.parseInt(args[1]);
        serverRunner.startServer(portNumber);
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
