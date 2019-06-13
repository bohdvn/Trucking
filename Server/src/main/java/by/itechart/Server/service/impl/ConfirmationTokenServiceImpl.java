package by.itechart.Server.service.impl;

import by.itechart.Server.dto.ConfirmationTokenDto;
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
    public void save(ConfirmationTokenDto confirmationTokenDto) {
        confirmationTokenRepository.save(confirmationTokenDto.transformToEntity());
    }

    @Override
    public void delete(ConfirmationTokenDto confirmationTokenDto) {
        confirmationTokenRepository.delete(confirmationTokenDto.transformToEntity());
    }

    @Override
    public ConfirmationTokenDto findByConfirmationToken(String confirmationToken) {
        return confirmationTokenRepository.findByConfirmationToken(confirmationToken).transformToDto();
    }


}
