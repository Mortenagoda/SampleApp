package dk.vandborgandersen.restapi.backendservices;

import dk.vandborgandersen.restapi.domain.Person;

import java.util.UUID;

/**
 * @author Morten Andersen (mortena@gmail.com)
 */
public class PersonService {

    public Person findPersonByEmail(String email) {
        Person person = new Person();
        person.setPersonId(UUID.randomUUID().toString());
        person.setFirstName("Morten");
        person.setLastName("Andersen");
        person.setEmail("mortena@gmail.com");
        person.setPassword("password");
        return person;
    }
}
