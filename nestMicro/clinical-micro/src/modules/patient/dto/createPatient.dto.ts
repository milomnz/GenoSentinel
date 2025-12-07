import { IsString, IsDate } from 'class-validator';
import { Type } from 'class-transformer';

export class CreatePatientDto {
    @IsString()
    firstName: string;
    @IsString()
    lastName: string;
    @Type(() => Date)
    @IsDate()
    birthDate: Date;
    @IsString()
    gender: string;
    @IsString()
    status: string;
}