
import { IsString, IsNotEmpty, IsNumberString, IsDateString, IsArray } from 'class-validator';
export class UpdateClinicalRecordDto {
    @IsString()
    @IsNotEmpty()
    stage: string;
    @IsString()
    @IsNotEmpty()
    treatmentProtocol: string;
}