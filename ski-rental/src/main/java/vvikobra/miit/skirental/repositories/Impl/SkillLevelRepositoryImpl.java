package vvikobra.miit.skirental.repositories.Impl;

import org.springframework.stereotype.Repository;
import vvikobra.miit.skirental.models.enums.SkillLevel;
import vvikobra.miit.skirental.repositories.BaseRepository;

import java.util.UUID;

@Repository
public class SkillLevelRepositoryImpl extends BaseRepository<SkillLevel, UUID> {
    protected SkillLevelRepositoryImpl() {
        super(SkillLevel.class);
    }
}
