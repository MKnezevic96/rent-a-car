import { User } from './User';
    export class Pricing {
        distanceLimit: number;
        regularPrice: number;
        overusePrice: number;
        collisionDamage: number;
        discountDays: number;
        discountPercent: number;
        name: string;
        owner: string;
        deleted: boolean;
    }