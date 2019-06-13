package by.itechart.Server.service;

import by.itechart.Server.dto.ConfirmationTokenDto;


public interface ConfirmationTokenService {

    void save(ConfirmationTokenDto confirmationTokenDto);

    void delete(ConfirmationTokenDto confirmationTokenDto);

    ConfirmationTokenDto findByConfirmationToken(String confirmationToken);
}
