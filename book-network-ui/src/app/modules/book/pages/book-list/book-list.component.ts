import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PageResponseBookResponse } from 'src/app/services/models';
import { BookService } from 'src/app/services/services';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.scss']
})
export class BookListComponent implements OnInit {
  size: number | undefined;
  page: number | undefined = 5;

  bookResponse: PageResponseBookResponse = {};

  constructor(
    private bookService: BookService,
    private router: Router
  ){
    
  }
  ngOnInit(): void {
    this.findAllBooks();
  }
  findAllBooks() {
  
    this.bookService.finadAllBooks({
      page:this.page,
      size:this.size
    }).subscribe({
      next: (books:PageResponseBookResponse): void => {
        debugger;
        this.bookResponse = books
      } 
    })
  }

}
