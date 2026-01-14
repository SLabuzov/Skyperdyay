package by.skyperdyay.engine.core.web;

import by.skyperdyay.engine.core.middleware.model.request.TransferRequest;
import by.skyperdyay.engine.core.middleware.service.TransferEdgeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/funds-transfers")
public class TransfersResource {

    private final TransferEdgeService transferEdgeService;

    public TransfersResource(TransferEdgeService transferEdgeService) {
        this.transferEdgeService = transferEdgeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void executeTransfer(@Valid @RequestBody TransferRequest request) {
        transferEdgeService.executeFundsTransfer(request);
    }
}
