package sheridan.mamtani.vetclinic.demo.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sheridan.mamtani.vetclinic.demo.model.Pet;
import sheridan.mamtani.vetclinic.demo.model.PetForm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PetFormServiceImpl implements PetFormService {

    private PetRepository petRepository;

    @Autowired
    PetFormServiceImpl(PetRepository petRepository){
        this.petRepository = petRepository;
    }

    private static void copyFormToPet(PetForm form, Pet pet){
        pet.setId(form.getId());
        pet.setPetName(form.getPetName());
        pet.setPetAge(form.getPetAge());

    }

    private static void copyPetToForm(Pet pet, PetForm form){
        form.setId(pet.getId());
        form.setPetName(pet.getPetName());
        form.setPetAge(pet.getPetAge());
    }

    @Override
    public void insertPetForm(PetForm form) {
        Pet pet = new Pet();
        copyFormToPet(form, pet);
        pet = petRepository.save(pet);
        petRepository.flush();
        form.setId(pet.getId());
    }

    @Override
    public List<PetForm> getAllPetForms() {
        List<PetForm> formList = new ArrayList<>();
        List<Pet> petList = petRepository.findAll();
        for(Pet pet: petList){
            PetForm form = new PetForm();
            copyPetToForm(pet, form);
            formList.add(form);
        }
        return formList;
    }

    @Override
    public void deleteAllPetForms() {
        petRepository.deleteAll();
    }

    @Override
    public void deletePetForm(int id) {
        petRepository.deleteById(id);
    }

    @Override
    public PetForm getPetForm(int id) {
        Optional<Pet> result = petRepository.findById(id);
        if(result.isPresent()){
            PetForm form = new PetForm();
            Pet pet = result.get();
            copyPetToForm(pet, form);
            return form;
        }
        return null;
    }

    @Override
    public void updatePetForm(PetForm form) {
        Optional<Pet> result = petRepository.findById(form.getId());
        if(result.isPresent()){
            Pet pet = result.get();
            copyFormToPet(form, pet);
            petRepository.save(pet);
            petRepository.flush();
        }
    }
}
