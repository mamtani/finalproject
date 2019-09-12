package sheridan.mamtani.vetclinic.demo.data;

import sheridan.mamtani.vetclinic.demo.model.PetForm;

import java.util.List;

public interface PetFormService {

    void insertPetForm(PetForm form);

    List<PetForm> getAllPetForms();

    void deleteAllPetForms();

    void deletePetForm(int id);

    PetForm getPetForm(int id);

    void updatePetForm(PetForm form);
}
