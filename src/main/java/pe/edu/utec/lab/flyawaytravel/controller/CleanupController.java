package pe.edu.utec.lab.flyawaytravel.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utec.lab.flyawaytravel.service.CleanupService;

@RestController
@RequestMapping("/cleanup")
public class CleanupController {

    private final CleanupService cleanupService;

    public CleanupController(CleanupService cleanupService) {
        this.cleanupService = cleanupService;
    }

    @DeleteMapping
    public ResponseEntity<Void> cleanup() {
        cleanupService.cleanupAll();
        return ResponseEntity.noContent().build();
    }
}
