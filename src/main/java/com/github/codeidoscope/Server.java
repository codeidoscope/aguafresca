package com.github.codeidoscope;

public class Server {

    public static void main(String[] args) {
        ServerConnection serverConnection = new TCPServerConnection();
        Router router = createValidPathList(new Router());
        ServerRunner serverRunner = new ServerRunner(serverConnection, router);

        int portNumber = Integer.parseInt(args[1]);
        serverRunner.startServer(portNumber);
    }

    private static Router createValidPathList(Router router) {
        Route validRoute = new Route("/valid", "GET");
        RouteHandler validHandler = new ValidHandler();
        router.setHandlerForRoute(validRoute, validHandler);

        Route fooTextFileRoute = new Route("/foo.txt", "GET");
        RouteHandler fooTextFileHandler = new FooTextFileHandler();
        router.setHandlerForRoute(fooTextFileRoute, fooTextFileHandler);

        Route defaultRoute = new Route("/", "GET");
        RouteHandler defaultRouteHandler = new DefaultRouteHandler();
        router.setHandlerForRoute(defaultRoute, defaultRouteHandler);

        return router;
    }
}
