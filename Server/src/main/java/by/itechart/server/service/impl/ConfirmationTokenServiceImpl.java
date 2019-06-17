package by.itechart.server.service.impl;

import by.itechart.server.entity.ConfirmationToken;
import by.itechart.server.repository.ConfirmationTokenRepository;
import by.itechart.server.service.ConfirmationTokenService;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmationTokenServiceImpl(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @Override
    public void save(final ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    @Override
    public void delete(final ConfirmationToken confirmationToken) {
        confirmationTokenRepository.delete(confirmationToken);
    }

    @Override
    public ConfirmationToken findByConfirmationToken(final String confirmationToken) {
        return confirmationTokenRepository.findByConfirmationToken(confirmationToken);
    }

}
