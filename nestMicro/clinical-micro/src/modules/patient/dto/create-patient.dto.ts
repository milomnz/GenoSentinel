import { IsString, IsDate } from 'class-validator';

export class CreatePatientDto {
    @IsString()
    firstName: string;
    @IsString()
    lastName: string;
    @IsDate()
    birthDate: Date;
    @IsString()
    gender: string;
    @IsString()
    status: string;
}