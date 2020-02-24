package org.acme.quarkus.sample;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.reactivestreams.Publisher;

import io.smallrye.reactive.messaging.annotations.Channel;
import io.smallrye.reactive.messaging.annotations.Emitter;

@Path("/greetings")
public class HelloResource {

    @Inject
    @Channel("translated-greetings")
    Publisher<String> linguaGreetings;

    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Publisher<String> linguaGreeting(){
        return linguaGreetings;
    }

}
