package shparos.reservation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reservation")
public class TestController {
    @GetMapping("/test")
    public String testMethod(){
        return "Reservation Service";
    }
}
