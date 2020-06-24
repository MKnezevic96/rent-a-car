export class UserRequest {
    firstname: string;
    lastname: string;
    email: string;
    password: string;
    isSelected: string;  
    name : string;
    adress : string;
    number : string;



    constructor (firstname:string, lastname:string, email:string, password:string, name:string, adress: string, number: string  ) {
        this.firstname=firstname;
        this.lastname=lastname;
        this.email=email;
        this.password=password;
        this.isSelected="User";
        this.name=name;
        this.adress=adress;
        this.number=number;
    }
}