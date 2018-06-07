package nl.quintor.studybits.university.controllers.admin;

import lombok.AllArgsConstructor;
import nl.quintor.studybits.university.UserContext;
import nl.quintor.studybits.university.entities.ExchangeApplicationRecord;
import nl.quintor.studybits.university.models.ExchangeApplicationModel;
import nl.quintor.studybits.university.services.ExchangeApplicationService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/{universityName}/admin/{userName}/applications")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AdminExchangeApplicationController {

    private final ExchangeApplicationService exchangeApplicationService;
    private final UserContext userContext;
    private final Mapper mapper;

    private ExchangeApplicationModel toModel(ExchangeApplicationRecord record) {
        return mapper.map(record, ExchangeApplicationModel.class);
    }

    @GetMapping
    List<ExchangeApplicationModel> getAllApplications() {
        return exchangeApplicationService
                .findAllForUniversity(userContext.currentUniversityName())
                .stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    @PostMapping
    void updateState(@RequestBody ExchangeApplicationModel model) {
        exchangeApplicationService.updateState(model);
    }
}