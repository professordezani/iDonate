package br.com.idonate.iDonate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Entity(name = "campaign")
@Data
@EqualsAndHashCode(callSuper = false, of = {"id"})
@ToString(of = {"id"})
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @NotNull
    @Size(min = 4, max = 100)
    private String name;

    @NotNull
    @Size(min = 4)
    private String description;

    private String logo;

    @Min(0)
    @Column(name = "goal_points")
    private Integer goalPoints;

    @Min(0)
    @Column(name = "points_received")
    private Integer pointsReceived;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Transient
    private BigDecimal targetPercentage;

    @JsonIgnore
    public Profile getProfile() {
        return this.profile;
    }

    @JsonProperty
    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @JsonProperty
    public BigDecimal getTargetPercentage() {
        BigDecimal goalPoints = new BigDecimal(this.goalPoints).setScale(5);
        BigDecimal pointsReceived = new BigDecimal(this.pointsReceived).setScale(5);
        this.targetPercentage = (pointsReceived.divide(goalPoints, RoundingMode.HALF_EVEN)).multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_EVEN);
        return this.targetPercentage;
    }

    @JsonIgnore
    public void setTargetPercentage(BigDecimal targetPercentage) {
        this.targetPercentage = targetPercentage;
    }

}
