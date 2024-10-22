package tolgstoy;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import org.jboss.resteasy.reactive.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Path("hello")
public class GreetingResource {

    public static class Parameters {
        @RestPath
        String type;

        @RestMatrix
        String variant;

        @RestQuery
        String age;

        @RestCookie
        String level;

        @RestHeader("X-Cheese-Secret-Handshake")
        String secretHandshake;

        @RestForm
        String smell;
    }

    /*
     * POST /cheeses;variant=goat/tomme?age=matured HTTP/1.1
     * Content-Type: application/x-www-form-urlencoded
     * Cookie: level=hardcore
     * X-Cheese-Secret-Handshake: fist-bump
     *
     * smell=strong
     */

    @POST()
//    @Path("tolga")
//    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@BeanParam Parameters parameters) {
        return parameters.type + "/" + parameters.variant + "/" + parameters.age
                + "/" + parameters.level + "/" + parameters.secretHandshake
                + "/" + parameters.smell;
    }

    @Path("{name}/{age:\\d+}")
    @GET
    public String personalisedHello(String name, int age) {
        return "Hello " + name + " is your age really " + age + "?";
    }

    @GET
    @Path("hello/v2")
    public RestResponse<String> hello2() {
        // HTTP OK status with text/plain content type
        return RestResponse.ResponseBuilder.ok("Hello, World!", MediaType.TEXT_PLAIN_TYPE)
                // set a response header
                .header("X-Cheese", "Camembert")
                // set the Expires response header to two days from now
//                .expires(Date.from(Instant.now().plus(Duration.ofDays(2))))
                // send a new cookie
                .cookie(new NewCookie("Flavour", "chocolate"))
                // end of builder API
                .build();
    }



    @ResponseStatus(201)
    @ResponseHeader(name = "X-Cheese", value = "Camembert")
    @GET
    @Path("hello/v3")
    public String hello3() {
        return "Hello, World!";
    }


}
