package cz.genesis.GenesisResources.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String surname;

    @Column(nullable = false, unique = true)
    private String personid;

    @Column(nullable = false, unique = true)
    private String uuid;

    public User() {
    }

    public User(Long id, String name, String surname, String personid, String uuid) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.personid = personid;
        this.uuid = uuid;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getPersonid() { return personid; }
    public String getUuid() { return uuid; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setPersonid(String personid) { this.personid = personid; }
    public void setUuid(String uuid) { this.uuid = uuid; }
}
