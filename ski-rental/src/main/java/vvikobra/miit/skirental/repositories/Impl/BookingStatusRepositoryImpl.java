package vvikobra.miit.skirental.repositories.Impl;

import org.springframework.stereotype.Repository;
import vvikobra.miit.skirental.models.enums.BookingStatus;
import vvikobra.miit.skirental.repositories.BaseRepository;

import java.util.UUID;

@Repository
public class BookingStatusRepositoryImpl extends BaseRepository<BookingStatus, UUID> {
    protected BookingStatusRepositoryImpl() {
        super(BookingStatus.class);
    }
}
