package com.iwor.spring.database.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NamedEntityGraph(name = "withLocales",
        attributeNodes = @NamedAttributeNode("locales")
)
@NamedEntityGraph(name = "withLocalesAndUsers",
        attributeNodes = {
                @NamedAttributeNode("locales"),
                @NamedAttributeNode("users")
        }
)
@NamedQuery(name = "Company.findByName", query = "SELECT c FROM Company c WHERE lower(c.name) = lower(:name2)")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "name")
@ToString(exclude = "locales")
@Builder
@Entity
@Table(schema = "spring")
public class Company implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Builder.Default
    @ElementCollection
    @CollectionTable(schema = "spring", name = "company_locales",
            joinColumns = @JoinColumn(name = "company_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"lang", "company_id"}))
    @MapKeyColumn(name = "lang")
    @Column(name = "description")
    private Map<String, String> locales = new HashMap<>();

    @Builder.Default
    @OneToMany(mappedBy = "company")
    private List<User> users = new ArrayList<>();

    public void addUsers(User... users) {
        Arrays.stream(users).forEach(user -> {
            this.users.add(user);
            user.setCompany(this);
        });
    }
}
