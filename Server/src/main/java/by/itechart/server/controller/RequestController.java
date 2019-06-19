package by.itechart.server.controller;

import by.itechart.server.dto.ClientCompanyDto;
import by.itechart.server.dto.RequestDto;
import by.itechart.server.entity.Request;
import by.itechart.server.security.CurrentUser;
import by.itechart.server.security.UserPrincipal;
import by.itechart.server.service.ClientCompanyService;
import by.itechart.server.service.RequestService;
import by.itechart.server.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ClientCompanyService clientCompanyService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PreAuthorize("hasAuthority('OWNER') or hasAuthority('DISPATCHER')")
    @PutMapping("/")
    public ResponseEntity<?> edit(final @RequestBody RequestDto requestDto) {
        LOGGER.info("REST request. Path:/car method: POST. car: {}", requestDto);
        requestService.save(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @PostMapping("/")
    public ResponseEntity<?> create(@CurrentUser UserPrincipal userPrincipal,
                                    final @Valid @RequestBody RequestDto requestDto) {
        LOGGER.info("REST request. Path:/car method: POST. car: {}", requestDto);
        final ClientCompanyDto clientCompanyDto = clientCompanyService.findById(userPrincipal.getClientCompanyId());
        requestDto.setClientCompanyFrom(clientCompanyDto);
        requestService.save(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('OWNER') or hasAuthority('DISPATCHER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@CurrentUser UserPrincipal user, @PathVariable("id") int id) {
        LOGGER.info("REST request. Path:/request/{} method: GET.", id);
        final RequestDto requestDto = requestService.findByIdAndClientCompanyFromId(id, user.getClientCompanyId());
        return Objects.nonNull(requestDto) ?
                ResponseEntity.ok().body(requestDto) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAuthority('OWNER')")
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

    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("/list/")
    public ResponseEntity<Page<RequestDto>> getAll(@CurrentUser UserPrincipal userPrincipal, Pageable pageable) {
        LOGGER.info("REST request. Path:/request method: GET.");
        final Page<RequestDto> requestDtos = requestService
                .findAllByClientCompanyFromId(userPrincipal.getClientCompanyId(), pageable);
        return new ResponseEntity<>(requestDtos, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("/list/{query}")
    public ResponseEntity<Page<RequestDto>> getAll(@CurrentUser UserPrincipal userPrincipal, Pageable pageable,
                                                   @PathVariable("query") String query) {
        LOGGER.info("REST request. Path:/request method: GET.");
        final Page<RequestDto> requestDtos = requestService
                .findAllByClientCompanyFromId(userPrincipal.getClientCompanyId(), pageable, query);
        return new ResponseEntity<>(requestDtos, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DISPATCHER')")
    @GetMapping("/notviewed/")
    public ResponseEntity<Page<RequestDto>> getNotViewed(@CurrentUser UserPrincipal userPrincipal, Pageable pageable) {
        LOGGER.info("REST request. Path:/request method: GET.");
        final Page<RequestDto> requestDtos = requestService
                .findAllByClientCompanyFromIdAndStatus(
                        userPrincipal.getClientCompanyId(),
                        Request.Status.NOT_VIEWED,
                        pageable);
        return new ResponseEntity<>(requestDtos, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DISPATCHER')")
    @GetMapping("/notviewed/{query}")
    public ResponseEntity<Page<RequestDto>> getNotViewed(@CurrentUser UserPrincipal userPrincipal, Pageable pageable,
                                                         @PathVariable("query") String query) {
        LOGGER.info("REST request. Path:/request method: GET.");
        final Page<RequestDto> requestDtos = requestService
                .findAllByClientCompanyFromIdAndStatus(
                        userPrincipal.getClientCompanyId(),
                        Request.Status.NOT_VIEWED,
                        pageable,
                        query);
        return new ResponseEntity<>(requestDtos, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(ValidationUtils.getErrorsMap(ex), HttpStatus.BAD_REQUEST);
    }
}
