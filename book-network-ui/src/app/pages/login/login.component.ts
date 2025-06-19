import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationRequest, AuthenticationResponse } from 'src/app/services/models';
import { AuthenticationService } from 'src/app/services/services';
import { TokenService } from 'src/app/services/token/token.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  
  constructor(
    private router: Router,
    private authService: AuthenticationService,
    private tokenService: TokenService
  ){}

  authRequest: AuthenticationRequest = { email: '', password: '' };
  errorMsg: Array<string> = [];

  register() {
    this.router.navigate(['register'])
  }
  login() {
    this.errorMsg = [];
    this.authService.authenticate(
      {
        body: this.authRequest
      }
    ).subscribe(

      {
        next: (res: AuthenticationResponse): void => {
          this.tokenService.token = res.token as string;
          this.router.navigate(['books']);
        },
        error: (err):void => {
          console.log(err);
          // debugger;
          if (err.error.validationErrors){
            this.errorMsg = err.error.validationErrors;
          }
          else {
            this.errorMsg.push(err.error.businessExceptionDescription)
          }
        }
      }
    )
  }
}
