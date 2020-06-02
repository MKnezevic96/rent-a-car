import { User } from './User';
import { Company } from './Company';

    export class Pricing {
        distancelimit: DoubleRange;
        regularprice: DoubleRange;
        overuseprice: DoubleRange;
        collisiondamage: DoubleRange;
        discountdays: DoubleRange;
        discountperc: DoubleRange;
        name: string;
        owner: User;
        company: Company;
        deleted: boolean;
      
    }
