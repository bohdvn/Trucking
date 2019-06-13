package by.itechart.Server.controller;

import by.itechart.Server.dto.RequestDto;
import by.itechart.Server.entity.Request;
import by.itechart.Server.service.RequestService;
import by.itechart.Server.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/request")
public class RequestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestController.class);
    private RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PutMapping("/")
    public ResponseEntity<?> edit(final @RequestBody Request request) {
        LOGGER.info("REST request. Path:/car method: POST. car: {}", request);
        if(request.getStatus().equals(null)){
            request.setStatus(Request.Status.ISSUED);
        }
        requestService.save(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @PostMapping("")
    public ResponseEntity<?> create(final @Valid @RequestBody Request request) {
        LOGGER.info("REST request. Path:/car method: POST. car: {}", request);
        requestService.save(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") int id) {
        LOGGER.info("REST request. Path:/request/{} method: GET.", id);
        Optional<Request> request = requestService.findById(id);
        return request.isPresent() ?
                ResponseEntity.ok().body(request.get().transform()) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{selectedRequests}")
    public ResponseEntity<?> remove(@PathVariable("selectedRequests") String selectedRequests) {
        LOGGER.info("REST request. Path:/request/{} method: DELETE.", selectedRequests);
        final String delimeter = ",";
        final String[] requestId = selectedRequests.split(delimeter);
        for (String id : requestId) {
            requestService.deleteById(Integer.valueOf(id));
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<RequestDto>> getAll(Pageable pageable) {
        LOGGER.info("REST request. Path:/request method: GET.");
        Page<Request> requests = requestService.findAll(pageable);
        Page<RequestDto> requestDtos = new PageImpl<>(requests.stream().map(Request::transform)
                .sorted(Comparator.comparing(RequestDto::getStatus))
                .collect(Collectors.toList()), pageable, requests.getTotalElements());
        LOGGER.info("Return requestList.size:{}", requestDtos.getNumber());
        return requests.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(requestDtos, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(ValidationUtils.getErrorsMap(ex), HttpStatus.BAD_REQUEST);
    }
}
