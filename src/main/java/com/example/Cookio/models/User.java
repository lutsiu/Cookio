package com.example.Cookio.models;
import com.example.Cookio.utils.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name", nullable = false, length = 100)
    @NotBlank(message = "First name must be provided")
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    @NotBlank(message = "Last name must be provided")
    private String lastName;

    @Column(name = "email", nullable = false, length = 150, unique = true)
    @NotBlank(message = "Email must be provided")
    @Email(message = "Email should be valid")
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    @NotBlank(message = "Password must be provided")
    private String password;

    @Column(name = "avatar", length = 255)
    private String avatar;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_email_verified")
    private boolean isEmailVerified;

    @Column(name = "verification_token", length = 255)
    private String verificationToken;

    @ManyToMany
    @JoinTable(
            name = "user_recipe",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    private Set<Recipe> savedRecipes;

    public void addRecipe(Recipe recipe) {
        this.savedRecipes.add(recipe);
        recipe.getUsers().add(this);
    }
    public void removeRecipe(Recipe recipe) {
        this.savedRecipes.remove(recipe);
        recipe.getUsers().remove(this);
    }
}
