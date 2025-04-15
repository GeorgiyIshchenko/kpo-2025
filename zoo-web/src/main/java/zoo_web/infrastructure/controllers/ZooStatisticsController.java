package zoo_web.infrastructure.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zoo_web.application.services.ZooStatisticsService;

@RestController
@RequestMapping("/stats")
public class ZooStatisticsController {

    private final ZooStatisticsService statsService;

    public ZooStatisticsController(ZooStatisticsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/animals/count")
    public int getTotalNumberOfAnimals() {
        return statsService.countAnimals();
    }

    @GetMapping("/enclosures/free")
    public int getNumberOfFreeEnclosures() {
        return statsService.countFreeEnclosures();
    }

    @GetMapping("/animals/sick")
    public long getNumberOfSickAnimals() {
        return statsService.getNumberOfSickAnimals();
    }

}
