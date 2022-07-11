package cz.cvut.fel.ear.planIt.rest;

import cz.cvut.fel.ear.planIt.model.CalendarEntry;
import cz.cvut.fel.ear.planIt.model.CalendarInstance;
import cz.cvut.fel.ear.planIt.model.Category;
import cz.cvut.fel.ear.planIt.model.GroupCalendar;
import cz.cvut.fel.ear.planIt.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/groupCalendar")
public class GroupCalendarController {

    protected final GroupCalendarService service;

    protected final CategoryService categoryService;

    protected final CalendarEntryService calendarEntryService;

    @Autowired
    public GroupCalendarController(GroupCalendarService service, CategoryService categoryService, CalendarEntryService calendarEntryService) {
        this.service = service;
        this.categoryService = categoryService;
        this.calendarEntryService = calendarEntryService;
    }


    @GetMapping(value = "/{id}/entries", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CalendarEntry> getAllEntries(@PathVariable Integer id) {
        final CalendarInstance ci = service.find(id);
        if (ci == null) {
            throw new NoSuchElementException("Calendar Instance was not found");
        }
        return service.findAllEntries(ci);
    }

    @GetMapping(value = "/{id}/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Category> getAllCategories(@PathVariable Integer id) {
        final CalendarInstance ci = service.find(id);
        if (ci == null) {
            throw new NoSuchElementException("Calendar Instance was not found");
        }
        return service.findAllCategories(ci);
    }

    @GetMapping(value = "/{id}/categories/entries", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CalendarEntry> getAllEntriesByCategory(@PathVariable Integer id, @RequestBody Category category) {
        final CalendarInstance ci = service.find(id);
        if (ci == null) {
            throw new NoSuchElementException("Calendar Instance was not found");
        }
        return service.findAllEntriesByCategory(category);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    @PostMapping(value = "/{id}/entries")
    public ResponseEntity<Void> addEntry(@PathVariable Integer id, @RequestBody CalendarEntry ce){
        final CalendarInstance ci = service.find(id);
        if (ci == null) {
            throw new NoSuchElementException("Calendar Instance was not found");
        }
        service.createNewEntry(ci, ce);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    @DeleteMapping(value = "/{id_ci}/entries/{id_ce}")
    public ResponseEntity<Void> removeEntry(@PathVariable Integer id_ci, @PathVariable Integer id_ce){
        final CalendarInstance ci = service.find(id_ci);
        if (ci == null) {
            throw new NoSuchElementException("Calendar Instance was not found");
        }
        final CalendarEntry ce = calendarEntryService.find(id_ce);
        if (ce == null) {
            throw new NoSuchElementException("Calendar Entry was not found");
        }
        service.deleteEntryByName(ci, ce.getName());
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    @PostMapping(value = "/{id_ci}/entries/{id_ce}/categories")
    public ResponseEntity<Void> addCategoryToEntry(@PathVariable Integer id_ci, @PathVariable Integer id_ce, @RequestBody String color){
        final CalendarInstance ci = service.find(id_ci);
        if (ci == null) {
            throw new NoSuchElementException("Calendar Instance was not found");
        }
        final CalendarEntry ce = calendarEntryService.find(id_ce);
        if (ce == null) {
            throw new NoSuchElementException("Calendar Entry was not found");
        }
        calendarEntryService.addCategoryToEntry(ci, ce.getName(), color);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    @DeleteMapping(value = "/{id_ci}/entries/{id_ce}/categories")
    public ResponseEntity<Void> removeCategoryFromEntry(@PathVariable Integer id_ci, @PathVariable Integer id_ce, @RequestBody String color){
        final CalendarInstance ci = service.find(id_ci);
        if (ci == null) {
            throw new NoSuchElementException("Calendar Instance was not found");
        }
        final CalendarEntry ce = calendarEntryService.find(id_ce);
        if (ce == null) {
            throw new NoSuchElementException("Calendar Entry was not found");
        }
        calendarEntryService.deleteCategoryFromEntry(ci, ce.getName(), color);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
