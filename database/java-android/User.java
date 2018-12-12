public class User {

    private int id;
    private String fullname;
    private String email;
    private int age;
    private String city;

    public User() {
        // An empty constructor is required in order to be able to use DataSnapshot.getValue()
    }

    public User(int id, String fullname, String email, int age, String city) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.age = age;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullname='" + fullname + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", city='" + city + '\'' +
                '}';
    }
}