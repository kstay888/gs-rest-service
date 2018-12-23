package hello;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final Map<Long, Visitor> greetings = new LinkedHashMap<Long, Visitor>();
    
    // Postman - http://localhost:8080/greetings
    @RequestMapping("/greetings")
    public List<Visitor> greetings(@RequestParam(value="name", defaultValue="World") String name) {
    	
    	return greetings.values().stream().collect(Collectors.toList());
    }

    // Postman - http://localhost:8080/greeting/1
    @RequestMapping(value = "/greeting/{id}", method = RequestMethod.GET)
    public Visitor greeting(@PathVariable long id) {
    	return greetings.getOrDefault(id, new Visitor(-1, "Unknown Visitor"));
    }
    
    @RequestMapping(value = "/createGreeting", method = RequestMethod.POST)
    public ResponseEntity < String > createPerson(@RequestBody Visitor person) {

    	System.out.println("createGreeting Greeting : " + person);
    	if (greetings.putIfAbsent(person.getId(), person)==null) {
    		return ResponseEntity.status(HttpStatus.CREATED).build();
    	} else {
    		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    	}
        
        //return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }
    
    @RequestMapping(value = "/deleteGreeting/{id}", method = RequestMethod.DELETE)
    public ResponseEntity < String > deleteGreeting(@PathVariable long id) {

    	System.out.println("deleteGreeting Greeting : " + id);
    	if (greetings.containsKey(id)) { 
    		greetings.remove(id);
    		return ResponseEntity.status(HttpStatus.OK).build();
    	} else {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    	}
        //return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }

    @RequestMapping(value = "/updateGreeting", method = RequestMethod.PUT)
    public ResponseEntity < String > updateGreeting(@RequestBody Visitor person) {

    	System.out.println("updateGreeting Greeting : " + person);
    	if (greetings.containsKey(person.getId())) { 
    		greetings.replace(person.getId(), person);
    		return ResponseEntity.status(HttpStatus.OK).build();
    	} else {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    	}
        
        //return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }
}
