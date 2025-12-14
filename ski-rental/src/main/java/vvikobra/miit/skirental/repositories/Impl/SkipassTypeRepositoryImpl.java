package vvikobra.miit.skirental.repositories.Impl;

import org.springframework.stereotype.Repository;
import vvikobra.miit.skirental.models.enums.SkipassType;
import vvikobra.miit.skirental.repositories.BaseRepository;

import java.util.UUID;

@Repository
public class SkipassTypeRepositoryImpl extends BaseRepository<SkipassType, UUID> {
    protected SkipassTypeRepositoryImpl() {
        super(SkipassType.class);
    }
}
