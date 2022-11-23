package data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
//    private int id;
    private String email;
    private String name;
    private String parola;




    private List<Flight> userFlights = new ArrayList<>();
    public List<Flight> getUserFlights() {
        return userFlights;
    }

    public User(String email, String name, String parola ) {

        this.email = email;
        this.name = name;
        this.parola = parola;

    }

    public void addFlight(Flight flight){
        userFlights.add(flight);
    }
    public void deleteFlight(Flight flight){
        userFlights.remove(flight);
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getParola() {
        return parola;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", parola='" + parola + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) && Objects.equals(name, user.name) && Objects.equals(parola, user.parola) && Objects.equals(userFlights, user.userFlights);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, name, parola, userFlights);
    }
}
