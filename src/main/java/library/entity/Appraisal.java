package library.entity;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Appraisal {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long appraisalId;
  
  private String appraisal;

  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @ManyToMany(mappedBy = "appraisals")
  private Set<Review> reviews = new HashSet<>();
}

