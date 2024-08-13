package library.entity;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Game {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long gameId;
  
  
  @Column(unique = true)
  private String gameName;
  
  private String gameDeveloper;
  
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
  public Set<Review> reviews = new HashSet<>();
}
