import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationResponse, RegistrationRequest } from 'src/app/services/models';
import { AuthenticationService } from 'src/app/services/services';
import { TokenService } from 'src/app/services/token/token.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  
  constructor(
    private router: Router,
    private authService: AuthenticationService,
    private tokenService: TokenService
  ){}
  
  registerRequest: RegistrationRequest = {email: '', firstName: '', lastName: '', password: ''}
  errorMsg: Array<string> = [];
  
  login() {
    
    this.router.navigate(['login']);
  
  }

  
  register() {
    this.errorMsg = [];
        this.authService.register(
          {
            body: this.registerRequest
          }
        ).subscribe(
          {
            next: (res: any): void => {
              this.router.navigate(['activate']);
            },
            error: (err):void => {
              console.log(err);
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
