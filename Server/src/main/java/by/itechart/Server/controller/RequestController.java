package by.itechart.Server.controller;

import by.itechart.Server.dto.RequestDto;
import by.itechart.Server.service.RequestService;
import by.itechart.Server.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
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
import java.util.Objects;

@RestController
@RequestMapping("/request")
public class RequestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestController.class);
    private RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SYSADMIN')")
    @PutMapping("/")
    public ResponseEntity<?> edit(final @RequestBody RequestDto requestDto) {
        LOGGER.info("REST request. Path:/car method: POST. car: {}", requestDto);
        requestService.save(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SYSADMIN')")
    @PostMapping("")
    public ResponseEntity<?> create(final @Valid @RequestBody RequestDto requestDto) {
        LOGGER.info("REST request. Path:/car method: POST. car: {}", requestDto);
        requestService.save(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SYSADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") int id) {
        LOGGER.info("REST request. Path:/request/{} method: GET.", id);
        final RequestDto requestDto = requestService.findById(id);
        return Objects.nonNull(requestDto) ?
                ResponseEntity.ok().body(requestDto) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SYSADMIN')")
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

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SYSADMIN')")
    @GetMapping("/list")
    public ResponseEntity<Page<RequestDto>> getAll(Pageable pageable) {
        LOGGER.info("REST request. Path:/request method: GET.");
        final Page<RequestDto> requestDtos = requestService.findAll(pageable);
        return requestDtos.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(requestDtos, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(ValidationUtils.getErrorsMap(ex), HttpStatus.BAD_REQUEST);
    }
}
