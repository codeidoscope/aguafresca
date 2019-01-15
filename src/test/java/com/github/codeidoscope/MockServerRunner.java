package com.github.codeidoscope;

import java.util.logging.Level;

public class MockServerRunner implements ServerRunner {
    private final RequestParser requestParser = new RequestParser();
    private final ResponseSerialiser responseSerialiser = new ResponseSerialiser();
    private MockRouter mockServerRouter;
    private MockServerConnection mockServerConnection;

    MockServerRunner(MockServerConnection mockServerConnection, MockRouter mockServerRouter) {
        this.mockServerConnection = mockServerConnection;
        this.mockServerRouter = mockServerRouter;
    }

    @Override
    public void startServer(int portNumber) {
        ServerLogger.serverLogger.log(Level.INFO, "Connection made to port " + portNumber);
        mockServerConnection.createServerSocket(portNumber);
        mockServerConnection.listenForClientConnection();
            try {
                Request request = requestParser.parse(mockServerConnection.getInput());
                Response response = mockServerRouter.route(request);
                String serialisedResponse = responseSerialiser.serialise(response);

                mockServerConnection.sendOutput(serialisedResponse);

                mockServerConnection.closeClientConnection();
            } catch (RuntimeException e) {
                System.err.println(e);
            }
        mockServerConnection.closeConnection();
    }
}
