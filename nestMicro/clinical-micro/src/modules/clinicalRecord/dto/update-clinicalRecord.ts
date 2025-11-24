import { PartialType } from "@nestjs/swagger";
import { CreateClinicalRecordDto } from "./create-clinicalRecord";

export class UpdateClinicalRecordDto extends PartialType(CreateClinicalRecordDto){}