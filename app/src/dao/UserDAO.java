package dao;

public class UserDAO {
    public int id;
    public String login;
    public String name;
    public String surname;
    public String patronymic;
    public String email;
    public String phoneNumber;

    public int getId() {
        return this.id;
    }

    public String getLogin() {
        return this.login;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getPatronymic() {
        return this.patronymic;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }
}
