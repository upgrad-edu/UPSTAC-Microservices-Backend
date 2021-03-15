package org.upgrad.upstac.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.upgrad.upstac.users.models.Gender;
import org.upgrad.upstac.users.models.AccountStatus;
import org.upgrad.upstac.users.roles.Role;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userName;

    @Column
    @JsonIgnore
    @ToString.Exclude
    private String password;

    private LocalDateTime created;

    private LocalDate dateOfBirth;


    private LocalDateTime updated;

    private String firstName;

    private AccountStatus status;

    @Column(unique = true)
    private String email;


    private String lastName;


    private Gender gender;

    @Column(unique = true)
    private String phoneNumber;
    private String address;

    private Integer pinCode;

    //CascadeType.PERSIST has issues with many to many which makes us not use CascadeType.ALL
    //So Using  other Cascades other than CascadeType.PERSIST
//    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE,CascadeType.REFRESH,CascadeType.DETACH})
//    @JoinTable(name = "USER_ROLES", joinColumns = {
//            @JoinColumn(name = "USER_ID") }, inverseJoinColumns = {
//            @JoinColumn(name = "ROLE_ID") })
//    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;


    public boolean doesRoleIsDoctor() {

        return doesUserHasRole("DOCTOR");


    }

    public boolean doesUserHasRole(String s) {
        return roles.stream()
                .filter(role -> {
                    return role.getName().equalsIgnoreCase(s);
                })
                .findFirst()
                .isPresent();
    }

    public boolean doesRoleIsUser() {
        return doesUserHasRole("USER");
    }

    public boolean doesRoleIsAuthority() {
        return doesUserHasRole("GOVERNMENT_AUTHORITY");
    }

    public boolean doesRoleIsTester() {
        return doesUserHasRole("TESTER");
    }

    public Integer getAge(){

        if(null != dateOfBirth)
            return LocalDate.now().getYear() - dateOfBirth.getYear();
        else
            return 0;
    }

}
