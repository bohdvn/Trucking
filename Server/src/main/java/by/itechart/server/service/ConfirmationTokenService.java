package by.itechart.server.service;

import by.itechart.server.entity.ConfirmationToken;


public interface ConfirmationTokenService {

    void save(final ConfirmationToken confirmationToken);

    void delete(final ConfirmationToken confirmationToken);

    ConfirmationToken findByConfirmationToken(final String confirmationToken);
}
