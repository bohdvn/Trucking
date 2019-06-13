package by.itechart.server.service;

import by.itechart.server.dto.ConfirmationTokenDto;


public interface ConfirmationTokenService {

    void save(ConfirmationTokenDto confirmationTokenDto);

    void delete(ConfirmationTokenDto confirmationTokenDto);

    ConfirmationTokenDto findByConfirmationToken(String confirmationToken);
}
