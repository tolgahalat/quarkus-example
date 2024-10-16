package tolgstoy;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@Path("/hello")
@RequestMapping("/greeting")
public class GreetingResource {

//    @GET
//    @Produces(MediaType.TEXT_PLAIN)
    @GetMapping
    public String hello() {
        return "Hello from Quarkus REST";
    }

    @GetMapping("/{name}")
    public Greeting hello(@PathVariable(name = "name") String name) {
        return new Greeting("hello " + name);
    }

    public class Greeting {
        private final String message;

        public Greeting(String message) {
            this.message = message;
        }

        public String getMessage(){
            return message;
        }
    }

}
