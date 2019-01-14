package com.github.codeidoscope;

public class Server {

    public static void main(String[] args) {
        ServerConnection serverConnection = new TCPServerConnection();
        ServerRouter serverRouter = createValidPathList(new ServerRouter());
        ServerRunner serverRunner = new ServerRunner(serverConnection, serverRouter);

        Configuration.getInstance().setPortNumber(Integer.parseInt(args[1]));
        Configuration.getInstance().setSubPath(args[3]);
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
