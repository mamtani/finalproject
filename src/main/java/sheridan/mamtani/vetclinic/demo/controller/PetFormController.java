package sheridan.mamtani.vetclinic.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sheridan.mamtani.vetclinic.demo.data.PetFormService;
import sheridan.mamtani.vetclinic.demo.model.PetForm;

import java.util.List;

@Controller
@RequestMapping("/pets")
public class PetFormController {

    private final Logger logger = LoggerFactory.getLogger(PetFormController.class);



    private PetFormService petFormService;

    @Autowired
    public PetFormController(PetFormService petFormService){
        this.petFormService = petFormService;
    }

    @RequestMapping(value={"/","/Index.do"})
    public String index(){
        logger.trace("index() is called");
        return "Pets";
    }


    @RequestMapping("/AddPet.do")
    public ModelAndView addPet(){
        logger.trace("addPet() is called");
        ModelAndView modelAndView =
                new ModelAndView("AddPet",
                                    "form", new PetForm());
        return modelAndView;
    }


    @RequestMapping("/InsertPet.do")
    public String insertPet(
            @Validated @ModelAttribute("form") PetForm form,
            BindingResult bindingResult,
            Model model){
        logger.trace("insertPet() is called");
        // correcting the checkbox input values

        if (!bindingResult.hasErrors()) {
            logger.trace("the user inputs are correct");
            petFormService.insertPetForm(form);
            return "redirect:ConfirmInsert.do?id=" + form.getId();
        } else {
            logger.trace("input validation errors");
            model.addAttribute("form", form);
            return "AddPet";
        }
    }


    @RequestMapping("/ConfirmInsert.do")
    public String confirmInsert(@RequestParam(name = "id") String strId, Model model){
        logger.trace("confirmInsert() is called");
        if (strId == null || strId.isEmpty()) {
            logger.trace("no id in the request");
            return "DataNotFound";
        } else {
            try {
                int id = Integer.parseInt(strId);
                logger.trace("looking for the data in the database");
                PetForm form = petFormService.getPetForm(id);
                if (form == null) {
                    logger.trace("no data for this id=" + id);
                    return "DataNotFound";
                } else {
                    logger.trace("showing the data");
                    model.addAttribute("pet", form);
                    return "ConfirmInsert";
                }
            } catch (NumberFormatException e) {
                logger.trace("the id in not an integer");
                return "DataNotFound";
            }
        }
    }

    @RequestMapping("/ListPets.do")
    public ModelAndView listPets() {
        logger.trace("listPets() is called");
        List<PetForm> list = petFormService.getAllPetForms();
        return new ModelAndView("ListPets",
                                "pets", list);
    }


    @RequestMapping("/DeleteAll.do")
    public String deleteAll(){
        logger.trace("deleteAll() is called");
        petFormService.deleteAllPetForms();
        return "redirect:ListPets.do";
    }

    @RequestMapping("PetDetails.do")
    public String petDetails(@RequestParam String id, Model model){
        logger.trace("petDetails() is called");
        try {
            PetForm form = petFormService.getPetForm(Integer.parseInt(id));
            if (form != null) {
                model.addAttribute("pet", form);
                return "PetDetails"; // show the pet data in the form to edit
            } else {
                logger.trace("no data for this id=" + id);
                return "DataNotFound";
            }
        } catch (NumberFormatException e) {
            logger.trace("the id is missing or not an integer");
            return "DataNotFound";
        }
    }

    // a user clicks "Delete" link (in the table) to "DeleteStudent"

    @RequestMapping("/DeletePet.do")
    public String deletePet(@RequestParam String id, Model model) {
        logger.trace("deletePet() is called");
        try {
            PetForm form = petFormService.getPetForm(Integer.parseInt(id));
            if (form != null) {
                model.addAttribute("pet", form);
                return "DeletePet"; // ask "Do you really want to remove?"
            } else {
                return "redirect:ListPets.do";
            }
        } catch (NumberFormatException e) {
            return "redirect:ListPets.do";
        }
    }

    // a user clicks "Remove Record" button in "DeleteStudent" page,
    // the form submits the data to "RemoveStudent"

    @RequestMapping("/RemovePet.do")
    public String removePet(@RequestParam String id) {
        logger.trace("removePet() is called");
        try {
            petFormService.deletePetForm(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            logger.trace("the id is missing or not an integer");
        }
        return "redirect:ListPets.do";
    }

    // a user clicks "Edit" link (in the table) to "EditStudent"

    @RequestMapping("/EditPet.do")
    public String editPet(@RequestParam String id, Model model) {
        logger.trace("editPet() is called");
        try {
            PetForm form = petFormService.getPetForm(Integer.parseInt(id));
            if (form != null) {
                model.addAttribute("form", form);
                return "EditPet";
            } else {
                logger.trace("no data for this id=" + id);
                return "redirect:ListPets.do";
            }
        } catch (NumberFormatException e) {
            logger.trace("the id is missing or not an integer");
            return "redirect:ListStudents.do";
        }
    }

    // the form submits the data to "UpdateStudent"

    @RequestMapping("/UpdatePet.do")
    public String updatePet(
            @Validated @ModelAttribute("form") PetForm form,
            BindingResult bindingResult,
            Model model) {
        logger.trace("updatePet() is called");
        // checking for the input validation errors
        if (!bindingResult.hasErrors()) {
            logger.trace("the user inputs are correct");
            petFormService.updatePetForm(form);
            return "redirect:PetDetails.do?id=" + form.getId();
        } else {
            logger.trace("input validation errors");
            model.addAttribute("form", form);
            return "EditPet";
        }
    }
}
