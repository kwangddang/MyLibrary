package com.example.mylibrary.common

import com.example.mylibrary.data.dto.response.BookInfo
import com.example.mylibrary.data.room.entity.Book


fun bookToBookInfo(book: Book): BookInfo =
    BookInfo(
        book.author,
        book.description,
        book.discount,
        book.image,
        book.isbn,
        book.link,
        book.price,
        book.pubdate,
        book.publisher,
        book.title,
        book.isBookMark
    )

fun bookInfoToBook(bookInfo: BookInfo): Book =
    Book(
        bookInfo.author,
        bookInfo.description,
        bookInfo.discount,
        bookInfo.image,
        bookInfo.isbn,
        bookInfo.link,
        bookInfo.price,
        bookInfo.pubdate,
        bookInfo.publisher,
        bookInfo.title,
        bookInfo.isBookMark
    )



