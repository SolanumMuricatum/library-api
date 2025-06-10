package com.pepino.bookstorageservice.service;

import com.pepino.bookstorageservice.client.TrackerServiceClient;
import com.pepino.bookstorageservice.dto.BookDTO;
import com.pepino.bookstorageservice.entity.Book;
import com.pepino.exception.BookException;
import com.pepino.bookstorageservice.repository.BookRepository;
import com.pepino.event.BookCreatedEvent;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;


@Service
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<UUID, BookCreatedEvent> kafkaTemplate;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final TrackerServiceClient trackerServiceClient;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, ModelMapper modelMapper,
                           KafkaTemplate<UUID, BookCreatedEvent> kafkaTemplate,
                           TrackerServiceClient trackerServiceClient){
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.kafkaTemplate = kafkaTemplate;
        this.trackerServiceClient = trackerServiceClient;
    }
    @Override
    public void saveBook(BookDTO bookDTO) throws BookException, ExecutionException, InterruptedException {
        Book book = modelMapper.map(bookDTO, Book.class);
        Example<Book> example = Example.of(book);
        //TODO добавить проверку на исбн тк он тоже уникальный
        if(bookRepository.exists(example)){
            throw new BookException("Book already exists");
        }
        else{
            //тут словить исключения и обработать, а то сохранение идёт в бд и 500 ошибочка
            bookRepository.save(book);

            BookCreatedEvent bookCreatedEvent = new BookCreatedEvent(book.getId(),
                    book.getIsbn(), book.getName(), book.getGenre(), book.getDescription(),
                    book.getAuthor(), book.isDeletedAt());

            SendResult<UUID, BookCreatedEvent> result = kafkaTemplate
                    .send("book-created-events-topic", book.getId(), bookCreatedEvent).get();

            LOGGER.info("Topic: {}", result.getRecordMetadata().topic());
            LOGGER.info("Partition: {}", result.getRecordMetadata().partition());
            LOGGER.info("Offset: {}", result.getRecordMetadata().offset());
        }
    }

    @Override
    public List<BookDTO> getAllBooks() throws BookException {
        List<Book> books = bookRepository.getAllBooks();
        List<BookDTO> booksDTO = books.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .toList();

        if(booksDTO.isEmpty()){
            throw new BookException("No books found");
        }
        else{
            return booksDTO;
        }
    }

    @Override
    public BookDTO getBookById(UUID id) throws BookException {
        Optional<Book> optionalBook = bookRepository.getBookById(id);

        if(optionalBook.isPresent()){
            Book book = optionalBook.get();
            return modelMapper.map(book, BookDTO.class);
        }
        else{
            throw new BookException("Book doesn't exist");
        }
    }


    @Override
    public BookDTO getBookByISBN(String isbn) throws BookException {
        public
        /*Book currentBook = new Book();
        currentBook.setIsbn(ISBN);
        Example<Book> example = Example.of(currentBook);

        Optional<Book> optionalBook = bookRepository.findOne(example);*/

        Optional<Book> optionalBook = bookRepository.getBookByISBN(isbn);

        if(optionalBook.isPresent()){
            Book book = optionalBook.get();
            return modelMapper.map(book, BookDTO.class);
        }
        else{
            throw new BookException("Book doesn't exist");
        }
    }

    @Override
    public void updateBook(BookDTO bookDTO, String currentIsbn) throws BookException {
       /* Book currentBook = new Book();
        currentBook.setIsbn(currentIsbn);
        Example<Book> example = Example.of(currentBook);

        Optional<Book> optionalBook = bookRepository.findOne(example);*/

        Optional<Book> optionalBook = bookRepository.getBookByISBN(currentIsbn);

        if (optionalBook.isPresent()) {
            Book foundBook = optionalBook.get();

            //id не ставим, он вроде передался после найденного example
            if(bookDTO.getIsbn() != null){
                foundBook.setIsbn(bookDTO.getIsbn());
            }
            if(bookDTO.getName() != null){
                foundBook.setName(bookDTO.getName());
            }
            if(bookDTO.getDescription() != null){
                foundBook.setDescription(bookDTO.getDescription());
            }
            if(bookDTO.getGenre() != null){
                foundBook.setGenre(bookDTO.getGenre());
            }
            if(bookDTO.getAuthor() != null){
                foundBook.setAuthor(bookDTO.getAuthor());
            }

            bookRepository.save(foundBook);

        } else {
            throw new BookException("Book for update doesn't exist");
        }
    }

    @Override
    public void deleteBookSoft(String currentIsbn) throws BookException {
       /* Book currentBook = new Book();
        currentBook.setIsbn(currentIsbn);
        Example<Book> example = Example.of(currentBook);

        Optional<Book> optionalBook = bookRepository.findOne(example);*/

        // если жёсткое удаление, то удаляем по айдишнику

        Optional<Book> optionalBook = bookRepository.getBookByISBN(currentIsbn);

        if(optionalBook.isPresent()){
            Book book = optionalBook.get();
            book.setDeletedAt(true);
            bookRepository.save(book);
            trackerServiceClient.deleteTrackerSoft(book.getId());
        }
        else{
            throw new BookException("Book for deletion doesn't exist");
        }
    }

    public void sendBookId(){}
}

