import { IsString, IsDateString, IsDate } from 'class-validator';

export class CreatePatientDto {
    @IsString()
    name: string;

    @IsString()
    lastName: string;

    @IsDate()
    birthDate: Date;

    @IsString()
    gender: string;

    @IsString()
    status: string;
}