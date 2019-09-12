package sheridan.mamtani.vetclinic.demo.data;

import org.springframework.data.jpa.repository.JpaRepository;
import sheridan.mamtani.vetclinic.demo.model.Pet;

public interface PetRepository extends JpaRepository<Pet, Integer> {
}
