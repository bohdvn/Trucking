package by.itechart.Server.service;

import by.itechart.Server.entity.ConfirmationToken;


public interface ConfirmationTokenService {

    void save(ConfirmationToken confirmationToken);
    void delete(ConfirmationToken confirmationToken);
    ConfirmationToken findByConfirmationToken(String confirmationToken);
}
