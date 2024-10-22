package tolgstoy;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Path("/fruits")
public class RedirectionExample {
    public static class Fruit {
        public Long id;
        public String name;
        public String description;

        public Fruit() {
        }

        public Fruit(Long id, String name, String description) {
            this.id = id;
            this.name = name;
            this.description = description;
        }
    }

    private final Map<Long, Fruit> fruits = new ConcurrentHashMap<>();
    private final AtomicLong ids = new AtomicLong(0);


    public RedirectionExample() {
        Fruit apple = new Fruit(ids.incrementAndGet(), "Apple", "Winter fruit");
        fruits.put(apple.id, apple);
        Fruit pinneapple = new Fruit(ids.incrementAndGet(), "Pineapple", "Tropical fruit");
        fruits.put(pinneapple.id, pinneapple);
    }

    // when invoked, this method will result in an HTTP redirect to the GET method that obtains the fruit by id
    @POST
    public RestResponse<Fruit> add(Fruit fruit, @Context UriInfo uriInfo) {
        fruit.id = ids.incrementAndGet();
        fruits.put(fruit.id, fruit);
        // seeOther results in an HTTP 303 response with the Location header set to the value of the URI
        return RestResponse.seeOther(uriInfo.getAbsolutePathBuilder().path(Long.toString(fruit.id)).build());
    }

    @GET
    @Path("{id}")
    public Fruit byId(Long id) {
        return fruits.get(id);
    }
}
