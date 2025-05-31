package com.pepino.booktrackerservice.service;

import com.pepino.bookstorageservice.dto.BookDTO;
import com.pepino.bookstorageservice.entity.Book;
import com.pepino.booktrackerservice.repository.TrackerRepository;
import com.pepino.exception.BookException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrackerServiceImplTest {
    @Mock
    private TrackerRepository trackerRepository;
    @InjectMocks
    private TrackerServiceImpl trackerServiceImpl;

    @Test
    void deleteTrackerSoftWhenBookNotFoundThrowsException() {
        UUID id = UUID.randomUUID();

        when(trackerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BookException.class, () -> trackerServiceImpl.deleteTrackerSoft(id));
    }

}
