package cz.cvut.fel.ear.planIt.rest;

import cz.cvut.fel.ear.planIt.model.CalendarEntry;
import cz.cvut.fel.ear.planIt.model.CalendarInstance;
import cz.cvut.fel.ear.planIt.model.Category;
import cz.cvut.fel.ear.planIt.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/personalCalendar")
public class PersonalCalendarController {

    protected final PersonalCalendarService service;

    protected final CategoryService categoryService;

    protected final CalendarEntryService calendarEntryService;

    @Autowired
    public PersonalCalendarController(PersonalCalendarService service, CategoryService categoryService, CalendarEntryService calendarEntryService) {
        this.service = service;
        this.categoryService = categoryService;
        this.calendarEntryService = calendarEntryService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    @PostAuthorize("returnObject.owner.username == principal.username")
    public CalendarInstance find(@PathVariable("id") Integer id){
        final CalendarInstance ci = service.find(id);
        if (ci == null) {
            throw new NoSuchElementException("Calendar Instance was not found");
            }
        return ci;
    }

    @GetMapping(value = "/{id}/entries", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CalendarEntry> getAllEntries(@PathVariable Integer id) {
        return service.findAllEntries(find(id));
    }

    @GetMapping(value = "/{id}/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Category> getAllCategories(@PathVariable Integer id) {
        return service.findAllCategories(find(id));
    }

    @GetMapping(value = "/{id}/categories/entries", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CalendarEntry> getAllEntriesByCategory(@PathVariable Integer id, @RequestBody String color) {
        final Category category = categoryService.findCategory(color);
        return service.findAllEntriesByCategory(category);
    }


    @PostMapping(value = "/{id}/entries")
    public ResponseEntity<Void> addEntry(@PathVariable Integer id, @RequestBody CalendarEntry ce) {
        ce.setCalendarInstance(find(id));
        service.createNewEntry(find(id), ce);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id_ci}/entries/{id_ce}")
    public ResponseEntity<Void> removeEntry(@PathVariable Integer id_ci, @PathVariable Integer id_ce) {
        final CalendarEntry ce = calendarEntryService.find(id_ce);
        if (ce == null) {
            throw new NoSuchElementException("Calendar Entry was not found");
        }
        service.deleteEntryByName(find(id_ci), ce.getName());
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{id_ci}/entries/{id_ce}/categories")
    public ResponseEntity<Void> addCategoryToEntry(@PathVariable Integer id_ci, @PathVariable Integer id_ce, @RequestBody String color) {
        final CalendarEntry ce = calendarEntryService.find(id_ce);
        if (ce == null) {
            throw new NoSuchElementException("Calendar Entry was not found");
        }
        calendarEntryService.addCategoryToEntry(find(id_ci), ce.getName(), color);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id_ci}/entries/{id_ce}/categories")
    public ResponseEntity<Void> removeCategoryFromEntry(@PathVariable Integer id_ci, @PathVariable Integer id_ce, @RequestBody String color) {
        final CalendarEntry ce = calendarEntryService.find(id_ce);
        if (ce == null) {
            throw new NoSuchElementException("Calendar Entry was not found");
        }
        calendarEntryService.deleteCategoryFromEntry(find(id_ci), ce.getName(), color);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PutMapping(value = "/{id_ci}/categories")
    public ResponseEntity<Void> changeCategoryName(@PathVariable Integer id_ci, @RequestBody Category category){
        final CalendarInstance ci = service.find(id_ci);
        if (ci == null) {
            throw new NoSuchElementException("Calendar Instance was not found");
        }
        final Category cat = categoryService.findCategory(category.getColor().toString().toUpperCase());
        if (cat == null) {
            throw new NoSuchElementException("Category was not found");
        }
        categoryService.setNameToCategory(ci, category.getName(), cat.getColor().toString());
        return new ResponseEntity<>(null, HttpStatus.OK);
    }


}
