package sheridan.mamtani.vetclinic.demo.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "student")
public class Pet implements Serializable {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id = 0;

    @Column(name = "pet_name")
    private String petName = "";

    @Column(name = "pet_age")
    private int petAge;



    public Pet(){
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public int getPetAge() {
        return petAge;
    }

    public void setPetAge(int petAge) {
        this.petAge = petAge;
    }



}
