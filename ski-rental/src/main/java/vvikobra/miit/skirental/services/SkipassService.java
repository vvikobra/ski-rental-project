package vvikobra.miit.skirental.services;

import com.example.skirentalcontracts.api.dto.skipass.SkipassResponse;

import java.util.UUID;

public interface SkipassService {
    SkipassResponse getSkipass(UUID id);

    SkipassResponse useLift(UUID id);

}
