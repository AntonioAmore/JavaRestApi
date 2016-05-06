package com.github.AntonioAmore.JavaRestApi;

import com.github.AntonioAmore.DataStorage.TransactionRepository;
import com.github.AntonioAmore.JavaRestApi.serialization.SerializationProvider;
import io.netty.handler.codec.http.HttpMethod;
import org.restexpress.RestExpress;
import org.restexpress.exception.BadRequestException;
import org.restexpress.exception.ConflictException;
import org.restexpress.exception.NotFoundException;
import org.restexpress.util.Environment;

public class Main {

    protected static TransactionRepository dataStorage = new TransactionRepository();

    public static void main(String[] args) throws Exception {
        RestExpress.setSerializationProvider(new SerializationProvider());
        Configuration config = Environment.load(args, Configuration.class);
        RestExpress server = new RestExpress()
                .setName(config.getName())
                .setPort(config.getPort());

        defineRoutes(server, config);

        if (config.getWorkerCount() > 0) {
            server.setIoThreadCount(config.getWorkerCount());
        }

        if (config.getExecutorThreadCount() > 0) {
            server.setExecutorThreadCount(config.getExecutorThreadCount());
        }

        mapExceptions(server);
        server.bind();
        server.awaitShutdown();
    }

    /**
     * @param server
     * @param config
     */
    private static void defineRoutes(RestExpress server, Configuration config) {

        server.uri("/user.{format}", config.getUserController())
                .action("readAll", HttpMethod.GET)
                .action("create", HttpMethod.POST);

        server.uri("/user/{user_id}.{format}", config.getUserController())
                .action("delete", HttpMethod.DELETE)
                .action("read", HttpMethod.GET)
                .action("update", HttpMethod.PUT);

        server.uri("/transaction.{format}", config.getTransactionController())
                .action("readAll", HttpMethod.GET)
                .action("create", HttpMethod.POST);

        server.uri("/transaction/{transaction_id}.{format}", config.getTransactionController())
                .action("delete", HttpMethod.DELETE)
                .action("read", HttpMethod.GET);

        server.uri("/user/{user_id}/balance.{format}", config.getTransactionController())
                .action("getBalance", HttpMethod.GET);

    }

    /**
     * @param server
     */
    private static void mapExceptions(RestExpress server) {
    	server
    	.mapException(ItemNotFoundException.class, NotFoundException.class)
    	.mapException(DuplicateItemException.class, ConflictException.class)
    	.mapException(ValidationException.class, BadRequestException.class);
    }

}
