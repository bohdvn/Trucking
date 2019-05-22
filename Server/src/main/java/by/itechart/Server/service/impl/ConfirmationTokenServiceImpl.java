package by.itechart.Server.service.impl;

import by.itechart.Server.entity.ConfirmationToken;
import by.itechart.Server.repository.ConfirmationTokenRepository;
import by.itechart.Server.service.ConfirmationTokenService;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmationTokenServiceImpl(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @Override
    public void save(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    @Override
    public void delete(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.delete(confirmationToken);
    }

    @Override
    public ConfirmationToken findByConfirmationToken(String confirmationToken) {
        return confirmationTokenRepository.findByConfirmationToken(confirmationToken);
    }


}
