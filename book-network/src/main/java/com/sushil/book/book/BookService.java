package com.sushil.book.book;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sushil.book.common.PageResponse;
import com.sushil.book.exception.OperationNotPermittedException;
import com.sushil.book.file.FileManagementService;
import com.sushil.book.history.BookTransactionHistory;
import com.sushil.book.history.BookTransactionHistoryRepository;
import com.sushil.book.user.User;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService extends Book {

    private final BookMapper bookMapper;
    private final BookTransactionHistoryRepository transactionHistoryRepository;
    private final BookRepository bookRepository;
    private final FileManagementService fileManagementService;

    public Integer save(BookRequest request, Authentication connectedUser) {

        User user = ((User) connectedUser.getPrincipal());
        Book book = bookMapper.toBook(request);
        book.setOwner(user);
        return bookRepository.save(book).getId();
    }

    public BookResponse findById(Integer bookId) {
        return bookRepository.findById(bookId)
                .map(bookMapper::toBookResponse)
                .orElseThrow(() -> new EntityNotFoundException("No book found with Id::" + bookId));
    }

    public PageResponse<BookResponse> findAllBoooks(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAllDisplayableBooks(pageable, user.getId());
        List<BookResponse> bookResponse = books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(
                bookResponse,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast());
    }

    public PageResponse<BookResponse> findAllBoooksByOwner(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAll(BookSpecification.withOwnerId(user.getId()), pageable);
        List<BookResponse> bookResponse = books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(
                bookResponse,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast());
    }

    public PageResponse<BorrowedBookResponse> findAllBorrowedBooks(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<BookTransactionHistory> allBorrowedBooks = transactionHistoryRepository.findAllBorrowedBooks(pageable,
                user.getId());
        List<BorrowedBookResponse> bookResponses = allBorrowedBooks.stream().map(bookMapper::toBorrowedBookResponse)
                .toList();

        return new PageResponse<>(
                bookResponses,
                allBorrowedBooks.getNumber(),
                allBorrowedBooks.getSize(),
                allBorrowedBooks.getTotalPages(),
                allBorrowedBooks.getTotalPages(),
                allBorrowedBooks.isFirst(),
                allBorrowedBooks.isLast());
    }

    public PageResponse<BorrowedBookResponse> findAllReturnedBooks(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<BookTransactionHistory> allBorrowedBooks = transactionHistoryRepository.findAllReturnedBooks(pageable,
                user.getId());
        List<BorrowedBookResponse> bookResponses = allBorrowedBooks.stream().map(bookMapper::toBorrowedBookResponse)
                .toList();

        return new PageResponse<>(
                bookResponses,
                allBorrowedBooks.getNumber(),
                allBorrowedBooks.getSize(),
                allBorrowedBooks.getTotalPages(),
                allBorrowedBooks.getTotalPages(),
                allBorrowedBooks.isFirst(),
                allBorrowedBooks.isLast());
    }

    public Integer updateShareableStatus(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with the id::" + bookId));


        if (!Objects.equals(book.getCreatedBy(), connectedUser.getPrincipal())) {
            new OperationNotPermittedException("You cannot update books shareable sttus");
        }
        book.setShareable(!book.isShareable()  );
        bookRepository.save(book);
        return bookId;
    }

    public Integer updateArchivedStatus(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with the id::" + bookId));


        if (!Objects.equals(book.getCreatedBy(), connectedUser.getPrincipal())) {
            new OperationNotPermittedException("You cannot update books Archived   sttus");
        }
        book.setArchived(!book.isArchived());
        bookRepository.save(book);
        return bookId;
    }

    public Integer borrowBook(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()-> new EntityNotFoundException("Book not found by the given id::" + bookId));

        if(book.isArchived() && !book.isShareable()){
                throw new OperationNotPermittedException("This book is not eligible for borrowing");
        }
        User user = ((User)connectedUser.getPrincipal());
        if(Objects.equals(user.getId(),book.getId())){
                throw new OperationNotPermittedException("You don't need to borrow your book!");
        }
        boolean isBookAlreadyBorrowed = transactionHistoryRepository.isBookAlreadyBorrowed(bookId, user.getId());
        if(isBookAlreadyBorrowed) throw new OperationNotPermittedException("The Book is not available!");

        BookTransactionHistory transactionHistory = BookTransactionHistory.builder()
                .user(user)
                .book(book)
                .returned(false)
                .returnApproved(false)
                .build();

        
        return transactionHistoryRepository.save(transactionHistory).getId();
        
    }

    public Integer retrunBorrowBook(Integer bookId, Authentication connectedUser) {
        
        Book book = bookRepository.findById(bookId) .orElseThrow(()-> new EntityNotFoundException("Book not found by the given id::" + bookId));
        if(book.isArchived() && !book.isShareable()){
                throw new OperationNotPermittedException("This book is not eligible for borrowing");
        }
        User user = ((User)connectedUser.getPrincipal());
        if(Objects.equals(user.getId(),book.getId())){
                throw new OperationNotPermittedException("You don't need to borrow your book!");
        }
        BookTransactionHistory transactionHistory = transactionHistoryRepository.findByBookIdAndUserId(bookId, user.getId())
                .orElseThrow(() -> new OperationNotPermittedException("You did not borrow this book!"));

        transactionHistory.setReturned(true);

        return transactionHistoryRepository.save(transactionHistory).getId();
    }

    public Integer approveRetrunBorrowBook(Integer bookId, Authentication connectedUser) {

        Book book = bookRepository.findById(bookId) .orElseThrow(()-> new EntityNotFoundException("Book not found by the given id::" + bookId));
        if(book.isArchived() && !book.isShareable()){
                throw new OperationNotPermittedException("This book is not eligible for borrowing");
        }
        User user = ((User)connectedUser.getPrincipal());
        if(Objects.equals(user.getId(),book.getId())){
                throw new OperationNotPermittedException("You don't need to borrow your book!");
        }
        BookTransactionHistory transactionHistory = transactionHistoryRepository.findByBookIdAndOwnerId(bookId, user.getId())
        .orElseThrow(() -> new OperationNotPermittedException("The book is not ready to be approved yet"));
        
        transactionHistory.setReturnApproved(true);
        
        return transactionHistoryRepository.save(transactionHistory).getId();
}

public void uplaodBookCoverPicture(Integer bookId, MultipartFile file, Authentication connectedUser) {
        
        User user = ((User)connectedUser.getPrincipal());
        Book book = bookRepository.findById(bookId) .orElseThrow(()-> new EntityNotFoundException("Book not found by the given id::" + bookId));
        String cover = fileManagementService.saveFile(user.getId(), file);
    }
}
